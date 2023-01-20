<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<option value="action">Action</option>

    <c:if test="${student.speciality.abbreviation == 'MAE'}">
    	<sec:authorize access="hasRole('ROLE_MANAGE_STUDENTS_UNDER_TRAINING')">

            <c:if test="${ empty student.person.archive }">
                  <c:if test="${ empty student.studentSessions }">
                        <option value="present"
                           onClick="openNav('Student Details | Information Personnelle', '', '60%');doGet('${pageContext.request.contextPath}/student/detail?id=${student.id}','contenu')
                           ; returnfalse;"> <fmt:message key="present.candidate" /></option>
                  </c:if>
                  <c:if test="${not empty student.studentSessions }">
                            <c:set var="test" value="0" />
                            <c:forEach var="studentFoundSession" items="${studentFound.studentSessions}" >
                                <c:if test="${studentFoundSession.studentSessionStatus.id != 4 && studentFoundSession.eligibleCenter.eligibleCenterStatus.id == 4}">
                                    <c:set var = "test"  value = "${test+1}"/>
                                </c:if>
                            </c:forEach>
                         <c:if test="${fn:length(student.studentSessions) == test}">
                            <option value="present"
                               onClick="openNav('Student Details | Information Personnelle', '', '60%');doGet('${pageContext.request.contextPath}/student/detail?id=${student.id}','contenu')
                               ; returnfalse;"> <fmt:message key="present.candidate" /></option>
                         </c:if>
                  </c:if>
            </c:if>
    	</sec:authorize>
    </c:if>


    <c:if test="${student.speciality.abbreviation == 'IPCSR' || student.speciality.abbreviation == 'DPCSR'}">
    	<sec:authorize access="hasRole('ROLE_MANAGE_STUDENTS_UNDER_TRAINING')">
            <option value="present"
               onClick="openNav('Student Details | Information Personnelle', '', '60%');doGet('${pageContext.request.contextPath}/student/detail?id=${student.id}','contenu')
               ; returnfalse;"> <fmt:message key="present.candidate" /></option>
    	</sec:authorize>
    </c:if>

    <c:forEach var="studentSession" items="${student.studentSessions}" varStatus="status">
        <c:if test="${status.last}">

            <c:if test="${studentSession.studentSessionStatus.id==1 }">
                <sec:authorize access="hasRole('ROLE_MANAGE_STUDENTS_UNDER_TRAINING')">
                    <c:if test="${not empty studentSession.student.person.surnameCni}">

                        <option value="printSubscription"
                                onclick="printSubscription('${pageContext.request.contextPath}','${studentSession.id}','FR');active_link('student-management');return false;">fiche inscription fr</option>
                        <option value="printSubscriptionEnglish" onclick="printSubscriptionEnglish('${pageContext.request.contextPath}','${studentSession.id}','EN');active_link('student-management');return false;">fiche inscription en</option>
                   </c:if>
                  <c:if test="${ empty studentSession.student.person.surnameCni}">

                    <option value="present-identity" onClick="openNav('', '', '60%');doGet('${pageContext.request.contextPath}/student/idCardForm?id=${studentSession.id}','contenu')" ; return false;"><fmt:message key="present.candidate.cni.info" /></option>
                  </c:if>
                   <c:if test="${not empty studentSession.student.person.surnameCni}">

                    <option value="edit-cni-identity" onClick="openNav('', '', '60%');doGet('${pageContext.request.contextPath}/student/idCardForm?id=${studentSession.id}','contenu')" ; return false;"><fmt:message key="edit.candidate.cni.info" /></option>
                  </c:if>

                </sec:authorize>
            </c:if>

        </c:if>
    </c:forEach>
<option value="history"><fmt:message key="app.action.history" /></option>

<option value="details"
	onClick="openNav('Student Details | Information Personnelle', '', '60%');doGet('${pageContext.request.contextPath}/student/view?id=${student.id}','contenu')";" >Details</option>




