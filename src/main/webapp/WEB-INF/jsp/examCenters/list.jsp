<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />

<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
<div class="kt-container  kt-container--fluid ">
	<h3 class="kt-subheader-search__title" style="margin-top:10px; margin-bottom:20px">
	      <fmt:message key="list.centers" />
	</h3>
</div>
  <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">

    		<div class="kt-portlet__body">
                <!--begin: Datatable -->
    		<table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; ">
				<thead style="background:#01A8F9;">
					<tr style="color:white" role="row">
                    <th class="sorting_asc" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;"><strong>No</strong></th>
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 176.35px;"><strong>Centre</strong></th>
					<sec:authorize access="hasRole('ROLE_MANAGE_TRAINING_CENTERS')">
					<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 176.35px;" ><strong><fmt:message key="capacity.student" /></strong></th>
			        </sec:authorize>
			        <th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" ><strong>Action</strong></th>
					</tr>
				</thead>
				        <tbody>
				            <c:set var = "i"  value = "1"/>
    						<c:forEach var="center" items="${centers}">
    						    <c:if test ="${i%2==1}">
                                    <tr  role="row" class="odd clear-odd">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td><c:out value="${center.name}" /></td>
                                        <sec:authorize access="hasRole('ROLE_MANAGE_TRAINING_CENTERS')">
                                        <td><input readOnly type="number" value="${center.maxStudent}" min="0" id="td-${center.id }"  onchange="persistCapacity('${pageContext.request.contextPath}','${center.id}');"  ondblclick="toggleEdition('${center.id}');"/> </td>                               
                                         </sec:authorize>
                                        <td>
                                          <c:if test="${session.status=='REGISTER' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Open')" >
                                               <fmt:message key="open" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                            <c:if test="${session.status=='OPEN' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Close')" >
                                               <fmt:message key="close" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                           
                                           <c:if test="${session.status=='CLOSE' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Reopen')" >
                                               <fmt:message key="reopen" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                        </td>
                                    </tr>
                            	</c:if>
                                <c:if test ="${i%2==0}">
                                    <tr  role="row" class="even dark-even">
                                        <td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
                                        <td><c:out value="${center.name}" /></td>
                                        <sec:authorize access="hasRole('ROLE_MANAGE_TRAINING_CENTERS')">
                                        <td><input readOnly type="number" value="${center.maxStudent}" min="0" id="td-${center.id }"  onchange="persistCapacity('${pageContext.request.contextPath}','${center.id}');"  ondblclick="toggleEdition('${center.id}');"/> </td>                               
                                        </sec:authorize>
                                        <td>
                                            <c:if test="${session.status=='REGISTER' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Open')" >
                                               <fmt:message key="open" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                            <c:if test="${session.status=='OPEN' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Close')" >
                                               <fmt:message key="close" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                           
                                           <c:if test="${session.status=='CLOSE' }">
                                            <a href="#"
                                               id="sessionId"
                                               type="button" class="btn btn-brand btn-elevate btn-icon-sm"
                                               onClick="manageStatus('${session.id}','${sessionDate }', 'Reopen')" >
                                               <fmt:message key="reopen" />     <!--  Edit odd-list values-->
                                            </a>
                                           </c:if>
                                        </td>
                                    </tr>
                            	</c:if>
                            <c:set var = "i"  value = "${i+1}"/>
                            </c:forEach>

                        </tbody>
			</table>
    			</div>
    	    </div>

</div>
<script>
toggleEdition=(id)=>{
	
	let td=document.getElementById("td-"+id);
	td.removeAttribute("readOnly");
}

persistCapacity=(baseUrl,id)=>{
	let td=document.getElementById("td-"+id);
	td.setAttribute("readOnly","readOnly");	
	//const url= basUrl +"/pv/maxCapacity?id="+id +"&maxStudent="+
	const max= document.getElementById ("td-"+id).value;
	const url= baseUrl +"/center/maxCapacity?id="+id +"&maxStudent="+max

	 doget(url).then(response=>{
		 
		  }).catch(error=>{
		   alert("bug created successfully");
		  });
}
</script>