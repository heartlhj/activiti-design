	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${systemName!"activiti-design"}</title>

	<!-- Global stylesheets -->
	<link rel="stylesheet" type="text/css" href="${staticRoot}/static/css/fonts.googleapis.com.css" >

	<!--jqGrid-->
	<link rel="stylesheet" type="text/css" href="${staticRoot}/jqGrid/css/jquery-ui.css">
	
	<link rel="stylesheet" type="text/css" href="${staticRoot}/limitless/assets/css/countDiv.css">
	
	<link rel="stylesheet" type="text/css" href="${staticRoot}/jqGrid/css/ui.jqgrid.css">
	<!--
	<link rel="stylesheet" type="text/css" href="${staticRoot}/jqGrid/css/ui.jqgrid-bootstrap.css">
	<link rel="stylesheet" type="text/css" href="${staticRoot}/jqGrid/css/ui.jqgrid-bootstrap-ui.css">
	-->
	<!-- 附件上传下载 -->
	<link rel="stylesheet" type="text/css" href="${staticRoot}/limitless/assets/js/plugins/uploaders/css/fileinput.min.css" >
	
	<!-- /global stylesheets -->
	
    <link rel="stylesheet" type="text/css" href="${staticRoot}/limitless/assets/css/navigation.css">
	<!-- Core JS files  -->
	<script type="text/javascript" src="${staticRoot}/static/js/angular.min.js"></script>
	
	<!-- /theme JS files -->
	<script>
		var WEB_ROOT = '${webRoot!''}';
		
		
		var _SESSION = ${mySessionUser!''};
		
		/*_SESSION.hasPriv = function(privCode){
			if(_SESSION.privates!=null && _SESSION.privates.indexOf(privCode)>-1){
				return true;
			}else{
				return false;
			}
		};*/
		
		
	</script>
	
	<script type="text/javascript" src="${webRoot}/template/public/jqueryExt.js"></script>
	<script type="text/javascript" src="${webRoot}/template/public/helper.js"></script>
	<script type="text/javascript" src="${webRoot}/template/public/formExt.js"></script>
	<script type="text/javascript" src="${webRoot}/template/public/dataGrid.js"></script>
	<script type="text/javascript" src="${webRoot}/template/public/tab.js"></script>
	<script type="text/javascript" src="${webRoot}/template/public/zTree.js"></script>
	<!-- 分页插件 -->
	<script type="text/javascript" src="${staticRoot}/limitless/assets/js/plugins/pagination/bs_pagination.min.js"></script>