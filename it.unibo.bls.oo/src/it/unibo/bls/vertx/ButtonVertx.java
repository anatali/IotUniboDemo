package it.unibo.bls.vertx;

import javax.swing.JFrame;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import it.unibo.bls.utils.BlsFactory;

public class ButtonVertx extends AbstractVerticle {
	
	private final BlsVertxControl control;
	
	public ButtonVertx(final JFrame frame) {
		control = new BlsVertxControl();
		BlsFactory.createButton(frame, "PRESS", control); 
	}
	
	public void start() {		
		initEventBusConsumer();
		
		control.setVertx(vertx);
	}
	
	private void initEventBusConsumer() {
		vertx.eventBus().consumer(C.BUTTON_CONSUMER_ADDRESS, this::handleMessageReceived);
	}
	
	private void handleMessageReceived(Message<JsonObject> message) {
		Logger.log(getClass(), "I've received a message: " + message.body().encode());
		
		JsonObject event = message.body();
		
		switch(event.getString("id")) {
			case "button-pressed":
				JsonObject ev = new JsonObject()
					.put("id", "led-switch");
				
				vertx.eventBus().send(C.LED_CONSUMER_ADDRESS, ev);
				break;
				
			case "new-led-state":
				break;
			
			default:
				Logger.log(getClass(), "Error. Unknown event id!");
				break;
		}
		
	}
}
