<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

            <td  tabindex="0" class="sorting_1">
                <c:out value = "${studentSession.id}"/>
            </td>
        <td>${studentSession.student.trainingCenter.name}</td>
        <td>${studentSession.student.person.surName}</td>
        <td>${studentSession.student.person.givenName}</td>
        <td id='studentSession.student.person.id'> <fmt:formatDate value="${studentSession.student.person.dob}" type="date" pattern="dd-MM-yyyy" var="dob"/>${dob}</td>
        <td>${studentSession.student.person.pob}</td>
        <c:if test="${not empty studentSession.photoAndSignature}">
            <td style="background-color:#80ad71;">${studentSession.pvNumber}</td>
        </c:if>
        <c:if test="${empty studentSession.photoAndSignature}">
            <td>${studentSession.pvNumber}</td>
        </c:if>

        <td><!-- Status -->
            <c:if test="${studentSession.studentSessionStatus.id == 1 }">
                <fmt:message key="student.exam.result.status.presented" />
            </c:if>
            <c:if test="${studentSession.studentSessionStatus.id == 2 }">
                <fmt:message key="student.exam.result.status.approved" />
            </c:if>
            <c:if test="${studentSession.studentSessionStatus.id == 3 }">
                <fmt:message key="student.exam.result.status.rejected" />
                     <!--
                     <span style="color:red;font-weight:bold;">
                          <c:out value="${studentSession.reasonForReject}" />
                      </span>
                      -->
            </c:if>
            <c:if test="${studentSession.studentSessionStatus.id == 4 }">
                <fmt:message key="student.exam.result.status.succeed" />
            </c:if>
            <c:if test="${studentSession.studentSessionStatus.id == 5 }">
                <fmt:message key="student.exam.result.status.failed" />
            </c:if>

        </td>
        <td><!-- Action -->
            <select id="candidate-select-action-${studentSession.id }"  class="form-control input-sm">
                <option value="action">Action</option>
              
                       <c:if test="${studentSession.studentSessionStatus.id == 4  }">
	 				     <option id="approved" onClick="uploadStudentDocumentInSession('${studentSession.id}');"><fmt:message key="student.upload.document.key" /></option>
							<sec:authorize access="hasRole('ROLE_MANAGE_CANDIDATES_TRANSCRIPT')">
							    <c:if test="${studentSession.eligibleCenter.eligibleCenterStatus.id == 3}">
									<option id="enter-notes" onClick="manageCandidate('${pageContext.request.contextPath}/studentSession/manage', '${studentSession.id}', '${studentSession.studentSessionStatus.id}', 'enter-notes');"><fmt:message key="computerize.candidate.notes" /></option>	
								    <option id="enter-final-note" onClick="saveFinalNote('${pageContext.request.contextPath}','${studentSession.id}');active_link('student-management');return false;"><fmt:message key="computerize.candidate.final.note" /></option>
								</c:if>
							</sec:authorize> 
     							 
      						<sec:authorize access="hasRole('ROLE_PRINT_TRANSCRIPT')">
      							   
       						        <option onClick="printCertificate('${pageContext.request.contextPath}','${studentSession.id}');active_link('student-management');return false;"><fmt:message key="print.certificate" /></option>
                                     <option onClick="printProfessionalCard('${studentSession.id}','preview');active_link('student-management');return false;"><fmt:message key="print.professional.card" /></option>
      						</sec:authorize> 
     							
                        </c:if>
      						
    			        <c:if test="${studentSession.studentSessionStatus.id  == 5 || studentSession.studentSessionStatus.id == 4  }">
    							  	<sec:authorize access="hasRole('ROLE_PRINT_TRANSCRIPT')">
    							  	    <option  onCLick="printTranscriptPreview('${pageContext.request.contextPath}','${studentSession.id}');active_link('student-management');return false;"><fmt:message key="print.transcript" /></option>
     				            </sec:authorize> 
        				 </c:if>
		                        				 
                <option value="history" onClick="openNav('STUDENT SESSION HISTORY :: TRANSCRIPT TRACKING', '', '50%');printing_transcript_action(this.value,'${studentSession.id}','transcript');return false;"><fmt:message key="pv.action.history" /></option>

            </select>
        </td>
              
              