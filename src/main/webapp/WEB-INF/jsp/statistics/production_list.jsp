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
			       
				</div>
			</div>
		</div>
	</div>

  <div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	    <fmt:message key="app.title.production.statistics" />
	</h3>
    <!--begin: Search Form -->
	<form class="kt-form kt-form--fit kt-margin-b-20">
		<div class="row kt-margin-b-20">
        	<div class="col-lg-3">
            	<label style="color:#564FC1;"><fmt:message key="period" /></label>
           		<input name="datefilter" type="text" class="form-control" id="dateRangeId" placeholder="Select Range">
           	</div>
           	<div class="col-lg-3">
            	<label style="color:#564FC1;"><fmt:message key="appStatus" /></label>
           		<select  class="form-control"  id="appStatus" name="appStatus"  >
						<option value="0" >TOUS</option>
						<c:forEach var="appStatus" items="${appStatus}">
							<option value="${appStatus.id}" >${appStatus.description}</option> 
						</c:forEach>
				</select>
           	</div>
			<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
            	<button style="width: 50%; margin-top:20px; background-color: rgb(1, 168, 249);" type="button" class="btn btn-success" title="search In-Slip" onClick="getProductionStatistics();">
                	<i class="la la-search"></i>
                    <fmt:message key="search_msg" />
                </button>
            </div>
		</div>
		<div class="kt-separator kt-separator--md kt-separator--dashed"></div>
	</form>
  </div>

<!-- begin:: Content -->
<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">

	<div >
		 <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">

    		<div class="kt-portlet__body">
                <!--begin: Datatable -->
    		    <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1644px;">
				  	<thead style="background:#01A8F9;">
						<tr style="color:white"  role="row">
							<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending"><fmt:message key="app.categoryRegion.title" /></th>
							<c:forEach var="office" items="${offices}">
								<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">${office.abreviation }</th>
							</c:forEach>
							<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">TOTAL</th>
						</tr>
				  	</thead>
				  	<tbody>
				  		<c:set var = "i"  value = "1"/>
    						<c:forEach var="processType" items="${processTypes}">
    						    
                                    <tr  role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if> 
                                    <c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                    	<td >
                                           ${processType.abbreviation } 
                                        </td>
                                        <c:forEach var="office" items="${offices}">
                                        	<td>
                                        	0
                                        	</td>
                                        </c:forEach>
                                        <td>0</td>
                                    </tr>
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            <tr>
                            	<td >
                                           TOTAL
                                        </td>
                                        <c:forEach var="office" items="${offices}">
                                        	<td>
                                        	0
                                        	</td>
                                        </c:forEach>
                                        <td>
                                        0
                                        </td>
                            </tr>
                  </tbody>
			    </table>
                <!--end: Datatable -->
    		</div>
    	</div>

</div>
	</div> 
</div>
<!-- end:: Content -->

<script>
function createSlip(baseUrl, id) {
	document.getElementById("successId").classList.add("kt-hidden");
	document.getElementById("failedId").classList.add("kt-hidden");
	baseUrl=baseUrl+"/marine/inSlip/create"
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
		 closeNav();
		  swal.fire(
                     'In Slip Created successfully!',
                     'Thanks'
                );
		 doGet("/simt/marine/inSlip/list", "kt_content");
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
