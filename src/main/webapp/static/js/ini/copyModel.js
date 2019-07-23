//初始化
activiti.Package("activiti.model");

activiti.model.copy = function() {
    var URL  =  CONTEXT_PATH + '/activiti/funcPage/model/copy.do';//当前窗口的URL
    var form = null;
    return{
        init : function() {
            var paramObj = activiti.GetModalWinParam(URL);
            var selItem = paramObj.selItem;
            form = new activiti.FormExt("activiti-model-copyModel-form");
            form.objectToForm(selItem);
        },
        doSubmit : function () {
            //验证表单必填项
            if(!form.validate()){
                return;
            }
            $("#activiti-model-copyModel-btn").hide();
            var valueObj = form.formToObject();// 整个表单的值
            // var d = valueObj;
            // var paramsObj = {};
            // paramsObj.d = d;
            // paramsObj = activiti.ToJson(paramsObj);
            // var params = {};
            // params.params = paramsObj;
            var url = CONTEXT_PATH + "/activiti/model/copy.do";
            activiti.InvokeMethodAsyn(url,valueObj,function(msg){
            if("success" == msg.status){
                activiti.SubmitModalWin(URL,msg);
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
    activiti.model.copy.init()
});