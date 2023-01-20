<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" /> 
<div class="kt-portlet kt-portlet--mobile">
	<div class="kt-subheader-search " style="background:#80ad71;">
		<div class="kt-container  kt-container--fluid ">
			<h6 class="kt-subheader-search__title">
				<fmt:message key="students.lists.management" />
			</h6>
		</div>
	</div>
  <div class="kt-container  kt-container--fluid ">

    <div class="form-group col-sm-3">
		<label for="activate" class="  col-sm-9 control-label">
			<fmt:message key="search.per.license.or.referenceNumber" />
		</label>
		<div class="col-sm-9">
			<label for="activate"> <input id="activate"
				name="activate" type="checkbox"
				class="pos-rel p-l-30 custom-checkbox" value="1">
				&nbsp;
			</label>
		</div>
	</div>

    <!--begin: Search Form -->

	<form id ="form" class="kt-form kt-form--fit kt-margin-b-20">

	    <div id="block2" style="display:none;">
		    <div class="row kt-margin-b-20">
	            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
					<label><fmt:message key="numero.permis.and.matricule" />:</label>
					<input id="licenseNumber" type="text" class="form-control kt-input" placeholder="NW-101256-09" data-col-index="0">
				</div>
			
	
	            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
	               <center>
	                   <button id="search-candidate" style="width: 50%; margin-top:20px; background-color: #80ad71; border-color: #80ad71; " type="button" class="btn btn-success" title="search Candidates" onClick="searchtrainingCenterCandidateList('${trainingCenter.id }');">
	                   <i class="la la-search"></i>
	                   <fmt:message key="search_msg" />
	                   </button>
	               </center>
			    </div>
			  </div>
	
        </div>
        <div id="block1">
      
            <hr style="border-color: #80ad71; ">
            <span style="font-weight:bold;color:#80ad71;"><fmt:message key="search_msg_students_title" /></span>
            <br>
	        <div class="row kt-margin-b-20" id="searchStudents">
	            <div class="col-lg-3">
	                <label style="color:#564FC1;"><fmt:message key="plage.candidate.temps_file" /></label>
	                <input name="datefilter" type="text" class="form-control" id="dateRangeId" placeholder="Select time">
	            </div>
	            <div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
					<label><fmt:message key="candidates.statuses" />:</label>
					<select id="statusStudents" class="form-control kt-input" data-col-index="2">
	                    <!--<option value="-1"><fmt:message key="all.statuses" /></option>-->
	                    <option value=""><fmt:message key="select.statuses" /></option>
	                    <option value="0"><fmt:message key="registered.statuses" /></option>

					</select>
				</div>
			
				<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
	               <center>
	                   <button id="searchStudentButton" style="width: 50%; margin-top:20px; background-color: #80ad71; border-color: #80ad71; " type="button" class="btn btn-success" title="search Candidates"  onClick="searchtrainingCenterCandidateList('${trainingCenter.id }');">
	                   <i class="la la-search"></i>
	                   <fmt:message key="search_msg_students" />
	                   </button>
	               </center>
				</div>
			  </div>
			  
			 
            <hr style="border-color: #80ad71; ">
			  <span style="font-weight:bold;color:#80ad71;"><fmt:message key="search_msg_candidate_title" /></span>
			  <br>
			  <div class="row kt-margin-b-20" id="searchCandidates">

	            <div class="col-lg-2 kt-margin-b-10-tablet-and-mobile">
					<label><fmt:message key="candidates.statuses" />:</label>
					<select onchange="getval3(this);" id="statusCandidates" class="form-control kt-input" data-col-index="2">
	                    <option value=""><fmt:message key="select.statuses" /></option>
	                    <option value="1"><fmt:message key="presented.statuses" /></option>
	                    <option value="2"><fmt:message key="approved.statuses" /></option>
	                    <option value="3"><fmt:message key="rejected.statuses" /></option>
					</select>
				</div>
				<div class="col-lg-2 kt-margin-b-10-tablet-and-mobile">
					<label><fmt:message key="open.sessions" />:</label>
	                <select onchange="getval2(this);" id="eligibleCenterIdl" class="form-control kt-input" data-col-index="2">
	                    <option value="0"><fmt:message key="option_all" /></option>
	                    <c:forEach var="eligibleCenter" items="${listOpenEligibleCenters}">
	                       <fmt:formatDate value="${eligibleCenter.examSession.sessionDate}" pattern="dd/MM/yyyy" var="sessionDate" />
	                        <option value="${eligibleCenter.id }">${eligibleCenter.examCenter.name} - ${sessionDate}</option>
	                    </c:forEach>
					</select>
				</div>
                <div class="col-lg-2 kt-margin-b-10-tablet-and-mobile">
					<label><fmt:message key="trainingCenter_Specialities" />:</label>
					<select onchange="getval1(this);" id="specialityCandidates" class="form-control kt-input" data-col-index="2">
	                    <option value="0"><fmt:message key="select.all" /></option>
	                    <option value="1">MAE</option>
	                    <option value="2">IPCSR</option>
	                    <option value="3">DPCSR</option>
					</select>
				</div>
				<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile">
	               <center>
	                   <button id="searchCandidateButton" style="width: 50%; margin-top:20px; background-color: #80ad71; border-color: #80ad71; " type="button" class="btn btn-success" title="search Candidates" onClick="searchButtonCandidateList();">
	                   <i class="la la-search"></i>
	                   <fmt:message key="search_msg_candidates" />
	                   </button>
	               </center>
				</div>

				<div id="printCandidateList" class="col-lg-3 kt-margin-b-10-tablet-and-mobile" style="display:none;">
                   <center>
                       <button  style="width: 50%; margin-top:20px; background-color: #80ad71; border-color: #80ad71; " type="button" class="btn btn-success" title="list Candidates" onCLick="printList();return false;">
                       <i class="la la-file-pdf-o"></i>
                       <fmt:message key="print.candidates.presenter" />
                       </button>
                   </center>
                </div>
			  </div>
			    <hr style="border-color: #80ad71; ">
          
        </div>

		<div class="kt-separator kt-separator--md kt-separator--dashed"></div>
	</form>
  </div>

