package it.unibo.buttonqagui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import it.unibo.bls.devices.ButtonAsGuiBasic;
import it.unibo.bls.devices.LedAsGui;
import it.unibo.bls.interfaces.IButton;
import it.unibo.bls.interfaces.ILed;
import it.unibo.qactors.akka.QActor;

public class blsGuiFactory {
private static LedAsGui led;
	/*
	 * BLS components low Level	
	 */
	public static void createButton(QActor qa, String label ) {
		Frame frame =  initFrame(200,100);
		ActionListener listener = new Listener(qa);
		new ButtonAsGuiBasic(frame, label, listener);	
 	}
	public static void createLed(QActor qa  ){
		Frame frame =  initFrame(400,200);
		led = new LedAsGui(frame);
 		led.turnOff();
 	}

	public static void turnOn(QActor qa ) {
		led.turnOn();
	}
	public static void turnOff(QActor qa ) {
		led.turnOff();
	}
	private static Frame initFrame(int dx, int dy){
 		Frame frame         = new Frame();
 		BorderLayout layout = new BorderLayout();
 		frame.setSize( new Dimension(dx,dy) );
 		frame.setLayout(layout);		
 		frame.addWindowListener(new WindowListener() {			
			@Override
			public void windowOpened(WindowEvent e) {}				
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);				
			}			
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		}); 	
		frame.setVisible(true);
		return frame;
 	}
	 	
}

class Listener implements ActionListener{
private QActor qa;
	public Listener(QActor qa) {
		this.qa = qa;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		 qa.emit("usercmd", "pressed");		
	}	
}


