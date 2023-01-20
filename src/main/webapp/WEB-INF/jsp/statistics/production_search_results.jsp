<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>


<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />
<div id="content"  class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
 <div class="alert alert-light alert-elevate" role="alert">
    	<div class="kt-portlet kt-portlet--mobile">

    		<div class="kt-portlet__body">
                <!--begin: Datatable -->
    		    <table class="table table-striped- table-bordered table-hover table-checkable dataTable dtr-inline" id="kt_table_1" role="grid" aria-describedby="kt_table_1_info" style="position: relative; width: 1644px;">
				  	<thead style="background:#01A8F9;">
						<tr style="color:white"  role="row">
							<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending"><fmt:message key="app.categoryRegion.title" /></th>
							<c:forEach var="office" items="${offices}">
								<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">${office.abreviation }</th>
							</c:forEach>
							<th class="sorting" tabindex="0" aria-controls="kt_table_1" rowspan="1" colspan="1" style="width: 65.25px;" aria-label="Order ID: activate to sort column ascending">TOTAL</th>
						</tr>
				  	</thead>
				  	<tbody>
				  		<c:set var = "i"  value = "1"/>
				  		<c:forEach var="productionResult" items="${results}">
    						<tr  role="row" <c:if test ="${i%2==1}"> class="odd clear-odd" </c:if> 
                            	<c:if test ="${i%2==0}"> class="even dark-even" </c:if> >
                            	<c:set var = "total"  value = "0"/>
                               	<td >
                                	${productionResult.processType.abbreviation} 
                               	</td>
                                <c:forEach var="officeResult" items="${productionResult.result}">
                                    <td>
                                       ${officeResult.total} 
                                    </td> 
                                    <c:set var = "total"  value = "${total + officeResult.total }"/>	
                                </c:forEach> 
                                
                                    
                                    <td><strong>${total}</strong></td>
                        	</tr>
                           	<c:set var = "i"  value = "${i+1}"/>
                           	
                         </c:forEach>
                            
                         
                            <tr>
                            	<td >
                                           <strong>TOTAL</strong>
                                        </td>
                                        <c:set var = "grandTotal"  value = "0"/>
                                        <c:forEach var="officeTotal" items="${officeTotals}">
                                        	<td>
                                        	<strong>${officeTotal}</strong>
                                        	</td>
                                        	<c:set var = "grandTotal"  value = "${grandTotal + officeTotal }"/>
                                        </c:forEach>
                                        <td>
                                        <strong>${grandTotal}</strong>
                                        </td>
                            </tr>
                  </tbody>
			    </table>
			    
               
    		</div>
    	</div>
</div>
</div>