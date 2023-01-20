<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<table class="table table-striped- table-bordered table-hover table-checkable  dtr-inline"  role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1644px;">
				<thead style="background:#01A8F9">
					<tr style="color:white" role="row">
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" >No</th>
				    <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 120.25px;" ><fmt:message key="archive.table.surname" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 120.25px;" ><fmt:message key="archive.table.givenName" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 152.25px;" ><fmt:message key="archive.table.pob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 188.25px;" ><fmt:message key="archive.table.dob" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 146.25px;" ><fmt:message key="archive.table.archive.num" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 224.25px;" ><fmt:message key="archive.table.archive.date" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" ><fmt:message key="archive.table.archive.status" /></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 73.25px;" ><fmt:message key="archive.table.archive.action" /></th>
					</tr>
				</thead>
				<tbody>
				
		       <c:set var = "i"  value = "1"/>
			   <c:forEach var ="archive" items="${archives}">  
			   
			   
			   <c:if test ="${i%2==1}">  
				<tr role="row" class="odd clear-odd">
						<td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
						
						<td>${archive.person.surName}</td>
						<td>${archive.person.givenName}</td>
						<td>${archive.person.pob}</td>
						<fmt:formatDate value="${archive.person.dob}" pattern="dd/MM/yyyy" var="archivePOBDate" />
						<td>${archivePOBDate}</td>
						<td>${archive.archiveNumber}</td>
						<fmt:formatDate value="${archive.registrationDate}" pattern="dd/MM/yyyy hh.mm,s" var="archiveRegistrationDate" />
						<td>${archiveRegistrationDate}</td>
						
						
						 <c:if test = "${archive.status=='REGISTERED'}">
						   <td id='archive-status-${archive.id}'><fmt:message key="archive.table.status.registered" /></td>
						  </c:if>
						  
						  <c:if test = "${archive.status=='VALIDATED'}">
						    <td id='archive-status-${archive.id}'><fmt:message key="archive.table.status.validated" /></td>
						  </c:if>

						<td nowrap="">
                         <select name="action">

                            <%@include file="action.jsp" %>

                         </select>
                         </td>
					</tr>
				</c:if>
				
				<c:if test ="${i%2==0}">  
				<tr role="row" class="even dark-even">
						<td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
						
                        <td>${archive.person.givenName}</td>
						<td>${archive.person.surName}</td>
						<td>${archive.person.pob}</td>
						<fmt:formatDate value="${archive.person.dob}" pattern="dd/MM/yyyy" var="archivePOBDate" />
						<td>${archivePOBDate}</td>
						<td>${archive.archiveNumber}</td>
						<fmt:formatDate value="${archive.registrationDate}" pattern="dd/MM/yyyy hh.mm,s" var="archiveRegistrationDate" />
						<td>${archiveRegistrationDate}</td>


						 <c:if test = "${archive.status=='REGISTERED'}">
						   <td id='archive-status-${archive.id}'><fmt:message key="archive.table.status.registered" /></td>
						  </c:if>
						  
						  <c:if test = "${archive.status=='VALIDATED'}">
						    <td id='archive-status-${archive.id}'><fmt:message key="archive.table.status.validated" /></td>
						  </c:if>
					  
						<td nowrap="">
                         <select name="action">
                         
                         <%@include file="action.jsp" %>
                         </select>
                         </td>
					</tr>
				</c:if>
				
				
					
					<c:set var = "i"  value = "${i+1}"/>
				 </c:forEach>
					</tbody>
				
			</table>