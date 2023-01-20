
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- This value holds an indicator for the current list being displayed on the screen. Values change when each list is loaded -->
            <input id="list-indicator" type="hidden" value="1">
    		    <table id="table_result" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 100%;">
				  <thead style="background:#01A8F9;">
					<tr style="color:white" role="row">

					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 24.25px;" aria-label="Company Name: activate to sort column ascending">No</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 24.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.license.number" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 120.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.given.surname" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.given.name" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 40.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="student.dob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="student.pob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 10.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.speciality" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.exam.result" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.final.score" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 100.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.final.training.center" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="applicant" items="${applicants}">
    						    <tr id="applicant-${applicant.id}" role="row"<c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td tabindex="0" class="sorting_1">
                                        	<c:out value = "${i}"/>

                                        </td>
                                        <td>${applicant.person.licenseNum}</td>
                                        <td>${applicant.person.surName}</td>
                                        <td>${applicant.person.givenName}</td>
                                        <td id='applicant.person.id'> <fmt:formatDate value="${applicant.person.dob}" type="date" pattern="dd-MM-yyyy" var="dob"/>${dob}</td>
                                        <td>${applicant.person.pob}</td>
                                        <td>${applicant.speciality.abbreviation}</td>
                                        <c:if test="${applicant.note == 0 }">
                                            <td>${applicant.result}</td>
                                        </c:if>
                                        <c:if test="${applicant.note > 0 }">
                                           <td style="background-color:#bdc27a" >${applicant.result}</td>
                                        </c:if>
                                        <td>${applicant.note}</td>
                                        <td>${applicant.student.trainingCenter.name}</td>
                                        
										<td><!-- Action -->
                                            <select id="applicant-select-action-${applicant.id }"  class="form-control input-sm">
						                      	<option value="action">Action</option>

						                      	<c:if test="${applicant.result == 'REGISTERED' }">
						                      		<option id="mark-as-pass-theory" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'PASSED THEORY', 'pass-theory');"><fmt:message key="applicant.result.action.passed.theory" /></option>
						                      		<option id="mark-as-fail-theory" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'FAILED THEORY', 'fail-theory');"><fmt:message key="applicant.result.action.failed.theory" /></option>
						                      		
						                      	</c:if>
						                      	<c:if test="${applicant.result== 'FAILED THEORY' }">
						                      		<option id="send-from-approved" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'REGISTERED', 'send-back-from-theory');"><fmt:message key="applicant.result.action.send.back" /></option>


						                      	</c:if>
						                      	<c:if test="${applicant.result == 'PASSED THEORY' }">
						                      	    <option id="mark-as-pass" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'PASSED PRACTICALS', 'pass-practical');"><fmt:message key="applicant.result.action.passed.practicals" /></option>
						                      		<option id="mark-as-fail" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'FAILED PRACTICALS', 'fail-practical');"><fmt:message key="applicant.result.action.failed.practicals" /></option>
						                      		<option id="send-from-reject" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'REGISTERED', 'send-back-from-theory');"><fmt:message key="applicant.result.action.send.back" /></option>
													
						                      	</c:if>
						                      	<c:if test="${applicant.result== 'PASSED PRACTICALS' }">
						                      	<option id="mark-examen-result" onClick="openNav('FINAL EXAMEN RESULT ', '', '50%');doGet('${pageContext.request.contextPath}/applicant/examenResult/?applicantId=${applicant.id}', 'contenu');"><fmt:message key="student.result.action.examen.result" /></option>
						                      		<c:if test="${entranceEligibleCenter.entranceEligibleCenterStatus.id == 4  }">
						                      				<option id="mark-as-advance-to-training" onClick="openNav('SELECT TRAINING CENTER', '', '50%');doGet('${pageContext.request.contextPath}/applicant/advanceToTraining/?applicantId=${applicant.id}', 'contenu');"><fmt:message key="student.result.action.advanced.to.training.center" /></option>
						                      		</c:if>
						                      		<c:if test="${entranceEligibleCenter.entranceEligibleCenterStatus.id != 4  }">

						                      		    <c:if test="${applicant.note == 0 }">
						                      			    <option id="mark-as-fail" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'PASSED THEORY', 'mark-as-pass-theory');"><fmt:message key="applicant.result.action.send.back" /></option>
						                      		    </c:if>
						                      		</c:if>


						                      	</c:if>
						                      	<c:if test="${applicant.result == 'FAILED PRACTICALS' }">
						                      	    <option id="send-from-failed-practicals" onClick="manageApplicant('${pageContext.request.contextPath}/applicant/manage', '${applicant.id}', 'PASSED THEORY', 'send-back-from-practical');"><fmt:message key="applicant.result.action.send.back" /></option>

						                      	</c:if>
						                      	<c:if test="${applicant.result == 'ADVANCED TO TRAINING' }">
						                      	
						                      	</c:if>
						                      	<option value="history" onClick="openNav('APPLICANT HISTORY', '', '50%');candidateSession_action(this.value,'${applicant.id}','applicant');return false;"><fmt:message key="pv.action.history" /></option>

						                  	</select>
                                        </td>
                                    </tr>

                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                  </tbody>
			    </table>