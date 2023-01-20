
function candidateSession_action(value,id,entity){
	switch(value){
	case "action": 
		return;
    		
	case "history":
		
		// openNav('STUDENT TRACKING', '', '50%');
		// alert("student");
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		
		break;
		
	default:
	   return;
	}
}

function studentSession_action(value,id,entity){
	
	switch(value){
	case "action": 
		return;
    		
	case "history":
		
		// openNav('STUDENT SESSION TRACKING', '', '50%');
		// alert("studentSession");
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		
		break;
		
	default:
	   return;
	}
}

//"use strict";

function manage_action(value,id,entity){


	switch(value){
	case "action":
		return;
	/*
	 * case "detail": if(entity=='Student'){ var url = $("#baseUrl").val() +
	 * "/manageStudent/detail/" + id; openNav('STUDENT DETAILS / DETAILS DE L
	 * ELEVE', '', '100%'); doGet(url,'contenu'); }else{ var url =
	 * $("#baseUrl").val() + "/manageStudent/detail/" + id; openNav('CANDIDATE
	 * DETAILS / DETAILS DU CANDIDAT ', '', '100%'); doGet(url,'contenu'); }
	 * break;
	 */

case "history":
		
		openNav('MANAGE STUDENT LIST HISTORY', '', '50%');
	    // id = id;
	    // alert("id"+id);
	    
	    // entity = entity;
		// alert("entity"+entity);
		// alert("manage_action history");
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		
		break;

default:
	   return;
		/*
		 * case "history": if(entity=='Student'){ var url = $("#baseUrl").val() +
		 * "/simt/tracking?&entity=" + entity + "&id=" + id; openNav('STUDENT
		 * TRACKING / HISTORIQUE DE L ELEVE', '', '50%'); doGet(url,'contenu');
		 * }else{ var url = $("#baseUrl").val() + "/simt/tracking?&entity=" +
		 * entity + "&id=" + id; openNav('CANDIDATE DETAILS / DETAILS DU
		 * CANDIDAT', '', '50%'); doGet(url,'contenu'); } break;
		 * 
		 * case "present": if(entity=='Student') presentStudent(id); break;
		 * default: return;
		 */
	}
}


function printing_transcript_action(value,id,entity){
	switch(value){
	case "action": 
		return;
    		
	case "history":
		
		// openNav('TRANSCRIPT TRACKING', '', '50%');
		// alert("TRANSCRIPT TRACKING");
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		
		break;
		
	default:
	   return;
	}
}

/*
 * function emptyForm() { doGet(
 * $("#baseUrl").val()+'/candidate/create','content');
 * initializeCandidateAddForm(); }
 */

/**
 * Definition function LoadFiles() Definition function setFileId() Definition
 * function LoadFilesPreview() Definition function setFilePreviewId() Definition
 * function showPDF1(pdf_url, canvasId) Definition function
 * showPage(page_no,canvasId, __PDF_DOC) Definition function
 * setBackingDocsPreview() Definition function delete_file(id) Definition
 * function uploadFiles
 */
function LoadFiles() {
	var url = $("#baseUrl").val()+"/student/upload_additional_backing_document";
    var xmlHttp     =   getXMLHttp();
    xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
      // return error page if status code is not 200
      if(xmlHttp.status !== 200){
    	  alert("Something went wrong on the server!");
          return;
      }
     
      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
    
    
          var respons = xmlHttp.responseText;
          var divAddNewFile = document.createElement("div");
          divAddNewFile.classList.add('file','col-md-4');
          divAddNewFile.innerHTML=respons;
          document.getElementById("new-file-div").appendChild(divAddNewFile);
          setFileId();
          
          
     
     // we set the event to the file elements which were added
      var files = document.getElementsByClassName('id_file');
	      
      for(var i=files.length-1; i<files.length; i++){
      
    	
    	  // when i i=0 there is already a listener for that
    	  files[i].addEventListener('change', function(){
    	  
    		 
    		  
    		 
    	  		var idDocument = event.target.value;
    	  		var allowedFileTypes = /(\.pdf)$/i;
    	  		var fileSize = event.target.files[0].size;
    	  		var maxFileSize = 250000;
    	  		
    	  		// var fileButton = event.target.id;
    	  		
    	  		var fileButton = "file-id-"+(i-1);
    	  		// var fileButton = "file-id-";
    	  		// var fileButton = $(event.target).attr('file-id-');
    	  	    console.log("fileButton"+fileButton);
    	  		 
    	  		var fileNumber= fileButton.split("file-id-"); 
    	  		
    	  		console.log("fileNumber"+fileNumber); 
    	  		
    	  		if(!allowedFileTypes.exec(idDocument)) {
    	  			event.target.value = '';
    	  			return;  
    	  		}else{
    	  			if (fileSize>maxFileSize) {
    	  				event.target.value = '';
    	  				return;
    	  			}else{
    	  				// upload the file and display on the appropriate div
						// element
    	  				var file = this.files[0];
    	  				var preview = document.querySelector('#id-preview-'+(i-1));
    	  				  var reader  = new FileReader();
    	  				  reader.readAsDataURL(file);
    	  				  reader.onloadend = function () {
    	  				    preview.src = reader.result;
    	  				  }
    	  				var canvasId =fileNumber[1];
    	  				
    	  				
    	  				showPDF2(URL.createObjectURL(file), canvasId);
    	  			}
    	  		}
    	  	});
      }
      }
  };
  xmlHttp.send(null);
}

/* PREVIEW FIRST DOC */

