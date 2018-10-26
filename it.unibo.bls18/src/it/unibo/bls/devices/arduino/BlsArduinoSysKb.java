package it.unibo.bls.devices.arduino;

 
import it.unibo.is.interfaces.IObserver;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.supports.FactoryProtocol;

public class BlsArduinoSysKb {
	/*
	 * Messages on serial
	 */
	public final static String on  = "1";
	public final static String off = "0"; 

	/*
	 * Singleton cpnn
	 */
private static IConnInteraction conn = null;
	public static IConnInteraction getConnection(IOutputView outView,String PORT_NAME,IObserver observer) throws Exception{
		if( conn == null){
			FactoryProtocol factoryProtocol = new FactoryProtocol(outView,"SERIAL",PORT_NAME);
			conn = factoryProtocol.createSerialProtocolSupport(PORT_NAME, observer); 			
		}else{
 		}
		return conn;
	}
	public static IConnInteraction getConnection(IOutputView outView,String PORT_NAME) throws Exception{
		if( conn == null){
			FactoryProtocol factoryProtocol = new FactoryProtocol(outView,"SERIAL",PORT_NAME);
			conn = factoryProtocol.createSerialProtocolSupport(PORT_NAME); 			
		}
		return conn;
	}
}
