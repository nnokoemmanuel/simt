<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


 <!-- begin:: Content Head -->
 <div class="kt-subheader   kt-grid__item" id="kt_subheader">
  <div class="kt-container  kt-container--fluid ">
	<div class="kt-subheader__main">
		<h3 class="kt-subheader__title">
		    Records Found
		 </h3>
		 <span class="kt-subheader__separator kt-subheader__separator--v"></span>
		  <div class="kt-subheader__group" id="kt_subheader_search" >
		  <span class="kt-subheader__desc" id="kt_subheader_total">${fn:length(records)}  Total </span>
				<div class="kt-input-icon kt-input-icon--right kt-subheader__search" style="min-width:200px;width:500px; float:left" >
				<input type="text" class="form-control" placeholder="Search..." id="generalSearchTerm" >
				<span class="kt-input-icon__icon kt-input-icon__icon--right">
				<span>
				<svg id="searchButton" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
					<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
						<rect x="0" y="0" width="24" height="24"></rect>
						<path d="M14.2928932,16.7071068 C13.9023689,16.3165825 13.9023689,15.6834175 14.2928932,15.2928932 C14.6834175,14.9023689 15.3165825,14.9023689 15.7071068,15.2928932 L19.7071068,19.2928932 C20.0976311,19.6834175 20.0976311,20.3165825 19.7071068,20.7071068 C19.3165825,21.0976311 18.6834175,21.0976311 18.2928932,20.7071068 L14.2928932,16.7071068 Z" fill="#000000" fill-rule="nonzero" opacity="0.3"></path>
						<path d="M11,16 C13.7614237,16 16,13.7614237 16,11 C16,8.23857625 13.7614237,6 11,6 C8.23857625,6 6,8.23857625 6,11 C6,13.7614237 8.23857625,16 11,16 Z M11,18 C7.13400675,18 4,14.8659932 4,11 C4,7.13400675 7.13400675,4 11,4 C14.8659932,4 18,7.13400675 18,11 C18,14.8659932 14.8659932,18 11,18 Z" fill="#000000" fill-rule="nonzero"></path>
					</g>
				</svg>

				<!--<i class="flaticon2-search-1"></i>-->
			    </span>
				</span>
				 </div>
				</div>
				</div>
				
			</div></div>

<div id="search-results" class="container-fluid">

