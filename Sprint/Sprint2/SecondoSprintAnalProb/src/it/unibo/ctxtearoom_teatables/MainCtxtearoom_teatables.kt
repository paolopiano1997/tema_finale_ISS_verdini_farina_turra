/* Generated by AN DISI Unibo */ 
package it.unibo.ctxtearoom_teatables
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "subteatablestearoom.pl", "sysRules.pl"
	)
}
