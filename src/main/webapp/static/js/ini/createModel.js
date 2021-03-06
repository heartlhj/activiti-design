//初始化
activiti.Package("activiti.model");

activiti.model.create = function() {
    var URL  =  CONTEXT_PATH + '/activiti/funcPage/model/create.do';//当前窗口的URL
    var form = null;
    return{
        init : function() {
            form = new activiti.FormExt("activiti-model-create--form");

        },
        doSubmit : function () {
            //验证表单必填项
            if(!form.validate()){
                return;
            }
            $("#activiti-model-create-btn").hide();
            var valueObj = form.formToObject();// 整个表单的值
            var d = valueObj;
            var paramsObj = {};
            paramsObj.d = d;
            paramsObj = activiti.ToJson(paramsObj);
            var params = {};
            params.params = paramsObj;
            var url = CONTEXT_PATH + "/activiti/createModel.do";
            activiti.InvokeMethodAsyn(url,params,function(msg){
            if("success" == msg.status){
                activiti.SubmitModalWin(URL,params);
                activiti.Alert("操作成功");
            }else{
                activiti.Alert(msg.message);
            }
           });
        },

        //关闭窗口
        doClose:function(){
            activiti.CloseModalWin(URL);
        },
    }
}();

//初始化
activiti.ExecWait(function(){
    activiti.model.create.init()
});