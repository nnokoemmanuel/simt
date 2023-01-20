<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>


<div class="kt-portlet kt-portlet--mobile">
	<div class="kt-portlet__head kt-portlet__head--lg">
		<div class="kt-portlet__head-label">
			
			
		</div>
		<div class="kt-portlet__head-toolbar">
			<div class="kt-portlet__head-wrapper">
				<div class="kt-portlet__head-actions">
					<div class="dropdown dropdown-inline">
						<button type="button" class="btn btn-default btn-icon-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<i class="la la-download"></i> <fmt:message key="archive.list.export" />
						</button>
						<div class="dropdown-menu dropdown-menu-right">
							<ul class="kt-nav">
								<li class="kt-nav__section kt-nav__section--first">
									<span class="kt-nav__section-text"><fmt:message key="archive.list.choose.option" /></span>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-print"></i>
										<span class="kt-nav__link-text"><fmt:message key="archive.list.print" /></span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-copy"></i>
										<span class="kt-nav__link-text"><fmt:message key="archive.list.copy" /></span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-file-excel-o"></i>
										<span class="kt-nav__link-text"><fmt:message key="archive.list.excel" /></span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-file-text-o"></i>
										<span class="kt-nav__link-text"><fmt:message key="archive.list.csv" /></span>
									</a>
								</li>
								<li class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-file-pdf-o"></i>
										<span class="kt-nav__link-text"><fmt:message key="archive.list.pdf" />PDF</span>
									</a>
								</li>
							</ul>
						</div>
					</div>
					&nbsp;
					<sec:authorize access="hasRole('ROLE_MANAGE_ARCHIVE') ">
					<a href="#" class="btn btn-brand btn-elevate btn-icon-sm" onClick="doGet('${pageContext.request.contextPath}/archive/create/actionCreate/0','kt_content');active_link('archive-pv');return false;">
						 <fmt:message key="app.archive.new" />
					</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>



     <div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
		<fmt:message key="archive.list" />  
	</h3>
	
	<div class="form-group col-sm-6">
		<label for="activate" class="  col-sm-9 control-label">
			<fmt:message key="search.per.licenseNumberOrArchiveNumber" />
		</label>
		<div class="col-sm-9">
			<label for="activate"> <input id="activate"
				name="activate" type="checkbox"
				class="pos-rel p-l-30 custom-checkbox" value="1">
				&nbsp;
			</label>
		</div>
	</div> 
	
	<form class="kt-form">
	<div id="block2" style="display:none;">
	<div class="kt-grid kt-grid--desktop kt-grid--ver-desktop">
			<div class="kt-grid__item kt-grid__item--middle">
				<div class="row kt-margin-r-10 form-group row">			   
					<div class="col-lg-12">
					    <label style="color:#564FC1;"><fmt:message key="numero.permis.and.matricule" />:</label>
					<input id="archiveNumber" type="text" class="form-control kt-input" placeholder="NW-101256-09" data-col-index="0">
					</div>   
				</div>
			</div>
			<div class="kt-grid__item kt-grid__item--middle" style="margin-bottom:10px">
				<div class="kt-margin-top-20 kt--visible-tablet-and-mobile"></div>
				<button type="button"  style="background:#01A8F9"class="btn btn-pill " id="searchArchive" onClick="loadArchive('${pageContext.request.contextPath}');">Search</button>
			</div>
		</div>
	
	
	
	</div>
	<div id="block1">
		<div class="kt-grid kt-grid--desktop kt-grid--ver-desktop">
			<div class="kt-grid__item kt-grid__item--middle">
				<div class="row kt-margin-r-10 form-group row">
				   
				   
					<div class="col-lg-7">
					    <label style="color:#564FC1;"><fmt:message key="plage.temps" /></label>
						<input name="datefilter" type="text" class="form-control" id="archiveRangeId" readonly="" placeholder="Select time">
					</div>
    
                   <div class="col-lg-5">
                   <label  style="color:#564FC1;"><fmt:message key="status" /></label>
			        <select class="form-control" id="archiveStatus">
								<option value="ALL"><fmt:message key="archive.list.all" /></option>
								<option value="REGISTERED"><fmt:message key="archive.list.Registered" /></option>
								<option value="VALIDATED"><fmt:message key="archive.list.validated" /></option>
			      </select>
		          </div>
		    
				</div>
			</div>
			<div class="kt-grid__item kt-grid__item--middle" style="margin-bottom:10px">
				<div class="kt-margin-top-20 kt--visible-tablet-and-mobile"></div>
				<button type="button"  style="background:#01A8F9"class="btn btn-pill " id="searchArchive" onClick="loadArchive('${pageContext.request.contextPath}');">Search</button>
			</div>
		</div>
	</div>	
	</form>
</div>
		   
		   <div class="kt-portlet__body">

			<div id="kt_table_1_wrapper" class=" dt-bootstrap4">
			<div class="row"><div class="col-sm-12 col-md-6">
			
			
			<div class="dataTables_length" ></div></div>
			<div class="col-sm-12 col-md-6">
			<div id="kt_table_1_filter" class="dataTables_filter">
			</div></div></div><div class="row"><div class="col-sm-12">
			<div style="position: absolute; height: 1px; width: 0px; overflow: hidden;"><input type="text" tabindex="0"></div>
			
			<div id="achiveTableContent">
			 
			     <%@include file="table.jsp" %>
			     
		    </div>
			
			</div>
			
			</div>
		</div>
	</div>	    
		<script>
			var KTAppOptions = {
				"colors": {
					"state": {
						"brand": "#22b9ff",
						"light": "#ffffff",
						"dark": "#282a3c",
						"primary": "#5867dd",
						"success": "#34bfa3",
						"info": "#36a3f7",
						"warning": "#ffb822",
						"danger": "#fd3995"
					},
					"base": {
						"label": ["#c5cbe3", "#a1a8c3", "#3d4465", "#3e4466"],
						"shape": ["#f0f3ff", "#d9dffa", "#afb4d4", "#646c9a"]
					}
				}
			};
		</script>
		
		 <script>
	          addDataTable();
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
		