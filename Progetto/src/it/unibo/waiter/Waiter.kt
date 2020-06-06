/* Generated by AN DISI Unibo */ 
package it.unibo.waiter

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Waiter ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						itunibo.planner.plannerUtil.initAI(  )
					}
					 transition( edgeName="goto",targetState="reachHome", cond=doswitch() )
				}	 
				state("reachHome") { //this:State
					action { //it:State
						println("Reaching home...")
						delay(1000) 
					}
					 transition(edgeName="t00",targetState="accept",cond=whenRequest("enter"))
				}	 
				state("accept") { //this:State
					action { //it:State
					}
				}	 
			}
		}
}
