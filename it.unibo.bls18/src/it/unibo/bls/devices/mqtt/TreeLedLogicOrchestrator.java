package it.unibo.bls.devices.mqtt;

import java.util.Observable;

import org.eclipse.paho.client.mqttv3.MqttClient;

import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.mqtt.utils.MqttUtils;
import it.unibo.contactEvent.interfaces.IActorMessage;
import it.unibo.qactors.QActorMessage;

public class TreeLedLogicOrchestrator implements IObserver{
	protected String severAddr      = CommonLedNames.serverAddr;
	protected MqttClient clientmqtt = null;
	protected MqttUtils mqttutils   = MqttUtils.getMqttSupport(   );
	protected int count             = 0;
	
	protected IObservable leds[];
	
	public static TreeLedLogicOrchestrator create( IObservable[] leds ) {
		return new TreeLedLogicOrchestrator( leds );
	}
	
	public TreeLedLogicOrchestrator( IObservable[] leds ) {
		clientmqtt = mqttutils.connect("treeLedOrch", severAddr);  
		
	}
  
	@Override
	public void update(Observable source, Object val) {
		System.out.println("TreeLedLogicOrchestrator update:"  + val);
		IActorMessage msg;
		try {
			/*
			 * emit a ledCmd event 
			 */
			if( val.equals(CommonLedNames.startCmd) )
				doJob();
			else
				trunOffAll();
 		} catch (Exception e) {
 			e.printStackTrace();
		}
		
	}
	
	protected void trunOffAll() {
 		try {
 			int n = CommonLedNames.nunOfTreeRows;
			IActorMessage turnOffMsg = new QActorMessage("ledCmd","event","treeLedOrch","ledThing","false",""+count++);
 			for( int i=0; i<n; i++ ) {  //for each row of the tree
 				mqttutils.publish(clientmqtt, CommonLedNames.ledRowTopcs[i],   turnOffMsg.toString() );
 			}			 		
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	protected void doJob() {
		while(true) {
	 		try {
	 			int n = CommonLedNames.nunOfTreeRows;
				IActorMessage turnOnMsg  = new QActorMessage("ledCmd","event","treeLedOrch","ledThing","true",""+count++);
				IActorMessage turnOffMsg = new QActorMessage("ledCmd","event","treeLedOrch","ledThing","false",""+count++);
	 			for( int i=0; i<n; i++ ) {  //for each row of the tree
	 				UtilsBls.delay(1000);
					int prec = i>0?i-1:n-1;
					mqttutils.publish(clientmqtt, CommonLedNames.ledRowTopcs[prec], turnOffMsg.toString() );
					mqttutils.publish(clientmqtt, CommonLedNames.ledRowTopcs[i],   turnOnMsg.toString() );
	  			}
			} catch (Exception e) {
	 			e.printStackTrace();
			}
		}
	}
}
