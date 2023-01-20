function printPV(eligibleCenterId, typeList){

	var url =$("#baseUrl").val()+"/pv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}

function printPVByNote(eligibleCenterId, typeList){

	var url =$("#baseUrl").val()+"/pv/printByNote?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}

function printCandidatesList(eligibleCenterId, typeList){
	var url =$("#baseUrl").val()+"/pv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}
function printFailedTheory(eligibleCenterId, typeList){

	var url =$("#baseUrl").val()+"/pv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}
function printFailedPractical(eligibleCenterId, typeList){
	var url =$("#baseUrl").val()+"/pv/print?id="+eligibleCenterId + "&typeList=" + typeList;
    window.open(url);
}


function eligibleCenter_PV_action(value,id,entity){
	switch(value){
	case "action":
		return;
    		
	case "history":
		
		openNav('PV TRACKING', '', '50%');
		//alert("PV TRACKING");
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
        	var baseUrlOfController="/simt/pv/update/reset/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
        			//start for reset
        			if( responseSuccess == "ok"){
        				
        		        var url = $("#baseUrl").val() + "/pv/search";
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
        	var baseUrlOfController="/simt/pv/update/close/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 

        			//start for close
        			if( responseSuccess == "ok"){
        			
        			var url = $("#baseUrl").val() + "/pv/search";
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
        	var baseUrlOfControllerForValidationForm="/simt/pv/get/validationForm/"+eligibleCenterId;
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



var searchButton = document.querySelector('#search-button');
searchButton.addEventListener('click', function(){
		var url = $("#baseUrl").val() + "/pv/search";
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
	var url = $("#baseUrl").val() + "/pv/get/sessions";
	loadExamSession(value, url);

});


function loadExamSession(value, url){
    var error="";
    var str="";
    str +=  '?year=' + value;
  //  document.getElementById('table-eligible-centers').innerHTML="";
    url = url+str;
    doGet(url, "sessions");

    

}


function validatePV(eligibleCenterId){
	//validate PV here register file
	document.getElementById("successId").classList.add("kt-hidden");
	document.getElementById("failedId").classList.add("kt-hidden");
    var juryNum = document.getElementById("juryNum").value;
    var eligibleCenterIdPlusJuryNum = eligibleCenterId+"@"+juryNum;
   if($("#pvValidationForm").valid()==true) {
	//we validate 
  	var baseUrlOfController="/simt/pv/update/validate/"+eligibleCenterIdPlusJuryNum;
  	if(getBaseUrl(baseUrlOfController) != null ){
  		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
		//start response validate 
		if( responseSuccess == "ok"){
			uploadFile(eligibleCenterId,0,"eligibleCenter" ,"fileId" ).then(
					function(responseFileSuccess){
						if(responseFileSuccess=="ok.file.uploaded"){
							
							 document.getElementById("successId").classList.remove("kt-hidden");
							 document.getElementById("fileId").value=null;
							 
							 var url = $("#baseUrl").val() + "/pv/search";
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
    	
    	 baseUrl=baseUrl+"/pv/create"
    	 var formData = new FormData();
    	 formData.append("centers",listCenter);
    	 formData.append("sessionDate",sessionDate.value);    	 
    	 displayLoading("eligibleFormId");
    	 doPost(baseUrl,formData,"kt_content").then(result=>{
    		 removeLoading("eligibleFormId");
    		 closeNav();
    		 if(result=="ok"){
    			var url = $("#baseUrl").val() + "/pv/search";
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
		var baseUrlOfController="/simt/pv/list";
	  	if(getBaseUrl(baseUrlOfController)!= null){
	  		doGet(getBaseUrl(baseUrlOfController),'kt_content');
	  		active_link('archive-pv');
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
        	var baseUrlOfController="/simt/pv/update/open/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for close
        			if( responseSuccess == "ok"){
        			var url = $("#baseUrl").val() + "/pv/search";
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
        	var baseUrlOfController="/simt/pv/update/generate/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 

        			//start for close
        			if( responseSuccess == "ok"){
        			
        			var url = $("#baseUrl").val() + "/pv/search";
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
        	var baseUrlOfController="/simt/pv/update/reset_pv/"+eligibleCenterId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
        			//start for reset
        			if( responseSuccess == "ok"){
        				
        		        var url = $("#baseUrl").val() + "/pv/search";
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

function searchButtonCandidateList() {

	let  dateRange=document.getElementById("dateRangeId").value;
    dateRange =dateRange.split("-");
    [startDate, endDate]=dateRange;
    
	if(document.getElementById("statusStudents").value != "" || !(startDate =="" &&  endDate ==undefined)){
		if(document.getElementById("statusCandidates").value != "" || document.getElementById("eligibleCenterIdl").value != "0"){
			swal.fire('Please do not fill block 1 and 2 at the same time ! / veuillez soumettre uniquement un bloc pour la recherche!')
			return;
		}
	}

	var licenseNumber=undefined;
	var matricule=undefined;

	var status = -1;
	if(document.getElementById("statusStudents").value != "")
		 status= document.getElementById("statusStudents").value;
	if(document.getElementById("statusCandidates").value != "")
         status= document.getElementById("statusCandidates").value;
	if(document.getElementById("licenseNumber").value.trim().length == 12)
		licenseNumber= document.getElementById("licenseNumber").value;
	if(document.getElementById("licenseNumber").value.trim().length == 7)
		matricule= document.getElementById("licenseNumber").value;
    var eligibleCenterId = document.getElementById("eligibleCenterIdl").value;

    var speciality= document.getElementById("specialityCandidates").value;
    if(document.getElementById("statusStudents").value != "" && !(startDate =="" &&  endDate ==undefined)){
    	if(document.getElementById("statusStudents").value == ""){
    		swal.fire('please select student status ! / veuillez selectionner un statut !')
			return;
    	}
    	//if block 1 is launched !
    	var url = $("#baseUrl").val() + "/manageCandidate/listeSearchCandidate?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&eligibleCenterId=" + eligibleCenterId + "&licenseNumber=" + licenseNumber + "&matricule=" + matricule+ "&speciality=" + speciality+"";
		doGet(url,'content');
		return;
    }

    if(document.getElementById("statusCandidates").value != "" && document.getElementById("eligibleCenterIdl").value != "0"){
    	//cas de recherche par bloc 2
    	if(document.getElementById("statusCandidates").value == ""){
    		swal.fire('please select student status ! / veuillez selectionner un statut !')
			return;
    	}
    	
    	//if block 2 is launched !	
    	var url = $("#baseUrl").val() + "/manageCandidate/listeSearchCandidate?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&eligibleCenterId=" + eligibleCenterId + "&licenseNumber=" + licenseNumber + "&matricule=" + matricule+ "&speciality=" + speciality+"";
		doGet(url,'content');
		
		return;
    	
    }


    if(document.getElementById("licenseNumber").value.trim().length > 0){
    	eligibleCenterId = 0;
    	status = -1;
    	var url = $("#baseUrl").val() + "/manageCandidate/listeSearchCandidate?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&eligibleCenterId=" + eligibleCenterId + "&licenseNumber=" + licenseNumber + "&matricule=" + matricule+ "&speciality=" + speciality+"";
		doGet(url,'content');
		return;
    	
    }

}




function validateSearchOrigin(){
	var specialityId = document.getElementById("specialityId");
    if (specialityId.value == "" || specialityId.value == 0 ) {
        //If the "" or 0 option is selected display error.
        //alert("Please select an option!");
        swal.fire(
	                'Please select an option!',
	                'Sil vous plait choisissez une option!.',
	                'error'
	            )
			
        return false;
    }
    //alert("ok!");
    searchOrigin(baseUrl);
    return true;
}


function searchOrigin(baseUrl) {
	//alert("searchOrigin");
	var specialityId = $("#specialityId").val();
	//alert("specialityId"+specialityId);	
	var url = $("#baseUrl").val() + "/manageCandidate/filterSpeciality?&specialityId=" + specialityId;
	doGet(url,'content');
	return;
	
}




function printElligibleCenterTranscriptPreview(baseUrl,eligibleCenterId){
	var studentSessionId = 0;
	var url =baseUrl+"/pv/printTranscriptPreview?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId;
	openNav('TRANSCRIPT PREVIEW | PREVISUALIZATION DU RELEVE DES NOTES', '', '50%');
	doGet(url,'contenu');
}

function printTranscript(baseUrl,eligibleCenterId){
    var studentSessionId = 0;
	var url =baseUrl+"/pv/printTranscript?studentSessionId="+studentSessionId + "&eligiblecenterId=" + eligibleCenterId;
    window.open(url);
}


$("#startdate").datetimepicker({
	widgetPositioning : {
		vertical : 'bottom'
	},
	format : 'DD/MM/YYYY',
	useCurrent : false,

});

$("#enddate").datetimepicker({
    widgetPositioning: {
        vertical: 'bottom'
    },
    format: 'DD/MM/YYYY',
    useCurrent: false,

});

function printStudentSessionFile(studentSessionId,countFile){
//	alert(studentSessionId);
//	alert($("#baseUrl").val() +"/downloadFile/"+studentSessionId+"/"+countFile);
	var url =$("#baseUrl").val() +"/downloadFile/"+studentSessionId+"/"+countFile;
    window.open(url);
}


//TODO
//REDO THIS FUNCTION TO FOLLOW STANDARD
function generateInSlip(url){
	displayLoading("kt_content");
	removeLoading("kt_content");
	var inSlipType = $("#inslip-type").val();
	url+= "?inSlipType=" + inSlipType;
	
	swal.fire({
        title: 'Are you sure you want to generate the corresponding Inslip from this PV ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Generate InSlip!'
    }).then(function(result) {
        if (result.value) {
        	
        	var baseUrlOfController=url;
        	if(getBaseUrl(baseUrlOfController) != null ){
        	//	openNav('Upload Student Documents', '', '67%');
        		sendGet(getBaseUrl(baseUrlOfController)).then(result=>{
        			console.log(result);
       		//	 removeLoading("add_candidate_form");
        			removeLoading("kt_content");
    			 if (result.indexOf("inslip.exists") > -1) {
    		   	    	swal.fire(
    			                'This inslip already exist in the system!',
    			                'Cet bordereau existe dans le system!.',
    			                'error'
    			            )
    						return;
    					} 
    			 if (result.indexOf("certificate.not.printed") > -1) {
 		   	    	swal.fire(
 			                'This inslip contains unprinted certificate!',
 			                'Cet bordereau contient des diplomes non imprime!.',
 			                'error'
 			            )
 						return;
 					}
    				  swal.fire(
    					    'Inslip Successfully created!',
    		            	'Bordereau generer avec succes',
    		            	'success'
    	               );
    		
    		removeLoading("kt_content");
    		closeNav();	 
    		
    		 }).catch(function(getFormError){
    			alert('echec ! '+getFormError);
    		});

        }

        }
    });


	
}



/** function is to validate all user-inputs field in INSLIP */

//var inSlipType = $("#inslip-type").val();

function inSlipBootstrapValidation(url){
	
	var inSlipType = document.getElementById("inslip-type");
	var validatePositionZERO =  inSlipType.options[inSlipType.selectedIndex].value;
			if(validatePositionZERO==0 || validatePositionZERO =="") {
			    //alert("Please select an Option");
			    document.getElementById('error-inslip-type').innerHTML = " Required field / Champ requis *";
			    return;
			}
	
	
	$("#eligible-inslip_FORMID")
		.bootstrapValidator({
				fields : {
					/*eligible-center : {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a moduleCompleteName'
    						}

    					}
    				},
    				office: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  moduleMinNote'
    						}
    					}
    				},
    				moduleClassification: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid moduleClassification '
    						}
    					}
    				},*/
					inSlipType: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis'
    						}
    					}
    				},
    			}
    	});

	//$("#submitINSLIPBUTTONID").on('click', function()
	//{
	//alert("validator");
		var validator = $("#eligible-inslip_FORMID").data('bootstrapValidator').validate();
		if (validator.isValid()) {
			//alert("isValid");
			 generateInSlip(url);
			 //alert("ok");
		}else{
			//alert("isNotValid");
		    swal.fire(
		    		'Form validation error',
		    		'Please fill in all the fields !.',
		    		'error'
		        )
			return;   	
		}
	//});
}
function print_Admitted_pdf(){
	if(document.getElementById("session-date").value == "0"){
    	swal.fire(
                'Not Allowed!',
                'Please Select an exam session | Svp Selectionnez une session d examen',
                'error'
            )
		return false;
	}
    if(document.getElementById("exam-center-select").value == "0"){
    	swal.fire(
                'Not Allowed!',
                'Please Select an exam center | Svp Selectionnez un centre d examen',
                'error'
            )
		return false;
    }
    if(document.getElementById("training-center").value == "0"){
    	swal.fire(
                'Not Allowed!',
                'Please Select a training center | Svp Selectionnez un centre de formation',
                'error'
            )
		return false;
	}
	var url = $("#baseUrl").val() + "/student/printAdmittedPdfList";
	var session= document.querySelector('#session-date').value;
	var trainingCenter= document.querySelector('#training-center').value;
	var examCenter= document.getElementById("exam-center-select").value;
	var params =  "?sessionId=" + session+"&trainingCenterId="+trainingCenter+"&examCenterId="+examCenter ;
	url+=params;
    window.open(url);
}


