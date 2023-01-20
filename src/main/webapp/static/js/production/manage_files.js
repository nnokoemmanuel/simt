function application_action(value,id,entity,processTypeId,studentSessionId,eligibleCenterId){
	switch(value){
	case "action":
		return;
	case "detail":
		var url = $("#baseUrl").val() + "/manageFile/detail/" + id;
		openNav('APPLICATION DETAILS / DETAILS APPLICATION', '', '100%');
		doGet(url,'contenu');
		break;
	case "edit":		
		var url = $("#baseUrl").val() +"/manageFile/manage/edit/" + id;
		openNav('Edit Application / Editez la demande', '', '90%');
		doGet(url, "contenu");
		break;	
	case "history":
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		openNav('APPLICATION TRACKING / HISTORIQUE APPLICATION', '', '50%');
		doGet(url,'contenu');		
		break;
	case "reject":
		rejectApplication(id);
		break;
	case "cancel":
		cancelApplication(id);
		break;
	case "reset":
		resetApplication(id);
		break;
	case "confirm":
		var url = $("#baseUrl").val() + "/manageFile/detail/confirm/" + id;
		openNav('APPLICATION DETAILS / DETAILS APPLICATION', '', '90%');
		doGet(url,'contenu');
		break;
	case "confirm_reject":
		confirmReject(id);
		break;
	case "print_report":
		printReport(id);
		break;
	case "request_reprint":
		requestReprintApplication(id);
		break;
	case "grant_reprint":
		grantApplication(id);
		break;
	case "deliver_with_reject":
		deliverReject(id);
		break;
	case "print_preview":

		if(processTypeId==1 || processTypeId==4 ){
		    var eligibleCenterId = 0;
            var url =$("#baseUrl").val()+"/pv/printTranscriptPreviews?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId + "&id=" + id;
               openNav(' TRANSCRIPT PREVIEW / PREVISUALIZATION DU RELEVE DES NOTES ', '', '50%');
               doGet(url,'contenu');
		}else if(processTypeId==3 || processTypeId==6 ){
		    var token = "preview";
            var url;
            url = $("#baseUrl").val() + "/manageFile/productionPrintPreviewProfessionalCard?applicationId="+id + "&token="+token;
                openNav('PRINT PREVIEW OF PROFESSIONAL CARD  | PREVISUALIZATION DU CARTE PROFESSIONELLE', '', '70%');
                doGet(url,'contenu');

		}else if(processTypeId==2 || processTypeId==5 ){
				var eligibleCenterId = 0;
                openNav('CERTIFICATE PREVIEW / PREVISUALISATION DU DIPLOME ', '', '50%');
                var url =$("#baseUrl").val()+"/pv/productionPrintPvCertificatePreview?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId + "&id=" + id;

                doGet(url,'contenu');

            }
		break;	
	default:
	   return;
	}
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

function searchButtonApplicationList() {
     let  dateRange=document.getElementById("dateRangeId").value;
          dateRange =dateRange.split("-");
          [startDate, endDate]=dateRange;
	var status= document.getElementById("status").value;
    var office= document.getElementById("office").value;
    var processType= document.getElementById("processType").value;
    var inSlipReference= document.getElementById("referenceNum").value;
    var fileNumber= document.getElementById("fileNumber").value;
    if (startDate =="" &&  endDate ==undefined && fileNumber == ""  ){
                swal.fire(
                     'Merci de Renseigner les champs de recherche ...!',
                     'ok !'
                )
                return;
   }else{
		var url = $("#baseUrl").val() + "/manageFile/listeSearchApplication?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&office=" + office + "&inSlipReference=" + inSlipReference + "&processType=" + processType + "&fileNumber=" + fileNumber+"";
		doGet(url,'content');
   }
}


function rejectApplication(applicationId) {
	var url = $("#baseUrl").val() +"/manageFile/manage/reject/" + applicationId;
	openNav('Enter A Reason / Entrez La Raison', '', '50%');
	doGet(url, "contenu");
}


function requestReprintApplication(applicationId) {
	var url = $("#baseUrl").val() +"/manageFile/manage/request_reprint/" + applicationId;
	openNav('Enter A Reason / Entrez La Raison', '', '50%');
	doGet(url, "contenu");
}

function printReport(applicationId){
	var url = $("#baseUrl").val() +"/manageFile/manage/print_report/" + applicationId;
	openNav('Make A Print Report / Faire Un Rapport D impression', '', '50%');
	doGet(url, "contenu");
}

function confirmApplication(applicationId) {
	swal.fire({
        title: 'Are you sure you want to confirm this application?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Confirm Application!'
    }).then(function(result) {
        if (result.value) {
        	
        	var baseUrlOfController=$("#baseUrl").val() + "/manageFile/manage/confirm/" + applicationId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for reset
        			if( responseSuccess == "ok"){
        				var myModal = document.getElementById("myModal");
        				myModal.style.display = "none";
        				refreshCurrentRow(applicationId);
                            swal.fire(
                                 'Application Confirmed Successfully !',
                                 'Your Application has been confirmed.',
                                 'success'
                                )
                            
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to perform this action on this application.',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'Action not Allowed on application.',
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

function cancelApplication(applicationId) {
	swal.fire({
        title: 'Are you sure you want to cancel processing on this application?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Cancel Application!'
    }).then(function(result) {
        if (result.value) {
        	
        	var baseUrlOfController=$("#baseUrl").val() + "/manageFile/manage/cancel/" + applicationId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for reset
        			if( responseSuccess == "ok"){
        				refreshCurrentRow(applicationId);
                            swal.fire(
                                 'Application Cancelled Successfully !',
                                 'Your Application has been cancelled.',
                                 'success'
                                )
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to perform this action on this application  .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'Action not Allowed on application.',
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
    });$("#baseUrl").val()
}

function resetApplication(applicationId) {
	swal.fire({
        title: 'Are you sure you want to reset this application?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Reset Application!'
    }).then(function(result) {
        if (result.value) {
        	
        	var baseUrlOfController=$("#baseUrl").val() + "/manageFile/manage/reset/" + applicationId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for reset
        			if( responseSuccess == "ok"){
        				refreshCurrentRow(applicationId);
                            swal.fire(
                                 'Application Successfully Resetted!',
                                 'Your Application has been resetted.',
                                 'success'
                                )
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to perform this action on this application  .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'Action not Allowed on application.',
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

function confirmReject(applicationId){
	swal.fire({
        title: 'Are you sure you want to confirm this application as Rejected?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Confirm Reject!'
    }).then(function(result) {
        if (result.value) {
        	
        	var baseUrlOfController=$("#baseUrl").val()+ "/manageFile/manage/confirm_reject/" + applicationId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for reset
        			if( responseSuccess == "ok"){
        				refreshCurrentRow(applicationId);
                            swal.fire(
                                 'Application Successfully confirmed as Rejected!',
                                 'Your Application has been confirmed as Rejected!',
                                 'success'
                                )
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have enough rights to perform this action on this application .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'Action not Allowed on application.',
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

function grantApplication(applicationId) {
	swal.fire({
        title: 'Are you sure you want to confirm this application as Reprint?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Confirm Reprint!'
    }).then(function(result) {
        if (result.value) {
        	
        	var baseUrlOfController=$("#baseUrl").val() + "/manageFile/manage/grant_reprint/" + applicationId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for reset
        			if( responseSuccess == "ok"){
        				refreshCurrentRow(applicationId);
                            swal.fire(
                                 'Reprint Request Granted Successfully',
                                 'Your Reprint Request was successfully granted!',
                                 'success'
                                )
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have appropriate rights to perform this action on this application .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'Action not Allowed on application.',
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

function deliverReject(applicationId) {
	swal.fire({
        title: 'Are you sure you want to deliver this rejected Application?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Deliver Application!'
    }).then(function(result) {
        if (result.value) {
        	
        	var baseUrlOfController= $("#baseUrl").val() + "/manageFile/manage/deliver_with_reject/" + applicationId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for reset
        			if( responseSuccess == "ok"){
        				refreshCurrentRow(applicationId);
                            swal.fire(
                                 'Application Successfully set as Delivered with Reject',
                                 'Your application has been set as Deliver with reject!',
                                 'success'
                                )
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed!',
        		                'you do not have appropriate rights to perform this action on this application .',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed!',
        		                'Action not Allowed on application.',
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

function saveApplicationComment(id){
	var action = document.getElementById("action").value;
	var message = document.getElementById("comment").value;
	var myModal = document.getElementById("myModal");
	var baseUrlOfController = $("#baseUrl").val() + "/manageFile/manage/save/" + action + "/" + id + "/" + message;
	if(getBaseUrl(baseUrlOfController) != null ){
		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
			//start for reset
			if( responseSuccess == "ok"){
				myModal.style.display = "none";
				refreshCurrentRow(id);
                    swal.fire(
                         'Application updated Successfully ',
                         'Your application has been set as ' + action.toUpperCase() + '!',
                         'success'
                        )
                    return;

			}
			if( responseSuccess == "koBadRole"){
				swal.fire(
		                'Not Allowed!',
		                'you do not have appropriate rights to perform this action on this application .',
		                'error'
		            )
				return;
			}
			if( responseSuccess == "koBadState"){
				swal.fire(
		                'Not Allowed!',
		                'Action not Allowed on application.',
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

function saveprintReport(applicationId) {
	var action = document.getElementById("print-report").value;
	var cardNumber = document.getElementById("cardNumber").value;
	var myModal = document.getElementById("myModal");
	var baseUrlOfController = $("#baseUrl").val() + "/manageFile/manage/print/report/" +action +"/" + applicationId + "?cardNumber=" + cardNumber;
	if(getBaseUrl(baseUrlOfController) != null ){
		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
			if( responseSuccess == "ok"){
				myModal.style.display = "none";
				refreshCurrentRow(applicationId);
				if(action == "print_successful"){
					swal.fire(
	                         'Application updated Successfully ',
	                         'You have marked this application as Successfully Printed!',
	                         'success'
	                        )
				}else{
					swal.fire(
	                         'Application updated Successfully ',
	                         'You have marked this application as  Unsuccessfully Printed!',
	                         'success'
	                        )
				}
                    
                    return;

			}
			if( responseSuccess == "koBadRole"){
				swal.fire(
		                'Not Allowed!',
		                'you do not have appropriate rights to perform this action on this application .',
		                'error'
		            )
				return;
			}
			if( responseSuccess == "koBadState"){
				swal.fire(
		                'Not Allowed!',
		                'Action not Allowed on application.',
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

function refreshContent(){
	var manageFileButton = document.getElementById("search-file");
	var fileNumber =document.getElementById("fileNumber");
	if (fileNumber.value) {
		manageFileButton.onclick();
	}else{
		var datefilter =document.getElementById("datefilter");
		if(datefilter) {
			manageFileButton.onclick();
		}else{
			var manageFileLink = document.getElementById("load-file-management");
			manageFileLink.onclick();
		}
		
	}
}

function moreInformation(personId) {
	var url = $("#baseUrl").val() +"/more/" + personId;
	openNav('Détail du permis', '', '100%');
	doGet(url, "contenu");
}

function printCapacity(id) {
	var url =$("#baseUrl").val()+"/manageFile/capacity/print?id="+id ;
	window.open(url);
	closeNav();
	
}
function productionPrintTranscript(baseUrl,studentSessionId,eligiblecenterId,applicationId){
	var url =baseUrl+"/pv/printTranscript?studentSessionId="+studentSessionId + "&eligiblecenterId=" + eligiblecenterId+"&applicationId=" + applicationId;
    window.open(url);
     var myModal = document.getElementById("myModal");
             myModal.style.display = "none";
             refreshCurrentRow(applicationId);
         swal.fire(
                    'Print Completed!',
                    'Your Transcript was successfully printed.',
                    'success'
                )
         return;
}
function productionPrintProfessionalCard(applicationId, token){
	var url;
	var token= "print";
		url = $("#baseUrl").val() + "/manageFile/printPvProfessionalCard?applicationId="+applicationId + "&token="+token;
		 var span = document.getElementsByClassName("close")[0];
		 span.click();
		 window.open(url);
		 var myModal = document.getElementById("myModal");
             myModal.style.display = "none";
             refreshCurrentRow(applicationId);
		 swal.fire(
	                'Print Completed!',
	                'Your Card was successfully printed.',
	                'success'
	            )
	     return;


}
function productionPrintCertificate(studentSessionId,eligibleCenterId,applicationId){
	var url = $("#baseUrl").val() + "/pv/printPvCertificate?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId+ "&applicationId=" + applicationId;
	window.open(url);
    var myModal = document.getElementById("myModal");
             myModal.style.display = "none";
             refreshCurrentRow(applicationId);
             
		 swal.fire(
	                'Print Completed!',
	                'Your Certificate was successfully printed.',
	                'success'
	            )
	     return;
}

function refreshCurrentRow(applicationId) {
	var currentRowId = "appRow-"+ applicationId;
	var appRow = document.getElementById(currentRowId);
	var cellValue = appRow.cells[0].innerHTML;
	url= $("#baseUrl").val() + "/manageFile/listOneApplication?applicationId="+applicationId;
	 var xmlHttp     =   getXMLHttp();
	    xmlHttp.open("GET", url, false);
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	    xmlHttp.onreadystatechange = function(){
	      if(xmlHttp.status !== 200){
	      	$("#"+idResultContener).html(xmlHttp.responseText);      	
	          return;
	      }
	      
	      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){

	    	  appRow.innerHTML= xmlHttp.responseText;
	    	  appRow.cells[0].innerHTML= cellValue;

	      }else{
	    	 
	      }
	  };
	  xmlHttp.send(null);
}


