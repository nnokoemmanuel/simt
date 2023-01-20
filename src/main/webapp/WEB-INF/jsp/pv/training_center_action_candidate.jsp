<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<option value="action">Action</option>
<option value="details"
	onClick="openNav('Student Details | Information Personnelle', '', '60%');doGet('${pageContext.request.contextPath}/student/view?id=${candidate.student.id}','contenu')";" >Details</option>

<option value="history"><fmt:message key="app.action.history" /></option>

	<%-- <c:if test="${candidate.studentSessionStatus.id==1 }"> --%>
		<sec:authorize access="hasRole('ROLE_MANAGE_STUDENTS_UNDER_TRAINING')">
			<c:if test="${not empty candidate.student.person.surnameCni}">
				
				<option value="printSubscription"
						onclick="printSubscription('${pageContext.request.contextPath}','${candidate.id}','FR');active_link('student-management');return false;">fiche inscription fr</option>
				<option value="printSubscriptionEnglish" onclick="printSubscriptionEnglish('${pageContext.request.contextPath}','${candidate.id}','EN');active_link('student-management');return false;">fiche inscription en</option>
           </c:if>
            
		 <c:if test="${ empty candidate.student.person.surnameCni}">
            
            <option value="present-identity" onClick="openNav('', '', '60%');doGet('${pageContext.request.contextPath}/student/idCardForm?id=${candidate.id}','contenu')" ; return false;"><fmt:message key="present.candidate.cni.info" /></option>
		  </c:if>
		   <c:if test="${not empty candidate.student.person.surnameCni}">
            
            <option value="edit-cni-identity" onClick="openNav('', '', '60%');doGet('${pageContext.request.contextPath}/student/idCardForm?id=${candidate.id}','contenu')" ; return false;"><fmt:message key="edit.candidate.cni.info" /></option>
		  </c:if>		
		  </sec:authorize>
	<%-- </c:if> --%>
	
	 <c:forEach var="studentSession" items="${candidate.student.studentSessions}" varStatus="loop">
 		
 		<c:if test="${loop.last}">
        	<c:if test="${studentSession.studentSessionStatus.id==3 || studentSession.studentSessionStatus.id==5 || studentSession.studentSessionStatus.id==6 || studentSession.studentSessionStatus.id==7}">  
		
 		<sec:authorize access="hasRole('ROLE_MANAGE_STUDENTS_UNDER_TRAINING')"> 
 	
			<option value="present"
				onClick="openNav('Student Details | Information Personnelle', '', '60%');doGet('${pageContext.request.contextPath}/student/detail?id=${candidate.student.id}','contenu')
				; returnfalse;"><fmt:message key="transfer.candidate" />
			</option>
		</sec:authorize> 
		
 	</c:if>
       </c:if>
 
 </c:forEach> 

<!--
<option value="history"  onClick="openNav('MANAGE LIST OF STUDENT SESSION HISTORY', '', '50%');candidateSession_action(this.value,'${candidate.id}','studentSession');return false;"><fmt:message key="app.action.history" /></option>
-->


