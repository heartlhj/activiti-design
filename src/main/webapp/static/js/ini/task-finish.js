//初始化
activiti.Package("activiti.task");

activiti.task.finish = function() {
    var URL  =  CONTEXT_PATH + '/task/funcPage/finish.do';//当前窗口的URL
    var form = null;
    return{
        init : function() {
            form = new activiti.FormExt("task-model-finish-form");
            var paramObj = activiti.GetModalWinParam(URL);
            var finishObj = paramObj.selItem;
            form.objectToForm(finishObj);
        },
        doSubmit : function () {
            //验证表单必填项
            if(!form.validate()){
                return;
            }
            $("#task-model-finish-btn").hide();
            var valueObj = form.formToObject();// 整个表单的值
            var paramMap = {};
            var params = valueObj.paramMap;
            if(!activiti.IsNull(params)){
                var split = params.split(",");
                for(var i=0;i<split.length;i++){
                    var strings = split[i].split("=");
                    paramMap[strings[0]] = strings[1];
                }

            }
            debugger
            valueObj.paramMap=paramMap;
            valueObj = activiti.ToJson(valueObj);
            var params = {};
            params.params = valueObj;
            var url = CONTEXT_PATH + "/task/finish.do";
            activiti.InvokeMethodAsyn(url,params,function(msg){
                if("success" == msg.status){
                    activiti.model.design.init()
                    activiti.Alert("操作成功");
                }else{
                    activiti.Alert(msg.message);
                }
            });
            activiti.task.finish.doClose();
        },
        //关闭窗口
        doClose:function(){
            activiti.CloseModalWin(URL);
        },
    }
}();

//初始化
activiti.ExecWait(function(){
    activiti.task.finish.init()
});