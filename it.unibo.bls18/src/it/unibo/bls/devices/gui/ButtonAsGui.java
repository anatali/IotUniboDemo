package it.unibo.bls.devices.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.utils.UtilsBls;

public class ButtonAsGui extends Observable implements IObservable, ActionListener{
	
protected String cmd;

//Factory method
public static IObservable createButton( Frame frame, String cmd, IObserver obs ){
	ButtonAsGui button         = new ButtonAsGui(cmd);
	java.awt.Button buttonBase = new java.awt.Button(cmd);
	buttonBase.addActionListener(  button );
	frame.add(BorderLayout.WEST,buttonBase); 
	frame.validate();
  	if( obs != null ) button.addObserver(obs);
	return button;
}

public static IObservable createButton( Frame frame, String cmd  ){
	return createButton(frame,cmd,null);
}

	public ButtonAsGui(String cmd ) {
		this.cmd = cmd;
	}

	@Override //from IObservable
	public void addObserver(IObserver observer) {
		 super.addObserver(observer);
	}
 	@Override //from ActionListener
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		this.notifyObservers( this.cmd );
	}
	
	
	/*
	 * Just for a rapid test	
	 */		
		public static void main(String[] args) {
			BlsApplicationLogic appl = new BlsApplicationLogic(null);
			ButtonAsGui.createButton( UtilsBls.initFrame(200,200), "press", appl);
		}
	
}
