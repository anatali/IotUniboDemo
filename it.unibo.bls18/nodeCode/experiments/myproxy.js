/*
* =====================================
* myproxy.js
* =====================================
*/

const
	http    = require("http"),
	parse   = require('url').parse,
    coap  = require('coap'),
    async = require('async'),				//npm install asynch --save
    Readable = require('stream').Readable,
    requestNumber = 1;

var targetServer,
    proxy,
    client1,
    client2,
    targetResults;

//-------------------------------------------------------------------------------
function createHttpServer(callback){
	//configure the system;
		console.log('myproxy creates a server and register handleRequest');
		var server = http.createServer();
		server.on( 'request' , handleHttpRequest); 
	//start;
		console.log('myproxy running on 3000/');
		//server.listen(3000, function() { console.log('bound to port 3000'); });	
		server.listen(3000, callback);
};
var handleHttpRequest = function (request, response) { 
	var url  = parse(request.url);
	console.log("myproxy request.method=" + request.method + " url.pathname=" + url.pathname); 
	mySendCoapRequest(request, response,handleCoapAnswer);
//	response.end("hello from handleHttpRequest");	
} 

var handleCoapAnswer = function(request, response, coapData){
	response.end("<html>"+"new hello from handleCoapAnswer " + coapData + "</html>");	
}
//-------------------------------------------------------------------------------
function formatTitle(msg) {
    return '\n\n' + msg + '\n-------------------------------------';
}

function requestHandler(req, res) {
    console.log('Target receives [%s] in port [8976] from port [%s]', req.payload, req.rsinfo.port);
    res.end('RES_' + req.payload);
}

/*
 * The final callback is of the form callback(err, results...)
 */
function createTargetServer(callback) {
    console.log('Creating target server at port 8976 ' ); //+ callback
    targetServer = coap.createServer(requestHandler);
    targetServer.listen(8976, '0.0.0.0', callback);  
}


function proxyHandler(req, res) {
    console.log('Proxy handled [%s]', req.payload);
    res.end('RES_' + req.payload);
}

function createProxy(callback) {
    console.log('Creating proxy at port 6780 ' ); // + callback
    proxy = coap.createServer({ proxy: true }, proxyHandler);
    proxy.listen(6780, '0.0.0.0', callback);
}

function mySendCoapRequest(request, response, callback ){
	console.log("mySendRequest " );
	//coap://localhost:5683/Led
//    var req = {
//            host: 'localhost',
//            port: 5683,
//            agent: false
//        },
//    rs = new Readable();
//    request = coap.request(req);
//
//    request.on('response', function(res) {
//        console.log('Coap request receives [%s] in port [%s] from [%s]', res.payload, res.outSocket.port, res.rsinfo.port);
//        callback(request, response, res.payload);
//    });
//
//    rs.push('MSG_' );
//    rs.push(null);
//    rs.pipe(request);
	request = coap.request('coap://localhost/Led');
	rs = new Readable();
	request.on('response', function(res) {
	   console.log('Coap request receives [%s] in port [%s] from [%s]', res.payload, res.outSocket.port, res.rsinfo.port);
	   callback(request, response, res.payload);
	  });
  rs.push('MSG_' );
  rs.push(null);
  rs.pipe(request);
}

function sendRequest(proxied) {
    return function(n, callback) {   	
        var req = {
                host: 'localhost',
                port: 8976,
                agent: false
            },
            rs = new Readable();
        if (proxied) {
            req.port = 6780;
            req.proxyUri = 'coap://localhost:8976'; //'http://localhost:3000'; //
        }
        request = coap.request(req);
        request.on('response', function(res) {
            console.log('Client receives [%s] in port [%s] from [%s]', res.payload, res.outSocket.port, res.rsinfo.port);
            callback();
        });

        rs.push('MSG_' + n);
        rs.push(null);
        rs.pipe(request);
    }
}

function executeTest(proxied) {
    return function(callback) {
        if (proxied) {
            console.log(formatTitle('Executing tests with proxy'));
        } else {
            console.log(formatTitle('Executing tests without proxy'));
        }

        async.times(requestNumber, sendRequest(proxied), callback);
    }
}

function cleanUp(callback) {
    targetServer.close(function () {
        proxy.close(callback);
    });
}

function checkResults(callback) {
    console.log(formatTitle('Finish'));
 }

/*
 * The callback passed to each of the functions to be executed is internal to the async library. 
 */

 
async.series([
	createHttpServer,
    createTargetServer,
    //createProxy,
    //executeTest(false),
    //executeTest(true)
    //cleanUp
], //checkResults);
  function (err, results) {
    // Here, results is an array of the value from each function
    console.log(results);  
});
 
//createTargetServer( function(res){ console.log( "createTargetServer:"+res) })

//createHttpServer();
/*
https://www.npmjs.com/package/ponte
PUBLIC SERVERS
 coap://californium.eclipse.org/ or coap://vs0.inf.ethz.ch/, coap://coap.me/.
*/