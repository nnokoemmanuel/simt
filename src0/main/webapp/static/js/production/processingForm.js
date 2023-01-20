/*
 * this file validate every step of the form and activate button previous if necessary
 */

function validateStepOne(){
	$("#step-1")
	.bootstrapValidator(
		{

			fields : {
				surName : {
					validators : {
						notEmpty : {
							message : 'Please enter applicant name '
						},

					}
				},
				
				pob : {
					validators : {
						notEmpty : {
							message : 'Please enter applicant place of birth'
						},


					}
				},

                dob : {
					validators : {
						notEmpty : {
							message : 'Please enter applicant date of birth'
						},


					}
				},
				
				
				phoneNumber : {
					validators : {
						notEmpty : {
							message : 'Please enter applicant phone number '
						},
						

					}
				},
				
				gender : {
					validators : {
						notEmpty : {
							message : 'Please enter applicant gender '
						},
						

					}
				},
				
				nationality : {
					validators : {
						notEmpty : {
							message : 'Please enter applicant nationality '
						},
						

					}
				},
				
				language : {
					validators : {
						notEmpty : {
							message : 'Please enter applicant language '
						},
						

					}
				},
				
			}

		});
	var validator = $("#step-1").data('bootstrapValidator').validate();
	if (validator.isValid()) {
		var age = calculAge($("#dob").val());
		/*if($("#dob").val() == "" || $("#dob").val() == null || $("#dob").val() == "dd/mm/yyyy" || $("#dob").val() == undefined || $("#dob").val().split('/').length < 3 ){

			swal.fire(
	                'warning !',
	                'PLease Enter a valid Date of birth !.',
	                'error'
	            );
			return;
		}*/
        if( age < 18){
                swal.fire(
		                'Age Not Eligible!',
		                'Age Must Be At least 18 years old !.',
		                'error'
		            )
				return;
			}
	

		//validate PHOTO field
		if(document.getElementById("photoInput").files[0] == undefined || document.getElementById("photoInput").files[0] == null ){
			//add js code to link div photo to input file
			if(document.getElementById("photoHolder").getAttribute("recordFromCandidateSession")==0){
				swal.fire(
		                'warning !',
		                'PLease Upload The applicant photo !.',
		                'error'
		            );
			   return;
			}else{
				if(document.getElementById("signatureHolder").getAttribute("recordFromCandidateSession")==1){
					openStepTwo();
				}
			}
			
		}
		
		//validate SIGNATURE field
		if(document.getElementById("signatureInput").files[0] == undefined || document.getElementById("signatureInput").files[0] == null ){
			
			
			if(document.getElementById("signatureHolder").getAttribute("recordFromCandidateSession")==0){
				swal.fire(
		                'warning !',
		                'PLease Upload The applicant signatureInput !.',
		                'error'
		            );
			   return;
			}
		}
		
		openStepTwo();

	}else{
		    var errors = $('.has-error');
	        $('html, body').animate({ scrollTop: errors.offset().top - 50 }, 500); 
		    return;
	}
}

function calculAge(strDate) {
    strDate = strDate.split('/');
    var birthMonth = strDate[1]-1, // (les mois commencent à 0)
        birthDay = strDate[0],
        now = new Date(),
        nowMonth = now.getMonth(),
        nowDay = now.getDate(),
        age = now.getFullYear()-strDate[2];

    // Si la date d'anniversaire n'est pas encore passée, on corrige l'age
    if(nowMonth<birthMonth || nowMonth==birthMonth && nowDay<birthDay) {
        age--;
    }
    return age;
}

