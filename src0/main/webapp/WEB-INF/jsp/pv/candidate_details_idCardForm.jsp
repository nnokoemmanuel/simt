<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${pageContext.request.contextPath}/resources/static/js/pv/candidateSession.js" type="text/javascript"></script>

<div class="kt-portlet">
			<!--begin::Form-->
				    <form class="kt-form kt-form--label-right" id="add_CNI_formID" name="add_CNI_formID">
					    <input id="candidateId" type="hidden" value="${candidateForm.id}">
					    <input id="eligibleCenterId" name="eligibleCenterId" type="hidden" value="${eligibleCenterId}">
					    <input id="studentSession-Id" type="hidden" value="${studentSession.id}">
						<div class="kt-portlet__body">
							<div id="divBlock1">
							
								<div class="kt-portlet__head-label col-lg-12">
									<h2>
             	                       INFORMATIONS DE LA CNI  / <i> ID CARD INFORMATIONS </i>
									</h2>
								</div>
								
				 <div class="form-group row"> 				
								<div class="col-lg-4">
									<label  style="color:#564FC1;"><fmt:message key="person.surnameCni" />*</label>
									<input name="surnameCni" id="surNameCni" type="text" class="form-control" placeholder="Enter SurName CNI" value="${studentSession.student.person.surnameCni}" required>
								</div>
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.givenNameCni" /></label>
									<input name="givenNameCni" id="givenNameCni" type="text" class="form-control" placeholder="Enter GivenName CNI" value="${studentSession.student.person.givenNameCni}" >
								</div>
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.dobCni" /></label>
									<fmt:formatDate value="${studentSession.student.person.dobCni}" pattern="dd/MM/yyyy" var="dobCniDate" />
									<input name="dates" id="dobCni"  value="${dobCniDate}" type="text" class="form-control" placeholder="dd/mm/yyyy" required>
								</div>
								
				  </div>
				  
				  
				   <div class="form-group row"> 
				                	
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.pobCni" />*</label>
								    <input name="pobCni" id="pobCni" value="${studentSession.student.person.pobCni}" type="text" class="form-control" placeholder="Enter pob CNI" required>
								</div>
											
								<div class="col-lg-4">
									<label  style="color:#564FC1;"><fmt:message key="person.genderCni" />*</label>
									<select name="genderCni" id="gendeCni" class="form-control">
			
										
										<c:if test="${empty studentSession.student.person.genderCni}">
					                    	<option value="">Select your gender...</option>
											<option value="F"><fmt:message key="female.gender" /></option>
											<option value="M"><fmt:message key="male.gender" /></option>
				                          </c:if>
				                           <c:if test="${not empty studentSession.student.person.genderCni}">
					                            <option value="">Select your gender ...</option>
												
												 <c:if test="${studentSession.student.person.genderCni != 'F'}">
												 	<option value="${studentSession.student.person.genderCni}" selected><fmt:message key="male.gender" /></option>
													<option value="F"><fmt:message key="female.gender" /></option>
				                                 </c:if>
				                                   <c:if test="${studentSession.student.person.genderCni != 'M'}">
												 	<option value="${studentSession.student.person.genderCni}" selected><fmt:message key="female.gender" /></option>
													<option value="M"><fmt:message key="male.gender" /></option>
				                                 </c:if>
				                          </c:if>
									</select>
								</div>
								
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.fatherCni" /></label>
									<input name="fatherCni" id="fatherCni" value="${studentSession.student.person.fatherCni}" type="text" class="form-control"  placeholder="Enter father" required>
								</div>
				  </div>
				  
				  
				   <div class="form-group row"> 	
				   
				                <div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.motherCni" /></label>
									<input name="motherCni" id="motherCni"  value="${studentSession.student.person.motherCni}" type="text" class="form-control"  placeholder="Enter mother" required>
								</div>
											
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.professionCni" />*</label>
									<input name="professionCni" id="professionCni" value="${studentSession.student.person.professionCni}" type="text" class="form-control" placeholder="Enter profession" required>
								</div>
								
							
								
								<div class="col-lg-4">
									<label  style="color:#564FC1;"><fmt:message key="person.addressCni" />*</label>
									<input name="addressCni" id="addressCni" value="${studentSession.student.person.addressCni}" type="text" class="form-control" placeholder="Enter address" required>
								</div>
				  </div>
				  
				  
				  
				     <div class="form-group row"> 				
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.heightCni" />*</label>
									<input name="heightCni" id="heightCni" value="${studentSession.student.person.heightCni}" type="text" class="form-control" placeholder="height from CNI eg 1.68m" required>
								</div>
								
								<div class="col-lg-4">
									<label  style="color:#564FC1;"><fmt:message key="person.issuedDateCni" />*</label>
									<fmt:formatDate value="${studentSession.student.person.issuedDateCni}" pattern="dd/MM/yyyy" var="issuedDateCniDate" />
									<input name="dates" id="issuedDateCni" type="text" value="${issuedDateCniDate}" class="form-control" placeholder="dd/mm/yyyy" required>
								</div>
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.nic" />*</label>
									<input name="idcardnumber" id="idCardNumber"  value="${studentSession.student.person.nic}" type="text" class="form-control" placeholder="IDCMR0123456789" required>
								</div>
				     </div>
						  
				       <div class="form-group row"> 				
								<div class="form-group">
									<label style="color:#564FC1;" >Language *</label>
									<select  class="form-control"  id="language" name="language"  >

				                          <c:if test="${studentSession.student.person.language==null}">
					                            <option value="">Select your language ...</option>
												<option value="FR"><fmt:message key="app.user.langue.french" /></option>
												<option value="EN"><fmt:message key="app.user.langue.english" /></option>
				                          </c:if>
				                           <c:if test="${studentSession.student.person.language != null}">
					                            <option value="">Select your language ...</option>
												<option value="${studentSession.student.person.language}" selected>${studentSession.student.person.language}</option>
												 <c:if test="${studentSession.student.person.language != 'EN'}">
													<option value="EN"><fmt:message key="app.user.langue.english" /></option>
				                                 </c:if>
				                                  <c:if test="${studentSession.student.person.language != 'FR'}">
													<option value="FR"><fmt:message key="app.user.langue.french" /></option>
				                                 </c:if>
				                          </c:if>
			                          
									</select>
								</div>
								
				     </div>
						  
					<label style="color:#564FC1;font-weight: bold;"><b><fmt:message key="diplome.infos" /></b></label>  	
			<div class="kt-portlet__foot">
					<div class="form-group row"> 
						  
							
								
									<%-- <div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="person.diplome" />*</label>
									<select name="diplome"  id="diplome" class="form-control">
										<option value=""><fmt:message key="select.fill.person.diplome"/></option>
										<option value="BACC"><fmt:message key="select.option.bacc.or.equivalent"/></option>
										<option value="CAPEC"><fmt:message key="select.option.capec"/></option>
										<option value="BEPEC"><fmt:message key="select.option.ordinary.level"/></option>
									</select>
								    </div> --%>
								    
								    <div class="col-lg-4">
									    <label  style="color:#564FC1;"><fmt:message key="person.diplome" />*</label>
									    <input name="diplome" id="diplome" type="text" value="${studentSession.studentQualification.entryCertificateName}"  class="form-control" placeholder="Enter Diplome Info" required>
								    </div>
								    
								 <div class="col-lg-4">
									<label  style="color:#564FC1;"><fmt:message key="qualification.issuedDate" />*</label>
									<fmt:formatDate value="${studentSession.studentQualification.issuedDate}" pattern="dd/MM/yyyy" var="diplomeIssuedDate" />
									<input name="dates" id="diplomeIssuedDate" type="text" value="${diplomeIssuedDate}" class="form-control" placeholder="dd/mm/yyyy" required>
								</div>
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="qualification.issuedPlace" />*</label>
									<input name="issuedPlace" id="diplomeIssuedPlace" value="${studentSession.studentQualification.issuedPlace}"  type="text" class="form-control" placeholder="Yaounde" required>
								</div>
								
								<div class="col-lg-4">
									<label style="color:#564FC1;"><fmt:message key="qualification.diplomeOption" />*</label>
									<input name="diplomeOption" id="diplomeOption" value="${studentSession.studentQualification.diplomeOption}"  type="text" class="form-control" placeholder="Mathematiques" required>
								</div>
								
																
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
										<button type="button" class="btn btn-brand"  id="create_CNI_BUTTONID" onclick="bootstapValidationCNI();" >Submit</button>
										<button type="button" class="btn btn-secondary" onclick="closeNav();">Cancel</button>
									</div>
								</div>
							</div>
						</div>
						
						</div>
						</div>
					</form>
	
</div>



    <script>
		datePickersInitializer();
	</script>
	<script>
	 $('input[name="dates"]').datepicker({'format':'dd/mm/yyyy'});
</script>
