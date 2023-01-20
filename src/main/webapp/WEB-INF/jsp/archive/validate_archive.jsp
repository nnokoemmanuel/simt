<%@include file="archive_detail.jsp" %>
<div class="pull-right" >
	<button style=" background-color: rgb(1, 168, 249);" onclick="validArchive('${pageContext.request.contextPath}/archive/update/validate/${archive.id}','achiveTableContent');"><fmt:message key="archive.validate.label" /></button>
</div>