function openStepTwo(){
	var iconeStepOne = document.getElementById('icone-step-1');
	var iconeStepTwo = document.getElementById('icone-step-2');
	var stepOne = document.getElementById('step-1');
	var stepTwo = document.getElementById('step-2');
	// set icones steps
	iconeStepOne.setAttribute("data-ktwizard-state", "done");
	iconeStepTwo.setAttribute("data-ktwizard-state", "current");
	// set step wizards
	stepOne.setAttribute("data-ktwizard-state", "done");
	stepTwo.setAttribute("data-ktwizard-state", "current");
	document.getElementById('step-1-action').style.display="none";
	document.getElementById('step-2-action').style.display="block";

	document.getElementById('prev-step-2').style.display="block";
}

function validateStepTwo(){
	$("#step-2")
	.bootstrapValidator(
		{

			fields : {
			
				slipRef : {
					validators : {
						notEmpty : {
							message : 'Please enter application  in slip reference number'
						},
						

					}
				},
				
				
				applicationNumber : {
					validators : {
						notEmpty : {
							message : 'Please enter application number '
						},
						

					}
				},
				
				authority : {
					validators : {
						notEmpty : {
							message : 'Please select an authority '
						},
						

					}
				},
				
				formNumber : {
					validators : {
						notEmpty : {
							message : 'Please enter application form serial number'
						},
						

					}
				},
				
				
			}

		});

	var validator = $("#step-2").data('bootstrapValidator').validate();
	if (validator.isValid()) {
		//validate in slip number // check if exists
		var baseUrlOfController="/simt/manageFile/processForm/checkApplication";
		if(getBaseUrl(baseUrlOfController) != null ){

		    var step2Data = new FormData();
        	step2Data.append("inslipRefEntered",$("#slipRef").val().trim());
        	step2Data.append("appNumEntered",$("#applicationNumber").val().trim());
        	step2Data.append("formSerialNumberEntered",$("#formNumber").val().trim());
        	step2Data.append("appId",0);
          doPost(getBaseUrl(baseUrlOfController),step2Data,"kt_content").then(function(responseSuccess){

                  			//start response validate
                  			if( responseSuccess == "ok"){
                                //we proceed to the next step
                                removeLoading("kt_content");
                                openStepThree();
                  			}
                  			if( responseSuccess == "inslip.not.found"){

                  			    removeLoading("kt_content");
                  				swal.fire(
                  		                'Not Allowed!',
                  		                'The reference number entered does not correspond to any in slip of the system .',
                  		                'error'
                  		            );
                  				return;
                  			}
                  			if( responseSuccess == "inslip.application.number.already.used"){

                  			    removeLoading("kt_content");
                  				swal.fire(
                  		                'Not Allowed!',
                  		                'this application number is already used.',
                  		                'error'
                  		            );
                  				return;
                  			}

                  			if( responseSuccess == "application.number.not.include.for.this.in.slip"){

                  			                               removeLoading("kt_content");
                                              				swal.fire(
                                              		                'Not Allowed!',
                                              		                'this application number is not available for this in slip .',
                                              		                'error'
                                              		            );
                                              				return;
                             }

                             if( responseSuccess == "please.reentered.old.serial.number"){

                                                             removeLoading("kt_content");
                                                             swal.fire(
                                                                      'Not Allowed!',
                                                                      'this application has been rejected please reenter his old file number  .',
                                                                      'error'
                                                             );
                                                             return;
                             }

                             if( responseSuccess == "form.serial.number.already.used"){

                                                              removeLoading("kt_content");
                                                                                          swal.fire(
                                                                                                   'Not Allowed!',
                                                                                                   'this form serial number is already used  .',
                                                                                                   'error'
                                                                                          );
                                                                                          return;
                                                          }
                             
                             
                             if( responseSuccess.indexOf("you.can.not.process.in.slips.of.office@")>-1){
 
                   			    removeLoading("kt_content");
                   			    var officeName=responseSuccess.split('@')[1]
                   				swal.fire(
                   		                'Not Allowed!',
                   		                'you can not register an application inside an  in slip of the office '+officeName,
                   		                'error'
                   		            );
                   				return;
                   			}

                  			//end response validate
                  		}
                  		).catch(function(responseError){
                  			alert('echec ! '+responseError);
                  		});
		
	     }
		
		
		


	}else{
		    var errors = $('.has-error');
	        $('html, body').animate({ scrollTop: errors.offset().top - 50 }, 500); 
		    return;
	}
}

