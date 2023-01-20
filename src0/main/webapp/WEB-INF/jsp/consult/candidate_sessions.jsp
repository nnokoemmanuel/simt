<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-sm-12">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th>#</th>
					<th><fmt:message key="trainingCenter.Name"/></th>
					<th><fmt:message key="specialiy.key"/></th>
					<th><fmt:message key="consult.more.candidate.session.pv"/></th>
					<th><fmt:message key="consult.more.candidate.session.exam.date"/></th>
					<th><fmt:message key="consult.more.candidate.session.exam.place"/></th>
					<th>Possible processes</th>
				</tr>
			</thead>
			<tbody>
				<c:set var = "i"  value = "1"/>
				<c:forEach var="candidateSession" items="${studentSessions}">
					<tr>
						<td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
						<td>${candidateSession.student.trainingCenter.name}</td>
						<td>${candidateSession.speciality.abbreviation }</td>
						<td>${candidateSession.pvNumber }</td>
						<td><fmt:formatDate value="${candidateSession.eligibleCenter.examSession.sessionDate }" type="date" pattern="dd-MM-yyyy"/></td>
						<td>${candidateSession.eligibleCenter.examCenter.name }</td>	
						<td>
						    <c:if test="${ (empty candidateSession.applicationTranscript)  && (empty candidateSession.eligibleCenter.inSlip) && (candidateSession.studentSessionStatus.id == 4 ||  candidateSession.studentSessionStatus.id == 5 ||  candidateSession.studentSessionStatus.id == 7  )}">
						    	<button title="new Transcript" type="button" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/1/${candidateSession.id}/StudentSession','kt_content');active_link('home-1');return false;">NT</button>
						    </c:if>
						    <c:if test="${ (not empty candidateSession.applicationTranscript)  && (empty candidateSession.eligibleCenter.inSlip)  && (candidateSession.studentSessionStatus.id == 4 ||  candidateSession.studentSessionStatus.id == 5 ||  candidateSession.studentSessionStatus.id == 7  )}">
						    
							    <c:if test="${candidateSession.applicationTranscript.isPrinted == 1 && candidateSession.applicationTranscript.onProcess == 0  }">
							        <button title="duplicate of Transcript"  type="button" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/4/${candidateSession.applicationTranscript.id}/Transcript','kt_content');active_link('home-1');return false;">DT</button>
							    </c:if>
							    <c:if test="${candidateSession.applicationTranscript.isPrinted == 0  && candidateSession.applicationTranscript.onProcess == 0  }">
							    	<button title="new Transcript"  type="button" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/1/${candidateSession.applicationTranscript.id}/Transcript','kt_content');active_link('home-1');return false;">NT</button>
							    </c:if>
							    <c:if test="${candidateSession.applicationTranscript.isPrinted == 1 && candidateSession.applicationTranscript.onProcess > 0  }">
							        <button title="Duplicate of Transcript (process initiated) "  type="button"  class="btn" style="background-color:red;  color:white; " onClick="active_link('home-1');return false;" disabled>DT</button>
							    </c:if>
							    <c:if test="${candidateSession.applicationTranscript.isPrinted == 0  && candidateSession.applicationTranscript.onProcess > 0  }">
							    	<button type="button" title="New Transcript (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>NT</button>
							    </c:if>
						    </c:if>
						    <c:if test="${ (empty candidateSession.certificate)  && (empty candidateSession.eligibleCenter.inSlip) && candidateSession.studentSessionStatus.id == 4  }">
						        <button type="button" title="New Certificate" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/2/${candidateSession.id}/StudentSession','kt_content');active_link('home-1');return false;">NC</button>
