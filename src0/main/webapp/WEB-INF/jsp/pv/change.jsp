<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="kt-form" id="pvValidationForm">
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
           <b>Upload Pv and Jury Final Decision</b>
            <br>
            <i class="fas fa-paperclip" aria-hidden="true"></i>
          <input name="scannedPv" type="file" required class="form-constrol" id="fileId" >
         </a>
		</div>

        <div class="form-group row">
            <div class="col-lg-6">
                <label>Enter Jury Number:</label>
                <input type="text" id="juryNum" name="juryNum" class="form-control" required>
            </div>
            <div class="col-lg-12 kt-align-right">
                <button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" validatePV('${eligibleCenterId}');">Submit</button>
            </div>
        </div>
	
	<div class="alert alert-outline-success fade show kt-hidden" role="alert" id="successId">
		<div class="alert-icon"><i class="flaticon-warning"></i></div>
		<div class="alert-text">Pv successfully validated ! </div>
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
