<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="${pageContext.request.contextPath}/assets/js/pages/custom/user/add-user.js" type="text/javascript"></script>

<!DOCTYPE html>
<html>

	<!-- begin::Head -->
	<head>
		<base href="../../../">
		<meta charset="utf-8" />
		<title>Metronic | Add User</title>
		<meta name="description" content="Add user example">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	</head>

	<!-- end::Head -->

	<!-- begin::Body -->
	<body class="kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-aside--minimize kt-page--loading">

		<!-- begin:: Page -->

		
		<div class="kt-grid kt-grid--hor kt-grid--root" style="margin-top:0px; margin-left:0px;">
			<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--ver kt-page">

				<!-- begin:: Aside -->
				<button class="kt-aside-close " id="kt_aside_close_btn"><i class="la la-close"></i></button>

				<!-- end:: Aside -->
				<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper" style="padding-top:0px; padding-left:0px;">

					<!-- begin:: Header -->

					<!-- end:: Header -->
					<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">

						<!-- begin:: Content Head -->
						<div class="kt-subheader   kt-grid__item" id="kt_subheader">
							<div class="kt-container  kt-container--fluid ">
								<div class="kt-subheader__main">
									<h3 class="kt-subheader__title">
										<fmt:message key="new_user" />
									</h3>
									<span class="kt-subheader__separator kt-subheader__separator--v"></span>
									<div class="kt-subheader__group" id="kt_subheader_search">
										<span class="kt-subheader__desc" id="kt_subheader_total">
											Enter user details and submit </span>
									</div>
								</div>
							</div>
						</div>

						<!-- end:: Content Head -->

						<!-- begin:: Content -->
						<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
							<div class="kt-wizard-v4" id="kt_user_add_user" data-ktwizard-state="step-first">

								<!--begin: Form Wizard Nav -->
								<div class="kt-wizard-v4__nav">
									<div class="kt-wizard-v4__nav-items nav" style="display:none">

										<!--doc: Replace A tag with SPAN tag to disable the step link click -->
										
									
										<div class="kt-wizard-v4__nav-item nav-item" data-ktwizard-type="step">
											<div class="kt-wizard-v4__nav-body">
												<div class="kt-wizard-v4__nav-number">
													3
												</div>
												<div class="kt-wizard-v4__nav-label">
													<div class="kt-wizard-v4__nav-label-title">
														Address
													</div>
													<div class="kt-wizard-v4__nav-label-desc">
														User's Shipping Address
													</div>
												</div>
											</div>
										</div>
										<div class="kt-wizard-v4__nav-item nav-item" data-ktwizard-type="step">
											<div class="kt-wizard-v4__nav-body">
												<div class="kt-wizard-v4__nav-number">
													4
												</div>
												<div class="kt-wizard-v4__nav-label">
													<div class="kt-wizard-v4__nav-label-title">
														Submission
													</div>
													<div class="kt-wizard-v4__nav-label-desc">
														Review and Submit
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!--end: Form Wizard Nav -->
								<div class="kt-portlet">
									<div class="kt-portlet__body kt-portlet__body--fit">
										<div class="kt-grid">
											<div class="kt-grid__item kt-grid__item--fluid kt-wizard-v4__wrapper">

												<!--begin: Form Wizard Form-->
												<form class="kt-form" id="kt_user_add_form">
												<input id="userId" type="hidden" value="${userForm.id}"> 
												<input id="image" type="hidden" value="${userForm.image}">
												<input id="oldGroupId" type="hidden"  value="${userForm.group}"> 

							<!--begin: Form Wizard Step 1-->
													<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" id="step-1">
														<div class="kt-heading kt-heading--md"> <fmt:message key="user's_profile_details" /> :</div>
														<div class="kt-section kt-section--first">
															<div class="kt-wizard-v4__form">
																<div class="row">
																	<div class="col-xl-12">
																		<div class="kt-section__body">
																			<div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label">Avatar</label>
																				<div class="col-lg-9 col-xl-6">
																					<div class="kt-avatar kt-avatar--outline" id="kt_user_add_avatar">
																						<div class="kt-avatar__holder" style="background-image: url(${pageContext.request.contextPath}/file/download?fileName=${userForm.image}&folderName=user.profile.folder)">
