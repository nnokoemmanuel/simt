<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form class="kt-form" id="inSlipForm">
	<div class="kt-portlet__body">
		<div class="form-group form-group-last">
			<div class="alert alert-secondary" role="alert">
				<div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
				<div class="alert-text">
				 be careful, please upload the correct file    [editing of slip ${slip.referenceNumber}]
				</div>
			</div>
		</div>
		<div class="form-group">
         <a class="btn-floating peach-gradient mt-0 ">
            <i class="fas fa-paperclip" aria-hidden="true"></i>
          <input name="scannedSlip" type="file" required class="form-constrol" id="fileId">
         </a>
		</div>
		<div class="form-group">
			<label>Total Files</label>
			<input name="totalFiles" min="1" max="1000" type="number" class="form-control" id="totalId" placeholder="ex 20" required >
		</div>
		<div class="form-group">
			<label>Delegation Reference</label>
			<input name="slipNumber" min="1" max="10000" type="number" class="form-control" id="slipNumberId" placeholder="ex 10" required >
		</div>
		
	<div class="kt-portlet__foot">
		<div class="kt-form__actions">
			<button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" createSlip('${pageContext.request.contextPath}','${slip.id}');">Submit</button>
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