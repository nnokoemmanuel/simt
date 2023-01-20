<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!-- <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--desktop kt-grid--ver-desktop kt-grid--hor-tablet-and-mobile"> -->
<!-- <div class=" kt-portlet kt-portlet--mobile"> -->
<%-- <c:set var = "i"  value = "0"/> --%>
<div class=" container-fluid  kt-container  kt-container--fluid ">
<c:forEach var="record" items="${records}">

<%--    <c:set var="process" value="DUPLICATE"/> --%>
 <div <c:if test ="${i%2==0}"> class="row col-sm-12" </c:if> >  
 <div class="col-sm-12 ">
  <div class="row" style="margin-top:5px; margin:5px ;background:#ccf3ff; padding-top:20px;padding-bottom:20px;"> <!-- #F2F0E4  -->
   
    <div class="col-sm-2" style="align:left;"> 
        <div class="kt-widget__media row" style="margin-left: 0px;">
        	<c:if test="${empty record.lastCandidatePhoto}">
<%--         		<c:if test="${empty record.photo}">   --%>
		        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/assets/media/users/default.jpg" alt="image">
<%-- 		        </c:if>  --%>
<%-- 		        <c:if test="${not empty record.photo}">   --%>
<%-- 		        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${record.photo}&folderName=application.photo.folder" width="130" height="130" alt="image"> --%>
<%-- 		        </c:if> --%>
        	</c:if>
	        <c:if test="${not empty record.lastCandidatePhoto && record.photoSource == 'studentSessionPhotoFolder' }">  
	        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${record.lastCandidatePhoto}&folderName=studentSession.photo.folder" width="130" height="130" alt="image">
	        </c:if>  
<%-- 	  		<c:if test="${not empty record.lastCandidatePhoto && record.photoSource == 'appFolder' }">   --%>
<%-- 	        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${record.lastCandidatePhoto}&folderName=application.photo.folder" width="130" height="130" alt="image"> --%>
<%-- 	        </c:if>  --%>

      </div>
        
 <button type="button" class="btn pull-left" style="background-color:#cccccc; width:60px;height:40px; margin-top:0px; margin-left:30px;" onClick="moreInformation('${record.person.id}');active_link('home-1');return false;" >more</button>
        

   </div>
    
    <div class="col-sm-5" style="align:left; padding-left: 100px;  margin-left:-100px;">
           <div style="align:left; ">
				<span style="color:black">Record From : <label style="color:black; font-weight:bold"><c:out value = "${record.recordOrigin}"/></label></span>
			</div>
			<div style="align:left;">
				<span style="color:black">License Number: <label style="color:black; font-weight:bold"><c:out value = "${record.person.licenseNum}"/></label></span>
			</div>
           <div style="align:left">
            <span style="color:black"> Surname : </span><label style="color:black; font-weight:bold"><c:out value = "${record.person.surName}"/></label>
           
           </div>
         
    
           <div>
           <span style="color:black"> GivenName : </span><label style="color:black;font-weight:bold"><c:out value = "${record.person.givenName}"/></label>
           
           </div>
    </div>  
   
   
    <div class="" style="align:left;  margin-top:0px; margin-left: 0px;">       
           <div>
            <span style="color:black"> POB : </span><label style="color:black;font-weight:bold">${record.person.pob}</label>
           
           </div>
           
           <div>
           
           <span style="color:black"> DOB ${i}: </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${record.person.dob}" /></label>
          
           </div>
            <div>
           
           <span style="color:black"> LATEST OPTION: </span><label style="color:black; font-weight:bold">
           ${record.latestSpeciality}
           </label>
          
           </div>
  </div> 

<!--       <div class="col-sm-7" style="align:left"> -->


<%--        <c:forEach var="capacity" items="${record.capacities }"> --%>
       
<!--            <div style="margin-bottom:10px;"> -->
<%--             <span style="color:black"> Capacity No :</span><label style="color:black; font-weight:bold">${capacity.capacityNumber}|</label> --%>
            
<%--             <c:forEach var="archive" items="${capacity.person.archives }"> --%>
            
