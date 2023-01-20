<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script> --%>

    		    <table id="table_result" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 100%;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white" role="row">
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 10.10px;" aria-label="Country: activate to sort column ascending">No.</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 120.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="candidate.given.surname" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="candidate.given.name" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 40.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="candidate.dob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="candidate.pob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 10.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="candidate.speciality" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 10.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="candidate.refNumber" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 10.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="candidate.licenseNum" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="student" items="${finalListStudents}">
    						    <tr id="student-${student.id}" role="row"<c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td>${i}</td>
                                        <td>${student.person.surName}</td>
                                        <td>${student.person.givenName}</td>
                                        <td id='student.person.id'> <fmt:formatDate value="${student.person.dob}" type="date" pattern="dd-MM-yyyy" var="dob"/>${dob}</td>
                                        <td>${student.person.pob}</td>
                                        <td>${student.speciality.abbreviation}</td>
                                        <td>${student.referenceNum}</td>
                                        <td>${student.person.licenseNum}</td>
										<td>
                                            <select id="candidate-select-action-${candidateSession.id }"  class="form-control input-sm">
						                      	<option value="action">Action</option>
				                      	        <option value="history" onClick="openNav('STUDENT SESSION HISTORY', '', '50%');candidateSession_action(this.value,'${student.id}','student');return false;"><fmt:message key="pv.action.history" /></option>
						                        <option value="details" onClick="openNav('Student Details', '', '60%');doGet('${pageContext.request.contextPath}/student/view?id=${student.id}','contenu')" ; return false;">Detail</option> 
						                        
						                  	</select>
                                        </td>
                                    </tr>
                            	
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                  </tbody>
			    </table>
			    