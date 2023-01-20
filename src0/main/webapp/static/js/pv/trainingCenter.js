var searchButton = document.querySelector('#search-button');
searchButton.addEventListener('click', function(){
 var url = $("#baseUrl").val();
 var speciality = $("#speciality").val();
 var divisionId = $("#divisionId").val();
 var statusId = $("#statusId").val();
 if (speciality=="ALL" && statusId == -1 && divisionId == -1) {
  	    refreshTrainingCenterPage(url,true,divisionId,statusId);
	}else{
		refreshTrainingCenterPage(url,false,divisionId,statusId);
	}
});

function manageAction(value,id,entity){
	switch(value){
	case "action":
		return;
    		
	case "history":
		openNav('Training Center History | Historique du Centre de Formation', '', '50%');
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		break;
	default:
	   return;
	}
}

function editTrainingCenter(url, trainingcenterId){
	openNav('Training Center Edition | Edition du Centre de Formation', '', '50%');
	url +=  trainingcenterId;
	doGet(url,'contenu');
	
}

function showTrainingCenter(url, trainingCenterId){
	openNav('Training Center Details | Details du Centre de Formation', '', '50%');
	url +=  trainingCenterId;
	doGet(url,'contenu');
}

function deleteTrainingCenter(url, trainingCenterId){
	swal.fire({
        title: 'Are you sure you want to delete this Training Center ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete Training Center !'
    }).then(function(result) {
        if (result.value) {
        	var baseUrlOfControllerFordeletion=url+trainingCenterId;
        	sendGet(baseUrlOfControllerFordeletion).then(function(getFormSuccess){
        		if(getFormSuccess.indexOf("OK") > -1){
        			
        			swal.fire(
	    					 'Success !',
	    					 'Training Center deleted successfuly !.',
	    					 'success'
	    			);
        			
        			url = url.split('/trainingCenter/delete/')[0];
        			var speciality = $("#speciality").val();
        			var divisionId = $("#divisionId").val();
        			var statusId = $("#statusId").val();
     		        if (speciality=="ALL") {
     		        	refreshTrainingCenterPage(url,true,divisionId,statusId);
     		    	}else{
     		    		refreshTrainingCenterPage(url,false,divisionId,statusId);
     		    	}
        			
        		}
        		if(getFormSuccess.indexOf("KO") > -1){
        			
        			swal.fire(
                            'Not Allowed!',
                            'Can not delete the Training Center there are students under it ! .',
                            'error'
                        );
        			
        			url = url.split('/trainingCenter/delete/')[0];
        			var speciality = $("#speciality").val();
        			var divisionId = $("#divisionId").val();
        			var statusId = $("#statusId").val();
     		        if (speciality=="ALL") {
     		        	refreshTrainingCenterPage(url,true,divisionId,statusId);
     		    	}else{
     		    		refreshTrainingCenterPage(url,false,divisionId,statusId);
     		    	}
                	return false;
        			
        		}
        		

        	}).catch(function(getFormError){
    			alert('echec ! '+getFormError);
    		});
        		
        
            
        }
    });
}




/** function is to validate all user-inputs field in TRAINING CENTER */

function trainingCenterBootstrapValidation(baseUrl,trainingCenterId){
	
	//alert("initialize BootstrapValidation");
	$("#trainingCenterFORMID")
		.bootstrapValidator(
				{    
	    	        
					fields : {   
						name : {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a name'
    						}

    					}
    				},
    				   founder: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  founder'
    						}
    					}
    				},
    				
    				owner: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid owner '
    						}
    					}
    				},
    			
    				ownerContact: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid owner Contact '
    						}
    					}
    				},
    				
    				location: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid location '
    						}
    					}
    				},
    				
    				maxStudent: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid max number of Student '
    						}
    					}
    				},
    				
    				speciality: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid speciality '
    						}
    					}
    				},
    				agreementAuthorisationNumber: {
                        validators : {
                            notEmpty : {
                                message : 'Required field / Champ requis :: Please enter a  valid agreement Number'
                            }
                        }
                    },
    				 fileInput: {
    					validators: {
    						 notEmpty : {
    	                         message : ' Please upload the agreement Backing Document'
    	                        },
    	                        file: {
    							extension: 'pdf',
    							type: 'application/pdf',
    							maxSize: 1024*201, //205KO
    							message: 'Invalid file: maxsize is 205k or invalid file format (pdf files needed)'
    						}
    					}
    				},
    				

    					}
    				});

		var validator = $("#trainingCenterFORMID").data('bootstrapValidator').validate();

		if (validator.isValid()) {
			createTrainingCenter(baseUrl,trainingCenterId)
		}else{
		    swal.fire(
		    		'Form validation error',
		    		'Please fill in all the fields !.',
		    		'error'
		        )
			return;   	
		}
}