var fileDoc = document.querySelector('.id_file');
// var fileDoc = document.getElementsByClassName("id_file");

	fileDoc.addEventListener('change', function(){
		 
		
		var idDocument = event.target.value;
		var allowedFileTypes = /(\.pdf)$/i;
		var fileSize = event.target.files[0].size;
		var maxFileSize = 250000;
		
		
		if(!allowedFileTypes.exec(idDocument)) {
			alert("Invalid File Type");
			event.target.value = '';
			return; 
		}else{

			if (fileSize>maxFileSize) {
				alert('File uploaded is larger than 250Kb');
				event.target.value = '';
				return;
			}else{
				// upload the file and display on the appropriate div element
				var file = this.files[0];
				var preview = document.querySelector('#id-preview');

				  var reader  = new FileReader();
				  reader.readAsDataURL(file);

				  reader.onloadend = function () {
				    preview.src = reader.result;
				  }
				  
				var  canvasId = "#pdf-canvas-0";
				showPDF1(URL.createObjectURL(file), canvasId);
				

			}


		}

	});

	

	function setFileId() {
        var taille = document.getElementsByClassName('id_file').length;
        var listElement = document.getElementsByClassName('id_file');
        var lsitPreviw =document.querySelectorAll("[addedFile]");
        var listElementButton = document.getElementsByClassName('boutonDelette');
        var listFile = document.getElementsByClassName('id_file');
        var listCanvas = document.getElementsByClassName('pdf-canvas-view');
        var canvasId ="";
            for(var i=0 ; i<taille; i++){
                var idvalue = "div-file-"+i;
                
                var fileid = "file-id-"+i;
                if(i!=0) {
                canvasId = "pdf-canvas-"+i;
                listCanvas[i].setAttribute("id", canvasId);
                lsitPreviw[i-1].setAttribute("id", "id-preview-"+i);
                }
                listElement[i].setAttribute("id", idvalue);
                if(listElementButton[i] != null && listElementButton[i] != undefined){
                	var buttonvalue = "div-cat-button-"+(i+1);
	                listElementButton[i].setAttribute("id", buttonvalue);
	                listElementButton[i].setAttribute("value", i+1);
                }
                listFile[i].setAttribute("id", fileid);
                
                
            }
      
    }




function LoadFilesPreview() {

		var url = $("#baseUrl").val()+"/student/upload_additional_backing_document_file_preview";
		// var url =
		// $("#baseUrl").val()+"/candidate/upload_additional_backing_document";
	    var xmlHttp     =   getXMLHttp();


	    xmlHttp.open("POST", url, true);
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	    xmlHttp.onreadystatechange = function(){
	 
	      if(xmlHttp.status !== 200){
	        alert("Erreur interne | Internal error");
	          return;
	      }
	      

	      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
	    	
	      var response = xmlHttp.responseText;
	      document.getElementById("file-preview").innerHTML+=response;
	      
	      setFilePreviewId();
	      }
	  };

	  xmlHttp.send(null);

}

function setFilePreviewId() {

	var listPreviewCanvas = document.getElementsByClassName('pdf-canvas-preview');
	var listViewCanvas = document.getElementsByClassName('pdf-canvas-view');
    var listPreviewDiv = document.getElementsByClassName('file-preview-div');
	for(var i=0; i<listPreviewCanvas.length; i++) {
		var idValue = "pdf-canvas-preview-" +i;
		var previewValue = "file-preview-div-" +i;
		listPreviewCanvas[i].setAttribute("id", idValue);
		listPreviewDiv[i].setAttribute("id", previewValue);
	}
}

function showPDF1(pdf_url, canvasId) {
	var __PDF_DOC,
	__CURRENT_PAGE,
	__TOTAL_PAGES,
	__PAGE_RENDERING_IN_PROGRESS = 0;
	
	pdfjsLib.getDocument({ url: pdf_url }).then(function(pdf_doc) {
	__PDF_DOC = pdf_doc;
	__TOTAL_PAGES = __PDF_DOC.numPages;

	// Show the first page
	showPage1(1, canvasId, __PDF_DOC);
	}).catch(function(error) {
	alert(error.message);
	});
}

function showPDF2(pdf_url,i) {
	console.log(i+" ---------------");

	if(i!= undefined){
console.log("showPDF if");

var	__CANVAS = document.getElementById("pdf-canvas-"+i);
	}
	else 
		console.log("showPDF else");	
	
	__CANVAS = document.getElementById("pdf-canvas-"+i);
		
	var __CANVAS_CTX = __CANVAS.getContext('2d');
	pdfjsLib.getDocument({ url: pdf_url }).then(function(pdf_doc) {
	__PDF_DOC = pdf_doc;
	__TOTAL_PAGES = __PDF_DOC.numPages;
	// Show the first page*
	showPage2(1,i);

	}).catch(function(error) {
		console.log("exception");
	alert(error.message);
	});
    
}

function showPage1(page_no,canvasId, __PDF_DOC) {
	__PAGE_RENDERING_IN_PROGRESS = 1;
	__CURRENT_PAGE = page_no;
	var __CANVAS = $(canvasId).get(0);
	console.log(__CANVAS);
	__CANVAS_CTX = __CANVAS.getContext('2d');


	
	// While page is being rendered hide the canvas and show a loading message
	$("#pdf-canvas").hide();



	// Fetch the page
	__PDF_DOC.getPage(page_no).then(function(page) {
		// As the canvas is of a fixed width we need to set the scale of the
		// viewport accordingly
		var scale_required = __CANVAS.width / page.getViewport(1).width;

		// Get viewport of the page at required scale
		var viewport = page.getViewport(scale_required - 0.7);

		// Set canvas height
		__CANVAS.height = viewport.height;

		var renderContext = {
		canvasContext: __CANVAS_CTX,
		viewport: viewport
		};
	
		// Render the page contents in the canvas
		page.render(renderContext).then(function() {
			// page has finished rendering or is not rendering at all
			__PAGE_RENDERING_IN_PROGRESS = 0;
			// Show the canvas and hide the page loader
			$("#pdf-canvas").show();
		});
	});
}



