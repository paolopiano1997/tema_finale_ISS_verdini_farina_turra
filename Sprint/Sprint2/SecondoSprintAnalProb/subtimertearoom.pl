%====================================================================================
% subtimertearoom description   
%====================================================================================
context(ctxtearoom_dummy, "192.168.0.38",  "TCP", "8050").
context(ctxtearoom_dummy_timer, "localhost",  "TCP", "8045").
 qactor( waitercleaner, ctxtearoom_dummy, "it.unibo.waitercleaner.Waitercleaner").
  qactor( timer, ctxtearoom_dummy_timer, "it.unibo.timer.Timer").
