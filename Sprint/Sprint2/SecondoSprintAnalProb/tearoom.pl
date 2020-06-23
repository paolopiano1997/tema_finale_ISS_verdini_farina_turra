%====================================================================================
% tearoom description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxtearoom, "127.0.0.1",  "TCP", "8068").
context(ctxtearoom_dummy, "localhost",  "TCP", "8050").
 qactor( teatables, ctxtearoom_dummy, "it.unibo.teatables.Teatables").
  qactor( waitercleaner, ctxtearoom_dummy, "it.unibo.waitercleaner.Waitercleaner").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiterengine, ctxtearoom, "it.unibo.waiterengine.Waiterengine").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
