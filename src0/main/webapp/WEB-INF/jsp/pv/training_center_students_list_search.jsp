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
					<style="color:white" tr role="row">
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">NO</th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 80.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.surname" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="student.givenName" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 188.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="student.speciality" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 250.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="student.matricule" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 160.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.computerizationDate" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>
					</tr>
				  </thead>
				  <tbody>
				             <c:if test="${listType == 'Student'}">
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="student" items="${listToDisplay}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd" id="${student.id}">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${student.person.surName}</td>
                                        <td>${student.person.givenName}</td>
                                        <td>${student.speciality.abbreviation}</td>
                                        <td>${student.referenceNum}</td>
                                        <fmt:formatDate value="${student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${student.id}','student');return false;" class="form-control input-sm">
                                                 <%@include file="training_center_action_student.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even" id="${student.id}">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${student.person.surName}</td>
                                        <td>${student.person.givenName}</td>
                                        <td>${student.speciality.abbreviation}</td>
                                        <td>${student.referenceNum}</td>
                                        <fmt:formatDate value="${student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${student.id}','student');return false;" class="form-control input-sm">
                                                 <%@include file="training_center_action_student.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            </c:if>
                            <c:if test="${listType == 'StudentSession'}">
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="candidate" items="${listToDisplay}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd" id="${candidate.student.id}">                                   <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${candidate.student.person.surName}</td>
                                        <td>${candidate.student.person.givenName}</td>
                                        <td>${candidate.student.speciality.abbreviation}</td>
                                        <td>${candidate.student.referenceNum}</td>
                                        <fmt:formatDate value="${candidate.student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${candidate.id}','studentSession');return false;" class="form-control input-sm">
                                                 <%@include file="training_view_action_candidate.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even" id="${candidate.student.id}">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${candidate.student.person.surName}</td>
                                        <td>${candidate.student.person.givenName}</td>
                                        <td>${candidate.student.speciality.abbreviation}</td>
                                        <td>${candidate.student.referenceNum}</td>
                                        <fmt:formatDate value="${candidate.student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${candidate.id}','studentSession');return false;" class="form-control input-sm">
                                                 <%@include file="training_view_action_candidate.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            </c:if>
                  </tbody>
			    </table>
                <!--end: Datatable -->
    		</div>
    	</div>

</div>
<!-- end:: Content -->


   
