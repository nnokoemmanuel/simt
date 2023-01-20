<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<link href="${pageContext.request.contextPath}/css/user.css" rel="stylesheet" type="text/css" />


<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper" style="padding-top:0px; padding-left:0px;">


		<!-- begin:: Content Head -->
		<div class="kt-subheader   kt-grid__item" id="kt_subheader">
			<div class="kt-container  kt-container--fluid ">
				<div class="kt-subheader__main">
					<h3 class="kt-subheader__title">
						<fmt:message key="archive.details.title" />
					</h3>
					<span class="kt-subheader__separator kt-subheader__separator--v"></span>
					<div class="kt-subheader__group" id="kt_subheader_search">
						<span class="kt-subheader__desc" id="kt_subheader_total">
							<fmt:message key="archive.details.view" /></span>
					</div>
				</div>
			</div>
		</div>

		<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
			<div class="kt-wizard-v4" id="kt_user_add_user" data-ktwizard-state="step-first">
				<div class="kt-portlet">
					<div class="kt-portlet__body kt-portlet__body--fit">
						<div class="kt-grid">
							
								<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" style="padding-left:13%; padding-top:5%; padding-bottom:5%; padding-right:10%;">
									<div class="kt-heading kt-heading--md"><fmt:message key="archive.details.basic.infos" /></div><hr>
										<div class="kt-section kt-section--first">
											<div class="kt-wizard-v4__form row">
											 	
											 	<div class="col-sm-4">
											 	<fmt:message key="archive.details.surname" />: <span><h4> ${fn:toUpperCase(archive.person.surName)}</h4></span>
											 	<hr>
											 	<fmt:message key="archive.details.givenName" />:<span><h4>${fn:toUpperCase(archive.person.givenName) }  </h4></span>
											 	
											 	<hr>
											 	<fmt:message key="archive.details.dob" />:  <span><h4><fmt:formatDate value="${archive.person.dob }" type="date" pattern="dd-MM-yyyy"/></h4></span>
											 	<hr>
											 	
											 	
											 	</div>
											 	<div class="col-sm-4">
											 	<fmt:message key="archive.details.pob" />: <span><h4>${fn:toUpperCase(archive.person.pob) }  </h4></span>
											 	
											 	<hr>
											 	<fmt:message key="archive.details.gender" />: <span><h4>${fn:toUpperCase(archive.person.gender) }  </h4></span>
											 	
											 	<hr>
											 	
											 	<fmt:message key="archive.details.nic" />: <span><h4>${fn:toUpperCase(archive.person.nic) }  </h4></span>
											 	
											 	<hr>
											 	</div>
											 	<div class="col-sm-4">
											 	<fmt:message key="archive.details.phone" />: <span><h4>${archive.person.phoneNumber }  </h4></span>
											 	
											 	<hr>
											 	<fmt:message key="archive.details.email" />: <span><h4>${fn:toLowerCase(archive.person.email) }  </h4></span>
											 	
											 	 <hr>
											 	<fmt:message key="archive.person.matricule" />: <span><h4>${fn:toUpperCase(archive.person.matricule) }  </h4></span> 
											 	
											 	<hr>
											 	<fmt:message key="archive.person.licenseNum" />: <span><h4>${fn:toUpperCase(archive.person.licenseNum) }  </h4></span> 
											 	
											 	<hr>
											 	</div>	
                                                													
												<hr>
											</div>
										</div>
									</div>
							    
							</div>
						</div>
					</div>
					<div class="kt-portlet">
					<div class="kt-portlet__body kt-portlet__body--fit">
						<div class="kt-grid">
							
								<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" style="padding-left:13%; padding-top:5%; padding-bottom:5%; padding-right:10%;">
									<div class="kt-heading kt-heading--md">Archive Information</div><hr>
										<div class="kt-section kt-section--first">
											<div class="kt-wizard-v4__form row">
												<div class="col-sm-4">
												<fmt:message key="archive.details.archive.number" />: <span><h4>${fn:toUpperCase(archive.archiveNumber) } </h4></span>
											 	
											 	<hr>
											 	<fmt:message key="archive.details.archive.status.label" />: <span><h4>
											 	<c:if test="${archive.status == 'REGISTERED'}">
											 	<fmt:message key="archive.details.archive.status.registered" /> </h4></span>
											 	</c:if>
											 	
											 	<c:if test="${archive.status =='VALIDATED'}">
											 	<fmt:message key="archive.details.archive.status.validated" /> </h4></span>
											 	</c:if>
											 	<%-- <c:if test="${archive.status ==3}">
											 	<fmt:message key="archive.details.archive.status.suspended" /> </h4></span>
											 	</c:if>
											 	<c:if test="${archive.status ==4}">
											 	<fmt:message key="archive.details.archive.status.computerized" /></h4></span>
											 	</c:if> --%>
											 	<hr>
												</div>
												<div class="col-sm-4">
												<fmt:message key="archive.details.archive.pv.num" />: <span><h4>${fn:toUpperCase(archive.pvNumber) }  </h4></span>
											 	
											 	<hr>
											 	<fmt:message key="archive.details.archive.issued.at" />: <span><h4>${fn:toUpperCase(archive.issuedPlace) }  </h4></span>
											 	
											 	<hr>
												</div>
												<div class="col-sm-4">
												<fmt:message key="archive.details.registration.date" />:  <span><h4><fmt:formatDate value="${archive.registrationDate }" type="date" pattern="dd-MM-yyyy"/></h4></span>
											 	<hr>
											 	<fmt:message key="archive.details.issued.date" />:  <span><h4><fmt:formatDate value="${archive.issuedDate }" type="date" pattern="dd-MM-yyyy"/></h4></span>
											 	<hr>
												</div>
											 	
                                                 
											</div>
										</div>
									</div>
							    
							</div>
						</div>
					</div>
					
					
					
					<div class="kt-portlet">
					<div class="kt-portlet__body kt-portlet__body--fit">
						<div class="kt-grid">
							
								 <div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" style="padding-left:13%; padding-top:5%; padding-bottom:5%; padding-right:10%;">
									<div class="kt-heading kt-heading--md"><fmt:message key="archive.details.files" /></div><hr>
										<div class="kt-section kt-section--first">
											<div class="kt-wizard-v4__form">
											 	<div class="row col-sm-12">
											 		<c:forEach var="archiveFile" items="${archive.archiveFiles}">
											 		<div class="col-sm-3">
											 		<a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${archiveFile.fileName}&folderName=archive.file.folder">${archiveFile.fileName }</a> 
                                                 	<hr>
											 		</div>
											 		   
									    		</c:forEach>													
												<hr>
											 	</div>
											 	
											</div>
										</div>
									</div> 
							    
							</div>
						</div>
					</div>
				</div>
			</div>




</div>


<!-- end:: Content -->