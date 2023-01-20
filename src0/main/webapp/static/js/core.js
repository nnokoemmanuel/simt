function getXMLHttp(){
	var xmlHttp = false;
	try{
		//try to get request object for IE 5 or later
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
	}catch(e){
		//get request object for earlier versions of IE.
		try{
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}catch(e){
			
		}
	}	
	
	if(!xmlHttp && typeof XMLHttpRequest !== 'undefined'){
		xmlHttp = new XMLHttpRequest();
	}
	
	return xmlHttp;
}

function doGet(url, idResultContener){
	displayLoading(idResultContener);
    var xmlHttp     =   getXMLHttp();
    xmlHttp.open("GET", url, false);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlHttp.onreadystatechange = function(){
      if(xmlHttp.status !== 200){
      	$("#"+idResultContener).html(xmlHttp.responseText);      	
          return;
      }
      if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
      	$("#"+idResultContener).html(xmlHttp.responseText);
      }else{
    	  $("#"+idResultContener).html("ERROR 202");
      }
  };
  xmlHttp.send(null);
}


function doPost(url,formData,idResult) {
	let token =  $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	displayLoading(idResult);
    return new Promise(function (resolve, reject) {
        var xhr =  getXMLHttp();
        //xhr.setRequestHeader(header,token);  
        xhr.onload = function (event) {
            resolve(xhr.responseText); 
        };

        xhr.onerror = function (err) {
            reject(err); 
        }
        
        xhr.open('POST', url, false);
        xhr.send(formData);
    });
}



var __PDF_DOC,
__CURRENT_PAGE,
__TOTAL_PAGES,
__PAGE_RENDERING_IN_PROGRESS = 0;



function showPDF(pdf_url,i) {
	if(i!= undefined)
		__CANVAS = document.getElementById('pdf-canvas-'+i);
	else
		__CANVAS = document.getElementById('pdf-canvas');
	__CANVAS_CTX = __CANVAS.getContext('2d');
	pdfjsLib.getDocument({ url: pdf_url }).then(function(pdf_doc) {
	__PDF_DOC = pdf_doc;
	__TOTAL_PAGES = __PDF_DOC.numPages;
	// Show the first page*
	showPage(1,i);
	}).catch(function(error) {
	
	alert(error.message);
	});
    
}


function showPage(page_no,i) {
	var canVasx=$("#pdf-canvas-"+i);
	if(i!= undefined)
		$("#pdf-canvas-"+i).hide();
	else
		$("#pdf-canvas").hide();
	// Fetch the page
	__PDF_DOC.getPage(page_no).then(function(page) {
		// As the canvas is of a fixed width we need to set the scale of the viewport accordingly

		var scale_required = canVasx.width / page.getViewport(1).width;
		// Get viewport of the page at required scale
		var viewport = page.getViewport(scale_required);

		// Set canvas height
		canVasx.height = viewport.height;
		var renderContext = {
		canvasContext: __CANVAS_CTX,
		viewport: viewport
		};
		// Render the page contents in the canvas
		page.render(renderContext).then(function() {
			//page has finished rendering or is not rendering at all
			__PAGE_RENDERING_IN_PROGRESS = 0;
			// Show the canvas and hide the page loader
			if(i!= undefined)
				$("#pdf-canvas-"+i).show();
			else
				$("#pdf-canvas").show();
		});
	});
}


function showFirstPDF(pdf_url,idCanVas) {
	__CANVAS = document.getElementById(idCanVas);
	__CANVAS_CTX = __CANVAS.getContext('2d');
	pdfjsLib.getDocument({ url: pdf_url }).then(function(pdf_doc) {
	__PDF_DOC = pdf_doc;
	__TOTAL_PAGES = __PDF_DOC.numPages;
	// Show the first page*
	showThePage(1,idCanVas);
	}).catch(function(error) {
	
	alert(error.message);
	});
    
}


function showThePage(page_no,idCanVas) {
	var canVasx=$("#"+idCanVas);
     $("#file-canvas").hide();
	// Fetch the page
	__PDF_DOC.getPage(page_no).then(function(page) {
		// As the canvas is of a fixed width we need to set the scale of the viewport accordingly

		var scale_required = canVasx.width / page.getViewport(1).width;
		// Get viewport of the page at required scale
		var viewport = page.getViewport(scale_required);

		// Set canvas height
		canVasx.height = viewport.height;
		var renderContext = {
		canvasContext: __CANVAS_CTX,
		viewport: viewport
		};
		// Render the page contents in the canvas
		page.render(renderContext).then(function() {
			//page has finished rendering or is not rendering at all
			__PAGE_RENDERING_IN_PROGRESS = 0;
			// Show the canvas and hide the page loader
				$("#file-canvas").show();
		});
	});
}

