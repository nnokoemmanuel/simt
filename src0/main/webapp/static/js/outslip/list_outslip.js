function outSlip_action(value,id,entity){
	switch(value){
	case "action":
		return;
	case "printSuccessfully":
        var url =$("#baseUrl").val()+"/outSlip/printSuccessfully?id="+id ;
        window.open(url);
		break;
    case "printRejected":
            var url =$("#baseUrl").val()+"/outSlip/printRejected?id="+id ;
            window.open(url);
    		break;
    		
	case "history":
		openNav('OUT SLIP TRACKING', '', '50%');
		var url = $("#baseUrl").val() + "/simt/tracking?&entity=" + entity + "&id=" + id;
		doGet(url,'contenu');
		
		break;
	case "reset":
        resetOutSlip(id);
        active_link('file-management');
    	break;
    case "deliver":
        deliverOutSlip(id);
        active_link('file-management');
        break;
	case "validate":
         validateOutSlip(id);
         active_link('file-management');
         break;
    case "confirm":
            //alert(id);
         //active_link('slip-management');
         let  dateRange=document.getElementById("dateRangeId").value;
                       dateRange =dateRange.split("-");
                       [startDate, endDate]=dateRange;
         var status= document.getElementById("status").value;
         var office= document.getElementById("office").value;
         var referenceNum= document.getElementById("referenceNum").value;
         openNav('Confirm out Slip ', '', '40%');
         var url =$("#baseUrl").val()+"/outSlip/confirmView?id="+id + "&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&office=" + office + "&referenceNum=" + referenceNum +"";;
         doGet(url,'contenu');
         break;
	default:
	   return;
	}
}

function confirmSlip(baseUrl,id) {

  if($("#outSlipForm").valid()==true) {

  	document.getElementById("successId").classList.add("kt-hidden");
  	document.getElementById("failedId").classList.add("kt-hidden");


    var id= document.getElementById("outSlipId").value;
     var startDate= document.getElementById("startDate").value;
      var endDate= document.getElementById("endDate").value;
       var referenceNum= document.getElementById("referenceNum").value;

       	 const file=document.getElementById("fileId").files[0];
       	 const filename=document.getElementById("fileId").files[0].name;
       	 var formData = new FormData();
       	 formData.append("id",id);
       	 formData.append("file",file);
       	 formData.append("filename",filename);
       	 displayLoading("outSlipForm");
         baseUrl=baseUrl+"/outSlip/confirm";
       	 doPost(baseUrl,formData,"kt_content").then(function(responseSuccess){
        			//start for reset
        			removeLoading("outSlipForm");
        			closeNav();
        			if( responseSuccess == "ok"){
                         var url = $("#baseUrl").val() + "/outSlip/listeSearchOutSlip";

                        let  dateRange=document.getElementById("dateRangeId").value;
                              dateRange =dateRange.split("-");
                              [startDate, endDate]=dateRange;
                        var referenceNum= document.getElementById("referenceNum").value;
                        if (((startDate == "" && endDate == undefined) || referenceNum=="" ) && (startDate == "" && endDate == undefined && referenceNum=="" )){
                            refreshOutSlipPage(url,true);
                        }else{
                            refreshOutSlipPage(url,false);
                        }
                            swal.fire(
                                 'Confirmed / confirmée',
                                 'Your Out Slip has been Confirm / ton bordereau de sortir a été confirmée',
                                 'success'
                                )
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                'you do not have enough rights to CONFIRMED this Out Slip / vous ne disposez pas de droits pour confirmer ce bordereau!',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                "the Out Slip actual state is not Valided / le statut actuel bordereau n'est pas Validé",
        		                'error'
        		            )
        				return;
        			}
                    //removeLoading("outSlipForm");

       	 }).catch(error=>{
       		 removeLoading("outSlipForm");
       		 document.getElementById(error).classList.remove("kt-hidden");

       	 });
    }
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

function searchButtonOutSlipList() {

	let  dateRange=document.getElementById("dateRangeId").value;
              dateRange =dateRange.split("-");
              [startDate, endDate]=dateRange;
	var status= document.getElementById("status").value;
    var office= document.getElementById("office").value;
    var referenceNum= document.getElementById("referenceNum").value;

 if (startDate =="" &&  endDate ==undefined && referenceNum == ""  ){
     swal.fire(
        'Fill in the search fields / merci de renseigner les champs de recherche ...!',
        'ok !'
        )
         return;
   }else{
		var url = $("#baseUrl").val() + "/outSlip/listeSearchOutSlip?&startDate=" + startDate + "&endDate=" + endDate +  "&status=" + status + "&office=" + office + "&referenceNum=" + referenceNum +"";
		doGet(url,'content');
   }
}