function openStepThree(){
	
	var iconeStepTwo = document.getElementById('icone-step-2');
	var iconeStepThree = document.getElementById('icone-step-3');
	
	var stepTwo = document.getElementById('step-2');
	var stepThree = document.getElementById('step-3');
	// set icones steps
	iconeStepTwo.setAttribute("data-ktwizard-state", "done");
	iconeStepThree.setAttribute("data-ktwizard-state", "current");
	// set step wizards
	stepTwo.setAttribute("data-ktwizard-state", "done");
	stepThree.setAttribute("data-ktwizard-state", "current");
	document.getElementById('step-2-action').style.display="none";
    document.getElementById('step-3-action').style.display="block";

    document.getElementById('prev-step-3').style.display="block";

}

function validateStepThree(){
	openStepFour();

}

function openStepFour(){
	var iconeStepThree = document.getElementById('icone-step-3');
	var iconeStepFour = document.getElementById('icone-step-4');
	var stepThree = document.getElementById('step-3');
	var stepFour = document.getElementById('step-4');
	// set icones steps
	iconeStepThree.setAttribute("data-ktwizard-state", "done");
	iconeStepFour.setAttribute("data-ktwizard-state", "current");
	// set step wizards
	stepThree.setAttribute("data-ktwizard-state", "done");

	stepFour.setAttribute("data-ktwizard-state", "current");
	document.getElementById('step-3-action').style.display="none";
	document.getElementById('step-4-action').style.display="block";

	document.getElementById('prev-step-4').style.display="block";
}
function validateStepFour(){
	openStepFive();

}

function openStepFive(){
	var iconeStepFour = document.getElementById('icone-step-4');
	var iconeStepFive = document.getElementById('icone-step-5');
	var stepFour = document.getElementById('step-4');
	var stepFive = document.getElementById('step-5');
	// set icones steps
	iconeStepFour.setAttribute("data-ktwizard-state", "done");
	iconeStepFive.setAttribute("data-ktwizard-state", "current");
	// set step wizards
	stepFour.setAttribute("data-ktwizard-state", "done");

	stepFive.setAttribute("data-ktwizard-state", "current");
	document.getElementById('step-4-action').style.display="none";
	document.getElementById('step-5-action').style.display="block";

    fillFormReview();

}

function fillFormReview(){

  // transfert uploaded signatures and photo in step 5
 	document.getElementById('block-review-photo').innerHTML  = document.getElementById('block-photo').innerHTML ;
 	document.getElementById('block-review-signature').innerHTML  = document.getElementById('block-signature').innerHTML ;

 	document.getElementById('enteredSurName').innerText  = document.getElementById('surName').value ;
 	document.getElementById('enteredGivenName').innerText  = document.getElementById('givenName').value ;
 	document.getElementById('enteredDob').innerText  = document.getElementById('dob').value ;
 	document.getElementById('enteredPob').innerText  = document.getElementById('pob').value ;
 	document.getElementById('enteredNationality').innerText  =   $('#nationality option:selected').text();
 	document.getElementById('enteredLanguage').innerText  =  $('#language option:selected').text();
 	document.getElementById('enteredPhoneNumber').innerText  = document.getElementById('phoneNumber').value ;
 	document.getElementById('enteredGender').innerText  =  $('#gender option:selected').text();


    document.getElementById('enteredSlipNumber').innerText  = document.getElementById('slipRef').value ;
    document.getElementById('enteredAuthority').innerText  =  $('#authority option:selected').text(); ;
    document.getElementById('enteredFormSerialNumber').innerText  = document.getElementById('formNumber').value ;
    document.getElementById('enteredApplicationNumber').innerText  = document.getElementById('applicationNumber').value ;

    document.getElementById('capacity-details-review').innerHTML  = document.getElementById('capacity-details').innerHTML ;
   // document.getElementById('attachments-details-review').innerHTML  = document.getElementById('filesContainer').innerHTML ;
    document.getElementById('filesContainer-preview').innerHTML="";
  
    if(document.getElementById("filesContainer").getAttribute("recordFromCandidateSession") == 0){
    	autoConcatenateUIFilesForApplicationFormPreviewsAttachments();
    	setAttachmentsPreview() ;
    }else{
    	//do preview if possible
    	//filesContainer-preview
    	document.getElementById('filesContainer-preview').innerHTML=document.getElementById("filesContainer").innerHTML;
    }

    document.getElementById('submit-application').style.display="block";
    document.getElementById('prev-step-5').style.display="block";

}

