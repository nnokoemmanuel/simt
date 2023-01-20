
	 
	 function check(e) {

	        if (e.checked == false){
	            e.checked=true;
	        }else{
	            e.checked=false;
	        }
		 //   alert("ok");
		}

/**  Ajax Call : UserGroupForm
	 
function createUserGroup(){
    checkIfARoleHasBeenSelected();
	//displayLoading(content);
	 alert("userGroup");

	var userGroup= "groupName="+$("#group_name").val();
        userGroup += "&role="+getSelectedRoleValue();
	var url = $("#baseUrl").val()+"/marine/group/createPost";

	var xmlHttp     =   getXMLHttp();
    xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
      //return error page if status code is not 200
      if(xmlHttp.status !== 200){
    	       customAlert("Something whent wrong on the server!");
          return;
        }
      if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
    	       customAlert("Request complete");
		}
	};
	xmlHttp.send(userGroup);
	 alert("emms"+userGroup);
}*/

function checkIfARoleHasBeenSelected(){
	 var textinputs = document.querySelectorAll('input[type=checkbox]');
     var empty = [].filter.call( textinputs, function( el ) {
		     return !el.checked
     });

	 if (textinputs.length == empty.length){
		 alert("Please select a role !|Veuillez sélectionner un rôle!");
		 die();
		 return undefined;
	 }

}

function getSelectedRoleValue(){
     var tenpom=0;
     var allCheck = new Array();
	 var textinputs = document.querySelectorAll('input[type=checkbox]');
     var selectedRoleCheckbox = [].filter.call( textinputs, function( el ) {
		     return el.checked
		  })
		  if(selectedRoleCheckbox.length==1){
			  return selectedRoleCheckbox[0].value;
		  }else if(selectedRoleCheckbox.length>1){
			  for(var i=0; i< selectedRoleCheckbox.length ;i++){
                tenpom= selectedRoleCheckbox[i].value;
                allCheck += tenpom+'/';
			  }
              return allCheck;
		  }else{
           	alert("Select adleast one Role !|Sélectionnez un rôle au moins!");
            return undefined;
      }
}

function createUserGroup(){

    var group_name = document.getElementById("group_name").value;
         if (group_name === "") {
           alert("Entrez le nom du groupe ! |Enter the name of the group !");
           die();
         }
    checkIfARoleHasBeenSelected();
	var name= $("#group_name").val();
	var url = $("#baseUrl").val()+"/marine/group/createPost?groupName="+name+"&role="+getSelectedRoleValue()+"";
    doGet(url,'kt_content');
}

function editUserGroup(id){

    var group_name = document.getElementById("group_name").value;
         if (group_name === "") {
           alert("Entrez le nom du groupe !|Enter the name of the group !");
           die();
         }
    //     alert(id);
    checkIfARoleHasBeenSelected();
	//displayLoading(content);
	// alert("userGroup");
	var name= $("#group_name").val();

	var url = $("#baseUrl").val()+"/marine/user/group/edit/save?groupName="+name+"&role="+getSelectedRoleValue()+"&id="+id;
	doGet(url,'kt_content');
}
