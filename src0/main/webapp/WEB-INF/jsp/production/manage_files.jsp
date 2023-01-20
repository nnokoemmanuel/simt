<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />
<div class="kt-portlet kt-portlet--mobile">
	<div class="kt-subheader-search ">
		<div class="kt-container  kt-container--fluid ">
			<h6 class="kt-subheader-search__title">
				<fmt:message key="title.mamage.files" />
			</h6>
		</div>
	</div>
  <div class="kt-container  kt-container--fluid ">

    <div class="form-group col-sm-3">
		<label for="activate" class="  col-sm-9 control-label">
			<fmt:message key="periode" />
		</label>
		<div class="col-sm-9">
			<label for="activate"> <input id="activate"
				name="activate" type="checkbox"
				class="pos-rel p-l-30 custom-checkbox" value="1">
				&nbsp;
			</label>
		</div>
	</div>

    <!--begin: Search Form -->

	<form id ="form" class="kt-form kt-form--fit kt-margin-b-20">

	    <div id="block2" style="display:none;">
		  <div class="row kt-margin-b-20">
            <div class="col-lg-3">
                <label style="color:#564FC1;"><fmt:message key="plage.temps_file" /></label>
                <input name="datefilter" type="text" class="form-control" id="dateRangeId" placeholder="Select time">
            </div>
            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
				<label><fmt:message key="processType.application" />:</label>
				<select id="processType" class="form-control kt-input" data-col-index="2">
                    <option value="0"><fmt:message key="option_all" /></option>
                    <c:forEach var="processType" items="${listProcessType}">
                        <option value="${processType.id }">${processType.description}</option>
                    </c:forEach>

				</select>
			</div>
            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
				<label><fmt:message key="application.status" />:</label>
                <select id="status" class="form-control kt-input" data-col-index="2">
                    <option value="0"><fmt:message key="option_all" /></option>
                    <c:forEach var="applacationStatus" items="${listApplacationStatus}">
                        <option value="${applacationStatus.id }">${applacationStatus.description}</option>
                    </c:forEach>
				</select>
			</div>
			<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">

               <center>
                   <button style="width: 50%; margin-top:20px; background-color: rgb(1, 168, 249);" type="button" class="btn btn-success" title="search Application" onClick="searchButtonApplicationList();">
                   <i class="la la-search"></i>
                   <fmt:message key="search_msg" />
                   </button>
               </center>
			</div>
		  </div>
		  <div class="row kt-margin-b-20">
            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
				<label><fmt:message key="inslip.reference" />:</label>
				<input id="referenceNum" type="text" class="form-control kt-input" placeholder="NW-10056-0221-P" data-col-index="0">
			</div>

            <sec:authorize access="hasRole('ROLE_MANAGE_ALL_OFFICE')">
                <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
                    <label><fmt:message key="office.slip" />:</label>
                    <select id="office" class="form-control kt-input" data-col-index="2">
                    <option value="${myOffice.id }">${myOffice.name}</option>
                    <c:forEach var="office" items="${listOffices}">
                        <c:if test="${office.id != myOffice.id }">
                            <option value="${office.id }">${office.name}</option>
                        </c:if>
                    </c:forEach>
                    </select>
                </div>
			</sec:authorize>
            <sec:authorize access="!hasRole('ROLE_MANAGE_ALL_OFFICE')">
                <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile" style="display:none">
                    <label><fmt:message key="office.slip" />:</label>
                    <select id="office" class="form-control kt-input" data-col-index="2">
                         <option value="${myOffice.id }">${myOffice.name}</option>
                    </select>
                </div>
			</sec:authorize>
          </div>
        </div>
        <div id="block1">
          <div class="row kt-margin-b-20">
            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
				<label><fmt:message key="numero.dossier" />:</label>
				<input id="fileNumber" type="text" class="form-control kt-input" placeholder="NW-10056-0221-P-1" data-col-index="0">
			</div>

            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
               <center>
                   <button id="search-file" style="width: 50%; margin-top:20px; background-color: rgb(1, 168, 249);" type="button" class="btn btn-success" title="search Application" onClick="searchButtonApplicationList();">
                   <i class="la la-search"></i>
                   <fmt:message key="search_msg" />
                   </button>
               </center>
		    </div>
		  </div>
        </div>

		<div class="kt-separator kt-separator--md kt-separator--dashed"></div>
	</form>
  </div>

