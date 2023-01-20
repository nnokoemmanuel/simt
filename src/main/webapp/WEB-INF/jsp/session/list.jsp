<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />

<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
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

				</div>
			</div>
		</div>
	</div>

<div class="kt-container  kt-container--fluid ">

	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	      <fmt:message key="app.menu.session.management" />
	</h3>
	
	 
    <!--begin: Search Form -->
	<form class="kt-form kt-form--fit kt-margin-b-20">
			<div class="row kt-margin-b-20">

            							      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

            						         <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
            							         <input id="sessionYear" 
            							                class="form-control kt-input" class="form-control required"
            							                placeholder="yyyy" name="sessionDate" type="number"
            							               min="${minYear}" max="${maxYear}" />

            						         </div>
            										<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">

            										        <button type="button" style="background:#01A8F9" class="btn btn-pill"
            										                id="searchSession"  
            												        onclick="loadSession();searchSessionTable();">
            										                 <i class="la la-search"></i>
            										                 <fmt:message key="search_msg" />

            										        </button>
            								        </div>

            </div>
		    <div class="kt-separator kt-separator--md kt-separator--dashed"></div>
		</form>
</div>


<!-- begin:: Content -->
<!-- <div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid"> -->
   <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">

    		<div class="kt-portlet__body">
                <!--begin: Datatable -->
    		<table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; ">
				<thead style="background:#01A8F9;">
					<tr style="color:white" role="row">
                    <th class="sorting_asc" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;"><strong>No</strong></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 176.35px;"><strong>Session Date</strong></th>
					
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 176.35px;"><strong>Session Status</strong></th>
				
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 176.35px;" ><strong>createdOn</strong></th>
			        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" ><strong>Action</strong></th>
					</tr>
				</thead>
				        <tbody>
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="session" items="${session}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <fmt:formatDate value="${session.sessionDate}" pattern="dd/MM/yyyy" var="sessionDate" />
                                        <td><c:out value="${sessionDate}" /></td>
                                        <fmt:formatDate value="${session.createdOn}" pattern="dd/MM/yyyy hh.mm,s" var="sessionCreationDate" />
                                        <td><c:out value="${session.status}" /></td>
                                        <td><c:out value="${sessionCreationDate}" /></td>
                                        
                                        <sec:authorize access="hasRole('ROLE_MANAGE_ELIGIBLE_CENTER')">
                                        
                                  
                                        <td>
                                          <c:if test="${session.status=='REGISTER' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Open')" >
                                               <fmt:message key="open" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                            <c:if test="${session.status=='OPEN' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Close')" >
                                               <fmt:message key="close" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                           
                                           <c:if test="${session.status=='CLOSE' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Reopen')" >
                                               <fmt:message key="reopen" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                        </td>
                                        </sec:authorize>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <fmt:formatDate value="${session.sessionDate}" pattern="dd/MM/yyyy" var="sessionDate" />
                                        <td><c:out value="${sessionDate}" /></td>
                                        <fmt:formatDate value="${session.createdOn}" pattern="dd/MM/yyyy hh.mm,s" var="sessionCreationDate" />
                                        <td><c:out value="${session.status}" /></td>
                                        <td><c:out value="${sessionCreationDate}" /></td>
                                       
                                        
                                        <sec:authorize access="hasRole('ROLE_MANAGE_ELIGIBLE_CENTER')">
                                        <td>
                                            <c:if test="${session.status=='REGISTER' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Open')" >
                                               <fmt:message key="open" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                            <c:if test="${session.status=='OPEN' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Close')" >
                                               <fmt:message key="close" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                           
                                           <c:if test="${session.status=='CLOSE' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Reopen')" >
                                               <fmt:message key="reopen" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                        </td>
                                        </sec:authorize>
                                    </tr>
                            	</c:if>
                            <c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            
                            
                            
                            <c:forEach var="session" items="${sessionSearch}">
								 <!-- <tr align="left">  --> 
								  
									  <c:if test ="${i%2==1}">
									  <tr  role="row" class="odd clear-odd">
									      <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
										    <!-- <td tabindex="0" class="sorting_1"><c:out value="${session.id}" /></td> -->
											<td><fmt:formatDate value="${session.sessionDate}" type="date" pattern="dd-MM-yyyy"/> </td>
											<td><c:out value="${session.status}" /></td>
											<fmt:formatDate value="${session.createdOn}" pattern="dd/MM/yyyy hh.mm,s" var="sessionCreationDate" />
											<td><c:out value="${sessionCreationDate}" /></td>
											
											<td>
											 <c:if test="${session.status=='REGISTER' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Open')" >
                                               <fmt:message key="open" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                            <c:if test="${session.status=='OPEN' }">
                                            <a href="#"
                                               id="sessionId"
                                  
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Close')" >
                                               <fmt:message key="close" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                           
                                           <c:if test="${session.status=='CLOSE' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Reopen')" >
                                               <fmt:message key="reopen" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
											</td> 
										</tr>
										</c:if>
									
									   <c:if test ="${i%2==0}">
									    <tr  role="row" class="even dark-even">
										      <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
											    <!-- <td tabindex="0" class="sorting_1"><c:out value="${session.id}" /></td> -->
												<td><fmt:formatDate value="${session.sessionDate}" type="date" pattern="dd-MM-yyyy"/> </td>
												<td><c:out value="${session.status}" /></td>
												<fmt:formatDate value="${session.createdOn}" pattern="dd/MM/yyyy hh.mm,s" var="sessionCreationDate" />
												<td><c:out value="${sessionCreationDate}" /></td>
												<sec:authorize access="hasRole('ROLE_MANAGE_ELIGIBLE_CENTER') ">
												<td>
												<a href="#"
												   style="visibility:hidden;"
	                                               id="sessionId"
	                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
	                                               onClick="openNav('Advanced Session Management', '', '40%');doGet('${pageContext.request.contextPath}/marine/session/update?id=${session.id}','contenu');" >
	                                               <fmt:message key="app.session.update" />      <!--  Edit search-list even-values -->
	                                            </a> 
												</td> 
												</sec:authorize>
											</tr>
										</c:if>
                                    <c:set var = "i"  value = "${i+1}"/>
			               	<!--	</tr> -->
		                   </c:forEach>
		                   
                        </tbody>
			</table>
                    <!--end: Datatable -->
    			</div>
    	    </div>

</div>

<!-- end:: Content --c
<!--begin::Page Scripts(used by this page) -->
		<script src="${pageContext.request.contextPath}/js/session.js" type="text/javascript"></script>
   
 <script>
 <!--end::Page Scripts -->

 	addDataTable();
 </script>
 <script>
   function manageStatus(id, sessionDate, action){
   Swal.fire({
  title: 'Are you sure you want to '+action+  ' this session '+sessionDate,
  showDenyButton: true,
  showCancelButton: true,
  confirmButtonText: `Save`,
  denyButtonText: `Don't save`,
}).then((result) => {
  console.log(result);
  if (result.hasOwnProperty('value') === true) {
   let url =$("#baseUrl").val();
   url=url+"/studentSession/manageStatus?id="+id+"&action="+action;
     doget(url).then(response=>{
     
     if(response === 'accepted'){
      Swal.fire('change accepted!', '', 'success');
     doGet($("#baseUrl").val()+"/marine/session/list",'kt_content');
     }
     else Swal.fire('Changes are not saved', '', 'info');
     }).catch(err=>{
     Swal.fire('internal serverv errvor', '', 'warning');
     });

  }else{
    Swal.fire('Changes are not saved', '', 'info')
  }
})
   };

 </script>