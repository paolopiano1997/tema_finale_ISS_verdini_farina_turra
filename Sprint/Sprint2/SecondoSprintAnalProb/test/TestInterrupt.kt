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
	//val mqttTest   	      = MqttUtils("test") 
	val initDelayTime     = 1000L

	val enter = MsgUtil.buildRequest("waitermind","enter","enter","waitermind")
	val clientready = MsgUtil.buildDispatch("waitermind","clientready","clientready","waitermind")	
	val drinkready = MsgUtil.buildDispatch("waitermind","drinkready","drinkready","waitermind")
	val paymentready = MsgUtil.buildDispatch("waitermind","paymentready","paymentready","waitermind")
	//val tableState = MsgUtil.buildDispatch("waitercleaner","tableState","tableState","waitercleaner")
	//val stoptimer = MsgUtil.buildDispatch("waitercleaner","stoptimer","stoptimer","waitercleaner")
	
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
	
	fun checkStop(){
		if(waitermind!=null)
			assert(waitermind!!.geResourceRep()==Expected.cleanStopped)
	}
	
	fun checkCleaning(){ //controllo che NON sia in stop
		if(waitermind!=null)
			assert(waitermind!!.geResourceRep()!=Expected.cleanStopped)
	}
	
	fun checkPrevState(){	//controllo che riprenda la clean dallo stato precedente
		if(teatables!=null)
			assert(teatables!!.geResourceRep()==Expected.tablePrevState)
	}
	
	fun checkRemainingTime(){	//controllo che il timer segni il tempo effettivamente rimasto
		if(waitercleaner!=null)
			assert(waitercleaner!!.geResourceRep().toInt()<Expected.cleanRemainingTime)
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
 			delay(15000)
			MsgUtil.sendMsg(clientready,waitermind!!)
			delay(15000)
			MsgUtil.sendMsg(drinkready,waitermind!!)
			delay(15000)
			MsgUtil.sendMsg(paymentready,waitermind!!)
			delay(10000)
			MsgUtil.sendMsg(enter,waitermind!!)	//INTERROMPO LA CLEAN
			
			//subito dopo l'interruzione ho bisogno di sapere lo stato a cui era rimasto il tavolo
			Expected.tablePrevState = teatables!!.geResourceRep()	//SALVATAGGIO in Expected.tablePrevState, param: tavolo
			//allo stesso modo salvo il tempo rimasto
			Expected.cleanRemainingTime = waitercleaner!!.geResourceRep().toInt()
													
 			delay(8000)
			checkStop()	//controllo che il waiter abbia interrotto la clean
			
			delay(10000)	
			checkCleaning()	//controllo che riprenda la clean
			checkPrevState()	//controllo che riprenda la clean dallo stato precedente
			checkRemainingTime()//controllo che il timer segni il tempo effettivamente rimasto
			
			delay(20000) //da aggiustare in modo che corrisponda più o meno alla fine della clean
			//controllo che una volta raggiunto sanitized NON interrompa la clean
			MsgUtil.sendMsg(enter,waitermind!!)	//entra il cliente mentre la clean è in sanitized
			checkCleaning() //controllo semplicemente se sta continuando a pulire
						
			MsgUtil.sendMsg("end","end","end",waitermind!!)
 			if( waitermind != null ) waitermind!!.waitTermination()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}