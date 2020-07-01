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
	var waitercleaner         : ActorBasic? = null
	var teatables         	  : ActorBasic? = null
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
	
	/*Controllo che waitermind sia nello stato collect*/
	fun checkTimeFinished(){
		if(waitermind!=null)
			assert(waitermind!!.geResourceRep()==Expected.collect)
	}
	
	@Test
	fun testRobotboundary(){
	 	runBlocking{
 			while( waitermind == null ){
				println("testWaiterPosition waits for waitermind ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
				waitercleaner = it.unibo.kactor.sysUtil.getActor("waitercleaner")
				teatables = it.unibo.kactor.sysUtil.getActor("teatables")
 			}
			
			MsgUtil.sendMsg(enter,waitermind!!)
 			delay(61000)
			checkTimeFinished()
			
			//MsgUtil.sendMsg(clientready,waitermind!!)
			//delay(15000)
			//MsgUtil.sendMsg(drinkready,waitermind!!)
			//delay(65000) //sforo il maxstaytime
			//MsgUtil.sendMsg(paymentready,waitermind!!)
			//delay(10000)													
						
			MsgUtil.sendMsg("end","end","end",waitermind!!)
 			if( waitermind != null ) waitermind!!.waitTermination()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}