<%--             <c:if test="${not empty archive}">   --%>
<%--             <span style="color:black"> Exam Date : </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${archive.examDate}" />|</label> --%>
<%--             <span style="color:black"> Exam Place : </span><label style="color:black; font-weight:bold">${archive.examPlace}|</label> --%>
<%--             <span style="color:black"> PV No : ${archive.pvNumber}|</span> --%>
<%--             <c:if test="${archive.category.id==1}">   --%>
<!--               <span style="color:black; font-weight:bold"> Type : < 75CV </span> -->
<%--            </c:if>  --%>
<%--            <c:if test="${archive.category.id==2}">   --%>
<!--               <span style="color:black; font-weight:bold"> Type : >= 75CV </span> -->
             
<%--            </c:if>  --%>
<%--             </c:if> --%>
            
<%--             </c:forEach> --%>
            
           
<%--             <c:set var="lastcandId" value="0"/> --%>
<%--            <c:forEach var="candidateSession" items="${capacity.person.candidateSessions }" varStatus="lastCansess"> --%>
           
<%--             <c:set var="lastcandId" value="${candidateSession.id}"/> --%>
            
<%--            <c:if test="${not empty candidateSession}">   --%>
<%--               <span style="color:black"> Exam Date : </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${candidateSession.eligibleCenter.examSession.sessionDate}" />|</label> --%>
<%--               <span style="color:black"> Exam Place : </span><label style="color:black; font-weight:bold">${candidateSession.eligibleCenter.examCenter.name}|</label> --%>
<%--               <span style="color:black"> PV No : ${candidateSession.pvNumber}|</span> --%>
<%--                 <c:if test="${candidateSession.category.id==1}">   --%>
<!--                 <span style="color:black; font-weight:bold"> Type : < 75CV </span> -->
<%--            </c:if>  --%>
<%--            <c:if test="${candidateSession.category.id==2}">   --%>
<!--               <span style="color:black; font-weight:bold"> Type : >= 75CV </span> -->
             
<%--            </c:if>  --%>
<%--            </c:if> --%>
<%--            <c:if test="${candidateSession.processed==0}"> --%>
<%--            	<c:set var="process" value="EXTENSION"/> --%>
           
<%--            </c:if> --%>
           
<%--            </c:forEach>  --%>
           

<%--                  <c:if test="${ capacity.onProcess ==0 && process =='EXTENSION'}">             --%>

<%--  <div class="" style="align:left; margin-left:-1100px; padding-left: 150px; padding-right:0px; padding-top:0px; float:left;"> 
    <button type="button" class="btn pull-left" style="background-color:#cccccc; width:60px; margin-top:135px; margin-left:0px;" onClick="moreInformation('${record.person.id}');active_link('home-1');return false;" >more</button>
 </div>  --%>
 
   
 
<div class="" style="align:left;  margin-top:-20px; ">            
<!-- <div class="row col-sm-5" style="margin-top:10px; padding-left: 1000px;"> --> 