function createTrainingCenter(baseUrl,trainingCenterId) {

	var originalBaseUrl = baseUrl;
	var formData = new FormData();
	if(trainingCenterId !== 0){
		formData.append("id",trainingCenterId);
	}
    if($("#divisionId").val() == 0){
    	 swal.fire(
				 'Failed !',
				 'Please Select Training Center Division ! .',
				 'error'
           );
		 return false;
    }
    
    if($("#maxStudent").val() < 0){
   	 swal.fire(
				 'Failed !',
				 'Please Enter a valid Training Center max students value ! .',
				 'error'
          );
		 return false;
   }
	formData.append("divisionId",$("#divisionId").val());
	formData.append("name",$("#name").val());
	formData.append("owner",$("#owner").val());
	formData.append("ownerContact",$("#ownerContact").val());
	formData.append("creationDate",$("#creationDate").val());
	formData.append("founder",$("#founder").val());
	formData.append("location",$("#location").val());
	formData.append("maxStudent",$("#maxStudent").val());
//	if(trainingCenterId === 0){
		formData.append("agreementAuthorisationNumber",$("#agreementAuthorisationNumber").val());
		formData.append("agreementIssuedDate",$("#agreementIssuedDate").val());
//	}
//	else{
//		formData.append("agreementAuthorisationNumber",undefined);
//		formData.append("agreementIssuedDate",undefined);
//	}
	const checkboxes=document.querySelectorAll('input[name="specialities"]');

	let specialities="";
	for(const checkbox of checkboxes){
		if(checkbox.checked)
			if(specialities=="")
				specialities=checkbox.value;
			else specialities=checkbox.value+","+specialities;
	}

	if(specialities==""){
		 swal.fire(
				 'Failed !',
				 'Please Select Training Center Specialities ! .',
				 'error'
           );
		 return false;
	}
	formData.append("specialities",specialities);

    var trainingCenterForm=formData;
	let trainingCenterName=document.querySelector("#name");

    //if(trainingCenterName.value==""||moduleSpeciality.value=="ALL") {
	if(trainingCenterName.value=="") {
    	if(trainingCenterName.value==""){
    		swal.fire(
                    'Not Allowed!',
                    'Please fill the training Center name.',
                    'error'
                );
        	return false;
    		
    	}
    	
    }
	
	displayLoading("trainingCenterFormId");
    baseUrl=baseUrl+"/trainingCenter/create" 
    doPost(baseUrl,formData,"kt_content").then(result=>{
    		 removeLoading("trainingCenterFormId");
    		
    		 if(result.indexOf("NOSPECIALITIES") > -1){
    			 swal.fire(
    					 'Failed !',
    					 'Training Center not registered ! please select a specialities .',
    					 'error'
    	           );
    			 return false;
    		 }
    		
    		 if(result.indexOf("OK") > -1){
    			closeNav();
    			var url = originalBaseUrl;
    			var speciality = $("#speciality").val();
    			var divisionId = $("#divisionId").val();
    			var statusId = $("#statusId").val();
 		        
 		        if (speciality=="ALL") {

 		        	refreshTrainingCenterPage(url,true,divisionId,statusId);
 		    	}else{

 		    		refreshTrainingCenterPage(url,false,divisionId,statusId);
 		    	}
 		      
 		       
	            if(trainingCenterId === 0){
	            	//on persiste le fichier de l agreement seulement lors de la creation du training Center
	            	if(result.indexOf("OKWITHAGREEMENT") > -1){
	            		persistUnikFile(result,result.split('OKWITHAGREEMENT-')[1]);
	            	}
	            	
	            	  swal.fire(
		    					 'Success !',
		    					 'Training Center created successfuly !.',
		    					 'success'
		    			)	;
	            	  
	             }else{
	            	 //persist unique file if it exists.
	            	 if(document.getElementById("fileInput").value!=null){
	            		 alert(result);
	            		// console.log(result);
		            		persistUnikFile(result,result.split('OKWITHAGREEMENT-')[1]);
		            	}
	            	  swal.fire(
		    					 'Success !',
		    					 'Training Center edited successfuly !.',
		    					 'success'
		    			)	;
	            	  
	             }
		    		  
    		 }else 
    			swal.fire(
    					 'Failed !',
    					 'Training Center not registered .',
    					 'error'
    	        )	; 
    		
    	 }).catch(error=>{
    		 removeLoading("trainingCenterFormId");
    		 closeNav();
    		 swal.fire(
    					 'Failed !',
    					 'Something went wrong at our end.',
    					 'error'
    	       ); 

    	 });
    	
    	
    	
  
}


