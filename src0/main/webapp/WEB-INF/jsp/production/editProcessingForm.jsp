<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

							<div class="kt-subheader-search ">
							<div class="kt-container  kt-container--fluid ">
								<h3 class="kt-subheader-search__title">
								<span>Edit Application |<strong>${application.serialNumber }</strong> </span>	
									
								</h3>
								
							</div>
						</div>

						<!-- begin:: Content -->
						<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
							<div class="kt-portlet">
								<div class="kt-portlet__body kt-portlet__body--fit">
									<div class="kt-grid kt-wizard-v1 kt-wizard-v1--white" id="kt_wizard_v1" data-ktwizard-state="first">
										<div class="kt-grid__item">


										</div>
										<div class="kt-grid__item kt-grid__item--fluid kt-wizard-v1__wrapper">

											<!--begin: Form Wizard Form-->
											<form class="kt-form" id="add_application_form" name="add_application_form">

												<!--begin: Form Wizard Step 1-->
												
													<div class="kt-heading kt-heading--md" style="font-weight: bold;">Personal Information</div>
													<hr style="border:1px solid green; border-radius:1px;">
														<div class="kt-wizard-v1__form">
														    <div class="row" >
														        <div class="col-xl-3 " style="margin-bottom:70px;" id="block-photo">
																	<div class="form-group">
																		<label class="col-xl-3 col-lg-3 col-form-label">photo</label>
													
																		<div class="custom-file col-lg-9 col-xl-6">										
																			<div class="kt-avatar kt-avatar--outline kt-avatar--circle" id="kt_user_avatar_photo">
																		
																				<c:if test="${application.certificate!=Null && application.photo != null}">
																				
																				   <div id="photoHolder" class="kt-avatar__holder"  style="background-image: url('${pageContext.request.contextPath}/file/download?fileName=${application.photo}&folderName=application.photo.folder')" urlFile="${pageContext.request.contextPath}/file/download?fileName=${application.photo}&folderName=application.photo.folder"> </div>
																				</c:if> 
																				<c:if test="${application.transcript!=Null && application.photo != null}">
																				
																				   <div id="photoHolder" class="kt-avatar__holder" style="background-image: url('${pageContext.request.contextPath}/file/download?fileName=${application.photo}&folderName=application.photo.folder')" urlFile="${pageContext.request.contextPath}/file/download?fileName=${application.photo}&folderName=application.photo.folder"> </div>
																				</c:if> 
																				
																				<c:if test="${(sourceEntity== 'StudentSession' && studentSession.photoAndSignature == null)}">
																				knknk
																				<div id="photoHolder" class="kt-avatar__holder" recordFromCandidateSession="0" style="background-image: url(assets/production/images/application-photo.jpg)"> 
																				</div>
																				</c:if> 
																			
																				<label id="changePhoto" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar" >
																					<i class="fa fa-pen"></i>
																					<input id="photoInput"  type="file" name="profile_avatar" accept=".png, .jpg, .jpeg">
																				</label>
																				<span id="photoCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																					<i class="fa fa-times"></i>
																				</span>
																	        </div>
																		</div>
																	</div>
																</div>
																<div class="col-xl-3 " style="margin-bottom:70px;" id="block-signature">
																	<div class="form-group">
																		<label class="col-xl-3 col-lg-3 col-form-label">Signature</label>
													
																		<div class="custom-file col-lg-9 col-xl-6">										
																			<div class="kt-avatar kt-avatar--outline" id="kt_user_avatar_signature">
																				
																				<c:if test="${application.certificate!=Null && application.signature != null}">
																				   <div id="signatureHolder" class="kt-avatar__holder" style="background-image: url('${pageContext.request.contextPath}/file/download?fileName=${application.signature}&folderName=application.signature.folder')" urlFile="${pageContext.request.contextPath}/file/download?fileName=${application.signature}&folderName=application.signature.folder"> </div>
																				</c:if> 
																				<c:if test="${application.transcript!=Null && application.signature != null}">
																				   <div id="signatureHolder" class="kt-avatar__holder" style="background-image: url('${pageContext.request.contextPath}/file/download?fileName=${application.signature}&folderName=application.signature.folder')" urlFile="${pageContext.request.contextPath}/file/download?fileName=${application.signature}&folderName=application.signature.folder"> </div>
																				</c:if>
																				
																				<c:if test="${(sourceEntity== 'StudentSession' && studentSession.photoAndSignature == null)}">
																					<div id="signatureHolder" class="kt-avatar__holder" recordFromCandidateSession="0" style="background-image: url(assets/production/images/application-signature.jpg)"></div>
																				</c:if>
																				
																				<label id="changeSignature" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar" >
																					<i class="fa fa-pen"></i>
																					<input id="signatureInput"  type="file" name="profile_avatar" accept=".png, .jpg, .jpeg">
																				</label>
																				<span id="signatureCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																					<i class="fa fa-times"></i>
																				</span>
																	        </div>
																		</div>
																	</div>
																</div>
															    <div class="col-xl-3">
																	<div class="form-group">
																		
																	</div>
																</div>
																<div class="col-xl-3">
																	<div class="form-group">
																		
																	</div>
																</div>
															</div>
															<div class="row">							
																<div class="col-xl-3">
																	<div class="form-group">
																		<label style="font-weight: bold;">Nom</label>
																		<input type="text" class="form-control"  id="surName" name="surName" value="${person.surName}" readonly>
																	</div>
																</div>
																<div class="col-xl-3">
																	<div class="form-group">
																		<label style="font-weight: bold;">Prenom</label>
																		<input type="text" class="form-control" id="givenName" name="givenName" value="${person.givenName}" readonly>
																	</div>
																</div>
																<div class="col-xl-3 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Place of Birth</label>
																		<input type="text" class="form-control" id="pob" name="pob"  value="${person.pob}" readonly>
																	</div>
																</div>
																<div class="col-xl-3 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Date of Birth</label>
																		<fmt:formatDate value="${person.dob}" pattern="dd/MM/yyyy" var="personDob" />
																		<input type="text" class="form-control " id="dob" name="dob"  placeholder="dd/mm/yyyy"   value="${personDob}" readonly>
																	</div>
																</div>
															</div>
															
															<div class="row">							
																<div class="col-xl-3 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Phone Number</label>
																		<input type="text" class="form-control" id="phoneNumber" name="phoneNumber" pattern="(2|6)\d{8}$" value="${person.phoneNumber}" >
																	</div>
																</div>
																<div class="col-xl-3 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Gender:</label>
																		<select  class="form-control"  id="gender" name="gender"  >
																			
													                          <c:if test="${person.gender=='M'}">
														                      		<option value="M" selected><fmt:message key="male.gender" /></option>
													                          </c:if>
													                          <c:if test="${person.gender=='F'}">
														                            <option value="F" selected><fmt:message key="female.gender" /></option>
													                          </c:if>
												                       
																		</select>
																	</div>
																</div>
																<div class="col-xl-3 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Nationality:</label>
																		<select readonly  class="form-control"  id="nationality" name="nationality"  >
																			<option value=""><fmt:message key="select.fill.person.nationality"/></option>
																			<c:forEach var="country" items="${countries}">
																				<c:if test="${person.nationality.id == country.id}">
											                                		<option value="${country.id }" selected>${country.countryName}</option>
											                                	</c:if>
										                                    </c:forEach>
																		</select>
																																
																	</div>
																</div>
																<div class="col-xl-3">
																	<div class="form-group">
																		<label style="font-weight: bold;" >Language</label>
																		<select  class="form-control"  id="language" name="language"  >
																			
													                          <c:if test="${person.language=='FR'}">
														                      		<option value="FR" selected><fmt:message key="app.user.langue.french" /></option>
													                          </c:if>
													                          <c:if test="${person.language=='EN'}">
														                            <option value="EN" selected><fmt:message key="app.user.langue.english" /></option>
													                          </c:if>
													                          <c:if test="${person.language==null}">
														                            <option value="">Select your language ...</option>
																					<option value="FR"><fmt:message key="app.user.langue.french" /></option>
																					<option value="EN"><fmt:message key="app.user.langue.english" /></option>
													                          </c:if>
												                          
																		</select>
																	</div>
																</div>
																
															</div>
															
														</div>


												<!--end: Form Wizard Step 1-->

												<!--begin: Form Wizard Step 2-->
													<div class="kt-heading kt-heading--md" style="font-weight: bold;"><fmt:message key="process.record.registration.next.application.details" /></div>
													<hr style="border:1px solid green; border-radius:1px;">
														<div class="kt-wizard-v1__form">
														<div class="row">							
																
																
																<div class="col-sm-4 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Application Number</label>
																		<input readonly type="text" class="form-control" id="applicationNumber" name="applicationNumber" placeholder="Please enter your application number ... "  pattern="[0-9]+" title="please only numbers / svp uniquement des nombres" value="${application.number}" >
																	</div>
																</div>
																<div class="col-sm-4 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Authority:</label>
																		<select  class="form-control"  id="authority" name="authority"  >
																			<c:forEach var="authority" items="${authorities}">
																				<option value="${authority.id}">${authority.position}</option>
																			</c:forEach>
																			
																		</select>
																	</div>
																</div>
																<div class="col-sm-4 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Form Serial Number</label>
																		<input type="text" class="form-control" id="formSerialNumber" name="formNumber" placeholder="Please enter your form Number ... "  pattern="[0-9]+" title="please only numbers / svp uniquement des nombres" value="${application.formSerialNumber}" >

																	</div>
																</div>
																
															</div>
															

														</div>

													
													
													<span><h4> <fmt:message key="application.details.backing.doc" /></h4></span>
													<hr style="border:1px solid green; border-radius:1px;">
													<div class="row">
														<c:forEach var="applicationFile" items="${application.applicationFiles}">
															<div class="col-sm-3">
																<a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${applicationFile.name}&folderName=application.file.folder">${applicationFile.name }</a> 
										                        	<hr>
															</div>
														</c:forEach>
													 </div>	



												<!--end: Form Wizard Step 4-->

												
													
												</div>
                                                <div id="sub" class="kt-form__actions">
												    
												    <button type="button"  id="submit-edit-application" class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-right" onclick="editApplication(${application.id});">
														Submit
													</button>                                                
												</div>

											</form>
										</div>
									
											<!--end: Form Wizard Form-->
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- end:: Content -->
						
						<script>
					
						pushAvatarsEvents("changePhoto","photoInput","photoHolder","kt_user_avatar_photo","photoCancel");
						pushAvatarsEvents("changeSignature","signatureInput","signatureHolder","kt_user_avatar_signature","signatureCancel");
						pushAvatarsEvents("changeFile","fileInput","fileHolder","kt_user_avatar_file","fileCancel");
						datePickersInitializer();
						</script>