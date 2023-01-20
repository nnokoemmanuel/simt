<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html >

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
																					<input id="group_name" type="text" value="" placeholder="" class="form-control " required>
																				</div>
																			</div>
                                                                            <div class="kt-heading kt-heading--md"><fmt:message key="new.group.role" /></div>
                                                                            <div class="kt-form__section kt-form__section--first">
                                                                                <div id ="divCheckbox" class="kt-wizard-v4__form">
                                                                                    <c:forEach var="role" items="${roles}">
                                                                                    <div style="display:inline-block;margin-bottom:5px;margin-left:5px">
                                                                                        <p><input type="checkbox" id="${role.id}" name="role" value="${role.id}"/>${role.name} <i class="fa fa-fw fa-sign-out pull-right"></i></p>
                                                                                            <c:set var="role" value="${role.name}" />
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
													<div class="kt-form__actions">
														<button type="reset" class="btn btn-secondary" >Cancel</button>
                                                        <button type="button" class="btn btn-success" onClick="createUserGroup()" >Submit</button>
													</div>
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
		<!--begin::Page Scripts(used by this page) -->
		<script src="${pageContext.request.contextPath}/assets/js/pages/custom/user/add-group.js" type="text/javascript"></script>
		<!--end::Page Scripts -->
	</body>
	<!-- end::Body -->
</html>



