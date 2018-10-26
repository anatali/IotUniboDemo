/*
* =====================================
* asyncTest.js
* =====================================
*/
var async = require('async')

function callbackhandler(err, results) {
    console.log('It came back with this ' + results);
}   

function takes3seconds(callback) {
    console.log('Starting 3 second task');
    setTimeout( function() { 
        console.log('Just finshed 3 seconds');
        callback(null, 'three');
    }, 3000);
}   

function takes2Seconds(callback) {
    console.log('Starting 2 second task');
    setTimeout( function() { 
        console.log('Just finshed 2 seconds');
        callback(null, 'two');
    }, 2000); 
}   

 

/*
 * The callback passed to each of the functions to be executed is internal to the async library. 
 * You execute it once your function has completed, passing an error and/or a value. 
 * You don't need to define that function yourself.
 * 
 * The final callback is 
		function (err, result) {
            if (arguments.length > 2) {
                result = slice(arguments, 1);
            }
            results[key] = result;
            callback(err);
        }
 */
 
async.series([
    takes2Seconds,
    takes3seconds
], function (err, results) {
    // Here, results is an array of the value from each function
    console.log(results); // outputs: ['two', 'three']
});
 