function uploadDocs(fileId,name,entity,number){
	var formData = new FormData();
	var xmlHttp     =   getXMLHttp();
	// var url = $("#baseUrl").val()+"/online/services/file/upload";
	var url = $("#baseUrl").val()+"/student/createPost?eligibleCenterId="+eligibleCenterId;
	if(document.getElementById(fileId).files.length==0){
		return;
	}
	formData.append("file",document.getElementById(fileId).files[0]);
	formData.append("name",name);
	formData.append("entity",entity);
	formData.append("number",number);
	xmlHttp.open("POST", url, false);
    xmlHttp.send(formData);
}


function delete_file(id){
  var idvalue = "div-file-"+id;
  var list=document.getElementById(idvalue);
  // list.parentNode.removeChild(list);
  setFileId();
}


function resetForm() {
	 //alert("reset");
   document.getElementById("add_CNI_formID").reset();
}

function bootstapValidationCNI(){
	

	$("#add_CNI_formID")
		.bootstrapValidator(
				{    
	    	        
					fields : {   
						surnameCni : {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter surName from CNI'
    						}

    					}
    				},
    			
    				pobCni : {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter Pob from CNI'
    						}
    					}
    				},
    				dobCni: {
						validators : {
							notEmpty : {
								message : ' Required field / Champ requis :: Please enter Dob from CNI'
							}
						}
					},
					genderCni : {
						validators : {
							notEmpty : {
								message : 'Required field / Champ requis:: Select gender from CNI'									}
						}
					},
					
					fatherCni:  {
						validators : {
							notEmpty : {
								message : 'Please enter a valid name'
							},
							regexp: {
								regexp: /(^[\w\s[çÇ\'\-\"]{0,})\s{0,}(\w{1,}|\w{0,}[çÇ\'\-\"]{1,}\w{1,}|\w{1,}\s{1,}[I]{1,})\s{0,}$/,
								message: 'Please enter Fathers name from CNI'
							}

						}
					},
					motherCni :  {
						validators : {
							notEmpty : {
								message : 'Please enter a valid name'
							},
							regexp: {
								regexp: /(^[\w\s[çÇ\'\-\"]{0,})\s{0,}(\w{1,}|\w{0,}[çÇ\'\-\"]{1,}\w{1,}|\w{1,}\s{1,}[I]{1,})\s{0,}$/,
								message: 'Please enter Mothers name from CNI'
							}

						}
					},
					professionCni: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter profession from CNI'
    						}
    					}
    				},
    				addressCni: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter address from CNI'
    						}
    					}
    				},
    				heightCni: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter height from CNI'
    						}
    					}
    				},
    				issuedDateCni: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter issuedDate from CNI'
    						}
    					}
    				},
    				language: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis ::  Please Select Language'
    						}
    					}
    				},
    				diplome: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please Select Highest Diplome'
    						}
    					}
    				},
    				
    				issuedDate: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please Select Highest Diplome'
    						}
    					}
    				},
    				

    				issuedPlace: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please Select Highest Diplome'
    						}
    					}
    				},
    				
    				diplomeOption: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please Select Highest Diplome'
    						}
    					}
    				},
					
					
					
    			}
    	});

		var validator = $("#add_CNI_formID").data('bootstrapValidator').validate();

		if (validator.isValid()) {
			//alert("isValid");
			//createCNI();
			registerPersonIdentityInformation();
		}else{
			//alert("isNotValid");
		    swal.fire(
		    		'Form validation error',
		    		'Please fill in all the fields !',
		    		'error'
		        )
			return;   	
		}
}


function bootstapValidationStudent(){
	$("#add_candidate_form")
		.bootstrapValidator(
				{

					fields : {
                     phoneNumber : {
                    	validators : {
                    		regexp: {
                    			regexp: /^\s*\w{2,3}-\d{5,6}-\d{2}\s*$/,
                    				message: 'Please enter a valid phone number'
                    		}
                    	}
                    },
    			}
    	});

		var validator = $("#add_candidate_form").data('bootstrapValidator').validate();

		if (validator.isValid()) {
	    registerStudent();

		}else{
		    swal.fire(
		    		'Form validation error',
		    		'Please fill in a correct phone number !',
		    		'error'
		        )
			return;
		}
}




/**
 * Create or update a user
 * 
 * @returns
 */

function registerStudent(){
	displayLoading("kt_content");
	var formData = new FormData(); 
	formData.append("id",0);
	formData.append("surName",$("#surName").val());
	formData.append("givenName",$("#givenName").val());
	formData.append("pob",$("#pob").val());
	formData.append("dob",$("#dob").val());
	formData.append("nationality",$("#nationality").val());
	formData.append("gender",$("#gender").val());
    formData.append("catBdate", $("#catBdate").val());
    formData.append("email", $("#email").val());
    formData.append("phoneNumber",$("#phoneNumber").val());
    formData.append("speciality",$("#speciality").val());
    formData.append("licenseNum",$("#licenseNum").val());
    formData.append("diplome",$("#diplome").val());
    if($("#phoneNumber").val().trim() ==""){
    	swal.fire(
                'Please enter the phone number of the student '
                
            )
            removeLoading("kt_content");
			return;
    	
    }
    if($("#gender").val() ==""){
    	swal.fire( 
    			'Validation Formular',
                'Gender is a required field',
    			'error'
            )
            removeLoading("kt_content");
			return;
    	
    }
		var url = $("#baseUrl").val()+"/student/createPost";
		
		doPost(url,formData,"kt_content").then(result=>{
			 removeLoading("add_candidate_form");			 
			 
	   	     if (result == "dupilcation.of.person") {
	   	    	swal.fire(
		                'This person already exist in the system!',
		                'Cette Person exist dans le system!.',
		                'error'
		            )
					return;
				} 
	   	     
	   	  if (result == "student.has.archive") {
	   	    	swal.fire(
		                'This student already has a CAPEC MAE !',
		                'L eleve dispose deja d un CAPEC MAE !.',
		                'error'
		            )
					return;
				}
	   	     if (result == "dupilcation.of.student") {
	   	    	swal.fire(
		                'This student already exist in the system!',
		                'Ce candidat exist dans le system!.',
		                'error'
		            )
					return;
				}
	
	   	      closeNav();
			  swal.fire(
					    'success!',
		            	'Candidate Session Created Successfully.',
		            	'success'
	               );
			 
			 doGet( $("#baseUrl").val()+ "/student/createGet","kt_content");
			 
		 }).catch(error=>{
			 removeLoading("add_candidate_form");
			 document.getElementById(error).classList.remove("kt-hidden")
			// alert("Candidate Session Created Successfully");
	 
	         emptyForm();
			 
		 });

	// }else $("#add_candidate_form").validate();
	}  




