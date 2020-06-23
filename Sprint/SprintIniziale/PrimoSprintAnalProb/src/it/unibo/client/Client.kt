/* Generated by AN DISI Unibo */ 
package it.unibo.client

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Client ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("client   |||   init")
					}
					 transition( edgeName="goto",targetState="doSomething", cond=doswitch() )
				}	 
				state("doSomething") { //this:State
					action { //it:State
						println("client   |||   sending enter request")
						request("enter", "enter(id)" ,"waitermind" )  
					}
					 transition(edgeName="t015",targetState="ok",cond=whenReply("accept"))
				}	 
				state("ok") { //this:State
					action { //it:State
						println("client   |||   accepted")
						delay(15000) 
						println("client   |||   ready")
						forward("clientready", "clientready(id)" ,"waitermind" ) 
						delay(15000) 
						println("barman   |||   ready")
						forward("drinkready", "drinkready(id)" ,"waitermind" ) 
						delay(15000) 
						forward("paymentready", "paymentready(id)" ,"waitermind" ) 
						delay(20000) 
						forward("end", "end(end)" ,"waitermind" ) 
					}
				}	 
			}
		}
}
