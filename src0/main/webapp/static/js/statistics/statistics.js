function getProductionStatistics() {
	let  dateRange=document.getElementById("dateRangeId").value;
	let  appStatusId=document.getElementById("appStatus").value;
    	dateRange =dateRange.split("-");
    	[startDate, endDate]=dateRange;

    if (startDate =="" &&  endDate ==undefined ){
          swal.fire(
               'Merci de Renseigner les champs de recherche ...!',
               'ok !'
          )
          return;
    }else{
    	var url = $("#baseUrl").val() + "/statistics/search?&startDate=" + startDate + "&endDate=" + endDate + "&appStatus=" + appStatusId;  
    	doGet(url,'content');
}
	
}