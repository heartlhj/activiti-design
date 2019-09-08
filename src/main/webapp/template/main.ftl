<!DOCTYPE html>
<html lang="cn">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<#include "index-head.ftl">
    <title>activiti主页</title>
    <link rel="shortcut icon" href="${contextPath}/static/image/favicon.ico">
<style type="text/css">
        
</style>
<script type="text/javascript" src="${contextPath}/static/js/ini/index.js"></script>

</head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<body>
<div class="container">
	 <div class="row">
		 <#include "title.ftl">
	 </div>
	<hr>
	<div class="row">
		<ul class="nav nav-pills" role="tablist">
			  <li class="active"><a href="#model_Table"  data-toggle="tab">模型管理</a></li>
			  <li><a href="#deployment_Table" onclick="activiti.deployment.design.init()" data-toggle="tab">部署管理</a></li>
			  <li><a href="#task_Table" onclick="activiti.task.design.init()" data-toggle="tab">待办管理</a></li>
			</ul>
	 </div>
</div>
 <#include "index-modal.ftl">
<hr>
<div class="tab-content">
    <div class="tab-pane active" id="model_Table">
		<#include "model/modelList.ftl">
    </div>
    <div class="tab-pane" id="deployment_Table">
		<#include "model/deployment-list.ftl">
    </div>
    <div class="tab-pane" id="task_Table">
		<#include "task/task-list.ftl">
    </div>
</div>

</body>
<script>

</script>
</html>