function emptyForm() {
    var eligibleCenterId= document.getElementById("eligibleCenterId").value;
	doGet( $("#baseUrl").val()+ "/student/createGet/?id="+eligibleCenterId,"kt_content");
}


function loadCandidates(url, result){
	var eligibleCenterId = $("#eligibleCenter").val();
	url += "?result=" + result;
	url += "&eligibleCenter=" +eligibleCenterId;

	doGet(url,'candidate-list');
	document.getElementById("list-indicator").value = result;	
}

/*
 * function manageCandidate(url, candidate, result, resultIndicator){
 * displayLoading("kt_content"); var rowId= "candidate-" + candidate; var
 * rowIndex = document.getElementById(rowId); url += "?id=" + candidate; url +=
 * "&result=" + result; url+= "&type=" + resultIndicator;
 * 
 * 
 * var xmlHttp = getXMLHttp(); xmlHttp.open("POST", url, true);
 * xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;
 * charset=UTF-8"); xmlHttp.setRequestHeader("X-Requested-With",
 * "XMLHttpRequest"); xmlHttp.onreadystatechange = function(){ if(xmlHttp.status
 * !== 200){ removeLoading("kt_content"); swal.fire( 'Failed !', 'Something went
 * wrong', 'error' ) ; return; } if(xmlHttp.readyState === 4 && xmlHttp.status
 * === 200){ removeLoading("kt_content"); swal.fire( 'Successful !', 'Cndidate
 * result updated', 'success' ); rowIndex.innerHTML=" "; }else{ } };
 * xmlHttp.send(null); }
 */
function manageCandidate(url, student, result, resultIndicator){
	displayLoading("kt_content");																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																													
	var rowId= "candidate-"+student;
	var rowIndex = document.getElementById(rowId);
	url += "?id=" + student;
	url += "&result=" + result;
	url+= "&type=" + resultIndicator;
	

	var xmlHttp     =   getXMLHttp();
    xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
      if(xmlHttp.status !== 200){
    	  removeLoading("kt_content");
    	  swal.fire(
					 'Failed !',
					 'Something went wrong',
					 'error'
	   )	; 
          return;
      }
      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
    	  removeLoading("kt_content");
    	  // enter-notes
    	  if(resultIndicator != "enter-notes"){
	    	  swal.fire(
						 'Successful !',
						 'Candidate result updated',
						 'success'
		      ); 
	    	  rowIndex.innerHTML="";
	    	 // rowIndex.innerHTML = xmlHttp.responseText;
    	  }else{
    		  openNav('STUDENT TRANSCRIPT FORM | FORMULAIRE POUR RELEVE DES NOTES DE L ETUDIANT', '', '80%');
    		  $("#contenu").html(xmlHttp.responseText);
  
    		  
    	  }
      }else{
      }
  };
  xmlHttp.send(null);
}




function checkAll(tableId, checktoggle)
{
  var checkboxes = new Array(); 
  var checkboxes = document.querySelectorAll("input[class='row-select']");
 
  for (var i=0; i<checkboxes.length; i++)  {
    checkboxes[i].checked = checktoggle;
  }
}


function selectItem(tableId) {
    var checktoggle = document.getElementById('table_select');
    if(checktoggle.checked === true) {
        checkAll(tableId, true);
    }
    else {
        checkAll(tableId, false);
    }
}


function validateReasonForReject(studentSessionId){
	swal.fire({
        title: 'Are you sure you want to Reject this student in this session ?',
        text: "You can still revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, Reject Student!'
         }).then(function(result) {
		        if (result.value) {
		        	// first we call validation form
		        	var baseUrlOfControllerForValidationForm="/simt/studentSession/get/validationForm/reason_for_reject/get/"+studentSessionId;
		        
		        	if(getBaseUrl(baseUrlOfControllerForValidationForm) != null ){
		        		openNav('REASON FOR REJECT', '', '40%');
		        		sendGet(getBaseUrl(baseUrlOfControllerForValidationForm)).then(function(getFormSuccess){
		        			
		        		$("#contenu").html(getFormSuccess);
		        		
		        		console.log("Reject Student form loaded ...");
		
		
		        	}).catch(function(getFormError){
		    			alert('echec ! '+getFormError);
		    		});
		
		        } 

        }
    });
}


/*
 * function ReasonForReject(){
 * 
 * displayLoading("kt_content"); var reasonForReject =
 * document.getElementById('reason_for_reject').value; var url =
 * $("#baseUrl").val() +
 * "/studentSession/manage/reason_for_reject/get"+reasonForReject;
 * 
 * 
 * var myModal = document.getElementById('myModal'); myModal.style.display =
 * "none"; console.log(myModal); console.log("I am here"); doGet(url,
 * 'kt_content'); }
 */	

