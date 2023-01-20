<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<option value="action">Action</option>
   <sec:authorize access="hasRole('ROLE_MANAGE_OUTSLIP')">
        <c:if test = "${outSlip.status==1}">
           <option value="validate" ><fmt:message key="app.in.outSlip.action.validate" /></option>
        </c:if>
        <c:if test = "${outSlip.status==2}">
           <option value="reset" ><fmt:message key="app.in.outSlip.action.reset" /></option>
           <option value="deliver" ><fmt:message key="app.in.outSlip.action.deliver" /></option>
        </c:if>
        <c:if test = "${outSlip.status==3}">
           <option value="confirm" ><fmt:message key="app.in.outSlip.action.confirm" /> </option>
        </c:if>
   </sec:authorize>
    <sec:authorize access="hasRole('ROLE_VIEW_OUTSLIP')">
       <option value="printSuccessfully"><fmt:message key="app.in.outSlip.successfully.prints" /></option>
       <option value="printRejected"><fmt:message key="app.in.outSlip.list.rejects" /></option>
    </sec:authorize>
    <option value="history" >history</option>