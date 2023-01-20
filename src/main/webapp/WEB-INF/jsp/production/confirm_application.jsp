<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="application_detail.jsp" %>
<div class="pull-right" >

	<button style=" background-color: rgb(1, 168, 249);" onclick="confirmApplication(${application.id})"><fmt:message key="confirm.application" /></button>
</div>