function reopenStepOne(){

    var iconeStepOne = document.getElementById('icone-step-1');
	var iconeStepTwo = document.getElementById('icone-step-2');
	var stepOne = document.getElementById('step-1');
	var stepTwo = document.getElementById('step-2');
	// set icones steps
	iconeStepOne.setAttribute("data-ktwizard-state", "current");
	iconeStepTwo.setAttribute("data-ktwizard-state", "pending");
	// set step wizards
	stepOne.setAttribute("data-ktwizard-state", "current");

	stepTwo.setAttribute("data-ktwizard-state", "pending");
	document.getElementById('step-1-action').style.display="block";
	document.getElementById('step-2-action').style.display="none";

}

function reopenStepTwo(){
	var iconeStepTwo = document.getElementById('icone-step-2');
	var iconeStepThree = document.getElementById('icone-step-3');

	var stepTwo = document.getElementById('step-2');
	var stepThree = document.getElementById('step-3');
	// set icones steps
	iconeStepTwo.setAttribute("data-ktwizard-state", "current");
	iconeStepThree.setAttribute("data-ktwizard-state", "pending");
	// set step wizards
	stepTwo.setAttribute("data-ktwizard-state", "current");

	stepThree.setAttribute("data-ktwizard-state", "pending");
	document.getElementById('step-2-action').style.display="block";
	document.getElementById('step-3-action').style.display="none";

}

function reopenStepThree(){

	var iconeStepThree = document.getElementById('icone-step-3');
	var iconeStepFour = document.getElementById('icone-step-4');


	var stepThree = document.getElementById('step-3');
	var stepFour = document.getElementById('step-4');
	// set icones steps
	iconeStepThree.setAttribute("data-ktwizard-state", "current");
	iconeStepFour.setAttribute("data-ktwizard-state", "pending");
	// set step wizards
	stepThree.setAttribute("data-ktwizard-state", "current");

	stepFour.setAttribute("data-ktwizard-state", "pending");
	document.getElementById('step-3-action').style.display="block";
	document.getElementById('step-4-action').style.display="none";

}


function reopenStepThree(){

	var iconeStepThree = document.getElementById('icone-step-3');
	var iconeStepFour = document.getElementById('icone-step-4');


	var stepThree = document.getElementById('step-3');
	var stepFour = document.getElementById('step-4');
	// set icones steps
	iconeStepThree.setAttribute("data-ktwizard-state", "current");
	iconeStepFour.setAttribute("data-ktwizard-state", "pending");
	// set step wizards
	stepThree.setAttribute("data-ktwizard-state", "current");

	stepFour.setAttribute("data-ktwizard-state", "pending");
	document.getElementById('step-3-action').style.display="block";
	document.getElementById('step-4-action').style.display="none";

}

function reopenStepFour(){

	var iconeStepFour = document.getElementById('icone-step-4');
	var iconeStepFive = document.getElementById('icone-step-5');

	var stepFour = document.getElementById('step-4');
	var stepFive = document.getElementById('step-5');
	// set icones steps
	iconeStepFour.setAttribute("data-ktwizard-state", "current");
	iconeStepFive.setAttribute("data-ktwizard-state", "pending");
	// set step wizards
	stepFour.setAttribute("data-ktwizard-state", "current");

	stepFive.setAttribute("data-ktwizard-state", "pending");
	document.getElementById('step-4-action').style.display="block";
	document.getElementById('step-5-action').style.display="none";

}

