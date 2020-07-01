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
  qactor( barman, ctxtearoom, "it.unibo.barman.Barman").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
  qactor( maxstaytimer, ctxtearoom, "it.unibo.maxstaytimer.Maxstaytimer").
  qactor( staytimertable1, ctxtearoom, "it.unibo.staytimertable1.Staytimertable1").
  qactor( staytimertable2, ctxtearoom, "it.unibo.staytimertable2.Staytimertable2").
