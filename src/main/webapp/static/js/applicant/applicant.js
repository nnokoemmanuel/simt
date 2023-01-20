function verifdivBlock1() {

	$("#divBlock")
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
	var validator = $("#divBlock").data('bootstrapValidator').validate();
	if (validator.isValid()) {
		displayLoading("load-preview-step1");
		
		var license = document.getElementById("licenseNum").value.trim();
		var speciality = document.getElementById("speciality").value;
		var diplome = document.getElementById("diplome").value;

            	var link = $("#baseUrl").val() + "/applicant/verify/applicant/license";
            	url = link +"?license="+license+"&speciality="+speciality+"&diplome="+diplome;
           
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
	        		                'Student Rejected!',
	        		                'Your Student can not be registered... <br><strong>Student does not match adequate categories prerequisites</strong>',
	        		                'warning'
	        		            )
						 return;	
            			}
            			
            			if(applicantDatas.can_be_register =="KO.DO.NOT.HAVE.CAPEC"){
            				swal.fire(
	        		                'Student Rejected!',
	        		                'Your Student can not be registered... <br><strong>Student must archive his CAPEC <br> Or <br> no prerequisites certificates (CAPEC) found</strong>',
	        		                'warning'
	        		            )
						 return false;	
            			}
            	
            			document.getElementById('surName').value = applicantDatas.sn;
            			document.getElementById('givenName').value = applicantDatas.gn;
            			document.getElementById('dob').value = applicantDatas.dob;
            			document.getElementById('pob').value = applicantDatas.pob;
            			document.getElementById('catBdate').value = applicantDatas.catB_Date;
            			//document.getElementById('nationality').value = applicantDatas.nationality;
            			document.getElementById('surName').readOnly = true;
                        document.getElementById('givenName').readOnly = true;
                        document.getElementById('dob').readOnly = true;
                        document.getElementById('pob').readOnly = true;
                        document.getElementById('catBdate').readOnly = true;
                        
        //document.getElementById('nationality').value = applicantDatas.country;

                        document.getElementById("divBlock2").style.display = "block";
                        document.getElementById("divBlock").style.display = "none";
            			
            			}
            	};
            	xmlHttp.send(null);
            	//end code to retrieve datas from API
	
    }
}

function loadApplicant(url, result){

	var entranceEligibleCenterId = $("#entranceEligibleCenter").val();
	url += "?result=" + result;
	url += "&entranceEligibleCenter=" +entranceEligibleCenterId;
	doGet(url,'applicant-list');
	document.getElementById("list-indicator").value = result;
}

function manageApplicant(url, student, result, resultIndicator){
	displayLoading("kt_content");
	var rowId= "applicant-" + student;
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
    	  swal.fire(
					 'Successful !',
					 'Applicant result updated',
					 'success'
	   );
    	  rowIndex.innerHTML=" ";
      }else{
      }
  };
  xmlHttp.send(null);
}

/**
 * Create or update an applicant
 * @returns
 */

