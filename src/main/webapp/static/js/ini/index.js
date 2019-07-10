activiti.Package("activiti.model");

activiti.model.design = function() {
    var grid1;
    return{
        init : function() {
            grid1 = new activiti.DataGrid("activiti-model-grid1",{
                postData : {
                    // staffId:_UserDetail.staffId,
                    // orgId: _UserDetail.orgId
                },
                url : CONTEXT_PATH + '/activiti/func/model/pagin.do',shrinkToFit:false
            });

        },
        //新增模型
        create : function () {
            var paramObj = {};//传给模态窗口的参数
            var url = CONTEXT_PATH + '/activiti/funcPage/model/create.do';
            var width = null;
            var heigth = null;
            var submitFn = activiti.model.design.createCloseComeBack;//回调方法
            var modalClass = "modal-lg";
            activiti.ShowModalWin(url,width,heigth,paramObj,submitFn,modalClass);
        },
        //新增回调
        createCloseComeBack : function (param) {
            activiti.model.design.loadData();
        },
        //加载数据
        loadData : function() {

            var url = CONTEXT_PATH + '/activiti/func/model/pagin.do';
            var  paramObj = {};
            paramObj.page = 1;
            paramObj.limit = 10;
            grid1.loadData(url, paramObj);
        },
        //编辑模型
        modify : function () {
            var ids = grid1.getCheckedIds();
            if(!activiti.IsNull(ids)){
                if(ids.length == 0){
                    activiti.Alert('请选择');
                    return false;
                }else if(ids.length > 1){
                    activiti.Alert('不能选择多条数据');
                    return false;
                }else{
                    var id = ids[0];
                    window.location.href= CONTEXT_PATH  + "/modeler.html?modelId="+id;
                }
            }else{
                activiti.Alert('请选择');
                return false;
            }

        },
        //删除模型
        del : function () {
            var modelIds = grid1.getCheckedIds();//所选行的id数组
            if(!activiti.IsNull(modelIds) && modelIds.length > 0){
                modelIds = modelIds.join(",");
                var msg = "确定要删除吗？";
                activiti.Confirm(msg, function(result) {
                    if(result || 'true' == result) {
                        var params = {};
                        params.modelIds = modelIds;
                        var url = CONTEXT_PATH + '/activiti/deleteModel.do';
                        activiti.InvokeMethodAsyn(url,params,function(msg){
                            if("success" == msg.status){
                                activiti.Alert("操作成功");
                                activiti.model.design.loadData();
                            }else{
                                activiti.Alert(msg.message);
                            };
                        })
                    }
                });
            }else{
                activiti.Alert('请选择');
            }
        },

        deploy : function () {
            var rowIds = grid1.getCheckedIds();
            if (rowIds.length <= 0) {
                activiti.Alert("请选择记录");
            }else if(rowIds.length >1) {
                activiti.Alert("只能选择一条记录");
            }else {
                var id = rowIds[0];
                var msg = "确定要部署吗？";
                activiti.Confirm(msg, function(result) {
                    if (result || 'true' == result) {
                        var params = {};
                        params.id = id;
                        var url = CONTEXT_PATH + '/activiti/deploy.do';
                        activiti.InvokeMethodAsyn(url,params,function(msg){
                            if("success" == msg.status){
                                activiti.Alert("操作成功");
                                activiti.model.design.loadData();
                            }else{
                                activiti.Alert(msg.message);
                            };
                        })
                    }
                });
            }
        },

        import : function () {
            var paramObj = {};//传给模态窗口的参数
            var url = CONTEXT_PATH + '/activiti/funcPage/model/import.do';
            var width = null;
            var heigth = null;
            var submitFn = activiti.model.design.createCloseComeBack;//回调方法
            var modalClass = "modal-lg";
            activiti.ShowModalWin(url,width,heigth,paramObj,submitFn,modalClass);
        },

        export : function () {
            var rowIds = grid1.getCheckedIds();
            if (rowIds.length <= 0) {
                activiti.Alert("请选择记录");
            }else if(rowIds.length >1) {
                activiti.Alert("只能选择一条记录");
            }else {
                var id = rowIds[0];
                window.open(CONTEXT_PATH + "/activiti/export.do?modelId="+id);
                // var paramObj = {};//传给模态窗口的参数
                // paramObj.id = id;
                // var url = CONTEXT_PATH + '/activiti/export.do';
                // activiti.InvokeMethodAsyn(url,paramObj,function(msg){
                //     if("success" == msg.status){
                //         activiti.Alert("操作成功");
                //         activiti.model.design.loadData();
                //     }else{
                //         activiti.Alert(msg.message);
                //     };
                // })
            }
        },

        copy : function () {
            var rowIds = grid1.getCheckedIds();
            if (rowIds.length <= 0) {
                activiti.Alert("请选择记录");
            }else if(rowIds.length >1) {
                activiti.Alert("只能选择一条记录");
            }else {
                var selItem = grid1.getSelectedItem();//所选行
                var paramObj = {};//传给模态窗口的参数
                var url = CONTEXT_PATH + '/activiti/funcPage/model/copy.do';
                var width = null;
                var heigth = null;
                paramObj.selItem = selItem;
                var submitFn = activiti.model.design.createCloseComeBack;//回调方法
                var modalClass = "modal-lg";
                activiti.ShowModalWin(url, width, heigth, paramObj, submitFn, modalClass);
            }
        },
    }
}();

//初始化
activiti.ExecWait(function(){
    activiti.model.design.init()
});