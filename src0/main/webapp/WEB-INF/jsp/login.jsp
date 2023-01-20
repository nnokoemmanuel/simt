

	<!--  S.I.M.T --->


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

	<!-- begin::Head -->
	<head>
		<base href="../../../">
		<meta charset="utf-8" />
		<title>LOGIN | SIMT</title>
		<meta name="description" content="Login page example">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!--begin::Page Custom Styles(used by this page) -->
		<link href="${pageContext.request.contextPath}/assets/css/pages/login/login-3.css" rel="stylesheet" type="text/css" />
        <!--begin::Fonts -->

		<link href="${pageContext.request.contextPath}/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />

		<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/media/logos/favicon.ico" />
	</head>

	<!-- end::Head -->

	<body class="kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-aside--minimize">

		<!-- begin:: Page -->
		<div class="kt-grid kt-grid--ver kt-grid--root kt-page">
			<div class="kt-grid kt-grid--hor kt-grid--root  kt-login kt-login--v1" id="kt_login">
				<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--desktop kt-grid--ver-desktop kt-grid--hor-tablet-and-mobile">

					<!--begin::Aside-->
					<div class="kt-grid__item kt-grid__item--order-tablet-and-mobile-2 kt-grid kt-grid--hor kt-login__aside" style="background-image: url(${pageContext.request.contextPath}/assets/media/bg/bg-4.jpg);"> 
						<div class="kt-grid__item">
						
						    <br><br><br><br><br><br>
						    <br><br><br><br><br><br>
						    
							<a  class="kt-login__logo">
								<center><img src="${pageContext.request.contextPath}/images/logo100x100.png"></center>
							</a>
							
							<br><br>
							
							<h3 class="kt-login__title" style="position:center; text-align: center; color: white;"><center><b>S.S.D.T</b></center></h3>
							<h4 class="kt-login__subtitle" style="position:center; text-align: center; color: white;"><center>SYSTEME DE SECURISATION DES DOCUMENTS DE TRANSPORT</center></h4>
							<h4 class="kt-login__subtitle" style="position:center; text-align: center; color: white;"><center>SECURISATION SYSTEM FOR TRANSPORT DOCUMENTS</center></h4>
								
						</div>
						<div style="visibility:hidden;" class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--ver">
							<div class="kt-grid__item kt-grid__item--middle">
								<h3 class="kt-login__title" style="visibility:hidden; position:center; text-align: center; color: white;">
								<h4 class="kt-login__subtitle" style="visibility:hidden; position:center; text-align: center; color: white;">
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 
							</div>
						</div>
						<div style="visibility:hidden;" class="kt-grid__item">
							<div class="kt-login__info">
								<div class="kt-login__copyright">
									<center>� 2020 Presprint Plc All Right Reserved</center>
								</div>
								<div class="kt-login__menu">
									<a style="visibility:hidden;" href="#" class="kt-link">Privacy</a>
									<a style="visibility:hidden;" href="#" class="kt-link">Legal</a>
									<a style="visibility:hidden;" href="#" class="kt-link">Contact</a>
								</div>
							</div>
						</div>
					</div>

					<!--begin::Aside-->

					<!--begin::Content-->
					<div class="kt-grid__item kt-grid__item--fluid  kt-grid__item--order-tablet-and-mobile-1  kt-login__wrapper">

						<!--begin::Head
						<div class="kt-login__head">
							<span class="kt-login__signup-label">Don't have an account yet?</span>&nbsp;&nbsp;
							<a href="#" class="kt-link kt-login__signup-link">Sign Up!</a>
						</div> -->

						<!--end::Head-->

						<!--begin::Body-->
						<div class="kt-login__body">

							<!--begin::Signin
							<div class="kt-login__form">
								<div class="kt-login__title">
									<h3>Sign In</h3>
								</div> -->

								<!--begin::Form
								<form class="kt-form" action="" novalidate="novalidate" id="kt_login_form">
									<div class="form-group">
										<input class="form-control" type="text" placeholder="Username" name="username" autocomplete="off">
									</div>
									<div class="form-group">
										<input class="form-control" type="password" placeholder="Password" name="password" autocomplete="off">
									</div> -->

									<!--begin::Action
									<div class="kt-login__actions">
										<a href="#" class="kt-link kt-login__link-forgot">
											Forgot Password ?
										</a>
										<button id="kt_login_signin_submit" class="btn btn-primary btn-elevate kt-login__btn-primary">Sign In</button>
									</div> -->

									<!--end::Action
								</form> -->
								
								
						<!-- begin:: Page -->
		<div class="kt-grid kt-grid--ver kt-grid--root kt-page">
			<div class="kt-grid kt-grid--hor kt-grid--root  kt-login kt-login--v3 kt-login--signin" id="kt_login">
				<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" style="background-image: url(${pageContext.request.contextPath}/assets/media/bg/bg-3.jpg);">
					<div class="kt-grid__item kt-grid__item--fluid kt-login__wrapper">
						<div id="Div1" style="margin-bottom:19px" class="kt-login__container" >
							<div style="visibility:hidden;" class="kt-login__logo" >
							
								 <a href="#">
									<img src="${pageContext.request.contextPath}/images/logo100x100.png">
								</a> 
							</div>




							
							<div class="kt-login__signin">
								<div class="kt-login__head">
									<h3 class="kt-login__title">Authentication</h3>
								</div>
								
								 <c:if test="${not empty param.error}">
			                         <div class="uk-form-row">
                        	            <p style="color:red; text-align:center">Bad Credential, PLease Try Again</p>
                    	             </div>   
		                       </c:if>
	                           
								<form class="kt-form" action="${pageContext.request.contextPath}/login" method="POST">
								     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									<div class="input-group">
										<input class="form-control" type="text" placeholder="Username" name="username" autocomplete="off">
									</div>
									<div class="input-group">
										<input class="form-control" type="password" placeholder="Password" name="password">
									</div>
									<div class="row kt-login__extra">
										<div class="col" style="visibility:hidden">
											<label class="kt-checkbox">
												<input type="checkbox" name="remember"> Remember me
												<span></span>
											</label>
										</div>
										<div class="col kt-align-right">
											<a onclick="switchVisible_Div1_Div2();" href="javascript:;" id="kt_login_forgot" class="kt-login__link">Forget Password ?</a>
										</div>
									</div>
									<div class="kt-login__actions">
										<button id="kt_login_signin_submit12" type="submit" class="btn btn-brand btn-elevate kt-login__btn-primary">Sign In</button>
									</div>
								</form>
							</div>




							<div class="kt-login__signup">
								<div class="kt-login__head">
									<h3 class="kt-login__title">Sign Up</h3>
									<div class="kt-login__desc">Enter your details to create your account:</div>
								</div>
								<form class="kt-form" action="">
									<div class="input-group">
										<input class="form-control" type="text" placeholder="Fullname" name="fullname">
									</div>
									<div class="input-group">
										<input class="form-control" type="text" placeholder="Email" name="email" autocomplete="off">
									</div>
									<div class="input-group">
										<input class="form-control" type="password" placeholder="Password" name="password">
									</div>
									<div class="input-group">
										<input class="form-control" type="password" placeholder="Confirm Password" name="rpassword">
									</div>
									<div class="row kt-login__extra">
										<div class="col kt-align-left">
											<label class="kt-checkbox">
												<input type="checkbox" name="agree">I Agree the <a href="#" class="kt-link kt-login__link kt-font-bold">terms and conditions</a>.
												<span></span>
											</label>
											<span class="form-text text-muted"></span>
										</div>
									</div>
									<div class="kt-login__actions">
										<button id="kt_login_signup_submit" class="btn btn-brand btn-elevate kt-login__btn-primary">Sign Up</button>&nbsp;&nbsp;
										<button id="kt_login_signup_cancel" class="btn btn-light btn-elevate kt-login__btn-secondary">Cancel</button>
									</div>
								</form>
							</div>





							





							<div  style="visibility:hidden" class="kt-login__account">
								<span class="kt-login__account-msg">
									Don't have an account yet ?
								</span>
								&nbsp;&nbsp;
								<a href="javascript:;" id="kt_login_signup" class="kt-login__account-link">Sign Up!</a>
																
								
							</div>
							
							<br><br><br><br>
						</div>
						
						
						
						
						<div id="Div2" class="kt-login__forgot">
								<div class="kt-login__head">
								
								   <br><br><br><br>
								   <h3  class="kt-login__title">Forgotten Password ?</h3>
								   
								 <div class="kt-login__desc">Enter your email to reset your password:</div>
									 
								</div>
								
								<form class="kt-form" action="/simt/account/reset" id="sumit-forgot-form">
									<div class="input-group">
										<input class="form-control" type="text" placeholder="Email" name="email" id="kt_email" autocomplete="off" required>
									</div>
									<div class="uk-form-row" id="forgotPasswordFailed" style="display:none" >
                                     <p style="color:red; text-align:center">Email not found , please enter another one</p>
                                    </div>
                                    
                                    <br><br>
                                    
									<div class="kt-login__actions">
										<button id="kt_login_forgot_submit_id" class="btn btn-brand btn-elevate kt-login__btn-primary">Request</button>&nbsp;&nbsp;
										<button onclick="switchVisible_Div1_Div2();" id="kt_login_forgot_cancel" class="btn btn-light btn-elevate kt-login__btn-secondary">Cancel</button>
									</div>
								</form>
								
								<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
								
							</div>
						
					
						
						
					</div>
				</div>
			</div>
			
		</div>
       
		<!-- end:: Page -->
								
								
								

								<!--end::Form-->

								<!--begin::Divider-->
								<!--
								<div style="visibility:hidden;" class="kt-login__divider">
									<div class="kt-divider">
										<span></span>
										<span>OR</span>
										<span></span>
									</div>
								</div>

								<!--end::Divider-->

								<!--begin::Options-->
								<!--
								<div style="visibility:hidden;" class="kt-login__options">
									<a  style="visibility:hidden;" "href="#" class="btn btn-primary kt-btn">
										<i class="fab fa-facebook-f"></i>
										Facebook
									</a>
									<a style="visibility:hidden;" href="#" class="btn btn-info kt-btn">
										<i class="fab fa-twitter"></i>
                             			Twitter
						            </a>
        							<a style="visibility:hidden;" href="#" class="btn btn-danger kt-btn">
										<i class="fab fa-google"></i>
										Google
									</a>
								</div>

								<!--end::Options-->
							</div>

							<!--end::Signin-->
						</div>

						<!--end::Body-->
					</div>

					<!--end::Content-->
				</div>
			</div>
		

		<!-- end:: Page -->
<style type="text/css">
#Div2 {
  display: none;
}
</style>

<script type="text/javascript">
function switchVisible_Div1_Div2() {
            if (document.getElementById('Div1')) {

                if (document.getElementById('Div1').style.display == 'none') {
                    document.getElementById('Div1').style.display = 'block';
                    document.getElementById('Div2').style.display = 'none';
                }
                else {
                    document.getElementById('Div1').style.display = 'none';
                    document.getElementById('Div2').style.display = 'block';
                }
            }
}
</script>



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

			<!-- end::Global Config -->
</script>
		<!--begin::Global Theme Bundle(used by all pages) -->
		<script src="${pageContext.request.contextPath}/assets/plugins/global/plugins.bundle.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/assets/js/scripts.bundle.js" type="text/javascript"></script>

		<!--end::Global Theme Bundle -->

		<!--begin::Page Scripts(used by this page) -->
		<script src="${pageContext.request.contextPath}/assets/js/pages/custom/login/login-general.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/install-sw.js" type="text/javascript"></script>
		<!--end::Page Scripts -->

	</body>
</html>