<!-- begin:: Content -->
<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
   <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">
    		<div class="kt-portlet__body">
                <!--begin: Datatable -->
    		    <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1650px;">
				  <thead style="background:#01A8F9;">
					<tr 1 style="color:white"  role="row">
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">NO</th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 130.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="numero.dossier" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 130.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="app.in.slip.create.date" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 188.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="processType.application" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 250.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="app.person.all.name" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 160.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="application.status" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 60.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>
					</tr>
				  </thead>
				  <tbody>
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="application" items="${listApplication}">
    						    
                                    <tr id="appRow-${application.id }" role="row" class=" <c:if test ="${i%2==1}">odd </c:if><c:if test ="${i%2==0}">even</c:if> clear-odd">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${application.serialNumber}</td>
                                        <fmt:formatDate value="${application.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>

                                        <!-- start managing status -->
                                        <td >
	                                        <c:if test="${application.processType.id == 1 }">
	                                        	<fmt:message key="processType.new.transcript" />
	                                        </c:if>
	                                        <c:if test="${application.processType.id == 2 }">
	                                        	<fmt:message key="processType.new.certificate" />
	                                        </c:if>
	                                        <c:if test="${application.processType.id == 3 }">
	                                        	<fmt:message key="processType.new.professional.card" />
	                                        </c:if>
	                                        <c:if test="${application.processType.id == 4 }">
	                                        	<fmt:message key="processType.duplicate.transcript" />
	                                        </c:if>
	                                        <c:if test="${application.processType.id == 5 }">
	                                        	<fmt:message key="processType.duplicate.certificate" />
	                                        </c:if>
	                                        <c:if test="${application.processType.id == 6 }">
	                                        	<fmt:message key="processType.duplicate.professional.card" />
	                                        </c:if>
                                       </td>
                                        <!-- end  managing status -->
                                        	<td>
                                        	<c:if test="${application.transcript != 'NULL' }">
                                            	${application.transcript.studentSession.student.person.surName} ${application.transcript.studentSession.student.person.givenName}
											</c:if>
                                            	${application.certificate.studentSession.student.person.surName} ${application.certificate.studentSession.student.person.givenName}
                                        	
                                        	</td>
										

                                        <!-- start managing status -->
                                        <td >
                                        	<c:if test="${application.applicationStatus.id == 1 }">
                                            	<fmt:message key="application.status.registered" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 2 }">
                                           		<fmt:message key="application.status.rejected" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 3 }">
                                            	<fmt:message key="application.status.cancelled" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 4 }">
                                            	<fmt:message key="application.status.confirmed" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 5 }">
                                            	<fmt:message key="application.status.capacity.printed" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 6 }">
                                            	<fmt:message key="application.status.successfully.printed" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 7 }">
                                            	<fmt:message key="application.status.confirmed.rejected" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 8 }">
                                            	<fmt:message key="application.status.unsuccessfully.printed" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 9 }">
                                            	<fmt:message key="application.status.delivered" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 10 }">
                                            	<fmt:message key="application.status.deliver.rejected" />
                                        	</c:if>
                                        	<c:if test="${application.applicationStatus.id == 11 }">
                                            	<fmt:message key="application.status.authorized.reprint" />
                                        	</c:if>
                                        
                                        </td>
                                        <!-- end  managing status -->
                                        <td >
                                        <c:if test="${application.transcript != NULL }">
                                            <select onchange="application_action(this.value,'${application.id}','application', '${application.processType.id}','${application.transcript.studentSession.id}','${application.transcript.studentSession.eligibleCenter.id}');return false;" class="form-control input-sm">
                                                 <%@include file="action.jsp" %>
                                            </select>
                                        </c:if>
                                        <c:if test="${application.certificate != NULL }">
                                            <select onchange="application_action(this.value,'${application.id}','application', '${application.processType.id}','${application.certificate.studentSession.id}','${application.certificate.studentSession.eligibleCenter.id}');return false;" class="form-control input-sm">

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

<script>
$("input[type=checkbox][name=activate]").change(function(){
    if(this.checked){

          document.getElementById("block2").style.display = "block";
          document.getElementById("block1").style.display = "none";
          document.getElementById("form").reset();
    }else if(!this.checked){

          document.getElementById("block1").style.display = "block";
          document.getElementById("block2").style.display = "none";
          document.getElementById("form").reset();
    }
});

</script>

<script >
$(function() {

  $('input[name="datefilter"]').daterangepicker({
      autoUpdateInput: false,
      locale: {
          cancelLabel: 'Clear'
      }
  });

  $('input[name="datefilter"]').on('apply.daterangepicker', function(ev, picker) {
      $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
  });

  $('input[name="datefilter"]').on('cancel.daterangepicker', function(ev, picker) {
      $(this).val('');
  });

});

</script>
