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
		<div class="form-group form-group-last">
			<div class="alert alert-secondary" role="alert">
				<div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
				<div class="alert-text">
				 be careful, please upload the correct file
				</div>
			</div>
		</div>
		<div class="form-group">
         <a class="btn-floating peach-gradient mt-0 ">
            <i class="fas fa-paperclip" aria-hidden="true"></i>
          <input name="scannedSlip" type="file" required class="form-constrol" id="fileId">
         </a>
		</div>
		 <input id="startDate" name="startDate" type="hidden" value="${startDate}">
		  <input id="endDate" name="endDate" type="hidden" value="${endDate}">
		   <input id="status" name="status" type="hidden" value="${status}">
		    <input id="office" name="office" type="hidden" value="${office}">
		     <input id="referenceNum" name="referenceNum" type="hidden" value="${referenceNum}">
		      <input id="outSlipId" name="outSlipId" type="hidden" value="${id}">

	<div class="kt-portlet__foot">
		<div class="kt-form__actions">
			<button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" confirmSlip('${pageContext.request.contextPath}', '${id}');">Submit</button>
		</div>
	</div>
	
	<div class="alert alert-outline-success fade show kt-hidden" role="alert" id="successId">
		<div class="alert-icon"><i class="flaticon-warning"></i></div>
		<div class="alert-text">Slip created successfully</div>
		<div class="alert-close">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true"><i class="la la-close"></i></span>
			</button>
		</div>
	</div>
	
	<div class="alert alert-outline-danger fade show kt-hidden" role="alert" id="failedId">
		<div class="alert-icon"><i class="flaticon-questions-circular-button"></i></div>
		<div class="alert-text">Something when wrong, Please try again</div>
		<div class="alert-close">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true"><i class="la la-close"></i></span>
			</button>
		</div>
	</div>
</form>
</body>
</html>