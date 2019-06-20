<!DOCTYPE html>
<html lang="en">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${systemName!"RuiZhi"}</title>

	<!-- Global stylesheets -->
	<!--<link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">-->
	<link rel="stylesheet" type="text/css" href="${staticRoot}/limitless/assets/css/fonts.googleapis.com.css" >
	<link href="${staticRoot}/limitless/assets/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
	<link href="${staticRoot}/limitless/assets/css/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="${staticRoot}/limitless/assets/css/core.css" rel="stylesheet" type="text/css">
	<link href="${staticRoot}/limitless/assets/css/components.css" rel="stylesheet" type="text/css">
	<link href="${staticRoot}/limitless/assets/css/colors.css" rel="stylesheet" type="text/css">
	<link href="${staticRoot}/limitless/assets/css/magic-input.min.css" rel="stylesheet" type="text/css">
	<link href="${staticRoot}/limitless/assets/css/login.css" rel="stylesheet" type="text/css">
	<!-- /global stylesheets -->

	<!-- Core JS files -->
	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/plugins/loaders/pace.min.js"></script>
	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/core/libraries/jquery.min.js"></script>
	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/core/libraries/bootstrap.min.js"></script>
	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/plugins/loaders/blockui.min.js"></script>
	<!-- /core JS files -->

	<!-- Theme JS files -->
	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/plugins/forms/styling/uniform.min.js"></script>

	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/core/app.js"></script>
	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/pages/login.js"></script>
	<!-- /theme JS files -->

</head>

<body>

	<div class="login-main">
		<div class="logo-show"><embed src="./template/img/login/logo.svg" type="image/svg+xml" pluginspage="img/"></div>
		<div class="login-body">
			<!-- Advanced login -->
			<form action="${webRoot}/login/login" method="POST" style="height:100%">
				<div class="login-form">
					<#if Session.SPRING_SECURITY_LAST_EXCEPTION??>
					<div class="alert bg-danger">
						<button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button>
						<span class="text-semibold">${Session.SPRING_SECURITY_LAST_EXCEPTION.message}</span>
					</div>
					</#if>


					<div class="login-top-title">
						<p>登陆<span>正圣ERP系统</span></p>
						<p>LOGIN TO ZHENGSHENG ERP SYSTEM</p>
					</div>
					<p><span class="glyphicon glyphicon-user" aria-hidden="true"></span>YOUR LOGIN NAME</p>
					<input type="text" placeholder="input your login name" id="username" name="userAccount">
					<p><span class="glyphicon glyphicon-lock" aria-hidden="true"></span>YOUR LOGIN PASSWORD</p>
					<input type="password" placeholder="input your login password" id="password" name="userPassword">
					<div class="login-btn">
						<div class="row">
							<div class="col-md-6"><p><input type="checkbox" class="mgc mgc-danger mgc-circle" name="remember-me" id="remember">记住密码</p></div>
							<div class="col-md-6"><p><a href="">忘记密码</a></p></div>
						</div>
						<button id="login-btn" type="submit">登陆</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>

</html>
