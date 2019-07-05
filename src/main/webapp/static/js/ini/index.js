$(function () {
    WorkFlowMain.init();
})
var WorkFlowMain = {

    init : function() {
        var grid1 = new activiti.DataGrid("buyManager-purContractInfo-grid1",{
            postData : {
                // staffId:_UserDetail.staffId,
                // orgId: _UserDetail.orgId
            },
            url : CONTEXT_PATH + '/activiti/func/model/pagin.do',shrinkToFit:false
            //customPager: '#buyManager-purContractInfo-page1_custom'
        });
        form1 = new activiti.FormExt("buyManager-purContractInfo-form1");

    },
}