function pushAvatarsEvents(idKAvatarChange,idphotoInput,idphotoHolder,idelement,idcancel){
		var KAvatarChange = document.getElementById(idKAvatarChange);
		var photoInput = document.getElementById(idphotoInput);
		var photoHolder = document.getElementById(idphotoHolder);
		var element =  document.getElementById(idelement);
		var  cancel = document.getElementById(idcancel);
		var photoSource = KTUtil.css(photoHolder, 'backgroundImage');
		var i=photoHolder.getAttribute("id").split('fileHolder-')[1];
		$("#"+idKAvatarChange).unbind('change'); // unbind all change events on cancel button
		KAvatarChange.addEventListener('change', function(e) {
			 e.preventDefault();
	            if (photoInput && photoInput.files && photoInput.files[0]) {
	            	 if(photoInput.files[0].size > 1048576){ // the configured maximum size
	           			 swal.fire(
				                'upload blocked !',
				                'the file size is too big put a file less or equal to 15 ko  !.',
				                'error'
				            );
	           			 return false;
	           		    }
	                var reader = new FileReader();
	                reader.onload = function(e) {
	                	 if(photoInput.files[0].type != "application/pdf"){
	                		 //hide pdf canvas
	                		 if(i!= undefined)
	                			 $("#pdf-canvas-"+i).hide();
	                		 else
	                			 $("#pdf-canvas").hide();
	                		 KTUtil.css(photoHolder, 'background-image', 'url('+e.target.result +')');
	                		 
	                	 }
	                    
	                }
	                reader.readAsDataURL(photoInput.files[0]);
	                if(photoInput.files[0].type == "application/pdf"){
		                //we hide default pdf file holder
	                	
		               // $("#photoHolder").hide();
		                photoHolder.style.display="none";
		                showPDF(URL.createObjectURL(photoInput.files[0]),i);
		              
	                }

	                KTUtil.addClass(element, 'kt-avatar--changed');
	            }
            });
		$("#"+idcancel).unbind('click'); // unbind all click events on cancel button	
		cancel.addEventListener('click', function(e) {
             var j =e.target.id.split('fileCancel-')[1];
			 if(photoInput.files[0].type == "application/pdf"){
				//hide pdf canvas
				 if(j!= undefined){
					 $("#pdf-canvas-"+j).hide();
			
				 }else{
					 $("#pdf-canvas").hide();
				 }
        		//show default image holder
        		 photoHolder.style.display="block";
			 }

            e.preventDefault();
            KTUtil.removeClass(element, 'kt-avatar--changed');
            KTUtil.css(photoHolder, 'background-image', photoSource);
            photoInput.value = "";

        });	
}

function   datePickersInitializer(){
$(".date-picker").datepicker({
	widgetPositioning : {
		vertical : 'bottom'
	},
	format : 'dd/mm/yyyy',
	useCurrent : false,
	minDate: '01/01/1930',
	maxDate : new Date()
});
}


function addDataTable() {
	 const table = $('table').DataTable();
      table.on( 'search.dt', function () {
      $('#dataTableSearddcher').html( 'Currently applied global search: '+table.search() );
} );		
	
}

function autoConcatenateUIFiles(){
	var baseUrlOfControllerAddFilesBlock="/simt/file/load/documents/archive/";
	var isANewRow = 0;
	if(checkIfLoadAFileRow()==1)
		isANewRow = 1;
	baseUrlOfControllerAddFilesBlock+=isANewRow;
	var url =baseUrlOfControllerAddFilesBlock;
	doget(url).then(function(responseSuccess){ 
		//if simple block
    var tailleOfFiles= document.getElementsByClassName('col-lg-4 upload-block-margins file').length;
    
    setFilesId();
    var idLastFileInput=tailleOfFiles-1;
		if(checkIfLoadAFileRow()==0){
		 //add a new block
	      var divAddNewFile = document.createElement("div");
	      divAddNewFile.classList.add('col-lg-4');
	      divAddNewFile.classList.add('upload-block-margins');
	      divAddNewFile.classList.add('file');
	      divAddNewFile.innerHTML=responseSuccess;
	      var idLastRow=(document.getElementsByClassName('form-group row file').length) -1;
	      //on append a la div id la plus recente
	      $("#div-file-row-"+idLastRow).append(divAddNewFile);
		}else{
			//create a new row
			 var divAddNewFile = document.createElement("div");
			 divAddNewFile.classList.add('form-group');
			 divAddNewFile.classList.add('row');
			 divAddNewFile.classList.add('file');
			 divAddNewFile.innerHTML=responseSuccess;
		      //on append a la div id la plus recente
		      $("#filesContainer").append(divAddNewFile);
		}
		setFilesId();
		//hideOtherPlusFileButtons();
	}
	).catch(function(responseError){
		alert('echec ! '+responseError);
	});
	
}

