/* Generated by AN DISI Unibo */ 
package it.unibo.ctxBlsQactorMqtt;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxBlsQactorMqtt  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxblsqactormqtt", "./srcMore/it/unibo/ctxBlsQactorMqtt/blsqactormqtt.pl", 
		"./srcMore/it/unibo/ctxBlsQactorMqtt/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}
