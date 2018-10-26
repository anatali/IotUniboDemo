package it.unibo.bls18.coap.hexagonal.bls;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import it.unibo.bls18.coap.hexagonal.ResourceLocalGofObserver;
 

public class BlsHexagApplLogicObserver extends ResourceLocalGofObserver  {
protected CoapClient ledClient;
protected boolean ledState = false;

 	public BlsHexagApplLogicObserver( ) {
 		ledClient = new  CoapClient( CommonBlsHexagNames.BlsLedUriStr );	//
 		getCurrentLedState();
   		showMsg("BlsHexagApplLogicObserver CREATED ledState=" + ledState);
 	}
 	
 	protected void getCurrentLedState() {  //just to be sure ...
 		String ledValStr = ledClient.get().getResponseText();
 		ledState = ledValStr.equals("true");		
 	}
 	/*
 	 * Called by  
  	 */
 	@Override
	public void update(String v) {
		System.out.println("BlsHexagApplLogicObserver updated with: " + v);
		//Switch
		if( ledState ) ledClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
		else ledClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
		getCurrentLedState();
  	}
 }
