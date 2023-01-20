<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div >
	<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
		<div class="kt-wizard-v4" id="kt_user_add_user" data-ktwizard-state="step-first">
		<span><h3><fmt:message key="application.detail.app.number" />: <strong>${application.serialNumber}</strong></h3></span>  
			<hr style="border:1px solid blue; border-radius:1px;">
			<div class="row">
			<div class = col-sm-3>
					<c:if test="${not empty application.photo}">
                      	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${application.photo}&folderName=application.photo.folder" width="150" height="150" alt="image">
                    </c:if>
                    <c:if test="${empty application.photo}">
						<img class="kt-widget__img" src="resources/static/img/default_user.png" alt="<fmt:message key="picture"/>">
					</c:if>
				</div>
				<div class = col-sm-3>
					<c:if test="${not empty application.signature}">
                      	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${application.signature}&folderName=application.signature.folder" width="150" height="75" alt="image">
                    </c:if>
                    <c:if test="${empty application.signature}">
						<img class="kt-widget__img" src="resources/static/img/default_user.png" alt="<fmt:message key="picture"/>">
					</c:if>
				</div>
			</div>
				<hr style="border:1px solid blue; border-radius:1px;">
		<span><h4> <fmt:message key="application.details.label" /></h4></span>  
			<hr style="border:1px solid blue; border-radius:1px;">		
			<div class="row"> 
				<div class="col-sm-4">
					<label><span><fmt:message key="application.details.process.type" />:</span></label>
					<span><h5>${application.processType.description}</h5></span>
				</div>
				<div class="col-sm-4">
					<label><span><fmt:message key="application.details.inslip.number" />:</span></label>
					<span><h5>${application.inSlip.referenceNumber}</h5></span>
				</div>
				
				<div class="col-sm-4">
					<label><span><fmt:message key="application.details.app.number" />:</span></label>
					<span><h5>${application.number}</h5></span>
				</div>
				<div class="col-sm-4">
					<label><span><fmt:message key="application.details.authority" />:</span></label>
					<span><h5>${application.authority.position}</h5></span>
					<c:if test="${ application.authority==null}">
							<select  class="form-control"  id="authority" name="authority"  >
								<option value="">Select authority ...</option>
								<c:forEach var="authority" items="${authorities}">
										<option value="${authority.id}" >${authority.position}</option> 
							    </c:forEach>
							</select>
					</c:if>
				</div>
				

				<div class="col-sm-4">
					<label><span><fmt:message key="application.details.form.serial.number" />:</span></label>

				</div>
			</div>
			<hr style="border:1px solid blue; border-radius:1px;">
			<span><h4> <fmt:message key="application.details.personal.infos.label" /></h4></span>
			<hr style="border:1px solid blue; border-radius:1px;">
			<div class="row">
				
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.surname" />:</span></label>
					<span><h5>${person.surName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.givenName" />:</span></label>
					<span><h5>${person.givenName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.dob" />:</span></label>
					<span><h5>${person.dob}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.pob" />:</span></label>
					<span><h5>${person.pob}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.gender" />:</span></label>
					<span><h5>${person.gender}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.nationality" />:</span></label>
					<span><h5>${person.nationality.countryName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.email" />:</span></label>
					<span><h5>${person.email}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.nic" />:</span></label>
					<span><h5>${person.nic}</h5></span>
				</div>
			
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.phone" />:</span></label>
					<span><h5>${person.phoneNumber}</h5></span>
				</div>
			</div>
			<hr style="border:1px solid blue; border-radius:1px;">
			<span><h4> <fmt:message key="application.details.capacity.infos" /></h4></span>
			<hr style="border:1px solid blue; border-radius:1px;">
			<div class="row">
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.capacity.number" />:</span></label>
					<span><h5>${application.certificate.number}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="application.details.category" />:</span></label>
					<span>
						
<%-- 						<c:forEach var="candidateSession" items="${application.certificate.studentSession}"> --%>
							<h5 style="margin-right:10px">	${application.certificate.studentSession.student.speciality.name }</h5>  
<%-- 						</c:forEach> --%>
						
<%-- 						<c:forEach var="archive" items="${capacity.person.archives}"> --%>
							<h5 style="margin-right:10px">	${application.archive.archiveNumber }</h5>  
<%-- 						</c:forEach> --%>
						
					</span>
				</div>
			</div>
				
			
			<hr style="border:1px solid blue; border-radius:1px;">
			<span><h4> <fmt:message key="application.details.backing.doc" /></h4></span>
			<hr style="border:1px solid blue; border-radius:1px;">
			<div class="row">
				<c:forEach var="applicationFile" items="${application.applicationFiles}">
					<div class="col-sm-3">
						<a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${applicationFile.name}&folderName=application.file.folder">${applicationFile.name }</a> 
                        	<hr>
					</div>
				</c:forEach>
			 </div>					
		</div>
	</div>	
</div>