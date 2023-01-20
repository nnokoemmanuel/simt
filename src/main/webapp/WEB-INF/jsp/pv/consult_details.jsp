<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="kt-portlet">
			<div class="row">
				
				<div class="col-sm-3">
					<label><span><fmt:message key="student.given.name" /> : </span></label>
					<span><h5>${student.person.givenName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span><fmt:message key="student.given.surname" /> : </span></label>
					<span><h5>${student.person.surName}</h5></span>
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
					<label><span><fmt:message key="student.speciality" />: </span></label>
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
					<label><span>nationality : </span></label>
					<span><h5>${student.person.nationality.countryName}</h5></span>
				</div>
				<div class="col-sm-3">
					<label><span>Telephone : </span></label>
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
   
            <div class="row">
					              
					              <c:if test="${not all_categories == true }">
					             	     <div class="col-sm-6">
							                   <c:out value = " "/>
							             </div>
					              </c:if>
					              <c:if test="${all_categories == false }">
					                 	<div class="col-sm-6">
						                      <span style="margin-top:20px; text-align:justify;color:red">
							                     <h5 style="float:right"> <fmt:message key="prerequise.not.respected" /></h5>
					                    	    </span>
				                    	</div>
							      </c:if>
					
				</div>
            
			<span style="margin-top:20px; text-align:center;color:black">
			<h3> <fmt:message key="registration.info" /></h3>
			</span>
			
             <hr class="app-color" style="border:1.5px solid;height:1.px;width:95%; border-radius:1px;">
				
		<div class="row">
		<div class="col-sm-4">
		<h4><fmt:message key="session.opened" /> :</h4> 
		<span class="pull-left"><h3 style="color:red"><fmt:formatDate pattern = "dd-MM-yyyy" 
         value = "${session.sessionDate}" /></h3></span>
		</div>
		
		<div class="col-sm-4" id="loading">
		<h4>Centre</h4>
		<select name="center"  id="center" class="form-control" onchange="validateForm(this)">
			<option value="0"><fmt:message key="select.one"/></option>
			<c:forEach var="eligiblecenter" items="${eligibleCenters}">
		       <option value="${eligiblecenter.id }">${eligiblecenter.examCenter.name}</option>
		    </c:forEach>
		</select>
		
		</div>
		
		<div class="col-sm-4">
		<h6 style="visibility:hidden">test</h6>
		<button onclick="persist('${student.id}')" id="registerBtn" disabled type="button" class="btn app-color pull-right" style="margin-right:20px">
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
			
			<div class="col-sm-4">
			<h4>Exam date</h4>
			<span> ${session.eligibleCenter.examSession.sessionDate}</span>
			</div>
			
			<div class="col-sm-4">
			<h4>Exam Place</h4>
			<span> ${session.eligibleCenter.examCenter.name}</span>
			</div>
			<div class="col-sm-4">
			<h4>Result</h4>
			<span> ${session.studentSessionStatus.description}</span>
			
			</div>
			
			</div>
			
			<br><br>
			<hr class="app-color" style="border:1.5px solid;height:1.px;width:95%; border-radius:1px;">
			
		<c:if test="${status != 'OK' && not empty student.studentSessions}">	
		<div class="form-group row">
			
		
			<div class="col-lg-4">
						   <!-- Preview Photo :: Default Photo  -->
							<h4>Photo</h4>
							<img src="${pageContext.request.contextPath}/file/download?fileName=${session.photoAndSignature}&folderName=candidateSession.photo.folder"   style="height:100px; width:100px;">
			</div>		
			<div class="col-lg-4">		
				<h4>Signature</h4>
					<img src="${pageContext.request.contextPath}/file/download?fileName=${session.photoAndSignature}&folderName=candidateSession.signature.folder"   style="height:100px; width:100px;">
							
			</div>
			<div class="col-lg-4" >
				<h4>File</h4>
				
			<c:if test="${countFile !=0 }">	
				<c:forEach var="i" begin="1" end="${countFile}" step="1" varStatus="loop">
					<td>
					<button class="btn" onClick=" printStudentSessionFile('${session.id}','${i}');" style="display:inline-block ;"><i class="fa fa-download"></i> Download</button></td>
					
				</c:forEach>	
			</c:if>				
			</div>
			
			</div>
			<br>
			<br>
						
	
	</c:if>
			</c:forEach>
			
	
</c:if>	




    <br><br>
	<hr class="app-color" style="border:1.5px solid;height:1.px;width:95%; border-radius:1px;">
 
     <div class="kt-portlet">
		   <div class="form-group row">	
					
					<div class="col-lg-4">	
						<c:if test="${status == 'OK' && not empty student.studentSessions}">	
							<!-- Preview Photo :: Student Photo  -->
							<h4>Photo</h4>
							<img src="${pageContext.request.contextPath}/file/download?fileName=${photo}&folderName=studentSession.file.photo.folder" src="resources/static/images/default_candidate.png"  style="height:100px; width:100px;">
							
							<br>
							
							<!-- Download Photo :: Student Photo  -->
							<a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${photo}&folderName=studentSession.file.photo.folder">
							     <i class="fa fa-download" aria-hidden="true"></i>
						     <fmt:message key="label_Downloadlink_Student_Photo" />
							 </a>
							 
						</c:if>	
				   </div>		 
	         </div>		
       </div>		



	

	
</div>




<script>
function validateForm(option){
 if(option.value==0) document.getElementById("registerBtn").disabled=true;
 else document.getElementById("registerBtn").disabled=false;
}


function persist(candidateId){
   const eligibleCenterId =document.getElementById("center").value;
   document.getElementById("registerBtn").disabled=true;
   displayLoading("loading");
   let url =$("#baseUrl").val();
   url=url+"/student/present?eligible="+eligibleCenterId+"&candidate="+candidateId;
  
  doget(url).then(response=>{
  if(response=="acceptedInspector"){
        closeNav();
        $('#'+candidateId).remove();
        swal.fire(
                    'Successfull registered|Presente avec succes pour la specialite IPCSR',
                         'ok !'
                    );
                refreshCandidateContent();
                return false;
  }
  if(response=="acceptedMonitor"){
          closeNav();
          $('#'+candidateId).remove();
          swal.fire(
                      'Successfull registered|Presente avec succes pour la specialite MAE',
                           'ok !'
                      );
                  refreshCandidateContent();
                  return false;
    }
  if(response=="accepted"){
    closeNav();
    $('#'+candidateId).remove();
    swal.fire(
                'Successfull registered|Presente avec succes',
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