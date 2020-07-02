package connQak

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.CoapHandler 
 
object CoapObserver {

    private val client = CoapClient()
	
	private val ipaddr      = "localhost:8050"		//5683 default
	private val context     = "ctxtearoom"
 	private val destactor   = "tearoomstate"
//	private val msgId       = "cmd"

	fun init(){
       val uriStr = "coap://$ipaddr/$context/$destactor"
	   System.out.println("CoapObserver | START uriStr: $uriStr")
       client.uri = uriStr
       client.observe(object : CoapHandler {
            override fun onLoad(response: CoapResponse) {
               // System.out.println("CoapObserver | GET RESP-CODE= " + response.code + " content:" + response.responseText)
            	println("${response.responseText}")
			}
            override fun onError() {
                System.out.println("CoapObserver | FAILED")
            }
        })		
	}
 }

 
 fun main( ) {
		CoapObserver.init()
		System.`in`.read()   //to avoid exit
 }

