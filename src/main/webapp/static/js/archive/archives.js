
function validEditedArchive(archiveId){
	$("#add_archive_form")
		.bootstrapValidator(
			{

				fields : {
					archiveService: {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis'
							}
						}
					},
					issuedAuthority : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},
					archiveNumber : {
						validators : {
							notEmpty : {
								message : ' Required field / Champ requis'
							}
						}
					},

					issuedCountry : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},
					issuedPlace : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},
					issuedDate : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},

					examPlace : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},
					pvNumber : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},
                    grade : {
                            validators : {
                                notEmpty : {
                                    message : '  Required field / Champ requis'
                                }
                            }
                        },
                    finalAverage : {
                            validators : {
                                notEmpty : {
                                    message : '  Required field / Champ requis'
                                }
                            }
                        },
                    examDate : {
                            validators : {
                                notEmpty : {
                                    message : '  Required field / Champ requis'
                                }
                            }
                        },
				}

			});
		var validator = $("#add_archive_form").data('bootstrapValidator').validate();
		if (validator.isValid()) {
			saveEditArchive(archiveId);
	}else{
	alert("ereur lors de la validation");
	}
}


/**
 * Verify if person is eligible for registration
 * @returns
 */
function veriffdivBlock1() {

	$("#divBlock1")
	.bootstrapValidator(
		{

			fields : {
				surName : {
						validators : {
							notEmpty : {
								message : 'Please enter a  valid name'
							}

						}
					},
					givenName : {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis'
							}
						}
					},
					gender: {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis'
							}
						}
					},
					nationality: {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis'
							}
						}
					},
					dob: {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis'
							}
						}
					},
				/*	diplome: {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis'
							}
						}
					},*/
					pob : {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis'
							}
						}
					},


					nic : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},
					occupation : {
						validators : {
							notEmpty : {
								message : '  Required field / Champ requis'
							}
						}
					},
					licenseNum : {
					    validators : {
						notEmpty : {
							message : 'Please enter a  license number'
						}

					}
				},


			}

		});
	var validator = $("#divBlock1").data('bootstrapValidator').validate();
	if (validator.isValid()) {
		//displayLoading("load-preview-step1");

		var license = document.getElementById("licenseNum").value.trim();
		var speciality = document.getElementById("speciality").value;
		//var diplome = document.getElementById("diplome").value;
		var surName = document.getElementById("surName").value;
		var givenName = document.getElementById("givenName").value;
		var dob = document.getElementById("dob").value;
		var pob = document.getElementById("pob").value;
		var gender = document.getElementById("gender").value;


            	var link = $("#baseUrl").val() + "/archive/verify/applicant/licenseInfos";
				//url = link +"?license="+license+"&speciality="+speciality+"&diplome="+ diplome+"&surName="+ surName+"&givenName="+ givenName+"&dob="+ dob+"&pob="+ pob;
				url = link +"?license="+license+"&speciality="+speciality+"&surName="+ surName+"&givenName="+ givenName+"&dob="+ dob+"&pob="+ pob;

				var xmlHttp = getXMLHttp();
				xmlHttp.open("GET", url, true);

            	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            	xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            	xmlHttp.onreadystatechange = function() {

            		if (xmlHttp.status !== 200) {
            			customAlert("Erreur interne | Internal error");
            			return;
            		}

            		if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            			//removeLoading("load-preview-step1");
            			var response = xmlHttp.responseText;
            			//var applicantDatas = JSON.parse(response);
            			if(response == "dupilcation.of.person"){
            				swal.fire(
	        		                'Person Rejected!',
	        		                'This person can not be registered... <strong> Person already in system</strong>',
	        		                'warning'
	        		            )
						 return;
						}
                        
            			if(response == "ko.catb.not.found"){
            				swal.fire(
	        		                'License has no Category B !',
	        		                'Le permis n a pas de categorie B !',
	        		                'warning'
	        		            )
						 return;
            			}
						if(response == "ko"){
            				swal.fire(
	        		                'License number do not match!',
	        		                'Wrong person informations',
	        		                'warning'
	        		            )
						 return;
            			}
						
						document.getElementById("licenseNum").readOnly = true;
						document.getElementById("speciality").readOnly = true;
						//document.getElementById("diplome").readOnly = true;
						document.getElementById("surName").readOnly = true;
						document.getElementById("givenName").readOnly = true;
						document.getElementById("dob").readOnly = true;
						document.getElementById("pob").readOnly = true;
						document.getElementById("gender").readOnly = true;
                        document.getElementById("divBlock2").style.display = "block";
                        document.getElementById("divBlock3").style.display = "block";

            			}
				};

            	xmlHttp.send(null);
            	//end code to retrieve datas from API

    }
}