function registerApplicant(){
	displayLoading("kt_content");
	var entranceEligibleCenterId = $("#entranceEligibleCenter").val();
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
    formData.append("trainingCenterChoice1",$("#trainingCenterChoice1").val());
    formData.append("trainingCenterChoice2",$("#trainingCenterChoice2").val());
    formData.append("trainingCenterChoice3",$("#trainingCenterChoice3").val());
    formData.append("diplome", $("#diplome").val());

    if($("#phoneNumber").val().trim() ==""){
    removeLoading("kt_content");
    	swal.fire(
                'Please enter the phone number of the student '
                
            )
			return;
    	
    }
    if($("#gender").val() ==""){
    removeLoading("kt_content");
    	swal.fire(
    			'Validation Formular',
                'Gender is a required field',
    			'error'
            )

			return;
    	
    }
 /*   if($("#trainingCenterChoice1").val() =="" || $("#trainingCenterChoice2").val() =="" || $("#trainingCenterChoice3").val() ==""){
        removeLoading("kt_content");
        	swal.fire(
        			'Validation Formular',
                    'Le formation est un champ obligatoire /Training center is a required field',
        			'error'
                )
            return;

        }*/
         if($("#trainingCenterChoice1").val() !="" && $("#trainingCenterChoice2").val() !="" ){
            if($("#trainingCenterChoice1").val() == $("#trainingCenterChoice2").val() ){
                            removeLoading("kt_content");
                            	swal.fire(
                            			'Validation Formular',
                                        'Entrez deux différent  choix de centre de formation/ Enter three different choice of training center',
                            			'error'
                       )
                     return;

                }
         }
          if($("#trainingCenterChoice1").val() !="" && $("#trainingCenterChoice2").val() !="" && $("#trainingCenterChoice3").val() !=""){
               if($("#trainingCenterChoice1").val() == $("#trainingCenterChoice2").val() || $("#trainingCenterChoice1").val() == $("#trainingCenterChoice3").val() || $("#trainingCenterChoice2").val() == $("#trainingCenterChoice3").val()){
                               removeLoading("kt_content");
                               	swal.fire(
                               			'Validation Formular',
                                           'Entrez trois différent  choix de centre de formation/ Enter three different choice of training center',
                               			'error'
                          )
                        return;

                   }
          }

         if($("#trainingCenterChoice1").val() =="" ){
             removeLoading("kt_content");
             	swal.fire(
             			'Validation Formular',
                         'Le premier choix du centre de formation est un champ obligatoire /Training center is a required field',
             			'error'
                     )
                 return;

             }


		var url = $("#baseUrl").val()+"/applicant/createPost?entranceEligibleCenterId="+entranceEligibleCenterId;
		
		doPost(url,formData,"kt_content").then(result=>{
			 removeLoading("add_candidate_form");
			 if (result == "dupilcation.of.applicant") {
		   	    	swal.fire(
			                'This applicant already exist in the system!',
			                'Ce candidat exist dans le system!.',
			                'error'
			            )
						return;
					} 
				  swal.fire(
					    'success!',
		            	'Candidate Session Created Successfully.',
		            	'success'
	               );
			 
			 doGet( $("#baseUrl").val()+ "/expertPv/manage_applicants?entranceEligibleCenterId="+entranceEligibleCenterId,"kt_content");
			 
		 }).catch(error=>{
			 removeLoading("add_candidate_form");
			 document.getElementById(error).classList.remove("kt-hidden");
	 
	         emptyForm();
			 
		 });

	}

function createStudent(url, applicantId ){
	displayLoading("kt_content");
	var rowId= "applicant-" + applicantId;
	var rowIndex = document.getElementById(rowId);
	var trainingCenterSelect = document.getElementById("training-center");
	var trainingCenterId = trainingCenterSelect.options[trainingCenterSelect.selectedIndex].value;

	url += "?applicantId=" + applicantId;
	url += "&trainingCenterId=" + trainingCenterId;

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
    	  alert("right");
    	  removeLoading("kt_content");
    	  closeNav();
    	  if (result == "dupilcation.of.student") {
	   	    	swal.fire(
		                'This student already exist in the system!',
		                'Ce candidat exist dans le system!.',
		                'error'
		            )
					return;
				} 
    	  
    	  if (result == "training.center.max.student.zero") {
	   	    	swal.fire(
		                'No space available in this school!',
		                'Pas de place disponible dans cette école!.',
		                'error'
		            )
					return;
				} 
    	  
    	  swal.fire(
					 'Successful !',
					 'Applicant result updated',
					 'success'
	   );
    	  rowIndex.innerHTML=" ";
      }else{
      }
  };
  xmlHttp.send(null);
}
function saveFinalResult(url, applicantId , entranceEligibleCenterId){
	displayLoading("kt_content");
	//var rowId= "applicant-" + applicantId;
	//var rowIndex = document.getElementById(rowId);
	var note = document.getElementById("note").value;

	url += "?applicantId=" + applicantId;
	url += "&note=" + note;

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
    	  closeNav();
    	  swal.fire(
					 'Successful !',
					 'Applicant final exam result updated',
					 'success'
	   );
	    	$("#applicant-list").html(xmlHttp.responseText);
    	  rowIndex.innerHTML=" ";
      }
  };
  xmlHttp.send(null);
}


function orderApplicant(){
	displayLoading("kt_content");
	var entranceEligibleCenterId = $("#entranceEligibleCenter").val();
	
	doGet( $("#baseUrl").val()+ "/expertPv/orderApplicants?entranceEligibleCenterId="+entranceEligibleCenterId,"kt_content");
	
	var xmlHttp     =   getXMLHttp();
	xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
	if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
  	  alert("right");
  	  removeLoading("kt_content");
  	  closeNav();
  	  
  	  if (result == "training.center.max.student.zero") {
	   	    	swal.fire(
		                'No space available in this school!',
		                'Pas de place disponible dans cette école!.',
		                'error'
		            )
					return;
				} 
  	  
  	  swal.fire(
					 'Applicants successfully assigned to training Centers !',
					 'Candidats affectes avec succes aux centres de formation',
					 'success'
	   );
  	  rowIndex.innerHTML=" ";
    }else{
    }
};
xmlHttp.send(null);
	
}

