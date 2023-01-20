"use strict";

// Class definition
var KTUserAdd = function () {
	// Base elements
	var wizardEl;
	var formEl;
	var validator;
	var wizard;
	var avatar;

	// Private functions
	var initWizard = function () {
		// Initialize form wizard
		wizard = new KTWizard('kt_user_add_user', {
			startStep: 1, // initial active step number
			clickableSteps: true  // allow step clicking
		});

		// Validation before going to next page
		wizard.on('beforeNext', function(wizardObj) {
			if (validator.form() !== true) {
				wizardObj.stop();  // don't go to the next step
			}
		})

		// Change event
		wizard.on('change', function(wizard) {
			KTUtil.scrollTop();
		});
	}

	var initValidation = function() {
		validator = formEl.validate({
			// Validate only visible fields
			ignore: ":hidden",

			// Validation rules
			rules: {
				// Step 1
				/*kt_user_add_user_avatar: {
					required: true
				},*/
				first_name: {
					//required: true
				},
				last_name: {
	                required: true,
				},
				phone_number: {
					required: true,
				},
				/*email_address: {
					required: true,
					email: true
				},*/
				password: {
					required: true,
				
				},
				password_confirm: {
					required: true
				},
				gender: {
					required: true
				},
				group: {
					required: true
				},
				activate: {
					
				},
				pob: {
					required: true
				},
				dob: {
					required: true
				}
			},

			// Display error
			invalidHandler: function(event, validator) {
				KTUtil.scrollTop();

				swal.fire({
					"title": "",
					"text": "There are some errors in your form.|Il y a des erreurs dans votre formulaire.",
					"type": "error",
					"buttonStyling": false,
					"confirmButtonClass": "btn btn-brand btn-sm btn-bold"
				});
			},

			// Submit valid form
			submitHandler: function (form) {

			}
		});
	}

	var initSubmit = function() {
		var btn = formEl.find('[data-ktwizard-type="action-submit"]');
		btn.on('click', function(e) {
			e.preventDefault();

			if (validator.form()) {
				// See: src\js\framework\base\app.js
				KTApp.progress(btn);
				//KTApp.block(formEl);

				// See: http://malsup.com/jquery/form/#ajaxSubmit
				createUser();
				
			}
		});
	}

	var initUserForm = function() {
		avatar = new KTAvatar('kt_user_add_avatar');
	}

	return {
		// public functions
		init: function() {
			formEl = $('#kt_user_add_form');

			initWizard();
			initValidation();
			initSubmit();
			initUserForm();
		}
	};
}();

jQuery(document).ready(function() {
	KTUserAdd.init();
});



/**
 * Create or update a user
 * @returns
 */
function createUser(){
	displayLoading("kt_content");
	
	var user= "firstName="+$("#first_name").val();
	user += "&lastName="+$("#last_name").val();
	user += "&dob="+$("#dob").val();
	user += "&pob="+$("#pob").val();
	user += "&gender="+$("#gender").val();
	user += "&active="+$("#activate").val();
	user += "&group="+$("#group").val();
	user += "&phoneNumber="+$("#phone_number").val();
	user += "&office="+$("#office").val();
    user += "&trainingCenter="+$("#trainingCenter").val();

	let roles=document.querySelectorAll("[rolecheck]");
	let userroles="";
	roles.forEach(role => {
		 if(role.checked==true) userroles+=role.value+",";
	});
	user += "&roles="+userroles.slice(0,-1);
	if($("#userId").val()==""){
		user += "&password="+$("#password").val();
		user += "&username="+$("#pseudo").val();
		user += "&email="+$("#email_address").val();
		//user += "&id=0";
		var url = $("#baseUrl").val()+"/marine/user/createPost";
	}else{
		var url = $("#baseUrl").val()+"/marine/user/update";
		user += "&id="+$("#userId").val();
		user += "&email="+$("#email_address").val();
	}
	
	var xmlHttp     =   getXMLHttp();
    xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
      if(xmlHttp.status !== 200){
    	  removeLoading("kt_content");
    	  swal.fire(
					 'Failed !|Échoué !',
					 'Server error | Erreur du serveur',
					 'error'
	   )	; 
          return;
      }
      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
    	  removeLoading("kt_content");
    	  if(xmlHttp.responseText=="email.used" || xmlHttp.responseText == "username.used"){
    		  swal.fire(
 					 'Failed !',
 					 'Email or username already taken.|Email ou nom utilisateur déjà pris',
 					 'error'
 	   )	;     		  return;
    	  }
    	  var entityID = xmlHttp.response;
    	  uploadFile(entityID,0,'user' ,'profile_pic');
          doGet($("#baseUrl").val() + "/marine/user/create","kt_content");
          swal.fire(
					 'Registered !',
					 'Operation performed successfully|Opération effectué avec succès',
					 'success'
	   )	; 
      }else{
      }
  };
  xmlHttp.send(user);
}

function loadGroupRole(){
	var groupId = $("#group").val();
	var url = $("#baseUrl").val()+"/marine/user/listGroupRole?id="+groupId;
	var xmlHttp     =   getXMLHttp();
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
 
      if(xmlHttp.status !== 200){
    	  swal.fire(
					 'Failed !',
					 'Email or username already taken.|E-mail ou nom dutilisateur déjà pris.',
					 'error'
	   )	;     		  return; 
    	
      }
      if( xmlHttp.status === 200){
    	  
    	  var hh = document.getElementById("role-content");
    	  hh.innerHTML =xmlHttp.responseText ;
    	
    	  
          
      }
  };
  xmlHttp.send(groupId);
}

function loadGroupRoleUpdate(){
	
	var groupId = $("#group").val();
	var userId = $("#userId").val();	
	var oldGroupId = $("#oldGroupId").val();
	var url = $("#baseUrl").val()+"/marine/user/listGroupRoleUpdate?id="+groupId + "&userId=" + userId + "&oldGroupId=" + oldGroupId;
	var xmlHttp     =   getXMLHttp();
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
 
      if(xmlHttp.status !== 200){
    	  swal.fire(
					 'Failed !',
					 'Email or username already taken.|E-mail ou nom dutilisateur déjà pris.',
					 'error'
	   )	;     		  return; 
    	
      }
      if( xmlHttp.status === 200){
    	  
    	  var hh = document.getElementById("role-content");
    	  hh.innerHTML =xmlHttp.responseText ;
    	
    	  
          
      }
  };
  xmlHttp.send(groupId);
}


