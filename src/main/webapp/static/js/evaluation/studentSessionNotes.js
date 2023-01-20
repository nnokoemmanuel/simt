/*$("input[type=number]").focusin(function(){
	

  for(var i = 0 ; i< $(this).length ; i++){
	  alert($(this)[i]);
	  var num = $(this)[i].value.match(/^\d+$/);
	  
		if(num == null){
			if(parseInt($(this)[i].value) <= 20){
				swal.fire(
		                'Invalid Information !',
		                'Please fill note field with a number <= 20 !',
		                'error'
		            );
				
			}
			
		}
	  $(this)[0].style="";
  }
	
}) ; */

function saveStudentNotes(url,studentSessionId){
    var tableRows = document.getElementById("table-studentSession-notes").rows;
	var numRows = document.getElementById("table-studentSession-notes").rows.length; 
	var studentSessionNotesJsonText ='';
	for(var i = 0 ; i < numRows ; i++){
		var rowId = tableRows[i].getAttribute("id");
			if( rowId.localeCompare('headers') != 0 ){
				var idCourse =rowId.split('course-row-')[1];
				var courseNote = document.getElementById("input-course-note-"+idCourse).value;
				
				if(courseNote == undefined || courseNote == ''  ){
					 swal.fire(
				                'Please fill All Courses Note !',
				                'you did not enter  note value for line number  '+i,
				                'error'
				            );
					 document.getElementById("course-row-"+idCourse).style.backgroundColor="#ffcccc";
	           		 return false;
				}
				
				studentSessionNotesJsonText+=idCourse+':'+courseNote;
				
				if( i < numRows-1){
					studentSessionNotesJsonText+=',';	
				}
				
				
			}
	    
		
	}
	
	//var studentSessionNotesJson = JSON.parse(studentSessionNotesJsonText);
	url+="?studentSessionId="+studentSessionId;
	url += "&studentSessionNotes=" + encodeURIComponent(studentSessionNotesJsonText);
	displayLoading("contenu");
    var xmlHttp     =   getXMLHttp();
    xmlHttp.open("POST", url, false);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
      if(xmlHttp.status !== 200){
      	$("#contenu").html(xmlHttp.responseText);      	
          return;
      }
      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
    	  if(xmlHttp.responseText=="ok"){
	      	closeNav();
	      	swal.fire("Student Notes successfully saved ! | Notes de l etudiant bien enrégistrées! ");
    	  }
      }
    };
   xmlHttp.send(null);

}