<!-- begin:: Content -->
<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
   <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">
    		<div class="kt-portlet__body">
                <!--begin: Datatable -->
    		    <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1650px;">
				  <thead style="background:#01A8F9;">
					<tr 1 style="color:white"  role="row">
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">NO</th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 80.25px;" aria-label="Country: activate to sort column ascending"><fmt:message key="student.surname" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" aria-label="Ship City: activate to sort column ascending"><fmt:message key="student.givenName" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 188.25px;" aria-label="Ship Address: activate to sort column ascending"><fmt:message key="student.speciality" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 250.25px;" aria-label="Company Agent: activate to sort column ascending"><fmt:message key="student.matricule" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 160.25px;" aria-label="Company Name: activate to sort column ascending"><fmt:message key="student.computerizationDate" /></th>
                        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" aria-label="Ship Date: activate to sort column ascending">ACTIONS</th>
					</tr>
				  </thead>
				  <tbody>
				           <c:if test="${listType == 'Student'}">
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="student" items="${listToDisplay}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd" id="${student.id}">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${student.person.surName}</td>
                                        <td>${student.person.givenName}</td>
                                        <td>${student.speciality.abbreviation}</td>
                                        <td>${student.referenceNum}</td>
                                        <fmt:formatDate value="${student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${student.id}','student');return false;" class="form-control input-sm">
                                                 <%@include file="training_center_action_student.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even" id="${student.id}">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${student.person.surName}</td>
                                        <td>${student.person.givenName}</td>
                                        <td>${student.speciality.abbreviation}</td>
                                        <td>${student.referenceNum}</td>
                                        <fmt:formatDate value="${student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${student.id}','student');return false;" class="form-control input-sm">
                                                 <%@include file="training_center_action_student.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            </c:if>
                            <c:if test="${listType == 'StudentSession'}">
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="candidate" items="${listToDisplay}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd" id="${candidate.student.id }">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${candidate.student.person.surName}</td>
                                        <td>${candidate.student.person.givenName}</td>
                                        <td>${candidate.student.speciality.abbreviation}</td>
                                        <td>${candidate.student.referenceNum}</td>
                                        <fmt:formatDate value="${candidate.student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${candidate.id}','studentSession');return false;" class="form-control input-sm">
                                                 <%@include file="training_view_action_candidate.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even" id="${candidate.student.id }">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td>${candidate.student.person.surName}</td>
                                        <td>${candidate.student.person.givenName}</td>
                                        <td>${candidate.student.speciality.abbreviation}</td>
                                        <td>${candidate.student.referenceNum}</td>
                                        <fmt:formatDate value="${candidate.student.computerizationDate}" pattern="dd/MM/yyyy hh.mm,s" var="computerizationDate" />
                                        <td>${computerizationDate}</td>
                                        <td >
                                            <select onchange="manage_action(this.value,'${candidate.id}','studentSession');return false;" class="form-control input-sm">
                                                 <%@include file="training_view_action_candidate.jsp" %>
                                            </select>
                                        </td>
                                    </tr>
                            	</c:if>
                            	<c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                            </c:if>
                  </tbody>
			    </table>
                <!--end: Datatable -->
    		</div>
    	</div>

