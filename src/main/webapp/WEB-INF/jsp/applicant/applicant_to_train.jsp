<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="${pageContext.request.contextPath}/css/candidate.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/applicant/applicant.js" type="text/javascript"></script>


<!-- begin:: Content -->
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
	<div class="row">
		<div class="col-lg-12">

			<!--begin::Portlet-->
			<div class="kt-portlet">
				<div class="kt-portlet__head">
					<div class="kt-portlet__head-label">
						<h3 class="kt-portlet__head-title" style="color:black;" >
						</h3>
					</div>
				</div>
				<div class="kt-portlet__head-label col-lg-6">
									<h2>
             	                        ${applicant.person.surName}  ${applicant.person.givenName}
									</h2>
								</div>
				
				<div  id="load-preview-step1" style="color:white;margin-left:42%;margin-right:42%;" > </div>	
					<!--begin::Form-->
				    <form class="kt-form kt-form--label-right" id="add_applicant_form" name="add_applicant_form">
					    <input id="applicantId" type="hidden" value="${applicant.id}">
					    <div class="kt-portlet__body">
							<div id="divBlock">
							
							<div class="form-group row" id="div4">
							
								
								<div class="col-lg-6">
									<label style="color:#564FC1;"><fmt:message key="select.training.center.key" /></label>
									<select name="training-center"  id="training-center" class="form-control">
										<option value=""><fmt:message key="select.fill.training.center"/></option>
										<c:forEach var="trainingCenter" items="${trainingCenters}">
											<option value="${trainingCenter.id }">${trainingCenter.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-lg-12 kt-align-right">
									<button type="button" class="btn btn-brand"  id="create_applicant" onclick="createStudent('${pageContext.request.contextPath}/applicant/createStudent', '${applicant.id}');" >Submit</button>
								</div>
							</div>
							
							
							
							
					
						</div>
					</form>
					<!--end::Form-->					


		</div>
		</div>
	</div>
</div>
	<!-- end:: Content -->