function persistReasonForReject(){
	
	displayLoading("kt_content");
	var id = document.getElementById("studentSessionId").value;
	// alert("id = = "+id);
	var reasonForReject = document.getElementById("reason_for_reject").value 
	// alert("reasonForReject = = "+reasonForReject);
	
	var url = $("#baseUrl").val() + "/studentSession/manage/reason_for_reject/post/"+ id + "/"+  reasonForReject;
	
	
	
	var myModal = document.getElementById('myModal');
	myModal.style.display = "none";
			console.log(myModal);
			console.log("I am here");
	doGet(url, 'kt_content');
		
}



function approveReasonForReject(){
	// we test :: if :: we validate
	$("#approveReasonForRejectForm").validate();
	return;	    	      
  }
  

function nospaces_ReasonForReject(t){
	
  if(t.value.match(/\s/g)){
		  	swal.fire(
					'Validation Formular',
		            'reasonForReject is a required field (Spaces not allow)',
					'error'
		        );
	   t.value=t.value.replace(/\s/g,'');
   }

}


function testRequirement_persist_ReasonForReject(){
    
	var studentSessionId = document.getElementById("studentSessionId").value; 
    var reasonForReject = document.getElementById("reason_for_reject").value.trim();
    
    if($("#reasonForReject").val() !== '' || $("#reasonForReject").val() !== null || $("#reasonForReject").val() !== 0 ){
   
         persistReasonForReject();
         return;
         
     } 
    
   
    else if ($("#reasonForReject").val() == '' || $("#reasonForReject").val() == null || $("#reasonForReject").val() == 0 || $('reasonForReject').empty()){ 
        	alert("reasonForReject is EMPTY= "+reasonForReject);
        	
            
        	swal.fire(
        			'Validation Formular',
                    'reasonForReject is a required field',
        			'error'
                );
    		return;
     	
     }
     
     
     
    else{
    	 console.log("Do Nothing");
    	 alert("Do Nothing");
     }
    
   
} 
 



/*
 * function changeCandidateRegistrationActionBatch(action, url, result){
 * displayLoading("kt_content"); var table =
 * document.querySelector("#table_result"); // Find all selected checkedboxes
 * var selectedCheckboxes =
 * table.querySelectorAll("input[class='row-select']:checked"); var j=0; var
 * candidates = ""; var candidateRows = []; var listIndicator =
 * document.getElementById("list-indicator").value; var eligibleCenterId =
 * $("#eligibleCenter").val(); if(selectedCheckboxes.length <= 0){ swal.fire(
 * 'No data Selected !', 'Please select at least one (1) candidate to perform
 * the action', 'error' ); return; }
 * 
 * for (var i = 0; i < selectedCheckboxes.length; i++) { var
 * selection=(selectedCheckboxes.length)-1; var id = selectedCheckboxes[i].id;
 * console.log(selectedCheckboxes); // Get the select dropdown for that row var
 * select = document.getElementById('candidate-select-action-' + id); var
 * currentOption=select.options.namedItem(action);
 *  // This just simulates the click action. // But first setting the value and
 * the triggering the onclick event if(currentOption === null
 * ||currentOption==='' ) { j++; continue;
 * 
 * }else{
 * 
 * if (i!=0 && candidates!="") { //Collect id of candidate to be appended to
 * array to be send to backend candidates+="@"; } candidates+= id;
 * candidateRows.push(id); }
 * 
 *  }
 * 
 * url+= "?candidates=" + candidates; url+= "&indicator=" + listIndicator; url +=
 * "&result=" + result; url += "&action=" + action; url += "&eligibleCenterId=" +
 * eligibleCenterId;
 * 
 * var xmlHttp = getXMLHttp(); xmlHttp.open("POST", url, true);
 * xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;
 * charset=UTF-8"); xmlHttp.setRequestHeader("X-Requested-With",
 * "XMLHttpRequest"); xmlHttp.onreadystatechange = function(){ if(xmlHttp.status
 * !== 200){ removeLoading("kt_content"); swal.fire( 'Failed !', 'Something went
 * wrong', 'error' ) ; return; } if(xmlHttp.readyState === 4 && xmlHttp.status
 * === 200){ removeLoading("kt_content"); if (j>0){ var message= "Your current
 * action is not applicable to the remaining " + j + " selected candidates";
 * swal.fire( 'Successful !', message, 'success' ); }
 * 
 * //document.getElementById("candidate-list").innerHTML = xmlHttp.responseText;
 * swal.fire( 'Successful !', 'Candidates results updated successfully!!',
 * 'success' ); for(var k=0; k<candidateRows.length; k++){ var candidateRowId =
 * "candidate-"+candidateRows[k];
 * document.getElementById(candidateRowId).innerHTML = ""; } }else{ } };
 * xmlHttp.send(null);
 *  }
 */

