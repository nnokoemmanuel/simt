<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<option value="action">Action</option>
 <sec:authorize access="hasRole('ROLE_OPEN_INSLIP')">

 		<c:if test = "${inSlip.inSlipStatus.id==2 }">
	      <option value="reset">Reset</option>
	    </c:if>
	    <c:if test = "${inSlip.inSlipStatus.id==1}">
	      <option value="open" >Open</option>
	      <option value="edit">Edit</option> 
	    </c:if>


    </sec:authorize>
 
 <option value="history" >history</option>
 <option value="detail">Detail</option>