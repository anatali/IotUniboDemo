/* Generated by AN DISI Unibo */ 
package it.unibo.controlqagui;
import it.unibo.qactors.PlanRepeat;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.StateExecMessage;
import it.unibo.qactors.QActorUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.StateFun;
import java.util.Stack;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.action.ActorTimedAction;
public abstract class AbstractControlqagui extends QActor { 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	protected String planFilePath    = null;
	protected String terminationEvId = "default";
	protected String parg="";
	protected boolean bres=false;
	protected IActorAction action;
	 
	
		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}
		public AbstractControlqagui(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/controlqagui/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/controlqagui/plans.txt";
	  	}
		@Override
		protected void doJob() throws Exception {
			String name  = getName().replace("_ctrl", "");
			mysupport = (IMsgQueue) QActorUtils.getQActor( name ); 
			initStateTable(); 
	 		initSensorSystem();
	 		history.push(stateTab.get( "init" ));
	  	 	autoSendStateExecMsg();
	  		//QActorContext.terminateQActorSystem(this);//todo
		} 	
		/* 
		* ------------------------------------------------------------
		* PLANS
		* ------------------------------------------------------------
		*/    
	    //genAkkaMshHandleStructure
	    protected void initStateTable(){  	
	    	stateTab.put("handleToutBuiltIn",handleToutBuiltIn);
	    	stateTab.put("init",init);
	    	stateTab.put("pressedOdd",pressedOdd);
	    	stateTab.put("pressedEven",pressedEven);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "controlqagui tout : stops");  
	    		repeatPlanNoTransition(pr,myselfName,"application_"+myselfName,false,false);
	    	}catch(Exception e_handleToutBuiltIn){  
	    		println( getName() + " plan=handleToutBuiltIn WARNING:" + e_handleToutBuiltIn.getMessage() );
	    		QActorContext.terminateQActorSystem(this); 
	    	}
	    };//handleToutBuiltIn
	    
	    StateFun init = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("init",-1);
	    	String myselfName = "init";  
	    	temporaryStr = "\"controlqa STARTS\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"controlqagui_"+myselfName,false,
	          new StateFun[]{stateTab.get("pressedOdd") }, 
	          new String[]{"true","E","usercmd" },
	          600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun pressedOdd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("pressedOdd",-1);
	    	String myselfName = "pressedOdd";  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"turn(X)","turn(on)", guardVars ).toString();
	    	sendMsg("turn","ledqagui", QActorContext.dispatch, temporaryStr ); 
	    	//bbb
	     msgTransition( pr,myselfName,"controlqagui_"+myselfName,false,
	          new StateFun[]{stateTab.get("pressedEven") }, 
	          new String[]{"true","E","usercmd" },
	          600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_pressedOdd){  
	    	 println( getName() + " plan=pressedOdd WARNING:" + e_pressedOdd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//pressedOdd
	    
	    StateFun pressedEven = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("pressedEven",-1);
	    	String myselfName = "pressedEven";  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"turn(X)","turn(off)", guardVars ).toString();
	    	sendMsg("turn","ledqagui", QActorContext.dispatch, temporaryStr ); 
	    	//switchTo init
	        switchToPlanAsNextState(pr, myselfName, "controlqagui_"+myselfName, 
	              "init",false, false, null); 
	    }catch(Exception e_pressedEven){  
	    	 println( getName() + " plan=pressedEven WARNING:" + e_pressedEven.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//pressedEven
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
