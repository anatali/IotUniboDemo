 //Initialization
var ledMod = require("./Led");
var l1     = new ledMod.Led('l1', null);

exports.handler = (event, context, callback) => {
    console.log('Received event:', JSON.stringify(event, null, 2));
    console.log('edge =', event.edge);
     if ('edge' in event) {
        l1.switchState();
        console.log( l1.getDefaultRep() ); //Logging to Amazon CloudWatch Log
    } 
    callback(null, l1.getDefaultRep());  // End and return
};