function registerApplication(applicationId,processTypeId,sourceId,sourceEntity){
    var formData = new FormData();
  	if(applicationId !== 0){
  		//alert("archiveId is "+archiveId);
  		formData.append("id",applicationId);
  	}
  	formData.append("authority",$("#authority").val());
  	formData.append("processtype",processTypeId);
  	formData.append("inslipReference",$("#slipRef").val().trim());
  	formData.append("applicationNumber",$("#applicationNumber").val().trim());
    formData.append("formSerialNumber",$("#formNumber").val().trim());
    formData.append("sourceId",sourceId);
    formData.append("applicationStatus",1);
    formData.append("applicantPhoneNumber",$("#phoneNumber").val().trim());
    formData.append("sourceEntity",sourceEntity);
    var applicationForm=formData;
  	var baseUrlOfController="/simt/manageFile/save";

  	if(getBaseUrl(baseUrlOfController) != null ){
  	doPost(getBaseUrl(baseUrlOfController),applicationForm,"kt_content").then(function(responseSuccess){
  		persistPhotoOfApplication(responseSuccess,applicationId);
  		persistSignatureOfApplication(responseSuccess,applicationId);
  		 // first input file check
		var idFirstFileInput = "";
		if(document.getElementById("fileInput")){
		idFirstFileInput="fileInput";
		}else{
		if(document.getElementById("fileInput-0")){
			idFirstFileInput="fileInput-0";
		}
		}
		
  		if(document.querySelector("#"+idFirstFileInput).files[0] != undefined || document.querySelector("#"+idFirstFileInput).files[0] != null ){
			var numberOfFilesOnView = document.getElementsByClassName('kt-avatar__input_file').length;

			if(numberOfFilesOnView == 1 && (document.getElementById("fileInput").files[0] != undefined || document.getElementById("fileInput").files[0] != null)){
				persistUnikApplicationFile(responseSuccess,applicationId);
			}else if(numberOfFilesOnView > 1){
				persistApplicationFiles(responseSuccess,applicationId);	
			}
			
		}else{
			 // the user should upload at least one document take him back to step 4
			if(document.querySelector("#filesContainer").getAttribute("recordFromCandidateSession")==0){
			  //cas normal ou on ne prend pas les fichiers provenant de la candidate session registration
				 swal.fire(
			                'Not Allowed !',
			                'Please upload at least one Backing documents !.',
			                'error'
			            );
			     
				 openStepFour();
			}else{
				//success 
				if(document.querySelector("#filesContainer").getAttribute("recordFromCandidateSession")==1){
					swal.fire(
			                'DONE !',
			                'Application successfully REGISTERED !.',
			                'success'
			            );
				     var baseUrlOfControllerCreate="/simt/consult/load";
		             doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
				     active_link('home-1');
				     return false;
				}
			}	 
			
		}
  	}).catch(function(responseError){
     			alert('echec ! '+responseError);
     		});

  	}

}


/*
 * this function is used during creation / edition to persist the multiple files  uploaded 
 */


