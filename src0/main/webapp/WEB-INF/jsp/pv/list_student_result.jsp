<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script> --%>

    <!-- This value holds an indicator for the current list being displayed on the screen. Values change when each list is loaded -->
            <input id="list-indicator" type="hidden" value="1">
    		    <table id="table_result" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 100%;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white" role="row">

					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 24.25px;" aria-label="Company Name: activate to sort column ascending">No.</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 224.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.trainingCenter" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 120.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.given.surname" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.given.name" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 40.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="student.dob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="student.pob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 10.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.pvNum" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.exam.result" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="studentSession" items="${students}">
    						    <tr id="candidate-${studentSession.id}" role="row"<c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td tabindex="0" class="sorting_1">
                                        	<c:out value = "${i}"/>

                                        </td>
                                        <td>${studentSession.student.trainingCenter.name}</td>
                                        <td>${studentSession.student.person.surName}</td>
                                        <td>${studentSession.student.person.givenName}</td>
                                        <td id='studentSession.student.person.id'> <fmt:formatDate value="${studentSession.student.person.dob}" type="date" pattern="dd-MM-yyyy" var="dob"/>${dob}</td>
                                        <td>${studentSession.student.person.pob}</td>
                                        <td>${studentSession.pvNumber}</td>
                                        <td><!-- Status -->
                                        	<c:if test="${studentSession.studentSessionStatus.id == 1 }">
                                        		<fmt:message key="student.exam.result.status.presented" />
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 2 }">
                                        		<fmt:message key="student.exam.result.status.approved" />
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 3 }">
                                        		<fmt:message key="student.exam.result.status.rejected" />
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 4 }">
                                        		<fmt:message key="student.exam.result.status.succeed" />
                                        	</c:if>
                                        	<c:if test="${studentSession.studentSessionStatus.id == 5 }">
                                        		<fmt:message key="student.exam.result.status.failed" />
                                        	</c:if>
                                            <c:if test="${studentSession.studentSessionStatus.id == 6 }">
                                                <fmt:message key="student.exam.result.status.absent" />
                                            </c:if>
                                            <c:if test="${studentSession.studentSessionStatus.id == 7 }">
                                                <fmt:message key="student.exam.result.status.demisionnaire" />
                                            </c:if>

                                        </td>
										<td><!-- Action -->
                                            <select id="candidate-select-action-${studentSession.id }"  class="form-control input-sm">
						                      	<option value="action">Action</option>

						                      	<c:if test="${studentSession.studentSessionStatus.id == 1 }">
						                      	<!-- 	<option id="approved" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 2, 'approved');"><fmt:message key="student.result.action.approved" /></option>  -->
						                      		<option id="approved" onClick="approveStudentInSession('${studentSession.id}');"><fmt:message key="student.result.action.approved" /></option>
												    <option id="rejected" onClick="validateReasonForReject('${studentSession.id}');"><fmt:message key="student.result.action.rejected" /></option>
                                                    <!--  <option id="rejected" onClick="openNav('REASON FOR REJECT', '', '40%');approveReasonForReject;doGet('${pageContext.request.contextPath}/studentSession/manage/reason_for_reject/get?id=${studentSession.id}','contenu');"><fmt:message key="student.result.action.rejected" /></option>-->

						                      	</c:if>
						                      	<c:if test="${studentSession.studentSessionStatus.id == 2 }">
						                      		<option id="mark-as-pass" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 4, 'mark-as-pass');"><fmt:message key="student.result.action.pass" /></option>
						                      		<option id="mark-as-fail" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 5, 'mark-as-fail');"><fmt:message key="student.result.action.fail" /></option>
						                      		<option id="mark-as-absent" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 6, 'mark-as-absent');"><fmt:message key="student.result.action.absent" /></option>
						                      		<option id="send-from-approved" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 1, 'send-from-approved');"><fmt:message key="student.result.action.send.back.from.present" /></option>
						                      		<option id="mark-as-demissionnaire" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 7, 'mark-as-demissionnaire');"><fmt:message key="student.result.action.demissionnaire" /></option>


						                      	</c:if>
						                      	<c:if test="${studentSession.studentSessionStatus.id == 3 }">
						                      	    <option id="send-from-reject" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 1, 'send-from-reject');"><fmt:message key="student.result.action.send.back.from.reject" /></option>

						                      	</c:if>
						                      	<c:if test="${studentSession.studentSessionStatus.id == 4 }">
						                      		<option id="send-from-pass-to-approved" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 2, 'send-from-pass-to-approved');"><fmt:message key="student.result.action.send.back.from.pass.to.approved" /></option>
						                      	</c:if>

                                                <c:if test="${studentSession.studentSessionStatus.id == 6 }">
						                      		<option id="send-from-absent-to-approved" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 2, 'send-from-absent-to-approved');"><fmt:message key="student.result.action.send.back.from.absent.to.approved" /></option>
						                      	</c:if>

						                      	<c:if test="${studentSession.studentSessionStatus.id == 5 }">
                                                	<option id="send-from-fail-to-approved" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 2, 'send-from-fail-to-approved');"><fmt:message key="student.result.action.send.back.from.fail.to.approved" /></option>
                                                </c:if>
                                                <c:if test="${studentSession.studentSessionStatus.id == 7 }">
                                                    <option id="send-from-pass-to-approved" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', 2, 'send-from-pass-to-approved');"><fmt:message key="student.result.action.send.back.from.pass.to.approved" /></option>
                                                </c:if>
 												<option value="details" onClick="openNav('Student Details | Information Personnelle', '', '60%');doGet('${pageContext.request.contextPath}/student/view?id=${studentSession.student.id}','contenu')" ;" >Details</option>

						                      	<option value="history" onClick="openNav('STUDENT SESSION HISTORY', '', '50%');candidateSession_action(this.value,'${studentSession.id}','studentSession');return false;"><fmt:message key="pv.action.history" /></option>

						                  	</select>
                                        </td>
                                    </tr>

                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                  </tbody>
			    </table>
</div>
<!-- end:: Content -->
<script>
	addDataTable();
</script>