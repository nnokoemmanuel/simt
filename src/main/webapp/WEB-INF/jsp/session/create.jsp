<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="${pageContext.request.contextPath}/css/user.css" rel="stylesheet" type="text/css" />


<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper" style="padding-top:0px; padding-left:0px;">
	<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">

		<!-- begin:: Content Head -->
		<div class="kt-subheader   kt-grid__item" id="kt_subheader">
			<div class="kt-container  kt-container--fluid ">
				<div class="kt-subheader__main">
					<h3 class="kt-subheader__title">
						Session Manager
					</h3>
					<span class="kt-subheader__separator kt-subheader__separator--v"></span>
					<div class="kt-subheader__group" id="kt_subheader_search">
						<span class="kt-subheader__desc" id="kt_subheader_total">
							Exam Session</span>
					</div>
				</div>
			</div>
		</div>

		<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
			<div class="kt-wizard-v4" id="kt_user_add_user" data-ktwizard-state="step-first">
				<div class="kt-portlet">
					<div class="kt-portlet__body kt-portlet__body--fit">
						<div class="kt-grid">
							<div class="kt-grid__item kt-grid__item--fluid kt-wizard-v4__wrapper">
								<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" style="padding-left:13%; padding-top:5%; padding-bottom:5%; padding-right:10%;">
									<div class="kt-heading kt-heading--md">Session Information</div><hr>
										<div class="kt-section kt-section--first">
											<div class="kt-wizard-v4__form">

													<div class="kt-section__body form-horizontal">
                                                      <div class=" col-xs-8 col-md-offset-2" style="margin:100px,5px" >
                                                        <div class="row" style="margin:40px">

                                                           
                                                            <div class="col-md-5 col-md-offset-1 pull-right">
                                                                  <table width="100%">
                                                                    <tbody>
                                                                        <tr>
                                                                            				
																			     <div class="kt-portlet__body">
																			
																						<!--begin: Form-body -->
																							<!--	<div class="col-lg-4 col-md-9 col-sm-12"  method="post"> -->
																								<div class="col-lg-4 col-md-9 col-sm-12"  >
																								    <form action="" method="post">
																										<div class="form-group row required" >
																											<label for="dob" class=" col-sm-3 control-label">
																											<span>Session Date*</span></label>
																					                            <div class="">
																					                     			 <input id="sessionDate" name="dob" type="date"  placeholder="eg: dd/mm/yyyy" class="form-control"  />
																												</div>
																										</div>
																											
																							            
																							            <a href="#" class="btn btn-brand btn-elevate btn-icon-sm" onclick="createExamSession();" class="btn btn-primary"
																					                      title="add session"> Add </a>
	<!-- <button type="button" class="btn btn-bold btn-label-brand btn-sm" data-toggle="modal" data-target="#kt_modal_1"> Launch Modal</button> -->
																									</form>		
																								</div>
																			     </div>
																		</tr>
                                                                    </tbody>
                                                                  </table>
                                                            </div>
                                                        </div>
												      </div>
												      <hr>
													</div>
											</div>
										</div>
									</div>
							    </div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- end:: Content -->
	</div>





<!-- end:: Content -->


        <!--begin::Global Theme Bundle(used by all pages) 
		<script src="${pageContext.request.contextPath}/assets/plugins/global/plugins.bundle.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/js/scripts.bundle.js" type="text/javascript"></script>
		end::Global Theme Bundle -->
		
        <!--begin::Page Scripts(used by this page) -->
		<script src="${pageContext.request.contextPath}/js/session.js" type="text/javascript"></script>
		<!--end::Page Scripts -->					
		