function persistApplicationFiles(responseSuccess,applicationId){
	//debut upload
	if(document.querySelector("#filesContainer").getAttribute("recordFromCandidateSession")==0){
	var listElementDivClassktAvatarInputFile = document.getElementsByClassName('kt-avatar__input_file');	
	var numberOfFiles = listElementDivClassktAvatarInputFile.length;
	var i=0;
	while(i<numberOfFiles ){
		var idFileField="fileInput-"+i;
		if(document.querySelector("#"+idFileField) != undefined && document.querySelector("#"+idFileField) && document.querySelector("#"+idFileField).files && document.querySelector("#"+idFileField).files[0]){	
		var numberOfTheFile=i+1;
		if(i < numberOfFiles){
			uploadFile(responseSuccess,numberOfTheFile,"application" ,idFileField ).then(
				function(responseFileSuccess){
					if(responseFileSuccess.indexOf("ok.file.uploaded")>-1){
						var fileActualNumber =responseFileSuccess.split("ok.file.uploaded-")[1];
						//start operation at last file
						if(fileActualNumber == numberOfFiles){
							removeLoading("kt_content");
							
							if(applicationId === 0){
								//we redirect to creation page 
								swal.fire(
						                'DONE !',
						                'Application successfully REGISTERED !.',
						                'success'
						            );
								 var baseUrlOfControllerCreate="/simt/consult/load";
					             doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
							 active_link('home-1');
							 return false;
							}else{
								//case edition
								/*
								// we redirect to list page
								swal.fire(
						                'DONE !',
						                'Archive success fully Edited !.',
						                'success'
						            )
								var baseUrlOfControllerList="/simt/archive/list";
								doGet(getBaseUrl(baseUrlOfControllerList),'kt_content');
								active_link('archive-capacity');
								return false;*/
							}
					 	
					    }
						//$("#fileCancel-"+i).click();
						//end operation at last file
					}
				}
				).catch(
				 function(responseFileError){
					alert('echec upload File ! '+responseFileError);
				        }
						);
		        }
		//fin upload
		}
		i++;
    }
}
}


/*
 * this function is used during creation / edition to persist the file when only one file is uploaded 
 */
	
function persistUnikApplicationFile(responseSuccess,applicationId){
		//debut upload
	if(document.querySelector("#filesContainer").getAttribute("recordFromCandidateSession")==0){
		uploadFile(responseSuccess,0,"application" ,"fileInput" ).then(
				function(responseFileSuccess){
					removeLoading("kt_content");
					if(responseFileSuccess=="ok.file.uploaded"){
						
						
						if(applicationId === 0){
							//we redirect to creation page 
							swal.fire(
					                'DONE !',
					                'Application successfully REGISTERED !.',
					                'success'
					            );
							 var baseUrlOfControllerCreate="/simt/consult/load";
				             doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
                     	 active_link('home-1');
						 return false;
						}else{
							//case we edit the application
							/*
							// we redirect to list page
							 swal.fire(
						                'DONE !',
						                'Archive success fully Edited !.',
						                'success'
						            );
							var baseUrlOfControllerList="/simt/archive/list";
							doGet(getBaseUrl(baseUrlOfControllerList),'kt_content');
							active_link('archive-capacity');
							return false;*/
						}
					
					}
				}
				).catch(
				 function(responseFileError){
					alert('echec upload File ! '+responseFileError);
				        }
						);
		//fin upload
    }
	}

function persistPhotoOfApplication(responseSuccess,applicationId){

	//debut upload
	if(document.querySelector("#photoInput").files[0] != undefined && document.querySelector("#photoInput").files[0] != null){
	if(document.querySelector("#photoHolder").getAttribute("recordFromCandidateSession")==0){
		uploadFile(responseSuccess,-1,"application" ,"photoInput" ).then(
				function(responseFileSuccess){
					//nothing
				}
				).catch(
				 function(responseFileError){
					alert('echec upload File ! '+responseFileError);
				        }
						);
		//fin upload
	}else{
		var candidateSessionId=document.querySelector("#filesContainer").getAttribute("candidateSessionId");
		responseSuccess+="@"+candidateSessionId;
		uploadFile(responseSuccess,-1,"application" ,"photoInput" ).then(
				function(responseFileSuccess){
					//nothing
				}
				).catch(
				 function(responseFileError){
					alert('echec upload File ! '+responseFileError);
				        }
						);
			
	}
    }
}

