<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
	<head>
		<base href="">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key="app.title" /></title>
		<meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
		<meta name="description" content="Latest updates and statistic charts">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="${pageContext.request.contextPath}/assets/css/pages/wizard/wizard-4.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/media/logos/favicon.ico" />
		<link href="${pageContext.request.contextPath}/assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />

		<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.css" rel="stylesheet" type="text/css" />

		<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo-header.png" />
		<link href="${pageContext.request.contextPath}/assets/css/loading.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/assets/js/vendors/bootstrapvalidator/css/bootstrapValidator.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/assets/css/pages/wizard/wizard-1.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body id="overlay" class=" kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-aside--minimize kt-page--loading  ">

    <input type="hidden" value="${pageContext.request.contextPath}" id="baseUrl">


	<!--Header Mobile -->
		<div id="kt_header_mobile" class="kt-header-mobile  kt-header-mobile--fixed ">
			<div class="kt-header-mobile__logo">
				<a href="${pageContext.request.contextPath}/home">
					<img alt="Logo" src="assets/media/logos/favicon.ico" />
				</a>
			</div>
			<div class="kt-header-mobile__toolbar">
				<div class="kt-header-mobile__toolbar-toggler kt-header-mobile__toolbar-toggler--left" id="kt_aside_mobile_toggler"><span></span></div>
				<div class="kt-header-mobile__toolbar-toggler" id="kt_header_mobile_toggler"><span></span></div>
				<div class="kt-header-mobile__toolbar-topbar-toggler" id="kt_header_mobile_topbar_toggler"><i class="flaticon-more"></i></div>
			</div>
		</div>
     <!-- end:: Header Mobile -->
		
		
		
	   <div class="kt-grid kt-grid--hor kt-grid--root">	
       <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--ver kt-page">

		<!-- Start The Modal  with Title-->
        <div id="myModal" class="modal " >
         Modal content 
          <div class="modal-content modal-dialog">
             <div class="modal-header">
                <h4 id="title" class="modal-title" style="color: white;">Modal Header</h4>
                <button type="button" class="close" data-dismiss="modal"></button>
             </div>
            <div id="contenu" class="modal-body">
                <p>Some text in the Modal Body</p>
            </div>
          </div>
        </div>
        <!-- End The Modal with Title -->
        
       

		<!-- begin:: Aside -->
				<button class="kt-aside-close " id="kt_aside_close_btn"><i class="la la-close"></i></button>
				<div class="kt-aside  kt-aside--fixed  kt-grid__item kt-grid kt-grid--desktop kt-grid--hor-desktop" id="kt_aside">

					<!-- begin:: Aside Menu -->
					<div class="kt-aside-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_aside_menu_wrapper">
						<div id="kt_aside_menu" class="kt-aside-menu " data-ktmenu-vertical="1" data-ktmenu-scroll="1" data-ktmenu-dropdown-timeout="500">
							<ul class="kt-menu__nav ">
							<sec:authorize access="hasRole('ROLE_VIEW_RECORD') ">
								<li id ="home-1" class="kt-menu__item  " aria-haspopup="true"><a  onClick="doGet('${pageContext.request.contextPath}/consult/load','kt_content');active_link('home-1');return false;" class="kt-menu__link "><i class="kt-menu__link-icon flaticon-search-1"></i><span class="kt-menu__link-text"><fmt:message key="app.menu.home" /></span></a></li>
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_VIEW_USERS') ">
								<li id="create-user" class="kt-menu__item " ><a onClick="doGet('${pageContext.request.contextPath}/marine/user/list','kt_content');active_link('create-user');return false;" class="kt-menu__link " ><i class="kt-menu__link-icon flaticon2-user"></i><span class="kt-menu__link-text"><fmt:message key="app.menu.user" /></span></a></li>
								<li id="list-group" class="kt-menu__item " aria-haspopup="false"><a  onclick="doGet('${pageContext.request.contextPath}/marine/user/group/list','kt_content');active_link('list-group');return false;" class="kt-menu__link "><i class="kt-menu__link-icon flaticon2-avatar "></i><span class="kt-menu__link-text"><fmt:message key="app.menu.group" /></span></a></li>
								</sec:authorize>
								
                                <li id = "session-management" class="kt-menu__item" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/marine/session/list','kt_content');active_link('session-management');return false;" class="kt-menu__link"><i class="kt-menu__link-icon flaticon2-hourglass-1"></i><span class="kt-menu__link-text  "><fmt:message key="app.menu.session.management" /></span></a></li>
                                <sec:authorize access="hasRole('ROLE_VIEW_COURSES_AND_MODULES')">
								    <li id = "courses-and-modules-management" class="kt-menu__item kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown" data-ktmenu-submenu-toggle="click" aria-haspopup="false"><a class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.menu.manage.courses.modules" /></span><i class="kt-menu__hor-arrow la la-angle-down"></i><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
										 <div class="kt-menu__submenu kt-menu__submenu--classic kt-menu__submenu--left">
                                                    <ul class="kt-menu__subnav">
                                                            
                                                                <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/module/list/ALL','kt_content');active_link('courses-and-modules-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.modules" /></span></a></li>
                                                             
                                                             	<li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/course/list/ALL','kt_content');active_link('courses-and-modules-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.courses" /></span></a></li>
                                                                  
                                                    </ul>
                                         </div>
                                    </li>
								</sec:authorize>
                                
								<!-- <sec:authorize access="hasRole('ROLE_VIEW_STATISTICS') ">
									<li id="list-statistics" class="kt-menu__item " aria-haspopup="false"><a  onclick="doGet('${pageContext.request.contextPath}/statistics/list','kt_content');active_link('list-statistics');return false;" class="kt-menu__link "><i class="kt-menu__link-icon flaticon2-protection"></i><span class="kt-menu__link-text"><fmt:message key="app.menu.statistic" /></span></a></li>
								</sec:authorize> -->

							<!-- showArchiveDetails is in the js/archive.js directory -->

							</ul>
						</div>
					</div>
                 </div>
	     <!-- end:: Aside Menu -->
					
	
				<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper">

					
					<!-- begin:: Header -->
					<div id="kt_header" class="kt-header kt-grid kt-grid--ver  kt-header--fixed ">

						<!-- begin:: Aside -->
						<div class="kt-header__brand kt-grid__item  " id="kt_header_brand">
							<div class="kt-header__brand-logo">
								<a href="${pageContext.request.contextPath}/home">
									<img alt="Logo" src="images/logo-trans.png" />
								</a>
							</div>
						</div>

						<!-- end:: Aside -->

						<!-- begin:: Title -->

						<h3  class="kt-header__title kt-grid__item" style="color:#154f03;font-size:1.5em">
							<fmt:message key="app.name" /><br/>
						</h3>



						<!-- end:: Title -->

						<!-- begin: Header Menu -->
						<button class="kt-header-menu-wrapper-close" id="kt_header_menu_mobile_close_btn"><i class="la la-close"></i></button>
						<div class="kt-header-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_header_menu_wrapper">
							<div id="kt_header_menu" class="kt-header-menu kt-header-menu-mobile  kt-header-menu--layout-default ">
								<ul class="kt-menu__nav ">
								    <br/>
                                    <sec:authorize access="hasRole('ROLE_VIEW_FILE')">
								        <li id = "file-management" class="kt-menu__item kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown" data-ktmenu-submenu-toggle="click" aria-haspopup="true"><a class="kt-menu__link kt-menu__toggle"><span class="kt-menu__link-text"><fmt:message key="app.menu.manage.production" /></span><i class="kt-menu__hor-arrow la la-angle-down"></i><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
										 <div class="kt-menu__submenu kt-menu__submenu--classic kt-menu__submenu--left">
                                                    <ul class="kt-menu__subnav">
                                                                <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a id="load-file-management" onClick="doGet('${pageContext.request.contextPath}/manageFile/list','kt_content');active_link('file-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.file" /></span></a></li>
                                                                <sec:authorize access="hasRole('ROLE_VIEW_INSLIP') ">
		                                                             <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/inSlip/list','kt_content');active_link('file-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text">Manage In-Slip</span></a></li>
		                                                         </sec:authorize>
		                                                         <sec:authorize access="hasRole('ROLE_VIEW_OUTSLIP') ">
		                                                             <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/outSlip/list','kt_content');active_link('file-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text">Manage Out-Slip</span></a></li>
		                                                        </sec:authorize>
                                                               

                                                    </ul>
                                                </div>
                                        </li>
								    </sec:authorize>
									<sec:authorize access="hasRole('ROLE_VIEW_PV_EXPERTS_EXAM')">
									<li id = "concours-experts" class="kt-menu__item kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown" data-ktmenu-submenu-toggle="click" aria-haspopup="true"><a class="kt-menu__link kt-menu__toggle"><span class="kt-menu__link-text"><fmt:message key="app.menu.concours.pv" /></span><i class="kt-menu__hor-arrow la la-angle-down"></i><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
										 <div class="kt-menu__submenu kt-menu__submenu--classic kt-menu__submenu--left">
                                                    <ul class="kt-menu__subnav">

                                                             <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/expertPv/list','kt_content');active_link('concours-experts');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.pv" /></span></a></li>
                                                             
                                                    </ul>
                                                </div>
                                        </li>
                                    </sec:authorize>
                                    
                                    
									<li id = "student-management" class="kt-menu__item kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown" data-ktmenu-submenu-toggle="click" aria-haspopup="true"><a class="kt-menu__link kt-menu__toggle"><span class="kt-menu__link-text"><fmt:message key="app.menu.students" /></span><i class="kt-menu__hor-arrow la la-angle-down"></i><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
										 <div class="kt-menu__submenu kt-menu__submenu--classic kt-menu__submenu--left">
                                                    <ul class="kt-menu__subnav">

                                                             <sec:authorize access="hasRole('ROLE_MANAGE_STUDENTS_UNDER_TRAINING')">

                                                             <c:forEach var="iligibleSpecialitie" items="${user.trainingCenter.eligibleSpeciality}">
                                                                <c:if test="${iligibleSpecialitie.speciality.id == 1 }">

                                                                    <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/student/createGet','kt_content');active_link('student-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.create.student" /></span></a></li>
                                                                </c:if>
                                                             </c:forEach>

                                                             	<li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/manageCandidate/list','kt_content');active_link('student-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.candidates.list" /></span></a></li>
                                                             </sec:authorize>
                                                              <sec:authorize access="hasRole('ROLE_VIEW_PV')">
                                                             <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/pv/list','kt_content');active_link('student-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.pv" /></span></a></li>
                                                             <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/student/admittedList','kt_content');active_link('succeeded-student-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="manage.succeed.students" /></span></a></li>
                                                             
                                                             </sec:authorize>
                                                    </ul>
                                                </div>
                                        </li>
                                   
   
                                                  
