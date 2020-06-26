%====================================================================================
% subtimertearoom description   
%====================================================================================
context(ctxtearoom_dummy, "192.168.0.38",  "TCP", "8050").
 qactor( waitercleaner, ctxtearoom_dummy, "it.unibo.waitercleaner.Waitercleaner").
  qactor( timer, ctxtearoom_dummy, "it.unibo.timer.Timer").