function persistSignatureOfApplication(responseSuccess,applicationId){
	//debut upload
	if(document.querySelector("#signatureInput").files[0] != undefined && document.querySelector("#signatureInput").files[0] != null){
	if(document.querySelector("#signatureHolder").getAttribute("recordFromCandidateSession")==0){
		uploadFile(responseSuccess,-2,"application" ,"signatureInput" ).then(
				function(responseFileSuccess){
	                 //nothing
				}
				).catch(
				 function(responseFileError){
					alert('echec upload File ! '+responseFileError);
				        }
						);
	}else{
		if(document.querySelector("#signatureHolder").getAttribute("recordFromCandidateSession")==1){
			var candidateSessionId=document.querySelector("#filesContainer").getAttribute("candidateSessionId");
			responseSuccess+="@"+candidateSessionId;
			uploadFile(responseSuccess,-2,"application" ,"signatureInput" ).then(
					function(responseFileSuccess){
						//nothing
					}
					).catch(
					 function(responseFileError){
						alert('echec upload File ! '+responseFileError);
					        }
							);
	 }
	}
	}
}

function setAttachmentsPreview() {
	var numOFiles = document.getElementsByClassName('col-xl-3 file upload-block-margins-application-block').length;
	var numOFilesPreview = document.getElementsByClassName('pdf-canvas preview').length;
	numOFiles=numOFiles-numOFilesPreview;
	if(numOFiles==1){
		//un  fichier uploadé
		if(document.getElementById('pdf-canvas') != undefined){
			var sourceCanvas = document.getElementById('pdf-canvas');
			var destinationCanvas = document.getElementById("pdf-canvas-preview-0");
		    var context = destinationCanvas.getContext('2d');

		    //set dimensions
		    destinationCanvas.width = sourceCanvas.width;
		    destinationCanvas.height = sourceCanvas.height;

		    //apply the old canvas to the new one
		    context.drawImage(sourceCanvas, 0, 0);
		    destinationCanvas.style.display='block';
		}
		if(document.getElementById('pdf-canvas-0') != undefined){
			var sourceCanvas = document.getElementById('pdf-canvas');
			var destinationCanvas = document.getElementById("pdf-canvas-preview-0");
		    var context = destinationCanvas.getContext('2d');

		    //set dimensions
		    destinationCanvas.width = sourceCanvas.width;
		    destinationCanvas.height = sourceCanvas.height;

		    //apply the old canvas to the new one
		    context.drawImage(sourceCanvas, 0, 0);
		    destinationCanvas.style.display='block';
		}
	}else{
		//plusieurs fichiers uploadés
		for(var i=0;i< numOFiles;i++){
			if(document.getElementById('pdf-canvas-'+i) != undefined && document.getElementById("pdf-canvas-preview-"+i) != undefined ){
				var sourceCanvas = document.getElementById('pdf-canvas-'+i);
				var destinationCanvas = document.getElementById("pdf-canvas-preview-"+i);
			    var context = destinationCanvas.getContext('2d');

			    //set dimensions
			    destinationCanvas.width = sourceCanvas.width;
			    destinationCanvas.height = sourceCanvas.height;

			    //apply the old canvas to the new one
			    context.drawImage(sourceCanvas, 0, 0);
			    destinationCanvas.style.display='block';
				
			}
			
		}
	}

}

function editValidateStepTwo(){
	$("#step-2")
	.bootstrapValidator(
		{

			fields : {
			
				slipRef : {
					validators : {
						notEmpty : {
							message : 'Please enter application  in slip reference number'
						},
						

					}
				},
				
				
				applicationNumber : {
					validators : {
						notEmpty : {
							message : 'Please enter application number '
						},
						

					}
				},
				
				authority : {
					validators : {
						notEmpty : {
							message : 'Please select an authority '
						},
						

					}
				},
				
				formNumber : {
					validators : {
						notEmpty : {
							message : 'Please enter application form serial number'
						},
						

					}
				},
				
				
			}

		});

	var validator = $("#step-2").data('bootstrapValidator').validate();
	if (validator.isValid()) {
		//validate in slip number // check if exists


		    var step2Data = new FormData();
        	step2Data.append("inslipRefEntered",$("#slipRef").val().trim());
        	step2Data.append("appNumEntered",$("#applicationNumber").val().trim());
        	step2Data.append("formSerialNumberEntered",$("#formNumber").val().trim());
        	step2Data.append("appId",0);
          //doPost(getBaseUrl(baseUrlOfController),step2Data,"kt_content").then(function(responseSuccess){

                  			//start response validate
              openStepThree();
                  		
                  		
                  			                            
                           

                  			//end response validate
                  		
                  	
		
	     }	
	
}

