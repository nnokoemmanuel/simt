<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="row">
	
		<div class="col-sm-12">
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th>#</th>
						<th><fmt:message key="consult.app.inslip" /></th>
						<th><fmt:message key="consult.app.num" /></th>
						<th><fmt:message key="consult.app.file.num" /></th>
						<th><fmt:message key="consult.app.office" /></th>
						<th><fmt:message key="consult.app.process.type" /></th>
						<th><fmt:message key="consult.app.registration.date" /></th>
						<th><fmt:message key="consult.app.delivery.date" /></th>
						<th><fmt:message key="consult.app.status" /></th>
					</tr>
				</thead>
				<tbody>
					<c:set var = "i"  value = "1"/>
						<c:forEach var="application" items="${applications}">
							<tr>
								<td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
								<td>${application.inSlip.referenceNumber}</td>
								<td>${application.number}</td>
								<td>${application.serialNumber}</td>
								<td>${application.office.name }</td>
								<td>${application.processType.description }</td>
								<td><fmt:formatDate value="${application.computerizationDate }" type="date" pattern="dd-MM-yyyy"/></td>
								<td><fmt:formatDate value="${application.issueDate }" type="date" pattern="dd-MM-yyyy"/></td>
								<td>${application.applicationStatus.description }</td>						
							</tr>
						<c:set var = "i"  value = "${i+1}"/>			
						</c:forEach>
				</tbody>
			</table>
		</div>				
				
</div>