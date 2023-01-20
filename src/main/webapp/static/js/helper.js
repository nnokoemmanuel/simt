/*
 *Ce fichier contient les foctions succeptibles d'être utilisées dans plusieurs modules
 */

/**
 * Remove loading
 * @param idResultContener
 * @returns
 */

function displayLoading(idResultContener){
	var loading = document.getElementById("lds-roller");
    if(loading == null){
        maDiv0 = document.createElement("div");
        maDiv1 = document.createElement("div");
        maDiv2 = document.createElement("div");
        maDiv3 = document.createElement("div");
        maDiv4 = document.createElement("div");
        maDiv5 = document.createElement("div");
        maDiv6 = document.createElement("div");
        maDiv7 = document.createElement("div");
        maDivL = document.createElement("div");
        maDivL.id = 'lds-roller';
        maDivL.className='lds-roller';
        maDivL.append(maDiv0);
        maDivL.append(maDiv1);
        maDivL.append(maDiv2);
        maDivL.append(maDiv3);
        maDivL.append(maDiv4);
        maDivL.append(maDiv5);
        maDivL.append(maDiv6);
        maDivL.append(maDiv7);
        document.getElementById(idResultContener).append(maDivL);
    }

   // alert("MOISE");
    //removeLoading(idResultContener);
}

function removeLoading(idResultContener){
    //alert("moise");
	var loading = document.getElementById("lds-roller");
	loading.remove();

}
function doget(url) {
    return new Promise(function (resolve, reject) {
        var xhr = new XMLHttpRequest();

        xhr.onload = function (event) {
            resolve(xhr.responseText); // Si la requête réussit, on résout la promesse en indiquant le contenu du fichier
        };

        xhr.onerror = function (err) {
            reject(err); // Si la requête échoue, on rejette la promesse en envoyant les infos de l'erreur
        }

        xhr.open('GET', url, true);
        xhr.send(null);
    });
}

function uploadFile(entityID,number,entity ,inputFileId ){
	var baseUrlOfController="/simt/file/upload";
	var formData = new FormData();
	if(getBaseUrl(baseUrlOfController) != null ){

		var url = getBaseUrl(baseUrlOfController);
		if(document.querySelector("#"+inputFileId).files.length==0){
			//alert("file length is 0;")
			return;
		}

		var fileDoc=document.querySelector("#"+inputFileId).files[0];
		var fileName=document.querySelector("#"+inputFileId).files[0].name;
		

		return new Promise(function (resolve, reject) {
		        var xhr     =   getXMLHttp();

		        xhr.onload = function (event) {
		            resolve(xhr.responseText); // Si la requête réussit, on résout la promesse en indiquant le contenu du fichier
		        };

		        xhr.onerror = function (err) {
		            reject(err); // Si la requête échoue, on rejette la promesse en envoyant les infos de l'erreur
		        }
		       
		    	formData.append("file",document.querySelector("#"+inputFileId).files[0]);
		    	formData.append("fileName",document.querySelector("#"+inputFileId).files[0].name);
		    	formData.append("entityID",entityID);
		    	formData.append("entity",entity);
		        formData.append("number",number);
		     
		        xhr.open("POST", url, false);
		        var fileUploadForm=formData;
		        xhr.send(fileUploadForm);
		    });
	}	
}

function getBaseUrl(baseUrlOfController){

	    var routeBase = '';
	    var contextPath='/simt';
	    if(window.location.href.indexOf(contextPath) >-1 ){
	         routeBase = window.location.href.split(contextPath)[0]+contextPath+baseUrlOfController;
	    }else
	    	return null;
	   return routeBase;

}

/**
* Custom alert
*/
function customAlert(message){
	swal({
        buttonsStyling: false,
        confirmButtonClass: 'btn btn-primary',
        text:message,
    }).catch(swal.noop)
}
function customDangerAlert(message){
	swal({
        buttonsStyling: false,
        confirmButtonClass: 'btn btn-danger',
        text:message,
        type:"error",
    }).catch(swal.noop)
}


function openNav(entete, message,width) {
//pour le test
//var message =  "<h5>Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré Je suis entrain de lire le tuto sur javascript, et je vois ca :innerH TML permet de récupérer le code HTML enfant un élément sous forme de texte.Le fonctionnement de textContent est le même qu innerHTML excepté le fait que seul le texte est récupéré<h5>";

    // Get the modal
    var modal = document.getElementById("myModal");
    var span = document.getElementsByClassName("close")[0];
      modal.style.display = "block";
   // }
     var title = document.getElementById("title").innerHTML = entete;
     var contenu = document.getElementById("contenu").innerHTML = message;
     let modalContent=document.querySelector(".modal-content");
     if (window.matchMedia("(max-width: 768px)").matches) {
         modalContent.style.maxWidth="100%";
     }else modalContent.style.maxWidth=width;
     
    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
      modal.style.display = "none";
    }
    // When the user clicks anywhere outside of the modal, close it
   /* window.onclick = function(event) {
      if (event.target == modal) {
        modal.style.display = "none";
      }
    }*/
}

function active_link(id){
	if(document.getElementById("create-user")!=null) document.getElementById("create-user").className="kt-menu__item";
	if(document.getElementById("list-group")!=null) document.getElementById("list-group").className="kt-menu__item";
	if(document.getElementById("file-management")!=null) document.getElementById("file-management").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
	if(document.getElementById("archive-capacity")!=null) document.getElementById("archive-capacity").className="kt-menu__item";
	if(document.getElementById("archive-pv")!=null) document.getElementById("archive-pv").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
	if(document.getElementById("slip-management")!=null) document.getElementById("slip-management").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
	if(document.getElementById("concours-experts")!=null) document.getElementById("concours-experts").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
    if(document.getElementById("courses-and-modules-management")!=null) document.getElementById("courses-and-modules-management").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
	if(document.getElementById("exam-center")!=null) document.getElementById("exam-center").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
	if(document.getElementById("session-management")!=null) document.getElementById("session-management").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
	if(document.getElementById("student-management")!=null) document.getElementById("student-management").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";
	if(document.getElementById("succeeded-student-management")!=null) document.getElementById("succeeded-student-management").className="kt-menu__item kt-menu__item--open kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown ";

	if(document.getElementById("action")!=null) document.getElementById("action").className="kt-menu__item";
	if(document.getElementById("home-1")!=null) document.getElementById("home-1").className="kt-menu__item";
	if(document.getElementById("home-2")!=null) document.getElementById("home-2").className="kt-menu__item";
	document.getElementById(id).classList.add("kt-menu__item--active");
}
/**
 * shows file name on input when uploading a file
 * @param fileInput
 * @returns
 */

function autoPopulateFilename(fileInput){
	var fileName = fileInput.val();
	fileInput.next('.custom-file-label').addClass('selected').html(fileName);
}
/*
function openNavWithoutTitle(message,width) {
	    var modal = document.getElementById("myModalWitoutTitle");
	    var span = document.getElementsByClassName("close")[0];
	    modal.style.display = "block";

	     var contenu = document.getElementById("contenu").innerHTML = message;
	     let modalContent=document.getElementById("myModalWitoutTitleContent");
	     modalContent.style.maxWidth=width;
	     
	    // When the user clicks on <span> (x), close the modal
	    span.onclick = function() {
	      modal.style.display = "none";
	    }
	
}*/


function closeNav() {
	  var modal = document.getElementById("myModal");
	  var span = document.getElementsByClassName("close")[0];
	  modal.style.display = "none";
}