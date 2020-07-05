var stompClient = null;
var hostAddr = "http://localhost:7002/move";

$("form").submit(function(e) {
    e.preventDefault();
});

//SIMULA UNA FORM che invia comandi POST
function sendRequestData( params, method) {
    method = method || "post"; // il metodo POST � usato di default
    //console.log(" sendRequestData  params=" + params + " method=" + method);
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", hostAddr);
    var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "move");
        hiddenField.setAttribute("value", params);
     	//console.log(" sendRequestData " + hiddenField.getAttribute("name") + " " + hiddenField.getAttribute("value"));
        form.appendChild(hiddenField);
    document.body.appendChild(form);
    console.log("body children num= "+document.body.children.length );
    form.submit();
    document.body.removeChild(form);
    console.log("body children num= "+document.body.children.length );
}


function postJQuery(params){
	var form = new FormData();
	form.append("name",  "move");
	form.append("value", params);
	
	//let myForm = document.getElementById('myForm');
	//let formData = new FormData(myForm);
	
	
	var settings = {
	  "url": "http://localhost:8080/move",
	  "method": "POST",
	  "timeout": 0,
	  "headers": {
	       "Content-Type": "text/plain"
	   },
	  "processData": false,
	  "mimeType": "multipart/form-data",
	  "contentType": false,
	  "data": form
	};
	
	$.ajax(settings).done(function (response) {
	  console.log("AJAX: " + response);  //The web page
	  console.log("done move:" + themove );
	});

}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/it-unibo-iss');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/display', function (msg) {
             showMsg(JSON.parse(msg.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/*
function sendMove() {
    stompClient.send("/app/move", {}, JSON.stringify({'name': $("#name").val()}));
}
*/

function sendTheMove(move){
	console.log("sendTheMove " + move);
    stompClient.send("/app/move", {}, JSON.stringify({'name': move }));
}

function sendUpdateRequest(){
	console.log(" sendUpdateRequest "  );
    stompClient.send("/app/update", {}, JSON.stringify({'name': 'update' }));
}
var curMsg = ""

function showMsg(message) {
	if(curMsg === message)
		return;
	console.log(message );
//    $("#applmsgs").html( message);
    if(message.toString().includes("welcome") || message.toString().includes("home") || message.toString().includes("wait")){
    	window.alert(message);
    }
    curMsg = message;
    // document.getElementById("applmsgs").value = message;
    //$("#applmsgs").onchange();
    //document.getElementById("applmsgs").onchange();
    //document.getElementById("applmsgs").dispatchEvent(new Event('change'));
    //Aggiungere dispatchEvent pure qua
   // document.getElementById("t1").dispatchEvent(new Event('change'));
    //document.getElementById("t2").dispatchEvent(new Event('change'));
   // document.getElementById("waiterstate").dispatchEvent(new Event('change'));
   // $("#applmsgs").val(message);
   // $("#applmsgs").trigger("change");
    //$("#applmsgintable").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
     $("form").on('submit', function (e) {
         e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });

//USED BY SOCKET.IO-BASED GUI  

    $( "#h" ).click(function() {  sendTheMove("h") });
    $( "#w" ).click(function() {  sendTheMove("w") });
    $( "#s" ).click(function() {  sendTheMove("s") });
    $( "#r" ).click(function() {  sendTheMove("r") });
    $( "#l" ).click(function() {  sendTheMove("l") });
    $( "#x" ).click(function() {  sendTheMove("x") });
    $( "#z" ).click(function() {  sendTheMove("z") });
    $( "#p" ).click(function() {  sendTheMove("p") });

    //$( "#rr" ).click(function() { console.log("submit rr"); redirectPost("r") });
    //$( "#rrjo" ).click(function() { console.log("submit rr"); jqueryPost("r") });

//USED BY POST-BASED GUI   
    
    $( "#ww" ).click(function() { sendRequestData( "w") });
    $( "#ss" ).click(function() { sendRequestData( "s") });
    $( "#rr" ).click(function() { sendRequestData( "r") });
    $( "#ll" ).click(function() { sendRequestData( "l") });
    $( "#zz" ).click(function() { sendRequestData( "z") });
    $( "#xx" ).click(function() { sendRequestData( "x") });
    $( "#pp" ).click(function() { sendRequestData( "p") });
    $( "#hh" ).click(function() { sendRequestData( "h") });

    $( "#bell" ).click(function() { sendRequestData( "enter")});
    $( "#cready" ).click(function() { sendRequestData( "clientready") })
    $( "#order" ).click(function() { sendRequestData( "order") })
    $( "#cash" ).click(function() { sendRequestData( "payment") });
    $( "#bell2" ).click(function() { sendRequestData( "enter2") });
    $( "#cready2" ).click(function() { sendRequestData( "clientready2") })
    $( "#order2" ).click(function() { sendRequestData( "order2") })
    $( "#cash2" ).click(function() { sendRequestData( "payment2") });
    
//USED BY POST-BASED BOUNDARY  
    $( "#start" ).click(function() { sendRequestData( "w") });
    $( "#stop" ).click(function()  { sendRequestData( "h") });

	$( "#update" ).click(function() { sendUpdateRequest(  ) });
});



