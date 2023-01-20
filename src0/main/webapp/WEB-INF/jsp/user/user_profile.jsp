<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="${pageContext.request.contextPath}/css/user.css" rel="stylesheet" type="text/css" />




		<!-- begin:: Content Head -->
		<div class="kt-subheader   kt-grid__item" id="kt_subheader">
			<div class="kt-container  kt-container--fluid ">
				<div class="kt-subheader__main">
					<h3 class="kt-subheader__title">
						User Profile Details
					</h3>
					<span class="kt-subheader__separator kt-subheader__separator--v"></span>
					<div class="kt-subheader__group" id="kt_subheader_search">
						<span class="kt-subheader__desc" id="kt_subheader_total">
							My Profile</span>
					</div>
				</div>
			</div>
		</div>

		<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
			<div class="kt-wizard-v4" id="kt_user_add_user" data-ktwizard-state="step-first">
				<div class="kt-portlet">
					<div class="kt-portlet__body kt-portlet__body--fit">
						<div class="kt-grid">
							<div class="kt-grid__item kt-grid__item--fluid kt-wizard-v4__wrapper">
								<div class="kt-wizard-v4__content" data-ktwizard-type="step-content" data-ktwizard-state="current" style="padding-left:13%; padding-top:5%; padding-bottom:5%; padding-right:10%;">
									<div class="kt-heading kt-heading--md">Basic Information</div><hr>
										<div class="kt-section kt-section--first">
											<div class="kt-wizard-v4__form">
												<div class="row">
													<div class="col-sm-4">
                                                     	<div class="kt-widget__media" style="border-radius: 40px" >
                                                           <c:if test="${not empty connectedUser.image}">
                                                               <img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${connectedUser.image}&folderName=user.profile.folder" width="150" height="150" alt="image">
                                                            </c:if>

                                                            <c:if test="${empty connectedUser.image}">

                                                                <img class="kt-widget__img" src="resources/static/img/default_user.png"
                                                                     alt="<fmt:message key="user_profile_pic"/>">

                                                             </c:if>
                                                          </div>
                                                    </div>
													<div class="col-sm-4">
														<span><h5><fmt:message key="fullName" />:</h5></span>
														<span><h4> ${fn:toUpperCase(connectedUser.firstName) } ${fn:toUpperCase(connectedUser.lastName) }</h4>
														<hr>
														
														<span><h5><fmt:message key="dob" />:</h5></span>
														<span><h4> <fmt:formatDate value="${connectedUser.dob }" type="date" pattern="dd-MM-yyyy"/></h4>
														<hr>
														
														<span><h5><fmt:message key="pob" />:</h5></span>
														<span><h4>${fn:toUpperCase(connectedUser.pob)} </h4></span>
														<hr>
														<span><h5><fmt:message key="gender" />:</h5></span>
														<span>
															<h4>
																<c:choose>
                                                                       <c:when test="${connectedUser.gender=='M'}">
                                                                           <fmt:message key="label_gender_male" />
                                                                       </c:when>
                                                                       <c:when test="${connectedUser.gender=='F'}">
                                                                           <fmt:message key="label_gender_female" />
                                                                       </c:when>
                                                                </c:choose>
															</h4>
														</span>
														<hr>
															
													</div>
                                                        	<div class="col-sm-4">
													<span><h5><fmt:message key="userName" />:</h5></span>
													<span><h4> ${connectedUser.username }</h4>
													<hr>
													
													<span><h5><fmt:message key="email" />:</h5></span>
													<span><h4><a href="#"><span><h5>${fn:toLowerCase(connectedUser.email) } </h4>
													<hr>
													
													<span><h5><fmt:message key="phone_number" />:</h5></span>
													<span><h4>${connectedUser.phoneNumber } </h4>
													<hr>
													
													<span><h5><fmt:message key="register_on" />:</h5></span>
													<span><h4> <fmt:formatDate value="${connectedUser.registerOn }" type="date" pattern="dd-MM-yyyy"/></h4>
													<hr>
												</div>
												 
												</div>
												<hr>
												<div class="row">
													<div class="col-md-4">
                                                        <span><h5><fmt:message key="groupName" />:</h5></span>
														<span><h4> ${fn:toUpperCase(connectedUser.group.name) }</h4>
																 
                                                    </div>
													<div class="col-md-4">
                                                        <span><h5><fmt:message key="office" />:</h5></span>
														<span><h4> ${fn:toUpperCase(connectedUser.office.name )}</h4>
													</div>
													<c:if test="${not empty connectedUser.trainingCenter}">
                                                        <div class="col-md-4">
                                                            <span><h5><fmt:message key="student.trainingCenter" />:</h5></span>
                                                            <span><h4> ${fn:toUpperCase(connectedUser.trainingCenter.name )}</h4>
                                                        </div>
                                                    </c:if>

												</div>
													
											</div>
										</div>
										
										<div class="kt-heading kt-heading--md">Roles</div><hr>
										<div class="row">
											<c:forEach var="userRole" items="${connectedUser.roles}">
											
												<div class="col-sm-3">
													<span data-toggle="kt-popover"  ><h6><fmt:message key="${userRole.name}" /></h6></span>
												</div>
                                             </c:forEach>
											 <c:forEach var="groupRole" items="${groupRoles}">
												<div class="col-sm-3">
													<span data-toggle="kt-popover" ><h6><fmt:message key="${groupRole.name}" /></h6></span>
												</div>
												
											</c:forEach>
										</div>
									</div>
							    </div>
							</div>
						</div>
					</div>
				</div>
			</div>
		

		<!-- end:: Content -->
	





<!-- end:: Content -->