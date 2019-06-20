<%@ page language="java" pageEncoding="UTF-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
    <head>  
        <title>login</title>  
        <meta http-equiv="pragma" content="no-cache">  
        <meta http-equiv="cache-control" content="no-cache">  
        <meta http-equiv="expires" content="0">  
    </head>  
    <body onload='document.f.userAccount.focus();'>  
        <h3>  
            ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}  
            <br/>  
            用户登录  
        </h3>  
        <form name='f' action='<%=request.getContextPath() %>/login/login'  
            method='POST'>  
            <table>  
                <tr>  
                    <td>User:</td>  
                    <td><input type='text' name='userAccount' value=''></td>  
                </tr>  
                <tr>  
                    <td>Password:</td>  
                    <td><input type='password' name='userPassword' /></td>  
                </tr>  
                <tr>  
                    <td colspan='2'>  
                        <input name="submit" type="submit" />  
                    </td>  
                </tr>  
                <tr>  
                    <td colspan='2'>  
                        <input name="reset" type="reset" />  
                    </td>  
                </tr>  
            </table>  
        </form>  
    </body>  
</html>  