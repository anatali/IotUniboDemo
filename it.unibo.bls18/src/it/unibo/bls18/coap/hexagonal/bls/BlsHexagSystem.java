package it.unibo.bls18.coap.hexagonal.bls;

import org.eclipse.californium.core.CoapServer;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import it.unibo.bls18.coap.hexagonal.button.ButtonAsGuiRestful;
import it.unibo.bls18.coap.hexagonal.button.ButtonResource;
import it.unibo.bls18.coap.hexagonal.led.LedAsGuiPluginObserver;
import it.unibo.bls18.coap.hexagonal.led.LedResource;


public class BlsHexagSystem {
	private CoapServer server;
	private ButtonResource buttonResource;
	
	public BlsHexagSystem() {
		createServer();
		configure();
	}
	
	protected  void createServer( ) {	//port=5683 default
		server   = new CoapServer( CommonBlsHexagNames.port );
		server.start();
		System.out.println("BlsHexagSystem Server started");
	}
	
	protected void configure( ) {
 		createInputDevice();
		createResourceModel(  ) ;
 		addApplLogic(  );
   	}
	
	protected void createResourceModel(  ) {
		LedResource ledResource = 
				new LedResource(new LedAsGuiPluginObserver(UtilsBls.initFrame(200,200)));  //add a model viewer
	  	System.out.println("BlsHexagSystem CREATE ledResource ");
		buttonResource = new ButtonResource();
		System.out.println("BlsHexagSystem CREATE buttonResource ");
		//Two resources in the same server
		server.add( ledResource );
 		server.add( buttonResource );
		System.out.println("BlsHexagSystem createResourceModel DONE ");
	}

	protected void addApplLogic(  ) {
 		buttonResource.setObserver( new BlsHexagApplLogicObserver() );
 	}
	protected void addLogButton(  ) {
 		buttonResource.setObserver( new ButtonLogObserver() );
 	}

	protected void createInputDevice() {
		ButtonAsGuiRestful.createButton( UtilsBls.initFrame(200,200), "press", CommonBlsHexagNames.BlsButtonUriStr );
	}
/*
 * 	
 */
	public static void main(String[] args) throws Exception {
		new BlsHexagSystem();
	}

}