function changeCandidateRegistrationActionBatch(action, url, result){
	displayLoading("kt_content");
	  var table = document.querySelector("#table_result");
	    // Find all selected checkedboxes
	    var selectedCheckboxes = table.querySelectorAll("input[class='row-select']:checked");
	    var j=0;
	    var student = "";
	    var candidateRows = [];
	    var listIndicator = document.getElementById("list-indicator").value;
	    var eligibleCenterId = $("#eligibleCenter").val();
	    if(selectedCheckboxes.length <= 0){
	    	swal.fire(
					 'No data Selected !',
					 'Please select at least one (1) candidate to perform the action',
					 'error'
	    	); 
	    	return;
	    }
	    
	    for (var i = 0; i < selectedCheckboxes.length; i++) {
	        var selection=(selectedCheckboxes.length)-1;
	        var id = selectedCheckboxes[i].id;
	        console.log(selectedCheckboxes);
	        // Get the select dropdown for that row
	        var select = document.getElementById('candidate-select-action-' + id); 
	        var currentOption=select.options.namedItem(action);
	        
	        // This just simulates the click action.
	        // But first setting the value and the triggering the onclick event
	        if(currentOption === null ||currentOption==='' ) {
	            j++;
	            continue;
	        
	        }else{
	        	
	        	if (i!=0 && student!="") {
	        		// Collect id of candidate to be appended to array to be
					// send to backend
	        		student+="@";
	        	}
	        	student+= id;
	        	candidateRows.push(id);
	        }
	        	
	        	
	    }
	    
	    	url+= "?student=" + student;
	    	url+= "&indicator=" + listIndicator;
	    	url += "&result=" + result;
	    	url += "&action=" + action;
	    	url += "&eligibleCenterId=" + eligibleCenterId;
	    	
	    	var xmlHttp     =   getXMLHttp();
	        xmlHttp.open("POST", url, true);
	        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	        xmlHttp.onreadystatechange = function(){
	          if(xmlHttp.status !== 200){
	        	  removeLoading("kt_content");
	        	  swal.fire(
	    					 'Failed !',
	    					 'Something went wrong',
	    					 'error'
	    	   )	; 
	              return;
	          }
	          if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
	        	  removeLoading("kt_content");
	        	  if (j>0){
	        	        var message= "Your current action is not applicable to the remaining " + j + " selected student";
	        	        swal.fire(
	        					 'Successful !',
	        					 message,
	        					 'success'
	        	        );
	        	    }
	        	  
	        	  // document.getElementById("candidate-list").innerHTML =
					// xmlHttp.responseText;
	        	  swal.fire(
	    					 'Successful !',
	    					 'student results updated successfully!!',
	    					 'success'
	    	   ); 
	        	  for(var k=0; k<candidateRows.length; k++){
	        		 var candidateRowId = "candidate-"+candidateRows[k];
	        		  document.getElementById(candidateRowId).innerHTML = "";
	        	  }
	          }else{
	          }
	      };
	      xmlHttp.send(null);
	    
}


function showPage2(page_no,i) {
	
	var __CANVAS=$("#pdf-canvas-"+i).get(0);
	__PAGE_RENDERING_IN_PROGRESS = 1;
	__CURRENT_PAGE = page_no;
	__CANVAS_CTX = __CANVAS.getContext('2d');

	if(i!= undefined)
		$("#pdf-canvas-"+i).hide();
	else
		$("#pdf-canvas").hide();
	// Fetch the page
	__PDF_DOC.getPage(page_no).then(function(page) {
		// As the canvas is of a fixed width we need to set the scale of the
		// viewport accordingly

		var scale_required = __CANVAS.width / page.getViewport(1).width;
		// Get viewport of the page at required scale
		var viewport = page.getViewport(scale_required);

		// Set canvas height
		__CANVAS.height = viewport.height;
		var renderContext = {
		canvasContext: __CANVAS_CTX,
		viewport: viewport
		};
		// Render the page contents in the canvas
		page.render(renderContext).then(function() {
			// page has finished rendering or is not rendering at all
			__PAGE_RENDERING_IN_PROGRESS = 0;
			// Show the canvas and hide the page loader
			if(i!= undefined)
				$("#pdf-canvas-"+i).show();
			else
				$("#pdf-canvas").show();
		});
	});
}


function verifdivBlock1() {
	
	var selectValue = document.getElementById("speciality").value;
	
	$("#divBlock1")
	.bootstrapValidator(
		{

			fields : {
				licenseNum : {
					validators : {
						notEmpty : {
							message : 'Please enter a  license number'
						}

					}
				},
				speciality: {
					validators : {
						notEmpty : {
							message : 'Required field / Champ requis'
						}
					}
				},
				
				
			}

		});
	var validator = $("#divBlock1").data('bootstrapValidator').validate();
	if (validator.isValid()) {
		displayLoading("load-preview-step1");
		
		var license = document.getElementById("licenseNum").value.trim();
		var speciality = document.getElementById("speciality").value;
		var diplomePath = document.getElementById("diplome").value;
		var diplome = $('input[type=file]').val().split('\\').pop();
   		//motif_.innerHTML=retours[1] ;

            	var link = $("#baseUrl").val() + "/student/verify/applicant/license";
            	url = link +"?license="+license+"&speciality="+speciality+"&diplome="+ diplome;
           
            	var xmlHttp = getXMLHttp();
            	xmlHttp.open("GET", url, true);
            	
            	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            	xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            	xmlHttp.onreadystatechange = function() {
            		
            		if (xmlHttp.status !== 200) {
            			customAlert("Erreur interne | Internal error");
            			console.log("response error "+response);
            			return;
            		}
            		
            		if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            			removeLoading("load-preview-step1");
            			var response = xmlHttp.responseText;
            			var applicantDatas = JSON.parse(response);
            			
            			if(applicantDatas.can_be_register =="no"){
            				swal.fire(
	        		                'Error | Erreur!',
	        		                '<strong>The candidate must have had category B for  at least 3 years </strong> ||  <strong> Le candidat doit posseder la category B datant de plus de 3 ans</strong>  ',
	        		                'warning'
	        		            )
						 return;	
            			}
            	
            			document.getElementById('surName').value = applicantDatas.sn;
            			document.getElementById('givenName').value = applicantDatas.gn;
            			document.getElementById('dob').value = applicantDatas.dob;
            			document.getElementById('pob').value = applicantDatas.pob;
            			document.getElementById('catBdate').value = applicantDatas.catB_Date;
            			// document.getElementById('nationality').value =
						// applicantDatas.nationality;
            			document.getElementById('surName').readOnly = true;
                        document.getElementById('givenName').readOnly = true;
                        document.getElementById('dob').readOnly = true;
                        document.getElementById('pob').readOnly = true;
                        document.getElementById('catBdate').readOnly = true;

                        document.getElementById("divBlock2").style.display = "block";
                        document.getElementById("divBlock1").style.display = "none";
            			
            			}
            	};
            	xmlHttp.send(null);
            	// end code to retrieve datas from API
	
    }
}

	function approveStudentInSession(studentSessionId){
    	swal.fire({
            title: 'Are you sure you want to Approve this student in this session ?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, Approve Student!'
        }).then(function(result) {
            if (result.value) {
            	// first we call validation form
            	var baseUrlOfControllerForValidationForm="/simt/studentSession/get/validationForm/"+studentSessionId;
            	if(getBaseUrl(baseUrlOfControllerForValidationForm) != null ){
            		openNav('Approve Student', '', '67%');
            		sendGet(getBaseUrl(baseUrlOfControllerForValidationForm)).then(function(getFormSuccess){
            		$("#contenu").html(getFormSuccess);
            		console.log("Approve Student form loaded ...");


            	}).catch(function(getFormError){
        			alert('echec ! '+getFormError);
        		});

            }

            }
        });
    }

