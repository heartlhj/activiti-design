<script type="text/javascript" src="${contextPath}/static/js/ini/task.js"></script>

<!-- 中间按钮 start -->
<div class="navbar navbar-default navbar-xs new-navbar">
    <div class="navbar-collapse collapse" id="navbar-filter">
        <ul class="nav nav-tabs nav-tabs-solid" id="task-ul">
            <li><a href="javascript:activiti.task.design.finish()"><i class="icon-plus2 position-left"></i>审批</a></li>
			<li><a href="javascript:activiti.task.design.viewPic()"><i class="icon-section position-left"></i>查看流程图</a></li>

        </ul>
    </div>
</div>


<!-- 下面展示数据table start -->
<table id="activiti-task-grid1" showCheck="true" height="400"  width="1140px"  idPropertyName="id"
       rowNum="10" pagerid="activiti-task-page1">
    <tr>
        <td display="false" displayName="Id" width="2px" propertyName="id" sortType="text" align="left">&nbsp;</td>
        <td display="false" displayName="processInstanceId" width="2px" propertyName="processInstanceId" sortType="text" align="left">&nbsp;</td>
        <td display="false" displayName="executionId" width="2px" propertyName="executionId" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="流程名称" width="190px" propertyName="name" sortType="text" align="center">&nbsp;</td>
        <td display="true" displayName="创建日期" width="140px" propertyName="createTime" sortType="text" align="center" formatter="activiti.utils.formatDate">&nbsp;</td>
        <td display="true" displayName="更新时间" width="140px" propertyName="lastUpdateTime" sortType="text" align="center" formatter="activiti.utils.formatDate">&nbsp;</td>
        <td display="true" displayName="审批人"  width="560px" propertyName="owner" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="环节编码" width="70px" propertyName="taskDefinitionKey" sortType="text" align="center">&nbsp;</td>
    </tr>
</table>
<div id="activiti-task-page1" style="margin-top: 80px;"></div>