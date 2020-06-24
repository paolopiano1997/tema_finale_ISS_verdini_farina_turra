%====================================================================================
% subtearoom description   
%====================================================================================
context(ctxtearoom, "127.0.0.1",  "TCP", "8068").
context(ctxtearoom_dummy, "localhost",  "TCP", "8050").
 qactor( teatables, ctxtearoom_dummy, "it.unibo.teatables.Teatables").
  qactor( timer, ctxtearoom_dummy, "it.unibo.timer.Timer").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
  qactor( waitercleaner, ctxtearoom_dummy, "it.unibo.waitercleaner.Waitercleaner").
