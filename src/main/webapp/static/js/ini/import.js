//初始化
activiti.Package("activiti.model");

activiti.model.import = function() {
    var URL  =  CONTEXT_PATH + '/activiti/funcPage/model/import.do';//当前窗口的URL
    var form = null;
    return{
        init : function() {
            form = new activiti.FormExt("activiti-model-import--form");

        },
        doSubmit : function () {
            //验证表单必填项
            if(!form.validate()){
                return;
            }
            $("#activiti-model-import-btn").hide();
            var name = $("#activiti-import").val();
            if(name == ''){
                activiti.Alert("上传为空");
                return false;
            }
            var formData = new FormData();
            formData.append("file", $("#activiti-import")[0].files[0]);
            $.ajax({
                url: CONTEXT_PATH + "/activiti/import.do",
                type: "post",
                data: formData,
                dataType: 'json',
                contentType: false,
                processData: false,

                success: function (data) {
                    activiti.SubmitModalWin(URL,data);
                    activiti.Alert("操作成功");
                },
                error: function (err) {
                    activiti.Alert(err.message);
                }
            });
           //  var valueObj = form.formToObject();// 整个表单的值
           //  var d = valueObj;
           //  var paramsObj = {};
           //  paramsObj.d = d;
           //  paramsObj = activiti.ToJson(paramsObj);
           //  var params = {};
           //  params.params = paramsObj;
           //  var url = CONTEXT_PATH + "/activiti/import.do";
           //  activiti.InvokeMethodAsyn(url,params,function(msg){
           //  if("success" == msg.status){
           //      activiti.SubmitModalWin(URL,params);
           //      activiti.Alert("操作成功");
           //  }else{
           //      activiti.Alert(msg.message);
           //  }
           // });
        },

        //关闭窗口
        doClose:function(){
            activiti.CloseModalWin(URL);
        },
    }
}();

//初始化
activiti.ExecWait(function(){
    activiti.model.import.init()
});