<%-- <button type="button" class="btn pull-left" style="background-color:#cccccc; width:60px; margin-top:155px; margin-left:-950px;" onClick="moreInformation('${record.person.id}');active_link('home-1');return false;" >more</button> --%>


             <c:if test="${not empty record.possibleProcessType }">
				 <c:forEach items="${record.possibleProcessType}" var="processType">
				  <c:if test="${processType.abbreviation =='NP' || processType.abbreviation =='DP'  || processType.abbreviation =='DC'  || processType.abbreviation =='DT' }">
				    <c:if test="${((processType.abbreviation =='NP'|| processType.abbreviation =='DP') && record.professionalCard.onProcess == 0) || ( processType.abbreviation =='DC' && record.certificate.onProcess == 0)  || ( processType.abbreviation =='DT' && record.applicationTranscript.onProcess == 0)}">
				        <c:if test="${((processType.abbreviation =='NP'|| processType.abbreviation =='DP') && record.professionalCard.onProcess == 0 && (empty record.professionalCard.certificate.studentSession.eligibleCenter.inSlip))}">
				           <button type="button"  class="btn" style="background-color:#DBDFE8; width:200px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${record.professionalCard.certificate.studentSession.student.person.id}/${processType.id}/${record.professionalCard.id}/ProfessionalCard','kt_content');active_link('home-1');return false;" >${processType.description}</button>
				        </c:if>
				        <c:if test="${(processType.abbreviation =='DC' && record.certificate.onProcess == 0 && (empty record.certificate.studentSession.eligibleCenter.inSlip))}">
				           <button type="button"  class="btn" style="background-color:#DBDFE8; width:200px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${record.certificate.studentSession.student.person.id}/${processType.id}/${record.certificate.id}/Certificate','kt_content');active_link('home-1');return false;" >${processType.description}</button>
				        </c:if>
				        <c:if test="${(processType.abbreviation =='DT' && record.applicationTranscript.onProcess == 0 && (empty record.applicationTranscript.studentSession.eligibleCenter.inSlip))}">
				           <button type="button"  class="btn" style="background-color:#DBDFE8; width:200px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${record.applicationTranscript.studentSession.student.person.id}/${processType.id}/${record.applicationTranscript.id}/Transcript','kt_content');active_link('home-1');return false;" >${processType.description}</button>
				        </c:if>
				    </c:if>
				    <c:if test="${((processType.abbreviation =='NP'|| processType.abbreviation =='DP') && record.professionalCard.onProcess > 0 && (empty record.professionalCard.certificate.studentSession.eligibleCenter.inSlip)) || ( processType.abbreviation =='DC' && record.certificate.onProcess > 0  && (empty record.certificate.studentSession.eligibleCenter.inSlip))  || ( processType.abbreviation =='DT' && record.applicationTranscript.onProcess > 0 && 0 && (empty record.applicationTranscript.studentSession.eligibleCenter.inSlip))}">
				           <button type="button" title="process initiated" class="btn" style="background-color:#FCE4EC; width:200px; margin-left:20px" onClick="active_link('home-1');return false;" disabled>${processType.description}</button>
				    </c:if>
				  </c:if>
				   <c:if test="${processType.abbreviation =='NC' || processType.abbreviation =='NT'  }">
				        <c:if test="${(processType.abbreviation =='NC' && record.certificate.onProcess == 0 ) || (processType.abbreviation =='NT'  && record.applicationTranscript.onProcess == 0 )  }">
			               <c:if test="${(processType.abbreviation =='NC' && record.certificate.onProcess == 0  && (empty record.certificate.studentSession.eligibleCenter.inSlip))}">
			               		<button type="button" class="btn" style="background-color:#DBDFE8; width:120px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${record.certificate.studentSession.student.person.id}/${processType.id}/${record.certificate.id}/Certificate','kt_content');active_link('home-1');return false;" >${processType.description}</button>
			               </c:if>
			               <c:if test="${(processType.abbreviation =='NT'  && record.applicationTranscript.onProcess == 0 && (empty record.applicationTranscript.studentSession.eligibleCenter.inSlip))}">
			               		<button type="button" class="btn" style="background-color:#DBDFE8; width:120px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${record.applicationTranscript.studentSession.student.person.id}/${processType.id}/${record.applicationTranscript.id}/Transcript','kt_content');active_link('home-1');return false;" >${processType.description}</button>
			               </c:if>
				        </c:if>
				         <c:if test="${(processType.abbreviation =='NC' && record.certificate.onProcess > 0 && (empty record.certificate.studentSession.eligibleCenter.inSlip)) || (processType.abbreviation =='NT'  && record.applicationTranscript.onProcess > 0  && (empty record.applicationTranscript.studentSession.eligibleCenter.inSlip))  }">
			                    <button type="button" title="process initiated" class="btn" style="background-color:#FCE4EC; width:120px; margin-left:20px" onClick="active_link('home-1');return false;">${processType.description}</button>
				        </c:if>
				   </c:if>
				 </c:forEach>
			  </c:if>
	
		<div class="" style="align:right;  margin-top:-50px; margin-right:10px; "> 
		
			  	<c:if test="${record.recordOrigin == 'studentSession' }">
				  	<c:forEach items="${record.studentSessions}" var="studentSession">
				  	<fmt:formatDate value="${studentSession.eligibleCenter.examSession.sessionDate}" pattern="dd/MM/yyyy" var="sessionDate" />
					  	
					  
						  	<c:if test="${ (empty studentSession.applicationTranscript) && studentSession.studentSessionStatus.id == 4 && studentSession.eligibleCenter.eligibleCenterStatus.id == 4  && (empty studentSession.eligibleCenter.inSlip)}">
				             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 00 -->     <%-- <button type="button" class="btn" style="background-color:#DBDFE8; width:120px; margin-right:-500px; margin-top:0px;" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${studentSession.student.person.id}/1/${studentSession.id}/StudentSession','kt_content');active_link('home-1');return false;" >NT SESSION ${sessionDate}</button> --%>
						  	</c:if>
					  	
					  	
					  	
					  	
					  	
						  	<c:if test="${ (not empty studentSession.applicationTranscript) && studentSession.applicationTranscript.onProcess >0  && (empty studentSession.eligibleCenter.inSlip)}">
			                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 11 -->   <!--  <button type="button" title="process initiated" class="btn " style="background-color:#FCE4EC; width:120px; margin-right:-600px;" onClick="active_link('home-1');return false;">NT</button>  -->
			
						  	</c:if>
					  	
					  	
					  	
					  	
					  	
							<c:if test="${ (not empty studentSession.applicationTranscript) && studentSession.applicationTranscript.isPrinted==1  && studentSession.studentSessionStatus.id == 4 && studentSession.eligibleCenter.eligibleCenterStatus.id == 4  && empty studentSession.eligibleCenter.inSlip}">
			<!-- 22 -->	<%-- <button type="button" class="btn" style="background-color:#DBDFE8; width:120px; margin-left:20px; " onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${studentSession.student.person.id}/4/${studentSession.applicationTranscript.id}/Transcript','kt_content');active_link('home-1');return false;" >DT SESSION ${sessionDate}</button> --%>
						  	</c:if>
					  	
					  	
					  	
					 
							<c:if test="${ (not empty studentSession.applicationTranscript) && studentSession.applicationTranscript.isPrinted==1 && studentSession.applicationTranscript.onProcess >0  && empty studentSession.eligibleCenter.inSlip}">
			<!--  33 --> <%-- <button type="button" title="process initiated" class="btn" style="background-color:#FCE4EC; width:120px; margin-left:20px" onClick="active_link('home-1');return false;">DT SESSION ${sessionDate}</button>  --%>
						  	</c:if>
					  
					  	
					  	
							  <c:if test="${ (empty studentSession.certificate) && studentSession.studentSessionStatus.id == 4 && studentSession.eligibleCenter.eligibleCenterStatus.id == 4  && empty studentSession.eligibleCenter.inSlip}">
			<!-- 44 --> 	 <%-- <button type="button" class="btn" style="background-color:#DBDFE8; width:120px; margin-left:380px; margin-top:150px;" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${studentSession.student.person.id}/2/${studentSession.id}/StudentSession','kt_content');active_link('home-1');return false;" >NC SESSION ${sessionDate}</button> --%>
							  </c:if>
					  	
					  	
					  	
						  	 <c:if test="${ (not empty studentSession.certificate) && studentSession.applicationTranscript.onProcess >0 && empty studentSession.eligibleCenter.inSlip}">
			<!-- 55 -->	       <%-- <button type="button" title="process initiated" class="btn" style="background-color:#FCE4EC; width:120px; margin-left:480px; margin-top:150px;" onClick="active_link('home-1');return false;">NC SESSION ${sessionDate}</button>  --%>
						  	</c:if>
					  	
					  	
					  	
					  
							<c:if test="${ (not empty studentSession.certificate) && studentSession.certificate.isPrinted==1  && studentSession.studentSessionStatus.id == 4 && studentSession.eligibleCenter.eligibleCenterStatus.id == 4  && empty studentSession.eligibleCenter.inSlip}">
			<!-- 66 --> 		<%--  <button type="button" class="btn btn-success" style="background-color:#DBDFE8; width:120px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${studentSession.student.person.id}/5/${studentSession.certificate.id}/Certificate','kt_content');active_link('home-1');return false;" >DC SESSION ${sessionDate}</button> --%>
						  	</c:if>
					 
					  	
					  	
					  
							<c:if test="${ (not empty studentSession.certificate) && studentSession.certificate.isPrinted ==1 && studentSession.certificate.onProcess >0  && empty studentSession.eligibleCenter.inSlip}">
			<!-- 77 -->	     <%-- <button type="button" title="process initiated" class="btn" style="background-color:#FCE4EC; width:120px; margin-left:20px" onClick="active_link('home-1');return false;">DC SESSION ${sessionDate}</button>  --%>
						  	</c:if>
					  
					  	
					  	
					  	
							<c:if test="${ (not empty studentSession.certificate) && studentSession.certificate.isPrinted == 1 && ( empty studentSession.certificate.professionalCard) && studentSession.certificate.isPrinted == 1 && studentSession.studentSessionStatus.id == 4 && studentSession.eligibleCenter.eligibleCenterStatus.id == 4  && (empty studentSession.eligibleCenter.inSlip)}">
			<!-- 88 -->  <%-- <button type="button" class="btn" style="background-color:#DBDFE8; width:120px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${studentSession.student.person.id}/3/${studentSession.id}/StudentSession','kt_content');active_link('home-1');return false;" >NP SESSION ${sessionDate}</button> --%>
						  	</c:if>
					  
					  	
					  	
					 
							<c:if test="${ (not empty studentSession.certificate.professionalCard) && studentSession.certificate.professionalCard.onProcess >0  && (empty studentSession.eligibleCenter.inSlip)}">
		   <!-- 99 -->  <%-- <button type="button" title="process initiated" class="btn" style="background-color:#FCE4EC; width:120px; margin-left:20px" onClick="active_link('home-1');return false;">NC SESSION ${sessionDate}</button>  --%>
						  	</c:if>
					  
					  	
					  	
					  	
							<c:if test="${ (not empty studentSession.certificate) && studentSession.certificate.isPrinted == 1 && ( not empty studentSession.certificate.professionalCard) && studentSession.certificate.professionalCard.isPrinted == 1 && studentSession.studentSessionStatus.id == 4 && studentSession.eligibleCenter.eligibleCenterStatus.id == 4 && (empty studentSession.eligibleCenter.inSlip) }">
			<!-- 100 --> 	<%-- <button type="button" class="btn" style="background-color:#DBDFE8; width:120px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${studentSession.student.person.id}/6/${studentSession.certificate.professionalCard.id}/ProfessionalCard','kt_content');active_link('home-1');return false;" >DP SESSION ${sessionDate}</button> --%>
						  	</c:if>
					  
					  	
					  	
					 
							<c:if test="${ (not empty studentSession.certificate.professionalCard) && studentSession.certificate.professionalCard.onProcess >0 && (empty studentSession.eligibleCenter.inSlip) }">
			<!-- 110 -->	   <%--  <button type="button" title="process initiated" class="btn" style="background-color:#FCE4EC; width:120px; margin-left:20px" onClick="active_link('home-1');return false;">DP SESSION ${sessionDate}</button>  --%>
						  	</c:if>
					  	
					  	
					  	
				  	</c:forEach>
			  	</c:if>

    </div>

 

           
           

   <!-- end candidate_session -->
   
 </div> 


    	</div>
</div>
	</div>
<%-- <c:set var = "i"  value = "${i+1}"/> --%>
     </c:forEach> 
</div>

<script>
   buttonSearch =document.querySelector("#searchButton");
   inputSearch =document.querySelector("#generalSearchTerm");
   
   inputSearch.addEventListener("keyup", event=>{
    if(event.keyCode===13)
    {
       let term=event.target.value;
       if(term.length>4){
          doGet('/simt/read/?searchTerm='+term,'search-results');
     
       }
    }
   });
   
   buttonSearch.addEventListener("click", event=>{
    let text =document.querySelector("#generalSearchTerm").value;
    if(text.length>4){
          doGet('/simt/read/?searchTerm='+text,'search-results');
     
       }else{
    	   swal.fire(
					 'Search string is too short',
					 ' cha�ne de recherche est trop courte',
					 'error'
	   		);
     }
   
 });
     
   });
</script>