/*
* =====================================
* serverHttpToCoap.js
* =====================================
*/
 

const
/*
 * 
 */
    app     = require('http').createServer(
	    		function (request, response) {  //request is an IncomingMessage;
	    				requestUtil( request, response, handler );
	    		}),   

	io      = require('socket.io').listen(app); 	//npm install --save socket.io
	http    = require("http"),
	parse   = require('url').parse,
    coap    = require('coap'),
    join    = require('path').join,
    srvUtil = require("./ServerUtils"),
    fs      = require('fs'),
    Readable = require('stream').Readable;

    const config = require('./config/config');  //see development.json
	/*
	 * MQTT PART
	 */
    const mqtt   = require('mqtt');			//npm install --save mqtt
	/*
	 * DB PART: controls
	 */
    const requestUtil = require('./utilsMongoose.js');   //sets connections; 

    //controls for user handling
    const ctrlGet     = require('./controls/ctrlGetUsersRest');
    const ctrlAdd     = require('./controls/ctrlAddUserRest');
    const ctrlDel     = require('./controls/ctrlDeleteUserRest');
    const ctrlChng    = require('./controls/ctrlChangeUserRest'); 
     //Control for a log on Mongo
     const ctrlLogAdd  = require('./controls/ctrlAddLogRest');
    
/*
 * USEFUL 
 */    
var root       	= __dirname; //set by Node to the directory path to the file;
var indexPath  	= root+"/index.html";
var ledViewPath	= root+"/ledState.html";
var html      	 = fs.readFileSync('index.html', 'utf8');

/**
 * MQTT section
 */
const topic  = config.mqttTopic;
const client = mqtt.connect(config.mqttUrl);

client.on('connect', function() {
    client.subscribe(topic);
    console.log('client mqtt has subscribed successfully ');
});

client.on('message', function(topic, message) {
    console.log('mqtt on server RECEIVES:' + message.toString() );
    io.sockets.send( message.toString());
});

/**
 * CREATE section
 */
function createHttpServer(port, callback){
	//configure the system;
		var server = http.createServer();
		server.on( 'request' , handleHttpRequest); 
		console.log('serverHttpToCoap register handleRequest root=' + root);
	//start;
 		server.listen(port, callback );	
};

function handler (request, response) {
	var method = request.method;
	var url    = parse(request.urlPathname);
	var path   = url.pathname;
	if( path === "/" ) path = "/index.html";
	
	console.log("serverHttpToCoap request.method=" + method + " path=" + path + " request=" + request.body); 

	var fpath = join(root, path);
	
	/*
	 * DB PART
	 */
 
	switch ( method ){
		case 'GET' :
			if( path === '/Led' ){
				sendCoapRequest(request, response, "Led", handleCoapAnswer);
			} else	if( path === "/Button" ) { 
		 		sendCoapRequest(request, response, "Button", handleCoapAnswer);	
 		 	} else if( path === '/api/user' ){
 				ctrlGet.getUsers(  response, doAnswerStr );  //perform an asynch  query
			} else  srvUtil.renderStaticFile(fpath,response);  //default index and ico 
			return;
		case 'POST' : //add (sent by browser)
			if ( path === '/ledSwitch' ) {
				sendCoapCoammnd(request, response, handleCoapAnswer);
 			} else if( path==='/api/user'  ){
  				ctrlAdd.addUser(  request.body, response, doAnswerStr );
			} else if( path==='/api/log'  ){
				ctrlLogAdd.addLog(  request.body, response, doAnswerStr );
			}  	
			return;
		case 'PUT':  //modify
			if ( path === '/ledSwitch' ) {
				sendCoapCoammnd(request, response, handleCoapAnswer);
 			} else if ( path === '/Button' ) {
				sendCoapCoammnd(request, response, handleCoapAnswer);
 			} else if( path === '/api/user' ){  //accepts only JSON format;
				var jsonBody = JSON.parse(  request.body  );
				console.log("oldUser=" + jsonBody.old);
				console.log("chngUser=" + jsonBody.new);
 				ctrlChng.changeUser(jsonBody.old, jsonBody.new, response, doAnswerStr );
			}  
			return;
		case 'DELETE':
			if( path === '/api/user' ){ // for JSON only;
				ctrlDel.deleteUser( JSON.parse( reqInfo.body ), response, doAnswerStr);
 			} //else{ response.statusCode=400; response.end("ERROR on DLEETE"); }
			return;
		default:{
			response.writeHead(405, {'Content-type':'application/json'});
			response.end(  "METHOD ERROR"  );		
			return;
		}	
		
	}//switch
 	
	
//	response.setHeader('Content-Type', 'text/html');
//	response.setHeader('Content-Length', Buffer.byteLength(html, 'utf8'));
//	response.end(html);

}

