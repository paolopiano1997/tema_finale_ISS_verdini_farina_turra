%====================================================================================
% tearoom description   
%====================================================================================
context(ctxwaiter, "localhost",  "TCP", "8068").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiter, ctxwaiter, "it.unibo.waiter.Waiter").
  qactor( client, ctxwaiter, "it.unibo.client.Client").
