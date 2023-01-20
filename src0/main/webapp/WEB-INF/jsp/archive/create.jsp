<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

						<div class="kt-subheader-search ">
							<div class="kt-container  kt-container--fluid ">
								<h6 class="kt-subheader-search__title">
									<fmt:message key="computerize.archive.capec" />
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
													<fmt:message key="person.informations" />
												</h3>
											</div>
										</div>

										<!--begin::Form-->
									    <form class="kt-form kt-form--label-right" id="add_archive_form" name="add_archive_form">
											<div class="kt-portlet__body" id="divBlock1">
												<div class="form-group row">
													<div class="col-lg-4">
														<label  style="color:#564FC1;"><fmt:message key="person.name" />*</label>
														<input name="surName" id="surName" type="text" class="form-control" placeholder="Enter name" required>
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="person.given.name" />*</label>
														<input name="givenName"  id="givenName" type="text" class="form-control" placeholder="Enter given name">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="person.gender" />*</label>
														<select name="gender" id="gender" class="form-control">
																			<option value="" >Select your gender...</option>
																			<option value="F" ><fmt:message key="female.gender" /></option>
																			<option value="M" ><fmt:message key="male.gender" /></option>
														</select>
													</div>
												</div>
												<div class="form-group row">
													
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="person.dob" />*</label>
														<input  name="dob" id="dob" type="text"  class="form-control date-picker" placeholder="dd/mm/yyyy" >
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="person.pob" />*</label>
														<input name="pob" id="pob" type="text" class="form-control" placeholder="Enter place of birth (Eg Yaounde )">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="person.nic" />*</label>
														<input name="nic" id="nic" type="text" class="form-control" placeholder="Enter your id card number">
													</div>
													
												</div>
												
																				
												<div class="form-group row">
													
													<div class="col-lg-4">
														<label  style="color:#564FC1;"><fmt:message key="person.licenseNum" />*</label>
														<input name="licenseNum" id="licenseNum" type="text" class="form-control" placeholder="Enter Licence Number" required>
													</div>
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
														<label style="color:#564FC1;"><fmt:message key="person.specialty" />*</label>
																<select name="speciality"  id="speciality" class="form-control">
																		<option value=""><fmt:message key="select.fill.person.speciality"/></option>
																		<option value="1">MAE</option>
																</select>
													</div>
													
												</div>
												<div class="kt-portlet__foot">
													<div class="kt-form__actions">
														<div class="row">
															<div class="col-lg-12 kt-align-right">
								        						<button class="btn btn-primary nextBtn btn-lg pull-right" type="button" onclick="veriffdivBlock1(); " id="buttonopen1">Next</button>
															</div>
														</div>
													</div>
												</div>
											</div>
											
											
										
									
									<!--end::Portlet-->

									<!--begin::Portlet-->
									<div class="kt-portlet" id="divBlock2" style="display:none;">
										<div class="kt-portlet__head">
											<div class="kt-portlet__head-label">
												<h3 class="kt-portlet__head-title" style="color:black;">
												  <fmt:message key="archive.category.informations" />
												</h3>
											</div>
										</div>

											<!--begin::Form-->
										
											<div class="kt-portlet__body">
												<div class="form-group row">
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedDate" />*</label>
														<input name="issuedDate" id="issuedDate" type="text" class="form-control date-picker" placeholder="dd/mm/yyyy">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedCountry" />*</label>
														<select  name="issuedCountry" id="issuedCountry" class="form-control">
																<option value=""><fmt:message key="select.fill.archive.issued.country"/></option>
																<c:forEach var="country" items="${countries}">
																<c:if test="${country.countryName == 'Cameroonian'}">
											                    <option selected value="${country.id }">${country.countryName}</option>
										                        </c:if>
										                        <c:if test="${country.countryName != 'Cameroonian'}">
											                    <option value="${country.id }">${country.countryName}</option>
										                        </c:if>
										                        </c:forEach>
														</select>
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedPlace" />*</label>
														<input name="issuedPlace" id="issuedPlace" type="text" class="form-control" placeholder="Enter your archive issued place">
													</div>
												</div>
												<div class="form-group row">							
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.examDate" />*</label>
														<input name="examDate" id="examDate" type="text" class="form-control date-picker" placeholder="dd/mm/yyyy">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.examPlace" />*</label>
														<input name="examPlace" id="examPlace" type="text" class="form-control" placeholder="Enter your archive exam place">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.pvNumber" />*</label>
														<input name="pvNumber" id="pvNumber" type="text" class="form-control" placeholder="Enter your archive Pv number" pattern="^[1-9][0-9]*$">
													</div>
													
												</div>
												<div class="form-group row">
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.service" />*</label>
														<select  name="archiveService" id="archiveService" class="form-control">
																<option value=""><fmt:message key="select.fill.archive.issued.service"/></option>
											                    <option value="MINFOPRA">MINFOPRA</option>
														</select>
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedAuthority" />*</label>
														<select  name="issuedAuthority" id="issuedAuthority" class="form-control">
																<option value=""><fmt:message key="select.fill.archive.issued.authority"/></option>
																<c:forEach var="authority" items="${authorities}">
											                    <option value="${authority.id }">${authority.position}</option>
										                        </c:forEach>
														</select>
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.grade" />*</label>
														<input name="grade" id="grade" type="text" class="form-control" placeholder="Enter your archive grade">
													</div>
												
												</div>
												<div class="form-group row">
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.number" />*</label>
														<input name="archiveNumber" id="archiveNumber" type="text" class="form-control" placeholder="Enter your archive archive number">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.finalAverage" /></label>
														<input name="finalAverage" id="finalAverage" type="text" class="form-control" placeholder="Enter your archive final average">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.personCountry" /></label>
														<select  name="personCountry" id="personCountry" class="form-control">
																<option value=""><fmt:message key="select.fill.archive.person.country"/></option>
																<c:forEach var="country" items="${countries}">
											                   <c:if test="${country.countryName == 'Cameroonian'}">
											                    <option selected value="${country.id }">${country.countryName}</option>
										                        </c:if>
										                        <c:if test="${country.countryName != 'Cameroonian'}">
											                    <option value="${country.id }">${country.countryName}</option>
										                        </c:if>
										                        </c:forEach>
														</select>
													</div>
												
												</div>
											
											</div>
										
										

									</div>

									<!--end::Portlet-->

									<!--begin::Portlet-->
									<div class="kt-portlet" id="divBlock3" style="display:none;">
									   <div class="kt-portlet__head">
											<div class="kt-portlet__head-label">
												<h3 class="kt-portlet__head-title" style="color:black;">
													<fmt:message key="capec.archive.file" /><span style="color:red;">(*)</span>
												</h3>
											</div>
										</div>
										
										<!--UPLOAD IMAGE/PHOTOS -->
						                <div class="col-lg-4">
						                
						                    <br>
						                <!-- IMAGE PREVIEW -->
						                    <canvas id="file-canvas" width="200" height="200" class="file-canvas" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; display:none;"></canvas>
						                    <br>
						                <!-- IMAGE INPUT -->
						                    <input  id="capec" name="capec" type="file" accept="application/pdf" required  onchange="showFirstPDF(window.URL.createObjectURL(this.files[0]),'file-canvas');" >
						                </div>
													
										<div class="kt-portlet__head">
											<div class="kt-portlet__head-label">
												<h3 class="kt-portlet__head-title" style="color:black;">
													<fmt:message key="attachments.details" />
												</h3>
											</div>
										</div>

										<!--begin::Form-->
									
											<div id="filesContainer" class="kt-portlet__body">
												<div class="form-group row file">
												   <!-- start photo  upload
												   <div class="col-lg-4">
													<label><fmt:message key="add.file.label" /></label>
													<div></div>
													<div class="custom-file">										
														<div class="kt-avatar kt-avatar--outline kt-avatar--circle" id="kt_user_avatar_photo">
															<div id="photoHolder" class="kt-avatar__holder" style="background-image: url(assets/media/users/100_3.jpg)"></div>
															<label id="changePhoto" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar" >
																<i class="fa fa-pen"></i>
																<input id="photoInput" type="file" name="profile_avatar" accept=".png, .jpg, .jpeg">
															</label>
															<span id="photoCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																<i class="fa fa-times"></i>
															</span>
												        </div>
													</div>
													</div>
													end photo  upload-->
													<div class="col-lg-4 upload-block-margins file">
													<label><fmt:message key="add.file.label" /></label>
													<div></div>
													<div class="custom-file">										
														<div class="kt-avatar" id="kt_user_avatar_file">
															<div id="fileHolder" class="kt-avatar__holder" style="background-image: url(assets/media/files/pdf.svg)"></div>
															<canvas id="pdf-canvas" width="200" height="200" class="pdf-canvas" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; display:none;"></canvas>
															<label id="changeFile" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar">
																<i class="fa fa-pen"></i>
																<input name="fileInput" id="fileInput" class="kt-avatar__input_file" type="file" name="profile_avatar" accept=".pdf">
															</label>
															<span id="fileCancel1" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
																<i class="fa fa-times"></i>
															</span>
														</div>
													<button id="button-file-plus" type="button" class="btn btn-brand btn-elevate btn-icon boutonFileAdd" onclick="autoConcatenateUIFiles();"><i class="la la-plus"></i></button>
													<button id="button-file-minus" type="button" class="btn btn-dark btn-icon boutonFileDelete" onclick="delete_file(this.value);" ><i class="la la-minus"></i></button>

													</div>
													</div>
									
												</div>
											
												<br/><br/><br/>
												<div class="kt-separator kt-separator--border-dashed kt-separator--space-lg kt-separator--portlet-fit"></div>
											   
										
											</div>
											<div class="kt-portlet__foot">
												<div class="kt-form__actions">
													<div class="row">
														<div class="col-lg-5"></div>
														<div class="col-lg-7">
															<button type="reset" class="btn btn-brand"  id="create_archive" onclick="registerArchive(0);" ><fmt:message key="archive.create.submit.button" /></button>
															<button type="reset" class="btn btn-secondary" onclick="refreshForm(0);"><fmt:message key="archive.create.cancel.button" /></button>
														</div>
													</div>
												</div>
											</div>
										
                                         
										<!--end::Form-->
									</div>
                                 </form>
									<!--end::Portlet-->
								</div>
							</div>
						</div>

						<!-- end:: Content -->
						<script>
						datePickersInitializer();
						pushAvatarsEvents("changeFile","fileInput","fileHolder","kt_user_avatar_file","fileCancel");
						
						</script>
						
