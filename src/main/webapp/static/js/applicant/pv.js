function printPV(eligibleCenterId, typeList){

	var url =$("#baseUrl").val()+"/expertPv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}

function printApplicantList(eligibleCenterId, typeList){

	var url =$("#baseUrl").val()+"/expertPv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}
function printFailedTheory(eligibleCenterId, typeList){

	var url =$("#baseUrl").val()+"/expertPv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}
function printFailedPractical(eligibleCenterId, typeList){
	var url =$("#baseUrl").val()+"/expertPv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}

function eligibleCenter_PV_action(value,id,entity){
	switch(value){
	case "action":
		return;
    		
	case "history":
		
		openNav('PV TRACKING', '', '50%');
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		
		break;
		
	default:
	   return;
	}
}

"use strict";
function entranceEligibleCenter_PV_action(value,id,entity){
	
	switch(value){
	case "action":
		return;
    		
	case "history":
		//id = id;
	    //alert("id = "+id);
	    
	    //entity = entity;
		//alert("entity ="+entity);
		openNav('EXAM PV TRACKING', '', '50%');
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		
		break;
		
	default:
	   return;
	}
}



function resetEligibleCenter(eligibleCenterId){
	swal.fire({
        title: 'Are you sure you want to reset ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, reset PV!'
    }).then(function(result) {
        if (result.value) {
        	//we reset 
        	var baseUrlOfController="/simt/expertPv/update/reset/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
        			//start for reset
        			if( responseSuccess == "ok"){
        				
        		        var url = $("#baseUrl").val() + "/expertPv/search";
        		        var sessionDate = $("#session-date").val();
        		        if (sessionDate=="&") {
        		        	refreshPVPage(url,true);
        		    	}else{
        		    		refreshPVPage(url,false);
        		    	}

        				swal.fire(
        		                'PV Resetted!',
        		                'Your Pv has been Resetted.',
        		                'success'
        		            );
        				return;
        				
        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to reset this PV .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'the PV actual state is not CLOSED.',
        		                'error'
        		            )
        				return;
        			}
        			
        			
        			
        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}
            
        }
    });
}


function closeEligibleCenter(eligibleCenterId){
	swal.fire({
        title: 'Are you sure you want to close ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, close PV!'
    }).then(function(result) {
        if (result.value) {
        	//we close 
        	var baseUrlOfController="/simt/expertPv/update/close/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 

        			//start for close
        			if( responseSuccess == "ok"){
        			
        			var url = $("#baseUrl").val() + "/expertPv/search";
     		        var sessionDate = $("#session-date").val();
     		        if (sessionDate=="&") {
     		        	refreshPVPage(url,true);
     		    	}else{
     		    		refreshPVPage(url,false);
     		    	}
        				swal.fire(
        		                'PV Closed!',
        		                'Your Pv has been Closed.',
        		                'success'
        		            )
        				return;
        				
        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to close this PV .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'the PV actual state is not OPENED.',
        		                'error'
        		            )
        				return;
        			}
        			//end for close
        			
        			
        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}
            
        }
    });
}

function validateEligibleCenter(eligibleCenterId){
	swal.fire({
        title: 'Are you sure you want to validate ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, validate PV!'
    }).then(function(result) {
        if (result.value) {
        	//first we call validation form 
        	var baseUrlOfControllerForValidationForm="/simt/expertPv/get/validationForm/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfControllerForValidationForm) != null ){
        		openNav('Pv Validation', '', '40%');
        		sendGet(getBaseUrl(baseUrlOfControllerForValidationForm)).then(function(getFormSuccess){
        		$("#contenu").html(getFormSuccess); 
        		console.log("pv validation form loaded ...");
        		
        	
        	}).catch(function(getFormError){
    			alert('echec ! '+getFormError);
    		});
        		
        }
            
        }
    });
}



var searchButton = document.querySelector('#search-button-experts');
searchButton.addEventListener('click', function(){
var url = $("#baseUrl").val() + "/expertPv/search";
searchPV(url);

});

function searchPV(url){
	var sessionDate = $("#session-date").val();
	if (sessionDate=="&") {
		document.getElementById("date-error").innerHTML = "Please select a valid session";
		return;
	}
	var region = $("#region").val();
	var str = "";
	str += "?session=" + sessionDate +"&region=" + region;
	
	url += str;
	doGet(url, "content");
	
	document.getElementById("date-error").innerHTML = "";

}


