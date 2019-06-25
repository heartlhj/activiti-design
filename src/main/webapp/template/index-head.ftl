	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<#include "commont/taglib.jsp">

	<!-- Global stylesheets -->
	<link rel="stylesheet" type="text/css" href="${staticRoot}/static/css/fonts.googleapis.com.css" >

	<!--jqGrid-->
	<link rel="stylesheet" type="text/css" href="${contextPath}/jqGrid/css/jquery-ui.css">
	
	<link rel="stylesheet" type="text/css" href="${contextPath}/limitless/assets/css/countDiv.css">
	<#--<script> var contextPath = "${contextPath}"</script>-->
	<link rel="stylesheet" type="text/css" href="${contextPath}/jqGrid/css/ui.jqgrid.css">
	<!--
	<link rel="stylesheet" type="text/css" href="${contextPath}/jqGrid/css/ui.jqgrid-bootstrap.css">
	<link rel="stylesheet" type="text/css" href="${contextPath}/jqGrid/css/ui.jqgrid-bootstrap-ui.css">
	-->
	<!-- 附件上传下载 -->
	<link rel="stylesheet" type="text/css" href="${contextPath}/limitless/assets/js/plugins/uploaders/css/fileinput.min.css" >
	
	<!-- /global stylesheets -->
	
    <link rel="stylesheet" type="text/css" href="${contextPath}/limitless/assets/css/navigation.css">
	<!-- Core JS files  -->
	<script type="text/javascript" src="${contextPath}/static/js/angular.min.js"></script>
	
	<!-- /theme JS files -->
	<script>
		var contextPath = '${contextPath!''}';
		alert(contextPath)

		
	</script>
	
	<script type="text/javascript" src="${contextPath}/template/public/jqueryExt.js"></script>
	<script type="text/javascript" src="${contextPath}/template/public/helper.js"></script>
	<script type="text/javascript" src="${contextPath}/template/public/formExt.js"></script>
	<script type="text/javascript" src="${contextPath}/template/public/dataGrid.js"></script>
	<script type="text/javascript" src="${contextPath}/template/public/tab.js"></script>
	<script type="text/javascript" src="${contextPath}/template/public/zTree.js"></script>
	<!-- 分页插件 -->
	<script type="text/javascript" src="${contextPath}/limitless/assets/js/plugins/pagination/bs_pagination.min.js"></script>