<%--                                      <li id = "session-management" class="kt-menu__item" aria-haspopup="true"><a onClick="doGet('${pageContext.request.contextPath}/marine/session/list','kt_content');active_link('session-management');return false;" class="kt-menu__link "><span class="kt-menu__link-text"><fmt:message key="app.menu.session.management" /></span></a></li> --%>
                                     <sec:authorize access="hasRole('ROLE_VIEW_TRAINNING_CENTERS')">
										<li id = "exam-center" class="kt-menu__item kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown" data-ktmenu-submenu-toggle="click" aria-haspopup="true"><a class="kt-menu__link kt-menu__toggle"><span class="kt-menu__link-text"><fmt:message key="exam.center.management" /></span><i class="kt-menu__hor-arrow la la-angle-down"></i><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
										 
										 <div class="kt-menu__submenu kt-menu__submenu--classic kt-menu__submenu--left">
                                                    <ul class="kt-menu__subnav">
                                                            
                                                                <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/trainingCenter/list/ALL/0/-1','kt_content');active_link('exam-center');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.exam.center.management" /></span></a></li>
                                                                                                                           
                                                    </ul>
                                                </div>
                                        </li>
								    
								    </sec:authorize>
								   <%--  <sec:authorize access="hasRole('ROLE_VIEW_COURSES_AND_MODULES')">
								    <li id = "courses-and-modules-management" class="kt-menu__item kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown" data-ktmenu-submenu-toggle="click" aria-haspopup="true"><a class="kt-menu__link kt-menu__toggle"><span class="kt-menu__link-text"><fmt:message key="app.menu.manage.courses.modules" /></span><i class="kt-menu__hor-arrow la la-angle-down"></i><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
										 <div class="kt-menu__submenu kt-menu__submenu--classic kt-menu__submenu--left">
                                                    <ul class="kt-menu__subnav">
                                                            
                                                                <li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/module/list/ALL','kt_content');active_link('courses-and-modules-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.modules" /></span></a></li>
                                                             
                                                             	<li class="kt-menu__item kt-menu__item--submenu" aria-haspopup="false"><a onClick="doGet('${pageContext.request.contextPath}/course/list/ALL','kt_content');active_link('courses-and-modules-management');return false;" class="kt-menu__link kt-menu__toggle" ><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text"><fmt:message key="app.manage.courses" /></span></a></li>
                                                                  
                                                    </ul>
                                                </div>
                                        </li>
								    </sec:authorize> --%>

                                    <sec:authorize access="hasRole('ROLE_VIEW_ARCHIVE')">
                                        <li id = "archive-pv" class="kt-menu__item kt-menu__item--submenu kt-menu__item--rel kt-menu__item--open-dropdown" data-ktmenu-submenu-toggle="click" aria-haspopup="true"><a onClick="doGet('${pageContext.request.contextPath}/archive/list','kt_content');active_link('archive-pv');return false;" class="kt-menu__link kt-menu__toggle"><span class="kt-menu__link-text"><fmt:message key="app.menu.archive.pv" /></span><i class="kt-menu__hor-arrow la la-angle-down"></i><i class="kt-menu__ver-arrow la la-angle-right"></i></a>

                                        </li>
                                    </sec:authorize>
								</ul>
							</div>
						</div>

						<!-- end: Header Menu -->

						<!-- begin:: Header Topbar -->
						<div class="kt-header__topbar">

							<!--begin: Search -->
							<!-- <div class="kt-header__topbar-item kt-header__topbar-item--search dropdown" id="kt_quick_search_toggle">
								<div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
									<span class="kt-header__topbar-icon"><i class="flaticon2-search-1"></i></span>
								</div>
								<div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-lg">
									<div class="kt-quick-search kt-quick-search--dropdown kt-quick-search--result-compact" id="kt_quick_search_dropdown">
										<form method="get" class="kt-quick-search__form">
											<div class="input-group">
												<div class="input-group-prepend"><span class="input-group-text"><i class="flaticon2-search-1"></i></span></div>
												<input type="text" class="form-control kt-quick-search__input" placeholder="Search...">
												<div class="input-group-append"><span class="input-group-text"><i class="la la-close kt-quick-search__close"></i></span></div>
											</div>
										</form>
										<div class="kt-quick-search__wrapper kt-scroll" data-scroll="true" data-height="325" data-mobile-height="200">
										</div>
									</div>
								</div>
							</div> -->

							<!--end: Search -->

							<!--begin: Notifications -->
							<!--<div class="kt-header__topbar-item dropdown">
								<div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
									<span class="kt-header__topbar-icon kt-header__topbar-icon--success"><i class="flaticon2-bell-alarm-symbol"></i></span>
									<span class="kt-hidden kt-badge kt-badge--danger"></span>
								</div>
								<div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-xl">
									<form>


										<div class="kt-head kt-head--skin-dark kt-head--fit-x kt-head--fit-b" style="background-image: url(assets/media/misc/bg-1.jpg)">
											<h3 class="kt-head__title">
												<fmt:message key="app.user.notifications" />
												&nbsp;
												<span id="notifications-counter" class="btn btn-success btn-sm btn-bold btn-font-md">23 new</span>
											</h3>
											<ul class="nav nav-tabs nav-tabs-line nav-tabs-bold nav-tabs-line-3x nav-tabs-line-success kt-notification-item-padding-x" role="tablist">
												<li class="nav-item">
													<a class="nav-link active show" data-toggle="tab" href="#topbar_notifications_notifications" role="tab" aria-selected="true"><fmt:message key="app.user.notification.alerts" /></a>
												</li>

											</ul>
										</div>


										 <div class="tab-content">
											<div class="tab-pane active show" id="topbar_notifications_notifications" role="tabpanel">
												<div class="kt-notification kt-margin-t-10 kt-margin-b-10 kt-scroll" data-scroll="true" data-height="300" data-mobile-height="200">
													<a href="#" class="kt-notification__item">
														<div class="kt-notification__item-icon">
															<i class="flaticon2-line-chart kt-font-success"></i>
														</div>
														<div class="kt-notification__item-details">
															<div class="kt-notification__item-title">
																New order has been received
															</div>
															<div class="kt-notification__item-time">
																2 hrs ago
															</div>
														</div>
													
													
												
												</div>
											</div>
											<div class="tab-pane" id="topbar_notifications_events" role="tabpanel">
												<div class="kt-notification kt-margin-t-10 kt-margin-b-10 kt-scroll" data-scroll="true" data-height="300" data-mobile-height="200">
													<a href="#" class="kt-notification__item">
														<div class="kt-notification__item-icon">
															<i class="flaticon2-psd kt-font-success"></i>
														</div>
														<div class="kt-notification__item-details">
															<div class="kt-notification__item-title">
																New report has been received
															</div>
															<div class="kt-notification__item-time">
																23 hrs ago
															</div>
														</div>
													</a>
													
												</div>
											</div>
											<div class="tab-pane" id="topbar_notifications_logs" role="tabpanel">
												<div class="kt-grid kt-grid--ver" style="min-height: 200px;">
													<div class="kt-grid kt-grid--hor kt-grid__item kt-grid__item--fluid kt-grid__item--middle">
														<div class="kt-grid__item kt-grid__item--middle kt-align-center">
															All caught up!
															<br>No new notifications.
														</div>
													</div>
												</div>
											</div>
										</div>
										
										
									</form>
								</div>
							</div> -->

						

							<!--begin: Language bar kt-header__topbar-icon--brand-->
							<div class="kt-header__topbar-item kt-header__topbar-item--langs">
								<div id="langDiv" class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
									<span class="kt-header__topbar-icon" id="francais">
										FR
									</span>
								</div>
								<div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim">
									<ul class="kt-nav kt-margin-t-10 kt-margin-b-10">
									   <c:set var="lang" value="${pageContext.response.locale}" />
										<li id="kt-nav__itemEn" class="kt-nav__item ">
											<a  href="${pageContext.request.contextPath}/home?lang=en" class="kt-nav__link">
												<span class="kt-nav__link-text"><fmt:message key="app.user.langue.english" /></span>
											</a><script src="${pageContext.request.contextPath}/js/inslip/list_inslip.js" type="text/javascript"></script>
											
										</li>
										<li id="kt-nav__itemFr" class="kt-nav__item ">
											<a href="${pageContext.request.contextPath}/home?lang=fr" class="kt-nav__link">
												<span class="kt-nav__link-text"><fmt:message key="app.user.langue.french" /></span>
											</a>
										</li>
										
									</ul>
								</div>
							</div>

							<!--end: Language bar -->

							<!--begin: User bar -->
							<div class="kt-header__topbar-item kt-header__topbar-item--user">
								<div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
									<span class="kt-hidden kt-header__topbar-welcome">Hi,</span>
									<span class="kt-hidden kt-header__topbar-username">Nick</span>
									<span class="kt-header__topbar-icon kt-hidden-" style="background:white;">
									    
				                    <c:if test="${empty user.image}">
				                        <img src="${pageContext.request.contextPath}/assets/media/users/default.jpg" alt="image">
                                    </c:if>
                                   <c:if test="${not empty user.image}">
						                <img  alt="Pic" src="${pageContext.request.contextPath}/file/download?fileName=${user.image}&folderName=user.profile.folder" />
                                   </c:if>
									
									</span>
								</div>
								<div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-xl">

									<!--begin: Head -->
									<div class="kt-user-card kt-user-card--skin-dark kt-notification-item-padding-x" style="background-image: url(assets/media/misc/bg-1.jpg)">
										<div class="kt-user-card__avatar">
											
											<!--use below badge element instead the user avatar to display username's first letter(remove kt-hidden class to display it) -->
											
											<span class="kt-badge kt-badge--lg kt-badge--rounded kt-badge--bold kt-font-success" style="text-transform:uppercase;">${fn:substring(user.firstName, 0, 1)}${fn:substring(user.lastName, 0, 1)} </span>
										</div>
										<div class="kt-user-card__name">
											<span style="text-transform:capitalize">${user.firstName } ${user.lastName} </span>
										</div>
										
									</div>

									<!--end: Head -->

									<!--begin: Navigation -->
									<div class="kt-notification">
										<a href="#" onclick="doGet('${pageContext.request.contextPath}/marine/user/profile', 'kt_content');" class="kt-notification__item">
											<div class="kt-notification__item-icon">
												<i class="flaticon2-calendar-3 kt-font-success"></i>
											</div>
											<div class="kt-notification__item-details">
												<div class="kt-notification__item-title kt-font-bold">
											        <fmt:message key="app.user.profile" />
												</div>
												<div class="kt-notification__item-time">
													 <fmt:message key="app.user.profile.settings" />
												</div>
											</div>
										</a>
										<div class="kt-notification__custom kt-space-between">
											<a href="${pageContext.request.contextPath}/logout"  class="btn btn-label btn-label-brand btn-sm btn-bold"><fmt:message key="app.user.profile.sign.out" /></a>
