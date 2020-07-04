/* Generated by AN DISI Unibo */ 
package it.unibo.staytimertable2

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Staytimertable2 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 
				val MaxStayTime = 60*10L //In secondi
				var TimePassed = 0L
				var RemTime = 0L
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						discardMessages = true
						println("staytimertable2   |||   init")
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("staytimertable2   |||   wait")
					}
					 transition(edgeName="t096",targetState="start",cond=whenDispatch("starttimer"))
				}	 
				state("start") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("starttimer(T)"), Term.createTerm("starttimer(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("staytimertable2   |||   start")
						}
						 TimePassed++  
						if(  TimePassed>=MaxStayTime  
						 ){forward("endtime", "endtime($TimePassed)" ,"staytimertable2" ) 
						}
						stateTimer = TimerActor("timer_start", 
							scope, context!!, "local_tout_staytimertable2_start", 1000.toLong() )
					}
					 transition(edgeName="t097",targetState="start",cond=whenTimeout("local_tout_staytimertable2_start"))   
					transition(edgeName="t098",targetState="replyTimeStart",cond=whenRequest("getRemainingTime"))
					transition(edgeName="t099",targetState="wait",cond=whenDispatch("stopstaytimer"))
					transition(edgeName="t0100",targetState="timeFinish",cond=whenDispatch("endtime"))
					transition(edgeName="t0101",targetState="end",cond=whenDispatch("timeroff"))
				}	 
				state("replyTimeStart") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("getRemainingTime(N)"), Term.createTerm("getRemainingTime(N)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 RemTime = MaxStayTime - TimePassed  
								answer("getRemainingTime", "remainingTime", "remainingTime($RemTime)"   )  
						}
					}
					 transition( edgeName="goto",targetState="start", cond=doswitch() )
				}	 
				state("end") { //this:State
					action { //it:State
						 TimePassed = 0L  
						 RemTime = 0L  
						println("staytimertable2   |||   end")
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("timeFinish") { //this:State
					action { //it:State
						println("staytimertable2   |||   timeFinish")
						 RemTime = 0L  
						 TimePassed = 0L  
						forward("timePassed", "timePassed(2)" ,"maxstaytimer" ) 
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
			}
		}
}
