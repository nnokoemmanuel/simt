<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="wrapper">
	<aside class="right-aside">
		<section class="content" id="invoice_statement">
			<div class="row">
				<div class="col-sm-12">
					<div class="panel" id="printableArea">
						<div class="panel-body">
							<span><strong>TRACKING INFORMATIONS</strong></span> <br>
							<div class="row">
								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-striped table-condensed"
											id="customtable">
											<thead>
												<tr >
													<th><strong>Date</strong></th>
													<th><strong><fmt:message key="user" /></strong></th>
													<th><strong>Operation</strong></th>

												</tr>
											</thead>
											<tbody>

												<c:forEach var="object" items="${data}">
													<tr align="left">
														<td><c:out value="${object.operationDate}" /></td>
														<td><c:out value="${object.user.username}" /></td>
														<td><c:out value="${object.operation}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

							</div>


						</div>
					</div>
				</div>
			</div>
		</section>
	</aside>
</div>