<%-- 											<a href="custom/user/login-v2.html" target="_blank" class="btn btn-clean btn-sm btn-bold"><fmt:message key="app.user.profile.upgrade.plan" /></a> --%>
										</div>
									</div>

									<!--end: Navigation -->
								</div>
							</div>

							
						</div>

						<!-- end:: Header Topbar -->
					</div>

					<!-- end:: Header -->
					

					<div   class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">

                      	  <jsp:include page="user/user_profile.jsp" /> 
                      	
                      
					</div>



					<!-- begin:: Footer -->
					<div  id="kt_footer_id" class="kt-footer  kt-grid__item kt-grid kt-grid--desktop kt-grid--ver-desktop" id="kt_footer">
						<div class="kt-container  kt-container--fluid ">
						    <div class="kt-footer__menu">
                        		<h5 > <fmt:message key="app.full.name" /></h5>
                        	</div>
							<div class="kt-footer__copyright">
								2020&nbsp;&copy;&nbsp;<a href="http://keenthemes.com/metronic" target="_blank" class="kt-link">Presprint Plc All Right Reserved</a>
							</div>
							<div class="kt-footer__menu">
								<a href="#"  class="kt-footer__menu-link kt-link"><fmt:message key="app.name.short" /><span style="margin-left:2px;">${version}</span></a>
							</div>
						</div>
					</div>

					<!-- end:: Footer -->
					
					
					
				</div>
			</div>
		</div>

		<!-- end:: Page -->

	
		<!-- begin::Global Config(global config for global JS sciprts) -->
		<script>
			var KTAppOptions = {
				"colors": {
					"state": {
						"brand": "#22b9ff",
						"light": "#ffffff",
						"dark": "#282a3c",
						"primary": "#5867dd",
						"success": "#34bfa3",
						"info": "#36a3f7",
						"warning": "#ffb822",
						"danger": "#fd3995"
					},
					"base": {
						"label": ["#c5cbe3", "#a1a8c3", "#3d4465", "#3e4466"],
						"shape": ["#f0f3ff", "#d9dffa", "#afb4d4", "#646c9a"]
					}
				}
			};
		</script>

		<!-- end::Global Config -->

		<!--begin::Global Theme Bundle(used by all pages) -->
		
		
		<script src="${pageContext.request.contextPath}/assets/plugins/global/plugins.bundle.js" type="text/javascript"></script>

		<script src="${pageContext.request.contextPath}/assets/js/scripts.bundle.js" type="text/javascript"></script>

		<!--end::Global Theme Bundle -->

		<!--begin::Page Vendors(used by this page) -->
		<script src="${pageContext.request.contextPath}/assets/plugins/custom/fullcalendar/fullcalendar.bundle.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/plugins/custom/gmaps/gmaps.js" type="text/javascript"></script>

		<!--end::Page Vendors -->

		<!--begin::Page Scripts(used by this page) -->
		<script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/extensions/keytable.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/js/pages/dashboard.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/helper.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/js/pages/user/profile.js" type="text/javascript"></script>
		<!--<script src="${pageContext.request.contextPath}/assets/js/pages/user/create.js" type="text/javascript"></script>-->
		<script src="${pageContext.request.contextPath}/js/core.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/archive/archives.js" type="text/javascript"></script>
		
		<script src="${pageContext.request.contextPath}/js/inslip/list_inslip.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/outslip/list_outslip.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/plugins/custom/datatables/datatables.bundle.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/assets/js/pages/crud/datatables/advanced/row-callback.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/pv/pv.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/production/processingForm.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/production/manage_files.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/statistics/statistics.js" type="text/javascript"></script>
          <script src="${pageContext.request.contextPath}/js/pv/trainingCenter.js" type="text/javascript"></script>
		<!--end::Page Scripts -->

	  <!--begin::Page Scripts(polling user notifications) -->
       <script src="${pageContext.request.contextPath}/js/push-notifications.js" type="text/javascript"></script>
       <script src="${pageContext.request.contextPath}/assets/js/pages/crud/forms/widgets/bootstrap-daterangepicker.js" type="text/javascript"></script>
       <script src="${pageContext.request.contextPath}/assets/js/vendors/bootstrapvalidator/js/bootstrapValidator.min.js" type="text/javascript"></script>
         
        <script src="${pageContext.request.contextPath}/js/pdf.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/pdf.worker.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/pv/candidateSession.js" type="text/javascript"></script>
        
		<script src="${pageContext.request.contextPath}/assets/js/pages/custom/user/add-user.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/js/pages/custom/user/add-group.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/js/pages/custom/wizard/wizard-1.js" type="text/javascript"></script>

		<!--end::Page Scripts -->
	
      
	</body>

	<!-- end::Body -->
</html>
