/*
C:\aLabDemoIotGit\IotUniboDemo\it.unibo.bls.aws\awsSdk\testAwsCall.js
*/
var AWS = require('aws-sdk');

// you shouldn't hardcode your keys in production! 
// See http://docs.aws.amazon.com/AWSJavaScriptSDK/guide/node-configuring.html
AWS.config.update({
    accessKeyId: 'AKIAIHN4JZGSA6YBCG4A', 
	secretAccessKey: 'NOyHsXBMaDoVw0TvLH8vv/CE65uKe5HE3p+yT8eW',
	region: 'eu-west-1'
	});

var lambda = new AWS.Lambda();
var params = {
  FunctionName: 'ClosureExample', /* required */
  Payload: "{}"
};
lambda.invoke(params, function(err, data) {
  if (err) console.log(err, err.stack); // an error occurred
  else     console.log(data);           // successful response
});
