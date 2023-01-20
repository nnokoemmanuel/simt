<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">
			
                <!--begin: Datatable -->
    		    <table id="table-eligible-centers" class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info">
				  <thead style="background:#01A8F9;">
					<tr style="color:white"  role="row">
					
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ">NO</th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ><fmt:message key="app.exam.center" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  ><fmt:message key="app.exam.session" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" ><fmt:message key="app.pv.status" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1"  >ACTIONS</th>

					</tr>
				  </thead>
				  <tbody>

                            <c:set var = "i"  value = "1"/>
      						<c:forEach var="eligibleCenter" items="${eligibleCenters}">
      						    <tr role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if><c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td id='eligibleCenter-name-${eligibleCenter.id}' >
                                        	<c:if test="${eligibleCenter.eligibleCenterStatus.id == 4 }">
                                       		    <a target="_blank" href="${pageContext.request.contextPath}/file/download?fileName=${eligibleCenter.id}.pdf&folderName=eligibleCenter.file.folder">${eligibleCenter.examCenter.name}</a>
                                       		</c:if>
                                       		<c:if test="${eligibleCenter.eligibleCenterStatus.id != 4 }">
                                       			${eligibleCenter.examCenter.name}
                                       		</c:if>
                                        </td>
                                        <td id='eligibleCenter-session-date-${eligibleCenter.id}'> <fmt:formatDate value="${eligibleCenter.examSession.sessionDate}" type="date" pattern="dd-MM-yyyy"/></td>
                                        <!-- start managing status -->
                                        <c:if test="${eligibleCenter.eligibleCenterStatus.id == 1 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.created" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.eligibleCenterStatus.id == 2 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.opened" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.eligibleCenterStatus.id == 3 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.closed" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.eligibleCenterStatus.id == 4 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.validated" /></td>
                                        </c:if>
                                        <c:if test="${eligibleCenter.eligibleCenterStatus.id == 5 }">
                                        <td id='eligibleCenter-status-${eligibleCenter.id}' ><fmt:message key="pv.status.generated" /></td>
                                        </c:if>
                                        <!-- end  managing status -->
                                          <td >
                                            <select  class="form-control input-sm">
                                                <option value="action">Action</option>

                                                <sec:authorize access="hasRole('ROLE_MANAGE_ELIGIBLE_CENTER')">
	                                                <c:if test = "${eligibleCenter.eligibleCenterStatus.id==1}">
	                                                	<option onClick="openEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.open" /></option>
	                                                </c:if>
                                                </sec:authorize>

                                                <sec:authorize access="hasRole('ROLE_PV_CONTROLLER')">
	                                                <c:if test = "${eligibleCenter.eligibleCenterStatus.id==2}">
	                                                    <option onClick="closeEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.close" /></option>

	                                                </c:if>
	                                                <c:if test = "${eligibleCenter.eligibleCenterStatus.id==3}">
	                                                    <option onClick="resetEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.reset" /></option>
	                                                </c:if>
	                                                <c:if test = "${eligibleCenter.eligibleCenterStatus.id==3}">
	                                                    <option onClick="generatePV('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.generate" /></option>
	                                                </c:if>
	                                                <c:if test = "${eligibleCenter.eligibleCenterStatus.id==5}">
	                                                	<option onClick="resetPV('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.reset" /></option>
	                                                    <option onClick="validateEligibleCenter('${eligibleCenter.id}');active_link('concours-experts');return false;"><fmt:message key="pv.action.validate" /></option>
	                                                </c:if>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_MANAGE_CANDIDATE')">
                                                	<c:if test = "${eligibleCenter.eligibleCenterStatus.id==2 || eligibleCenter.eligibleCenterStatus.id==3}">
	                                                  <!--    <option value="candidate" onClick="doGet('${pageContext.request.contextPath}/student/createGet/?id=${eligibleCenter.id}','kt_content');"><fmt:message key="pv.action.manage.candidate" /></option> -->
	                                                    <option value="candidate" onClick="doGet('${pageContext.request.contextPath}/pv/manage_pv_list/?id=${eligibleCenter.id}',,'kt_content');"><fmt:message key="pv.action.manage.candidate" /></option>
                                                    </c:if>
                                                </sec:authorize>
                                                
                                                 <sec:authorize access="hasRole('ROLE_MANAGE_CANDIDATES_TRANSCRIPT')">
                                                    <c:if test = "${ eligibleCenter.eligibleCenterStatus.id == 3 || eligibleCenter.eligibleCenterStatus.id == 4 }">
                                                		<option value="candidate" onClick="doGet('${pageContext.request.contextPath}/evaluation/manage_pv_transcript/?eligibleCenterId=${eligibleCenter.id}','kt_content');"><fmt:message key="pv.action.manage.candidate.transcripts" /></option>
                                                    </c:if>
                                                </sec:authorize>
                                                
                                                <%-- <c:if test="${eligibleCenter.eligibleCenterStatus.id  == 3 || eligibleCenter.eligibleCenterStatus.id == 4  }">
                        							<sec:authorize access="hasRole('ROLE_PRINT_TRANSCRIPT')">
                        							  	<option  onCLick="printElligibleCenterTranscriptPreview('${pageContext.request.contextPath}','${eligibleCenter.id}');active_link('courses-and-modules-management');return false;"><fmt:message key="print.transcript" /></option>
                        							</sec:authorize> 
                         						</c:if> --%>

                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_presented@MAE');active_link('concours-experts');return false;">Liste des candidats presentes - (MAE)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_presented@IPCSR');active_link('concours-experts');return false;">Liste des candidats presentes - (IPCSR)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_presented@DPCSR');active_link('concours-experts');return false;">Liste des candidats presentes - (DPCSR)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_appouved@MAE');active_link('concours-experts');return false;">Liste des candidats elligibles - (MAE)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_appouved@IPCSR');active_link('concours-experts');return false;">Liste des candidats elligibles - (IPCSR)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_appouved@DPCSR');active_link('concours-experts');return false;">Liste des candidats elligibles - (DPCSR)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_rejected@MAE');active_link('concours-experts');return false;">Liste des candidats rejetes - (MAE)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_rejected@IPCSR');active_link('concours-experts');return false;">Liste des candidats rejetes - (IPCSR)</option>
                                                <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_list_rejected@DPCSR');active_link('concours-experts');return false;">Liste des candidats rejetes - (DPCSR)</option>
                                                <c:if test = "${eligibleCenter.eligibleCenterStatus.id >= 4}">
                                                     <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_failed@MAE');active_link('concours-experts');return false;">Liste des candidats ayant echoues - (MAE)</option>
                                                     <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_failed@IPCSR');active_link('concours-experts');return false;">Liste des candidats ayant echoues- (IPCSR)</option>
                                                     <option  onCLick="printCandidatesList('${eligibleCenter.id}','candidates_failed@DPCSR');active_link('concours-experts');return false;">Liste des candidats ayant echoues - (DPCSR)</option>
                                                     <option  onCLick="printPV('${eligibleCenter.id}','print_pv');active_link('concours-experts');return false;"><fmt:message key="list.print.pv" /></option>
                                                </c:if>
                                              <!--  <option value="history">History</option>  -->
                                             <option value="history" onClick="eligibleCenter_PV_action(this.value,'${eligibleCenter.id}','eligibleCenter');return false;"><fmt:message key="pv.action.history" /></option>

                                            </select>
                                         </td>
                                    </tr>
                            <c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>
                  </tbody>
			    </table>
                <!--end: Datatable -->
    		
    	</div>

</div>
<script>
	addDataTable();
</script>