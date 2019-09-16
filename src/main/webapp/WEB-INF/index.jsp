<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>activiti</title>
	</head>
	
	<script type="text/javascript">
        var logon = "<%=request.getSession().getAttribute("USER") %>";
        debugger
        var cont = "<%=request.getContextPath() %>";
        if (logon != null) {
            window.location.href = cont +"/main.do";
        }else{
            window.location.href = cont +"/index.do";
		}
	</script>
	
	<body>
	<H1>HELLO </H1>
	</body>
</html>