/**
 * Register archive
 * @returns
 */
function registerArchive(archiveId) {
	displayLoading("kt_content");
	if($("#dob").val() == "dd/mm/yyyy" || $("#dob").val() == undefined || $("#dob").val().split('/').length < 3 ){
		removeLoading("kt_content");
		swal.fire(
                'warning !',
                'PLease Enter a valid Date of birth !.',
                'error'
            );
		 var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
         doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
		 active_link('archive-pv');
		 return false;
	}

	if($("#examDate").val() == "dd/mm/yyyy" || $("#examDate").val() == undefined || $("#examDate").val().split('/').length < 3 ){
		removeLoading("kt_content");
		swal.fire(
                'warning !',
                'PLease Enter a valid Exam Date !.',
                'error'
            );
		 var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
         doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
		 active_link('archive-pv');
		 return false;
	}

	if($("#issuedDate").val() == "dd/mm/yyyy" || $("#issuedDate").val() == undefined || $("#issuedDate").val().split('/').length < 3 ){
		removeLoading("kt_content");
		swal.fire(
                'warning !',
                'PLease Enter a valide issued Date !.',
                'error'
            );
		 var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
         doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
		 active_link('archive-pv');
		 return false;
	}
	
	if(document.getElementById("capec").files[0] == undefined && document.getElementById("capec").files[0] == null){
		removeLoading("kt_content");
		swal.fire(
                'warning !',
                'PLease upload the CAPEC archive.',
                'error'
            );
		 var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
         doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
		 active_link('archive-pv');
		 return false;
		
	}

	var formData = new FormData();

	formData.append("surName",$("#surName").val().trim());
	formData.append("givenName",$("#givenName").val().trim());
	formData.append("pob",$("#pob").val().trim());
	formData.append("gender",$("#gender").val());
    formData.append("nic",$("#nic").val());
	formData.append("dob",$("#dob").val());

	formData.append("licenseNum",$("#licenseNum").val().trim());
	//formData.append("diplome",$("#diplome").val());
	formData.append("speciality",$("#speciality").val());
    formData.append("issuedDate",$("#issuedDate").val());
    formData.append("issuedPlace",$("#issuedPlace").val());
    formData.append("issuedCountry",$("#issuedCountry").val());
   // formData.append("status",1);
    formData.append("examDate",$("#examDate").val());
    formData.append("examPlace", $("#examPlace").val());
    formData.append("pvNumber",$("#pvNumber").val());
	formData.append("archiveService",$("#archiveService").val());
	formData.append("issuedAuthority",$("#issuedAuthority").val());
	formData.append("grade",$("#grade").val());
	formData.append("archiveNumber",$("#archiveNumber").val().trim());
	formData.append("finalAverage",$("#finalAverage").val());
	formData.append("personCountry",$("#personCountry").val());
	formData.append("service",$("#archiveService").val());
	formData.append("authority",$("#issuedAuthority").val());

    var archiveForm=formData;
	var baseUrlOfController="/simt/archive/save";


	if(getBaseUrl(baseUrlOfController) != null ){
		doPost(getBaseUrl(baseUrlOfController),archiveForm,"kt_content").then(function(responseSuccess){
	    // first input file check
			var idFirstFileInput = "";
		if(document.getElementById("fileInput")){
			idFirstFileInput="fileInput";
		}else{
			if(document.getElementById("fileInput-0")){
				idFirstFileInput="fileInput-0";
			}
		}

		uploadFile(responseSuccess,-1,"archive" ,"capec" );
		if(document.getElementById(idFirstFileInput).files[0] != undefined || document.getElementById(idFirstFileInput).files[0] != null ){
			var numberOfFilesOnView = document.getElementsByClassName('kt-avatar__input_file').length;
            
			if(numberOfFilesOnView == 1 && (document.getElementById("fileInput").files[0] != undefined || document.getElementById("fileInput").files[0] != null)){
				
				persistUnikFil(responseSuccess,archiveId);
			}else if(numberOfFilesOnView > 1){
				persistMultipleFils(responseSuccess,archiveId);	

			}

		}
		
		else{
			 // archive persisted without a file ! here is success message
		 removeLoading("kt_content");
		 if(archiveId === 0){
			 swal.fire(
		                'DONE !',
		                'Archive success fully REGISTERED !.',
		                'success'
		            )
				//we redirect to creation page
			 var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
	         doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
			 active_link('archive-pv');
			 return false;
			}else{
				 swal.fire(
			                'DONE !',
			                'Archive success fully Edited !.',
			                'success'
			            )
				// we redirect to list page
				var baseUrlOfControllerList="/simt/archive/list";
				doGet(getBaseUrl(baseUrlOfControllerList),'kt_content');
				active_link('archive-pv');
				return false;
			}

	     return false;

		}

		}
		).catch(function(responseError){
			alert('echec ! '+responseError);
		});

	}

}

