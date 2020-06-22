%====================================================================================
% tearoom description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxtearoom, "127.0.0.1",  "TCP", "8068").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiterengine, ctxtearoom, "it.unibo.waiterengine.Waiterengine").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
  qactor( client, ctxtearoom, "it.unibo.client.Client").
