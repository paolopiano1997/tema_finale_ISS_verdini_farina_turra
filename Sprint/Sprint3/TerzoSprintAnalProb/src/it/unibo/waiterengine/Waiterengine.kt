/* Generated by AN DISI Unibo */ 
package it.unibo.waiterengine

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Waiterengine ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var XP = "0"
				var YP = "0"
				var CurMove = ""
				val inmapname  = "teaRoomExplored"
				var StepTime    	   = 348L
				val BackTime           = 2 * StepTime / 3
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						discardMessages = false
						itunibo.planner.plannerUtil.initAI(  )
						itunibo.planner.plannerUtil.loadRoomMap( inmapname  )
						itunibo.planner.plannerUtil.showCurrentRobotState(  )
						println("waiterengine   |||   init")
					}
					 transition(edgeName="t075",targetState="started",cond=whenRequest("start"))
				}	 
				state("started") { //this:State
					action { //it:State
						println("waiterengine   |||   started")
						answer("start", "ready", "ready(waiterengine)"   )  
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
						println("waiterengine   |||   wait")
					}
					 transition(edgeName="t076",targetState="planmove",cond=whenDispatch("moveto"))
					transition(edgeName="t077",targetState="wait",cond=whenDispatch("stopengine"))
				}	 
				state("planmove") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("moveto(X,Y)"), Term.createTerm("moveto(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 XP = payloadArg(0)
											   YP = payloadArg(1)			  
								println("waiterengine   |||   moveto ($XP,$YP)")
								itunibo.planner.plannerUtil.planForGoal( "$XP", "$YP"  )
						}
					}
					 transition( edgeName="goto",targetState="execPlanMove", cond=doswitch() )
				}	 
				state("execPlanMove") { //this:State
					action { //it:State
						  CurMove = itunibo.planner.plannerUtil.getNextPlannedMove()  
						stateTimer = TimerActor("timer_execPlanMove", 
							scope, context!!, "local_tout_waiterengine_execPlanMove", 100.toLong() )
					}
					 transition(edgeName="t078",targetState="nextStep",cond=whenTimeout("local_tout_waiterengine_execPlanMove"))   
					transition(edgeName="t079",targetState="stop",cond=whenDispatch("stopengine"))
				}	 
				state("nextStep") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="execStep", cond=doswitchGuarded({ CurMove == "w"  
					}) )
					transition( edgeName="goto",targetState="execMove", cond=doswitchGuarded({! ( CurMove == "w"  
					) }) )
				}	 
				state("stop") { //this:State
					action { //it:State
						println("waiterengine   |||   stop")
						itunibo.planner.plannerUtil.resetActions(  )
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("execStep") { //this:State
					action { //it:State
						request("step", "step($StepTime)" ,"basicrobot" )  
					}
					 transition(edgeName="t080",targetState="stepDone",cond=whenReply("stepdone"))
					transition(edgeName="t081",targetState="stepFailed",cond=whenReply("stepfail"))
				}	 
				state("stepDone") { //this:State
					action { //it:State
						updateResourceRep( itunibo.planner.plannerUtil.getMapOneLine()  
						)
						itunibo.planner.plannerUtil.updateMap( "w"  )
					}
					 transition( edgeName="goto",targetState="execPlanMove", cond=doswitchGuarded({ CurMove.length > 0  
					}) )
					transition( edgeName="goto",targetState="endSuccess", cond=doswitchGuarded({! ( CurMove.length > 0  
					) }) )
				}	 
				state("stepFailed") { //this:State
					action { //it:State
						println("waiterengine | stepFailed")
						if( checkMsgContent( Term.createTerm("stepfail(DURATION,CAUSE)"), Term.createTerm("stepfail(DURATION,CAUSE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val D = payloadArg(0).toLong()  ; val Dt = Math.abs(StepTime-D); val BackT = D/2  
								println("waiterengine stepFail D= $D, BackTime = ${BackTime}")
								if(  D > BackTime  
								 ){forward("cmd", "cmd(s)" ,"basicrobot" ) 
								delay(BackTime)
								forward("cmd", "cmd(h)" ,"basicrobot" ) 
								}
						}
					}
					 transition( edgeName="goto",targetState="execPlanMove", cond=doswitchGuarded({ CurMove.length > 0  
					}) )
					transition( edgeName="goto",targetState="endSuccess", cond=doswitchGuarded({! ( CurMove.length > 0  
					) }) )
				}	 
				state("execMove") { //this:State
					action { //it:State
						forward("cmd", "cmd($CurMove)" ,"basicrobot" ) 
						itunibo.planner.plannerUtil.updateMap( "$CurMove"  )
						delay(100) 
					}
					 transition( edgeName="goto",targetState="execPlanMove", cond=doswitchGuarded({ CurMove.length > 0  
					}) )
					transition( edgeName="goto",targetState="endSuccess", cond=doswitchGuarded({! ( CurMove.length > 0  
					) }) )
				}	 
				state("endSuccess") { //this:State
					action { //it:State
						updateResourceRep( "($XP,$YP)"  
						)
						println("waiterengine   |||   endSuccess, curpos=($XP,$YP)")
						itunibo.planner.plannerUtil.showCurrentRobotState(  )
						forward("done", "done($XP,$YP)" ,"waitermind" ) 
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
			}
		}
}