<c:forEach var="record" items="${records}">

   <c:set var="process" value="DUPLICATE"/>
   
  <div class="row" style="margin-top:20px; margin:20px ;background:#F2F0E4; padding-top:20px;padding-bottom:20px;">
    <div class="col-sm-2" style="align:left">
    
        <div class="kt-widget__media">
        	<c:if test="${empty record.lastCandidatePhoto}">
        		<c:if test="${empty record.photo}">  
		        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/assets/media/users/default.jpg" alt="image">
		        </c:if> 
		        <c:if test="${not empty record.photo}">  
		        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${record.photo}&folderName=application.photo.folder" width="130" height="130" alt="image">
		        </c:if>
        	</c:if>
	        <c:if test="${not empty record.lastCandidatePhoto && record.photoSource == 'candidateFolder' }">  
	        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${record.lastCandidatePhoto}&folderName=candidateSession.photo.folder" width="130" height="130" alt="image">
	        </c:if>  
	  		<c:if test="${not empty record.lastCandidatePhoto && record.photoSource == 'appFolder' }">  
	        	<img class="kt-widget__img" src="${pageContext.request.contextPath}/file/download?fileName=${record.lastCandidatePhoto}&folderName=application.photo.folder" width="130" height="130" alt="image">
	        </c:if> 
        </div>
        
        <div style="margin-top:10px;">
            <button type="button" class="btn pull-left" style="background-color:#cccccc; width:60px; margin-left:20px" onClick="moreInformation('${record.person.id}');active_link('home-1');return false;" >more</button>
        </div>

   </div>
    
    <div class="col-sm-3" style="align:left">

           <div style="align:left">
            <span style="color:black"> Surname : </span><label style="color:black; font-weight:bold"><c:out value = "${record.person.surName}"/></label>
           
           </div>
           
           
           <div>
           <span style="color:black"> GivenName : </span><label style="color:black;font-weight:bold"><c:out value = "${record.person.givenName}"/></label>
           
           </div>
           
           <div>
            <span style="color:black"> POB : </span><label style="color:black;font-weight:bold">${record.person.pob}</label>
           </label>
           
           </div>
           
           <div>
           
           <span style="color:black"> DOB : </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${record.person.dob}" /></label>
          
           </div>
           
    </div>

      <div class="col-sm-7" style="align:left">


       <c:forEach var="capacity" items="${record.capacities }">
       
           <div style="margin-bottom:10px;">
            <span style="color:black"> Capacity No :</span><label style="color:black; font-weight:bold">${capacity.capacityNumber}|</label>
            
            <c:forEach var="archive" items="${capacity.person.archives }">
            
            <c:if test="${not empty archive}">  
            <span style="color:black"> Exam Date : </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${archive.examDate}" />|</label>
            <span style="color:black"> Exam Place : </span><label style="color:black; font-weight:bold">${archive.examPlace}|</label>
            <span style="color:black"> PV No : ${archive.pvNumber}|</span>
            <c:if test="${archive.category.id==1}">  
              <span style="color:black; font-weight:bold"> Type : < 75CV </span>
           </c:if> 
           <c:if test="${archive.category.id==2}">  
              <span style="color:black; font-weight:bold"> Type : >= 75CV </span>
             
           </c:if> 
            </c:if>
            
            </c:forEach>
            
           
            <c:set var="lastcandId" value="0"/>
           <c:forEach var="candidateSession" items="${capacity.person.candidateSessions }" varStatus="lastCansess">
           
            <c:set var="lastcandId" value="${candidateSession.id}"/>
            
           <c:if test="${not empty candidateSession}">  
              <span style="color:black"> Exam Date : </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${candidateSession.eligibleCenter.examSession.sessionDate}" />|</label>
              <span style="color:black"> Exam Place : </span><label style="color:black; font-weight:bold">${candidateSession.eligibleCenter.examCenter.name}|</label>
              <span style="color:black"> PV No : ${candidateSession.pvNumber}|</span>
                <c:if test="${candidateSession.category.id==1}">  
                <span style="color:black; font-weight:bold"> Type : < 75CV </span>
           </c:if> 
           <c:if test="${candidateSession.category.id==2}">  
              <span style="color:black; font-weight:bold"> Type : >= 75CV </span>
             
           </c:if> 
           </c:if>
           <c:if test="${candidateSession.processed==0}">
           	<c:set var="process" value="EXTENSION"/>
           
           </c:if>
           
           </c:forEach> 
           
                 <c:if test="${ capacity.onProcess ==0 && process =='DUPLICATE'}">            
                     <button type="button" class="btn" style="background-color:#DBDFE8; width:100px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${capacity.person.id}/2/${capacity.id}/Capacity','kt_content');active_link('home-1');return false;">DUPLICATE</button>
                 </c:if>  
            
                 <c:if test="${ capacity.onProcess ==0 && process =='EXTENSION'}">            
                     <button type="button" class="btn" style="background-color:#DBDFE8; width:100px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${capacity.person.id}/4/${lastcandId}/CandidateSession','kt_content');active_link('home-1');return false;">EXTENSION</button>
                 </c:if>  
                 
                 <c:if test="${ capacity.onProcess !=0}">  
                    <button disabled type="button" class="btn" style="background-color:#DBDFE8; width:100px; margin-left:20px">ONPROCESS</button>
                 </c:if>
           </div>
           
           
           
           
        </c:forEach> 
        
       
       
        <!-- start archive -->
        
        <c:forEach var="archive" items="${record.archives }">
         <c:if test="${archive.status==2}"> 
           <div style="margin-bottom:10px;">
           
              <span style="color:black"> Capacity No :</span><label style="color:black; font-weight:bold">${archive.archiveNumber} |</label>
              <span style="color:black"> Exam Date : </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${archive.examDate}" />|</label>
              <span style="color:black"> Exam Place : </span><label style="color:black; font-weight:bold">${archive.examPlace}|</label>
              <span style="color:black"> PV No : ${archive.pvNumber}|</span>
              <c:if test="${archive.category.id==1}">  
                <span style="color:black; font-weight:bold"> Type : <= 75CV </span>
           </c:if> 
           <c:if test="${archive.category.id==2}">  
              <span style="color:black; font-weight:bold"> Type : > 75CV </span>
             
              </c:if> 
              <button type="button" class="btn" style="background-color:#DBDFE8; width:100px; margin-left:20px" onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${archive.person.id}/3/${archive.id}/Archive','kt_content');active_link('home-1');return false;">Renewal</button>

           </div>
            </c:if> 
        </c:forEach> 
        
        
        <!-- end archive -->  
   
   <!-- start candidate session -->
   <c:if test="${empty record.capacities}">
   <c:set var="process" value="NEW"/>
   <c:set var="candSess" />
    <c:forEach var="candidateSession" items="${record.candidateSessions }">
         <c:if test="${candidateSession.processed==0 and candidateSession.eligibleCenter.status==4}">
         	<c:set var="candSess" value="${candidateSession}"/> 
           <div style="margin-bottom:10px;">
              <span style="color:black"> Capacity No :</span><label style="color:black; font-weight:bold">****** | </label>
              <span style="color:black"> Exam Date : </span><label style="color:black; font-weight:bold"><fmt:formatDate pattern = "dd-MM-yyyy"  value = "${candidateSession.eligibleCenter.examSession.sessionDate}" />|</label>
              <span style="color:black"> Exam Place : </span><label style="color:black; font-weight:bold">${candidateSession.eligibleCenter.examCenter.name} |</label>
              <span style="color:black"> PV No : ${candidateSession.pvNumber}|</span>
              <c:if test="${candidateSession.category.id==1}">  
                <span style="color:black; font-weight:bold"> Type : < 75CV </span>
             </c:if> 
           <c:if test="${candidateSession.category.id==2}">  
              <span style="color:black; font-weight:bold"> Type : >= 75CV </span>
             
           </c:if> 
			
           </div>
            </c:if> 
        </c:forEach>
        <c:if test="${not empty candSess}">
        	<button type="button" class="btn" style="background-color:#cccccc; width:100px; margin-left:20px"  onClick="doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candSess.person.id}/1/${candSess.id}/CandidateSession','kt_content');active_link('home-1');return false;" >New</button>
        </c:if>
  </c:if>
     


   <!-- end candidate_session -->
   
           </div>

    </div>


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
					 ' chaîne de recherche est trop courte',
					 'error'
	   		);
       }
     
   });
</script>