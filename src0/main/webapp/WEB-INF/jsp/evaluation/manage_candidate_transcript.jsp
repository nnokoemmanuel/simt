<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%-- <script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script> --%>
<link href="${pageContext.request.contextPath}/css/candidate.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/static/js/pv/candidateSession.js" type="text/javascript"></script>



<div class="kt-subheader-search ">
	<div class="kt-container  kt-container--fluid ">
		<h6 class="kt-subheader-search__title">
			<fmt:message key="manage.pv" />
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
							
							
					
						</h3>
					</div>
				</div>
				
				
				
				
				
				


 <!--end::Portlet-->

							
			<div  class="kt-portlet__head">  <!-- status -->
				<div class="kt-portlet__head-toolbar">
					<ul class="nav nav-tabs nav-tabs-line nav-tabs-line-info nav-tabs-line-2x nav-tabs-line-right nav-tabs-bold" role="tablist">
						<!--  <li  class="nav-item">
							<a onclick="loadCandidates('${pageContext.request.contextPath}/studentSession/load' , 1);" class="nav-link active" data-toggle="tab" href="#candidate-list" role="tab">
								<i class="fa  fa-ship " aria-hidden="true"></i><fmt:message key="student.exam.result.status.presented" /> 

							</a>
						</li> -->
						<!-- <li onclick="loadCandidates('${pageContext.request.contextPath}/studentSession/load' , 2);" class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#candidate-list" role="tab">
								<i class="fa fa-gitter" aria-hidden="true"></i><fmt:message key="student.exam.result.status.approved" /> 

							</a>
						</li>-->
						<!-- <li onclick="loadCandidates('${pageContext.request.contextPath}/studentSession/load',  3);" class="nav-item">
							<a  class="nav-link" data-toggle="tab" href="#candidate-list" role="tab">
								<i class="fa  fa-check " aria-hidden="true"></i><fmt:message key="student.exam.result.status.rejected" />

							</a>
						</li>-->
						<li onclick="loadCandidates('${pageContext.request.contextPath}/evaluation/load',  4);" class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#candidate-list" role="tab">
								<i class="fab fa-dribbble" aria-hidden="true"></i><fmt:message key="student.exam.result.status.succeed" />

							</a>
						</li>
						
						<li onclick="loadCandidates('${pageContext.request.contextPath}/evaluation/load',  5);" class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#candidate-list" role="tab">
								<i class="fab  fa-ship" aria-hidden="true"></i><fmt:message key="student.exam.result.status.failed" />

							</a>
						</li>
					</ul>
				</div>
			</div>	
			
				
				
       		



<input id="eligibleCenter" type="hidden" value="${eligibleCenter.id}">




<div id="candidate-list">

