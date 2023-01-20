<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<script src="assets/js/pages/crud/datatables/data-sources/html.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/group/group.js" type="text/javascript"></script>

<div class="col-sm-12">
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
							<div class="alert alert-light alert-elevate" role="alert">
								<div class="alert-icon"><i class="flaticon-group kt-font-brand"></i></div>
								<div class="alert-text">
								<h1><fmt:message key="app.title.group.list" /></h1></div>
							</div>
							<div class="kt-portlet kt-portlet--mobile">
								<div class="kt-portlet__head kt-portlet__head--lg">
									<div class="kt-portlet__head-label">
										<span class="kt-portlet__head-icon">
											<i class="kt-font-brand flaticon2-line-chart"></i>
										</span>
										<h3 class="kt-portlet__head-title">
											<fmt:message key="app.title.group.table" />
										</h3>
									</div>
									<sec:authorize access="hasRole('ROLE_ADMIN')">
									<div class="kt-portlet__head-toolbar">

												&nbsp;
												<a href="#" class="btn btn-brand btn-elevate btn-icon-sm app-color" onclick="doGet('${pageContext.request.contextPath}/marine/user/form','kt_content');">
													<i class="la la-plus"></i>
													New Record
												</a>
									</div>
									 </sec:authorize>
									</div>
									<div class="kt-portlet__body">

									<!--begin: Datatable -->
									<table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
										<thead>
											<tr>
												<th>S/N</th>
												<th>Group Name</th>
												<th>Actions</th>
											</tr>
										</thead>
										<tbody>
										<c:set var = "i"  value = "1"/>
										<c:forEach var="userGroup" items="${groups}">
											<tr>
												<td tabindex="0" class="sorting_1"><c:out value = "${i}"/></td>
												<td>${userGroup.name}</td>
												<td nowrap>
                       
							                        <button class="btn btn-sm btn-clean btn-icon btn-icon-md" title="View" value="${userGroup.id}" onclick="doGet('${pageContext.request.contextPath}/marine/user/group/view/?id=${userGroup.id}' , 'kt_content');"><i class="la la-edit" ></i></button>
							                       <sec:authorize access="hasRole('ROLE_ADMIN')">
							                          <button class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit" value="${userGroup.id}" onclick="openNav('Edit Group', '', '70%');doGet('${pageContext.request.contextPath}/marine/user/group/edit/load?id=${userGroup.id}' , 'contenu');"><i class="la la-pencil"></i></button>
                                                   </sec:authorize>
                                                    <!-- <button class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Delete" onclick="deleteGroup('${userGroup.id}');"><i class="la la-close"></i></button> -->
                        						</td>
												
											</tr>
											<c:set var = "i"  value = "${i+1}"/>
										</c:forEach>
										</tbody>
									</table>

									<!--end: Datatable -->
								</div>
									</div>
								</div>

							</div>
						</div>



</div>

