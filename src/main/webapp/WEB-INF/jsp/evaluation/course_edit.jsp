<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="kt-portlet">
<div class="kt-portlet__head">
	<div class="kt-portlet__head-label">
		<h3 class="kt-portlet__head-title"><fmt:message key="edit.course.key"/></h3>
	</div>
</div>
<div class="kt-portlet__body" id="courseFormId">

	<!--begin::Form-->
<form class="kt-form">
	<div class="kt-portlet__body form-group row">
	   <div class="col-12">
			<label><fmt:message key="course_completeName" /><span style="color:red;">(* requis | required)</span></label>
			<input name="courseCompleteName" id="courseCompleteName"  type="text" class="form-control"  value="${course.completeName}" required >
		</div>
		<div class="col-12">
			<label><fmt:message key="course_coefficient" /><span style="color:red;">(* requis | required)</span></label>
			<input name="courseCoefficient" id="courseCoefficient"  value="${course.courseCoefficient}" type="number" class="form-control" value="0"  >
		</div>
	
		<div class="col-12">
			<label><fmt:message key="course_max_note" /></label>
			<input name="courseMaxNote" id="courseMaxNote" value="${course.courseMaxNote}" type="text" class="form-control" id="dateOutSlip" min="0" max="20" value="20" >
		</div>
		<div class="col-12">
			<div class="kt-checkbox-inline">
			     <label><fmt:message key="course_Module" /></label>
			     <select id="courseModule" class="form-control kt-input" data-col-index="2">
		                <option value="0"><fmt:message key="select_one" /></option>
		                <c:forEach var="module" items="${modules}">
		                    <c:if test="${course.module.id ==  module.id}">
		                    	<option value="${module.id}" selected>[${module.speciality.abbreviation}]-${module.completeName}</option>
		                    </c:if>
		                     <c:if test="${course.module.id !=  module.id}">
		                    	<option value="${module.id}">[${module.speciality.abbreviation}]-${module.completeName}</option>
		                    </c:if>
	
		                </c:forEach>
			     </select>
			</div>
		</div>
	</div>
	
     <div class="kt-portlet__foot">
		<div class="kt-form__actions">
			<button  style="margin-bottom:20px"type="button" class="btn btn-primary" onClick=" createCourse('${pageContext.request.contextPath}','${course.id}');"><fmt:message key="save.key"/></button>
		</div>
	</div>
</form>

<!--end::Form-->
	</div>
</div>


