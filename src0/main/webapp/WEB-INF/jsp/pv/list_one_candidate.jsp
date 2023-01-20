<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	
	
	<td tabindex="0" class="sorting_1">
     		<c:out value = "${i}"/>
              	<input type="checkbox" class="row-select" id="${candidateSession.id }">
              </td>
              <td>${candidateSession.trainingCenter.name}</td>
              <td>${candidateSession.person.surName}</td>
              <td>${candidateSession.person.givenName}</td>
              <td id='candidateSession.person.id'> <fmt:formatDate value="${candidateSession.person.dob}" type="date" pattern="dd-MM-yyyy" var="dob"/>${dob}</td>
              <td>${candidateSession.person.pob}</td>
              <td>${candidateSession.pvNumber}</td>
              <td>
	           	<c:if test="${candidateSession.examResult == 1 }">
	           		<fmt:message key="candidate.exam.result.admitted" />
	           	</c:if>
	           	<c:if test="${candidateSession.examResult == 2 }">
	           		<fmt:message key="candidate.exam.result.failed.theory" />
	           	</c:if>
	           	<c:if test="${candidateSession.examResult == 3 }">
	           		<fmt:message key="candidate.exam.result.passed.theory" />
	           	</c:if>
	           	<c:if test="${candidateSession.examResult == 4 }">
	           		<fmt:message key="candidate.exam.result.failed.practicals" />
	           	</c:if>
	           	<c:if test="${candidateSession.examResult == 5 }">
	           		<fmt:message key="candidate.exam.result.passed.practicals" />
	           	</c:if>
                              	
               </td>
				<td>
                	<select id="candidate-select-action-${candidateSession.id }"  class="form-control input-sm">
                      	<option value="action">Action</option>
                      	<c:if test="${candidateSession.examResult == 1 }">
                      		<option id="fail-theory" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 2, 'fail-theory');"><fmt:message key="candidate.result.action.fail.theory" /></option>
                      		<option id="pass-theory" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 3, 'pass-theory');"><fmt:message key="candidate.result.action.pass.theory" /></option>
                      	</c:if>
                      	<c:if test="${candidateSession.examResult == 2 }">
                      		<option id="send-back-to-admit" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 1, 'send-back-to-admit');"><fmt:message key="candidate.result.action.send.back.to.admitted" /></option>
                      	</c:if>
                      	<c:if test="${candidateSession.examResult == 3 }">
                      		<option id="send-back-to-admit" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 1, 'send-back-to-admit');"><fmt:message key="candidate.result.action.send.back.to.admitted" /></option>
                      		<option id="fail-practical" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 4, 'fail-practical');"><fmt:message key="candidate.result.action.fail.practicals" /></option>
                      		<option id="pass-practical" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 5, 'pass-practical');"><fmt:message key="candidate.result.action.pass.practicals" /></option>
                      	</c:if>
                      	<c:if test="${candidateSession.examResult == 4 }">
                      		<option id="send-back-theory" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 3, 'send-back-theory');"><fmt:message key="candidate.result.action.send.back.to.theory" /></option>
                      	</c:if>
                      	<c:if test="${candidateSession.examResult == 5 }">
                      		<option id="send-back-theory" onClick="manageCandidate('${pageContext.request.contextPath}/student/manage', '${candidateSession.id}', 3, 'send-back-theory');"><fmt:message key="candidate.result.action.send.back.to.theory" /></option>
                      	</c:if>

                      	<option value="history" onClick="openNav('CANDIDATE SESSION HISTORY', '', '50%');candidateSession_action(this.value,'${candidateSession.id}','candidateSession');return false;"><fmt:message key="pv.action.history" /></option>
                   
                  	</select>
              	</td>