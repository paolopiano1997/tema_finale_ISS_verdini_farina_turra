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

class TestWaiter {
	var waiter            : ActorBasic? = null
	//val mqttTest   	      = MqttUtils("test") 
	val initDelayTime     = 1000L
	
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxwaiter.main()
		}
	}

	@After
	fun terminate() {
		println("%%%  TestWaiter terminate ")
	}
	
	fun checkReachEntranceDoor(value: String){		
		if( waiter != null ){
			println(" --- checkReachEntranceDoor --- ${waiter!!.geResourceRep()}")
			assertTrue( waiter!!.geResourceRep() == value)
		}  
	}
	
	fun checkReachTable(value: String){		
		if( waiter != null ){
			println(" --- checkReachTable --- ${waiter!!.geResourceRep()}")
			assertTrue( waiter!!.geResourceRep() == value)
		}  
	}
	
	fun checkWait(value: String){		
		if( waiter != null ){
			println(" --- checkWait --- ${waiter!!.geResourceRep()}")
			assertTrue( waiter!!.geResourceRep() == value)
		}  
	}
	
	@Test
	fun testRobotboundary(){
	 	runBlocking{
 			while( waiter == null ){
				println("testWaiter wait for waiter ... ")
				delay(initDelayTime)  //time for robot to start
				waiter = it.unibo.kactor.sysUtil.getActor("waiter")
 			}
			
			MsgUtil.sendMsg(MsgUtil.buildRequest("waiter","enter","enter","waiter"),waiter!!)
 			delay(7000)
			checkReachEntranceDoor( "reachingEntranceDoor" )
			MsgUtil.sendMsg("ready","ready","ready",waiter!!)
 			delay(7000)
			checkReachTable( "reachingTable" )
			MsgUtil.sendMsg("ready","ready","ready",waiter!!)
 			delay(7000)
 			checkWait( "home" )
			delay(2000)
			MsgUtil.sendMsg("end","end","end",waiter!!)
 			if( waiter != null ) waiter!!.waitTermination()
  		}
	 	println("testWaiter BYE  ")  
	}
}