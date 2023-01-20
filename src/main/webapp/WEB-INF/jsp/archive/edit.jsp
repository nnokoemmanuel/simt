<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
						<div class="kt-subheader-search ">
							<div class="kt-container  kt-container--fluid ">
								<h6 class="kt-subheader-search__title">
									<fmt:message key="edit.archive.capec" />
								</h6>
							</div>
						</div>

						<!-- begin:: Content -->
						<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
							<div class="row">
								<div class="col-lg-12">

									<!--begin::Portlet-->
									<div class="kt-portlet" id="editArchiveFormId">

										<!--begin::Form-->
										<form class="kt-form kt-form--label-right" id="add_archive_form" name="add_archive_form">

									<div class="kt-portlet">
										<div class="kt-portlet__head">
											<div class="kt-portlet__head-label">
												<h3 class="kt-portlet__head-title" style="color:black;">
												  <fmt:message key="archive.category.infor" /> ${archive.person.surName} ${archive.person.givenName} <fmt:message key="archive.category.message" />${archive.person.licenseNum}
												</h3>
											</div>
										</div>

											<!--begin::Form-->
										
											<div class="kt-portlet__body">
												<div class="form-group row">
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.licenseNumber" /></label>
														<input name="archiveNumber" id="archiveNumber" type="text" class="form-control"  value="${archive.archiveNumber}">
													</div>
                                                    <div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.pvNumber" /></label>
														<input name="pvNumber" id="pvNumber" type="text" class="form-control" value="${archive.pvNumber}" pattern="^[1-9][0-9]*$">
													</div>
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedCountry" /></label>
														<select name="issuedCountry" id="issuedCountry" class="form-control">
																<c:forEach var="country" items="${countries}">
											                    <c:if test="${country.id == archive.person.nationality.id}">
													            <option value="${country.id}" selected>${country.countryName}</option>
													            </c:if>
													            <c:if test="${country.id != archive.person.nationality.id}">
											                    <option value="${country.id }">${country.countryName}</option>
											                    </c:if>
										                        </c:forEach>
														</select>
													</div>
												</div>
												<div class="form-group row">
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedPlace" /></label>
														<input name="issuedPlace" id="issuedPlace" type="text" class="form-control" value="${archive.issuedPlace}">
													</div>
                                                    <div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedDate" /></label>
														<fmt:formatDate value="${archive.issuedDate}" pattern="dd/MM/yyyy" var="issuedDate" />
														<input id="issuedDate" name="issuedDate" type="text" class="form-control date-picker" value="${issuedDate}" placeholder="dd/mm/yyyy">
													</div>
                                                    <div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.service" /></label>
														<select  name="archiveBureau" id="archiveBureau" class="form-control">
																<c:if  test="${not empty archive.service}">
																	<option value="MINFOPRA" selected>${archive.service}</option>
					                                            </c:if>
					                                            <c:if  test="${ empty archive.service}">
																	<option value="MINFOPRA" selected>MINFOPRA</option>
					                                            </c:if>
														</select>
													</div>
													
												</div>
												<div class="form-group row">
													<div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.examPlace" /></label>
														<input name="examPlace" id="examPlace" type="text" class="form-control" value="${archive.examPlace}">
													</div>
                                                    <div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.examDate" /></label>
														<fmt:formatDate value="${archive.examDate}" pattern="dd/MM/yyyy" var="examDate" />
														<input name="examDate" id="examDate" type="text" class="form-control date-picker" value="${examDate}" placeholder="dd/mm/yyyy">
													</div>
				                                    <div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.issuedAuthority" /></label>
														<select  name="issuedAuthority" id="issuedAuthority" class="form-control">
																<c:forEach var="authority" items="${authorities}">
											                    <option value="${authority.id }">${authority.position}</option>
										                        </c:forEach>
										                </select>
													</div>
												</div>
                                                <div class="form-group row">
                                                    <div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.grade" /></label>
														<input name="grade" id="grade" type="text" class="form-control" value="${archive.grade}">
													</div>
                                                    <div class="col-lg-4">
														<label style="color:#564FC1;"><fmt:message key="archive.finalAverage" /></label>
														<input name="finalAverage" id="finalAverage" type="text" class="form-control" value="${archive.finalAverage}">
													</div>
												</div>
											</div>

										<!--end::Form-->
									</div>

									<!--end::Portlet-->

									<!--begin::Portlet-->
									<div class="kt-portlet">
										<div class="kt-portlet__head">
											<div class="kt-portlet__head-label">
												<h3 class="kt-portlet__head-title" style="color:#564FC1;">
													<fmt:message key="attachments.details" />
												</h3>
											</div>
										</div>
									
											<div id="filesContainer" class="kt-portlet__body">
												<div class="form-group row ">
												    <div class="col-lg-8 ">
												    <c:set var = "i"  value = "1"/>
                                                        <c:forEach var="archiveFile" items="${archive.archiveFiles}">
                                                                <input value="archive file${i}" type="button" onclick=window.open("${pageContext.request.contextPath}/file/download?fileName=${archiveFile.fileName}&folderName=archive.file.folder") >
                                                                <c:set var = "i"  value = "${i+1}"/>
                                                        </c:forEach>
                                                    </div>
													    <div class="col-lg-4 ">
                                                        <label><fmt:message key="new.file.label" /></label>

                                                        <div class="custom-file">
                                                            <div class="kt-avatar" id="kt_user_avatar_file">
                                                                <div id="fileHolder" class="kt-avatar__holder" style="background-image: url(assets/media/files/pdf.svg)"></div>
                                                                <canvas id="pdf-canvas" width="200" height="200" class="pdf-canvas" style="width: 180px; max-width: 180px; max-height: 190px;border: 1px solid rgba(0,0,0,0.2);box-sizing: border-box; display:none;"></canvas>
                                                                <label id="changeFile" class="kt-avatar__upload" data-toggle="kt-tooltip" title="" data-original-title="Change avatar">
                                                                    <i class="fa fa-pen"></i>
                                                                    <input name="fileInput" id="fileInput" class="kt-avatar__input_file" type="file" name="profile_avatar" accept=".png, .jpg, .jpeg, .pdf">
                                                                </label>
                                                                <span id="fileCancel" class="kt-avatar__cancel" data-toggle="kt-tooltip" title="" data-original-title="Cancel avatar">
                                                                    <i class="fa fa-times"></i>
                                                                </span>
                                                            </div>

                                                        </div>
												    </div>
									
											</div>
											<div class="kt-separator kt-separator--border-dashed kt-separator--space-lg kt-separator--portlet-fit"></div>
											</div>
											<div class="kt-portlet__foot">
												<div class="kt-form__actions">
													<div class="row">
														<div class="col-lg-5"></div>
														<div class="col-lg-7">
															<button type="reset" class="btn btn-secondary"><fmt:message key="archive.edit.cancel.button" /></button>
															<button class="btn btn-brand" onclick="validEditedArchive(${archive.id});" ><fmt:message key="archive.edit.submit.button" /></button>
														</div>
													</div>
												</div>
											</div>
									

										<!--end::Form-->
									</div>
	                                </form>
									<!--end::Portlet-->
								</div>
							</div>
						</div>

						<!-- end:: Content -->
						<script>
                        pushAvatarsEvents("changeFile","fileInput","fileHolder","kt_user_avatar_file","fileCancel");
                        datePickersInitializer();
                        addDataTable();
						</script>
						