function getBaseUrl(baseUrlOfController){
    var routeBase = '';
    var contextPath='/simt';
    if(window.location.href.indexOf(contextPath) >-1 ){
         routeBase = window.location.href.split(contextPath)[0]+baseUrlOfController;
    }else
    	return null;
   return routeBase;

}

function checkIfLoadAFileRow(){
	 var taille = document.getElementsByClassName('col-lg-4 file').length;
	 if(taille%3 == 0)
		 return 1;//it is a row
	 else
		 return 0;
	
}

function setFilesId() {
	 /* pushAvatarsEvents("changeFile","fileInput","fileHolder","kt_user_avatar_file","fileCancel");
    //pushing avatars events when loading page*/
    var taille = document.getElementsByClassName('col-lg-4 upload-block-margins file').length;
    var listElementColFile = document.getElementsByClassName('col-lg-4 upload-block-margins file');
    var listElementDeleteButton = document.getElementsByClassName('btn btn-dark btn-icon boutonFileDelete');
    var listElementAddButton = document.getElementsByClassName('btn btn-brand btn-elevate btn-icon boutonFileAdd');
    var listElementFileRow = document.getElementsByClassName('form-group row file');
    var listElementDivClassktAvatar = document.getElementsByClassName('kt-avatar');
    var listElementDivClassktAvatarUpload = document.getElementsByClassName('kt-avatar__upload');
    var listElementDivClassktAvatarInputFile = document.getElementsByClassName('kt-avatar__input_file');
    var listElementDivClassktAvatarHolder = document.getElementsByClassName('kt-avatar__holder');
    var listElementDivClassktAvatarCancel = document.getElementsByClassName('kt-avatar__cancel');
    var listElementPdfCanvas = document.getElementsByClassName('pdf-canvas');
        for(var i=0 ; i<taille; i++){
            var idvalue = "div-file-block-"+i;
            var idRow = "div-file-row-"+i;
            var buttonDelevalue = "button-file-minus-"+i;
            var buttonAddvalue = "button-file-plus-"+i;
            var idDivktAvatar ="kt_user_avatar_file_"+i;
            var idDivktAvatarUpload= "changeFile-"+i;
            var idDivktAvatarInputFile="fileInput-"+i;
            var idDivktAvatarHolder="fileHolder-"+i;
            var idDivktAvatarCancel="fileCancel-"+i;
            var idPdfCanvas="pdf-canvas-"+i;
            listElementColFile[i].setAttribute("id", idvalue);
            if(listElementDeleteButton[i] != undefined){
            listElementDeleteButton[i].setAttribute("id", buttonDelevalue);
            listElementDeleteButton[i].setAttribute("value", i);
            }
            if(listElementAddButton[i] != undefined){
            listElementAddButton[i].setAttribute("id", buttonAddvalue);
            }
            if(listElementFileRow[i] != undefined){
            	listElementFileRow[i].setAttribute("id", idRow); 
            }
            if(listElementDivClassktAvatar[i] != undefined){
            	listElementDivClassktAvatar[i].setAttribute("id", idDivktAvatar);
            }
            if(listElementDivClassktAvatarUpload[i] != undefined){
            	listElementDivClassktAvatarUpload[i].setAttribute("id", idDivktAvatarUpload);
            }
            if(listElementDivClassktAvatarInputFile[i] != undefined){
            	listElementDivClassktAvatarInputFile[i].setAttribute("id", idDivktAvatarInputFile);
            }
            if(listElementDivClassktAvatarHolder[i] != undefined){
            	listElementDivClassktAvatarHolder[i].setAttribute("id", idDivktAvatarHolder);
            }
            if(listElementDivClassktAvatarCancel[i] != undefined){
            	listElementDivClassktAvatarCancel[i].setAttribute("id", idDivktAvatarCancel);
            }
            if(listElementPdfCanvas[i] != undefined){
            	listElementPdfCanvas[i].setAttribute("id", idPdfCanvas);
            }
            if(i==taille-1){
            	//we push avatars events only for last element
            $("#"+idDivktAvatarUpload).unbind('change');
            $("#"+idDivktAvatarCancel).unbind('click');
         
            	pushAvatarsEvents(idDivktAvatarUpload,idDivktAvatarInputFile,idDivktAvatarHolder,idDivktAvatar,idDivktAvatarCancel);
            }
           
       }
}
/*
function hideOtherPlusFileButtons(){
	//this function is made to hide all plus (add) buttons except the last
	var listElementAddButton = document.getElementsByClassName('btn btn-brand btn-elevate btn-icon boutonFileAdd');
	var tailleAddButton = listElementDeleteButton.length;
	  for(var i=0 ; i<tailleDeleteButton; i++){
		  var buttonToHide=document.getElementById("button-file-plus-"+i);
		  if(i != tailleDeleteButton-1){
			  buttonToHide.style.display="none";  
		  }  
      }
	  alert("we end to hide");
}*/

