%====================================================================================
% Context ctxBlsQactorGui  SYSTEM-configuration: file it.unibo.ctxBlsQactorGui.blsQactorGui.pl 
%====================================================================================
context(ctxblsqactorgui, "localhost",  "TCP", "8059" ).  		 
%%% -------------------------------------------
qactor( buttonqagui , ctxblsqactorgui, "it.unibo.buttonqagui.MsgHandle_Buttonqagui"   ). %%store msgs 
qactor( buttonqagui_ctrl , ctxblsqactorgui, "it.unibo.buttonqagui.Buttonqagui"   ). %%control-driven 
qactor( controlqagui , ctxblsqactorgui, "it.unibo.controlqagui.MsgHandle_Controlqagui"   ). %%store msgs 
qactor( controlqagui_ctrl , ctxblsqactorgui, "it.unibo.controlqagui.Controlqagui"   ). %%control-driven 
qactor( ledqagui , ctxblsqactorgui, "it.unibo.ledqagui.MsgHandle_Ledqagui"   ). %%store msgs 
qactor( ledqagui_ctrl , ctxblsqactorgui, "it.unibo.ledqagui.Ledqagui"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

