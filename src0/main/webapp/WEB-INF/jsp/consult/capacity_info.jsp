<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="row">
	
		<div class="col-sm-12">
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th>#</th>
						<th><fmt:message key="student.specialitys" /></th>
						<th><fmt:message key="consult.more.capacity.info.category" /></th>
						<th><fmt:message key="consult.more.capacity.info.print.date" /></th>
					</tr>
				</thead>
				<tbody>
					<c:set var = "i"  value = "1"/>
					<c:forEach var="capacity" items="${certificates}">
						<tr>
							<td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
							<td>${capacity.number}</td>
							<td>${capacity.type}</td>
							<td>${capacity.printedDate}</td>						
						</tr>
					<c:set var = "i"  value = "${i+1}"/>
					</c:forEach>

				</tbody>
			</table>
		</div>				
				
</div>

