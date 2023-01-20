<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<link href="${pageContext.request.contextPath}/css/candidate.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/pv/candidateSession.js" type="text/javascript"></script>


<div class="kt-subheader-search app-color ">
	<div class="kt-container  kt-container--fluid ">
		<h6 class="kt-subheader-search__title">
			<fmt:message key="computerize.candidate" />
		</h6>
	</div>
</div>

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
				
				<div  id="load-preview-step1" style="color:white;margin-left:42%;margin-right:42%;" > </div>	
				<input id="trainingSchoolId" name="drivingSchoolId" type="hidden" value="0">
                  <sec:authorize access="hasRole('ROLE_MANAGE_STUDENTS_UNDER_TRAINING')">
                  
					<!--begin::Form-->
				    <form class="kt-form kt-form--label-right" id="add_candidate_form" name="add_candidate_form">
					    <input id="candidateId" type="hidden" value="${candidateForm.id}">
					    <input id="eligibleCenterId" name="eligibleCenterId" type="hidden" value="${eligibleCenterId}">
						<div class="kt-portlet__body">
							<div id="divBlock1">
							
								<div class="kt-portlet__head-label col-lg-6">
									<h2>
             	                           Info de Traitement/ <i>Process Info</i>
									</h2>
								</div>
								
								<div class="form-group row" id="div4">
								<div class="col-lg-4">
									<label  style="color:#564FC1;"><fmt:message key="person.licenseNum" />*</label>
									<input name="licenseNum" id="licenseNum" type="text" class="form-control" placeholder="Enter Licence Number" required>
								</div>
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.specialty" />*</label>
									<select name="speciality"  id="speciality" class="form-control">
										<option value=""><fmt:message key="select.fill.person.speciality"/></option>
										<option value="1">MAE</option>
									</select>
								</div>
								
								 <div class="col-lg-4">
        							<label style="color:#564FC1;"><fmt:message key="person.diplome" />*</label>
        							<a class="btn-floating peach-gradient mt-0 ">
           								 <i class="fas fa-paperclip" aria-hidden="true"></i>
         									 <input name="scannedDiplome" type="file" required class="form-constrol" id="diplome">
        						    </a>
								</div>  
								
							<!--	<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.diplome" />*</label>
									<select name="diplome"  id="diplome" class="form-control">
										<option value=""><fmt:message key="select.fill.person.diplome"/></option>
										<option value="BACC"><fmt:message key="select.option.bacc.or.equivalent"/></option>
										<option value="CAPEC"><fmt:message key="select.option.capec"/></option>
										<option value="BEPEC"><fmt:message key="select.option.ordinary.level"/></option>
									</select> 
								</div>  -->
							</div>
							
							<div class="kt-portlet__foot">
								<div class="kt-form__actions">
									<div class="row">
										<div class="col-lg-12 kt-align-right">
								        	<button class="btn btn-primary nextBtn btn-lg pull-right" type="button" onclick="verifdivBlock1(); " id="buttonopen1">Next</button>
										</div>
									</div>
								</div>
							</div>
							
							</div>
							
							<div id="divBlock2" style="display:none;">
							<div class="kt-portlet__head-label col-lg-6">
									<h2>
             	                           Info Personelle/ <i>Personal Info</i>
									</h2>
							</div></br>
							
							<div class="form-group row" id="div1" >
								<div class="col-lg-4" >
									<label  style="color:#564FC1;"><fmt:message key="person.name" /></label>
									<input name="surName" id="surName" type="text" class="form-control" placeholder="Enter name" required >
								</div>
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.given.name" /></label>
									<input name="givenName"  id="givenName" type="text" class="form-control" placeholder="Enter given name">
								</div>
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.dob" /></label>
									<input  name="dob" id="dob" type="text" class="form-control" placeholder="dd/mm/yyyy" required>
								</div>
							</div>
							<div class="form-group row" id="div2">
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.gender" />*</label>
									<select name="gender" id="gender" class="form-control">
										<option value="">Select your gender...</option>
										<option value="F"><fmt:message key="female.gender" /></option>
										<option value="M"><fmt:message key="male.gender" /></option>
									</select>
								</div>
											
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.pob" /></label>
									<input name="pob" id="pob" type="text" class="form-control" placeholder="Enter place of birth (Eg Yaounde )">
								</div>
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.nationality" />*</label>
									<select name="nationality"  id="nationality" class="form-control">
										<option value=""><fmt:message key="select.fill.person.nationality"/></option>
										<c:forEach var="country" items="${countries}">
		                                	<option value="${country.id }">${country.countryName}</option>
		                                </c:forEach>
									</select>
								</div>
							</div>																				
							<div class="form-group row" id="div3">
							
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="category.b.date" /></label>
									<input name="catBdate" id="catBdate" type="text" class="form-control">
								</div>	
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="email" /></label>
									<input name="email" id="email" type="text" class="form-control">
								</div>
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="phone.number" />*</label>
									<input name="phoneNumber" id="phoneNumber" pattern="(2|6)\d{8}$" placeholder="Ex: 696764242" type="text" class="form-control" required>
								</div>									
																			
							</div>
							
								
										
						<!--end::Portlet-->
	
						<div class="col-md-12 ">
	                		<div class="form-group">
	                    		<div id="new-file-div" class="row">
	
	                    		</div>
	                		</div>
	            		</div>
						<div class="kt-portlet__foot">
							<div class="kt-form__actions">
								<div class="row">
									<div class="col-lg-5"></div>
									<div class="col-lg-7">
										<button type="button" class="btn btn-brand"  id="create_candidate" onclick="bootstapValidationStudent();" >Submit</button>
										<button type="button" class="btn btn-secondary">Cancel</button>
									</div>
								</div>
							</div>
						</div>
						
						</div>
						</div>
					</form>
					<!--end::Form-->					

                    </sec:authorize >
                     
				
            <!--end::Portlet-->	

		
			 
			<div  class="kt-portlet__body">
				<div id="candidate-list">
					<%@include file="list_student.jsp" %>
				</div>
			</div>	
										

		</div>
		</div>
	</div>
</div>
	<!-- end:: Content -->

       
       
         <script>
		datePickersInitializer();
	    </script>
    
<script src="${pageContext.request.contextPath}/resources/static/js/pdf.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/static/js/pdf.worker.js" type="text/javascript"></script>

<!-- <script src="${pageContext.request.contextPath}/resources/static/js/helper.js" type="text/javascript"></script> 

 <script src="${pageContext.request.contextPath}/resources/static/js/core.js" type="text/javascript"></script> -->

  <script>
	   $(function() {
		    var temp_id="31"; 
		    $("#nationality").val(temp_id);
		});
  </script>
  
