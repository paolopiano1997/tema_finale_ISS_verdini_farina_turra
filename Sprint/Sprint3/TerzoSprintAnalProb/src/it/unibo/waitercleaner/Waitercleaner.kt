/* Generated by AN DISI Unibo */ 
package it.unibo.waitercleaner

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Waitercleaner ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var Table = ""
				val	Cleantime = 3000L
				var Clean = Cleantime
				var TableState = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("waitercleaner   |||   init")
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("isTableStopped(T)"), Term.createTerm("isTableStopped(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("isTableStopped", "isTableStoppedDone", "isTableStoppedDone(0)"   )  
						}else{
							println("waitercleaner   |||   wait")
						}
					}
					 transition(edgeName="t039",targetState="preDirty",cond=whenDispatch("startcleaner"))
					transition(edgeName="t040",targetState="wait",cond=whenDispatch("stopcleaner"))
					transition(edgeName="t041",targetState="wait",cond=whenRequest("isTableStopped"))
				}	 
				state("timerStop") { //this:State
					action { //it:State
						println("waitercleaner   |||   timerStop")
						request("stoptimer", "stoptimer(stop)" ,"timer" )  
					}
					 transition(edgeName="t042",targetState="stop",cond=whenReply("okStop"))
				}	 
				state("stop") { //this:State
					action { //it:State
						println("waitercleaner   |||   stop")
						updateResourceRep( "cleanStopped"  
						)
						if( checkMsgContent( Term.createTerm("okStop(T)"), Term.createTerm("okStop(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Clean = Cleantime - payloadArg(0).toLong()  
						}
					}
					 transition(edgeName="t043",targetState="preStart",cond=whenDispatch("startcleaner"))
					transition(edgeName="t044",targetState="replyTableStop",cond=whenRequest("isTableStopped"))
				}	 
				state("replyTableStop") { //this:State
					action { //it:State
						println("waitercleaner   |||   replyTableStop")
						answer("isTableStopped", "isTableStoppedDone", "isTableStoppedDone($Table)"   )  
					}
					 transition( edgeName="goto",targetState="stop", cond=doswitch() )
				}	 
				state("preStart") { //this:State
					action { //it:State
						println("waitercleaner   |||   preStart")
						updateResourceRep( "nonStopped"  
						)
						if( checkMsgContent( Term.createTerm("startcleaner(T)"), Term.createTerm("startcleaner(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 TableState = payloadArg(0)  
								request("tableState", "tableState($TableState)" ,"teatables" )  
						}
					}
					 transition(edgeName="t045",targetState="checkState",cond=whenReply("state"))
				}	 
				state("checkState") { //this:State
					action { //it:State
						println("waitercleaner   |||   checkState")
						if( checkMsgContent( Term.createTerm("state(N,S)"), Term.createTerm("state(N,S)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0) == "dirty"  
								 ){forward("gotodirty", "gotodirty(go)" ,"waitercleaner" ) 
								}
								else
								 {if(  payloadArg(0)=="undirty"  
								  ){forward("gotoundirty", "gotoundirty(go)" ,"waitercleaner" ) 
								 }
								 else
								  {forward("gotosanitized", "gotosanitized(go)" ,"waitercleaner" ) 
								  }
								 }
						}
					}
					 transition(edgeName="t046",targetState="cleanDirty",cond=whenDispatch("gotodirty"))
					transition(edgeName="t047",targetState="cleanUndirty",cond=whenDispatch("gotoundirty"))
					transition(edgeName="t048",targetState="cleanSanitized",cond=whenDispatch("gotosanitized"))
				}	 
				state("preDirty") { //this:State
					action { //it:State
						 Clean = Cleantime  
						if( checkMsgContent( Term.createTerm("startcleaner(T)"), Term.createTerm("startcleaner(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Table = payloadArg(0)  
						}
					}
					 transition( edgeName="goto",targetState="cleanDirty", cond=doswitch() )
				}	 
				state("preUndirty") { //this:State
					action { //it:State
						 Clean = Cleantime  
					}
					 transition( edgeName="goto",targetState="cleanUndirty", cond=doswitch() )
				}	 
				state("preSanitized") { //this:State
					action { //it:State
						 Clean = Cleantime  
					}
					 transition( edgeName="goto",targetState="cleanSanitized", cond=doswitch() )
				}	 
				state("cleanDirty") { //this:State
					action { //it:State
						println("waitercleaner   |||   cleanDirty")
						if( checkMsgContent( Term.createTerm("isTableStopped(T)"), Term.createTerm("isTableStopped(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("isTableStopped", "isTableStoppedDone", "isTableStoppedDone(0)"   )  
						}else{
							forward("starttimer", "starttimer($Clean)" ,"timer" ) 
						}
					}
					 transition(edgeName="t049",targetState="preUndirty",cond=whenDispatch("endtime"))
					transition(edgeName="t050",targetState="timerStop",cond=whenDispatch("stopcleaner"))
					transition(edgeName="t051",targetState="cleanDirty",cond=whenRequest("isTableStopped"))
				}	 
				state("cleanUndirty") { //this:State
					action { //it:State
						updateResourceRep( "dirty"  
						)
						println("waitercleaner   |||   cleanUndirty")
						if( checkMsgContent( Term.createTerm("isTableStopped(T)"), Term.createTerm("isTableStopped(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("isTableStopped", "isTableStoppedDone", "isTableStoppedDone(0)"   )  
						}else{
							forward("setTableState", "setTableState($Table,undirty)" ,"teatables" ) 
							forward("starttimer", "starttimer($Clean)" ,"timer" ) 
						}
					}
					 transition(edgeName="t052",targetState="preSanitized",cond=whenDispatch("endtime"))
					transition(edgeName="t053",targetState="timerStop",cond=whenDispatch("stopcleaner"))
					transition(edgeName="t054",targetState="cleanUndirty",cond=whenRequest("isTableStopped"))
				}	 
				state("cleanSanitized") { //this:State
					action { //it:State
						updateResourceRep( "undirty"  
						)
						println("waitercleaner   |||   cleanSanitized")
						if( checkMsgContent( Term.createTerm("isTableStopped(T)"), Term.createTerm("isTableStopped(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("isTableStopped", "isTableStoppedDone", "isTableStoppedDone(0)"   )  
						}else{
							forward("setTableState", "setTableState($Table,sanitized)" ,"teatables" ) 
							forward("starttimer", "starttimer($Clean)" ,"timer" ) 
						}
					}
					 transition(edgeName="t055",targetState="cleanDone",cond=whenDispatch("endtime"))
					transition(edgeName="t056",targetState="timerStop",cond=whenDispatch("stopcleaner"))
					transition(edgeName="t057",targetState="cleanSanitized",cond=whenRequest("isTableStopped"))
				}	 
				state("cleanDone") { //this:State
					action { //it:State
						updateResourceRep( "clean"  
						)
						forward("clean", "clean($Table)" ,"teatables" ) 
						forward("cleanerdone", "cleanerdone(done)" ,"waitermind" ) 
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
			}
		}
}
