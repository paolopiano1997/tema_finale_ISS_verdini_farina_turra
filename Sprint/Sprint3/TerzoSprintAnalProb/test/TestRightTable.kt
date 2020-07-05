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
class TestRightTable {
	var waitermind            : ActorBasic? = null
	val initDelayTime     = 1000L

	val enter1 = MsgUtil.buildRequest("waitermind","enter","enter(1)","waitermind")
	val enter2 = MsgUtil.buildRequest("waitermind","enter","enter(2)","waitermind")
	
	val clientready = MsgUtil.buildDispatch("waitermind","clientready","clientready(1)","waitermind")	
	
	
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
	
	// Controllo che waiter sia andato al tavolo giusto
	fun checkRightTable(){
		println(waitermind!!.geResourceRep())
		if(waitermind!=null)
			assert(waitermind!!.geResourceRep()==Expected.table)
	}
	
	@Test
	fun testRobotboundary(){
	 	runBlocking{
 			while( waitermind == null ){
				println("testWaiterPosition waits for waitermind ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
 			}
			
			MsgUtil.sendMsg(enter1,waitermind!!)
			MsgUtil.sendMsg(enter2,waitermind!!)
			MsgUtil.sendMsg(clientready,waitermind!!)
 			readLine()
			checkRightTable()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}