function validArchive(url, idResultContener){
	 let  dateRange=document.getElementById("archiveRangeId").value;
	      const status=document.getElementById("archiveStatus").value;
	      dateRange =dateRange.split("-");
	      [startDate, endDate]=dateRange;
	      var myModal = document.getElementById("myModal");
	      baseUrl=url+"?startDate="+startDate+"&endDate="+endDate+"&status="+status;
	        swal.fire({
	            title: 'confirm validation / confirmez la validation ! ',
	            text: "irreversible operation / operation irreversible!",
	            type: 'warning / attention',
	            showCancelButton: true,
	            confirmButtonText: 'Yes / Oui'
	            //boolean =true;
	        }).then(function(result) {
	           if (result.value) {
	       // die(boolean);
		displayLoading(idResultContener);
	    var xmlHttp     =   getXMLHttp();
	    xmlHttp.open("GET", baseUrl, false);
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	    xmlHttp.onreadystatechange = function(){
	    removeLoading(idResultContener);
	      if(xmlHttp.status !== 200){
	      	$("#"+idResultContener).html(xmlHttp.responseText);
	          return;
	      }
	      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
	    	  myModal.style.display = "none";

	      	//$("#"+idResultContener).html(xmlHttp.responseText);
	      	    swal.fire(
	                'validated / validée !',
	                'Archive validated / Archive validée',
	                'success'
	            )
	      }else{
	    	  $("#"+idResultContener).html("ERROR 202");
	    	  swal.fire(
	               'Not Allowed / Refusé!',
	               'not enought Rights or Archive state not Registered / vous ne disposez pas de droits ou Archive pas au statut Enregistree !',
	               'error'
	            )
	      }
	      //refreshArchivePage(url,false);
	      refreshArchiveContent();
	  };
	  xmlHttp.send(null);
	  }
	   });
	}

	function suspendArchive(url, idResultContener){
	 let  dateRange=document.getElementById("archiveRangeId").value;
	      const status=document.getElementById("archiveStatus").value;
	      dateRange =dateRange.split("-");
	      [startDate, endDate]=dateRange;
	      baseUrl=url+"?startDate="+startDate+"&endDate="+endDate+"&status="+status;
	      swal.fire({
	        title: ' confirm Suspension / confirmez la suspension !',
	        text: "irreversible operation / operation irreversible!",
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonText: 'Yes / oui'
	        }).then(function(result) {
	           if (result.value) {
	        displayLoading(idResultContener);
	        var xmlHttp     =   getXMLHttp();
	        xmlHttp.open("GET", baseUrl, false);
	        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	        xmlHttp.onreadystatechange = function(){
	        removeLoading(idResultContener);
	          if(xmlHttp.status !== 200){
	            $("#"+idResultContener).html(xmlHttp.responseText);
	              return;
	          }
	          if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
	           // $("#"+idResultContener).html(xmlHttp.responseText);
	                swal.fire(
	        		    'Suspended / Suspendue!',
	        		    'Archive Suspended / Archive Suspendue!.',
	        		    'success'
	        		)
	          }else{
	              $("#"+idResultContener).html("ERROR 202");
	              swal.fire(
	                   'Not Allowed / Refusé!',
	                   'not enought Rights or Archive state not Registered / vous ne disposez pas de droits ou Archive pas au statut Enregistree !',
	                   'error'
	                )
	               
	          }
	          refreshArchivePage(url,false);
	        };
	      xmlHttp.send(null);
	      }
	   });
	}

	function resetArchive(url, idResultContener){
	 let  dateRange=document.getElementById("archiveRangeId").value;
	      const status=document.getElementById("archiveStatus").value;
	      dateRange =dateRange.split("-");
	      [startDate, endDate]=dateRange;
	      baseUrl=url+"?startDate="+startDate+"&endDate="+endDate+"&status="+status;
	       swal.fire({
	               title: 'confirm Resetting / confirmez la Reinitialisation !',
	               text: "irreversible operation / operation irreversible!",
	               type: 'warning',
	               showCancelButton: true,
	               confirmButtonText: 'Yes / Oui'
	        }).then(function(result) {
	           if (result.value) {
		displayLoading(idResultContener);
	    var xmlHttp     =   getXMLHttp();
	    xmlHttp.open("GET", baseUrl, false);
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	    xmlHttp.onreadystatechange = function(){
	    removeLoading(idResultContener);
	      if(xmlHttp.status !== 200){
	      	$("#"+idResultContener).html(xmlHttp.responseText);
	          return;
	      }
	      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
	      	//$("#"+idResultContener).html(xmlHttp.responseText);
	      	    swal.fire(
	                'Resetted / Réinitialisée !',
	                'Archive Resetted! / Archive Réinitialisée !',
	                'success'
	            )
	            //return false;
	            refreshArchiveContent();
	      }
	  };
	  xmlHttp.send(null);
	  }
	   });
	}


