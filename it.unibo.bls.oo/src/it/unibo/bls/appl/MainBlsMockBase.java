package it.unibo.bls.appl;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.ButtonMock;
import it.unibo.bls.devices.LedMock;
import it.unibo.bls.interfaces.IButton;
import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.utils.Utils;
 
public class MainBlsMockBase  {
private IButton btn;
private ILed led;
//Factory method   	
  	public static MainBlsMockBase createTheSystem(){
 		return new MainBlsMockBase();
 	} 	
 	protected MainBlsMockBase( ) {
 		createComponents();
 	}		
 	protected void createComponents(){
  		led = LedMock.createLed(   );
   		BlsApplicationLogic applLogic = new BlsApplicationLogic(led);
 		btn = ButtonMock.createButton(  applLogic );
 		led.turnOff();
	} 
 	public void doSomeJob() {
 		System.out.println("doSomeJob starts"   );
 		((ButtonMock)btn).press();
 		Utils.delay(1000);
 		((ButtonMock)btn).press();
 		System.out.println("doSomeJob ends"   );
 	}
 	public IButton getButton(){	//introduced for testing
		return btn;
	}
	public ILed getLed(){ //introduced for testing
		return led;
	} 	
public static void main(String[] args) {
   MainBlsMockBase sys = createTheSystem();
   sys.doSomeJob();
 }
}