<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form class="kt-form" id="outSlipForm">
	<div class="kt-portlet__body">

		<div class="form-group">
			<label>Choose a Period</label>
			<input name="dates"  type="text" class="form-control" id="dateOutSlip" placeholder="start date and end date" required >
		</div>
		
	<div class="kt-portlet__foot">
		<div class="kt-form__actions">
			<button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" createOutSlip('${pageContext.request.contextPath}',0);">Submit</button>
		</div>
	</div>
	
</form>
</body>
<script>
    $('input[name="dates"]').daterangepicker({  parentEl: "#contenu .modal-body"});
     function createOutSlip(baseUrl, id) {
     let baseUri=baseUrl;
	 baseUrl=baseUrl+"/outSlip/create";
	 let dateranges=document.getElementById("dateOutSlip").value;
	
	 dateranges=dateranges.split("-");
	 const startDate=dateranges[0];
	 const endDate=dateranges[1];
	 
	 var formData = new FormData();
	 formData.append("id",id);
	 formData.append("startDate",startDate);
	 formData.append("endDate", endDate);
	 
	 displayLoading("outSlipForm");
	 doPost(baseUrl,formData,"kt_content").then(result=>{
		 removeLoading("outSlipForm");
		 closeNav();
		 doGet(baseUri+'/outSlip/list','kt_content');
		 if(result=="ok")	
		 swal.fire(
					 'Success !',
					 'Out Slip success fully REGISTERED !.',
					 'success'
			)	; 
			else 
			swal.fire(
					 'Failed !',
					 'Out slip is empty and was not created.',
					 'error'
	   )	; 
		
	 }).catch(error=>{
		 removeLoading("outSlipForm");
		 closeNav();
		 swal.fire(
					 'Failed !',
					 'Something went wrong at our end.',
					 'error'
	   )	; 
		
		 
	 });
}
     
</script>
</html>