/*
 * this function is used during creation / edition to persist the file when only one file is uploaded
 */
	
function persistUnikFil(responseSuccess,archiveId){
		//debut upload
		uploadFile(responseSuccess,0,"archive" ,"fileInput" ).then(
				function(responseFileSuccess){
					removeLoading("kt_content");
					if(responseFileSuccess=="ok.file.uploaded"){


						if(archiveId === 0){
							//we redirect to creation page
							swal.fire(
					                'DONE / FAIT!',
					                'Archive successfully REGISTERED / Archive enregistrée !.',
					                'success'
					            );
						 var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
				         doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
						 active_link('archive-capacity');
						 return false;
						}else{
							// we redirect to list page
							 swal.fire(
						                'DONE / FAIT !',
						                'Archive successfully Edited /Archive editée !.',
						                'success'
						            );
							var baseUrlOfControllerList="/simt/archive/list";
							doGet(getBaseUrl(baseUrlOfControllerList),'kt_content');
							active_link('archive-capacity');
							return false;
						}

					}
				}
				).catch(
				 function(responseFileError){
					console.log('echec upload File ! '+responseFileError);
				        }
						);
		//fin upload

	}


/*
 * this function is used during creation / edition to persist the multiple files  uploaded
 */


function persistMultipleFils(responseSuccess,archiveId){
	//debut upload
	var listElementDivClassktAvatarInputFile = document.getElementsByClassName('kt-avatar__input_file');
	var numberOfFiles = listElementDivClassktAvatarInputFile.length;
	var i=0;
	while(i<numberOfFiles ){
		var idFileField="fileInput-"+i;
		if(document.getElementById(idFileField) != undefined && document.getElementById(idFileField) && document.getElementById(idFileField).files && document.getElementById(idFileField).files[0]){
		var numberOfTheFile=i+1;
		if(i < numberOfFiles){
			uploadFile(responseSuccess,numberOfTheFile,"archive" ,idFileField ).then(
				function(responseFileSuccess){
					if(responseFileSuccess.indexOf("ok.file.uploaded")>-1){
						var fileActualNumber =responseFileSuccess.split("ok.file.uploaded-")[1];
						//start operation at last file
						if(fileActualNumber == numberOfFiles){
							removeLoading("kt_content");

							if(archiveId === 0){
								//we redirect to creation page
								swal.fire(
						                'DONE / FAIT !',
						                'Archive successfully REGISTERED / Archive ENREGISTREE !.',
						                'success'
						            );
							 var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
					         doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
							 active_link('archive-pv');

							 return false;
							}else{
								// we redirect to list page
								swal.fire(
						                'DONE / FAIT !',
						                'Archive successfully Edited /  Archive éditée!.',
						                'success'
						            )
								var baseUrlOfControllerList="/simt/archive/list";
								doGet(getBaseUrl(baseUrlOfControllerList),'kt_content');
								active_link('archive-pv');
								return false;
							}

					    }
						//$("#fileCancel-"+i).click();
						//end operation at last file
					}
				}
				).catch(
				 function(responseFileError){
					console.log('echec upload File ! '+responseFileError);
				        }
						);
		        }
		//fin upload
		}
		i++;
    }
}

