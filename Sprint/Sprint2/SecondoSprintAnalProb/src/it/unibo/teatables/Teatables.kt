/* Generated by AN DISI Unibo */ 
package it.unibo.teatables

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Teatables ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
			var StateOfTables = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("teatables START")
						solve("consult('tearoomkb.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						solve("stateOfTeatables(S)","") //set resVar	
						if( currentSolution.isSuccess() ) { StateOfTables = getCurSol("S").toString()  
						}
						else
						{}
						println("teatables s0: $StateOfTables")
						updateResourceRep( StateOfTables  
						)
					}
					 transition(edgeName="t00",targetState="engageTable",cond=whenDispatch("engage"))
					transition(edgeName="t01",targetState="cleanTable",cond=whenDispatch("clean"))
					transition(edgeName="t02",targetState="replyClean",cond=whenRequest("isClean"))
					transition(edgeName="t03",targetState="setState",cond=whenDispatch("setTableState"))
				}	 
				state("setState") { //this:State
					action { //it:State
					}
				}	 
				state("replyClean") { //this:State
					action { //it:State
					}
				}	 
				state("engageTable") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("engage(N)"), Term.createTerm("engage(N)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("engageTable ${payloadArg(0)}")
								solve("engageTable(${payloadArg(0)})","") //set resVar	
								solve("stateOfTeatables(S)","") //set resVar	
								 StateOfTables = getCurSol("S").toString()  
								println("teatables engageTable ${payloadArg(0)}: $StateOfTables")
								updateResourceRep( StateOfTables  
								)
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("cleanTable") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("clean(N)"), Term.createTerm("clean(N)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("clean ${payloadArg(0)}")
								solve("cleanTable(${payloadArg(0)})","") //set resVar	
								solve("stateOfTeatables(S)","") //set resVar	
								 StateOfTables = getCurSol("S").toString()  
								println("teatables cleanTable ${payloadArg(0)}: $StateOfTables")
								updateResourceRep( StateOfTables  
								)
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}
