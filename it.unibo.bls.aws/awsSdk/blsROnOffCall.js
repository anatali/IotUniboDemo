/*
C:\aLabDemoIotGit\IotUniboDemo\it.unibo.bls.aws\awsSdk\blsROnOffCall.js
*/
var AWS = require('aws-sdk');

// you shouldn't hardcode your keys in production! 
// See http://docs.aws.amazon.com/AWSJavaScriptSDK/guide/node-configuring.html
AWS.config.update({
    accessKeyId: 'AKIAJLMGIFEHHTNGTPKA', 
	secretAccessKey: 'SwMmjYDPQZ0ANNY31CtCG1zVZOephJXICTSq9dpD',
	region: 'eu-west-1'
	});

var lambda = new AWS.Lambda();
var params = {
  FunctionName: 'blsROnOff', /* required */
  Payload: "{}"
};
lambda.invoke(params, function(err, data) {
  if (err) console.log(err, err.stack); // an error occurred
  else     console.log(data);           // successful response
});
