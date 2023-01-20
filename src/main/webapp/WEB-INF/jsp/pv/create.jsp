<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="kt-portlet">
<div class="kt-portlet__head">
	<div class="kt-portlet__head-label">
		<h3 class="kt-portlet__head-title"><fmt:message key="list.exam.center" /></h3>
	</div>
</div>
<div class="kt-portlet__body" id="eligibleFormId">

	<!--begin::Form-->
<form class="kt-form">
	<div class="form-group row">
		<div class="col-12">
			<div class="kt-checkbox-inline">
			 <c:forEach var="center" items="${centers}"> 
		        <label class="kt-checkbox" cheched="">
					<input  name="center" type="checkbox" value="${center.id}">${center.name}
					<span></span>
				</label>
		     </c:forEach>
				
				
			</div>
		</div>
	</div>
</form>
<form class="kt-form" id="outSlipForm">
	<div class="kt-portlet__body">

		<div class="form-group">
			<label><fmt:message key="session.date" /></label>
			<input name="dates" id="sessionId"  type="text" class="form-control" id="dateOutSlip" placeholder="ex : 24/08/2020 " required >
		</div>
		
	<div class="kt-portlet__foot">
		<div class="kt-form__actions">
			<button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" createEligibleCenter('${pageContext.request.contextPath}');">Submit</button>
		</div>
	</div>
	
</form>
<!--end::Form-->
	</div>
</div>
<script>
	 $('input[name="dates"]').datepicker({'format':'dd/mm/yyyy'});
</script>

