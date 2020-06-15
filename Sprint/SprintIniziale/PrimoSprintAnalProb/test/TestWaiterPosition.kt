package test

import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//import mapRoomKotlin.mapUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.MqttUtils
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.ApplMessageType
import it.unibo.waiterengine.Waiterengine

class TestWaiterPosition {
	var waitermind            : ActorBasic? = null
	var waiterengine		  : ActorBasic? = null
	//val mqttTest   	      = MqttUtils("test") 
	val initDelayTime     = 1000L
	
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxtearoom.main()
		}
	}

	@After
	fun terminate() {
		println("%%%  TestWaiter terminate ")
	}
	
	fun checkPosition(x: String, y: String){		
		if( waiterengine != null ){
			println(" --- checkHome --- ${waiterengine!!.geResourceRep()}")
			assertTrue( waiterengine!!.geResourceRep() == "($x,$y)")
		}  
	}
	
	@Test
	fun testRobotboundary(){
	 	runBlocking{
 			while( waitermind == null ){
				println("testWaiterPosition waits for waiterengine ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
 			}
			while(waiterengine == null){
				println("testWaiterPosition waits for waiterengine...")
				delay(initDelayTime)
				waiterengine = it.unibo.kactor.sysUtil.getActor("waiterengine")
			}
			
			MsgUtil.sendMsg(MsgUtil.buildRequest("waitermind","enter","enter","waitermind"),waitermind!!)
 			delay(15000)
			checkPosition("0","0")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","clientready","clientready","waitermind"),waitermind!!)
			delay(15000)
			checkPosition("2","2")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","gohome","gohome","waitermind"),waitermind!!)
			delay(8000)
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","drinkready","drinkready","waitermind"),waitermind!!)
			delay(10000)
			checkPosition("6","0")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","gohome","gohome","waitermind"),waitermind!!)
			delay(15000)
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","paymentready","paymentready","waitermind"),waitermind!!)
			delay(15000)
			checkPosition("6","4")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","gohome","gohome","waitermind"),waitermind!!)
			delay(20000)
			MsgUtil.sendMsg("end","end","end",waitermind!!)
 			if( waitermind != null ) waitermind!!.waitTermination()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}