<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="kt-portlet">
<div class="kt-portlet__head">
	<div class="kt-portlet__head-label">
		<h3 class="kt-portlet__head-title"><fmt:message key="show.trainingCenter.key"/></h3>
	</div>
</div>
<div class="kt-portlet__body" id="trainingCenterFormId">

	<!--begin::Form-->
<form class="kt-form">
	<div class="kt-portlet__body form-group row">
	   <div class="col-12">
			<label><fmt:message key="trainingCenter_Name" /></label>
			<input name="name" id="name"  type="text" class="form-control"  value="${trainingCenter.name}"   readonly >
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_FounderName" /></label>
			<input name="founder" id="founder"  type="text" class="form-control" value="${trainingCenter.founder}"  readonly >
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_OwnerName" /></label>
			<input name="owner" id="owner"  type="text" class="form-control"  value="${trainingCenter.owner}" readonly >
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_OwnerContact" /></label>
			<input name="ownerContact" id="ownerContact"  type="text" class="form-control"   value="${trainingCenter.owner}" readonly >
			<span style="color:red;">Please Fill a valid phone number | Veuillez renseignez un numeros de tephone valide</span>
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_location" /></label>
			<input name="location" id="location"  type="text" class="form-control" value="${trainingCenter.location}" readonly >
			<span style="color:red;">Please Fill town(quater) | Veuillez renseignez ville(quarier) Eg Douala(Rond point Deido)</span>
		</div>
		<div class="col-12">
			<label><fmt:message key="trainingCenter_maxStudent" /></label>
			<input name="maxStudent" id="maxStudent"  type="number" class="form-control"  value="${trainingCenter.maxStudent}"  readonly >
		</div>
		<c:if test="${fn:length(trainingCenterSpecialities) > 0}">
		<div class="col-12">
			<div class="kt-checkbox-inline">
			<label><fmt:message key="trainingCenter_Specialities" /><span style="color:red;">(* requis | required)</span></label>
			 
			    <c:forEach var="trainingCenterSpeciality" items="${trainingCenterSpecialities}"> 
		        <label class="kt-checkbox" cheched="">
                   <input  name="specialities"  type="checkbox" value="${trainingCenterSpeciality.speciality.id}" checked="checked" disabled>${trainingCenterSpeciality.speciality.abbreviation}
				   <span></span>
				</label>
				</c:forEach>  
				
				 <c:forEach var="specialityLeft" items="${specialitiesLeft}"> 
			        <label class="kt-checkbox" cheched="">
						<input  name="specialities"  type="checkbox" value="${specialityLeft.id}" disabled>${specialityLeft.abbreviation}
						<span></span>
					</label>
				</c:forEach>
		  
			</div>
		</div>	
		 </c:if>	
		<hr>
		
		<div class="col-12">
			<div class="kt-checkbox-inline">
			     <label><fmt:message key="trainingCenter_department" /></label>
			     <select id="divisionId" class="form-control kt-input" data-col-index="2">
		                <c:forEach var="division" items="${divisions}">
		                	<c:if test="${trainingCenter.division.id ==  division.id}">
		                    	<option value="${division.id}" selected>${division.name}</option>
		                    </c:if>
		                </c:forEach>
			     </select>
			</div>
		</div>
		
        <div class="kt-heading kt-heading--md"><fmt:message key="archive.details.files" /></div><hr>
            <div class="kt-section kt-section--first">
                <div class="kt-wizard-v4__form">
                    <div class="row col-sm-12">
                        <c:forEach var="agreement" items="${trainingCenter.agreements}">
                            <div class="col-sm-3">
                                <a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${agreement.id}.pdf&folderName=trainingCenter.agreement.file.folder">${agreement.authorisationNumber}</a>
                                <hr>
                            </div>
                        </c:forEach>
                        <hr>
                    </div>

                </div>
            </div>
        </div>

	</div>
	

</form>

<!--end::Form-->
	</div>
</div>



