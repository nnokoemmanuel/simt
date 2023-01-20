var searchButton = document.querySelector('#search-button');
searchButton.addEventListener('click', function(){
 var url = $("#baseUrl").val();
 var speciality = $("#speciality").val();
    if (speciality=="ALL") {
    	refreshCoursePage(url,true);
	}else{
		refreshCoursePage(url,false);
	}

});

function manageAction(value,id,entity){
	switch(value){
	case "action":
		return;
    		
	case "history":
		openNav('Course History | Historique du Cours', '', '50%');
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		break;
		
	default:
	   return;
	}
}

function editCourse(url, courseId){
	openNav('Course Edition | Edition du Cours', '', '50%');
	url +=  courseId;
	doGet(url,'contenu');
	
}

function showCourse(url, courseId){
	openNav('Course Edition | Edition du Cours', '', '50%');
	url +=  courseId;
	doGet(url,'contenu');
}

function deleteCourse(url, courseId){
	swal.fire({
        title: 'Are you sure you want to delete this course ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete Course !'
    }).then(function(result) {
        if (result.value) {
        	var baseUrlOfControllerFordeletion=url+courseId;
        	sendGet(baseUrlOfControllerFordeletion).then(function(getFormSuccess){
        		if(getFormSuccess.indexOf("OK") > -1){
        			
        			swal.fire(
	    					 'Success !',
	    					 'Course deleted successfuly !.',
	    					 'success'
	    			);
        			
        			url = url.split('/course/delete/')[0];
     		        var speciality = $("#speciality").val();
     		        if (speciality=="ALL") {
     		        	refreshCoursePage(url,true);
     		    	}else{
     		    		refreshCoursePage(url,false);
     		    	}
        			
        		}
        		if(getFormSuccess.indexOf("KO") > -1){
        			
        			swal.fire(
                            'Not Allowed!',
                            'Can not delete the course there are transcipts linked to it ! .',
                            'error'
                        );
        			
        			url = url.split('/course/delete/')[0];
     		        var speciality = $("#speciality").val();
     		        if (speciality=="ALL") {
     		        	refreshCoursePage(url,true);
     		    	}else{
     		    		refreshCoursePage(url,false);
     		    	}
                	return false;
        			
        		}

        	}).catch(function(getFormError){
    			alert('echec ! '+getFormError);
    		});
        		
        
            
        }
    });
}



/** function is to validate all user-inputs field in COURSE */

function courseBootstrapValidation(baseUrl,courseId){

	$("#courseFORMID")
		.bootstrapValidator(
				{    
	    	        
					fields : {   
						courseCompleteName : {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a Complete Course Name'
    						}

    					}
    				},
    				courseCoefficient: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  Course Coefficient'
    						}
    					}
    				},
    				courseMaxNote : {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  Course MaxNote'
    						}
    					}
    				},
    				courseModule: {
    					validators : {
    						notEmpty : {
    							message : 'Required field / Champ requis :: Please enter a  valid course Module '
    						}
    					}
    				},
    			}
    	});

		var validator = $("#courseFORMID").data('bootstrapValidator').validate();

		if (validator.isValid()) {
			//alert("isValid");
			createCourse(baseUrl,courseId);
		}else{
			//alert("isNotValid");
		    swal.fire(
		    		'Form validation error',
		    		'Please fill in all the fields !.',
		    		'error'
		        )
			return;   	
		}
}





function createCourse(baseUrl,courseId) {

	var originalBaseUrl = baseUrl;
	var formData = new FormData();
	if(courseId !== 0){
		formData.append("id",courseId);
	}
	
	formData.append("moduleId",$("#courseModule").val());
	formData.append("completeName",$("#courseCompleteName").val());
	formData.append("courseCoeff",$("#courseCoefficient").val());
    formData.append("courseMaxNote",$("#courseMaxNote").val());
 
    var courseForm=formData;
	let courseCompleteName=document.querySelector("#courseCompleteName");
	let courseModule=document.querySelector("#courseModule");
    if(courseCompleteName.value==""||courseModule.value==0) {
    	if(courseCompleteName.value==""){
    		swal.fire(
                    'Not Allowed!',
                    'Please fill the course name.',
                    'error'
                );
        	return false;
    		
    	}else{
    		swal.fire(
                    'Not Allowed!',
                    'Please select the course module.',
                    'error'
                );
        	return false;
    		
    	}
    	
    }
    
	displayLoading("courseFormId");
	
    baseUrl=baseUrl+"/course/create"
	
     	 
    doPost(baseUrl,formData,"kt_content").then(result=>{
    		 removeLoading("courseFormId");
    		 
    		 if(result.indexOf("OK") > -1){
    			closeNav();
    			var url = originalBaseUrl;
 		        var speciality = $("#speciality").val();
 		        if (speciality=="ALL") {
 		        	refreshCoursePage(url,true);
 		    	}else{
 		    		refreshCoursePage(url,false);
 		    	}
 		        
	            if(courseId === 0){
	            	  swal.fire(
		    					 'Success !',
		    					 'Course created successfuly !.',
		    					 'success'
		    			)	;
	            	  
	             }else{
	            	  swal.fire(
		    					 'Success !',
		    					 'Course edited successfuly !.',
		    					 'success'
		    			)	;
	            	  
	             }
		    		  
    		 }else 
    			swal.fire(
    					 'Failed !',
    					 'Course not registered .',
    					 'error'
    	        )	; 
    		
    	 }).catch(error=>{
    		 removeLoading("courseFormId");
    		 closeNav();
    		 swal.fire(
    					 'Failed !',
    					 'Something went wrong at our end.',
    					 'error'
    	       ); 
    		
    		 
    	 });
    	
    	
    	
  
}


function refreshCoursePage(url,isGlobalLoading){
	if(isGlobalLoading){
	  	doGet(url+'/course/list/ALL','kt_content');
	  	active_link('courses-and-modules-management');
	  	return false;

	}else{
		searchCourse(url);
	}
	
}


function searchCourse(url){
	var speciality = $("#speciality").val();
	if (speciality != "ALL") {
		var str = "";
		str += "/course/list/" + speciality ;
		url += str;
		doGet(url, "kt_content");
	}
	

}
