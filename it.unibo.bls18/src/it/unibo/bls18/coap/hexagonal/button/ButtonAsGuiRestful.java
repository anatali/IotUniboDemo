package it.unibo.bls18.coap.hexagonal.button;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;

/*
 * The ButtonGUI is now an element of the Inbound gateways.
 * It works as a client of the ButtonThing resource
 */
public class ButtonAsGuiRestful implements ActionListener{
	
protected String cmd;
protected CoapClient buttonClient;

//Factory method
public static ButtonAsGuiRestful createButton( Frame frame, String cmd, String uri  ){
	ButtonAsGuiRestful button         = new ButtonAsGuiRestful(cmd, uri);
	java.awt.Button buttonBase        = new java.awt.Button(cmd);
	buttonBase.addActionListener(  button );
	frame.add(BorderLayout.WEST,buttonBase); 
	frame.validate();
	return button;
	
}
public static ButtonAsGuiRestful createButton( Frame frame, String cmd  ){
	return createButton( frame,   cmd, null);
}

/*
 * Constructor 
 */
	public ButtonAsGuiRestful(String cmd, String uri ) {
		this.cmd = cmd;
		if( uri != null ) buttonClient  = new  CoapClient( uri ); 
		else buttonClient  = new  CoapClient( CommonBlsHexagNames.ButtonUriStr );
		System.out.println("ButtonAsGuiRestful buttonClient done "   );
	}
 
@Override //from ActionListener
	public void actionPerformed(ActionEvent e) {
 		CoapResponse resp =  buttonClient.put("pressed", MediaTypeRegistry.TEXT_PLAIN);	
 		System.out.println("ButtonAsGuiRestful RESPONSE to put= " + resp.getResponseText() ); 	
 	}
	
	
	/*
	 * Just for a rapid test	
	 */		
public static void main(String[] args) {
	ButtonAsGuiRestful.createButton( UtilsBls.initFrame(200,200), "press");
}
	
}
