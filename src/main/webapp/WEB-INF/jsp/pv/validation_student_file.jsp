<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="kt-form" id="approveSudentSessionForm">
	<div class="kt-portlet__body">
		<div class="form-group form-group-last">
			<div class="alert alert-secondary" role="alert">
				<div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
				<div class="alert-text">
				 be careful, please upload the correct file
				</div>
			</div>
		</div>
		<!-- <div class="form-group">
         <a class="btn-floating peach-gradient mt-0 ">
            <i class="fas fa-paperclip" aria-hidden="true"></i>
          <input name="scannedPv" type="file" required class="form-constrol" id="fileId" >
         </a>
		</div> -->
        <!--begin::Portlet-->
        <div class="kt-portlet">
            <div class="form-group row">

                <!--UPLOAD IMAGE/PHOTOS -->
                <div class="col-lg-4">
                    <label for="image" class="control-label">Photo* (Dimension: 300x300px)</label>
                    <br>
                <!-- IMAGE PREVIEW -->
                    <img id="image_previews" style="height:100px; width:100px;"  src="" >
                    <br>
                <!-- IMAGE INPUT -->
                    <input  id="image" name="image" type="file" required  onchange="document.getElementById('image_previews').src = window.URL.createObjectURL(this.files[0])" >
                </div>

                <!--UPLOAD SIGNATURE -->
                <div class="col-lg-4">
                    <label for="signature" class="control-label">Signature* (Dimension: 300x300)</label>
                    <br>
                <!-- SIGNATURE PREVIEW -->
                    <img id="signature_previews"  style="height:100px; width:100px;"  src="" >
                    <br>
                <!-- SIGNATURE INPUT -->
                    <input id="signature" name="signature" type="file" required  onchange="document.getElementById('signature_previews').src = window.URL.createObjectURL(this.files[0])" >
                </div>

                <!--UPLOAD FILES -->
                <div class="col-sm-4 pull-right">
                    <div class="form-group"  >
                       <label for="id_file" class="control-label">DIPLOME *</label>
                       <a class="btn-floating peach-gradient mt-0 ">
                          <i class="fas fa-paperclip" aria-hidden="true"></i>
                        <input name="scannedPv" type="file"  required  class="form-constrol" id="fileId" >
                       </a>
                    </div>
                </div>
            </div>
        </div>

	<div class="kt-portlet__foot">
		<div class="kt-form__actions">
		    <c:if test="${upload == 0}">
			    <button  style=" margin-bottom:20px"type="button" class="btn btn-primary pull-right" onClick="approveStudent('${pageContext.request.contextPath}/studentSession/manage', ${studentSessionId}, 2, 'approved');">Submit</button>
			</c:if>
			<c:if test="${upload == 1}">
			    <button  style=" margin-bottom:20px"type="button" class="btn btn-primary pull-right" onClick="approveStudent('${pageContext.request.contextPath}/studentSession/manage', ${studentSessionId}, -1, 'upload');">Submit</button>
		    </c:if>
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