</div>
<!-- end:: Content -->

<script>

$("input[type=checkbox][name=activate]").change(function(){
    if(this.checked){

          document.getElementById("block2").style.display = "block";
          document.getElementById("block1").style.display = "none";
          document.getElementById("form").reset();
    }else if(!this.checked){

          document.getElementById("block1").style.display = "block";
          document.getElementById("block2").style.display = "none";
          document.getElementById("form").reset();
    }
});

</script>

<script>

function getval1(sel)
{
    //alert(sel.value);

        var status= document.getElementById("statusCandidates").value;
        var eligibleCenterId = document.getElementById("eligibleCenterIdl").value;
        if(status!= "" && eligibleCenterId != 0 && sel.value != 0 ){
            document.getElementById("printCandidateList").style.display = "block";

        }else{
             document.getElementById("printCandidateList").style.display = "none";

        }

}

function getval2(sel)
{
    //alert(sel.value);

        var status= document.getElementById("statusCandidates").value;
         var speciality= document.getElementById("specialityCandidates").value;
        if(status!= "" && speciality != 0 && sel.value != 0 ){
            document.getElementById("printCandidateList").style.display = "block";

        }else{
             document.getElementById("printCandidateList").style.display = "none";

        }

}

function getval3(sel)
{
   // alert(sel.value);

        var speciality= document.getElementById("specialityCandidates").value;
        var eligibleCenterId = document.getElementById("eligibleCenterIdl").value;
        if(speciality!= 0 && eligibleCenterId != 0 && sel.value != 0 ){
            document.getElementById("printCandidateList").style.display = "block";

        }else{
             document.getElementById("printCandidateList").style.display = "none";

        }

}

function printList()
{
    var speciality= document.getElementById("specialityCandidates").value;
    if(speciality==1){
        speciality= "MAE";
    }else if (speciality==2){
        speciality= "IPCSR";
    }else{
        speciality= "DPCSR";
    }
    var eligibleCenterId = document.getElementById("eligibleCenterIdl").value;
    printCandidatesList(eligibleCenterId,'candidates_list_presented_print@'+speciality);
}
</script>


<script >
$(function() {

  $('input[name="datefilter"]').daterangepicker({
      autoUpdateInput: false,
      locale: {
          cancelLabel: 'Clear'
      }
  });

  $('input[name="datefilter"]').on('apply.daterangepicker', function(ev, picker) {
      $(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
  });

  $('input[name="datefilter"]').on('cancel.daterangepicker', function(ev, picker) {
      $(this).val('');
  });

});

</script>
