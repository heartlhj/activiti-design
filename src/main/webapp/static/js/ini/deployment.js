activiti.Package("activiti.deployment");

activiti.deployment.design = function() {
    var grid1;
    return{
        init : function() {
            grid1 = new activiti.DataGrid("activiti-deployment-grid1",{
                postData : {
                    // staffId:_UserDetail.staffId,
                    // orgId: _UserDetail.orgId
                },
                url : CONTEXT_PATH + '/deployment/func/deployment/pagin.do',shrinkToFit:false
            });

        },
        //激活
        create : function () {
            var rowIds = grid1.getCheckedIds();
            if (rowIds.length <= 0) {
                activiti.Alert("请选择记录");
            }else if(rowIds.length >1) {
                activiti.Alert("只能选择一条记录");
            }else {
                var selItem = grid1.getSelectedItem();//所选行
                selItem = activiti.ToJson(selItem);
                var params = {};
                params.params = selItem;
                var url = CONTEXT_PATH + '/task/createTask.do';
                activiti.InvokeMethodAsyn(url, params, function (msg) {
                    if ("success" == msg.status) {
                        activiti.Alert("操作成功");
                        activiti.model.design.loadData();
                    } else {
                        activiti.Alert(msg.message);
                    }
                    ;
                })
            }
        },

        //加载数据
        loadData : function() {

            var url = CONTEXT_PATH + '/deployment/func/deployment/pagin.do';
            var  paramObj = {};
            paramObj.page = 1;
            paramObj.limit = 10;
            grid1.loadData(url, paramObj);
        },

        //删除模型
        del : function () {
            var deploymentIds = grid1.getCheckedIds();//所选行的id数组
            if(!activiti.IsNull(deploymentIds) && deploymentIds.length > 0){
                deploymentIds = deploymentIds.join(",");
                var msg = "确定要删除吗？";
                activiti.Confirm(msg, function(result) {
                    if(result || 'true' == result) {
                        var params = {};
                        params.deploymentIds = deploymentIds;
                        var url = CONTEXT_PATH + '/deployment/deleteDeployment.do';
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

        //删除模型
        convert : function () {
            var rowIds = grid1.getCheckedIds();
            if (rowIds.length <= 0) {
                activiti.Alert("请选择记录");
            }else if(rowIds.length >1) {
                activiti.Alert("只能选择一条记录");
            }else {
                var selItem = grid1.getSelectedItem();//所选行
                var params = {};
                params.processDefinitionId = selItem.processDefinitionId;
                var url = CONTEXT_PATH + '/deployment/convert-to-model.do';
                activiti.InvokeMethodAsyn(url, params, function (msg) {
                    if ("success" == msg.status) {
                        activiti.Alert("操作成功");
                        activiti.model.design.loadData();
                    } else {
                        activiti.Alert(msg.message);
                    }
                    ;
                })
            }
        },

    }
}();

//初始化
activiti.ExecWait(function(){
    // activiti.deployment.design.init()
});