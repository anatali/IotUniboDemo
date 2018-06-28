package it.unibo.bls.vertx;

import javax.swing.JFrame;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.utils.BlsFactory;

public class LedVertx extends AbstractVerticle {

	private final ILed led;
	
	public LedVertx(final JFrame frame) {
		led = BlsFactory.createLed(frame);
		led.turnOff();
	}
	
	public void start() {
		initEventBusConsumer();		
	}
	
	private void initEventBusConsumer() {
		vertx.eventBus().consumer(C.LED_CONSUMER_ADDRESS, this::handleMessageReceived);
	}
	
	private void handleMessageReceived(Message<JsonObject> message) {
		Logger.log(getClass(), "I've received a message: " + message.body().encode());
		
		final JsonObject event = message.body();
		
		switch(event.getString("id")) {
			case "led-switch":
				if(led.getState()) {
					led.turnOff();
				} else {
					led.turnOn();
				}
				break;
				
			case "led-turnOn":
				led.turnOn();
				break;
				
			case "led-turnOff":
				led.turnOff();
				break;
				
			case "led-getState":
				JsonObject ev = new JsonObject()
					.put("event", "new-led-state")
					.put("content", new JsonObject().put("state", led.getState()));
			
				vertx.eventBus().send(C.LED_CONSUMER_ADDRESS, ev);
				break;
				
			default:
				Logger.log(getClass(), "Error. Unknown event id!");
				break;
		}
	}
}
