/* Generated by AN DISI Unibo */ 
package it.unibo.smartbell

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Smartbell ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val tmax = 37.5
				var IDRange = 1
				var CTemp:Double = 0.0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("smartbell   |||   init")
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
						println("smartbell   |||   wait")
					}
					 transition(edgeName="t075",targetState="checkTemperature",cond=whenRequest("enter"))
				}	 
				state("checkTemperature") { //this:State
					action { //it:State
						 CTemp = kotlin.random.Random.nextDouble(35.0,40.0)  
						if(  CTemp < tmax  
						 ){println("smartbell   |||   temp = $CTemp, client accepted")
						request("enter", "enter($IDRange)" ,"waitermind" )  
						 IDRange++  
						}
						else
						 {println("smartbell   |||   temp = $CTemp, client rejected")
						 forward("reject", "reject(acasa)" ,"smartbell" ) 
						 }
					}
					 transition(edgeName="t076",targetState="rejected",cond=whenDispatch("reject"))
					transition(edgeName="t077",targetState="done",cond=whenReply("accept"))
				}	 
				state("done") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("accept(C)"), Term.createTerm("accept(C)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  payloadArg(0) == "0"  
								 ){println("smartbell   |||   tearoom is full, please wait...")
								answer("enter", "accept", "accept(0)"   )  
								}
								else
								 {println("smartbell   |||   welcome!")
								 answer("enter", "accept", "accept(${payloadArg(0)})"   )  
								 }
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("rejected") { //this:State
					action { //it:State
						println("smartbell   |||   client temperatur is >= 37.5, go home!")
						 CTemp = -1.0  
						answer("enter", "accept", "accept($CTemp)"   )  
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
			}
		}
}
