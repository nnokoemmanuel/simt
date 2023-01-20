/**
 * Created 11-March-2020
 * 
 * Session CRUD
 */

function createExamSession(){
	
	displayLoading("kt_content");

	var sessionDate = document.getElementById('sessionDate').value;
	var myModal = document.getElementById('myModal');
	var url = $("#baseUrl").val() + "/marine/session/create/post/" + sessionDate;
	
	myModal.style.display = "none";
	console.log(myModal);
	console.log("I am here");
	doGet(url, 'kt_content');
	
	
  
}

function initializeSessionDatePicker(){
	
	$("#sessionDate1").datepicker({
		widgetPositioning : {
			vertical : 'bottom'
		},
		format : 'yyyy-mm-dd',
		useCurrent : true,
		minDate : 'now',
		maxDate : new Date()	
	});
}





function updateExamSession() {
	
	displayLoading("kt_content");
	
	var id = document.getElementById("id").value;
	var sessionDate1 = document.getElementById("sessionDate1").value 
	
	var url = $("#baseUrl").val() + "/marine/session/update/post/" + id + "/"+ sessionDate1;
	var myModal = document.getElementById('myModal');
	
	doGet(url, 'content');
	myModal.style.display = "none";
	
	
}


function loadSession() {
	
	var id = document.getElementById("sessionYear").value;
	var date = id;
	var url = $("#baseUrl").val() + "/marine/session/list/" + date;
	if(id==""){
		swal.fire(
                'Merci de Renseigner les champs de recherche ...!',
                'ok !'
           )
           return;
	}else{
	displayLoading("kt_content");
	doGet(url, 'kt_content');
	}
}



function searchSessionTable() {
	
	var buttonText=$('#searchSession').text();
	
	if(buttonText=="Change"){
		
		document.getElementById('searchSession').innerText="Change";
		
		document.getElementById("kt_content").style.display='none';
		
	}else{
		
		//document.getElementById('searchSession').innerText="Hide";
		//alert("emms"+allez);
		document.getElementById("kt_content").style.display='inline-block';
	
		
	}

	
}









