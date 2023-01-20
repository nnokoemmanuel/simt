<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="kt-portlet">
			<div class="row">
				
				<div class="col-sm-3">
					<label><span><fmt:message key="student.given.name" /> : </span></label>
					<span><h5>${student.person.surName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="student.given.surname" /> : </span></label>
					<span><h5>${student.person.givenName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="pob" /> : </span></label>
					<span><h5>${student.person.pob}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="dob" /> : </span></label>
					<span><h5>${student.person.dob}</h5></span>
				</div>
             </div>
	<hr style="border:1.5px solids;height:1.px;width:95%; border-radius:1px;">
	
	<div class="row">
				
				<div class="col-sm-3">
					<label><span><fmt:message key="gender" /> : </span></label>
					<span><h5>${student.person.gender}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="student.specialitys" />: </span></label>
					<span><h5>${student.speciality.abbreviation}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="student.trainingCenter" /> : </span></label>
					<span><h5>${student.trainingCenter.name}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="registered.on" />: </span></label>
					<span><h5>${student.computerizationDate}</h5></span>
				</div>
             </div>
             
             <hr style="border:1.5px solids;height:1.px;width:95%; border-radius:1px;">
		
	          <div class="row">
				
				<div class="col-sm-3">
					<label><span><fmt:message key="person.licenseNum" /> : </span></label>
					<span><h5>${student.person.licenseNum}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="catb.session.date" />: </span></label>
					<span><h5>${CatB_Date}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span>Matricule : </span></label>
					<span><h5>${student.person.matricule}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span style="font-weight: bold;">Categories : </span></label>
					<span><h3 style="font-weight: bold;">${categories}</h3></span>
				</div>

             </div>
                <hr style="border:1.5px solids;height:1.px;width:95%; border-radius:1px;">
             <div class="row">
	
				<div class="col-sm-3">
					<label><span><fmt:message key="person.nationality" /></span></label>
					<span><h5>${student.person.nationality.countryName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="person.phoneNumber" /> </span></label>
					<span><h5>${student.person.phoneNumber}</h5></span>
				</div>
				
				
             </div>
             <c:if test="${isActifSession=='no'}">
             <hr style="border:1.5px solids;height:1.px;width:95%; border-radius:1px;">
            
             <!--  <div class="row">
	                  <div class="col-sm-6">
						  <span style="margin-top:20px; text-align:justify;color:red">
							 <h5> <fmt:message key="prerequise.not.respected" /></h5>
						  </span>
					  </div>  
			      </div>  -->

             </c:if>
             
         <c:if test="${isActifSession=='yes'}">    
		 <div id="blockRegistration">
            
                <span style="margin-top:20px; text-align:center;color:black">
                <h3> <fmt:message key="registration.info" /></h3>
                </span>
			
                <hr class="app-color" style="border:1.5px solid;height:1.px;width:95%; border-radius:1px;">
				
                <div class="row">
                    <div class="col-sm-3">
                        <h4><fmt:message key="session.opened" /> :</h4>
                        <span class="pull-left"><h3 style="color:red"><fmt:formatDate pattern = "dd-MM-yyyy"
                         value = "${session.sessionDate}" /></h3></span>
                    </div>

                    <div class="col-sm-3" id="loading">
                        <h4>Centre</h4>
                        <select name="center"  id="center" class="form-control" onchange="validateForm(this)">
                            <option value="0"><fmt:message key="select.one"/></option>
                            <c:forEach var="eligiblecenter" items="${eligibleCenters}">
                            <c:if test="${eligiblecenter.eligibleCenterStatus.id == 2}">
                               <option value="${eligiblecenter.id }">${eligiblecenter.examCenter.name}</option>
                            </c:if>
                            </c:forEach>
                        </select>

                    </div>

                    <div class="col-sm-6">

                        <div class="row">

                                <c:if test="${ messageResult == 'to_presented_mae'}">
                                  <div class="col-sm-4 "></br>
                                       <h4><label for="mae" class="pull-right"> MAE</label> </h4>
                                      <input class="pull-right" type="checkbox" id="mae" onclick ="validateFormChechBox();" name="mae" value="mae">
                                      <br>
                                  </div>
                                </c:if>
                                 <c:if test="${  messageResult == 'already_has_mae' }">
                                   <div class="col-sm-6 "></br>
                                        <h4><label for="mae" class="pull-right" style="color:blue"> <fmt:message key="already.has.mae"/> </label> </h4>
                                       <br>
                                   </div>
                                 </c:if>
                                 <c:if test="${  messageResult == 'pending_session' }">
                                    <div class="col-sm-6 "></br>
                                         <h4><label for="mae" class="pull-right" style="color:blue"><fmt:message key="pending.session"/></label> </h4>
                                        <br>
                                    </div>
                                 </c:if>

                                 <c:if test="${ messageResult == 'to_presented_ipcsr'}">
                                      <div class="col-sm-4" ></br>
                                          <h4><label for="ipcsr" class="pull-right"> IPCSR</label> </h4>
                                         <input class="pull-right" type="checkbox" id="ipcsr" onclick ="validateFormChechBox();" name="ipcsr" value="ipcsr">
                                         <br>
                                      </div>
                                 </c:if>
                                 <c:if test="${messageResult == 'already_has_ipcsr'}">
                                    <div class="col-sm-6" ></br>
                                        <h4><label for="ipcsr" class="pull-right" style="color:blue"> <fmt:message key="already.has.ipcsr"/></label> </h4>
                                       <br>
                                    </div>
                                 </c:if>

                                  <c:if test ="${messageResult == 'experts_cat_required'}">
                                    <div class="col-sm-6 "></br>
                                        <h4><label for="mae" class="pull-right" style="color:red"><fmt:message key="experts.cat.required"/></label> </h4>
                                       <br>
                                    </div>
                                  </c:if>

                                 <c:if test="${ messageResult == 'to_presented_dpcsr'}">
                                      <div class="col-sm-4" ></br>
                                          <h4><label for="dpcsr" class="pull-right"> DPCSR</label> </h4>
                                         <input class="pull-right" type="checkbox" id="dpcsr" onclick ="validateFormChechBox();" name="dpcsr" value="dpcsr">
                                         <br>
                                      </div>
                                 </c:if>
                                 <c:if test="${messageResult == 'already_has_dpcsr'}">
                                    <div class="col-sm-6" ></br>
                                        <h4><label for="ipcsr" class="pull-right" style="color:blue"><fmt:message key="already.has.dpcsr"/></label> </h4>
                                       <br>
                                    </div>
                                 </c:if>

                                 <c:if test ="${messageResult == 'number_of_years_not_reach'}">
                                     <div class="col-sm-6 "></br>
                                         <h4><label for="mae" class="pull-right" style="color:red"><fmt:message key="number.of.years.not.reach"/></label> </h4>
                                        <br>
                                     </div>
                                 </c:if>
                        </div>
                    </div>
                </div></br></br>
                <div class="row">

                    <div class="col-sm-4">
                     </div>
                     <div class="col-sm-4">
                     </div>
                  <div class="col-sm-4  pull-right">

                      <button onclick="persist('${student.id}','${ messageResult}' )" id="registerBtn" disabled type="button" class="btn app-color pull-right" style="margin-right:20px">
                                   <fmt:message key="register" />
                      </button>
                  </div>
                </div>

		</div>
		</c:if>
		
		
		  <c:if test="${not empty student.studentSessions }">
                <span style="margin-top:20px; text-align:center;color:black">
                <h3><fmt:message key="session.passed"/></h3>
                </span>

                 <hr class="app-color" style="border:1.5px solid;height:1.px;width:95%; border-radius:1px;">
                <c:forEach var="session" items="${student.studentSessions}">

                    <div class="row">

                        <div class="col-sm-3">
                            <h4> <fmt:message key="session.examDate"/></h4>
                            <span> ${session.eligibleCenter.examSession.sessionDate}</span>
                        </div>

                        <div class="col-sm-3">
                            <h4><fmt:message key="session.examPlace"/></h4>
                            <span> ${session.eligibleCenter.examCenter.name}</span>
                        </div>
                        <div class="col-sm-3">
                            <h4><fmt:message key="session.examResul"/></h4>
                            <span> ${session.studentSessionStatus.description}</span>

                        </div>

                        <div class="col-sm-3">
                            <h4 style="color:blue"><fmt:message key="application.details.category"/></h4>
                            <span style="color:blue"> ${session.speciality.abbreviation}</span>

                        </div>

                    </div>

                </c:forEach>

            </c:if>




    <br><br>
	<hr class="app-color" style="border:1.5px solid;height:1.px;width:95%; border-radius:1px;">

     <div class="kt-portlet">
		   <div class="form-group row">	
					
				<div class="col-lg-4">	
						<c:if test="${studentSession_Status == 'OK' }">	
							<!-- Preview Photo :: Student Photo  -->
							<h4>Photo</h4>
							<img src="${pageContext.request.contextPath}/file/download?fileName=${photo}&folderName=studentSession.photo.folder" src="resources/static/images/default_candidate.png"  style="height:100px; width:100px;">
							
							<br>
							
							<!-- Download Photo :: Student Photo  -->
							<%-- <a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${photo}&folderName=studentSession.file.photo.folder">
							     <i class="fa fa-download" aria-hidden="true"></i>
						     <fmt:message key="label_Downloadlink_Student_Photo" />
							 </a> --%>
							   <!-- Download pdf :: Student Diplome  -->
							 <a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${student.id}.pdf&folderName=studentSession.file.folder"><i class="fa fa-download" aria-hidden="true"></i>
							     <fmt:message key="label_Downloadlink_Student_Diplome" />
							 </a>
							 
						</c:if>	
						<c:if test="${studentSession_Status != 'OK'}">
						   <!-- Preview Photo :: Default Photo  -->
							<h4>Photo</h4>
						    <img src="resources/static/images/default_candidate.png" class=" img-responsive img-circle" alt="Candidate picture"  style="height:100px; width:100px;">
						    <!-- Preview Photo :: Default Signature  -->
							
						</c:if>
			</div>	
						
						
			<div class="col-lg-4">
						<c:if test="${not empty student.studentSessions }">			 
								<div class="col-lg-4">	 
									 <!-- Preview Signature :: Student Signature  -->
									 	<h4>Signature</h4>
									 <img src="${pageContext.request.contextPath}/file/download?fileName=${student.id}.jpg&folderName=studentSession.signature.folder" style="height:100px; width:100px;">
									 
									 <br>
									 
									 <!-- Download Signature :: Student Signature  -->
									 <a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${student.id}.jpg&folderName=studentSession.signature.folder">
									     <i class="fa fa-download" aria-hidden="true"></i>
									     <fmt:message key="label_Downloadlink_Student_Signature" />
									 </a>
								</div>	 
									 
						</c:if>	


				  </div>		 
	         </div>		
       </div>		



	

	
</div>




<script>
function validateForm(option){
if(document.getElementById("dpcsr") != null){
    var data = document.getElementById("dpcsr");
 }else if (document.getElementById("mae") != null){
    var data = document.getElementById("mae");
 }else if (document.getElementById("ipcsr") != null){
   var data = document.getElementById("ipcsr");
 }

 if(option.value!=0 &&  data.checked == true  ) document.getElementById("registerBtn").disabled=false;
 else document.getElementById("registerBtn").disabled=true;
}


function validateFormChechBox(){
if(document.getElementById("dpcsr") != null){
    var data = document.getElementById("dpcsr");
 }else if (document.getElementById("mae") != null){
    var data = document.getElementById("mae");
 }else if (document.getElementById("ipcsr") != null){
   var data = document.getElementById("ipcsr");
 }
 if(document.getElementById("center").value != 0 &&  data.checked == true  ) document.getElementById("registerBtn").disabled=false;
  else document.getElementById("registerBtn").disabled=true;
}


function persist(candidateId , messageResult){
   const eligibleCenterId =document.getElementById("center").value;

   document.getElementById("registerBtn").disabled=true;
   displayLoading("loading");
   let url =$("#baseUrl").val();
   url=url+"/student/present?eligible="+eligibleCenterId+"&candidate="+candidateId+"&messageResult="+messageResult;
  
  doget(url).then(response=>{
    if(response=="accepted_ipcsr"){
      closeNav();
        swal.fire(
                  'Successfull registered|Presente avec succes pour la specialite IPCSR',
                       'ok !'
                  );
        refreshCandidateContent();
        return false;
      }else if(response=="accepted_mae"){
          closeNav();
            swal.fire(
                      'Successfull registered|Presente avec succes pour la specialite MAE',
                           'ok !'
                      );
            refreshCandidateContent();
            return false;
     }else if(response=="accepted_dpcsr"){
        closeNav();

        swal.fire(
                    'Successfull registered|Presente avec succes pour la specialite DPCSR',
                         'ok !'
                    );
                    refreshCandidateContent();
                    return false;
     }else{
    if(response=="forbidden"){
    swal.fire(
                'Candidate has not prerequisies !',
                     'ok !','error'
                );
                refreshCandidateContent();
                return false;

             }else{
             
             swal.fire(
                'exeption raised | exception levee!',
                     'ok !','error'
                );
                refreshCandidateContent();
                return false;
             }   
    
    }
  }).catch(error=>{
   alert("bug created successfully");
  });
}

function refreshCandidateContent(){
	var manageFileButton = document.getElementById("search-candidate");
	var searchStudentButton = document.getElementById("searchStudentButton");
    var searchCandidateButton = document.getElementById("searchCandidateButton");
	var datefilter =document.getElementById("dateRangeId");
	var statusCandidates =document.getElementById("statusCandidates");
	var licenseNumber =document.getElementById("licenseNumber");
	if (licenseNumber.value) {
		manageFileButton.onclick();
	}else if(datefilter.value){
			searchStudentButton.onclick();
	}else if(statusCandidates.value !="" && eligibleCenterId.value != 0){
            searchCandidateButton.onclick();
	}else{
			var baseUrlOfController="/simt/manageCandidate/list";
            doGet(getBaseUrl(baseUrlOfController),'kt_content');

		}

}
</script>