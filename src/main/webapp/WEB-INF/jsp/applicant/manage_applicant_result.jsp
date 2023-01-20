<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script> --%>
<link href="${pageContext.request.contextPath}/css/candidate.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/pv/candidateSession.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/applicant/applicant.js" type="text/javascript"></script>



<div class="kt-subheader-search ">
	<div class="kt-container  kt-container--fluid ">
		<h6 class="kt-subheader-search__title">
			<fmt:message key="manage.applicant.pv" />: ${fn:toUpperCase(eligibleCenter.entranceExamCenter.name)}
		</h6>
	</div>
</div>



<!-- begin:: Content -->
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
	<div class="row">
		<div class="col-lg-12">
		<input id="entranceEligibleCenter" type="hidden" value="${entranceEligibleCenter.id}">

                    <!--end::Portlet-->
                    <c:if test = "${entranceEligibleCenter.entranceEligibleCenterStatus.id==2}">
            			<div  class="kt-portlet__body">
            				<div id="applicant-view">
            					<%@include file="create_applicant.jsp" %>
            				</div>
            			</div>
            		</c:if>	
            
            	<c:if test = "${entranceEligibleCenter.entranceEligibleCenterStatus.id==4}">
                     <div class="col-md-2" style="float:left;">
							<button type="button" class="btn btn-primary" id="order_applicant" onclick="orderApplicant();" >
				  				<fmt:message key="assign.trainin.center.to.applicants" />
				  			</button>
			        </div>
			     </c:if>
            	
							
			<div  class="kt-portlet__head">  <!-- status -->
				<div class="kt-portlet__head-toolbar">
					
					<ul class="nav nav-tabs nav-tabs-line nav-tabs-line-info nav-tabs-line-2x nav-tabs-line-right nav-tabs-bold" role="tablist">
						
						<c:if test = "${entranceEligibleCenter.entranceEligibleCenterStatus.id != 4}">
							<li  class="nav-item">
								<a onclick="loadApplicant('${pageContext.request.contextPath}/applicant/load' , 'REGISTERED');" class="nav-link active" data-toggle="tab" href="#applicant-list" role="tab">
									<i class="fa  fa-school " aria-hidden="true"></i><fmt:message key="applicant.exam.result.registered" />
	
								</a>
							</li>
							<li onclick="loadApplicant('${pageContext.request.contextPath}/applicant/load' , 'FAILED THEORY');" class="nav-item">
								<a class="nav-link" data-toggle="tab" href="#applicant-list" role="tab">
									<i class="fa fa-window-close" aria-hidden="true"></i><fmt:message key="applicant.exam.result.failed.theory" />
	
								</a>
							</li>
							<li onclick="loadApplicant('${pageContext.request.contextPath}/applicant/load' , 'PASSED THEORY');" class="nav-item">
								<a  class="nav-link" data-toggle="tab" href="#applicant-list" role="tab">
									<i class="fa  fa-check " aria-hidden="true"></i><fmt:message key="applicant.exam.result.succeeded.theory" />
	
								</a>
							</li>
							<li onclick="loadApplicant('${pageContext.request.contextPath}/applicant/load' , 'FAILED PRACTICALS');" class="nav-item">
								<a class="nav-link" data-toggle="tab" href="#applicant-list" role="tab">
									<i class="fa fa-window-close" aria-hidden="true"></i><fmt:message key="applicant.exam.result.failed.practicals" />
	
								</a>
							</li>
						</c:if>	
						<li onclick="loadApplicant('${pageContext.request.contextPath}/applicant/load' , 'PASSED PRACTICALS');" class="nav-item">
							<a  class="nav-link" data-toggle="tab" href="#applicant-list" role="tab">
								<i class="fa  fa-check-double " aria-hidden="true"></i><fmt:message key="applicant.exam.result.succeeded.practicals" />

							</a>
						</li>
						<!-- <li onclick="loadApplicant('${pageContext.request.contextPath}/applicant/load' , 'ADVANCED TO TRAINING');" class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#applicant-list" role="tab">
								<i class="fab fa-dribbble" aria-hidden="true"></i><fmt:message key="applicant.exam.result.advance.to.training" />

							</a>
						</li> -->

					</ul>
				</div>
			</div>

            <div id="applicant-list">

                <div  class="kt-portlet__body">
                      <div id="applicant-list">
                            <%@include file="list_applicant.jsp" %>
                       </div>
                </div>
            </div>
 	    </div>
    </div>
</div>					    
			    