var yearInput = document.querySelector('#pvYear');
yearInput.addEventListener('change', function(){
	var value = event.target.value;
	if(value==""|| value==null||isNaN(value)||value<=1970) {
		document.getElementById("year-error").innerHTML = "Invalid Year Provided";
		return;
	}else{
		document.getElementById("year-error").innerHTML = "";
	}
	var url = $("#baseUrl").val() + "/expertPv/get/sessions";
	loadExamSession(value, url);

});


function loadExamSession(value, url){
   
    var error="";
    var str="";
    str +=  '?year=' + value;
    document.getElementById('table-eligible-centers').innerHTML="";
    url = url+str;
    doGet(url, "sessions");

    

}


function validatePV(eligibleCenterId){
	//validate PV here register file
	document.getElementById("successId").classList.add("kt-hidden");
	document.getElementById("failedId").classList.add("kt-hidden");

   if($("#pvValidationForm").valid()==true) {
	//we validate 
  	var baseUrlOfController="/simt/expertPv/update/validate/"+eligibleCenterId;
  	if(getBaseUrl(baseUrlOfController) != null ){
  		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
		//start response validate 
		if( responseSuccess == "ok"){
			uploadFile(eligibleCenterId,0,"entranceEligibleCenter" ,"fileId" ).then(
					function(responseFileSuccess){
						if(responseFileSuccess=="ok.file.uploaded"){
							
							 document.getElementById("successId").classList.remove("kt-hidden");
							 document.getElementById("fileId").value=null;
							 
							 var url = $("#baseUrl").val() + "/expertPv/search";
			     		        var sessionDate = $("#session-date").val();
			     		        if (sessionDate=="&") {
			     		        	refreshPVPage(url,true);
			     		    	}else{
			     		    		refreshPVPage(url,false);
			     		    	}
			        				
							 var span = document.getElementsByClassName("close")[0];
							 span.click();
							 swal.fire(
		        		                'PV Validated!',
		        		                'Your Pv has been Validate.',
		        		                'success'
		        		            )
		        				
							 return;
							 
						
						}
					}
					).catch(
					 function(responseFileError){
						 document.getElementById("failedId").classList.remove("kt-hidden");
						 console.log('echec upload PV File ! '+responseFileError);
					        }
							);
			
			
			
			
		}
		if( responseSuccess == "koBadRole"){
			swal.fire(
	                'Not Allowed!',
	                'you do not have enough rights to validate this PV .',
	                'error'
	            )
			return;
		}
		if( responseSuccess == "koBadState"){
			swal.fire(
	                'Not Allowed!',
	                'the PV actual state is not CLOSED.',
	                'error'
	            )
			return;
		}
		
		//end response validate 
	}
	).catch(function(responseError){
		alert('echec ! '+responseError);
	});
}
	 
 

  }else $("#pvValidationForm").validate();
	
}



function createEligibleCenter(baseUrl) {
	const checkboxes=document.querySelectorAll('input[name="center"]');
	let listCenter="";
	for(const checkbox of checkboxes){
		if(checkbox.checked)
			if(listCenter=="")
			listCenter=checkbox.value;
			else listCenter=checkbox.value+","+listCenter;
	}
	
	let sessionDate=document.querySelector("#sessionId");
    if(sessionDate.value==""||listCenter=="") {
    	swal.fire(
                'Not Allowed!',
                'Select one session date and at least one exam center.',
                'error'
            );
    }else {
    	
    	 baseUrl=baseUrl+"/expertPv/create"
    	 var formData = new FormData();
    	 formData.append("centers",listCenter);
    	 formData.append("sessionDate",sessionDate.value);    	 
    	 displayLoading("eligibleFormId");
    	 doPost(baseUrl,formData,"kt_content").then(result=>{
    		 removeLoading("eligibleFormId");
    		 closeNav();
    		 if(result=="ok"){
    			var url = $("#baseUrl").val() + "/expertPv/search";
 		        var sessionDate = $("#session-date").val();
 		        if (sessionDate=="&") {
 		        	refreshPVPage(url,true);
 		    	}else{
 		    		refreshPVPage(url,false);
 		    	}

	    		 swal.fire(
	    					 'Success !',
	    					 'Eligible Center created successfuly !.',
	    					 'success'
	    			)	; 
    		 }else 
    			swal.fire(
    					 'Failed !',
    					 'Eligible Center was not updated.',
    					 'error'
    	   )	; 
    		
    	 }).catch(error=>{
    		 removeLoading("eligibleFormId");
    		 closeNav();
    		 swal.fire(
    					 'Failed !',
    					 'Something went wrong at our end.',
    					 'error'
    	   )	; 
    		
    		 
    	 });
    	
    	
    	
    }
}

