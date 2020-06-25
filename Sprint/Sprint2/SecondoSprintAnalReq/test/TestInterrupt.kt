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
import Expected




class TestWaiterTableStates {
	var waitermind            : ActorBasic? = null
	//val mqttTest   	      = MqttUtils("test") 
	val initDelayTime     = 1000L

	val enter = MsgUtil.buildRequest("waitermind","enter","enter","waitermind")
	val clientready = MsgUtil.buildDispatch("waitermind","clientready","clientready","waitermind")	
	val drinkready = MsgUtil.buildDispatch("waitermind","drinkready","drinkready","waitermind")
	val paymentready = MsgUtil.buildDispatch("waitermind","paymentready","paymentready","waitermind")
	
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
	
	fun checkState(){
		if(waitermind!=null)
			assert(waitermind!!.geResourceRep()==Expected.expect)
	}
	
	@Test
	fun testRobotboundary(){
	 	runBlocking{
 			while( waitermind == null ){
				println("testWaiterPosition waits for waitermind ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
 			}
			
			MsgUtil.sendMsg(enter,waitermind!!)
 			delay(20000)
			MsgUtil.sendMsg(clientready,waitermind!!)
			delay(20000)
			MsgUtil.sendMsg(drinkready,waitermind!!)
			delay(20000)
			MsgUtil.sendMsg(paymentready,waitermind!!)
			delay(15000)
			MsgUtil.sendMsg(enter,waitermind!!)
			delay(6000)
			checkState()
 			delay(20000)
			MsgUtil.sendMsg("end","end","end",waitermind!!)
 			if( waitermind != null ) waitermind!!.waitTermination()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}