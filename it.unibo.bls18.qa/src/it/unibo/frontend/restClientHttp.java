/*
 * it.unibo.frontend/src/it/unibo/frontend/RestClientHttp.java
 */ 
package it.unibo.frontend;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import it.unibo.qactors.akka.QActor;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

public class restClientHttp {
//private static String hostAddr = "localhost";
//private static String hostAddr = "192.168.43.18";
private static String hostAddr = "localhost";

	public static int sendPutLed(QActor qa, String data, String url) {
		System.out.println("sendPutLed   " + data + " url="+url);
		return sendPut( qa, "{\"value\": \"VVV\" }".replace("VVV", data), url );
	}


	public static int sendPut(QActor qa, String data, String url) {
	    int responseCode = -1;
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    try {
	        System.out.println("sendPut   " + data + " url="+url);
	        HttpPut request = new HttpPut(url);
	        StringEntity params =new StringEntity(data,"UTF-8");
	        params.setContentType("application/json");
	        request.addHeader("content-type", "application/json");
	        request.addHeader("Accept", "*/*");
	        request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
	        request.addHeader("Accept-Language", "en-US,en;q=0.8");
	        request.setEntity(params);
	        CloseableHttpResponse response = httpclient.execute(request);
	        responseCode = response.getStatusLine().getStatusCode();
	        System.out.println("sendPut DONE " );
	        if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204) {
	            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
	            String output;
 	    		String info = "";
	    		while ((output = br.readLine()) != null) {
	    			info = info + output;
	    		}
	    		System.out.println("sendPut ANSWER="+ info + " responseCode=" + responseCode);
	        }
	        else{  throw new RuntimeException("Failed : HTTP error code : "
	                    + response.getStatusLine().getStatusCode());
	        }

	    }catch (Exception ex) {
 	    } finally {// 	    	httpclient.close();
	    }
	    return responseCode;
	}
	
	public static void connectPost(QActor qa ){
 		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://"+hostAddr+":3000");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);
		    HttpEntity entity2 = response2.getEntity();
		    // do something useful with the response body and ensure it is fully consumed
		    EntityUtils.consume(entity2);
	 	  } catch ( Exception e) {
	 			e.printStackTrace();	 			
	 	}
 	}
	public static void connectGet(QActor qa, String url){
	 try {
 		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);		
		CloseableHttpResponse response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(
                         new InputStreamReader((response.getEntity().getContent())));
		String output;
		String info = "";
		while ((output = br.readLine()) != null) {
			info = info + output;
		}
 		System.out.println(info);
 	  } catch ( Exception e) { e.printStackTrace(); }
	}
	public static void work() throws InterruptedException {
		for( int i=1; i<=3; i++) {
			//curl http://192.168.43.229:3000/pi/actuators/leds/1;
	  		connectGet(null,"http://"+hostAddr+":3000/pi/actuators/leds/1");	
			sendPut(null,"{\"value\": \"true\" }", "http://"+hostAddr+":3000/pi/actuators/leds/1");
			Thread.sleep(700);
 			sendPut(null,"{\"value\": \"false\" }", "http://"+hostAddr+":3000/pi/actuators/leds/1");
			//curl -H "Content-Type: application/json" -X PUT -d "{\"value\": \"false\" }" http://localhost:3000/pi/actuators/leds/1;
	  		connectGet(null,"http://"+hostAddr+":3000/pi/actuators/leds/1");		
			Thread.sleep(700);
		}
	}
	public static void main (String args[]) throws Exception{
		System.out.println("=====================================================================================");
		System.out.println("1) Activate a MQTT server on  hostAddr:1883");
		System.out.println("2) Run node frontendServer.js in it.unibo.frontend/nodeCode/frontend");
		System.out.println("3) Activate it.unibo.bls17.ledMockGui.qa/src-gen/it/unibo/ctxLedMockGui/MainCtxLedMockGui.java");
		System.out.println("4) Activate it.unibo.bls17.ledrasp.qa/src-gen/it/unibo/ctxLedOnRasp/MainCtxLedOnRasp.java");
		System.out.println("=====================================================================================");
		work();
	}
}