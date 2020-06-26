%====================================================================================
% tearoom description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxtearoom, "127.0.0.1",  "TCP", "8068").
context(ctxtearoom_dummy, "192.168.0.38",  "TCP", "8050").
context(ctxtearoom_teatables, "localhost",  "TCP", "8030").
 qactor( teatables, ctxtearoom_teatables, "it.unibo.teatables.Teatables").
  qactor( waitercleaner, ctxtearoom_dummy, "it.unibo.waitercleaner.Waitercleaner").
  qactor( waiterengine, ctxtearoom, "it.unibo.waiterengine.Waiterengine").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
