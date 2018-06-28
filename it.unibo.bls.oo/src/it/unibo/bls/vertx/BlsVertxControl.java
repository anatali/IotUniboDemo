package it.unibo.bls.vertx;

import java.awt.event.ActionEvent;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import it.unibo.bls.devices.ButtonListenerNaive;

public class BlsVertxControl extends ButtonListenerNaive {
	
	private Vertx vertx;
	
	public void setVertx(final Vertx vertx) {
		this.vertx = vertx;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JsonObject event = new JsonObject()
				.put("id", "button-pressed");
		
		if(vertx != null) {
			vertx.eventBus().send(C.BUTTON_CONSUMER_ADDRESS, event);
		}
 	}
}