function refreshTrainingCenterPage(url,isGlobalLoading,divisionId,statusId){
	if(isGlobalLoading){
	  	doGet(url+'/trainingCenter/list/ALL/0/-1','kt_content');
	  	active_link('courses-and-modules-management');
	  	return false;
	}else{
		searchTrainingCenter(url,divisionId,statusId);
	}
	
}


function searchTrainingCenter(url,divisionId,statusId){
	    var speciality = $("#speciality").val();
		var str = "";
		str += "/trainingCenter/list/" + speciality+"/"+divisionId+"/"+statusId ;
		url += str;
		doGet(url, "kt_content");
}


/*
 * this function is used during creation / edition to persist the file when only one file is uploaded 
 */
	
function persistUnikFile(responseSuccess,trainingCenterId){
		//debut upload
		uploadFile(responseSuccess,0,"trainingCenter" ,"fileInput" ).then(
					function(responseFileSuccess){
						if(responseFileSuccess=="ok.file.uploaded"){
							console.log("agreement.file.for.trainingCenter.uploaded !");
						}
					}
				).catch(
				 function(responseFileError){
					alert('echec upload File ! '+responseFileError);
				}
				);
		//fin upload
		
}


function printStudentsList(url, trainingCenterId){
	url += "?trainingCenterId=" + trainingCenterId;
    window.open(url);
	
}

function searchtrainingCenterCandidateList(trainingCenterId) {

	let  dateRange=document.getElementById("dateRangeId").value;
    dateRange =dateRange.split("-");
    [startDate, endDate]=dateRange;

	if(document.getElementById("statusStudents").value != "" || !(startDate =="" &&  endDate ==undefined)){
		if(document.getElementById("statusCandidates").value != "" || document.getElementById("eligibleCenterIdl").value != "0"){
			swal.fire('Please do not fill block 1 and 2 at the same time ! / veuillez soumettre uniquement un bloc pour la recherche!')
			return;
		}
	}

	var status= -1;
	var licenseNumber=undefined;
	var matricule=undefined;
	if(document.getElementById("statusStudents").value != "")
		 status= document.getElementById("statusStudents").value;
	if(document.getElementById("statusCandidates").value != "")
         status= document.getElementById("statusCandidates").value;
	if(document.getElementById("licenseNumber").value.trim().length == 12)
		licenseNumber= document.getElementById("licenseNumber").value;
	if(document.getElementById("licenseNumber").value.trim().length == 7)
		matricule= document.getElementById("licenseNumber").value;

    var eligibleCenterId = document.getElementById("eligibleCenterIdl").value; 

    if(document.getElementById("statusStudents").value != "" || !(startDate =="" &&  endDate ==undefined)){
    	if(document.getElementById("statusStudents").value == ""){
    		swal.fire('please select student status ! / veuillez selectionner un statut !')
			return;
    	}
    	//if block 1 is launched !
    	var url = $("#baseUrl").val() + "/trainingCenter/listeSearchCandidate?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&eligibleCenterId=" + eligibleCenterId + "&trainingCenterId=" + trainingCenterId+ "&licenseNumber=" + licenseNumber + "&matricule=" + matricule+"";
		doGet(url,'content');
		return;
    }

    if(document.getElementById("statusCandidates").value != "" || document.getElementById("eligibleCenterIdl").value != "0"){
    	//cas de recherche par bloc 2
    	if(document.getElementById("statusCandidates").value == ""){
    		swal.fire('please select student status ! / veuillez selectionner un statut !')
			return;
    	}
    	
    	//if block 2 is launched !	
    	var url = $("#baseUrl").val() + "/trainingCenter/listeSearchCandidate?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&eligibleCenterId=" + eligibleCenterId + "&trainingCenterId=" + trainingCenterId+  "&licenseNumber=" + licenseNumber + "&matricule=" + matricule+"";
		doGet(url,'content');
		
		return;
    	
    }

    if(document.getElementById("licenseNumber").value.trim().length > 0){
    	eligibleCenterId = 0;
    	status = -1;
    	var url = $("#baseUrl").val() + "/trainingCenter/listeSearchCandidate?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&eligibleCenterId=" + eligibleCenterId + "&trainingCenterId=" + trainingCenterId+  "&licenseNumber=" + licenseNumber + "&matricule=" + matricule+"";
		doGet(url,'content');
		return;
    	
    }

}



