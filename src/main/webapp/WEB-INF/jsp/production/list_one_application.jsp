<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>

<%@ page import="java.util.Date"%>


	<td tabindex="0" class="sorting_1"><c:out value="${i}" /></td>
	<td>${application.serialNumber}</td>
	<fmt:formatDate value="${application.computerizationDate}"
		pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
	<td>${computerizationDate}</td>

	<!-- start managing status -->
	<td>
		<c:if test="${application.processType.id == 1 }">
			<fmt:message key="processType.new.transcript" />
		</c:if> <c:if test="${application.processType.id == 2 }">
			<fmt:message key="processType.new.certificate" />
		</c:if> <c:if test="${application.processType.id == 3 }">
			<fmt:message key="processType.new.professional.card" />
		</c:if> <c:if test="${application.processType.id == 4 }">
			<fmt:message key="processType.duplicate.transcript" />
		</c:if> <c:if test="${application.processType.id == 5 }">
			<fmt:message key="processType.duplicate.certificate" />
		</c:if> <c:if test="${application.processType.id == 6 }">
			<fmt:message key="processType.duplicate.professional.card" />
		</c:if>
	</td>
	
	<!-- end  managing status -->
	<td><c:if
			test="${application.certificate.studentSession == NULL }">
        ${application.archive.person.surName} ${application.archive.person.givenName}
    </c:if> <c:if test="${application.archive == NULL }">


			<c:if test="${application.transcript != 'NULL' }">
        		${application.transcript.studentSession.student.person.surName} ${application.transcript.studentSession.student.person.givenName}
</c:if>
        		${application.certificate.studentSession.student.person.surName} ${application.certificate.studentSession.student.person.givenName}
    	    	
    </c:if></td>
	<!-- start managing status -->
	<td> 
		<c:if test="${application.applicationStatus.id == 1 }">
			<fmt:message key="application.status.registered" />
		</c:if> <c:if test="${application.applicationStatus.id == 2 }">
			<fmt:message key="application.status.rejected" />
		</c:if> <c:if test="${application.applicationStatus.id == 3 }">
			<fmt:message key="application.status.cancelled" />
		</c:if> <c:if test="${application.applicationStatus.id == 4 }">
			<fmt:message key="application.status.confirmed" />
		</c:if> <c:if test="${application.applicationStatus.id == 5 }">
			<fmt:message key="application.status.capacity.printed" />
		</c:if> <c:if test="${application.applicationStatus.id == 6 }">
			<fmt:message key="application.status.successfully.printed" />
		</c:if> <c:if test="${application.applicationStatus.id == 7 }">
			<fmt:message key="application.status.confirmed.rejected" />
		</c:if> <c:if test="${application.applicationStatus.id == 8 }">
			<fmt:message key="application.status.unsuccessfully.printed" />
		</c:if> <c:if test="${application.applicationStatus.id == 9 }">
			<fmt:message key="application.status.delivered" />
		</c:if> <c:if test="${application.applicationStatus.id == 10 }">
			<fmt:message key="application.status.deliver.rejected" />
		</c:if> <c:if test="${application.applicationStatus.id == 11 }">
			<fmt:message key="application.status.authorized.reprint" />
		</c:if>


	</td>

	<!-- end  managing status -->
	<td><c:if test="${application.transcript != NULL }">
			<select
				onchange="application_action(this.value,'${application.id}','application', '${application.processType.id}','${application.transcript.studentSession.id}','${application.inSlip.eligibleCenter.id}');return false;"
				class="form-control input-sm">

				<%@include file="action.jsp"%>
			</select>
		</c:if> <c:if test="${application.certificate != NULL }">
			<select
				onchange="application_action(this.value,'${application.id}','application', '${application.processType.id}','${application.certificate.studentSession.id}','${application.inSlip.eligibleCenter.id}');return false;"
				class="form-control input-sm">

				<%@include file="action.jsp"%>
			</select>
		</c:if></td>




   
