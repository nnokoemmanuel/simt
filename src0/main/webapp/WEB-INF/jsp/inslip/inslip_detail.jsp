<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<div class="kt-portlet kt-portlet--mobile">
	<div class="kt-portlet__head kt-portlet__head--lg">
		<div class="kt-portlet__head-label">


		</div>
		<div class="kt-portlet__head-toolbar">
			<div class="kt-portlet__head-wrapper">
				<div class="kt-portlet__head-actions">
			
				</div>
			</div>
		</div>
	</div>
    <div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	    <fmt:message key="app.title.in.slip.details" />
	</h3>
	<c:if test="${inSlip.inSlipStatus.id == 3 }">
	<sec:authorize access="hasRole('ROLE_INSLIP_CONTROLLER')">
	<span class="pull-right col-sm-8">
	      <a href="#" class="btn btn-brand btn-elevate btn-icon-sm"  onClick="openNav('In Slip Report', '', '40%');doGet('${pageContext.request.contextPath}/marine/inSlip/makeReport?id=${inSlip.id}','contenu')">
          <fmt:message key="app.title.make.report" /></a>         
 	</span>
 	</sec:authorize>
 	</c:if>
</div>


<!-- begin:: Content -->
<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
   <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">



    		<div class="kt-portlet__body">
										<div class="kt-section kt-section--first">
											<div class="kt-wizard-v4__form row">
											 	
											 	<div class="col-sm-4">
											 	<lable><fmt:message key="inslip.reference.number" />: </lable><span><h4>${inSlip.referenceNumber}</h4></span>
											 	<lable><fmt:message key="inslip.number" />: </lable><span><h4> ${inSlip.total} </h4></span>
											 	
											 	<lable><fmt:message key="inslip.not.found.files" />: </lable> <span><h4>${inSlip.notFound}</h4></span>											 												 	
											 	</div>
											 	
											 	<div class="col-sm-4">
											 	<lable><fmt:message key="inslip.status" />: </lable><span><h4>${inSlip.inSlipStatus.name} </h4></span>
											 	<lable><fmt:message key="inslip.date" />: </lable><span><h4><fmt:formatDate value="${inSlip.creationDate}" type="date" pattern="dd-MM-yyyy"/> </h4></span>
											 	<label><fmt:message key="inslip.not.found.files" />: </label>
										 <!-- <span><a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${inSlip.referenceNumber}unregistered.pdf&folderName=inslip.file.folder"><h4>Download PDF</h4></a></span> -->
												<span><a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${inSlip.id}.pdf&folderName=inslip.file.folder"><h4>Download PDF </h4></a></span> 
											 	</div>										 	
											 	
											 	<div class="col-sm-4">											 	
											<!-- 	<label><fmt:message key="inslip.validation.files" />: </label><span><a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${inSlip.referenceNumber}.pdf&folderName=inslip.file.folder"><h4>Download PDF</h4></a></span> --> 
											 	<label><fmt:message key="inslip.validation.files" />: </label><span><a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${inSlip.id}.pdf&folderName=inslip.file.folder"><h4>Download PDF </h4></a></span> 
											 	<label><fmt:message key="inslip.unsed.file.numbers" />: </label><span><a onmouseover="showDetails()" onmouseout="hideDetails()" href="#"><h4>Detail</h4></a></span>
											 	<div  id="unregistered" class="clear-both display-none flash-message" style="display:none">
       												${{unUsedAppNums}}
  											    </div>
  											    </div>	
                                                													
												<hr>
											</div>
										</div>	
    		
    		
                <!--begin: Datatable -->
    		    <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1644px;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white" role="row">

					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">NO</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="app.serial.number" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="app.surname" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="app.given.name" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="app.proces.type" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 188.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="app.status" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>
    						<c:forEach var="app" items="${apps}">
                            	<tr  role="row" class="even">
                            		<td>${app.id}</td>
                            		<td><span class=" message" onmouseover="showOutBordereaus('{{app.id}}');" onmouseout="showOutBordereau('{{app.id}}')" >${app.serialNumber}</span>
                            		<div class="display-none" id="{{app.id}}"></div></td>
                            		
                            		<c:set var = "namePrinted"  value = "0"/>
                            		<c:if test="${not empty app.transcript }">
                                        <td>${app.transcript.studentSession.student.person.surName}</td>
                            			<td>${app.transcript.studentSession.student.person.givenName}</td>
                            			<c:set var = "namePrinted"  value = "1"/>
                                     </c:if>
                                     <c:if test="${not empty app.certificate }">
                                     	<c:if test="${namePrinted ==0 }">
                                     		<td>${app.certificate.studentSession.student.person.surName}</td>
                            				<td>${app.certificate.studentSession.student.person.givenName}</td>
                                     	</c:if>
                                        
                                     </c:if>
                                     <c:if test="${not empty app.archive }">
                                     	<c:if test="${namePrinted ==0 }">
                                     		<td>${app.archive.person.surName}</td>
                            				<td>${app.archive.person.givenName}</td>
                                     	</c:if>
                                        
                                     </c:if>
                            		<td>${app.processType.description}</td>
                            		<td>${app.applicationStatus.description}</td>
                                    <td >
                            		    <select class="form-control input-sm">
                            		    	<option value="action">Action</option>
                                            <option onClick="openNav('APPLICATION DETAILS / DETAILS APPLICATION', '', '100%');doGet('${pageContext.request.contextPath}/manageFile/detail/${app.id}','contenu');" value="detail" >Detail</option>
											<option onClick="openNav('APPLICATION TRACKING / HISTORIQUE APPLICATION', '', '50%');doGet('${pageContext.request.contextPath}/simt/tracking?&entity=application&id=${app.id}' ,'contenu');" value="history">History</option>
                                        </select>
                                            
		
                                    </td>
                            	</tr>
                            </c:forEach>
                  </tbody>
			    </table>
                <!--end: Datatable -->
    		</div>
    	</div>

</div>
<!-- end:: Content -->