<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 4 & Angular 8
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Dribbble: www.dribbble.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
Renew Support: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<html lang="en">

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
										<fmt:message key="new.group" />
									</h3>
									<span class="kt-subheader__separator kt-subheader__separator--v"></span>
									<div class="kt-subheader__group" id="kt_subheader_search">
										<span class="kt-subheader__desc" id="kt_subheader_total">
											<fmt:message key="new.group.title" /> </span>
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


													<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" id="step-1">
														<div class="kt-heading kt-heading--md"><fmt:message key="new.group.title.form" /></div>
														<div class="kt-section kt-section--first">
															<div class="kt-wizard-v4__form">
																<div class="row">
																	<div class="col-xl-12">
																		<div class="kt-section__body">
																			<div class="form-group row">
																				<label class="col-xl-3 col-lg-3 col-form-label"><fmt:message key="new.group.name" />*</label>
																				<div class="col-lg-9 col-xl-9">
																					<input class="form-control" id="group_name" type="text" value="${group.name}" placeholder="">
																					<input type="hidden" value="${group.id}">
																				</div>
																			</div>
                                                                            <div class="kt-heading kt-heading--md"><fmt:message key="new.group.role" /></div>
                                                                            <div class="kt-form__section kt-form__section--first">
                                                                                <div class="kt-wizard-v4__form row">
                                                                                    <c:forEach var="role" items="${roles}">
                                                                                    	<div class="col-sm-4">
                                                                                    		<p><input type="checkbox" id="role" name="role" value="${role.id}" 
                                                                                    
                                                                                    			<c:forEach var="groupRole" items="${groupRoleIds}">
                                                                                    				<c:choose> 
                                                                                        				<c:when test="${groupRole eq role.id }"> 
                                                                                   							checked
                                                                                        				</c:when>
                                                                                            			<c:otherwise> 
												
                                                                                            			</c:otherwise> 
																									</c:choose> 
                                                                                    			</c:forEach>
                                                                                   
                                                                                   			/><fmt:message key="${role.name}" /> <i class="fa fa-fw fa-sign-out"></i></p>
                                                                                    	</div>
                                                                                    </c:forEach>
                                                                            	</div> 
                                                                            </div>
                                                                      	</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
														
												</form>
											</div>

													<!--begin: Form Actions -->
													<div class="kt-form__actions">
														<button type="reset" class="btn btn-secondary" >Cancel</button>
                                                        <button type="button" class="btn btn-success" id="create_addusergroup" onclick="editUserGroup(${group.id});" >Submit</button>
													</div>

													<!--end: Form Actions -->
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

		<!-- end::Global Config -->

		<!--begin::Global Theme Bundle(used by all pages) -->
		<script src="${pageContext.request.contextPath}/assets/plugins/global/plugins.bundle.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/js/scripts.bundle.js" type="text/javascript"></script>

		<!--end::Global Theme Bundle -->

		<!--begin::Page Scripts(used by this page) -->
		<script src="${pageContext.request.contextPath}/assets/js/pages/custom/user/add-user.js" type="text/javascript"></script>
				<script src="${pageContext.request.contextPath}/assets/js/pages/custom/user/add-group.js" type="text/javascript"></script>

		<!--end::Page Scripts -->
	</body>

	<!-- end::Body -->
</html>



