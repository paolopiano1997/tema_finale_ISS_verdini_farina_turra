%====================================================================================
% tearoom description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxtearoom, "::1",  "TCP", "8068").
context(ctxtearoom_dummy, "127.0.0.1",  "TCP", "8050").
context(ctxtearoom_teatables, "192.168.0.38",  "TCP", "8045").
 qactor( teatables, ctxtearoom_teatables, "it.unibo.teatables.Teatables").
  qactor( waitercleaner, ctxtearoom_dummy, "it.unibo.waitercleaner.Waitercleaner").
  qactor( waiterengine, ctxtearoom, "it.unibo.waiterengine.Waiterengine").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
  qactor( client, ctxtearoom, "it.unibo.client.Client").
