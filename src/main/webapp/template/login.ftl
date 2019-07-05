<!DOCTYPE html>
<html lang="en">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<#include "index-head.ftl">
</head>
<link rel="stylesheet" href="${contextPath}/static/css/login.css">
<style>

</style>

<body>
<!-- Page container -->
<div class="page-container login-container">

    <!-- Page content -->
    <div class="page-content">

        <!-- Main content -->
        <div class="content-wrapper">

            <!-- Content area -->
            <div class="content">

                <!-- Advanced login -->
                <form action="${contextPath}/login/login.do" method="POST" id="login">
                    <div id="login-left">
                        <div id="copyRight">
                        </div>
                    </div>
                    <div id="login-right">

                        <input type="hidden" name="encryptType" id="encryptType">
                        <div class="login-form">
                            <div class="text-center" style="margin-bottom: 40px;">
                                <img id="logo" src="${contextPath}/static/image/login/logo.svg">
                                <h4 id="title">activiti工作流程演示系统</h4>
                            </div>

                            <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 27px;">
                                <span class="span-input">Username</span>
                                <input autocomplete="off" type="text" class="form-control input-lg" placeholder="请输入用户名" name="userAccount" id="username">
                            </div>

                            <div class="form-group has-feedback has-feedback-left pos-r">
                                <span class="span-input">Password</span>
                                <input autocomplete="off" type="password" class="form-control input-lg" placeholder="请输入密码" name="userPassword" id="password" autocomplete="off" >
                                <a id="forget" href="#" onClick="alert('请联系超级管理员!')">忘记密码？</a>
                            </div>

                            <div class="form-group login-options" style="margin-top: 0;">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="checkbox-inline" style="color:#666666">
                                            <input type="checkbox" class="styled" name="remember" id="remember">
                                            记住密码
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <button type="submit" class="btn btn-block btn-lg">登录 </button>
                            </div>

                        </div>
                    </div>
                </form>

            </div>
            <!-- /content area -->

        </div>
        <!-- /main content -->

    </div>
    <!-- /page content -->

</div>


</body>

</html>
