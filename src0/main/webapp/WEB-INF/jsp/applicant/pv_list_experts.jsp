<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/js/applicant/pv.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>

<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />
<%@ page import="java.util.Date"%>


<div class="kt-subheader-search app-color ">
	<div class="kt-container  kt-container--fluid ">
		<h6 class="kt-subheader-search__title">
			<fmt:message key="computerize.applicant" />
		</h6>
	</div>
</div>

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
					<sec:authorize access="hasRole('ROLE_MANAGE_ELIGIBLE_CENTER')">
					<a href="#" onClick="openNav('Eligible Center Creation', '', '40%');doGet('${pageContext.request.contextPath}/expertPv/create','contenu')"  class="btn btn-brand btn-elevate btn-icon-sm">
                       <i class="la la-plus"></i>
                         <fmt:message key="app.new.pv" />
                    </a>
                    </sec:authorize>
				</div>
			</div>
		</div>
	</div>


  <div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	    <fmt:message key="app.title.pv.list" />
	</h3>
    <!--begin: Search Form -->
	<form class="kt-form kt-form--fit kt-margin-b-20">
	<div class="row kt-margin-b-20">
         <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
         <label><fmt:message key="app.date.year" />:</label>
         <input id="pvYear" class="form-control kt-input" type="text" name="year" placeholder="2020" onchange="loadExamSession(this.value, ${pageContext.request.contextPath}/expertPv/sessions);"/>
         <div id="year-error"></div>
         </div>   

		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="app.exam.session" /></label>
			<div id="sessions">
				<select id="session-date" class="form-control kt-input" data-col-index="2">
	                <option value="&"><fmt:message key="select_one" /></option>
	                <c:forEach var="session" items="${sessions}">
	                    <option value="${session.id}"><fmt:formatDate value="${session.sessionDate}" type="date" pattern="dd-MM-yyyy"/></option>
	                </c:forEach>
				</select>
				<div id="date-error"></div>
			</div>
            
		</div>
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="office.slip" />:</label>
			<select id="region" class="form-control kt-input" data-col-index="2">
                <option value="100"><fmt:message key="option_all" /></option>
                <c:forEach var="region" items="${regions}">
                    <option value="${region.id}">${region.name}</option>
                </c:forEach>
			</select>
		</div>
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
                                      
           <button id="search-button-experts" style="width: 50%; margin-top:20px;" type="button" class="btn btn-success" title="search In-Slip" >
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
   <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">
			
                <!--begin: Datatable -->
    		    <table id="table-eligible-centers" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info">
				  <thead style="background:#01A8F9;">
					<tr style="color:white"  role="row">
					
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" >NO</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ><fmt:message key="app.exam.center" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ><fmt:message key="app.exam.session" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="app.pv.status" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  >ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>

				            <c:set var = "i"  value = "1"/>
      						<c:forEach var="eligibleCenter" items="${entranceEligibleCenters}">
      						    
                                    <tr role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td id='eligibleCenter-name-${eligibleCenter.id}' >
                                        	<c:if test="${eligibleCenter.entranceEligibleCenterStatus.id == 4 }">
                                       		    <a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${eligibleCenter.id}.pdf&folderName=eligibleCenter.experts.file.folder">${eligibleCenter.entranceExamCenter.name}</a>
                                       		</c:if>
                                       		<c:if test="${eligibleCenter.entranceEligibleCenterStatus.id != 4 }">
                                       			${eligibleCenter.entranceExamCenter.name}
                                       		</c:if>
                                        </td>
                                        <td id='eligibleCenter-session-date-${eligibleCenter.id}'> <fmt:formatDate value="${eligibleCenter.entranceExamSession.sessionDate}" type="date" pattern="dd-MM-yyyy"/></td>
                                        <!-- start managing status -->
                                        <c:if test="${eligibleCenter.entranceEligibleCenterStatus.id == 1 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.created" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.entranceEligibleCenterStatus.id == 2 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.opened" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.entranceEligibleCenterStatus.id == 3 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.closed" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.entranceEligibleCenterStatus.id == 4 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.validated" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.entranceEligibleCenterStatus.id == 5 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.generated" /></td>
                                        </c:if>
                                        <!-- end  managing status -->
                                          <td >
                                            <select  class="form-control input-sm">
                                                <option value="action">Action</option>
                                                
                                                <sec:authorize access="hasRole('ROLE_MANAGE_ELIGIBLE_CENTER')">
	                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id==1}">
	                                                	<option onClick="openEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.open" /></option>
	                                                </c:if>
                                                </sec:authorize>
                                                
                                                <sec:authorize access="hasRole('ROLE_PV_CONTROLLER')">
	                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id==2}">
	                                                    <option onClick="closeEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.close" /></option>
                                                        
	                                                </c:if>
	                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id==3}">
	                                                    <option onClick="resetEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.reset" /></option>
	                                                </c:if>
	                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id==3}">
	                                                    <option onClick="generatePV('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.generate" /></option>
	                                                </c:if>
	                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id==5}">
	                                                	<option onClick="resetPV('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.reset" /></option>
	                                                    <option onClick="validateEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.validate" /></option>
	                                                </c:if>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_MANAGE_APPLICANT')">
                                                	<c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id==2 || eligibleCenter.entranceEligibleCenterStatus.id==3}">
	                                                    <option value="applicant" onClick="doGet('${pageContext.request.contextPath}/expertPv/manage_applicants/?entranceEligibleCenterId=${eligibleCenter.id}','kt_content'); active_link('concours-experts');"><fmt:message key="pv.action.manage.applicant" /></option>

                                                    </c:if>
                                                    <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id==4}">
	                                                    <option value="applicant" onClick="doGet('${pageContext.request.contextPath}/expertPv/manage_applicants/?entranceEligibleCenterId=${eligibleCenter.id}','kt_content'); active_link('concours-experts');"><fmt:message key="pv.action.manage.send.to.training" /></option>

                                                    </c:if>
                                                </sec:authorize>
                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id >= 2}">
                                                    <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id != 4}">
                                                        <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_list_presented@IPCSR');active_link('concours-experts');return false;">Liste des Participants presentes - (IPCSR)</option>
                                                        <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_list_presented@DPCSR');active_link('concours-experts');return false;">Liste des Participants presentes - (DPCSR)</option>
                                                    </c:if>
                                                </c:if>
                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id >= 3}">
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_pass_theorie@IPCSR');active_link('concours-experts');return false;">Liste des Participants ayant reussir la theorie - (IPCSR)</option>
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_pass_theorie@DPCSR');active_link('concours-experts');return false;">Liste des Participants ayant reussir la theorie - (DPCSR)</option>
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_close_theorie@IPCSR');active_link('concours-experts');return false;">Liste des Participants ayant echoue la theorie - (IPCSR)</option>
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_close_theorie@DPCSR');active_link('concours-experts');return false;">Liste des Participants ayant echoue la theorie - (DPCSR)</option>
                                                </c:if>
                                                <c:if test = "${eligibleCenter.entranceEligibleCenterStatus.id == 4}">
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_close_pratique@IPCSR');active_link('concours-experts');return false;">Liste des Participants ayant echoue la pratique - (IPCSR)</option>
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_close_pratique@DPCSR');active_link('concours-experts');return false;">Liste des Participants ayant echoue la pratique - (DPCSR)</option>
                                                    <option  onCLick="printPV('${eligibleCenter.id}','print_pv@IPCSR');active_link('concours-experts');return false;"><fmt:message key="list.print.pv.ipcsr" /></option>
                                                    <option  onCLick="printPV('${eligibleCenter.id}','print_pv@DPCSR');active_link('concours-experts');return false;"><fmt:message key="list.print.pv.dpcsr" /></option>
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_list_training@IPCSR');active_link('concours-experts');return false;">Liste des Participants en Formations - (IPCSR)</option>
                                                    <option  onCLick="printApplicantList('${eligibleCenter.id}','applicant_list_training@DPCSR');active_link('concours-experts');return false;">Liste des Participants en Formations - (DPCSR)</option>
                                                </c:if>
                                              <!--   <option value="history" onClick="eligibleCenter_PV_action(this.value,'${eligibleCenter.id}','eligibleCenter');return false;"><fmt:message key="pv.action.history" /></option>  --> 
                                              <option value="history" onClick="entranceEligibleCenter_PV_action(this.value,'${eligibleCenter.id}','entranceEligibleCenter');return false;"><fmt:message key="pv.action.history" /></option>  
                                                  
                                            </select>
                                         </td>
                                    </tr>
                              	
                                
                            <c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                  </tbody>
			    </table>
                <!--end: Datatable -->
    		
    	</div>

</div>
<!-- end:: Content -->


<div class="kt-subheader-search app-color ">
	<div class="kt-container  kt-container--fluid ">
		<h6 class="kt-subheader-search__title">
			
		</h6>
	</div>
</div>

<script>
	addDataTable();
</script>