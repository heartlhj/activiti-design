<!DOCTYPE html>
<html lang="en">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<!-- 不缓存，测试阶段，后面上线可去掉 -->
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<!-- 不缓存，测试阶段，后面上线可去掉 -->

<head>
<#include "./includes/index-head.ftl">	
</head>

<body class="navbar-top">
    
	<!-- Main navbar -->

	<#include "./includes/menu-navbar.ftl">

	<!-- /main navbar -->

	<!-- Page container -->
	<div class="page-container">

		<!-- Page content -->
		<div class="page-content">
            
			<!-- Main sidebar -->
			<#include "./includes/index-newNav.ftl">
			<!-- /main sidebar -->
         

			<!-- Main content -->
			<div class="content-wrapper">

					<!-- Basic-->

						
						<#include "./includes/navShow/msqs-head.ftl">
										
					
					<!-- /basic  -->

					<!-- Footer -->
					<div class="footer text-muted">
						&copy; 2015. <a href="#">Limitless Web App Kit</a> by <a href="http://themeforest.net/user/Kopyov" target="_blank">Eugene Kopyov</a>
					</div>
					<!-- /footer -->

			</div>
			<!-- /main content -->

		</div>
		<!-- /page content -->

	</div>
	<!-- /page container -->
	<#include "./includes/index-modal.ftl">
	<#include "./includes/modal-foot.ftl">
</body>
<script>

</script>
</html>
