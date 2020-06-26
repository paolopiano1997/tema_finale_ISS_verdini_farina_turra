%====================================================================================
% subtearoom description   
%====================================================================================
context(ctxsubtearoom, "127.0.0.1",  "TCP", "8068").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiterengine, ctxsubtearoom, "it.unibo.waiterengine.Waiterengine").
msglogging.
