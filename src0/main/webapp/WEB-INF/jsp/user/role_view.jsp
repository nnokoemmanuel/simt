<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="kt-heading kt-heading--md"><fmt:message key="select_the_users_role" /></div>
	<div class="kt-form__section kt-form__section--first">
		<div class="kt-wizard-v4__form">
			<c:forEach var="role" items="${roless}">
                <div style="display:inline-block;margin-bottom:5px;margin-left:5px">
                   <p><input type="checkbox" rolecheck="" name="role" value="${role.id}" style="display: inline-block;"
                   
                   		<c:forEach var="roleObj" items="${groupRoles}">
                                                                                    		<c:choose>
                                                                                    			<c:when test="${roleObj eq role.id }">
                                                                                    			
                                                                                    			checked
<%-- 																					         		<p><input type="checkbox" id="role" name="role" value="${role.id}" checked/>${role.name} <i class="fa fa-fw fa-sign-out pull-right"></i></p> --%>
<%-- 																					      			<c:set var="role" value="${role.name}" /> --%>
																					      		</c:when>
																					      		<c:otherwise>
																					      		
<%-- 																					         		<p><input type="checkbox" id="role" name="role" value="${role.id}"/>${role.name} <i class="fa fa-fw fa-sign-out pull-right"></i></p> --%>
<%-- 																					      			<c:set var="role" value="${role.name}" /> --%>
																					      		</c:otherwise>
																							</c:choose>
																					    </c:forEach>
                   
                   
                   
                   
                   
                   
                   />${role.name}<i class="fa fa-fw fa-sign-out pull-right"></i></p>
					<c:set var="role" value="${role.name}" />
				</div>	
			</c:forEach>
		</div>
	</div>
													
<script src="${pageContext.request.contextPath}/assets/js/pages/custom/user/add-user.js" type="text/javascript"></script>
	