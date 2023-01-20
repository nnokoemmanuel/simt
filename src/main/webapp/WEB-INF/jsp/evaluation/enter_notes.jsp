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




  <div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	   <span style="color:red;"> ${studentSession.student.person.givenName} </span> <br>
	   <fmt:message key="training.center.label" /> : ${studentSession.student.trainingCenter.name}  <span style="color:#01A8F9;">(${studentSession.student.trainingCenter.location})</span>
	</h3>
  </div>

<!-- begin:: Content -->
<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
   <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">
			    <form class="kt-form" id="enterNotesForm">
                <!--begin: Datatable -->
    		    <table id="table-studentSession-notes" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info">
				  <thead style="background:#01A8F9;">
					<tr id="headers" style="color:white"  role="row">
					
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" >NO</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  >COURS</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  >NOTE</th>
					</tr>
				  </thead>
				  <tbody>

                           <c:if test="${fn:length(listeStudentSessionNotes) == 0}">
				            <c:set var = "i"  value = "1"/>
      						<c:forEach var="course" items="${courses}">
                                    <tr id='course-row-${course.id}' role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td id='course-line-${course.id}' tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td id='course-${course.id}' >
                                        	<label id='course-label-${course.id}'>${course.completeName}</label>
                                        </td>
                                        <td id='course-note-${course.id}'> <input id='input-course-note-${course.id}' type="text" min="0" max="20" /></td>
                                    </tr>
                              	
                                
                            <c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            </c:if>
                            <c:if test="${fn:length(listeStudentSessionNotes) > 0}">
				            <c:set var = "i"  value = "1"/>
      						       <c:forEach var="studentSessionNote" items="${listeStudentSessionNotes}">
                                    <tr id='course-row-${studentSessionNote.course.id}' role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td id='course-line-${studentSessionNote.course.id}'  tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
		                                        <td id='course-${studentSessionNote.course.id}' >
		                                        	<label id='course-label-${studentSessionNote.course.id}'>${studentSessionNote.course.completeName}</label>
		                                        </td>
		                                        <td id='course-note-${studentSessionNote.course.id}'> <input id='input-course-note-${studentSessionNote.course.id}' type="text" min="0" max="20" value="${studentSessionNote.courseNote}" /></td>
                                       
                                     </tr>
                                     <c:set var = "i"  value = "${i+1}"/>
                              	    </c:forEach>
                              	    <c:forEach var="course" items="${coursesRemaining}">
                              	    
                              	    
		                                    <tr id='course-row-${course.id}' role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
		                                        <td id='course-line-${course.id}'  tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
				                                        <td id='course-${course.id}' >
				                                        	<label id='course-label-${course.id}'>${course.completeName}</label>
				                                        </td>
				                                        <td id='course-note-${course.id}'> <input id='input-course-note-${course.id}' type="text" min="0" max="20" value="0" /></td>
		                                     </tr>
	                                    
                                     <c:set var = "i"  value = "${i+1}"/>
                              	    </c:forEach>
                          
                            </c:if>
                  </tbody>
			    </table>
                <!--end: Datatable -->  
                <div class="kt-portlet__foot">
				<div class="kt-form__actions">
					<button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" saveStudentNotes('${pageContext.request.contextPath}/evaluation/saveNotes','${studentSession.id}');">Save Notes</button>
				</div>
				</div>
                </form>
    		
    	</div>

</div>
<!-- end:: Content -->
<script>
	$('#table-studentSession-notes').dataTable( {
		  paginate: false,
		  scrollY: 300
		} );
</script>
<script src="${pageContext.request.contextPath}/resources/static/js/evaluation/studentSessionNotes.js" type="text/javascript"></script>