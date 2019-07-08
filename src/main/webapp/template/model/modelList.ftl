<!-- 中间按钮 start -->
<div class="navbar navbar-default navbar-xs new-navbar">
    <div class="navbar-collapse collapse" id="navbar-filter">
        <ul class="nav nav-tabs nav-tabs-solid" id="purContractInfo-clientBtnId">
            <li><a href="javascript:WorkFlowMain.create()" id="purContractInfo-create"><i class="icon-plus2 position-left"></i>新增</a></li>
			<li><a href="javascript:WorkFlowMain.modify()" id="purContractInfo-modify"><i class="icon-pencil4 position-left"></i>修改</a></li>
			<li><a href="javascript:ruizhi.buyManager.purContractInfo.del()" id="purContractInfo-del"><i class="icon-x position-left"></i>删除</a></li>
			<li><a href="javascript:ruizhi.buyManager.purContractInfo.contractErrInfo()" id="purContractInfo-contractErrInfo"><i class="icon-warning position-left"></i>合同异常</a></li>
			<li><a href="javascript:ruizhi.buyManager.purContractInfo.contractClose()" id="purContractInfo-contractClose"><i class="icon-close2 position-left"></i>合同关闭</a></li>
			<li><a href="javascript:ruizhi.buyManager.purContractInfo.contractReturn()" id="purContractInfo-contractReturn"><i class="icon-close2 position-left"></i>合同回传</a></li>
			<li><a href="javascript:ruizhi.buyManager.purContractInfo.purContractInfoExcel()"><i class="icon-folder-download2 position-left"></i>导出EXCLE</a></li>

        </ul>
    </div>
</div>


<!-- 下面展示数据table start -->
<table id="activiti-model-grid1" showCheck="true" height="400" idPropertyName="id"
       rowNum="10" pagerid="activiti-model-page1">
    <tr>
        <td display="false" displayName="Id" width="2px" propertyName="id" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="流程名称" width="190px" propertyName="name" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="创建日期" width="140px" propertyName="createTime" sortType="text" align="center" formatter="activiti.utils.formatDate">&nbsp;</td>
        <td display="true" displayName="更新时间" width="140px" propertyName="lastUpdateTime" sortType="text" align="center" formatter="activiti.utils.formatDate">&nbsp;</td>
        <td display="true" displayName="内容"  width="560px" propertyName="metaInfo" sortType="text" align="left">&nbsp;</td>
        <td display="true" displayName="版本" width="70px" propertyName="version" sortType="text" align="center">&nbsp;</td>
    </tr>
</table>
<div id="activiti-model-page1" style="margin-top: 80px;"></div>