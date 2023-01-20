var searchButton = document.querySelector('#search-button');
searchButton.addEventListener('click', function(){
 var url = $("#baseUrl").val();
 var speciality = $("#speciality").val();
    if (speciality=="ALL") {
    	refreshModulePage(url,true);
	}else{
		refreshModulePage(url,false);
	}

});

function manageAction(value,id,entity){
	switch(value){
	case "action":
		return;
    		
	case "history":
		openNav('Module History | Historique du Module', '', '50%');
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		break;
		
	default:
	   return;
	}
}

function editModule(url, moduleId){
	openNav('Module Edition | Edition du Module', '', '50%');
	url +=  moduleId;
	doGet(url,'contenu');
	
}

function showModule(url, moduleId){
	openNav('Module Edition | Edition du Module', '', '50%');
	url +=  moduleId;
	doGet(url,'contenu');
}

function deleteModule(url, moduleId){
	swal.fire({
        title: 'Are you sure you want to delete this module ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete Module !'
    }).then(function(result) {
        if (result.value) {
        	var baseUrlOfControllerFordeletion=url+moduleId;
        	sendGet(baseUrlOfControllerFordeletion).then(function(getFormSuccess){
        		if(getFormSuccess.indexOf("OK") > -1){
        			
        			swal.fire(
	    					 'Success !',
	    					 'Module deleted successfuly !.',
	    					 'success'
	    			);
        			
        			url = url.split('/module/delete/')[0];
     		        var speciality = $("#speciality").val();
     		        if (speciality=="ALL") {
     		        	refreshModulePage(url,true);
     		    	}else{
     		    		refreshModulePage(url,false);
     		    	}
        			
        		}
        		if(getFormSuccess.indexOf("KO") > -1){
        			
        			swal.fire(
                            'Not Allowed!',
                            'Can not delete the Module there are transcipts linked to it ! .',
                            'error'
                        );
        			
        			url = url.split('/module/delete/')[0];
     		        var speciality = $("#speciality").val();
     		        if (speciality=="ALL") {
     		        	refreshModulePage(url,true);
     		    	}else{
     		    		refreshModulePage(url,false);
     		    	}
                	return false;
        			
        		}
        		if(getFormSuccess.indexOf("CLASSIFICATION-EXISTS") > -1){
        			
        			swal.fire(
                            'Not Allowed!',
                            'enter another classification number this one already belong to another module ! .',
                            'error'
                        );
        			
        			url = url.split('/module/delete/')[0];
     		        var speciality = $("#speciality").val();
     		        if (speciality=="ALL") {
     		        	refreshModulePage(url,true);
     		    	}else{
     		    		refreshModulePage(url,false);
     		    	}
                	return false;
        			
        		}

        	}).catch(function(getFormError){
    			alert('echec ! '+getFormError);
    		});
        		
        
            
        }
    });
}





/** function is to validate all user-inputs field in MODULE */

function moduleBootstrapValidation(baseUrl,moduleId){

	$("#moduleFORMIDs")
		.bootstrapValidator({
				fields : {
					  moduleCompleteName : {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a module Complete Name'
    						}

    					}
    				},
    				moduleMinNote: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  module MinNote'
    						}
    					}
    				},
    				modulePercentage: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  module Percentage'
    						}
    					}
    				},
    				moduleClassification: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid module Classification '
    						}
    					}
    				},
    				moduleSpeciality: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis'
    						}
    					}
    				},
    			}
    	});


		var validator = $("#moduleFORMIDs").data('bootstrapValidator').validate();

		if (validator.isValid()) {

			createModule(baseUrl,moduleId);
		}else{

		    swal.fire(
		    		'Form validation error',
		    		'Please fill in all the fields !.',
		    		'error'
		        )
			return;   	
		}

}





function createModule(baseUrl,moduleId) {

	var originalBaseUrl = baseUrl;
	var formData = new FormData();
	if(moduleId !== 0){
		formData.append("id",moduleId);
	}
	formData.append("completeName",$("#moduleCompleteName").val());
	formData.append("specialityAbbreviation",$("#moduleSpeciality").val());
	formData.append("moduleClassification",$("#moduleClassification").val());
	formData.append("moduleMinNote",$("#moduleMinNote").val());
	formData.append("modulePercentage",$("#modulePercentage").val());

    var moduleForm=formData;
	let moduleCompleteName=document.querySelector("#moduleCompleteName");
	let moduleSpeciality=document.querySelector("#moduleSpeciality");
    if(moduleCompleteName.value==""||moduleSpeciality.value=="ALL") {
    	if(moduleCompleteName.value==""){
    		swal.fire(
                    'Not Allowed!',
                    'Please fill the module name.',
                    'error'
                );
        	return false;
    		
    	}
        if(moduleSpeciality.value=="ALL"){
    		swal.fire(
                    'Not Allowed!',
                    'Please select the module speciality.',
                    'error'
                );
        	return false;
    		
    	}
    	
    }
    
	displayLoading("moduleFormId");
	
    baseUrl=baseUrl+"/module/create"
	
     	 
    doPost(baseUrl,formData,"kt_content").then(result=>{
    		 removeLoading("moduleFormId");
    		 
    		 if(result.indexOf("OK") > -1){
    			closeNav();
    			var url = originalBaseUrl;
 		        var speciality = $("#speciality").val();
 		        if (speciality=="ALL") {
 		        	refreshModulePage(url,true);
 		    	}else{
 		    		refreshModulePage(url,false);
 		    	}
 		        
	            if(moduleId === 0){
	            	  swal.fire(
		    					 'Success !',
		    					 'Module created successfuly !.',
		    					 'success'
		    			)	;
	            	  
	             }else{
	            	  swal.fire(
		    					 'Success !',
		    					 'Module edited successfuly !.',
		    					 'success'
		    			)	;
	            	  
	             }
		    		  
    		 }else 
    			swal.fire(
    					 'Failed !',
    					 'Module not registered .',
    					 'error'
    	        )	; 
    		
    	 }).catch(error=>{
    		 removeLoading("moduleFormId");
    		 closeNav();
    		 swal.fire(
    					 'Failed !',
    					 'Something went wrong at our end.',
    					 'error'
    	       ); 
    		
    		 
    	 });
    	
    	
    	
  
}





function refreshModulePage(url,isGlobalLoading){
	if(isGlobalLoading){
	  	doGet(url+'/module/list/ALL','kt_content');
	  	active_link('courses-and-modules-management');
	  	return false;

	}else{
		searchModule(url);
	}
	
}


function searchModule(url){
	var speciality = $("#speciality").val();
	if (speciality != "ALL") {
		var str = "";
		str += "/module/list/" + speciality ;
		url += str;
		doGet(url, "kt_content");
	}
	

}