<%-- 						        <button type="button" title="New Professional Card" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/3/${candidateSession.id}/StudentSession','kt_content');active_link('home-1');return false;">NP</button> --%>
						         
						    </c:if>
						    <c:if test="${(not empty candidateSession.certificate)  && (empty candidateSession.eligibleCenter.inSlip) }">
						    
							    <c:if test="${candidateSession.certificate.isPrinted == 1 && candidateSession.certificate.onProcess == 0 && candidateSession.studentSessionStatus.id == 4 }">
							         <button title="Duplicate of Certificate" type="button" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/5/${candidateSession.certificate.id}/Certificate','kt_content');active_link('home-1');return false;">DC</button>
							    </c:if>
							    <c:if test="${candidateSession.certificate.isPrinted == 0 && candidateSession.certificate.onProcess == 0 && candidateSession.studentSessionStatus.id == 4 }">
						        	<button title="New Certificate" type="button" class="btn" style="background-color:#1565C0; color:white; color:white;" onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/2/${candidateSession.certificate.id}/Certificate','kt_content');active_link('home-1');return false;">NC</button>
							    </c:if>
							    <c:if test="${candidateSession.certificate.isPrinted == 0 && candidateSession.certificate.onProcess > 0  && candidateSession.studentSessionStatus.id == 4}">
						        	<button type="button" title="New Certificate (process initiated)" class="btn "  style="background-color:red; color:white; " onClick="active_link('home-1');return false;" disabled>NC</button>
							    </c:if>
							     <c:if test="${candidateSession.certificate.isPrinted == 1 && candidateSession.certificate.onProcess > 0 && candidateSession.studentSessionStatus.id == 4 }">
							         <button type="button" title="Duplicate of Certificate (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>DC</button>
							    </c:if>
							    <c:if test="${not empty candidateSession.certificate.professionalCard }">
							    <c:if test="${ candidateSession.certificate.professionalCard.isPrinted == 1 && candidateSession.certificate.professionalCard.onProcess == 0 && candidateSession.studentSessionStatus.id == 4  }">
							        <button type="button" title="Duplicate of Professional Card" class="btn" style="background-color:#1565C0; color:white;  " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/6/${candidateSession.certificate.professionalCard.id}/ProfessionalCard','kt_content');active_link('home-1');return false;">DP</button>
							    </c:if>
							    <c:if test="${ candidateSession.certificate.professionalCard.isPrinted == 0 && candidateSession.certificate.isPrinted == 1  && candidateSession.certificate.professionalCard.onProcess == 0 && candidateSession.studentSessionStatus.id == 4 }">
						        	<button type="button" title="New Professional Card" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/3/${candidateSession.certificate.professionalCard.id}/ProfessionalCard','kt_content');active_link('home-1');return false;">NP</button>
							    </c:if>
							     <c:if test="${candidateSession.certificate.professionalCard.isPrinted == 1 && candidateSession.certificate.professionalCard.onProcess > 0 && candidateSession.studentSessionStatus.id == 4 }">
						        	<button type="button" title="Duplicate of Professional Card (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>DP</button>
							    </c:if>
							     <c:if test="${candidateSession.certificate.professionalCard.isPrinted == 0 && candidateSession.certificate.professionalCard.onProcess > 0 && candidateSession.studentSessionStatus.id == 4 }">
						        	<button type="button" title="New Professional Card (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>NP</button>
							    </c:if>
							    </c:if>
							    <c:if test="${ empty candidateSession.certificate.professionalCard }">
							        <c:if test="${candidateSession.studentSessionStatus.id == 4  && candidateSession.certificate.isPrinted == 1}">
							        	<button type="button" title="New Professional Card" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${candidateSession.student.person.id}/3/${candidateSession.id}/StudentSession','kt_content');active_link('home-1');return false;">NP</button>
							        </c:if>
							         <c:if test="${candidateSession.studentSessionStatus.id == 4 && candidateSession.processed == 1}">
						        		<button type="button" title="New Professional Card (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>NP</button>
							        </c:if>
							    </c:if>
						    </c:if>
						    
						     
						</td>					
					</tr>	
					<c:set var = "i"  value = "${i+1}"/>			
				</c:forEach>
			</tbody>
		</table>		
	</div>		
</div>