/*
 * this function helps to collect good informations when submitting the form
 */
function getGoodFields(oldSurName,oldGivenName,oldPob,oldDob,oldGender,oldNic,oldOccupation,oldIssuedDate,oldIssuedCountry,oldExamDate,oldExamPlace,oldPvNumber,oldCategory,oldArchiveNumber,oldNationality,oldIssuedPlace,oldNationalityInnerHTML,oldIssuedCountryInnerHTML,oldCategoryInnerHTML){
	var inputs = document.getElementsByClassName("help-block");
    if(inputs.length > 0 ){
		for(var i = 0 ; i < inputs.length ; i++){

			if(inputs[i].style.display == "none"){
				var fiedId=inputs[i].getAttribute("data-bv-for");
				if(fiedId == "surName"){
					document.getElementById("surName").setAttribute("value",oldSurName);
					if(oldSurName==""|| oldSurName==undefined){
						var surNamePlaceHolder = document.getElementById("surName").getAttribute("placeholder");
						document.getElementById("surName").setAttribute("placeholder",surNamePlaceHolder);
					}else{
						document.getElementById("surName").setAttribute("placeholder",oldSurName);
					}
				}
				if(fiedId == "givenName"){
					document.getElementById("givenName").setAttribute("value",oldGivenName);
					if(oldGivenName==""|| oldGivenName==undefined){
						var givenNamePlaceHolder = document.getElementById("givenName").getAttribute("placeholder");
						document.getElementById("givenName").setAttribute("placeholder",givenNamePlaceHolder);
					}else{
						document.getElementById("givenName").setAttribute("placeholder",oldGivenName);
					}
				}
				if(fiedId == "pob"){
					//$("#pob").innerText = oldPob;
					document.getElementById("pob").setAttribute("value",oldPob);
					if(oldPob==""|| oldPob==undefined){
						var pobPlaceHolder = document.getElementById("pob").getAttribute("placeholder");
						document.getElementById("pob").setAttribute("placeholder",pobPlaceHolder);
					}else{
						document.getElementById("pob").setAttribute("pob",oldPob);
					}
				}

				if(fiedId == "dob"){
					document.getElementById("dob").setAttribute("value",oldDob);
					if(oldDob==""|| oldDob==undefined){
						var dobPlaceHolder = document.getElementById("dob").getAttribute("placeholder");
						document.getElementById("dob").setAttribute("placeholder",dobPlaceHolder);
					}else{
						document.getElementById("dob").setAttribute("placeholder",oldDob);
					}
				}

				if(fiedId == "gender"){
					if(oldGender==""|| oldGender==undefined){
						document.getElementById("gender").innerHTML='<option value="" selected>Select your gender...</option><option value="F" ><fmt:message key="female.gender" /></option><option value="M" ><fmt:message key="male.gender" /></option>';
					}else{
						if(oldGender=="FEMME" || oldGender=="FEMALE" ){
							if(oldGender=="FEMME")
								document.getElementById("gender").innerHTML='<option value="" >Select your gender...</option><option value="F" selected>FEMME</option><option value="M" >HOMME</option>';
							else
								document.getElementById("gender").innerHTML='<option value="" >Select your gender...</option><option value="F" selected>FEMALE</option><option value="M" >MALE</option>';

						}
						if(oldGender=="HOMME" || oldGender=="MALE" ){
							if(oldGender=="HOMME")
								document.getElementById("gender").innerHTML='<option value="" >Select your gender...</option><option value="F" >FEMME</option><option value="M" selected>HOMME</option>';
							else
								document.getElementById("gender").innerHTML='<option value="" >Select your gender...</option><option value="F" >FEMALE</option><option value="M" selected>MALE</option>';


						}
					}
				}

				if(fiedId == "nic"){
					document.getElementById("nic").setAttribute("value",oldNic);
					document.getElementById("nic").setAttribute("placeholder",oldNic);
				}
				if(fiedId == "occupation"){
					document.getElementById("occupation").setAttribute("value",oldOccupation);
					document.getElementById("occupation").setAttribute("placeholder",oldOccupation);
				}
				if(fiedId == "issuedDate"){
					document.getElementById("issuedDate").setAttribute("value",oldIssuedDate);
					if(oldIssuedDate==""|| oldIssuedDate==undefined){
						var issuedDatePlaceHolder = document.getElementById("issuedDate").getAttribute("placeholder");
						document.getElementById("issuedDate").setAttribute("placeholder",issuedDatePlaceHolder);
					}else{
						document.getElementById("issuedDate").setAttribute("placeholder",oldIssuedDate);
					}
				}
				if(fiedId == "issuedCountry"){
					if(oldIssuedCountry==""|| oldIssuedCountry==undefined){
						document.getElementById("issuedCountry").innerHTML=oldIssuedCountryInnerHTML;
					}else{
						document.getElementById("issuedCountry").innerHTML=oldIssuedCountryInnerHTML;
					}
				}

				if(fiedId == "examDate"){
					document.getElementById("examDate").setAttribute("value",oldExamDate);
					if(oldExamDate==""|| oldExamDate==undefined){
						var examDatePlaceHolder = document.getElementById("examDate").getAttribute("placeholder");
						document.getElementById("examDate").setAttribute("placeholder",examDatePlaceHolder);
					}else{
						document.getElementById("examDate").setAttribute("placeholder",oldExamDate);
					}
				}
				if(fiedId == "examPlace"){
					document.getElementById("examPlace").setAttribute("value",oldExamPlace);
					document.getElementById("examPlace").setAttribute("placeholder",oldExamPlace);
				}
				if(fiedId == "issuedPlace"){
					document.getElementById("issuedPlace").setAttribute("value",oldIssuedPlace);
					document.getElementById("issuedPlace").setAttribute("placeholder",oldIssuedPlace);
				}
				if(fiedId == "pvNumber"){
					document.getElementById("pvNumber").setAttribute("value",oldPvNumber);
					if(oldPvNumber==""|| oldPvNumber==undefined){
						var pvPlaceHolder = document.getElementById("pvNumber").getAttribute("placeholder");
						document.getElementById("pvNumber").setAttribute("placeholder",pvPlaceHolder);
					}else{
						document.getElementById("pvNumber").setAttribute("placeholder",oldPvNumber);
					}
				}
				if(fiedId == "category"){

					if(oldCategory==""|| oldCategory==undefined){
						document.getElementById("category").innerHTML=oldCategoryInnerHTML;
					}else{
						document.getElementById("category").innerHTML=oldCategoryInnerHTML;
					}
				}
				if(fiedId == "archiveNumber"){
					document.getElementById("archiveNumber").setAttribute("value",oldArchiveNumber);
					document.getElementById("archiveNumber").setAttribute("placeholder",oldArchiveNumber);
				}
				if(fiedId == "nationality"){
					if(oldNationality==""|| oldNationality==undefined){
						document.getElementById("nationality").innerHTML=oldNationalityInnerHTML;
					}else{
						document.getElementById("nationality").innerHTML=oldNationalityInnerHTML;
					}
				}

			}
		}
    }
}


