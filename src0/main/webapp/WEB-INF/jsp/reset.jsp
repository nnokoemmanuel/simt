
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

	<!-- begin::Head -->
	<head>
		<base href="../../../">
		<meta charset="utf-8" />
		<title>Reseting Password| SIMT</title>
		<meta name="description" content="Login page example">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!--begin::Page Custom Styles(used by this page) -->
		<link href="${pageContext.request.contextPath}/assets/css/pages/login/login-3.css" rel="stylesheet" type="text/css" />
        <!--begin::Fonts -->

		<link href="${pageContext.request.contextPath}/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />

		<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/media/logos/favicon.ico" />
	</head>

	<!-- end::Head -->

	<body class="kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-aside--minimize kt-page--loading">

		<!-- begin:: Page -->
		<div class="kt-grid kt-grid--ver kt-grid--root kt-page">
			<div class="kt-grid kt-grid--hor kt-grid--root  kt-login kt-login--v3 kt-login--signin" id="kt_login">
				<div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" style="background-image: url(${pageContext.request.contextPath}/assets/media/bg/bg-3.jpg);">
					<div class="kt-grid__item kt-grid__item--fluid kt-login__wrapper">
						<div class="kt-login__container">
							<div class="kt-login__logo">
								<a href="#">
									<img src="${pageContext.request.contextPath}/images/logo100x100.png">
								</a>
							</div>
							<div class="kt-login__signin">
								<div class="kt-login__head">
									<h3 class="kt-login__title">Reset Password </h3>
								</div>

	                             <div class="uk-form-row" style="display:none" id="passwordWarning">
                        	            <p style="color:red; text-align:center">password mismatch | password too short</p>
                    	        </div> 
                    	        
                    	         <div class="uk-form-row" style="display:none" id="invalidToken">
                        	            <p style="color:red; text-align:center">Invalid Token</p>
                    	         </div>
                    	        
								<form class="kt-form"  method="POST" id="resetForm">
								     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									
									<input type="hidden" id="tokenId" name="token" value="${token}"/>
									<div class="input-group">
										<input class="form-control" id="reset1" type="password" placeholder="Password" name="password">
									</div>
									
									<div class="input-group">
										<input class="form-control" id="reset2" type="password" placeholder="Repeat the Password" name="password">
									</div>
								
									<div class="kt-login__actions">
										<button id="resetButton" type="submit" class="btn btn-brand btn-elevate kt-login__btn-primary">Submit</button>
									</div>
								</form>
							</div>
							
						</div>
					</div>
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

		<!-- end::Global Config -->
</script>
	
		<script>

const resetButton=document.getElementById("resetForm");
resetButton.addEventListener("submit", evnent =>{
evnent.preventDefault();
const password1 =document.getElementById("reset1").value;
const password2 =document.getElementById("reset2").value;
const token =document.getElementById("tokenId").value;
if((password1!=password2) || password1.length<7) {
document.getElementById("passwordWarning").style.display="block";
}
else{
      const url="/simt/password/forgotten/reset/step/"+token+"/"+password1;
      doget(url).then(function(responseSuccess){
           if(responseSuccess==="done"){
           alert("password has been reset successfully");
           }else{
           
           alert("can not reset this user account twice");
            
           }
      
    }).catch(function(responseError){
		
		
	});
}

}); 

</script>
		
  <script src="${pageContext.request.contextPath}/js/helper.js" type="text/javascript"></script>

	</body>

	<!-- end::Body -->
</html>