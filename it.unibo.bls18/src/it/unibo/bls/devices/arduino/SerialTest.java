package it.unibo.bls.devices.arduino;

import java.util.Observable;
import it.unibo.is.interfaces.IObserver;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.supports.FactoryProtocol;
import it.unibo.system.SituatedPlainObject;
import it.unibo.system.SituatedSysKb;
import jssc.SerialPort;

/*
 * SerialTest is a SituatedPlainObject that opens a IConnInteraction on the serial port connected with Arduino
 * It first waits for a message from Arduino ; afterwards. it
 * sends a command to Arduino and then waits for an answer
 */
public class SerialTest extends SituatedPlainObject implements  IObserver{ //SerialPortEventListener {
	
    /** The port we're normally going to use. */
	protected static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyUSB0", // Linux
			"/dev/ttyO2", "/dev/ttyAMA0", "/dev/ttyACM0", "/dev/ttyACM1", "COM3", //SAM
			"COM4","COM39","COM9","COM8", // Windows
	};
	private static final String PORT_NAME="COM9";
	
	protected SerialPort serialPort;
	protected FactoryProtocol factoryProtocol;
	protected IConnInteraction conn; //the comm channel with Arduino is a "general" two-way IConnInteraction
	
	public SerialTest(IOutputEnvView outEnvView ){
		super("serialTest",outEnvView);
		myconfigure();
		doJob();
	}
	 
	protected void myconfigure() {
		try {
	 		println("SerialTest waiting for connection ... " + PORT_NAME );
	 	 	factoryProtocol = new FactoryProtocol(outEnvView, "SERIAL", "serialTest");
//			conn = factoryProtocol.createSerialProtocolSupport(PORT_NAME,this); 
 			conn = factoryProtocol.createSerialProtocolSupport( PORT_NAME ); 
	  	} catch (Exception e) { e.printStackTrace(); }
	 }
		
	protected void doJob(){
		try {
			String input = conn.receiveALine();
			println("SerialTest first input = " +  input   ); 
 			for( int i=1; i<=3; i++){
  				conn.sendALine("pc:"+i);
				input = conn.receiveALine();
				input = input.replace("\n", "");
				println("SerialTest input = " +  input   ); 
 			}
			println("SerialTest ENDS "    ); 
			conn.closeConnection();
		} catch (Exception e) {
 				e.printStackTrace();
		}
	}
 
		/*
		 * Called when
		 * conn = factoryProtocol.createSerialProtocolSupport(PORT_NAME,this); 
		 */
	@Override
	public void update(Observable o, Object receivedData) {
		String data = (String) receivedData;
		println("SerialTest update data = " +  data );
 	}
/*
 * 		
 */
	public static void main(String[] args) throws Exception {
		new SerialTest(SituatedSysKb.standardOutEnvView);
	}
}
 