function deleteArchive(url, idResultContener){
	      baseUrl=url;
	       swal.fire({
	               title: 'confirm Deletion / confirmez la Suppression !',
	               text: "irreversible operation / operation irreversible!",
	               type: 'warning',
	               showCancelButton: true,
	               confirmButtonText: 'Yes / Oui'
	        }).then(function(result) {
	           if (result.value) {
		displayLoading(idResultContener);
	    var xmlHttp     =   getXMLHttp();
	    xmlHttp.open("GET", baseUrl, false);
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	    xmlHttp.onreadystatechange = function(){
	      if(xmlHttp.status !== 200){
	      	$("#"+idResultContener).html(xmlHttp.responseText);
	          return;
	      }
	      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
	      	if(xmlHttp.responseText == "ok"){
	      	    swal.fire(
	                'Deleted / Supprimée !',
	                'Archive Deleted! / Archive Supprimée !',
	                'success'
	            )
	      	}
	    	if(xmlHttp.responseText == "koProfCardsLinked"){
	    		swal.fire(
		                'Attention !',
		                'Archive linked to a professional card! / Archive liée a une carte professionnelle !',
		                'warning'
		            )

	    	}
            refreshArchiveContent();
	    	active_link('archive-pv');
	  		return false;

	      }else{
	    	  $("#"+idResultContener).html("ERROR 202");
	    	 swal.fire(
	            'Not Allowed / Refusé!',
	            'not enought Rights or Archive state not Registered / vous ne disposez pas de droits ou Archive pas au statut Enregistré !',
	            'error'
	         )
	      }
	  };
	  xmlHttp.send(null);
	  }
	   });
	}

