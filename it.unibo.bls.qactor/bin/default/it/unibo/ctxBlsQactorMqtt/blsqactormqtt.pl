%====================================================================================
% Context ctxBlsQactorMqtt  SYSTEM-configuration: file it.unibo.ctxBlsQactorMqtt.blsQactorMqtt.pl 
%====================================================================================
context(ctxblsqactormqtt, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( buttonqamqtt , ctxblsqactormqtt, "it.unibo.buttonqamqtt.MsgHandle_Buttonqamqtt"   ). %%store msgs 
qactor( buttonqamqtt_ctrl , ctxblsqactormqtt, "it.unibo.buttonqamqtt.Buttonqamqtt"   ). %%control-driven 
qactor( controlqamqtt , ctxblsqactormqtt, "it.unibo.controlqamqtt.MsgHandle_Controlqamqtt"   ). %%store msgs 
qactor( controlqamqtt_ctrl , ctxblsqactormqtt, "it.unibo.controlqamqtt.Controlqamqtt"   ). %%control-driven 
qactor( ledqamqtt , ctxblsqactormqtt, "it.unibo.ledqamqtt.MsgHandle_Ledqamqtt"   ). %%store msgs 
qactor( ledqamqtt_ctrl , ctxblsqactormqtt, "it.unibo.ledqamqtt.Ledqamqtt"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