var doAnswerStr = function(err, response, msg){
	console.log("serverHttpToCoap doAnswerStr" +msg);
 	if( err ){	response.statusCode=500; response.end(msg); }
	else{ response.statusCode=200; response.end(msg); }
} 
/**
 * COAP messaging
 */
function sendCoapRequest(request, response, resource, callback ){
	console.log("sendCoapRequest " + resource);
 	req   = coap.request('coap://localhost/'+resource)
	req.on('response', function(res) {
		//res.pipe( process.stdout );
		console.log("sendCoapRequest answer=" + res.payload);
		callback(request, response, res.payload);
	}) 
	req.end()
}
var handleCoapAnswer = function(request, response, coapData){
	//response.end("<html>"+"handleCoapAnswer=" + coapData + "</html>");	
	console.log("handleCoapAnswer " + ""+coapData );
	srvUtil.renderStaticFile(indexPath,response);
//	clientMqtt.publish(""+coapData);
 	setTimeout( function(){ io.sockets.send(""+coapData  ) }, 200 ) ;
	//io.sockets.send(""+coapData);
}

function sendCoapCoammnd(request, response, callback ){
	console.log("sendCoapCoammnd " );
	var requestOptions = {
			  host: 'localhost',
			  port: 5683,
			  pathname: '/Led',
			  method: 'PUT',
	};
 	req   = coap.request(requestOptions);
 	var payload = {
// 			  title: 'this is a test payload',
// 			  body: 'containing nothing useful'
 			value : 'true'
 	}

 	//req.write(JSON.stringify(payload));
 	
 	req.write("switch");		//From the Vocabulary of Led(Coap)Resource
 	
	req.on('response', function(res) {
 		srvUtil.renderStaticFile(indexPath,response);
		/*
		 * 
		 */
		//console.log("CoAp answer = " + res.payload) ;
 		setTimeout( function(){
			var now = new Date().toUTCString();
			io.sockets.send(""+res.payload + " time=" + now)
		}, 200 ) ;
 	}) 
	req.end()
}



 
/*
 * Another way to perform the request
 * A request to an HTTP server is a stream instance
 * Data is buffered in Readable streams when the implementation calls stream.push(chunk). 
 * If the consumer of the Stream does not call stream.read(), 
 * the data will sit in the internal queue until it is consumed.
 */
function sendCoapRequestOther(request, response, callback ){
	console.log("sendCoapRequest " );
 	 
 	request = coap.request('coap://localhost/Led');
	rs      = new Readable();
 	rs.push('MSG_' );
    rs.push(null);
	rs.pipe(request);	//attaches a Writable stream to the readable
 
	request.on('response', function(res) {
	   console.log('Coap request receives [%s] in port [%s] from [%s]', res.payload, res.outSocket.port, res.rsinfo.port);
	   callback(request, response, res.payload);
	});
}

/*
 * io.sockets test section
 */
function tick () {
	  var now = new Date().toUTCString();	  
	  io.sockets.send(now);
}
//setInterval(tick, 10000);


const initMsg=
	"\n"+
	"------------------------------------------------------\n"+
	"serverHttpToCoap bound to port 8080\n"+
	"uses socket.io\n"+
	"INITIALLY COONECTS TO  MQTT broker (mosuqitto) at tcp://localhost:1883\n"+
	"DYNAMICALLY CONNECTS (as client) TO  CoAP server at coap://localhost:5683\n"+
	"work as an HTTP-to-CoaP proxy\n"+
	"with reference to resource URI = /Led    (by MainCoapBasicLed / LedCoapResource)\n"+
	"with reference to resource URI = /Led    (by BlsHexagSystem / LedResource)\n"+
	"with reference to resource URI = /Button (by BlsHexagSystem / ButtonResource )\n"+
	"------------------------------------------------------\n";

/*
 * --------------------------------------------------------------
 * 1) 
 * --------------------------------------------------------------
 */

app.listen(8080, function(){console.log(initMsg)}); 
//createHttpServer( 8080, function() { console.log('serverHttpToCoap bound to port 8080'); } );


/*
/Led requires MainCoapBasicLed / LedCoapResource or BlsHexagSystem / LedResource
curl -X PUT -d "true" http://localhost:8080/ledSwitch
curl -X GET  http://localhost:8080/Led

/Button requires BlsHexagSystem / ButtonResource
curl -X GET  http://localhost:8080/Button  ()
curl -X PUT -d "pressed" http://localhost:8080/Button


 curl -X POST -d "{\"name\": \"Bob\",\"age\": \"35\",\"password\": \"qq\"}" http://localhost:8080/api/user
 curl -X POST -d "{ \"log\": \"xxx\" }" http://localhost:8080/api/log
 curl -X PUT -d "true" http://localhost:8080/ledSwitch
*/