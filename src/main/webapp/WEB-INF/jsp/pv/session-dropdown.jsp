<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<select id="session-date" class="form-control kt-input" data-col-index="2">
	<option value="&"><fmt:message key="select_one" /></option>
    	<c:forEach var="session" items="${sessions}">
        	 <option value="${session.id}"><fmt:formatDate value="${session.sessionDate}" type="date" pattern="dd-MM-yyyy"/></option>
        </c:forEach>
</select>
<div id="date-error"></div>