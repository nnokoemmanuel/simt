function inSlip_action(value,id,entity){
	switch(value){
	case "action":
		return;
	case "detail":
		var url =$("#baseUrl").val()+"/inSlip/detail?id="+id ;
		doGet(url,'kt_content');
		break;
	case "edit":
		
		openNav('Edit InSlip', '', '40%');
		doGet('/simt/inSlip/edit?id='+id,'contenu');
	
		break;
	case "history":
		//alert("emms inslip");
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		openNav('INSLIP HISTORY', '', '50%');
		doGet(url,'contenu');
		
		break;	
	case "open":
        openInSlip(id);
        active_link('file-management');
    	break;
    case "reset":
           resetInSlip(id);
            active_link('file-management');
        	break;
	default:
	   return;
	}
}

function showDetails()
  {
      document.getElementById('unregistered').style.display='block';
  }
  
 function hideDetails()
  {
      document.getElementById('unregistered').style.display='none';
  }
function showOutBordereaus(id)
  {
      document.getElementById(id).style.display="block";
  }
function showOutBordereau(id)
  {
      document.getElementById(id).style.display="none";
  }

function switch_box_message(message)
{
    if(document.getElementById(message).style.display=='block')
    {
        document.getElementById(message).style.display='none';
    }
    else 
    {
        document.getElementById(message).style.display='block';
    }
   document.getElementById('error').style.display='none';
   document.getElementById('error').innerHTML="";
}

function checkform(url)
{

    var msg=document.getElementById('message').style.display;
    var myModal = document.getElementById("myModal");
    var message="normal";
    if(msg=='block')
    {
        if((document.getElementById('message').value).length<20)
        {
          document.getElementById('error').style.display='block';
          document.getElementById('error').innerHTML="Minimum characters of 20 is required";
          return;
        }
        if((document.getElementById('message').value).length>80)
        {
          document.getElementById('error').style.display='block';
          document.getElementById('error').innerHTML="Maximum characters of 100 exceeded";
          return;
        }
        message=document.getElementById('message').value;

    }
    url+="&message="+message;
    doGet(url,"kt_content");
    myModal.style.display = "none";

}
  





	$("#startdate").datetimepicker({
		widgetPositioning : {
			vertical : 'bottom'
		},
		format : 'DD/MM/YYYY',
		useCurrent : false,

	});

	$("#enddate").datetimepicker({
        widgetPositioning: {
            vertical: 'bottom'
        },
        format: 'DD/MM/YYYY',
        useCurrent: false,

    });

function searchButtonInSlipList() {
     let  dateRange=document.getElementById("dateRangeId").value;
          dateRange =dateRange.split("-");
          [startDate, endDate]=dateRange;
	var status= document.getElementById("status").value;
    var office= document.getElementById("office").value;
    var referenceNum= document.getElementById("referenceNum").value;
    if (startDate =="" &&  endDate ==undefined && referenceNum == ""  ){
                swal.fire(
                     'Fill in the search fields / Merci de Renseigner les champs de recherche ...!',
                     'ok !'
                )
                return;
   }else{
		var url = $("#baseUrl").val() + "/inSlip/listeSearchInSlip?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&office=" + office + "&referenceNum=" + referenceNum +"";  
		doGet(url,'content');
   }
}

function convertDate(date, n) {
	date = date.split("/");
	var dateConvert = new Date(date[2] + '-' + date[1] + '-' + date[0]);
	if (n == 1) {
		dateConvert.setDate(dateConvert.getDate() + 1);
	}

	var month = dateConvert.getMonth() + 1;
	var day = dateConvert.getDate();
	var year = dateConvert.getFullYear();

	if (day < 10) {
		day = '0' + day
	}
	if (month < 10) {
		month = '0' + month
	}
	dateConvert = year + "-" + month + "-" + day;
	return dateConvert;
}


function createSlip() {
  if($("#inSlipForm").valid()==true) {

	  
  }
}

