%====================================================================================
% Context ctxBlsQactor  SYSTEM-configuration: file it.unibo.ctxBlsQactor.blsQactor.pl 
%====================================================================================
context(ctxblsqactor, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( buttonqa , ctxblsqactor, "it.unibo.buttonqa.MsgHandle_Buttonqa"   ). %%store msgs 
qactor( buttonqa_ctrl , ctxblsqactor, "it.unibo.buttonqa.Buttonqa"   ). %%control-driven 
qactor( controlqa , ctxblsqactor, "it.unibo.controlqa.MsgHandle_Controlqa"   ). %%store msgs 
qactor( controlqa_ctrl , ctxblsqactor, "it.unibo.controlqa.Controlqa"   ). %%control-driven 
qactor( ledqa , ctxblsqactor, "it.unibo.ledqa.MsgHandle_Ledqa"   ). %%store msgs 
qactor( ledqa_ctrl , ctxblsqactor, "it.unibo.ledqa.Ledqa"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