<!-- This value holds an indicator for the current list being displayed on the screen. Values change when each list is loaded -->
            <input id="list-indicator" type="hidden" value="1">
    		    <table id="table_result" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 100%;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white" role="row">

					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 24.25px;" aria-label="Company Name: activate to sort column ascending">No</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 224.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.trainingCenter" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 120.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.given.surname" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.given.name" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 40.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="student.dob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="student.pob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 10.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.pvNum" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.exam.result" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>
				            <c:set var = "i"  value = "1"/>
				             
    						<c:forEach var="studentSession" items="${students}">
    						    <tr id="candidate-${studentSession.id}" role="row"<c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >

                                        <td  tabindex="0" class="sorting_1">
                                            <c:out value = "${i}"/>
                                        </td>
                                        <td>${studentSession.student.trainingCenter.name}</td>
                                        <td>${studentSession.student.person.surName}</td>
                                        <td>${studentSession.student.person.givenName}</td>
                                        <td id='studentSession.student.person.id'> <fmt:formatDate value="${studentSession.student.person.dob}" type="date" pattern="dd-MM-yyyy" var="dob"/>${dob}</td>
                                        <td>${studentSession.student.person.pob}</td>
                                        <c:if test="${not empty studentSession.photoAndSignature}">
                                            <td style="background-color:#80ad71;">${studentSession.pvNumber}</td>
                                        </c:if>
                                        <c:if test="${empty studentSession.photoAndSignature}">
                                            <td>${studentSession.pvNumber}</td>
                                        </c:if>
                                        <td><!-- Status -->
                                        	<c:if test="${studentSession.studentSessionStatus.id == 1 }">
                                        		<fmt:message key="student.exam.result.status.presented" />
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 2 }">
                                        		<fmt:message key="student.exam.result.status.approved" />
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 3 }">
                                        		<fmt:message key="student.exam.result.status.rejected" />
                                        		     <!--  
                                        		     <span style="color:red;font-weight:bold;">
												          <c:out value="${studentSession.reasonForReject}" />
												      </span> 
												      --> 
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 4 }">
                                        		<fmt:message key="student.exam.result.status.succeed" />
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 5 }">
                                        		<fmt:message key="student.exam.result.status.failed" />
                                        	</c:if>
                                        	
                                        </td>
                                        <td><!-- Action -->
                                            <select id="candidate-select-action-${studentSession.id }"  class="form-control input-sm">
						                      	<option value="action">Action</option>
						                      
						                      	<%-- <c:if test="${studentSession.studentSessionStatus.id  == 5 || studentSession.studentSessionStatus.id == 4  }"> --%>
						                      	 
						                      	 <c:if test="${studentSession.studentSessionStatus.id == 4 }">
						                      	 
						                      	    <option id="approved" onClick="uploadStudentDocumentInSession('${studentSession.id}');"><fmt:message key="student.upload.document.key" /></option>
                      	 							<sec:authorize access="hasRole('ROLE_MANAGE_CANDIDATES_TRANSCRIPT')">
	                      	 							  <c:if test="${studentSession.eligibleCenter.eligibleCenterStatus.id == 3 && studentSession.studentSessionStatus.id == 4}">
	                        								<option id="enter-notes" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', '${studentSession.studentSessionStatus.id}', 'enter-notes');"><fmt:message key="computerize.candidate.notes" /></option>	
		                        						    <option id="enter-final-note" onClick="saveFinalNote('${pageContext.request.contextPath}','${studentSession.id}');active_link('student-management');return false;"><fmt:message key="computerize.candidate.final.note" /></option>
		                        						  
		                        						  </c:if>
	                        						 </sec:authorize> 
	                        						<%--   <sec:authorize access="hasRole('ROLE_PRINT_TRANSCRIPT')">
	                        							<option  onCLick="printTranscriptPreview('${pageContext.request.contextPath}','${studentSession.id}');active_link('student-management');return false;"><fmt:message key="print.transcript" /></option>
		                        						    <c:if test="${studentSession.eligibleCenter.eligibleCenterStatus.id == 4 && studentSession.studentSessionStatus.id == 4}">
			                        						    <option onClick="printCertificatePreview('${pageContext.request.contextPath}','${studentSession.id}');active_link('student-management');return false;"><fmt:message key="print.certificate" /></option>
			                        						    <c:if test="${not empty studentSession.photoAndSignature}">
			                        						        <option onClick="printProfessionalCard( '${studentSession.id}','preview');active_link('student-management');return false;"><fmt:message key="print.professional.card" /></option>
	                                                            </c:if>
                             							   </c:if>
	                        						</sec:authorize>  --%>
                         						</c:if>
						                       
						
						                      	<option value="history" onClick="openNav('STUDENT SESSION HISTORY :: TRANSCRIPT TRACKING', '', '50%');printing_transcript_action(this.value,'${studentSession.id}','transcript');return false;"><fmt:message key="pv.action.history" /></option>
						                   
						                  	</select>
                                        </td>
                                    </tr>
                            	
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                           
                  </tbody>
			    </table>

         </div>
 	  </div>
    </div>
</div>	

<input type="hidden" id="studentSessionStatus" value="${studentSessionStatus}"> 
<script>
	addDataTable();
</script>				    
			    