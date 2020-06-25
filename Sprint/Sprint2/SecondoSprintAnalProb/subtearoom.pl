%====================================================================================
% subtearoom description   
%====================================================================================
context(ctxtearoom, "127.0.0.1",  "TCP", "8068").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiterengine, ctxtearoom, "it.unibo.waiterengine.Waiterengine").
msglogging.
