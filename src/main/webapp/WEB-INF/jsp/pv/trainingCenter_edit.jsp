<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="kt-portlet">
<div class="kt-portlet__head">
	<div class="kt-portlet__head-label">
		<h3 class="kt-portlet__head-title"><fmt:message key="edit.trainingCenter.key"/></h3>
	</div>
</div>
<div class="kt-portlet__body" id="trainingCenterFormId">

	<!--begin::Form-->
<form class="kt-form">
	<div class="kt-portlet__body form-group row">
	   <div class="col-12">
			<label><fmt:message key="trainingCenter_Name" /><span style="color:red;">(* requis | required)</span></label>
			<input name="name" id="name"  type="text" class="form-control"  value="${trainingCenter.name}"   required >
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_FounderName" /><span style="color:red;">(* requis | required)</span></label>
			<input name="founder" id="founder"  type="text" class="form-control" value="${trainingCenter.founder}"  required >
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_OwnerName" /><span style="color:red;">(* requis | required)</span></label>
			<input name="owner" id="owner"  type="text" class="form-control"  value="${trainingCenter.owner}" required >
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_OwnerContact" /><span style="color:red;">(* requis | required)</span></label>
			<input name="ownerContact" id="ownerContact"  type="text" class="form-control"   value="${trainingCenter.owner}" required >
			<span style="color:red;">Please Fill a valid phone number | Veuillez renseignez un numeros de tephone valide</span>
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_location" /><span style="color:red;">(* requis | required)</span></label>
			<input name="location" id="location"  type="text" class="form-control" value="${trainingCenter.location}" required >
			<span style="color:red;">Please Fill town(quater) | Veuillez renseignez ville(quarier) Eg Douala(Rond point Deido)</span>
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_maxStudent" /><span style="color:red;">(* requis | required)</span></label>
			<input name="maxStudent" id="maxStudent"  type="number" class="form-control"  value="${trainingCenter.maxStudent}"  required >
		</div>
		<c:if test="${fn:length(trainingCenterSpecialities) > 0}">
		<div class="col-12">
			<div class="kt-checkbox-inline">
			<label><fmt:message key="trainingCenter_Specialities" /><span style="color:red;">(* requis | required)</span></label>
			 
			    <c:forEach var="trainingCenterSpeciality" items="${trainingCenterSpecialities}"> 
		        <label class="kt-checkbox" cheched="">
                   <input  name="specialities"  type="checkbox" value="${trainingCenterSpeciality.speciality.id}" checked="checked">${trainingCenterSpeciality.speciality.abbreviation}
				   <span></span>
				</label>
				</c:forEach>  
				
				 <c:forEach var="specialityLeft" items="${specialitiesLeft}"> 
			        <label class="kt-checkbox" cheched="">
						<input  name="specialities"  type="checkbox" value="${specialityLeft.id}">${specialityLeft.abbreviation}
						<span></span>
					</label>
				</c:forEach>
		  
			</div>
		</div>	
		 </c:if>	
		<hr>
		
		<div class="col-md-12">
			<label><fmt:message key="trainingCenter_agreementAuthorisationNumber" /><span style="color:red;">*</span></label>
			<input name="agreementAuthorisationNumber" id="agreementAuthorisationNumber"  value="${trainingCenter.agreements[0].authorisationNumber}" type="text" class="form-control"  required >
		</div>
		
		<div class="col-md-12">
			<label><fmt:message key="agreement_issuedDate" /><span style="color:red;">*</span></label>
			<fmt:formatDate value="${trainingCenter.agreements[0].issuedDate}" type="date" pattern="dd/MM/yyyy" var="issueDate"/>
			<input name="dates" id="agreementIssuedDate" name="agreementIssuedDate" value="${issueDate }" type="text" class="form-control" placeholder="ex : 24/08/2020 " required >
		</div>
		<div class="col-12">
			<div class="kt-checkbox-inline">
			     <label><fmt:message key="trainingCenter_division" /></label>
			     <select id="divisionId" class="form-control kt-input" data-col-index="2">
		                <option value="0"><fmt:message key="select_one" /></option>
		                <c:forEach var="division" items="${divisions}">
		                	<c:if test="${trainingCenter.division.id ==  division.id}">
		                    	<option value="${division.id}" selected>${division.name}</option>
		                    </c:if>
		                    <c:if test="${trainingCenter.division.id !=  division.id}">
		                    	<option value="${division.id}" >${division.name}</option>
		                    </c:if>
		                </c:forEach>
			     </select>
			</div>
		</div>
		
		<div class="kt-separator kt-separator--border-dashed kt-separator--space-lg kt-separator--portlet-fit"></div>
		<div id="filesContainer" class="kt-portlet__body">
			 <div class="form-group row file">
				<label><fmt:message key="agreement.file.label" /></label>
				<div class="custom-file">										
					<div class="kt-avatar" id="kt_user_avatar_file">
						<div id="fileHolder" class="kt-avatar__holder" style="background-image: url(assets/media/files/pdf.svg)"></div>
							<canvas id="pdf-canvas" width="30" height="30" class="pdf-canvas" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; display:none;"></canvas>
								<label id="changeFile" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar">
								<i class="fa fa-pen"></i>
								<input name="fileInput" id="fileInput" class="kt-avatar__input_file" type="file" name="profile_avatar" accept=".pdf">
								</label>
								<span id="fileCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
										<i class="fa fa-times"></i>
								</span>
								</div>
				</div>
				</div>
				<br/><br/><br/>
	   </div>
	

	</div>
	
     <div class="kt-portlet__foot">
		<div class="kt-form__actions">
			<button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" createTrainingCenter('${pageContext.request.contextPath}','${trainingCenter.id}');"><fmt:message key="save.key"/></button>
		</div>
	</div>
</form>

<!--end::Form-->
	</div>
</div>

<script>
	 $('input[name="dates"]').datepicker({'format':'dd/mm/yyyy'});
	 pushAvatarsEvents("changeFile","fileInput","fileHolder","kt_user_avatar_file","fileCancel");
</script>


