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