function sendGet(url){
    return new Promise(function (resolve, reject) {
        var xhr =  getXMLHttp();
        //xhr.setRequestHeader(header,token);  
        xhr.onload = function (event) {
            resolve(xhr.responseText); 
        };

        xhr.onerror = function (err) {
            reject(err); 
        }
        
        xhr.open('GET', url, false);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        xhr.send(null);
    });
  
}


function loadArchive(baseUrl){

      let archive = document.getElementById("archiveNumber"); 
      let archiveValue = archive.value.trim();
      if( archiveValue == ""){
    	  let  dateRange=document.getElementById("archiveRangeId").value;  
          const status=document.getElementById("archiveStatus").value;  
          dateRange =dateRange.split("-");
          [startDate, endDate]=dateRange;
         if (startDate =="" &&  endDate ==undefined ){
           swal.fire(
              'Merci de Renseigner les champs de recherche ...!',
              'ok !'
              )
               return;
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

function delete_file(id){
    var idvalue = "div-file-block-"+id;
    var list=document.getElementById(idvalue);
    //we delete the file block  except if it is the first block of the first row
    if(list != undefined && list != null){
    if(list.parentNode.getAttribute("id") == "div-file-row-0" && idvalue == "div-file-block-0"){
    	swal.fire(
                'Not Allowed!',
                'you can not delete this element ! please edit it instead ! ',
                'error'
            )
		return;
    	
    }else{
    	list.parentNode.removeChild(list);
    }

    var elementsInsideRow = list.parentNode.getElementsByClassName('col-lg-4 upload-block-margins file');
   
    if(elementsInsideRow == 0 ){
    	//we delete the row except if it is the first row !!! prolem here
    	if(list.parentNode.getAttribute("id") != "div-file-row-0" ){
    		// we delete the row 
    		var idrow= "div-file-row-"+id;
    		var rowToDelete=document.getElementById(idrow);
    		rowToDelete.parentNode.removeChild(rowToDelete);
    	}
    	
    }
    setFilesId();
 }
}

function checkIfLoadAFileRowForApplicationForm(){
	 var taille = document.getElementsByClassName('col-xl-3 file').length;
	 if(taille%3 == 0)
		 return 1;//it is a row
	 else
		 return 0;//it is a block
	
}

function autoConcatenateUIFilesForApplicationForm(){
	var baseUrlOfControllerAddFilesBlock="/simt/file/load/documents/application/";
	var isANewRow = 0;
	if(checkIfLoadAFileRowForApplicationForm()==1)
		isANewRow = 1;
	baseUrlOfControllerAddFilesBlock+=isANewRow;
	var url =baseUrlOfControllerAddFilesBlock;
	doget(url).then(function(responseSuccess){ 
		//if simple block
    var tailleOfFiles= document.getElementsByClassName('col-xl-3 file').length;
    
    setFilesForApplicationId();
    var idLastFileInput=tailleOfFiles-1;
		if(checkIfLoadAFileRowForApplicationForm()==0){
		 //add a new block
	      var divAddNewFile = document.createElement("div");
	      divAddNewFile.classList.add('col-xl-3');
	      divAddNewFile.classList.add('file');
	      divAddNewFile.classList.add('upload-block-margins-application-block');
	      divAddNewFile.innerHTML=responseSuccess;
	      var idLastRow=(document.getElementsByClassName('row file upload-block-margins-application-row').length) -1;
	      //on append a la div id la plus recente
	      $("#div-file-row-"+idLastRow).append(divAddNewFile);
		}else{
			//create a new row
			 var divAddNewFile = document.createElement("div");
			 divAddNewFile.classList.add('row');
			 divAddNewFile.classList.add('file');
			 divAddNewFile.classList.add('upload-block-margins-application-row');
			 divAddNewFile.innerHTML=responseSuccess;
		      //on append a la div id la plus recente
		      $("#filesContainer").append(divAddNewFile);
		}
		setFilesForApplicationId();
		//hideOtherPlusFileButtons();
	}
	).catch(function(responseError){
		alert('echec ! '+responseError);
	});
	
}

function setFilesForApplicationId() {
	 /* pushAvatarsEvents("changeFile","fileInput","fileHolder","kt_user_avatar_file","fileCancel");
   //pushing avatars events when loading page*/
   var taille = document.getElementsByClassName('col-xl-3 file upload-block-margins-application-block').length;
   var listElementColFile = document.getElementsByClassName('col-xl-3 file upload-block-margins-application-block');
   var listElementDeleteButton = document.getElementsByClassName('btn btn-dark btn-icon boutonFileDelete');
   var listElementAddButton = document.getElementsByClassName('btn btn-brand btn-elevate btn-icon boutonFileAdd');
   var listElementFileRow = document.getElementsByClassName('row file upload-block-margins-application-row');
   var listElementDivClassktAvatar = document.getElementsByClassName('kt-avatar');
   var listElementDivClassktAvatarWanted = [];
   var listElementDivClassktAvatarUpload = document.getElementsByClassName('kt-avatar__upload');
   var listElementDivClassktAvatarUploadWanted = [];
   var listElementDivClassktAvatarInputFile = document.getElementsByClassName('kt-avatar__input_file');
   var listElementDivClassktAvatarInputFileWanted = [];
   var listElementDivClassktAvatarHolder = document.getElementsByClassName('kt-avatar__holder');
   var listElementDivClassktAvatarHolderWanted = [];
   var listElementDivClassktAvatarCancel = document.getElementsByClassName('kt-avatar__cancel');
   var listElementDivClassktAvatarCancelWanted = [];
   var listElementPdfCanvas = document.getElementsByClassName('pdf-canvas');
  //lets remove photos and signature elements
       var tailleAvatar = document.getElementsByClassName('kt-avatar').length;
       for(var a=0 ; a<tailleAvatar; a++){
    	   if((listElementDivClassktAvatar[a].getAttribute("id") != "kt_user_avatar_signature") && (listElementDivClassktAvatar[a].getAttribute("id") != "kt_user_avatar_photo")) {
    		   //remove from array
    		   listElementDivClassktAvatarWanted.push(listElementDivClassktAvatar[a]);
    	   }
    	   
       }
       //we erase original content
       listElementDivClassktAvatar=listElementDivClassktAvatarWanted;
       
       var tailleAvatarUpload = document.getElementsByClassName('kt-avatar__upload').length;
       for(var b=0 ; b<tailleAvatarUpload; b++){
    	   if((listElementDivClassktAvatarUpload[b].getAttribute("id") != "changeSignature") && (listElementDivClassktAvatarUpload[b].getAttribute("id") != "changePhoto")) {
    		   //remove from array
    		   listElementDivClassktAvatarUploadWanted.push(listElementDivClassktAvatarUpload[b]);
    	   }
    	   
       }
       //we erase original content
       listElementDivClassktAvatarUpload=listElementDivClassktAvatarUploadWanted;
       
       var tailleAvatarInput = document.getElementsByClassName('kt-avatar__input_file').length;
       for(var c=0 ; c<tailleAvatarInput; c++){
    	   if((listElementDivClassktAvatarInputFile[c].getAttribute("id") != "signatureInput") && (listElementDivClassktAvatarInputFile[c].getAttribute("id") != "photoInput")) {
    		   //remove from array
    		   listElementDivClassktAvatarInputFileWanted.push(listElementDivClassktAvatarInputFile[c]);
    	   }
    	   
       }
       //we erase original content
       listElementDivClassktAvatarInputFile=listElementDivClassktAvatarInputFileWanted;
       
       var tailleAvatarHolder = document.getElementsByClassName('kt-avatar__holder').length;
       for(var d=0 ; d<tailleAvatarHolder; d++){
    	   if((listElementDivClassktAvatarHolder[d].getAttribute("id") != "signatureHolder") && (listElementDivClassktAvatarHolder[d].getAttribute("id") != "photoHolder")) {
    		   //remove from array
    		   listElementDivClassktAvatarHolderWanted.push(listElementDivClassktAvatarHolder[d]);
    	   }
    	   
       }
     //we erase original content
       listElementDivClassktAvatarHolder=listElementDivClassktAvatarHolderWanted;
       
       var tailleAvatarCancel = document.getElementsByClassName('kt-avatar__cancel').length;
       for(var e=0 ; e<tailleAvatarCancel; e++){
    	   if((listElementDivClassktAvatarCancel[e].getAttribute("id") != "signatureCancel") && (listElementDivClassktAvatarCancel[e].getAttribute("id") != "photoCancel")) {
    		   //remove from array
    		   listElementDivClassktAvatarCancelWanted.push(listElementDivClassktAvatarCancel[e]);
    	   }
    	   
       }
       
     //we erase original content
       listElementDivClassktAvatarCancel=listElementDivClassktAvatarCancelWanted;
       
       for(var i=0 ; i<taille; i++){
           var idvalue = "div-file-block-"+i;
           var idRow = "div-file-row-"+i;
           var buttonDelevalue = "button-file-minus-"+i;
           var buttonAddvalue = "button-file-plus-"+i;
           var idDivktAvatar ="kt_user_avatar_file_"+i;
           var idDivktAvatarUpload= "changeFile-"+i;
           var idDivktAvatarInputFile="fileInput-"+i;
           var idDivktAvatarHolder="fileHolder-"+i;
           var idDivktAvatarCancel="fileCancel-"+i;
           var idPdfCanvas="pdf-canvas-"+i;
           listElementColFile[i].setAttribute("id", idvalue);
           if(listElementDeleteButton[i] != undefined){
           listElementDeleteButton[i].setAttribute("id", buttonDelevalue);
           listElementDeleteButton[i].setAttribute("value", i);
           }
           if(listElementAddButton[i] != undefined){
           listElementAddButton[i].setAttribute("id", buttonAddvalue);
           }
           if(listElementFileRow[i] != undefined){
           	listElementFileRow[i].setAttribute("id", idRow); 
           }
           if(listElementDivClassktAvatar[i] != undefined){
           listElementDivClassktAvatar[i].setAttribute("id", idDivktAvatar);
           }
           if(listElementDivClassktAvatarUpload[i] != undefined){
	           listElementDivClassktAvatarUpload[i].setAttribute("id", idDivktAvatarUpload);
           }
           if(listElementDivClassktAvatarInputFile[i] != undefined){
	           listElementDivClassktAvatarInputFile[i].setAttribute("id", idDivktAvatarInputFile);
           }
           if(listElementDivClassktAvatarHolder[i] != undefined){
        	   listElementDivClassktAvatarHolder[i].setAttribute("id", idDivktAvatarHolder);
           }
           if(listElementDivClassktAvatarCancel[i] != undefined){
        	   listElementDivClassktAvatarCancel[i].setAttribute("id", idDivktAvatarCancel);
           }
           if(listElementPdfCanvas[i] != undefined){
        	   listElementPdfCanvas[i].setAttribute("id", idPdfCanvas);
           }
           if(i==taille-1){
	           	//we push avatars events only for last element
	        	//unbind all events here
	           $("#"+idDivktAvatarUpload).unbind('change');
	           $("#"+idDivktAvatarCancel).unbind('click');
	           if(document.getElementById(idDivktAvatarUpload) != null && document.getElementById(idDivktAvatarInputFile) != null && document.getElementById(idDivktAvatarHolder) != null &&  document.getElementById(idDivktAvatar) != null &&  document.getElementById(idDivktAvatarCancel) != null){
	        	   pushAvatarsEvents(idDivktAvatarUpload,idDivktAvatarInputFile,idDivktAvatarHolder,idDivktAvatar,idDivktAvatarCancel);
	           }
           }
          
      }
}

function delete_file_application(id){
	 var idvalue = "div-file-block-"+id;
	    var list=document.getElementById(idvalue);
	    //we delete the file block  except if it is the first block of the first row
	    if(list != undefined && list != null){
	    if(list.parentNode.getAttribute("id") == "div-file-row-0" && idvalue == "div-file-block-0"){
	    	swal.fire(
	                'Not Allowed!',
	                'you can not delete this element ! please edit it instead ! ',
	                'error'
	            )
			return;
	    	
	    }else{
	    	list.parentNode.removeChild(list);
	    }

	    var elementsInsideRow = list.parentNode.getElementsByClassName('col-xl-3 file upload-block-margins-application-block');
	   
	    if(elementsInsideRow == 0 ){
	    	//we delete the row except if it is the first row !!! prolem here
	    	if(list.parentNode.getAttribute("id") != "div-file-row-0" ){
	    		// we delete the row 
	    		var idrow= "div-file-row-"+id;
	    		var rowToDelete=document.getElementById(idrow);
	    		rowToDelete.parentNode.removeChild(rowToDelete);
	    	}
	    	
	    }
	    setFilesId();
	 }
	
}

function autoConcatenateUIFilesForApplicationFormPreviewsAttachments(){
	var rows = getNumOfRowAttachments();
	var numberOfFiles = document.getElementsByClassName('col-xl-3 file upload-block-margins-application-block').length;
	var numOfFilesNotAdded = numberOfFiles;
	for(var i = 1 ; i < rows+1 ; i ++){
		var baseUrlOfControllerAddFilesBlock="/simt/file/load/documents/applicationPreview/";
		var isANewRow = 1;
		var idDivRowAdded = "div-file-row-preview-"+i;
			//we construct the row with tree
			//create a new row
			 var divAddNewFile = document.createElement("div");
			 divAddNewFile.classList.add('row');
			 divAddNewFile.classList.add('file');
			 divAddNewFile.classList.add('preview');
			 divAddNewFile.classList.add('upload-block-margins-application-row');
			 divAddNewFile.setAttribute("id",idDivRowAdded);
			 //divAddNewFile.innerHTML=responseSuccess;
		      //on append a la div id la plus recente
		      $("#filesContainer-preview").append(divAddNewFile);
				  //we add block to the row
				  var numOfDecrementations = 0;
				  while(numOfFilesNotAdded > 0 && numOfDecrementations < 4 ){	

					//add a new block
				      var divAddNewFile = document.createElement("div");
				      divAddNewFile.classList.add('col-xl-3');
				      divAddNewFile.classList.add('file');
				      divAddNewFile.classList.add('preview');
				      divAddNewFile.classList.add('upload-block-margins-application-block');
				      var idDivBlockAdded = "div-file-block-preview-"+numOfDecrementations;
				      divAddNewFile.setAttribute("id",idDivBlockAdded);
				      //divAddNewFile.innerHTML=responseSuccessBlock;
				      if(document.getElementById("fileInput-"+numOfDecrementations)!=undefined)
				    	  var attachmentPdfURL = URL.createObjectURL(document.getElementById("fileInput-"+numOfDecrementations).files[0]);
				      else{
				    	  if(numberOfFiles == 1 ){
				    		  var attachmentPdfURL = URL.createObjectURL(document.getElementById("fileInput").files[0]);
				    	  }
				      }
				      divAddNewFile.innerHTML='<div class="form-group"><div class="custom-file-preview"><a href="'+attachmentPdfURL+'" target="_blank"><canvas id="pdf-canvas-preview" width="200" height="200" class="pdf-canvas preview" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; "></canvas></a></div></div>';
				      var canvasInBlock = divAddNewFile.getElementsByTagName('canvas')[0];
				      var canvasInBlockId="pdf-canvas-preview-"+numOfDecrementations;
				      canvasInBlock.setAttribute("id",canvasInBlockId);
				      //on append a la div row id la plus recente
				      $("#"+idDivRowAdded).append(divAddNewFile);
				      //we decrease the number of files added
				      numOfFilesNotAdded--;
				      numOfDecrementations++;
				
				  }
				  
				  

		
		
	}
	
}

function getNumOfRowAttachments(){
	 var taille = document.getElementsByClassName('col-xl-3 file upload-block-margins-application-block').length;
	 if(taille%3 == 0)
		 return taille/3;//it is a row
	 else
		 return Math.trunc(taille/3)+1;//it is a block
	
}