function refreshPVPage(url,isGlobalLoading){
	if(isGlobalLoading){
		var baseUrlOfController="/simt/expertPv/list";
	  	if(getBaseUrl(baseUrlOfController)!= null){
	  		doGet(getBaseUrl(baseUrlOfController),'kt_content');
	  		active_link('concours-experts');
	  		return false;
	  	}
	}else{
		searchPV(url);
	}
	
}

function openEligibleCenter(eligibleCenterId){

	swal.fire({
        title: 'Are you sure you want to open ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, open PV!'
    }).then(function(result) {
        if (result.value) {
        	//we close
        	var baseUrlOfController="/simt/expertPv/update/open/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for close
        			if( responseSuccess == "ok"){
        			var url = $("#baseUrl").val() + "/expertPv/search";
     		        var sessionDate = $("#session-date").val();
     		        if (sessionDate=="&") {
     		        	refreshPVPage(url,true);
     		    	}else{
     		    		refreshPVPage(url,false);
     		    	}
        				swal.fire(
        		                'PV Opened!',
        		                'Your Pv has been Opened.',
        		                'success'
        		            )
        				return;
        				
        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to open this PV .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'the PV actual state is not CREATED.',
        		                'error'
        		            )
        				return;
        			}
        			//end for close
        			
        			
        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}
            
        }
    });
}

function generatePV(eligibleCenterId){

	swal.fire({
        title: 'Are you sure you want to generate this PV ?',
        text: "You won't be able to revert action!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes,Generate PV!'
    }).then(function(result) {
        if (result.value) {
        	//we close 
        	var baseUrlOfController="/simt/expertPv/update/generate/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 

        			//start for close
        			if( responseSuccess == "ok"){
        			
        			var url = $("#baseUrl").val() + "/expertPv/search";
     		        var sessionDate = $("#session-date").val();
     		        if (sessionDate=="&") {
     		        	refreshPVPage(url,true);
     		    	}else{
     		    		refreshPVPage(url,false);
     		    	}
        				swal.fire(
        		                'PV Generated!',
        		                'Your Pv has been successfully generated.',
        		                'success'
        		            )
        				return;
        				
        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to generate this PV .',
        		                'error'
        		            )
        				return;
        			}
                    if( responseSuccess == "koNotReady"){
        				swal.fire(
        		                'Not Allowed!',
        		                'This PV is Not Ready, do a verification for it.',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'The current state of the PV does not allow you to perform this action',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koNotReady"){
        				swal.fire(
        		                'Not Allowed!',
        		                'This PV cannot be generated at the moment. Appropriate exam results must be specified for all its candidates!!',
        		                'error'
        		            )
        				return;
        			}
        			//end for close
        			
        			
        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}
            
        }
    });
	
}

function resetPV(eligibleCenterId){
	swal.fire({
        title: 'Are you sure you want to reset ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, reset PV!'
    }).then(function(result) {
        if (result.value) {
        	//we reset 
        	var baseUrlOfController="/simt/expertPv/update/reset_pv/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
        			//start for reset
        			if( responseSuccess == "ok"){
        				
        		        var url = $("#baseUrl").val() + "/expertPv/search";
        		        var sessionDate = $("#session-date").val();
        		        if (sessionDate=="&") {
        		        	refreshPVPage(url,true);
        		    	}else{
        		    		refreshPVPage(url,false);
        		    	}

        				swal.fire(
        		                'PV Resetted!',
        		                'Your Pv has been Resetted.',
        		                'success'
        		            );
        				return;
        				
        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to reset this PV .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'This PV has not yet been generated!!',
        		                'error'
        		            )
        				return;
        			}
        			
        			
        			
        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}
            
        }
    });
}


