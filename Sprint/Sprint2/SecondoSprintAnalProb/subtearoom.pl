%====================================================================================
% subtearoom description   
%====================================================================================
context(ctxtearoom, "127.0.0.1",  "TCP", "8068").
context(ctxtearoom_dummy, "192.168.0.38",  "TCP", "8050").
context(ctxtearoom_teatables, "localhost",  "TCP", "8030").
context(ctxtearoom_dummy_timer, "localhost",  "TCP", "8045").
 qactor( teatables, ctxtearoom_teatables, "it.unibo.teatables.Teatables").
  qactor( timer, ctxtearoom_dummy_timer, "it.unibo.timer.Timer").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
  qactor( waitercleaner, ctxtearoom_dummy, "it.unibo.waitercleaner.Waitercleaner").
