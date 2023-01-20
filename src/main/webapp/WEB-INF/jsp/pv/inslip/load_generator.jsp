<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="kt-portlet">
<div class="kt-portlet__head">
	<div class="kt-portlet__head-label">
		<h3 class="kt-portlet__head-title"><fmt:message key="create.generate.inslip.type.key"/></h3>
	</div>
</div>
<div class="kt-portlet__body" id="eligible-inslip_FORMID">

	<!--begin::Form-->
<form class="kt-form" id="eligible-inslip">
	<div class="kt-portlet__body form-group row">
	   <div class="col-md-12">
			<label><fmt:message key="app.exam.center" /></label>
			<input name="eligible-center" id="eligible-center" value="${fn:toUpperCase(eligibleCenter.examCenter.name) }" type="text" class="form-control"  readonly >
		</div>
		<div class="col-md-12">
			<label><fmt:message key="office" /></label>
			<input name="office" id="office"  type="text" class="form-control" readonly value="${connectedUser.office.name }" >
		</div>
		
		<div class="col-md-12">
			<div class="kt-checkbox-inline">
			     <label><fmt:message key="label.inslip.type" /></label>
			     <select id="inslip-type" class="form-control kt-input" data-col-index="2" required onchange="ValidateChoice();">

		                <option value="0"><fmt:message key="select_one" /></option>
		                <option value="T"><fmt:message key="option.inslip.transcript" /></option>
		                <option value="C"><fmt:message key="option.inslip.certificate" /></option>
		                <option value="P"><fmt:message key="option.inslip.professional.card" /></option>
		                
			     </select>
			     <span style="color:red" id="error-inslip-type"></span>
			</div>
		</div>
	</div>
	
     <div class="kt-portlet__foot">
		<div class="kt-form__actions">

			<button  style="margin-bottom:20px"type="button" class="btn btn-primary" id="submitINSLIPBUTTONID" onClick="inSlipBootstrapValidation('${pageContext.request.contextPath}/pv/generate_inslip/${eligibleCenter.id}/');"><fmt:message key="generate.key"/></button>

		</div>
	</div>
</form>

<!--end::Form-->
<!--<button  style="margin-bottom:20px"type="button" class="btn btn-primary" id="submitINSLIPBUTTONID" onClick="validateFormErrorMessage();inSlipBootstrapValidation('${pageContext.request.contextPath}/pv/generate_inslip/${eligibleCenter.id}/');"><fmt:message key="generate.key"/></button>
 -->
	</div>
</div>

<script type="text/javascript">
	
function ValidateChoice(){
	var inSlipType = document.getElementById("inslip-type");
	//Validation 1
	var dropdownSelectOption = inSlipType.options[inSlipType.selectedIndex].value;
	
	//Validation 1
	//if you need text to be compared then use
	//var strUser1 = e.options[e.selectedIndex].text;
	
	if(dropdownSelectOption==0 || dropdownSelectOption=="") //for text use if(strUser1=="Select")
	{
	    //alert("Display Error Message : Please Select Inslip Type");
	    document.getElementById('error-inslip-type').innerHTML = " Required field / Champ requis *"
	    swal.fire(
	    		'Form validation error',
	    		'Please Select Inslip Type !.',
	    		'error'
	        )
		return;
	}else{
    	//alert("Remove Error Message :");
    	//document.getElementById("error-inslip-type").style.display = 'none';
    	document.getElementById('error-inslip-type').innerHTML = " ";
    }
}


/* function validateFormErrorMessage(){
	
	var inSlipType = document.getElementById("inslip-type");
	 var dropdown_Display_ErrorMessage = inSlipType.options[inSlipType.selectedIndex].value;
	
	if (dropdown_Display_ErrorMessage==0 || dropdown_Display_ErrorMessage=="") {
        document.getElementById('error-inslip-type').innerHTML = " Required field / Champ requis *";
    }else{
    	document.getElementById('error-inslip-type').innerHTML = " ";
    }
} */


</script>
