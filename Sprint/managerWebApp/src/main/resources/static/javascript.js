function showGif(){
	console.log(document.getElementById("waiterstate").value)
		if(document.getElementById("waiterstate").value.includes("reach")
			|| document.getElementById("waiterstate").value.includes("convoy")){
			document.getElementById("waiter-f").style.display = "none";
			document.getElementById("waiter-m").style.display = "block";
		}else{
			document.getElementById("waiter-f").style.display = "block";
			document.getElementById("waiter-m").style.display = "none";
		}
	}
  var table1State = 0
  var table2State = 0
  function changeTable1State(){
    if(document.getElementById("t1").value.includes("occup")){
      document.getElementById('t1img').src='table1-red.png';
    }
    else if(document.getElementById("t1").value.includes("dirty")){
      document.getElementById('t1img').src='table1-yellow.png';
    }
    else if(document.getElementById("t1").value.includes("clean")){
      document.getElementById('t1img').src='table1.png';
    }
      
  }
  function changeTable2State(){
    if(document.getElementById("t2").value.includes("occup")){
      document.getElementById('t2img').src='table1-red.png';
    }
    else if(document.getElementById("t2").value.includes("dirty")){
      document.getElementById('t2img').src='table1-yellow.png';
    }
    else if(document.getElementById("t2").value.includes("clean")){
      document.getElementById('t2img').src='table1.png';
    }
  }
  var state = ""
  var waiter = ""
  var tableState1 = ""
  var tableState2 = ""
  var barman = ""
  function loadstate(trigger){
	state = document.getElementById("applmsgs").value
	localStorage.setItem("state",state)
	let rows = state.split("\n")
	waiter =rows[0].substring(7,rows[0].length-1)
	var tableStates = rows[1].substring(17).split(",")
	var table1 = tableStates[0].replace("[teatable1(","")
	tableState1 = table1.substring(0,table1.length-1)
	tableState2 = tableStates[1].replace("teatable2(","").replace(")])","")
	barman = rows[2].substring(7,rows[2].length-1)
	load()
  }
  
  function load(){
	if(localStorage.getItem("state")){
		document.getElementById("applmsgs").value  = localStorage.getItem("state");
	}
	console.log("Function load\n\rWaiter: " + waiter + "\n\rTable1: " + tableState1 +
		"\n\rTable2: " + tableState2 + "\n\rBarman: " + barman		
	)
	document.getElementById("waiterstate").value = waiter
	document.getElementById("t1").value = tableState1
	document.getElementById("t2").value = tableState2
	document.getElementById("barman").value = barman
	document.getElementById("applmsgs").value = state
  }
  	function loadbell(){
		document.getElementById("audio").play();
	};
	function loadcash(){
		document.getElementById("cashaudio").play();
	};

