<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/js/inslip/list_inslip.js" type="text/javascript"></script>


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
    		    <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1644px;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white"  role="row">

					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">NO</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 120.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="app.in.slip.reference.number" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="app.in.slip.create.date" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 188.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="app.in.slip.status" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 146.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="app.in.slip.total.files" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 224.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="app.in.slip.office" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>
				           <c:if test="${user.group.id == 2 }">
                            <c:set var = "i"  value = "1"/>
    						<c:forEach var="inSlip" items="${inslipsByOffice}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${inSlip.referenceNumber}</td>
                                        <fmt:formatDate value="${inSlip.creationDate}" pattern="dd/MM/yyyy hh.mm,s" var="creationDate" />
                                        <td>${creationDate}</td>

                                        <!-- start managing status -->
                                        <c:if test="${inSlip.inSlipStatus.id == 1 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.registered" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 2 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.opened" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 3 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.closed" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 4 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.ok" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 5 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.bad" /></td>
                                        </c:if>
                                        <!-- end  managing status -->

                                        <td>${inSlip.total}</td>
                                        <td>${inSlip.office.name}</td>
                                        <td >
                                            <select onchange="inSlip_action(this.value,'${inSlip.id}','inSlip');return false;" class="form-control input-sm">
                                                 <%@include file="action.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${inSlip.referenceNumber}</td>
                                        <fmt:formatDate value="${inSlip.creationDate}" pattern="dd/MM/yyyy hh.mm,s" var="creationDate" />
                                        <td>${creationDate}</td>

                                        <!-- start managing status -->
                                        <c:if test="${inSlip.inSlipStatus.id == 1 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.registered" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 2 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.opened" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 3 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.closed" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 4 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.ok" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 5 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.bad" /></td>
                                        </c:if>
                                        <!-- end  managing status -->

                                        <td>${inSlip.total}</td>
                                        <td>${inSlip.office.name}</td>
                                        <td >

                                            <select onchange="inSlip_action(this.value,'${inSlip.id}','inSlip');return false;" class="form-control input-sm">
                                                 <%@include file="action.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                          </c:if>  
                          
                  <c:if test="${user.group.id != 2 }">
                            <c:set var = "i"  value = "1"/>
    						<c:forEach var="inSlip" items="${inSlips}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${inSlip.referenceNumber}</td>
                                        <fmt:formatDate value="${inSlip.creationDate}" pattern="dd/MM/yyyy hh.mm,s" var="creationDate" />
                                        <td>${creationDate}</td>

                                        <!-- start managing status -->
                                        <c:if test="${inSlip.inSlipStatus.id == 1 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.registered" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 2 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.opened" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 3 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.closed" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 4 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.ok" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 5 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.bad" /></td>
                                        </c:if>
                                        <!-- end  managing status -->

                                        <td>${inSlip.total}</td>
                                        <td>${inSlip.office.name}</td>
                                        <td >
                                            <select onchange="inSlip_action(this.value,'${inSlip.id}','inSlip');return false;" class="form-control input-sm">
                                                 <%@include file="action.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${inSlip.referenceNumber}</td>
                                        <fmt:formatDate value="${inSlip.creationDate}" pattern="dd/MM/yyyy hh.mm,s" var="creationDate" />
                                        <td>${creationDate}</td>

                                        <!-- start managing status -->
                                        <c:if test="${inSlip.inSlipStatus.id == 1 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.registered" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 2 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.opened" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 3 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.closed" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 4 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.ok" /></td>
                                        </c:if>
                                        <c:if test="${inSlip.inSlipStatus.id == 5 }">
                                        <td id='inSlip-status-${inSlip.id}' ><fmt:message key="app.in.slip.status.controlled.bad" /></td>
                                        </c:if>
                                        <!-- end  managing status -->

                                        <td>${inSlip.total}</td>
                                        <td>${inSlip.office.name}</td>
                                        <td >

                                            <select onchange="inSlip_action(this.value,'${inSlip.id}','inSlip');return false;" class="form-control input-sm">
                                                 <%@include file="action.jsp" %>
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


   
