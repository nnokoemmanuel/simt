<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<option value="action">Action</option>
 <option value="detail">Detail</option>
    <c:if test = "${application.applicationStatus.id == 1 }">
    	<sec:authorize access="hasRole('ROLE_PROCESS_FILE')">
    		<option value="reject" ><fmt:message key="app.action.reject" /></option>
 			<option value="cancel" ><fmt:message key="app.action.cancel" /></option>
 			<option value="edit" ><fmt:message key="app.action.edit" /></option>
      	</sec:authorize>
      	<sec:authorize access="hasRole('ROLE_CONFIRM_PROCESS')">
    		<option value="confirm"><fmt:message key="app.action.confirm" /></option>
      	</sec:authorize>		
    </c:if>
    <c:if test = "${application.applicationStatus.id == 2 }">
    	
      	<sec:authorize access="hasRole('ROLE_PROCESS_FILE')">
    		<option value="cancel" ><fmt:message key="app.action.cancel" /></option>
      		<option value="reset"><fmt:message key="app.action.reset" /></option>
      	</sec:authorize>
      	<sec:authorize access="hasRole('ROLE_CONFIRM_PROCESS')">
    		<option value="confirm_reject"><fmt:message key="app.action.confirm.reject" /></option>
      	</sec:authorize>
    	
    </c:if>
    <c:if test = "${application.applicationStatus.id == 3 }">
    	<sec:authorize access="hasRole('ROLE_PROCESS_FILE')">
    		<option value="reject" ><fmt:message key="app.action.reject" /></option>
      		<option value="reset"><fmt:message key="app.action.reset" /></option>
      	</sec:authorize>
	
    </c:if>
    <c:if test = "${application.applicationStatus.id == 4 }">
        <sec:authorize access="hasRole('ROLE_PRINT_APPLICATION')">
        	<option value="reset"><fmt:message key="app.action.reset" /></option>
  			<option value="print_preview"><fmt:message key="app.action.print" /></option>
  			 
      	</sec:authorize>
    </c:if>
    <c:if test = "${application.applicationStatus.id == 5 }">
    	<sec:authorize access="hasRole('ROLE_PRINT_APPLICATION')">
        	<option value="print_report" ><fmt:message key="app.action.print.report" /></option>
      	</sec:authorize>
      
    </c:if>
    <c:if test = "${application.applicationStatus.id == 6 }">
    
    </c:if>
    <c:if test = "${application.applicationStatus.id == 7 }">
    	<sec:authorize access="hasRole('ROLE_PRINT_APPLICATION')">
        	<option value="reset"><fmt:message key="app.action.reset" /></option>
      		<option value="deliver_with_reject"><fmt:message key="app.action.deliver.with.reject" /></option>
      	</sec:authorize>
      
    </c:if>
    <c:if test = "${application.applicationStatus.id == 8 }">
    	<sec:authorize access="hasRole('ROLE_PRINT_APPLICATION')">
        	<option value="request_reprint" ><fmt:message key="app.action.request.reprint" /></option>
      	</sec:authorize>
      
    </c:if>
    <c:if test = "${application.applicationStatus.id == 9 }">
      
    </c:if>
    <c:if test = "${application.applicationStatus.id == 10 }">
      
    </c:if>
    <c:if test = "${application.applicationStatus.id == 11 }">
    	<sec:authorize access="hasRole('ROLE_PRINT_APPLICATION')">
        	<option value="grant_reprint"><fmt:message key="app.action.grant.reprint" /></option>
      	</sec:authorize>
       
    </c:if>

 <option value="history" ><fmt:message key="app.action.history" /></option>



