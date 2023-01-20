<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>
	<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
		<div class="kt-wizard-v4" id="kt_user_add_user" data-ktwizard-state="step-first">

			<span><h4> <fmt:message key="consult.more.personal.info" /></h4></span>  
			<hr style="border:1px solid blue; border-radius:1px;">
			<div class="row">
				<div class="col-sm-12">
					<div class="col-sm-10 pull-left"> 
						<%@include file="personal_info.jsp" %>
					</div>
					<div class="col-sm-2 pull-right" style="align:left">
	    				<div class="kt-widget__media"> 							   							
	    				
	    						<c:if test="${empty photo}">
<%-- 	    							<c:if test="${empty record.photo}"> --%>
	    									<img class="kt-widget__img" src="${pageContext.request.contextPath}/assets/media/users/default.jpg" alt="image">
<%-- 	         						</c:if>  --%>
<%--          							<c:if test="${not empty consultRecord.photo}">   --%>
<%--         								<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${consultRecord.photo}&folderName=application.photo.folder" width="130" height="130" alt="image"> --%>
<%--          							</c:if> --%>
	         					 </c:if>
	         					<c:if test="${not empty photo}">
	         					
	         					 	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${photo}&folderName=studentSession.photo.folder" width="130" height="130" alt="image">     
         						</c:if>
         					  
        				</div>
					</div>
				</div>
			</div>
			<hr style="border:1px solid blue; border-radius:1px;">
			
			<div class="row">
				<div class="col-sm-12">
					<div class="col-sm-6 pull-left">
						<span><h4>  <fmt:message key="consult.more.capacity.info" /></h4></span>
						<hr>
						<%@include file="capacity_info.jsp" %>
					</div>
					<div class="col-sm-6 pull-right"> 
					<span><h4> <fmt:message key="consult.more.archive.info" /></h4></span>
					<hr >
			
						<%@include file="archives.jsp" %>
					</div>
			
					<hr style="border:1px solid blue; border-radius:1px;">
					
				</div>
			</div>					
			<hr style="border:1px solid blue; border-radius:1px;">
			<div class="row">
				<div class="col-sm-12">
					
					<div class="col-sm-12 pull-right"> 
					<span><h4> <fmt:message key="consult.more.candidate.session" /></h4></span>
					<hr >
			
						<%@include file="candidate_sessions.jsp" %>
					</div>
			
					<hr style="border:1px solid blue; border-radius:1px;">
					
				</div>
			</div>
			<hr style="border:1px solid blue; border-radius:1px;">
			<div class="row">
				<div class= "col-sm-12">
					<span><h4><fmt:message key="consult.more.applications" /></h4></span>
					<hr>
					<%@include file="applications.jsp" %>
				</div>
			</div>
		</div>
	</div>	
</div>