function resetInSlip(InslipId){
	swal.fire({
        title: 'confirm reset / confirmez la réinitialisation ! ',
        text: 'irreversible operation / operation irreversible!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes / oui'
    }).then(function(result) {
        if (result.value) {
        	//we reset 
        	var baseUrlOfController="/simt/inSlip/update/reset/"+InslipId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
        			//start for reset
        			if( responseSuccess == "ok"){
                        var url = $("#baseUrl").val() + "/inSlip/listeSearchInSlip";

                        let  dateRange=document.getElementById("dateRangeId").value;
                              dateRange =dateRange.split("-");
                              [startDate, endDate]=dateRange;
                        var referenceNum= document.getElementById("referenceNum").value;

                        if (((startDate == "" && endDate == undefined) || referenceNum=="" ) && (startDate == "" && endDate == undefined && referenceNum=="" )){
                            refreshInSplisPage(url,true);
                        }else{
                            refreshInSplisPage(url,false);
                        }
                            swal.fire(
                                 'Resetted / réinitialisé',
                                 "Your In Slip has been Resetted / ton bordereau d'entrer a été réinitialisé",
                                 'success'
                                )
                            return;
        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                "you do not have enough rights to reset this In Slip / vous ne disposez pas le droits pour réinitialiser ce bordereau!",
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                "the In Slip actual state is not OPENED / le statut actuel du bordereau n'est pas OUVERT ",
        		                'error'
        		            )
        				return;
        			}
        			
        			
        			
        		}
        		).catch(function(responseError){
        			console.log('echec ! '+responseError);
        		});
        	}
            
        }
    });
}


function openInSlip(InslipId){
	swal.fire({
        title: 'Are you sure you want to open / êtes-vous sûr de vouloir ouvrir ?',
        text: 'irreversible operation / operation irreversible!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes / oui'
    }).then(function(result) {
        if (result.value) {
        	//we open
        	var baseUrlOfController="/simt/inSlip/update/open/"+InslipId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){ 
        			
        			//start response validate 
        			if( responseSuccess == "ok"){
                        var url = $("#baseUrl").val() + "/inSlip/listeSearchInSlip";

                        let  dateRange=document.getElementById("dateRangeId").value;
                              dateRange =dateRange.split("-");
                              [startDate, endDate]=dateRange;
                        var referenceNum= document.getElementById("referenceNum").value;

                        if (((startDate == "" && endDate == undefined) || referenceNum=="" ) && (startDate == "" && endDate == undefined && referenceNum=="" )){
                            refreshInSplisPage(url,true);
                        }else{
                            refreshInSplisPage(url,false);
                        }
                            swal.fire(
                                'In Slip opened / bordereau ouvert',
         		                'Your In Slip has been opened / ton bordereau a été ouvert',
         		                'success'
                                )
                            return;
        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                'you do not have enough rights to open this In Slip / vous ne disposez pas de droits pour ouvrir ce bordereau',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                "the In Slip actual state is not REGISTERED / le statut actuel du bordereau n'est pas ENREGISTRE",
        		                'error'
        		            )
        				return;
        			}
        			
        			//end response validate 
        		}
        		).catch(function(responseError){
        			console.log('echec ! '+responseError);
        		});
        	}
            
        }
    });
}

function refreshInSplisPage(url,isGlobalLoading){
	if(isGlobalLoading){
		var baseUrlOfController="/simt/inSlip/list";
	  	if(getBaseUrl(baseUrlOfController)!= null){
	  		doGet(getBaseUrl(baseUrlOfController),'kt_content');
	  		active_link('slip-management');
	  		return false;
	  	}
	}else{
		searchButtonInSlipList();
	}
}

function applicationAction(value,id, entity){
	switch(value){
	case "action":
		return;
	case "detail":
		var url = $("#baseUrl").val() + "/manageFile/detail/" + id;
		openNav('APPLICATION DETAILS / DETAILS APPLICATION', '', '100%');
		doGet(url,'contenu');
		break;
	case "history":
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		openNav('APPLICATION TRACKING / HISTORIQUE APPLICATION', '', '50%');
		doGet(url,'contenu');
		break;	

	default:
	   return;
	}
}