function refreshArchivePage(url,isGlobalLoading){
	if(isGlobalLoading){
		var baseUrlOfController="/simt/archive/list";
	  	if(getBaseUrl(baseUrlOfController)!= null){
	  		doGet(getBaseUrl(baseUrlOfController),'kt_content');
	  		active_link('archive-pv');
	  	 	addDataTable();
	  		return false;
	  	}
	}else{
		searchArchive(url);
	}

}

function searchArchive(url){

	 let archive = document.getElementById("archiveNumber");
     let archiveValue = archive.value.trim();
     if( archiveValue == ""){
   	  let  dateRange=document.getElementById("archiveRangeId").value;
         const status=document.getElementById("archiveStatus").value;
         dateRange =dateRange.split("-");
         [startDate, endDate]=dateRange;
        if (startDate =="" &&  endDate ==undefined ){
        	var baseUrlOfController="/simt/archive/list";
    	  	if(getBaseUrl(baseUrlOfController)!= null){
    	  		doGet(getBaseUrl(baseUrlOfController),'kt_content');
    	  		active_link('archive-pv');
    	  		return false;
    	  	}
        }else{

       	 let archiveNumber ="NULL";
       	 doGet(baseUrl+"/archive/search?startDate="+startDate+"&endDate="+endDate+"&status="+status+"&archiveNumber="+archiveNumber, "achiveTableContent");
        }
    }else{

   	 let archiveNumber = archiveValue;
   	 let startDate = undefined;
   	 let endDate = undefined;
   	 doGet(baseUrl+"/archive/search?startDate="+startDate+"&endDate="+endDate+"&status="+status+"&archiveNumber="+archiveNumber, "achiveTableContent");

    }
  	addDataTable();

}

