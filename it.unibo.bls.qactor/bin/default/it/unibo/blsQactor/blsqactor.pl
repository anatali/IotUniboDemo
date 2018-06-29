%====================================================================================
% Context blsQactor  SYSTEM-configuration: file it.unibo.blsQactor.blsQactor.pl 
%====================================================================================
context(blsqactor, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( buttonqa , blsqactor, "it.unibo.buttonqa.MsgHandle_Buttonqa"   ). %%store msgs 
qactor( buttonqa_ctrl , blsqactor, "it.unibo.buttonqa.Buttonqa"   ). %%control-driven 
qactor( controlqa , blsqactor, "it.unibo.controlqa.MsgHandle_Controlqa"   ). %%store msgs 
qactor( controlqa_ctrl , blsqactor, "it.unibo.controlqa.Controlqa"   ). %%control-driven 
qactor( ledqa , blsqactor, "it.unibo.ledqa.MsgHandle_Ledqa"   ). %%store msgs 
qactor( ledqa_ctrl , blsqactor, "it.unibo.ledqa.Ledqa"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

