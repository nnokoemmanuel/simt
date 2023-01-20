<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/js/production/manage_files.js" type="text/javascript"></script>


<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />
<%@ page import="java.util.Date"%>

<!-- begin:: Content -->
<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
   <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">
    		<div class="kt-portlet__body">
                <!--begin: Datatable -->
                <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1650px;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white"  role="row">
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">NO</th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 130.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="numero.dossier" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="app.in.slip.create.date" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 188.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="processType.application" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 250.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="app.person.all.name" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 160.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="application.status" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 60.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>
					</tr>
				  </thead>
				  <tbody>
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="application" items="${listApplication}">
    						    
                                    <tr id="appRow-${application.id }" role="row" class="<c:if test ="${i%2==1}">odd clear-odd</c:if><c:if test ="${i%2==0}">even dark-even</c:if> ">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${application.serialNumber}</td>
                                        <fmt:formatDate value="${application.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>

                                        <!-- start managing status -->
                                        <c:if test="${application.processType.id == 1 }">
                                        <td ><fmt:message key="processType.new.transcript" /></td>
                                        </c:if>
                                        <c:if test="${application.processType.id == 2 }">
                                        <td ><fmt:message key="processType.new.certificate" /></td>
                                        </c:if>
                                        <c:if test="${application.processType.id == 3 }">
                                        <td ><fmt:message key="processType.new.professional.card" /></td>
                                        </c:if>
                                        <c:if test="${application.processType.id == 4 }">
                                        <td ><fmt:message key="processType.duplicate.transcript" /></td>
                                        </c:if>
                                        <c:if test="${application.processType.id == 5 }">
	                                    <td ><fmt:message key="processType.duplicate.certificate" /></td >
	                                    </c:if>
	                                    <c:if test="${application.processType.id == 6 }">
	                                    <td ><fmt:message key="processType.duplicate.professional.card" /></td >
	                                    </c:if>
                                        <!-- end  managing status -->
										<td> 
                                        <c:if test="${application.certificate.studentSession == NULL }">
                                            ${application.archive.person.surName} ${application.archive.person.givenName}
                                        </c:if>
                                        <c:if test="${application.archive == NULL }">
                                        
                                        	
                                        		<c:if test="${application.transcript != 'NULL' }">
                                            		${application.transcript.studentSession.student.person.surName} ${application.transcript.studentSession.student.person.givenName}
												</c:if>
                                            		${application.certificate.studentSession.student.person.surName} ${application.certificate.studentSession.student.person.givenName}
                                        	
                                        	
                                        </c:if>
                                        </td>


                                        <!-- start managing status -->
                                        <c:if test="${application.applicationStatus.id == 1 }">
                                            <td ><fmt:message key="application.status.registered" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 2 }">
                                            <td ><fmt:message key="application.status.rejected" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 3 }">
                                            <td ><fmt:message key="application.status.cancelled" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 4 }">
                                            <td ><fmt:message key="application.status.confirmed" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 5 }">
                                            <td ><fmt:message key="application.status.capacity.printed" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 6 }">
                                            <td ><fmt:message key="application.status.successfully.printed" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 7 }">
                                            <td ><fmt:message key="application.status.confirmed.rejected" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 8 }">
                                            <td ><fmt:message key="application.status.unsuccessfully.printed" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 9 }">
                                            <td ><fmt:message key="application.status.delivered" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 10 }">
                                            <td ><fmt:message key="application.status.deliver.rejected" /></td>
                                        </c:if>
                                        <c:if test="${application.applicationStatus.id == 11 }">
                                            <td ><fmt:message key="application.status.authorized.reprint" /></td>
                                        </c:if>
                                        <!-- end  managing status -->
                                        <td >
                                        <c:if test="${application.transcript != NULL }">
                                            <select onchange="application_action(this.value,'${application.id}','application', '${application.processType.id}','${application.transcript.studentSession.id}','${application.inSlip.eligibleCenter.id}');return false;" class="form-control input-sm">

                                                 <%@include file="action.jsp" %>
                                            </select>
                                        </c:if>
                                        <c:if test="${application.certificate != NULL }">
                                            <select onchange="application_action(this.value,'${application.id}','application', '${application.processType.id}','${application.certificate.studentSession.id}','${application.inSlip.eligibleCenter.id}');return false;" class="form-control input-sm">

                                                 <%@include file="action.jsp" %>
                                            </select>
                                        </c:if>
                                        </td>
                                    </tr>
                           

                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                  </tbody>
			    </table>
                <!--end: Datatable -->
    		</div>
    	</div>

</div>
<!-- end:: Content -->


   
