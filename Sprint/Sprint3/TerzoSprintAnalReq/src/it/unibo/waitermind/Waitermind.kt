/* Generated by AN DISI Unibo */ 
package it.unibo.waitermind

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Waitermind ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
			
			val CollectTime = 3000L
		
			var CurMoveX = 0
			var CurMoveY = 0
			var CurCID = 0
			var CurTable = 0
			var CurTableClean = 0
			var CurOrder = ""
			
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						discardMessages = false
						solve("consult('tearoomkb.pl')","") //set resVar	
						println("waitermind   |||   init")
						request("start", "start(id)" ,"waiterengine" )  
					}
					 transition(edgeName="t00",targetState="home",cond=whenReply("ready"))
				}	 
				state("reachhome") { //this:State
					action { //it:State
						println("waitermind   |||   reachhome")
						solve("pos(home,X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) { CurMoveX = getCurSol("X").toString().toInt()  
						 			   CurMoveY = getCurSol("Y").toString().toInt()  
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						}
						else
						{}
					}
					 transition(edgeName="t01",targetState="home",cond=whenDispatch("done"))
				}	 
				state("home") { //this:State
					action { //it:State
						println("waitermind   |||   home")
						updateResourceRep( "home"  
						)
					}
					 transition(edgeName="t02",targetState="accept",cond=whenRequest("enter"))
					transition(edgeName="t03",targetState="take",cond=whenDispatch("clientready"))
					transition(edgeName="t04",targetState="reachBarman",cond=whenDispatch("drinkready"))
					transition(edgeName="t05",targetState="reachTableCollect",cond=whenDispatch("paymentready"))
					transition(edgeName="t06",targetState="endwork",cond=whenDispatch("end"))
				}	 
				state("accept") { //this:State
					action { //it:State
						println("waitermind   |||   accept")
						if( checkMsgContent( Term.createTerm("enter(ID)"), Term.createTerm("enter(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 CurCID = payloadArg(0).toString().toInt()  
								request("tableClean", "tableClean(n)" ,"teatables" )  
						}
					}
					 transition(edgeName="t07",targetState="checkTables",cond=whenReply("table"))
				}	 
				state("checkTables") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("table(N)"), Term.createTerm("table(N)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 CurTable = payloadArg(0).toString().toInt()  
						}
						if(  CurTable != 0  
						 ){println("waitermind   |||   tableclean=$CurTable")
						answer("enter", "accept", "accept($CurTable)"   )  
						forward("occupy", "occupy($CurTable,$CurCID)" ,"teatables" ) 
						}
						else
						 {println("waitermind   |||   accept failed, $CurCID")
						 answer("enter", "accept", "accept(0)"   )  
						  CurCID = 0  
						 }
					}
					 transition( edgeName="goto",targetState="reachEntranceDoor", cond=doswitchGuarded({ CurCID != 0  
					}) )
					transition( edgeName="goto",targetState="checkCleanHome", cond=doswitchGuarded({! ( CurCID != 0  
					) }) )
				}	 
				state("reachEntranceDoor") { //this:State
					action { //it:State
						updateResourceRep( "reachingEntranceDoor"  
						)
						println("waitermind   |||   reachEntranceDoor")
						solve("pos(entrancedoor,X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) { 
									   CurMoveX = getCurSol("X").toString().toInt()  
						 			   CurMoveY = getCurSol("Y").toString().toInt()  
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						}
						else
						{}
					}
					 transition(edgeName="t08",targetState="convoyToTable",cond=whenDispatch("done"))
				}	 
				state("convoyToTable") { //this:State
					action { //it:State
						updateResourceRep( "convoyToTable$CurTable"  
						)
						println("waitermind   |||   convoyToTable$CurTable")
						solve("pos('teatable$CurTable',X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) { 
									   CurMoveX = getCurSol("X").toString().toInt()  
						 			   CurMoveY = getCurSol("Y").toString().toInt()  
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						forward("occupy", "occupy($CurTable)" ,"teatables" ) 
						}
						else
						{}
						println("waitermind   |||   table1 state occupied")
					}
					 transition(edgeName="t09",targetState="checkCleanHome",cond=whenDispatch("done"))
				}	 
				state("checkCleanHome") { //this:State
					action { //it:State
						 readLine()  
						println("waitermind   |||   checkClean")
						request("isTableStopped", "isTableStopped(isStopped)" ,"waitercleaner" )  
					}
					 transition(edgeName="t010",targetState="checkIsTableStopped",cond=whenReply("isTableStoppedDone"))
				}	 
				state("checkIsTableStopped") { //this:State
					action { //it:State
						println("waitermind   |||   checkIsTableStopped")
						if( checkMsgContent( Term.createTerm("isTableStoppedDone(E)"), Term.createTerm("isTableStoppedDone(E)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0)=="0"  
								 ){forward("gotohome", "gotohome(go)" ,"waitermind" ) 
								 CurTableClean = 0  
								}
								else
								 {if(  payloadArg(0)=="1"  
								  ){ CurTableClean = 1  
								 }
								 else
								  { CurTableClean = 2  
								  }
								 }
						}
						stateTimer = TimerActor("timer_checkIsTableStopped", 
							scope, context!!, "local_tout_waitermind_checkIsTableStopped", 200.toLong() )
					}
					 transition(edgeName="t011",targetState="reachTableCleanStopped",cond=whenTimeout("local_tout_waitermind_checkIsTableStopped"))   
					transition(edgeName="t012",targetState="reachhome",cond=whenDispatch("gotohome"))
				}	 
				state("reachTableCleanStopped") { //this:State
					action { //it:State
						println("waitermind   |||   reachTable1CleanStopped")
						solve("pos('teatable$CurTableClean',X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) {
									   CurMoveX = getCurSol("X").toString().toInt()
						 			   CurMoveY = getCurSol("Y").toString().toInt()
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						}
						else
						{}
					}
					 transition(edgeName="t013",targetState="cleanTable",cond=whenDispatch("done"))
				}	 
				state("take") { //this:State
					action { //it:State
						println("waitermind   |||   take")
						updateResourceRep( "take"  
						)
						if( checkMsgContent( Term.createTerm("clientready(ID)"), Term.createTerm("clientready(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												CurCID = payloadArg(0).toString().toInt()
						}
						request("getTable", "getTable($CurCID)" ,"teatables" )  
					}
					 transition(edgeName="t014",targetState="checkTake",cond=whenReply("tableId"))
				}	 
				state("checkTake") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("tableId(N)"), Term.createTerm("tableId(N)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 CurTable = payloadArg(0).toString().toInt()  
								solve("pos('teatable$CurTable',X,Y)","") //set resVar	
								if( currentSolution.isSuccess() ) { 
												   CurMoveX = getCurSol("X").toString().toInt()  
									 			   CurMoveY = getCurSol("Y").toString().toInt()  
								forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
								}
								else
								{}
						}
					}
					 transition(edgeName="t015",targetState="waitOrder",cond=whenDispatch("done"))
				}	 
				state("waitOrder") { //this:State
					action { //it:State
						println("waitermind   |||   waitOrder")
					}
					 transition(edgeName="t016",targetState="transmit",cond=whenDispatch("order"))
				}	 
				state("transmit") { //this:State
					action { //it:State
						 readLine()  
						println("waitermind   |||   transmit")
						updateResourceRep( "transmit"  
						)
						if( checkMsgContent( Term.createTerm("order(ID,O)"), Term.createTerm("order(ID,O)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("order", "order($CurCID,payloadArg(0))" ,"barman" ) 
						}
					}
					 transition( edgeName="goto",targetState="checkCleanHome", cond=doswitch() )
				}	 
				state("reachBarman") { //this:State
					action { //it:State
						updateResourceRep( "reachBarman"  
						)
						println("waitermind   |||   reachBarman")
						if( checkMsgContent( Term.createTerm("drinkready(ID,O)"), Term.createTerm("drinkready(ID,O)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 CurCID = payloadArg(0).toString().toInt()  
						}
						solve("pos(barman,X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) { 
									   CurMoveX = getCurSol("X").toString().toInt()  
						 			   CurMoveY = getCurSol("Y").toString().toInt()  
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						}
						else
						{}
					}
					 transition(edgeName="t017",targetState="serve",cond=whenDispatch("done"))
				}	 
				state("serve") { //this:State
					action { //it:State
						 readLine()  
						println("waitermind   |||   serve")
						request("getTable", "getTable($CurCID)" ,"teatables" )  
					}
					 transition(edgeName="t018",targetState="checkTableId",cond=whenReply("tableId"))
				}	 
				state("checkTableId") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("tableId(N)"), Term.createTerm("tableId(N)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 CurTable = payloadArg(0).toString().toInt()  
								solve("pos('teatable$CurTable',X,Y)","") //set resVar	
								if( currentSolution.isSuccess() ) { 
												   CurMoveX = getCurSol("X").toString().toInt()  
									 			   CurMoveY = getCurSol("Y").toString().toInt()  
								forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
								}
								else
								{}
						}
					}
					 transition(edgeName="t019",targetState="checkCleanHome",cond=whenDispatch("done"))
				}	 
				state("reachTableCollect") { //this:State
					action { //it:State
						println("waitermind   |||   reachTableCollect")
						if( checkMsgContent( Term.createTerm("paymentready(ID)"), Term.createTerm("paymentready(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 CurCID = payloadArg(0).toString().toInt()  
						}
						solve("getState(N,occupy($CurCID))","") //set resVar	
						if( currentSolution.isSuccess() ) { CurTable = getCurSol("N").toString().toInt()  
						}
						else
						{}
						updateResourceRep( "reachTable$CurTable"  
						)
						solve("pos('teatable$CurTable',X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) { 
									   CurMoveX = getCurSol("X").toString().toInt()  
						 			   CurMoveY = getCurSol("Y").toString().toInt()  
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						}
						else
						{}
					}
					 transition(edgeName="t020",targetState="collect",cond=whenDispatch("done"))
				}	 
				state("reachTableClean") { //this:State
					action { //it:State
						 readLine()  
						println("waitermind   |||   reachTable")
						updateResourceRep( "reachTableClean"  
						)
						 CurTableClean = CurTable  
						solve("pos('teatable$CurTable',X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) { 
									   CurMoveX = getCurSol("X").toString().toInt()  
						 			   CurMoveY = getCurSol("Y").toString().toInt()  
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						}
						else
						{}
					}
					 transition(edgeName="t021",targetState="cleanTable",cond=whenDispatch("done"))
				}	 
				state("collect") { //this:State
					action { //it:State
						println("waitermind   |||   collect")
						updateResourceRep( "collect"  
						)
						delay(CollectTime)
					}
					 transition( edgeName="goto",targetState="convoyToExitDoor", cond=doswitch() )
				}	 
				state("convoyToExitDoor") { //this:State
					action { //it:State
						println("waitermind   |||   convoyToExitDoor")
						updateResourceRep( "convoyToExitDoor"  
						)
						 CurCID = 0  
						solve("pos(exitdoor,X,Y)","") //set resVar	
						if( currentSolution.isSuccess() ) { 
									   CurMoveX = getCurSol("X").toString().toInt()  
						 			   CurMoveY = getCurSol("Y").toString().toInt()  
						forward("moveto", "moveto($CurMoveX,$CurMoveY)" ,"waiterengine" ) 
						}
						else
						{}
						forward("release", "release($CurTable)" ,"teatables" ) 
					}
					 transition(edgeName="t022",targetState="reachTableClean",cond=whenDispatch("done"))
				}	 
				state("cleanTable") { //this:State
					action { //it:State
						updateResourceRep( "cleanTable$CurTableClean"  
						)
						println("waitermind   |||   cleanTable$CurTableClean")
						forward("startcleaner", "startcleaner($CurTableClean)" ,"waitercleaner" ) 
					}
					 transition(edgeName="t023",targetState="reachhome",cond=whenDispatch("cleanerdone"))
					transition(edgeName="t024",targetState="handleEnter",cond=whenRequest("enter"))
				}	 
				state("handleEnter") { //this:State
					action { //it:State
						forward("stopcleaner", "stopcleaner(stop)" ,"waitercleaner" ) 
					}
					 transition( edgeName="goto",targetState="accept", cond=doswitch() )
				}	 
				state("endwork") { //this:State
					action { //it:State
						println("waitermind   |||   end")
						terminate(0)
					}
				}	 
			}
		}
}