function approveStudent(manageCandidateUrl, studentSessionId, result, indicator){
	$("#approveSudentSessionForm")
    	.bootstrapValidator(
    		{

    			fields : {
                    image: {
						validators: {
                            file: {
								extension: 'jpeg,png,jpg,gif,tiff,svg',
								type: 'image/jpeg,image/png,image/jpg,image/gif,image/tiff,image/svg',
								maxSize: 1024*201, // 205KO
								message: 'Invalid file: maxsize is 205k or invalid file format (jpeg,png,jpg,gif,tiff,svg files neeted)'
							}
						}
					},
					signature: {
						validators: {
                            file: {
								extension: 'jpeg,png,jpg,gif,tiff,svg',
								type: 'image/jpeg,image/png,image/jpg,image/gif,image/tiff,image/svg',
								maxSize: 1024*201, // 205KO
								message: 'Invalid file: maxsize is 205k or invalid file format (jpeg,png,jpg,gif,tiff,svg files neeted)'
							}
						}
					},
					fileId: {
						validators: {
							file: {
								extension: 'pdf',
								type: 'application/pdf',
								// maxSize: 2024*201, //205KO
								message: 'Invalid file: maxsize is 205k or invalid file format (pdf files neeted)'
							}
						}
					},
    			}

    		});
    	var validator = $("#approveSudentSessionForm").data('bootstrapValidator').validate();
    	if (validator.isValid()) {
	document.getElementById("successId").classList.add("kt-hidden");
	document.getElementById("failedId").classList.add("kt-hidden");
	// we validate
  	var baseUrlOfController="/simt/studentSession/update/"+studentSessionId;
  	if(getBaseUrl(baseUrlOfController) != null ){
  		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
		// start response validate
		if( responseSuccess == "ok"){

            uploadFile(studentSessionId,-3,"studentSession" ,"signature" );
            uploadFile(studentSessionId ,-2,"studentSession" ,"image" );
			uploadFile(studentSessionId,-1,"studentSession" ,"fileId" ).then(
					function(responseFileSuccess){
						if(responseFileSuccess=="ok.file.uploaded"){

							 document.getElementById("successId").classList.remove("kt-hidden");
							 document.getElementById("fileId").value=null;

							 var span = document.getElementsByClassName("close")[0];
							 span.click();
							 document.getElementById("candidate-"+studentSessionId).innerHTML = "";
							 swal.fire(
		        		                'Student Approved!',
		        		                'Your Student has been Approve.',
		        		                'success'
		        		            )

							 return;


						}
					}
					).catch(
					 function(responseFileError){
						 document.getElementById("failedId").classList.remove("kt-hidden");
						 console.log('echec upload File ! '+responseFileError);
					        }
							);
					manageCandidate(manageCandidateUrl, studentSessionId, result, indicator);

                }

                // end response validate
            }
            ).catch(function(responseError){
                alert('echec ! '+responseError);
            });
        }

  }else{
     $("#approveSudentSessionForm").validate();
  }
}


function printTranscript(baseUrl,studentSessionId){
	var eligiblecenterId = document.getElementById("eligibleCenter").value;
	var url =baseUrl+"/pv/printTranscript?studentSessionId="+studentSessionId + "&eligiblecenterId=" + eligiblecenterId;
    window.open(url);
}
 

function printCertificate(studentSessionId,applicationId){
	var eligibleCenterId = document.getElementById("eligibleCenter").value;
	var url = $("#baseUrl").val() + "/pv/printPvCertificate?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId+ "&applicationId=" + applicationId;
	window.open(url);
	
}

function printCertificatePreview(baseUrl,studentSessionId) {
	var eligibleCenterId = document.getElementById("eligibleCenter").value;
	openNav('CERTIFICATE PREVIEW / PREVISUALISATION DU DIPLOME ', '', '100%');
	var url =$("#baseUrl").val()+"/pv/printPvCertificatePreview?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId;	

	doGet(url,'contenu');
	
}

function printProfessionalCard(studentSessionId, token){
	var eligibleCenterId = document.getElementById("eligibleCenter").value;
	var url;
	if(token=="preview"){
		url = $("#baseUrl").val() + "/pv/printPreviewProfessionalCard?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId +"&token="+token;
		openNav('PRINT PREVIEW OF PROFESSIONAL CARD  | PREVISUALIZATION DU CARTE PROFESSIONELLE', '', '70%');
		doGet(url,'contenu');
	}else{
		url = $("#baseUrl").val() + "/pv/printPvProfessionalCard?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId +"&token="+token;
		 var span = document.getElementsByClassName("close")[0];
		 span.click();
		 window.open(url);
		 swal.fire(
	                'Print Completed!',
	                'Your Card was successfully printed.',
	                'success'
	            )
	     return;
			
	}
	
	
}

