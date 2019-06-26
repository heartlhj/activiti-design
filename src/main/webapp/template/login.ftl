<!DOCTYPE html>
<html lang="en">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<#include "index-head.ftl">
</head>
<script type="text/javascript" src="${contextPath}/static/js/ini/index.js"></script>

<body>

	<div class="login-main">
		<div class="login-body">
			<!-- Advanced login -->
			<form action="/login/login" method="POST" style="height:100%">
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
