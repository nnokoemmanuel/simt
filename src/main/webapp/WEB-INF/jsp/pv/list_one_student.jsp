<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	


	<td tabindex="0" class="sorting_1">
     		<c:out value = "${i}"/>
              	<input type="checkbox" class="row-select" id="${studentSession.id }">
              </td>
              <td>${studentSession.trainingCenter.name}</td>
              <td>${studentSession.student.person.surName}</td>
              <td>${studentSession.student.person.givenName}</td>
              <td id='studentSession.student.person.id'> <fmt:formatDate value="${candidateSession.person.dob}" type="date" pattern="dd-MM-yyyy" var="dob"/>${dob}</td>
              <td>${studentSession.person.pob}</td>
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
                	<c:out value=" ${studentSession.reasonForReject}" />
                	
                </td>
				<td><!-- Action -->
                  <select id="candidate-select-action-${studentSession.id }"  class="form-control input-sm">
                  	<option value="action">Action</option>
                  	<c:if test="${studentSession.studentSessionStatus.id==1 }">
                  		<option id="presented" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${studentSession.id}', 1, 'presented');"><fmt:message key="student.result.action.presented" /></option>
                  		</c:if>
                  	<c:if test="${studentSession.studentSessionStatus.id == 2 }">
                  		<option id="approved" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${studentSession.id}', 2, 'approved');"><fmt:message key="student.result.action.approved" /></option>
                  	</c:if>
                  	<c:if test="${studentSession.studentSessionStatus.id == 3 }">
                  		<option id="mark-as-pass" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${studentSession.id}', 3, 'mark-as-pass');"><fmt:message key="student.result.action.pass" /></option>
                  	</c:if>
                  	<c:if test="${studentSession.studentSessionStatus.id == 4 }">
                  		<option id="mark-as-fail" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${studentSession.id}', 4, 'mark-as-fail');"><fmt:message key="student.result.action.fail" /></option>
                  	</c:if>
                  	<c:if test="${studentSession.studentSessionStatus.id == 5 }">
                  		<option id="rejected" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${studentSession.id}', 5, 'rejected');"><fmt:message key="student.result.action.rejected" /></option>
                  	</c:if>

                  	<option value="history" onClick="openNav('STUDENT SESSION HISTORY', '', '50%');studentSession_action(this.value,'${studentSession.id}','studentSession');return false;"><fmt:message key="pv.action.history" /></option>
               
                 	</select>
              </td>
              
              