function editApplication(applicationId){

    var formData = new FormData();    
  	formData.append("authority",$("#authority").val());
    formData.append("formSerialNumber",$("#formSerialNumber").val().trim());
    formData.append("nationality",$("#nationality").val().trim());
    formData.append("applicantPhoneNumber",$("#phoneNumber").val().trim());
    formData.append("language",$("#language").val());
    
    
    if ($("#authority").val()==null){
    	alert("Authority field must have a valid value");
    	return;
    }
    var applicationForm=formData;
  	var baseUrlOfController="/simt/manageFile/editApplication/" + applicationId;
  	if(getBaseUrl(baseUrlOfController) != null ){
  	doPost(getBaseUrl(baseUrlOfController),applicationForm,"kt_content").then(function(responseSuccess){
  		
  	
  		if(responseSuccess == "done"){
  			swal.fire(
	                'ACTION COMPLETE !',
	                'Application successfully UPDATED !.',
	                'success'
	            );
  			closeNav();
  		}else{
  			
  		}
  		
  		
  		
//  		persistPhotoOfApplication(responseSuccess,applicationId);
//  		persistSignatureOfApplication(responseSuccess,applicationId);
  		 // first input file check
//		var idFirstFileInput = "";
//		if(document.getElementById("fileInput")){
//		idFirstFileInput="fileInput";
//		}else{
//		if(document.getElementById("fileInput-0")){
//			idFirstFileInput="fileInput-0";
//		}
//		}
//		
//  		if(document.querySelector("#"+idFirstFileInput).files[0] != undefined || document.querySelector("#"+idFirstFileInput).files[0] != null ){
//			var numberOfFilesOnView = document.getElementsByClassName('kt-avatar__input_file').length;
//			alert("Number of files on view: " + numberOfFilesOnView);
//			
//			if(numberOfFilesOnView == 1 && (document.getElementById("fileInput").files[0] != undefined || document.getElementById("fileInput").files[0] != null)){
//				persistUnikApplicationFile(responseSuccess,applicationId);
//			}else if(numberOfFilesOnView > 1){
//				persistApplicationFiles(responseSuccess,applicationId);	
//			}
//			
//		}else{
//			 // the user should upload at least one document take him back to step 4
//			console.log(document.querySelector("#filesContainer").getAttribute("recordFromCandidateSession"));
//			alert("let's die here");
//			if(document.querySelector("#filesContainer").getAttribute("recordFromCandidateSession")==0){
//			  //cas normal ou on ne prend pas les fichiers provenant de la candidate session registration
//				 swal.fire(
//			                'Not Allowed !',
//			                'Please upload at least one Backing documents !.',
//			                'error'
//			            );
//			     
//			}else{
//				//success 
//				if(document.querySelector("#filesContainer").getAttribute("recordFromCandidateSession")==1){
//					swal.fire(
//			                'DONE !',
//			                'Application successfully REGISTERED !.',
//			                'success'
//			            );
//				     var baseUrlOfControllerCreate="/simt/read/?searchTerm=-1";
//		             doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
//				     active_link('home-1');
//				     return false;
//				}
//			}	 
//			
//		}
  	}).catch(function(responseError){
     			alert('echec ! '+responseError);
     		});

  	}

}


