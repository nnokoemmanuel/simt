<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/js/pv/trainingCenter.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>

<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />
<%@ page import="java.util.Date"%>
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
					<sec:authorize access="hasRole('ROLE_MANAGE_TRAINNING_CENTERS')">
					<a href="#" onClick="openNav('New Trainning Center | Nouveau Centre de Formation', '', '40%');doGet('${pageContext.request.contextPath}/trainingCenter/create','contenu')"  class="btn btn-brand btn-elevate btn-icon-sm">
                       <i class="la la-plus"></i>
                         <fmt:message key="app.new.trainingCenter" />
                    </a>
                    </sec:authorize>
				</div>
			</div>
		</div>
	</div>


  <div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	    <fmt:message key="app.title.trainingCenter.list" />
	</h3>
    <!--begin: Search Form -->
	<form class="kt-form kt-form--fit kt-margin-b-20">
	<div class="row kt-margin-b-20">
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="specialiy.key" />:</label>
			<select id="speciality" class="form-control kt-input" data-col-index="2">
                <option value="ALL"><fmt:message key ="select_one" /></option>
                <c:forEach var="speciality" items="${specialities}">
                    <option value="${speciality.abbreviation}">${speciality.abbreviation}</option>
                </c:forEach>
			</select>
		</div>
	
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="divisions.key" />:</label>
			<select id="divisionId" class="form-control kt-input" data-col-index="2">
                <option value="0"><fmt:message key ="select_one" /></option>
                <c:forEach var="division" items="${divisions}">
                    <option value="${division.id}">${division.name}</option>
                </c:forEach>
			</select>
		</div>
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="status.key" />:</label>
			<select id="statusId" class="form-control kt-input" data-col-index="2">
                <option value="-1"><fmt:message key ="select_one" /></option>
                <option value="1"><fmt:message key ="trainingCenter_active" /></option>
                <option value="0"><fmt:message key ="trainingCenter_suspended" /></option>
			</select>
		</div>
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
                                      
           <button id="search-button" style="width: 50%; margin-top:20px;" type="button" class="btn btn-success" title="search Training centers" >
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
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ><fmt:message key="trainingCenter.Name" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="trainingCenter.authorisationNumber" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="trainingCenter.maxStudents" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="trainingCenter.status" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  >ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>

				            <c:set var = "i"  value = "1"/>
      						<c:forEach var="trainingCenter" items="${trainingCenters}">
      						    
                                    <tr id='trainingCenter-row-${trainingCenter.id}' role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td id='trainingCenter-name-${trainingCenter.id}'>
                                           ${trainingCenter.name}
                                        </td>
                                        <td id='trainingCenter-authorisationNumber-${trainingCenter.id}'>
                                          <c:forEach var="agreement" items="${trainingCenter.agreements}">
                                       		 <a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${agreement.id}.pdf&folderName=trainingCenter.agreement.file.folder">${agreement.authorisationNumber}</a><br>
                                          </c:forEach>
                                        </td>
                                        <td id='trainingCenter-maxStudents-${trainingCenter.id}'>
                                           ${trainingCenter.maxStudent}
                                        </td>
                                        <td id='trainingCenter-status-${trainingCenter.id}'>
                                           <c:if test="${trainingCenter.status == 1}">
                                           <fmt:message key="trainingCenter.active" />
                                           </c:if>
                                            <c:if test="${trainingCenter.status == 0}">
                                           <fmt:message key="trainingCenter.suspended" />
                                            </c:if>
                                        </td>
                                        <td>
                                            <select  class="form-control input-sm">
                                                <option value="action">Action</option>
                                                <sec:authorize access="hasRole('ROLE_MANAGE_TRAINNING_CENTERS')">
	                                                	<option onClick="editTrainingCenter('${pageContext.request.contextPath}/trainingCenter/edit/','${trainingCenter.id}');active_link('exam-center');return false;"><fmt:message key="trainingCenter.action.edit" /></option>
                                                </sec:authorize>
	                                            <option onClick="showTrainingCenter('${pageContext.request.contextPath}/trainingCenter/show/','${trainingCenter.id}');active_link('exam-center');return false;"><fmt:message key="trainingCenter.action.details" /></option>
                                                <sec:authorize access="hasRole('ROLE_MANAGE_TRAINNING_CENTERS')">
	                                                	<option onClick="deleteTrainingCenter('${pageContext.request.contextPath}/trainingCenter/delete/','${trainingCenter.id}');active_link('exam-center');return false;"><fmt:message key="trainingCenter.action.delete" /></option>
                                                        <option value="history" onClick="manageAction(this.value,'${trainingCenter.id}','trainingCenter');return false;"><fmt:message key="trainingCenter.action.history" /></option>  
                                                 </sec:authorize>
                                                 <sec:authorize access="hasRole('ROLE_VIEW_TRAINNING_CENTERS')">
                                                        <option onClick="doGet('${pageContext.request.contextPath}/trainingCenter/list/${trainingCenter.id}','kt_content');active_link('exam-center');return false;"><fmt:message key="trainingCenter.action.view.students" /></option>
                                                 	    <option onClick="printStudentsList('${pageContext.request.contextPath}/trainingCenter/printStudentsList/','${trainingCenter.id}');active_link('exam-center');return false;"><fmt:message key="trainingCenter.action.print.students.List" /></option>
                                                 </sec:authorize>
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
<script>
	addDataTable();
</script>