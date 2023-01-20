<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="${pageContext.request.contextPath}/js/pv/pv.js" type="text/javascript"></script>
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
								<li onclick="print_Admitted_pdf();" class="kt-nav__item">
									<a href="#" class="kt-nav__link">
										<i class="kt-nav__link-icon la la-file-pdf-o"></i>
										<span class="kt-nav__link-text">Print PDF</span>
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
	    <fmt:message key="app.title.admitted.list" />
	</h3>
    <!--begin: Search Form -->
    <input type="hidden" id="baseUrl" value="${pageContext.request.contextPath}"/>
	<form class="kt-form kt-form--fit kt-margin-b-20">
	<div class="row kt-margin-b-20">
         <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
         <label><fmt:message key="app.date.year" />:</label>
         <input id="pvYear" class="form-control kt-input" type="text" name="${currentYear}" placeholder="2022" onchange="loadExamSession(this.value, '${pageContext.request.contextPath}/pv/get/sessions');"/>
         <div id="year-error"></div>
         </div>   

		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="app.exam.session" /></label>
			<div id="sessions">
				<select id="session-date" class="form-control kt-input" data-col-index="2">
	                <option value="0"><fmt:message key="select_one" /></option>
	                <c:forEach var="session" items="${sessions}">
	                    <option value="${session.id}"><fmt:formatDate value="${session.sessionDate}" type="date" pattern="dd-MM-yyyy"/></option>
	                </c:forEach>
				</select>
				<div id="date-error"></div>
			</div>
            
		</div>
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="app.exam.center" /></label>
			<div id="examCenters">
				<select id="exam-center-select" class="form-control kt-input" data-col-index="2">
	                <option value="0"><fmt:message key="select_one" /></option>
	                <c:forEach var="examCenter" items="${examCenters}">
	                    <option value="${examCenter.id}">${examCenter.name}</option>
	                </c:forEach>
				</select>
				
			</div>
            
		</div>
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
			<label><fmt:message key="training_centers_label" />:</label>
			<select id="training-center" class="form-control kt-input" data-col-index="2">
                <option value="0"><fmt:message key="option_all" /></option>
	                <c:forEach var="trainingCenter" items="${trainingCenters}">
	                    <option value="${trainingCenter.id}">${trainingCenter.name}</option>
	                </c:forEach>
			</select>
		</div>
		<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
                                      
           <button id="search-admitted-button" style="width: 50%; margin-top:20px;" type="button" class="btn btn-success" title="search In-Slip" >
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
    	<div class="kt-portlet kt-portlet--mobile" id="result-table">
			
                <!--begin: Datatable -->
    		    <table id="table-eligible-centers-admitted-students" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info">
				  <thead style="background:#01A8F9;">
					<tr style="color:white"  role="row">
					
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" >NO</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ><fmt:message key="student.surname.label" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ><fmt:message key="student.givenName.label" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="student.pob.label" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="student.dob.label" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="student.speciality.label" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  >ACTIONS</th>
                   	
					</tr>
				  </thead>
				  <tbody>

				            <c:set var = "i"  value = "1"/>
      						<c:forEach var="studentSession" items="${studentSessions}">
      						    
                                    <tr role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${studentSession.student.person.surName}</td>
                                        <td>${studentSession.student.person.givenName}</td>	
                                        <td>${studentSession.student.person.pob}</td>
                                        <td>${studentSession.student.person.dob}</td>
                                        <td>${studentSession.speciality.abbreviation}</td>
                                        <td>
                                            <select  class="form-control input-sm">
                                                <option value="action">Action</option>
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

<script>
	addDataTable();
	var searchAdmittedButton = document.querySelector('#search-admitted-button');
	searchAdmittedButton.addEventListener('click', function(){
			if(document.getElementById("session-date").value == "0"){
		    	swal.fire(
		                'Not Allowed!',
		                'Please Select an exam session | Svp Selectionnez une session d examen',
		                'error'
		            )
				return false;
			}
		    if(document.getElementById("exam-center-select").value == "0"){
		    	swal.fire(
		                'Not Allowed!',
		                'Please Select an exam center | Svp Selectionnez un centre d examen',
		                'error'
		            )
				return false;
		    }
		    if(document.getElementById("training-center").value == "0"){
		    	swal.fire(
		                'Not Allowed!',
		                'Please Select a training center | Svp Selectionnez un centre de formation',
		                'error'
		            )
				return false;
			}
			var url = $("#baseUrl").val() + "/student/getAdmitted";
			var session= document.querySelector('#session-date').value;
			var trainingCenter= document.querySelector('#training-center').value;
			var examCenter= document.getElementById("exam-center-select").value;
			var params =  "?sessionId=" + session+"&trainingCenterId="+trainingCenter+"&examCenterId="+examCenter ;
			url+=params;
			doGet(url,'result-table');
			addDataTable();
			});
		
	
</script>