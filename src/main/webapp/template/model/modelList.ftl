<!-- 中间按钮 start -->
<div class="navbar navbar-default navbar-xs new-navbar">
    <div class="navbar-collapse collapse" id="navbar-filter">
        <ul class="nav nav-tabs nav-tabs-solid" id="model-ul">
            <li><a href="javascript:activiti.model.design.create()"><i class="icon-plus2 position-left"></i>新增</a></li>
			<li><a href="javascript:activiti.model.design.modify()"><i class="icon-pencil4 position-left"></i>编辑</a></li>
			<li><a href="javascript:activiti.model.design.del()" ><i class="icon-x position-left"></i>删除</a></li>
			<li><a href="javascript:activiti.model.design.deploy()" ><i class="icon-warning position-left"></i>部署</a></li>
			<li><a href="javascript:activiti.model.design.import()" id="purContractInfo-contractClose"><i class="icon-folder-upload2 position-left"></i>导入</a></li>
			<li><a href="javascript:activiti.model.design.export()" id="purContractInfo-contractReturn"><i class="icon-folder-download2 position-left"></i>导出</a></li>
			<li><a href="javascript:activiti.model.design.copy()"><i class="icon-section position-left"></i>复制模型</a></li>

        </ul>
    </div>
</div>


<!-- 下面展示数据table start -->
<table id="activiti-model-grid1" showCheck="true" height="400" idPropertyName="id"
       rowNum="10" pagerid="activiti-model-page1">
    <tr>
        <td display="false" displayName="Id" width="2px" propertyName="id" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="流程名称" width="190px" propertyName="name" sortType="text" align="center">&nbsp;</td>
        <td display="true" displayName="创建日期" width="140px" propertyName="createTime" sortType="text" align="center" formatter="activiti.utils.formatDate">&nbsp;</td>
        <td display="true" displayName="创建日期" width="140px" propertyName="createDateStr" sortType="text" align="center">&nbsp;</td>
        <td display="true" displayName="更新时间" width="140px" propertyName="lastUpdateTime" sortType="text" align="center" formatter="activiti.utils.formatDate">&nbsp;</td>
        <td display="true" displayName="内容"  width="560px" propertyName="metaInfo" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="版本" width="70px" propertyName="version" sortType="text" align="center">&nbsp;</td>
    </tr>
</table>
<div id="activiti-model-page1" style="margin-top: 80px;"></div>