/* Generated by AN DISI Unibo */ 
package it.unibo.timer

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Timer ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 
				var Time: Long = 0
				var StartTime : Long = 0	
				var Duration : Long	= 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("timer   |||   init")
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("timer   |||   wait")
					}
					 transition(edgeName="t025",targetState="start",cond=whenDispatch("starttimer"))
				}	 
				state("start") { //this:State
					action { //it:State
						println("timer   |||   start")
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("starttimer(T)"), Term.createTerm("starttimer(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Time = payloadArg(0).toLong()  
								StartTime = getCurrentTime()
						}
						stateTimer = TimerActor("timer_start", 
							scope, context!!, "local_tout_timer_start", Time )
					}
					 transition(edgeName="t026",targetState="timeFinish",cond=whenTimeout("local_tout_timer_start"))   
					transition(edgeName="t027",targetState="stop",cond=whenRequest("stoptimer"))
				}	 
				state("stop") { //this:State
					action { //it:State
						Duration = getDuration(StartTime)
						answer("stoptimer", "okStop", "okTop($Duration)"   )  
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("timeFinish") { //this:State
					action { //it:State
						forward("endtime", "endtime(endtime)" ,"waitercleaner" ) 
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
			}
		}
}
