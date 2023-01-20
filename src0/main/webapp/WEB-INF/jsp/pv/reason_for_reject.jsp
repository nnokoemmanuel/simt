<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="${pageContext.request.contextPath}/css/user.css" rel="stylesheet" type="text/css" />


<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper" style="padding-top:0px; padding-left:0px;">
	<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">

		

		<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
			<div class="kt-wizard-v4"  data-ktwizard-state="step-first">
				<div class="kt-portlet">
					
						
							<div class="kt-grid__item kt-grid__item--fluid kt-wizard-v4__wrapper">
								<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" style="padding-left:13%; padding-top:5%; padding-bottom:5%; padding-right:5%;">
									
						
									<div class="kt-section__body form-horizontal">
                                       <div style="margin:5px,5px" >
                                           <div class="row" style="margin:10px">

                                                           
													<!--begin: Form-body -->
														<!--	<div class="col-lg-4 col-md-9 col-sm-12"  method="post"> -->
															
															    <form action="" method="post">

															              <input type="hidden" id="studentSessionId" value="${studentSessionId}"> 
															    
															    
																					<div class="form-group row required" >
																						<label for="reject" class=" col-sm-9 control-label">
																						Reason For Reject | Raison Pour Rejet *</label>
																                            <div class="">
																                           	<textarea
																                                       
																                     			        id="reason_for_reject"
																                     			        name="reasonForReject" 
																                     			        type="textarea"
																                     			        placeholder="eg: Reason For Reject" 
																                     			        class="form-control" 
																                     			        value="${studentSessionjsp.reasonForReject}" 
																                     			        required>
																							</textarea>
																							
																							</div>
																					</div>
																					
															            <input  type="submit" class="btn btn-brand btn-elevate btn-icon-sm"  class="btn btn-primary"  title="submit reason for reject"
															            onclick="testRequirement_persist_ReasonForReject();approveReasonForReject();manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSessionId}', 3, 'rejected');"/>  
															            
												                       
														                       
																</form>
																 
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
		<script src="${pageContext.request.contextPath}/js/pv/candidateSession.js" type="text/javascript"></script>
		<!--end::Page Scripts -->	
		
			
			<script type="text/javascript">
			
					$(document).ready(function() {
						//alert("textarea");
					    $('input[type="submit"]').attr('disabled', true);
					    
					    //$('input[type="text"],textarea').on('keyup',function() {
					$('textarea').on('keyup',function() {
					        var textarea_value = $("#reason_for_reject").val();
					        //var text_value = $('input[name="textField"]').val();
					        
					        //if(textarea_value != '' && text_value != '') {
					        if(textarea_value != '') {
					            $('input[type="submit"]').attr('disabled', false);
					        } else {
					            $('input[type="submit"]').attr('disabled', true);
					        }
					    });
					});
			    
			</script>			
				