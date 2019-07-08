$(function () {
    WorkFlowMain.init();
})
var grid1 = null;
var WorkFlowMain = {

    init : function() {
        grid1 = new activiti.DataGrid("activiti-model-grid1",{
            postData : {
                // staffId:_UserDetail.staffId,
                // orgId: _UserDetail.orgId
            },
            url : CONTEXT_PATH + '/activiti/func/model/pagin.do',shrinkToFit:false
        });
        // form1 = new activiti.FormExt("buyManager-purContractInfo-form1");

    },
    create : function () {
        var paramObj = {};//传给模态窗口的参数
        var url = CONTEXT_PATH + '/activiti/funcPage/model/create.do';
        var width = null;
        var heigth = null;
        var submitFn = WorkFlowMain.createCloseComeBack;//回调方法
        var modalClass = "modal-lg";
        activiti.ShowModalWin(url,width,heigth,paramObj,submitFn,modalClass);
    },

    createCloseComeBack : function (param) {
        var params = {};
        param = activiti.ToJson(param);
        params.params = param;

        var url = CONTEXT_PATH + "/activiti/createModel.do";
        ruizhi.InvokeMethodAsyn(url,params,function(msg){
            if("success" == msg.status){
                ruizhi.Alert("操作成功");
                ruizhi.buyManager.purYearPort.loadData();
            }else{
                ruizhi.Alert(msg.message);
            }
        });
    },

    modify : function () {
        var ids = grid1.getCheckedIds();
        if(!activiti.IsNull(ids)){
            if(ids.length == 0){
                activiti.Alert('请选择');
                return false;
            }else if(ids.length > 1){
                activiti.Alert('不能选择多条数据');
                return false;
            }
        }
    },

    del : function () {

    },
}