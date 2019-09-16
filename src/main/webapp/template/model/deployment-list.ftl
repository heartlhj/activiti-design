<script type="text/javascript" src="${contextPath}/static/js/ini/deployment.js"></script>

<!-- 中间按钮 start -->
<div class="navbar navbar-default navbar-xs new-navbar">
    <div class="navbar-collapse collapse" id="navbar-filter">
        <ul class="nav nav-tabs nav-tabs-solid" id="deployment-ul">
            <li><a href="javascript:activiti.deployment.design.create()" ><i class="icon-plus2 position-left"></i>激活</a></li>
            <li><a href="javascript:activiti.deployment.design.del()" ><i class="icon-x position-left"></i>删除</a></li>
            <li><a href="javascript:activiti.deployment.design.convert()" ><i class="icon-plus-circle2 position-left"></i>转换为Model</a></li>

        </ul>
    </div>
</div>


<!-- 下面展示数据table start -->
<table id="activiti-deployment-grid1" showCheck="true" height="400"  width="1140px"  idPropertyName="id"
       rowNum="10" pagerid="activiti-deployment-page1">
    <tr>
        <td display="false" displayName="Id" width="2px" propertyName="id" sortType="text" align="left">&nbsp;</td>
        <td display="false" displayName="processDefinitionId" width="2px" propertyName="processDefinitionId" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="流程名称" width="250px" propertyName="name" sortType="text" align="center">&nbsp;</td>
        <td display="true" displayName="部署日期" width="300px" propertyName="deploymentTime" sortType="text" align="center" formatter="activiti.utils.formatDate">&nbsp;</td>
        <td display="true" displayName="编码" width="250px" propertyName="key" sortType="text" align="center">&nbsp;</td>
        <td display="true" displayName="版本" width="300px" propertyName="version" sortType="text" align="center">&nbsp;</td>
    </tr>
</table>
<div id="activiti-deployment-page1" style="margin-top: 80px;"></div>