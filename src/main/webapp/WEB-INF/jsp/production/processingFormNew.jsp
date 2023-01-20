<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

							<div class="kt-subheader-search ">
							<div class="kt-container  kt-container--fluid ">
								<h3 class="kt-subheader-search__title">
									File Processing Form
									<span class="kt-subheader-search__desc">please fill the form below to process the record ...</span>
								</h3>
								
							</div>
						</div>

						<!-- begin:: Content -->
						<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
							<div class="kt-portlet">
								<div class="kt-portlet__body kt-portlet__body--fit">
									<div class="kt-grid kt-wizard-v1 kt-wizard-v1--white" id="kt_wizard_v1" data-ktwizard-state="first">
										<div class="kt-grid__item">

											<!--begin: Form Wizard Nav -->
											<div class="kt-wizard-v1__nav">

												<!--doc: Remove "kt-wizard-v1__nav-items--clickable" class and also set 'clickableSteps: false' in the JS init to disable manually clicking step titles -->
												<div class="kt-wizard-v1__nav-items kt-wizard-v1__nav-items--clickable">
													<div id="icone-step-1" class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="current"  >
														<div class="kt-wizard-v1__nav-body">
															<div class="kt-wizard-v1__nav-icon">
																<!--  <i class="flaticon-bus-stop"></i>-->
																<i class="flaticon-profile-1"></i>
															</div>
															<div class="kt-wizard-v1__nav-label">
																1. <fmt:message key="process.record.registration.next.personal.informations" />
															</div>
														</div>
													</div>
													<div id="icone-step-2"  class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="pending" >
														<div class="kt-wizard-v1__nav-body">
															<div class="kt-wizard-v1__nav-icon">
																<i class="flaticon-list"></i>
															</div>
															<div class="kt-wizard-v1__nav-label">
																2.  <fmt:message key="process.record.registration.next.application.details" />
															</div>
														</div>
													</div>
													<div id="icone-step-3"  class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="pending" >
														<div class="kt-wizard-v1__nav-body">
															<div class="kt-wizard-v1__nav-icon">
																<i class="flaticon2-document"></i>
															</div>
															<div class="kt-wizard-v1__nav-label">
																3.   <fmt:message key="process.record.registration.next.capacity.details" />
															</div>
														</div>
													</div>
													<div id="icone-step-4"  class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="pending" >
														<div class="kt-wizard-v1__nav-body">
															<div class="kt-wizard-v1__nav-icon">
																<i class="flaticon-upload"></i>
															</div>
															<div class="kt-wizard-v1__nav-label">
																4.  <fmt:message key="process.record.registration.next.Backing.Documents" />
															</div>
														</div>
													</div>
													<div id="icone-step-5"  class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="pending" >
														<div class="kt-wizard-v1__nav-body">
															<div class="kt-wizard-v1__nav-icon">
																<i class="flaticon-globe"></i>
															</div>
															<div class="kt-wizard-v1__nav-label">
																5.  <fmt:message key="process.record.registration.next.Review.Submit" />
															</div>
														</div>
													</div>
												</div>
											</div>

											<!--end: Form Wizard Nav -->
										</div>
										<div class="kt-grid__item kt-grid__item--fluid kt-wizard-v1__wrapper">

											<!--begin: Form Wizard Form-->
											<form class="kt-form" id="add_application_form" name="add_application_form">

												<!--begin: Form Wizard Step 1-->
												
												<div id="step-1" class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="current"  >
													<div class="kt-heading kt-heading--md" style="font-weight: bold;">Personal Informations</div>
													<div class="kt-form__section kt-form__section--first">
														<div class="kt-wizard-v1__form">
														    <div class="row" >
														        <div class="col-xl-3 " style="margin-bottom:70px;" id="block-photo">
																	<div class="form-group">
																		<label class="col-xl-3 col-lg-3 col-form-label">photo</label>
													
																		<div class="custom-file col-lg-9 col-xl-6">										
																			<div class="kt-avatar kt-avatar--outline kt-avatar--circle" id="kt_user_avatar_photo">
																				<c:if test="${sourceEntity== 'CandidateSession' && candidateSession.photoAndSignature != null}">
																				   <div id="photoHolder" class="kt-avatar__holder" recordHasPhoto="1" style="background-image: url('${pageContext.request.contextPath}/file/download?fileName=${candidateSession.photoAndSignature}&folderName=candidateSession.photo.folder')" urlFile="${pageContext.request.contextPath}/file/download?fileName=${candidateSession.photoAndSignature}&folderName=candidateSession.photo.folder"> </div>
																				</c:if> 
																				<c:if test="${(sourceEntity== 'CandidateSession' && candidateSession.photoAndSignature == null)||(sourceEntity!= 'CandidateSession')}">
																				<div id="photoHolder" class="kt-avatar__holder" recordHasPhoto="0" style="background-image: url(assets/production/images/application-photo.jpg)"> 
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
																			    <c:if test="${sourceEntity== 'CandidateSession' && candidateSession.photoAndSignature != null}">
																				   <div id="signatureHolder" class="kt-avatar__holder" recordHasPhoto="1" style="background-image: url('${pageContext.request.contextPath}/file/download?fileName=${candidateSession.photoAndSignature}&folderName=candidateSession.signature.folder')" urlFile="${pageContext.request.contextPath}/file/download?fileName=${candidateSession.photoAndSignature}&folderName=candidateSession.signature.folder"> </div>
																				</c:if> 
																				<c:if test="${(sourceEntity== 'CandidateSession' && candidateSession.photoAndSignature == null)||(sourceEntity!= 'CandidateSession')}">
																					<div id="signatureHolder" class="kt-avatar__holder" recordHasPhoto="0" style="background-image: url(assets/production/images/application-signature.jpg)"></div>
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
																		<input type="text" class="form-control"  id="surName" name="surName" placeholder="Please enter your Name ..." value="${person.surName}" readonly>
																	</div>
																</div>
																<div class="col-xl-3">
																	<div class="form-group">
																		<label style="font-weight: bold;">Prenom</label>
																		<input type="text" class="form-control" id="givenName" name="givenName" placeholder="Please enter your Given Name ..." value="${person.givenName}" readonly>
																	</div>
																</div>
																<div class="col-xl-3 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Place of Birth</label>
																		<input type="text" class="form-control" id="pob" name="pob" placeholder="Please enter your Place of birth ... "  value="${person.pob}" readonly>
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
																		<input type="text" class="form-control" id="phoneNumber" name="phoneNumber" pattern="(2|6)\d{8}$" placeholder="Please enter your Phone Number(Ex: 696764242) " value="${person.phoneNumber}" >
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
																		<select  class="form-control"  id="nationality" name="nationality"  >
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
														                      		<option value="FR" selected><fmt:message key="male.gender" /></option>
													                          </c:if>
													                          <c:if test="${person.language=='EN'}">
														                            <option value="EN" selected><fmt:message key="female.gender" /></option>
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
													</div>
												</div>

												<div id="step-1-action" class="kt-form__actions">
                                                	<button type="button" class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-right" data-ktwizard-type="action-next" onclick="validateStepOne();">Next </button>
                                                </div>

												<!--end: Form Wizard Step 1-->

												<!--begin: Form Wizard Step 2-->
												<div  id="step-2" class="kt-wizard-v1__content" data-ktwizard-type="step-content"  data-ktwizard-state="pending">
													<div class="kt-heading kt-heading--md" style="font-weight: bold;"><fmt:message key="process.record.registration.next.application.details" /></div>
													<div class="kt-form__section kt-form__section--first">
														<div class="kt-wizard-v1__form">
														<div class="row">							
																
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Slip Reference Number</label>
																	    <select  class="form-control"  id="slipRef" name="slipRef"  >
																			<option value="">Select an in slip  ...</option>
																			<c:forEach var="inSlip" items="${inSlips}">
											                                <option value="${inSlip.referenceNumber}" >${inSlip.referenceNumber}</option> 
										                                    </c:forEach>
																		</select>
																	</div>
																</div>
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Application Number</label>
																		<input type="text" class="form-control" id="applicationNumber" name="applicationNumber" placeholder="Please enter your application number ... "  pattern="[0-9]+" title="please only numbers / svp uniquement des nombres" >
																	</div>
																</div>
																
															</div>
															<div class="row">							
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">Authority:</label>
																		<select  class="form-control"  id="authority" name="authority"  >
																			<option value="">Select authority ...</option>
																			<c:forEach var="authority" items="${authorities}">
											                                <c:if test="${authority.status == 1 }" >
											                                <option value="${authority.id}" >${authority.position}</option> 
											                                </c:if>
										                                    </c:forEach>
																		</select>
																	</div>
																</div>
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">form serial number</label>
																		<input type="text" class="form-control" id="formNumber" name="formNumber" placeholder="Please enter your form Number ... "  pattern="[0-9]+" title="please only numbers / svp uniquement des nombres" >
																	</div>
																</div>
															
																
															</div>

														</div>
													</div>
												</div>
												<div id="step-2-action" class="kt-form__actions" style="display:none;">
												   <button id="prev-step-2" type="button" class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-left" data-ktwizard-type="action-prev" onclick="reopenStepOne();">Previous</button>

                                                  <button  type="button"  class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-right" data-ktwizard-type="action-next" onclick="validateStepTwo();">Next </i></button>
                                                </div>

												<!--end: Form Wizard Step 2-->

												<!--begin: Form Wizard Step 3-->
												<div id="step-3" class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="pending">
												    <div class="row" id="capacity-details">
												     <c:if test="${capacity != null}">
												     <c:if test="${capacity.capacityNumber != null}">
													 <div><label style="font-weight: bold; ">CAPACITY NUMBER : </label><label >${capacity.capacityNumber}</label></div><br>
													 </c:if>
													 <c:if test="${capacity.expiryDate != null}">
													 <fmt:formatDate value="${capacity.expiryDate}" pattern="dd/MM/yyyy" var="capacityExpiryDate" />
													 <div><label style="font-weight: bold;  ">EXPIRY DATE: </label><label style="color: red;  " >${capacityExpiryDate}</label></div>
													 </c:if>
													 </c:if>
													 <c:if test="${capacity == null}">
													 <div><label style="font-weight: bold; color:red; ">Capacity Not Yet Produced ! </label></div><br>
													 </c:if>
													<div class="kt-form__section kt-form__section--first">
														<div class="kt-wizard-v1__form">
														<div class="row">
														<c:if test="${sourceEntity== 'CandidateSession'}">
															<c:if test="${candidateSession.processed == 1}">					
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">OWNED CATEGORY:</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>${candidateSession.category.name}</td>
																			 <fmt:formatDate value="${candidateSession.eligibleCenter.examSession.sessionDate}" pattern="dd/MM/yyyy" var="candidateSessionExamDate" />
																			<td>${candidateSessionExamDate} </td>
																			<td>${candidateSession.eligibleCenter.examCenter.name}</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
																<div class="col-xl-6 ">
																    <!-- empty space for design purpose -->
																</div>
																</c:if>
																
																<c:if test="${candidateSession.processed == 0}">
																<div class="col-xl-6 ">
																    <!-- empty space for design purpose -->
																</div>
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">REQUESTED CATEGORY:</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>${candidateSession.category.name}</td>
																			 <fmt:formatDate value="${candidateSession.eligibleCenter.examSession.sessionDate}" pattern="dd/MM/yyyy" var="candidateSessionExamDate" />
																			<td>${candidateSessionExamDate} </td>
																			<td>${candidateSession.eligibleCenter.examCenter.name}</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
																</c:if>
																
																</c:if>
																
																<c:if test="${sourceEntity== 'Archive'}"> 				
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">OWNED CATEGORY ON ARCHIVE :</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>${archive.category.name}</td>
																			 <fmt:formatDate value="${archive.examDate}" pattern="dd/MM/yyyy" var="archiveExamDate" />
																			<td>${archiveExamDate} </td>
																			<td>${archive.examPlace}</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
															
																
																									
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">REQUESTED CATEGORY:</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>${archive.category.name}</td>
																			 <fmt:formatDate value="${archive.examDate}" pattern="dd/MM/yyyy" var="archiveExamDate" />
																			<td>${archiveExamDate} </td>
																			<td>${archive.examPlace}</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
																
																</c:if>
																
																<c:if test="${sourceEntity== 'Capacity'}"> 
																
																 <c:if test="${capacity.archive == null}">	
																	<c:if test="${capacity.candidateSession.processed == 1}">					
																		<div class="col-xl-6 ">
																			<div class="form-group">
																				<label style="font-weight: bold;">OWNED CATEGORY ON THE SYSTEM :</label>
																				<table class="table">
																				<thead class="thead-dark">
																				<tr>
																					<th>#</th>
																					<th>Category</th>
																					<th>Exam Date</th>
																					<th>Exam Place</th>
																				</tr>
																				</thead>
																				<tbody>
																				<tr>
																					<th scope="row">1</th>
																					<td>${capacity.candidateSession.candidateSession.category.name}</td>
																					 <fmt:formatDate value="${capacity.candidateSession.eligibleCenter.examSession.sessionDate}" pattern="dd/MM/yyyy" var="candidateSessionExamDate" />
																					<td>${candidateSessionExamDate} </td>
																					<td>${capacity.candidateSession.eligibleCenter.examCenter.name}</td>
																				</tr>
																	
																				</tbody>
																				</table>
																			</div>
																		</div>
																		
																</c:if>																						
																									
																<c:if test="${capacity.candidateSession.processed == 0}">					
																		<div class="col-xl-6 ">
																			<div class="form-group">
																				<label style="font-weight: bold;">REQUESTED CATEGORY:</label>
																				<table class="table">
																				<thead class="thead-dark">
																				<tr>
																					<th>#</th>
																					<th>Category</th>
																					<th>Exam Date</th>
																					<th>Exam Place</th>
																				</tr>
																				</thead>
																				<tbody>
																				<tr>
																					<th scope="row">1</th>
																					<td>${capacity.candidateSession.candidateSession.category.name}</td>
																					 <fmt:formatDate value="${capacity.candidateSession.eligibleCenter.examSession.sessionDate}" pattern="dd/MM/yyyy" var="candidateSessionExamDate" />
																					<td>${candidateSessionExamDate} </td>
																					<td>${capacity.candidateSession.eligibleCenter.examCenter.name}</td>
																				</tr>
																	
																				</tbody>
																				</table>
																			</div>
																		</div>
																		
																</c:if>
																</c:if>	
																<c:if test="${capacity.candidateSession == null}">	
																 	<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">OWNED CATEGORY ON ARCHIVE :</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>${capacity.archive.category.name}</td>
																			 <fmt:formatDate value="${capacity.archive.examDate}" pattern="dd/MM/yyyy" var="archiveExamDate" />
																			<td>${archiveExamDate} </td>
																			<td>${capacity.archive.examPlace}</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
															
																
																									
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">REQUESTED CATEGORY:</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>${capacity.archive.category.name}</td>
																			 <fmt:formatDate value="${capacity.archive.examDate}" pattern="dd/MM/yyyy" var="archiveExamDate" />
																			<td>${archiveExamDate} </td>
																			<td>${capacity.archive.examPlace}</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
																
																</c:if>
																</c:if>
															</div>
															
														</div>
													</div>
													</div>
												</div>

												<div id="step-3-action" class="kt-form__actions" style="display:none;">
												    <button type="button"  id="prev-step-3" class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-left" data-ktwizard-type="action-prev" onclick="reopenStepTwo();">
                                                                                                		Previous
                                                                                                	</button>
                                                    <button  type="button" class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-right" data-ktwizard-type="action-next" onclick="validateStepThree();">Next </i></button>
                                                 </div>

												<!--end: Form Wizard Step 3-->

												<!--begin: Form Wizard Step 4-->
												<div id="step-4" class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="pending" >
													<div class="kt-heading kt-heading--md">Attachments</div>
													<div class="kt-form__section kt-form__section--first">
													 <c:if test="${(sourceEntity== 'CandidateSession'&&(fn:length(candidateSession.candidateSessionFiles)== 0))||sourceEntity!= 'CandidateSession'}">
														<div id="filesContainer" recordHasFile="0" class="kt-wizard-v1__form">
													 </c:if >
													 <c:if test="${(sourceEntity== 'CandidateSession'&&(fn:length(candidateSession.candidateSessionFiles)>0))}">
													    <div id="filesContainer" recordHasFile="1" candidateSessionId="${candidateSession.id}" class="kt-wizard-v1__form">
													 </c:if >
															<c:if test="${(sourceEntity== 'CandidateSession' && ((fn:length(candidateSession.candidateSessionFiles) == 0)))|| sourceEntity != 'CandidateSession'}">
															<div class="row file upload-block-margins-application-row">
																<div class="col-xl-3 file upload-block-margins-application-block">
																	<div class="form-group">
																		<div></div>
																		<div class="custom-file">										
																		<div class="kt-avatar" id="kt_user_avatar_file">
																		    <c:if test="${fn:length(candidateSession.candidateSessionFiles)==1}">
																		    <a href="${pageContext.request.contextPath}/file/download?fileName=${file.fileName}&folderName=candidateSession.file.folder" target="_blank" >
																		    </c:if>
																			 <div id="fileHolder" class="kt-avatar__holder" style="background-image: url(assets/media/files/pdf.svg)"></div>
																			<c:if test="${fn:length(candidateSession.candidateSessionFiles)==1}">
																			</a>
																			</c:if>
																			<canvas id="pdf-canvas" width="200" height="200" class="pdf-canvas" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; display:none;"></canvas>
																			  <label id="changeFile" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar">
																				<i class="fa fa-pen"></i>
																				<input name="fileInput" id="fileInput" class="kt-avatar__input_file" type="file" name="profile_avatar" >
																			</label>
																			<span id="fileCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																				<i class="fa fa-times"></i>
																			</span>
																		</div>
																		<button id="button-file-plus" type="button" class="btn btn-brand btn-elevate btn-icon boutonFileAdd" onclick="autoConcatenateUIFilesForApplicationForm();"><i class="la la-plus"></i></button>
																		<button id="button-file-minus" type="button" class="btn btn-dark btn-icon boutonFileDelete" onclick="delete_file_application(this.value);" ><i class="la la-minus"></i></button>

													                </div>
																	</div>
																</div>
																
															</div>
													       </c:if>
													       <c:if test="${sourceEntity== 'CandidateSession'}">
													       <c:if test="${fn:length(candidateSession.candidateSessionFiles) >0}">
													       <c:set var = "i"  value = "0"/>
													       <c:set var = "printRow"  value = "1"/>
													       <c:set var = "numBlock"  value ="1"/>
													       <c:set var = "numRow"  value ="0"/>
													       <c:forEach items="${candidateSessionFiles}" var="file">
													       
													       <c:if test="${printRow == 1}">
															<div id="div-file-row-${numRow}" class="row file upload-block-margins-application-row">
														   </c:if>	
															  <c:if test="${numBlock < 4}">
																<div id="div-file-block-${i}" class="col-xl-3 file upload-block-margins-application-block">
																	<div class="form-group">
																		<div></div>
																		<div class="custom-file">										
																		<div class="kt-avatar" id="kt_user_avatar_file_${i}">
																			<a href="${pageContext.request.contextPath}/file/download?fileName=${file.fileName}&folderName=candidateSession.file.folder" target="_blank" ><div id="fileHolder-${i}" class="kt-avatar__holder" style="background-image: url(assets/media/files/pdf.svg)"></div></a>
																			<canvas id="pdf-canvas-${i}" width="200" height="200" class="pdf-canvas" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; display:none;"></canvas>
																			<!--<label id="changeFile-${i}" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar">
																				<i class="fa fa-pen"></i>
																				<input name="fileInput"  id="fileInput-${i}" class="kt-avatar__input_file" type="file" name="profile_avatar" >
																			</label>
																			<span id="fileCancel-${i}" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																				<i class="fa fa-times"></i>
																			</span>-->
																		</div>
																		<!--
																		<button id="button-file-plus-${i}" type="button" class="btn btn-brand btn-elevate btn-icon boutonFileAdd" onclick="autoConcatenateUIFilesForApplicationForm();"><i class="la la-plus"></i></button>
																		<button id="button-file-minus-${i}" type="button" class="btn btn-dark btn-icon boutonFileDelete" onclick="delete_candidate_file(this.value);" ><i class="la la-minus"></i></button>
                                                                        -->
													                </div>
																	</div>
																</div>
																<c:set var = "numBlock"  value = "${numBlock+1}"/>
																</c:if>
																<c:if test="${numBlock < 4}">
																<c:set var = "printRow"  value = "0"/>
															    </c:if>
															    <c:if test="${numBlock == 4}">
																<c:set var = "numBlock"  value = "1"/>
																<c:set var = "printRow"  value = "1"/>
																<c:set var = "numRow"  value ="${numRow+1}"/>
															    </c:if>
																
														    <c:if test="${printRow == 1}">	
															</div>
															</c:if>
															
															<c:set var = "i"  value = "${i+1}"/>
															
															</c:forEach>
															 </c:if>
													       </c:if>
															
														</div>
													


												<div id="step-4-action" class="kt-form__actions" style="display:none;">
												     <button type="button"  id="prev-step-4" class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-left" data-ktwizard-type="action-prev" onclick="reopenStepThree();">
                                                                                                  		Previous
                                                                                                  	</button>
                                                     <button  type="button" class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-right" data-ktwizard-type="action-next" onclick="validateStepFour();">Next </i></button>
                                                </div>

												<!--end: Form Wizard Step 4-->

												<!--begin: Form Wizard Step 5-->
												<div id="step-5" class="kt-wizard-v1__content" data-ktwizard-type="step-content"  data-ktwizard-state="pending">
													<div class="kt-heading kt-heading--md"><span style="color:black;">Review your Details and Submit</span></div>
													<div class="kt-form__section kt-form__section--first">
														<div class="kt-wizard-v1__review">
															<div class="kt-wizard-v1__review-item">
																<div class="row" >
														        <div class="col-xl-3 " style="margin-bottom:40px;" id="block-review-photo">
																	<div class="form-group">
																		<label class="col-xl-3 col-lg-3 col-form-label">photo</label>
													
																		<div class="custom-file col-lg-9 col-xl-6">										
																			<div class="kt-avatar kt-avatar--outline kt-avatar--circle" id="kt_user_avatar_photo">
																				<div id="photoHolder" class="kt-avatar__holder" style="background-image: url(assets/media/users/100_3.jpg)"></div>
																				<label id="changePhoto" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar" >
																					<i class="fa fa-pen"></i>
																					<input id="photoInput" type="file" name="profile_avatar" accept=".jpg">
																				</label>
																				<span id="photoCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																					<i class="fa fa-times"></i>
																				</span>
																	        </div>
																		</div>
																	</div>
																</div>
																<div class="col-xl-3 " style="margin-bottom:70px;" id="block-review-signature">
																	<div class="form-group">
																		<label class="col-xl-3 col-lg-3 col-form-label">Signature</label>
													
																		<div class="custom-file col-lg-9 col-xl-6">										
																			<div class="kt-avatar kt-avatar--outline kt-avatar--circle" id="kt_user_avatar_photo">
																				<div id="photoHolder" class="kt-avatar__holder" style="background-image: url(assets/media/users/100_3.jpg)"></div>
																				<label id="changePhoto" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar" >
																					<i class="fa fa-pen"></i>
																					<input id="photoInput" type="file" name="profile_avatar" accept=".jpg">
																				</label>
																				<span id="photoCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																					<i class="fa fa-times"></i>
																				</span>
																	        </div>
																		</div>
																	</div>
																</div>
															    <div class="col-xl-6">
																	<div class="form-group">
																	   <div class="kt-wizard-v1__review-title">
																	    <span style="color:black;"> Personal Informations </span>
																       </div>
																       <div class="kt-wizard-v1__review-content">
																	  	<label style="font-weight: bold;"> SURNAME  : </label><span id="enteredSurName"> MANDENGUE TEPONDJOU KELLINE </span> <br>
																	  	<label style="font-weight: bold;"> GIVEN NAME   :</label> <span id="enteredGivenName"> PAULE AGGY </span> <br>
																	  	<label style="font-weight: bold;"> DATE OF BIRTH   : </label> <span id="enteredDob"> 31/01/1990 </span> <br>
																	  	<label style="font-weight: bold;"> PLACE OF BIRTH   : </label> <span id="enteredPob"> YAOUNDE </span> <br>
																	  	<label style="font-weight: bold;"> NATIONALITY   : </label> <span id="enteredNationality"> CAMEROONIAN </span> <br>
																	    <label style="font-weight: bold;"> LANGUAGE   : </label> <span id="enteredLanguage"> FRENCH </span> <br>
																	    <label style="font-weight: bold;"> PHONE NUMBER  : </label> <span id="enteredPhoneNumber"> 691363621 </span> <br>
																	    <label style="font-weight: bold;"> GENDER  : </label> <span id="enteredGender"> FEMININ </span> <br>
																	    
																       </div>
																		
																	</div>
																</div>
																
															</div>
															</div>
															<div class="kt-wizard-v1__review-item">
																<div class="kt-wizard-v1__review-title">
																	<span style="color:black;">Application Details</span>
																</div>
																<div class="row">
															    <div class="col-xl-6">
																	<div class="form-group">
																       <div class="kt-wizard-v1__review-content">
																	  	<label style="font-weight: bold;"> PROCESS TYPE  : </label><span id="enteredProcessType"> NEW NATIONAL CAPACITY </span> <br>
																	  	<label style="font-weight: bold;"> SLIP REFERENCE NUMBER   :</label> <span id="enteredSlipNumber"> LT-4125-2 </span> <br>
																	  	<label style="font-weight: bold;"> APPLICATION NUMBER   : </label> <span id="enteredApplicationNumber"> 1 </span> <br>
																       </div>	
																	</div>
																</div>
																  <div class="col-xl-6">
																	<div class="form-group">
																       <div class="kt-wizard-v1__review-content">
																	  	<label style="font-weight: bold;"> AUTHORITY  : </label><span id="enteredAuthority"> DELEGEUE REGIONAL DU LITTORAL </span> <br>
																	  	<label style="font-weight: bold;"> FORM SERIAL NUMBER   :</label> <span id="enteredFormSerialNumber"> 75894 </span> <br>
																       </div>
																	</div>
																</div>
															</div>
															</div>
															<div class="kt-wizard-v1__review-item">
																<div class="kt-wizard-v1__review-title">
																	<span style="color:black;">Capacity Details</span>
																</div>
																<div class="row" id="capacity-details-review">
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">OWNED CATEGORY:</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>CAT LOWER THAN 75 KG </td>
																			<td>14/01/2019</td>
																			<td>Douala</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
																
																<div class="col-xl-6 ">
																	<div class="form-group">
																		<label style="font-weight: bold;">REQUESTED CATEGORY:</label>
																		<table class="table">
																		<thead class="thead-dark">
																		<tr>
																			<th>#</th>
																			<th>Category</th>
																			<th>Exam Date</th>
																			<th>Exam Place</th>
																		</tr>
																		</thead>
																		<tbody>
																		<tr>
																			<th scope="row">1</th>
																			<td>CAT LOWER THAN 75 KG </td>
																			<td>14/01/2019</td>
																			<td>Douala</td>
																		</tr>
															
																		</tbody>
																		</table>
																	</div>
																</div>
																
																
															</div>
															</div>
															<div class="kt-wizard-v1__review-item">
																<div class="kt-wizard-v1__review-title">
																	<span style="color:black;">Attachments</span>
																</div>
																<div class="kt-form__section kt-form__section--first">
																	<div id="filesContainer-preview" class="kt-wizard-v1__form">

																	</div>
													           </div>
																
															</div>
														</div>
														
													
													</div>
													
												</div>
                                                <div id="step-5-action" class="kt-form__actions" style="display:none;">
												    <button type="button"  id="prev-step-5" class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-left" data-ktwizard-type="action-prev" onclick="reopenStepFour();">
                                                		Previous
                                                	</button>
												    <button type="button"  id="submit-application" class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u pull-right" data-ktwizard-type="action-submit" onclick="registerApplication(0,'${processTypeId}','${sourceId}','${sourceEntity}');">
														Submit
													</button>                                                
												    </div>
												

												<!--end: Form Wizard Step 5-->

												<!--begin: Form Actions -->
												<div class="kt-form__actions" style="display:none;">
													
													<button  class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-submit">
														Submit
													</button>
													<button id="button-file-plus" type="button" class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u " data-ktwizard-type="action-next" onclick="validateStep();">Next</i></button>
													<!-- <div class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-next" onclick="validateStep();">
														Next Step
													</div> -->
												</div>

												<!--end: Form Actions -->
											</form>

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