function validateOutSlip(OutslipId){
	swal.fire({
        title: 'confirm validate / confirmez la validation ! ',
        text: 'irreversible operation / operation irreversible!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, oui'
    }).then(function(result) {
        if (result.value) {
        	//we reset
        	var baseUrlOfController="/simt/outSlip/update/validate/"+OutslipId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){
        			//start for reset
        			if( responseSuccess == "ok"){
                         var url = $("#baseUrl").val() + "/marine/outSlip/listeSearchOutSlip";

                        let  dateRange=document.getElementById("dateRangeId").value;
                              dateRange =dateRange.split("-");
                              [startDate, endDate]=dateRange;
                        var referenceNum= document.getElementById("referenceNum").value;
                        if (((startDate == "" && endDate == undefined) || referenceNum=="" ) && (startDate == "" && endDate == undefined && referenceNum=="" )){
                            refreshOutSlipPage(url,true);
                        }else{
                            refreshOutSlipPage(url,false);
                        }
                            swal.fire(
                                 'validated / validé',
                                 "Your Out Slip has been validated / ton bordereau d'entrée a été validé",
                                 'success'
                                )
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                'you do not have enough rights to validate this Out Slip / vous ne disposez pas de droits pour valider ce bordereau!"',
        		                'error'
        		            )
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                "the Out Slip actual state is not GENERATED / le statut actuel du bordereau n'est pas generé ",
        		                'error'
        		            )
        				return;
        			}



        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}

        }
    });
}



function resetOutSlip(OutslipId){
	swal.fire({
        title: 'confirm reset / confirmez la réinitialisation ! ',
        text: 'irreversible operation / operation irreversible!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes / oui'
    }).then(function(result) {
        if (result.value) {
        	//we open
        	var baseUrlOfController="/simt/outSlip/update/reset/"+OutslipId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){

        			//start response validate
        			if( responseSuccess == "ok"){
                        var url = $("#baseUrl").val() + "/outSlip/listeSearchOutSlip";

                        let  dateRange=document.getElementById("dateRangeId").value;
                              dateRange =dateRange.split("-");
                              [startDate, endDate]=dateRange;
                        var referenceNum= document.getElementById("referenceNum").value;

                        if (((startDate == "" && endDate == undefined) || referenceNum=="" ) && (startDate == "" && endDate == undefined && referenceNum=="" )){
                            refreshOutSlipPage(url,true);
                        }else{
                            refreshOutSlipPage(url,false);
                        }
                            swal.fire(
                                 'Resetted / réinitialisé',
                                 "Your Out Slip has been resetted / ton bordereau de sortir a été réinitialisé!",
                                 'success'
                                );
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                'you do not have enough rights to reset this Out Slip / vous ne disposez pas de droits pour réinitialiser ce bordereau!',
        		                'error'
        		            );
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
        		                "the Out Slip actual state is not VALIDATED / le statut actuel du bordereau n'est pas valide",
        		                'error'
        		            );
        				return;
        			}

        			//end response validate
        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}

        }
    });
}



function refreshOutSlipPage(url,isGlobalLoading){
	if(isGlobalLoading){
		var baseUrlOfController="/simt/outSlip/list";
	  	if(getBaseUrl(baseUrlOfController)!= null){
	  		doGet(getBaseUrl(baseUrlOfController),'kt_content');
	  		active_link('file-management');
	  		return false;
	  	}
	}else{
		searchButtonOutSlipList();
	}
}


function deliverOutSlip(OutslipId){
	swal.fire({
        title: 'Are you sure you want to deliver / êtes-vous sûr de vouloir delivrer ?',
        text: 'irreversible operation / operation irreversible!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes / oui'
    }).then(function(result) {
        if (result.value) {
        	//we open
        	var baseUrlOfController="/simt/outSlip/update/deliver/"+OutslipId;
        	if(getBaseUrl(baseUrlOfController) != null ){
        		sendGet(getBaseUrl(baseUrlOfController)).then(function(responseSuccess){

        			//start response validate
        			if( responseSuccess == "ok"){
                        var url = $("#baseUrl").val() + "/outSlip/listeSearchOutSlip";

                        let  dateRange=document.getElementById("dateRangeId").value;
                              dateRange =dateRange.split("-");
                              [startDate, endDate]=dateRange;
                        var referenceNum= document.getElementById("referenceNum").value;

                        if (((startDate == "" && endDate == undefined) || referenceNum=="" ) && (startDate == "" && endDate == undefined && referenceNum=="" )){
                            refreshOutSlipPage(url,true);
                        }else{
                            refreshOutSlipPage(url,false);
                        }
                            swal.fire(
                                'Out SLIP Delivered / bordereau delivré',
                                'Your Out Slip has been Delivered / ton bordereau a été delivré',
                                'success'
                                );
                            return;

        			}
        			if( responseSuccess == "koBadRole"){
        				swal.fire(
        		                 'Not Allowed / Refusé!',
                                 'you do not have enough rights to deliver this Out Slip / vous ne disposez pas de droits pour delivrer ce bordereau',
                                 'error'
        		            );
        				return;
        			}
        			if( responseSuccess == "koBadState"){
        				swal.fire(
        		                'Not Allowed / Refusé!',
                                "the Out Slip actual state is not VALIDATED / le statut actuel du bordereau n'est pas valide",
                                'error'
        		            );
        				return;
        			}

        			//end response validate
        		}
        		).catch(function(responseError){
        			alert('echec ! '+responseError);
        		});
        	}

        }
    });
}