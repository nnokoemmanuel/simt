<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	
		<div class="col-sm-12">
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th>#</th>
						<th><fmt:message key="consult.more.archive.num" /></th>
						<th><fmt:message key="specialiy.key" /></th>
						<th><fmt:message key="consult.more.archive.pv" /></th>
						<th><fmt:message key="consult.more.archive.exam.date" /></th>
						<th><fmt:message key="consult.more.archive.place" /></th>
						<th>Possible processes</th>
						
					</tr>
				</thead>
				<tbody>
				
					<c:set var = "i"  value = "1"/>
						<tr>
							<td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
							<td>${archive.archiveNumber}</td>
							<td>${archive.speciality.abbreviation}</td>
							<td>${archive.pvNumber }</td>
							<td><fmt:formatDate value="${archive.examDate }" type="date" pattern="dd-MM-yyyy"/></td>
							<td>${archive.examPlace }</td>	
							<td>
							    <c:if test="${ (empty archive.professionalCard || ((not empty archive.professionalCard) && archive.professionalCard.onProcess == 0)) && archive.onProcess == 0}">
							        <button type="button" title="New Professional Card" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${archive.person.id}/3/${archive.id}/Archive','kt_content');active_link('home-1');return false;">NP</button>
							    </c:if>
							    <c:if test="${archive.onProcess == 1  && (empty archive.professionalCard )}">
						        	<button type="button" title="New Professional Card (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>NP</button>
							    </c:if>
							    
							     <c:if test="${archive.onProcess == 1 && (not empty archive.professionalCard ) && archive.professionalCard.isPrinted == 0}">
						        	<button type="button" title="New Professional Card (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>NP</button>
							    </c:if>
							    
							    <c:if test="${ (not empty archive.professionalCard) && archive.professionalCard.isPrinted == 1 && archive.professionalCard.onProcess ==0 }">
							        <button type="button" title="Duplicate of Professional Card" class="btn" style="background-color:#1565C0; color:white; " onClick="closeNav();doGet('${pageContext.request.contextPath}/manageFile/process/record/Getform/${archive.person.id}/6/${archive.id}/Archive','kt_content');active_link('home-1');return false;">DP</button>
							    </c:if>
							    
							    <c:if test="${ (not empty archive.professionalCard) && archive.professionalCard.isPrinted == 1 && archive.professionalCard.onProcess > 0 }">
						        	<button type="button" title="Duplicate of Professional Card (process initiated)" class="btn" style="background-color:red; color:white;  " onClick="active_link('home-1');return false;" disabled>DP</button>
							    </c:if>
						    </td>					
						</tr>
					<c:set var = "i"  value = "${i+1}"/>			
				</tbody>
			</table>
		</div>				
				
</div>