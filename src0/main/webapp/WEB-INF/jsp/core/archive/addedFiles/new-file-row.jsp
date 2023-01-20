<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- <div class="form-group row">-->
		<!-- block row content -->
		<div class="col-lg-4 upload-block-margins file">
		<label><fmt:message key="add.file.label" /></label>
		<div></div>
		<div class="custom-file">										
		<div class="kt-avatar" id="kt_user_avatar_file">
		<div id="fileHolder" class="kt-avatar__holder" style="background-image: url(assets/media/files/pdf.svg)"></div>
		<canvas id="pdf-canvas" width="200" height="200" class="pdf-canvas" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; display:none;"></canvas>
		<label id="changeFile" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar">
		<i class="fa fa-pen"></i>
		<input id="fileInput"  class="kt-avatar__input_file" type="file" name="profile_avatar" accept=".pdf">
		</label>
		<span id="fileCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
		<i class="fa fa-times"></i>
		</span>
		</div>
		<button id="button-file-plus" type="button" class="btn btn-brand btn-elevate btn-icon boutonFileAdd" onclick="autoConcatenateUIFiles();"><i class="la la-plus"></i></button>
		<button id="button-file-minus" type="button" class="btn btn-dark btn-icon boutonFileDelete" onclick="delete_file(this.value);"><i class="la la-minus"></i></button>

		</div>
		</div>
		<!-- block end content -->								
<!-- </div>-->
