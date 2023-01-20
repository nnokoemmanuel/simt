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
	<div class="kt-portlet__head kt-portlet__head--lg">
		<div class="kt-portlet__head-label">
		</div>
		<div class="kt-portlet__head-toolbar">
			<div class="kt-portlet__head-wrapper">
				<div class="kt-portlet__head-actions">
					<div class="dropdown dropdown-inline">
						<button type="button" class="btn btn-default btn-icon-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<i class="la la-download"></i> Export
						</button>
						<div class="dropdown-menu dropdown-menu-right">
							<ul class="kt-nav">
								<li class="kt-nav__section kt-nav__section--first">
									<span class="kt-nav__section-text">Choose an option</span>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-print"></i>
										<span class="kt-nav__link-text">Print</span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-copy"></i>
										<span class="kt-nav__link-text">Copy</span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-file-excel-o"></i>
										<span class="kt-nav__link-text">Excel</span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-file-text-o"></i>
										<span class="kt-nav__link-text">CSV</span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-file-pdf-o"></i>
										<span class="kt-nav__link-text">PDF</span>
									</a>
								</li>
							</ul>
						</div>
					</div>
					&nbsp;
			       <sec:authorize access="hasRole('ROLE_MANAGE_INSLIP')">
					<a href="#" class="btn btn-brand btn-elevate btn-icon-sm" onClick="openNav('In Slip Creation', '', '40%');doGet('${pageContext.request.contextPath}/inSlip/create','contenu')">
                       <i class="la la-plus"></i>
                         <fmt:message key="app.new.in.slip" />
                    </a>
                     </sec:authorize>
				</div>
			</div>
		</div>
	</div>

  <div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	    <fmt:message key="app.title.in.slip.list" />
	</h3>
    <!--begin: Search Form -->
	<form class="kt-form kt-form--fit kt-margin-b-20">
										<div class="row kt-margin-b-20">
                                            <div class="col-lg-3">
                                            	<label style="color:#564FC1;"><fmt:message key="plage.temps" /></label>
                                            	<input name="datefilter" type="text" class="form-control" id="dateRangeId" placeholder="Select time">
                                            </div>

											<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
												<label>Status:</label>
                                                <select id="status" class="form-control kt-input" data-col-index="2">
                                                    <option value="0"><fmt:message key="option_all" /></option>
                                                    <option value="1"><fmt:message key="status.in.slip.registered" /></option>
                                                    <option value="2"><fmt:message key="status.in.slip.open" /></option>
                                                    <option value="3"><fmt:message key="status.in.slip.close" /></option>
                                                    <option value="4"><fmt:message key="status.in.slip.controlled" /></option>                                                    
												</select>
											</div>
											<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
											<c:if test="${user.group.id == 2 }">
												<label><fmt:message key="office.slip" />:</label>
												<select id="office" class="form-control kt-input" data-col-index="2">
                                                    <!-- <option value="100"><fmt:message key="option_all" /></option> -->
                                                   <!--   <c:forEach var="office" items="${listOffices}">  -->
                                                        <option value="${userOffice.id }">${userOffice.name}</option>
                                                   <!--   </c:forEach> -->
												</select>
											</c:if>	
											<c:if test="${user.group.id != 2 }">
												<label><fmt:message key="office.slip" />:</label>
												<select id="office" class="form-control kt-input" data-col-index="2">
                                                     <option value="100"><fmt:message key="option_all" /></option>
                                                      <c:forEach var="office" items="${listOffices}">  
                                                        <option value="${office.id }">${office.name}</option>
                                                      </c:forEach> 
												</select>
											</c:if>
											</div>
											<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
                                               <center>
                                                 <button style="width: 50%; margin-top:20px;" type="button" class="btn btn-success" title="search In-Slip" onClick="searchButtonInSlipList();">
                                                     <i class="la la-search"></i>
                                                     <fmt:message key="search_msg" />
                                                 </button>
                                               </center>
											</div>
										</div>
										<div class="row kt-margin-b-20">
                                            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
												<label>Reference Number:</label>
												<input id="referenceNum" type="text" class="form-control kt-input" placeholder="DTR-5-01188" data-col-index="0">
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
    		    <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1644px;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white" role="row">

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
    						    
                                    <tr  role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if> 
                                    <c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
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
                            
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            </c:if>
                            <c:if test="${user.group.id != 2 }">
                            <c:set var = "i"  value = "1"/>
    						<c:forEach var="inSlip" items="${inSlips}">
    						    
                                    <tr  role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if> 
                                    <c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
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

<script>
function createSlip(baseUrl, id) {
	document.getElementById("successId").classList.add("kt-hidden");
	document.getElementById("failedId").classList.add("kt-hidden");
	baseUrl=baseUrl+"/inSlip/create"
  if($("#inSlipForm").valid()==true) {
	 const total=document.getElementById("totalId").value;
	 const file=document.getElementById("fileId").files[0];
	 const filename=document.getElementById("fileId").files[0].name;
	 const slipNumber =document.getElementById("slipNumberId").value;
	 var formData = new FormData();
	 formData.append("id",id);
	 formData.append("slipNumber",slipNumber);
	 formData.append("total", total);
	 formData.append("file",file);
	 formData.append("filename",filename);
	 displayLoading("inSlipForm");
	
	 doPost(baseUrl,formData,"kt_content").then(result=>{
		 removeLoading("inSlipForm");
		 document.getElementById(result).classList.remove("kt-hidden");
		 document.getElementById("totalId").value="";
		 document.getElementById("fileId").value=null;
		 document.getElementById("slipNumberId").value="";
		 if(result == "successId"){
			  closeNav();
			  swal.fire(
	                     'In Slip Created successfully! '+result,
	                     'Thanks'
	                );
			 doGet("/simt/inSlip/list", "kt_content");
		 }
	 }).catch(error=>{
		 removeLoading("inSlipForm");
		 document.getElementById(error).classList.remove("kt-hidden");
		 
	 });

  }else $("#inSlipForm").validate();
}

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
