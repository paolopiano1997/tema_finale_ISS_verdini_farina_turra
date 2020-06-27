%====================================================================================
% tearoom description   
%====================================================================================
mqttBroker("mqtt.eclipse.org", "1883", "mado/mado").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxtearoom, "127.0.0.1",  "TCP", "8050").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
  qactor( timer, ctxtearoom, "it.unibo.timer.Timer").
  qactor( waitercleaner, ctxtearoom, "it.unibo.waitercleaner.Waitercleaner").
  qactor( teatables, ctxtearoom, "it.unibo.teatables.Teatables").
  qactor( waiterengine, ctxtearoom, "it.unibo.waiterengine.Waiterengine").
