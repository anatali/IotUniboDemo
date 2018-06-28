package it.unibo.bls.vertx;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import io.vertx.core.Vertx;

public class MainVertxSystem {

	private JFrame frame;
	
	public MainVertxSystem() {
		
		Logger.activate();
		
		initFrame();
		initTheSystem();
	}

	private void initFrame() {
		frame = new JFrame();
		frame.setSize(new Dimension(800, 300));
		frame.setLayout(new BorderLayout());
		frame.setTitle("BLS Vertx-based System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void initTheSystem() {
		final Vertx vertx = Vertx.vertx();
		
		final ButtonVertx button = new ButtonVertx(frame);
		final LedVertx led = new LedVertx(frame);
		
		vertx.deployVerticle(button, res -> {
			Logger.log(getClass(), res.succeeded() 
					? "Button Verticle successfully deployed." 
					: "Error during Button Verticle deployment.");
		});
		
		vertx.deployVerticle(led, res -> {
			Logger.log(getClass(), res.succeeded() 
					? "Led Verticle successfully deployed." 
					: "Error during Led Verticle deployment.");
		});
	}

	public static void main(String[] args) {
		new MainVertxSystem();
	}
}