function saveFinalNote(baseUrl,studentSessionId){
	openNav('COMPUTERIZE CANDIDATE FINAL NOTE | ENTREZ LA NOTE FINALE DU CANDIDAT', '', '50%');
	var url =baseUrl+"/studentSession/examenResult?studentSessionId="+studentSessionId ;  
	doGet(url,'contenu');
}

function printTranscriptPreview(baseUrl,studentSessionId){
	var eligibleCenterId = 0;
	var url =baseUrl+"/pv/printTranscriptPreview?studentSessionId="+studentSessionId + "&eligibleCenterId=" + eligibleCenterId;
	openNav('TRANSCRIPT PREVIEW | PREVISUALIZATION DU RELEVE DES NOTES', '', '50%');
	doGet(url,'contenu');
	
}


function saveFinalResult(baseUrlOfSaveNoteController,studentSessionId){
	displayLoading("contenu");
	var note = document.getElementById("note").value;
	var url =baseUrlOfSaveNoteController+'?&studentSessionId='+studentSessionId+'&note='+note;
	
	        sendGet(url).then(function(responseSuccess){
		    // start response validate
		    if( responseSuccess.indexOf("ok") > -1){
		    	removeLoading("contenu");
		    	 closeNav();
		    	 swal.fire(
 		                'Student Final Mark !',
 		                'Your Student final note has been updated.',
 		                'success'
 		            )
				 return;   
            }
                // end response validate
            }
            ).catch(function(responseError){
                alert('echec ! '+responseError);
            });
	
}
	function uploadStudentDocumentInSession(studentSessionId){
    	swal.fire({
            title: 'Are you sure you want to Upload Student Documents ?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, Upload Documents!'
        }).then(function(result) {
            if (result.value) {
            	// first we call validation form
            	var baseUrlOfControllerForValidationForm="/simt/studentSession/get/validationForms/"+studentSessionId;
            	if(getBaseUrl(baseUrlOfControllerForValidationForm) != null ){
            		openNav('Upload Student Documents', '', '67%');
            		sendGet(getBaseUrl(baseUrlOfControllerForValidationForm)).then(function(getFormSuccess){
            		$("#contenu").html(getFormSuccess);
            		console.log("Upload Student Documents form loaded ...");


            	}).catch(function(getFormError){
        			alert('echec ! '+getFormError);
        		});

            }

            }
        });
    }
	
function printReceipt(url){
	
	alert(url);
	window.open(url);
	return;
}
	

	
function loadRegistrationApplicationPdf(studentSessionId){
	    alert("student id "+studentSessionId)
		var url = $("#baseUrl").val()+"/studentSession/printRegistrationForm?studentSessionId="+studentSessionId;

		window.open(url);
		}
	
	
function printSubscription(baseUrl , studentSessionId,language){
		var url =baseUrl+"/studentSession/printSubscription?studentSessionId="+studentSessionId+"&language="+language;
	    window.open(url);
	}

	function printSubscriptionEnglish(baseUrl , studentSessionId,language){
		var url =baseUrl+"/studentSession/printSubscription?studentSessionId="+studentSessionId+"&language="+language;
	    window.open(url);
	}

function registerPersonIdentityInformation(){
	displayLoading("kt_content");
	var formData = new FormData(); 

	
	formData.append("id",$("#studentSession-Id").val());
	formData.append("surnameCni",$("#surNameCni").val());
	formData.append("givenNameCni",$("#givenNameCni").val());
	formData.append("pobCni",$("#pobCni").val());
	formData.append("dobCni",$("#dobCni").val());
	formData.append("nic",$("#idCardNumber").val());
	formData.append("genderCni",$("#gendeCni").val());
    formData.append("fatherCni", $("#fatherCni").val());
    formData.append("motherCni", $("#motherCni").val());
    formData.append("professionCni",$("#professionCni").val());
    formData.append("addressCni",$("#addressCni").val());
    formData.append("issuedDateCni",$("#issuedDateCni").val());
    formData.append("heightCni",$("#heightCni").val());
    formData.append("language",$("#language").val());
    formData.append("diplome",$("#diplome").val());
    formData.append("diplomeIssuedDate",$("#diplomeIssuedDate").val());
    formData.append("diplomeIssuedPlace",$("#diplomeIssuedPlace").val());
    formData.append("diplomeOption",$("#diplomeOption").val());

	var url = $("#baseUrl").val()+"/student/save/idCardInfo?studentSessionId=" + $("#studentSession-Id").val();
	
	doPost(url,formData,"kt_content").then(result=>{
		 removeLoading("kt_content");			 
		 if (result.indexOf("ok") > -1) {
			 swal.fire(
					    'success!',
		            	'Student ID Card has been updated!!',
		            	'success'
	               );

           closeNav();
           refreshCandidateContent();
		 }
		// removeLoading("kt_content");

	 }).catch(error=>{
		 removeLoading("kt_content");
		 document.getElementById(error).classList.remove("kt-hidden")
		// alert("Candidate Session Created Successfully");
 
         emptyForm();
		 
	 });
  
}	
function refreshCandidateContent(){
	var manageFileButton = document.getElementById("search-candidate");
	var searchStudentButton = document.getElementById("searchStudentButton");
    var searchCandidateButton = document.getElementById("searchCandidateButton");
    var eligibleCenterId = document.getElementById("eligibleCenterIdl");
	var datefilter =document.getElementById("dateRangeId");
	var statusCandidates =document.getElementById("statusCandidates");
	var licenseNumber =document.getElementById("licenseNumber");

	if (licenseNumber.value) {
		manageFileButton.onclick();
	}else if(datefilter.value){
			searchStudentButton.onclick();
	}else if(statusCandidates.value !=" " && eligibleCenterId.value != 0){
            searchCandidateButton.onclick();
	}else{


			var baseUrlOfController="/simt/manageCandidate/list";
            doGet(getBaseUrl(baseUrlOfController),'kt_content');

		}

}