function saveEditArchive(archiveId) {
    var editArchiveFormId = document.getElementById("editArchiveFormId");
	displayLoading("editArchiveFormId");

	if($("#examDate").val() == "dd/mm/yyyy" || $("#examDate").val() == undefined || $("#examDate").val().split('/').length < 3 ){
		removeLoading("editArchiveFormId");
		swal.fire(
                'warning !',
                'PLease Enter a valid Exam Date !.',
                'error'
            );
		return;
	}

	if($("#issuedDate").val() == "dd/mm/yyyy" || $("#issuedDate").val() == undefined || $("#issuedDate").val().split('/').length < 3 ){
		removeLoading("editArchiveFormId");
		swal.fire(
                'warning !',
                'PLease Enter a valide issued Date !.',
                'error'
            );
		return;
	}

	var issuedDate = document.getElementById("issuedDate").value;
    var issuedPlace = document.getElementById("issuedPlace").value;
  	var issuedCountry = document.getElementById("issuedCountry").value;
    var examDate = document.getElementById("examDate").value;
   	var examPlace = document.getElementById("examPlace").value;
   	var pvNumber = document.getElementById("pvNumber").value;
    var archiveBureau = document.getElementById("archiveBureau").value;
    var issuedAuthority = document.getElementById("issuedAuthority").value;
   	var grade = document.getElementById("grade").value;
    var archiveNumber = document.getElementById("archiveNumber").value;
    var finalAverage = document.getElementById("finalAverage").value;

	var url="/simt/archive/saveEditArchive?archiveId="+archiveId+"&issuedDate="+issuedDate+"&issuedPlace="+ issuedPlace+"&issuedCountry="+ issuedCountry+"&examDate="+ examDate+"&examPlace="+ examPlace+"&pvNumber="+ pvNumber
	+"&archiveBureau="+archiveBureau+"&issuedAuthority="+ issuedAuthority+"&grade="+ grade+"&archiveNumber="+ archiveNumber+"&finalAverage="+ finalAverage;
	    var xmlHttp = getXMLHttp();
                xmlHttp.open("GET", url, false);

                xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
                xmlHttp.onreadystatechange = function() {
                removeLoading("editArchiveFormId");
                closeNav();
                    if (xmlHttp.status !== 200) {
                        customAlert("Erreur interne | Internal error");
                        return;
                    }

                    if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
                         uploadFile(archiveId,1,"archiveEdition" ,"fileInput" );
                        var response = xmlHttp.responseText;
                        refreshArchiveContent();

                        }
                };

                xmlHttp.send(null);
                //end code to retrieve datas from API

        }

function refreshArchiveContent(){
	var manageFileButton = document.getElementById("searchArchive");
	var archiveNumber =document.getElementById("archiveNumber");
	if (archiveNumber.value) {
		manageFileButton.onclick();
	}else{
		var datefilter =document.getElementById("archiveRangeId");
		if(datefilter.value) {
			manageFileButton.onclick();
		}else{
			var baseUrlOfController="/simt/archive/list";
            doGet(getBaseUrl(baseUrlOfController),'kt_content');
            active_link('archive-pv');
            addDataTable();
           // return false;
		}

	}
}

function refreshForm(archiveId) {
	var baseUrlOfControllerCreate="/simt/archive/create/actionCreate/0";
    doGet(getBaseUrl(baseUrlOfControllerCreate),'kt_content');
	
}
