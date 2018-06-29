/* Generated by AN DISI Unibo */ 
package it.unibo.ctxBlsQactor;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxBlsQactor  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxblsqactor", "./srcMore/it/unibo/ctxBlsQactor/blsqactor.pl", 
		"./srcMore/it/unibo/ctxBlsQactor/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}
