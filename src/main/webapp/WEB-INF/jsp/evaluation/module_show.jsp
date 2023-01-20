<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="kt-portlet">
<div class="kt-portlet__head">
	<div class="kt-portlet__head-label">
		<h3 class="kt-portlet__head-title"><fmt:message key="show.module.key"/></h3>
	</div>
</div>
<div class="kt-portlet__body" id="moduleFormId">

	<!--begin::Form-->
<form class="kt-form">
	<div class="kt-portlet__body form-group row">
	   <div class="col-12">
			<label><fmt:message key="module_completeName" /><span style="color:red;">(* requis | required)</span></label>
			<input name="moduleCompleteName" id="moduleCompleteName"  type="text" class="form-control"  value="${module.completeName}"   readonly>
		</div>
		<div class="col-12">
			<label><fmt:message key="module_minNote" /><span style="color:red;">(* requis | required )</span></label>
			<input name="moduleMinNote" id="moduleMinNote"  type="number" class="form-control" value="${module.moduleMinNote}"  readonly >
			<br>
		</div>
		<div class="col-12">
			<label><fmt:message key="module.percentage" /><span style="color:red;">(* requis | required )</span></label>
			<input name="modulePercentage" id="modulePercentage"  type="number" class="form-control" value="${module.modulePercentage}"  readonly >
			<br>
		</div>
		
		<div class="col-12">
			<label><fmt:message key="module_classification" /><span style="color:red;">(* requis | required )</span></label>
			<input name="moduleClassification" id="moduleClassification"  type="number" class="form-control"   value="${module.moduleClassification}" readonly >
			<br>
		</div>

		<div class="col-12">
			<div class="kt-checkbox-inline">
			     <label><fmt:message key="course_Module" /></label>
			     <select id="speciality" class="form-control kt-input" data-col-index="2">
		                <option value="ALL"><fmt:message key="select_one" /></option>
		                <option value="${speciality.abbreviation}" selected>${speciality.abbreviation}</option>
		            
			     </select>
			</div>
		</div>
	</div>
	
    
</form>

<!--end::Form-->
	</div>
</div>