<%-- 																							<c:if test="${not empty userForm.image}"> --%>
<%-- 																							<img src="${pageContext.request.contextPath}/file/download?fileName=${userForm.image}&folderName=user.profile.folder" style="max-width: 250px; max-height: 120px;" > --%>
<%-- 													                                       </c:if> --%>
<%-- 																							<c:if test="${empty userForm.image}"> --%>
<!-- 																							<img src="resources/static/resources/static/img/unknown.png" alt="profile pic holder"> -->
<%-- 																							</c:if> --%>
																						</div>
																						<label class="kt-avatar__upload" data-toggle="kt-tooltip" title="Change avatar">
																							<i class="fa fa-pen"></i>
																							
																							
																						<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 200px;"></div>
																							
																							
																							<input id="profile_pic" type="file" name="kt_user_add_user_avatar" accept="image/*" maxlength="256">
																						</label>
																						<span class="kt-avatar__cancel" data-toggle="kt-tooltip" title="Cancel avatar">
																							<i class="fa fa-times"></i>
																						</span>
																					</div>
																				</div>
																			</div>
																			
																			
																			<div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label"><fmt:message key="first_name" /> *</label>
																				<div class="col-lg-9 col-xl-9">
																					<input class="form-control " id="first_name" name="first_name" type="text" value="${userForm.firstName}" placeholder="eg: Nguesuh">
																				</div>
																			</div>
																			<div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label"><fmt:message key="last_name" /> *</label>
																				<div class="col-lg-9 col-xl-9">
																					<input class="form-control " id="last_name" name="last_name" type="text" value="${userForm.lastName}"  placeholder="eg: Stella">
																				</div>
																			</div>
																			<div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label"><fmt:message key="pob" /> *</label>
																				<div class="col-lg-9 col-xl-9">
																					<input class="form-control " id="pob" type="text" name="pob" value="${userForm.pob}" placeholder="eg: Santa">
																				</div>
																			</div>
																			<div class="form-group row required">
																				<label for="dob" class="  col-sm-3 control-label"><fmt:message key="dob" /> *</label>
										                                    	<div class="col-lg-9 col-xl-9">
										                                    	<fmt:formatDate value="${userForm.dob}" pattern = "dd/MM/yyyy" var="dob" />
											                       				<input id="dob" name="dob" type="text" value="${dob}"
																					class="form-control " placeholder="ex 02/02/1986"/>
																				</div>
																			</div>
																			<div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label"><fmt:message key="select_gender" /> *</label>
																				<div class="col-lg-9 col-xl-9">
																					<select class="form-control " id="gender" name="gender" readonly>
																						<option disabled selected><fmt:message key="select_gender" /></option>
																						<c:choose>
																							<c:when test="${userForm.gender=='M'}">
																							<option value="M" selected><fmt:message key="male" /></option>
																							<option value="F"><fmt:message key="female" /></option>
																							</c:when>
																							<c:when test="${userForm.gender=='F'}">
																							<option value="M"><fmt:message key="male" /></option>
																							<option value="F" selected><fmt:message key="female" /></option>
																							</c:when>
																						</c:choose>
																					</select>
																				</div>
																			</div>
	
																			<div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label" for="phone_number"><fmt:message key="phone_number" /> *</label>
																				<div class="col-lg-9 col-xl-9">
																					<div class="input-group">
																						<div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
																						<input type="text" id="phone_number" name="phone_number" pattern="(2|6)\d{8}$" class="form-control " value="${userForm.phoneNumber}" placeholder="eg: 697258741" aria-describedby="basic-addon1">
																					</div>
																				</div>
																			</div>
                                                                            <div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label"><fmt:message key="email_address" /></label>
																				<div class="col-lg-9 col-xl-9">
																					<div class="input-group">
																						<div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
																						<input type="text" id="email_address" name="email_address" pattern="[^@\s]+@[^@\s]+\.[^@\s]+" class="form-control email" value="${userForm.email}" placeholder="eg: kill@gmail.com" aria-describedby="basic-addon1">
																					</div>
																				</div>
																			</div>




							
																		
																			<div class="form-group row">
																				<label for="group" class="  col-sm-3 control-label"><fmt:message key="office" /> *</label>
																				<div class="col-sm-9">
																					<select id="office" name="office" class="form-control required" title="Select office">
																						<option value="${office.name}"><fmt:message key="select_office" /></option>
																								<c:forEach var="office" items="${offices }">
																								<c:if test="${office.id == userForm.office }">
																						<option value="${office.id}" selected>${office.name}</option>
																								</c:if>
																								<c:if test="${office.id != userForm.office }">
																						<option value="${office.id}">${office.name}</option>
																								</c:if>

																							</c:forEach>
																					</select>
																				</div>
																			</div>
                                                                            <div class="form-group row">
																				<label for="group" class="  col-sm-3 control-label"><fmt:message key="training_center" /> *</label>
																				<div class="col-sm-9">
																					<select id="trainingCenter" name="trainingCenter" class="form-control " title="Select office">
																						<option value="0"><fmt:message key="select_training_centers" /></option>
																								<c:forEach var="trainingCenter" items="${trainingCenters}">
																								<c:if test="${trainingCenter.id == userForm.trainingCenter }">
																						<option value="${trainingCenter.id}" selected>${trainingCenter.name}</option>
																								</c:if>
																								<c:if test="${trainingCenter.id != userForm.trainingCenter }">
																						<option value="${trainingCenter.id}">${trainingCenter.name}</option>
																								</c:if>

																							</c:forEach>
																					</select>
																				</div>
																			</div>
																		   <div class="form-group row">
																				<label for="group" class="  col-sm-3 control-label"><fmt:message key="group" />*</label>
																				<div class="col-sm-9">
																					<select id="group" name="group" class="form-control " title="Select Group">
												 										<option value="${group.name}" disabled selected><fmt:message key="select_group" /></option>
																							<c:forEach var="group" items="${groups}">
																							<c:if test="${group.id == userForm.group }">
																						<option value="${group.id}" selected>${group.name}</option>
																							</c:if>
																						<c:if test="${group.id != userForm.group }">
																						<option value="${group.id}">${group.name}</option>
																								</c:if>
																							</c:forEach>
																					</select>
																				</div>
																			</div>
																			<div class="form-group row">
																				<label for="activate" class="  col-sm-3 control-label">
																					<fmt:message key="active_user" /> *
																				</label>
																				<div class="col-sm-9">
																				 	<label for="activate"> <input id="activate"
																					name="activate" type="checkbox"
																					class="pos-rel p-l-30 custom-checkbox" value="1">
																					&nbsp;
																					</label>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
				
													<!--end: Form Wizard Step 1-->

													<!--begin: Form Wizard Step 2-->
													<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" id="step-2">
															
													      <div id="role-content">
													      </div>
															
													</div>	
							

													<!--end: Form Wizard Step 2-->

													<!--begin: Form Actions -->
													<div class="kt-form__actions">
														<div class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-prev">
															<fmt:message key="previous" />
														</div>
														<div class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u"  id="create_user" data-ktwizard-type="action-submit">
															<fmt:message key="submit" />
														</div>
														<div class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-next" onclick="return loadGroupRoleUpdate()" >
															<fmt:message key="next" />
														</div>
													</div>

													<!--end: Form Actions -->
												</form>

												<!--end: Form Wizard Form-->
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- end:: Content -->
					</div>

	
				</div>
			</div>
		</div>

		<!-- end:: Page -->

		



		
		<!-- begin::Global Config(global config for global JS sciprts) -->
		<script>
			var KTAppOptions = {
				"colors": {
					"state": {
						"brand": "#22b9ff",
						"light": "#ffffff",
						"dark": "#282a3c",
						"primary": "#5867dd",
						"success": "#34bfa3",
						"info": "#36a3f7",
						"warning": "#ffb822",
						"danger": "#fd3995"
					},
					"base": {
						"label": ["#c5cbe3", "#a1a8c3", "#3d4465", "#3e4466"],
						"shape": ["#f0f3ff", "#d9dffa", "#afb4d4", "#646c9a"]
					}
				}
			};
		</script>
<script>
	 $('#dob').datepicker({'format':'dd/mm/yyyy'});
</script>
	</body>

	<!-- end::Body -->
</html>