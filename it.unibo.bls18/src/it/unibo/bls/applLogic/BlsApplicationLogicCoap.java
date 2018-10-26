package it.unibo.bls.applLogic;

import java.util.Observable;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.interfaces.IObserver;

public class BlsApplicationLogicCoap implements IObserver{
	private int count = 0;
	private CoapClient coapClient;
	private String ledState = "";
	
	public BlsApplicationLogicCoap(String hostName, int port, String resourceName){
		createClient(hostName, port, resourceName);
		ledState = synchGet();
	}
	public void createClient(String hostName, int port, String resourceName) {
		System.out.println( "BlsApplicationLogicCoap createClient " + hostName + ":" + port + "/" + resourceName);
		coapClient =  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
		System.out.println("BlsApplicationLogicCoap Client started");
 	}

	protected String synchGet() {	
 		CoapResponse coapResp = coapClient.get();
 //		System.out.println(Utils.prettyPrint(coapResp));
 		if( coapResp != null ) {
			System.out.println("%%% BlsApplicationLogicCoap ANSWER get " + coapResp.getResponseText());
			return coapResp.getResponseText();
 		}else return "NO ANSWER";
		
	}

//	protected void asynchGet() {
// 		coapClient.get( asynchListener );
//	}
 	
	protected void put(String v) {
		System.out.println("%%% BlsApplicationLogicCoap put " );
		ledState = v;
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
 		//System.out.println("%%% BlsApplicationLogicCoap ANSWER put " );
		//System.out.println(Utils.prettyPrint(coapResp));
	}
	@Override
	public void update(Observable source, Object val) {
		System.out.println("%%%  BlsApplicationLogicCoap update:"  + val);
 		switchTheLed();
	}
	protected void switchTheLed( ){
		count++;
		if( coapClient == null ) return;
		//if( ledState.equals("true")   ) put("false"); else put("true");	//NO MORE
		put("switch");
 		System.out.println("%%% BlsApplicationLogicCoap ledstate=" + ledState + " count=" + count);
 		//checkConsistence();	//just for testing
	}
	
	protected void checkConsistence() {
		String curLedVal = synchGet();
		if( ! curLedVal.equals(ledState) ) 
			System.out.println("%%% BlsApplicationLogicCoap ERROR: NOT CONSISTENTE" );
	}
	public int getNumOfActivations() {
		return count;
	}
}
