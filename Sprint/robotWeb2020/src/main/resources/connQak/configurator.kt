package connQak

import org.json.JSONObject
import java.io.File
//import java.nio.charset.Charset
//import org.apache.commons.io.Charsets
  

object configurator{
//Page
	@JvmStatic public var pageTemplate		= "GUI"

//MQTT broker	
//	@JvmStatic var mqtthostAddr    	= "broker.hivemq.com"
	@JvmStatic var mqtthostAddr    	= "mqtt.eclipse.org"
	@JvmStatic var mqttport    		= "1883"
//
	@JvmStatic var stepsize			= "350" 
	
//Basicrobot application
	@JvmStatic var hostAddr   	    = "127.0.0.1";  //"192.168.1.5";		
	@JvmStatic var port    			= "8050";
	@JvmStatic var qakdest	     	= "waitermind";
	@JvmStatic var ctxqadest 		= "ctxtearoom";
	
//Domains application
//	@JvmStatic var hostAddr   	    = "192.168.1.22";  //"192.168.1.5";		
//	@JvmStatic var port    			= "8060";
//	@JvmStatic var qakdest	     	= "waiter";
//	@JvmStatic var ctxqadest 		= "ctxdomains";
	
	@JvmStatic	//to be used by Java
	fun configure(){
		try{
			val configfile =   File("pageConfig.json")
			val config     =   configfile.readText()	//charset: Charset = Charsets.UTF_8
			//println( "		--- configurator | config=$config" )
			val jsonObject	=  JSONObject( config )			
			pageTemplate 	=  jsonObject.getString("page") 
			hostAddr    	=  jsonObject.getString("host") 
			port    		=  jsonObject.getString("port")
			qakdest         =  jsonObject.getString("qakdest")
			ctxqadest		=  jsonObject.getString("ctxqadest")
			stepsize		=  jsonObject.getString("stepsize")
		}catch(e:Exception){
			System.out.println( " &&& SORRY pageConfig.json NOT FOUND ")
			pageTemplate 	=  "GUI"  //jsonObject.getString("page") 
			hostAddr    	=  "127.0.0.1"    //jsonObject.getString("host") 
			port    		= "8050"             //jsonObject.getString("port")
			qakdest         = "waitermind"       //jsonObject.getString("qakdest")
			ctxqadest		= "ctxtearoom"    //jsonObject.getString("ctxqadest")
			stepsize		= "350"              //jsonObject.getString("stepsize")
		}
		
		System.out.println( "		--- configurator | pageTemplate=$pageTemplate hostAddr=$hostAddr port=$port stepsize=$stepsize" )
		
	}
}

