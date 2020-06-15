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

class TestWaiterTableStates {
	var waitermind            : ActorBasic? = null
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
	
	fun checkState(state: String){		
		if(waitermind!=null)
			assert(waitermind!!.geResourceRep()==state)
	}
	
	@Test
	fun testRobotboundary(){
	 	runBlocking{
 			while( waitermind == null ){
				println("testWaiterPosition waits for waitermind ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
 			}
			
			MsgUtil.sendMsg(MsgUtil.buildRequest("waitermind","enter","enter","waitermind"),waitermind!!)
 			delay(25000)
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","clientready","clientready","waitermind"),waitermind!!)
			delay(15000)
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","drinkready","drinkready","waitermind"),waitermind!!)
			delay(10000)
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","paymentready","paymentready","waitermind"),waitermind!!)
			delay(20000)
			checkState("dirty")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","ok","ok","waitermind"),waitermind!!)
			delay(10000)
			checkState("undirty")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","ok","ok","waitermind"),waitermind!!)
			delay(10000)
			checkState("sanitized")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","ok","ok","waitermind"),waitermind!!)
			delay(10000)
			checkState("cleaned")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","ok","ok","waitermind"),waitermind!!)
			delay(10000)
			MsgUtil.sendMsg("end","end","end",waitermind!!)
 			if( waitermind != null ) waitermind!!.waitTermination()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}