<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<option>Action</option>

   <sec:authorize access="hasRole('ROLE_VIEW_ARCHIVE') ">
      <option onclick="openNav('Archive Details', '', '80%');doGet('${pageContext.request.contextPath}/archive/detail?id=${archive.id}','contenu');active_link('archive-pv');return false;"><fmt:message key="action.details" /></option>
   </sec:authorize>
   
   <sec:authorize access="hasRole('ROLE_ARCHIVE_CONTROLLER') ">
	  <c:if test="${archive.status == 'REGISTERED' }">
         <option onClick="openNav('Validate Archive', '', '80%');doGet('${pageContext.request.contextPath}/archive/validate/detail?id=${archive.id}','contenu');active_link('archive-pv');return false;"><fmt:message key="archive.action.validate" /></option>
      </c:if>
       <c:if test="${archive.status == 'VALIDATED'}">
          <option onClick="resetArchive('${pageContext.request.contextPath}/archive/update/invalidate/${archive.id}','achiveTableContent');active_link('archive-pv');return false;"><fmt:message key="archive.action.invalidate" /></option>
       </c:if>
       

    </sec:authorize>
   
    <sec:authorize access="hasRole('ROLE_MANAGE_ARCHIVE') ">
      <c:if test="${archive.status == 'REGISTERED' }">
         <option onClick="openNav('', '', '70%');doGet('${pageContext.request.contextPath}/archive/actionEdit/${archive.id}','contenu');active_link('archive-capacity');return false;"><fmt:message key="action.edit" /></option>
         <option onClick="deleteArchive('${pageContext.request.contextPath}/archive/update/delete/${archive.id}', 'achiveTableContent');"><fmt:message key="action.delete" /></option>
      </c:if>
    </sec:authorize>
    
       <option onClick="openNav('Advanced Archive Tracking | History', '', '40%');doGet('${pageContext.request.contextPath}/simt/tracking/?entity=archive&id=${archive.id}','contenu');active_link('archive-pv');return false;"><fmt:message key="archive.action.history" /></option> 
 
