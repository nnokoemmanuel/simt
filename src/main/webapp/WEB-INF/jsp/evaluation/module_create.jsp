<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="kt-portlet">
    <div class="kt-portlet__head">
        <div class="kt-portlet__head-label">
            <h3 class="kt-portlet__head-title"><fmt:message key="create.module.key"/></h3>
        </div>
    </div>
    <div class="kt-portlet__body" id="moduleFormId">

            <!--begin::Form-->
        <form class="kt-form form-validation"  id="moduleFORMIDs">
            <div class="kt-portlet__body form-group row">
                <div class="col-md-12 control-label">
                    <label><fmt:message key="module_completeName" /><span style="color:red;">*</span></label>
                    <input name="moduleCompleteName" id="moduleCompleteName"  type="text" class="form-control"  >
                </div>

                <div class="col-md-12 control-label">
                    <label><fmt:message key="module_minNote" /><span style="color:red;">*</span></label>
                    <input name="moduleMinNote" id="moduleMinNote"  type="number" class="form-control"   >
                    <br>
                </div>
                <div class="col-md-12 control-label">
                    <label><fmt:message key="module.percentage" />(%)<span style="color:red;">*</span></label>
                    <input name="modulePercentage" id="modulePercentage"  type="number" class="form-control"   >
                    <br>
                </div>
                <div class="col-md-12 control-label">
                    <label><fmt:message key="module_classification" /><span style="color:red;">*</span></label>
                    <input name="moduleClassification" id="moduleClassification"  type="number" class="form-control"   >
                    <br>
                </div>

                <div class="col-md-12 control-label">
                    <div class="kt-checkbox-inline">
                         <label><fmt:message key="module_Speciality" /></label>
                         <select id="moduleSpeciality" class="form-control kt-input" data-col-index="2" required>
                                <option value=""><fmt:message key="select_one" /></option>
                                <c:forEach var="speciality" items="${specialities}">
                                    <option value="${speciality.abbreviation}">${speciality.abbreviation}</option>
                                </c:forEach>
                         </select>
                    </div>
                </div>
            </div>

             <div class="kt-portlet__foot">
                <div class="kt-form__actions">
                    <button  style="margin-bottom:20px"type="button" class="btn btn-primary" id="submitMODULEBUTTONIDs" onClick="moduleBootstrapValidation('${pageContext.request.contextPath}',0);"><fmt:message key="save.key"/></button>
                </div>
            </div>
        </form>

        <!--end::Form-->
	</div>
</div>


