<!DOCTYPE html>
<html lang="en">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<head>
    <!--页面title-->
    <div class="modal-header bg-primary">
        <button type="button" class="close" data-dismiss="modal" onclick="CreateModel.doClose()">&times;</button>
        <h6 class="modal-title">模型新增</h6>
    </div>
<#include "../index-head.ftl">
<script type="text/javascript" src="${contextPath}/static/js/ini/createModel.js"></script>

</head>

<body>

<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="basicMessage">
        <form class="form-horizontal" id="activiti-model-create--form" action="${contextPath}/activiti/createModel.do" method="post">
            <div>
                <br/>
            </div>
            <div class="panel panel-flat new-panel">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label class="col-md-4 control-label text-right" ><span style="color:red">*</span>流程名称:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="name"   placeholder="流程名称">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label class="col-md-4 control-label text-right" >描述:</label>
                                <div class="col-md-8">
                                    <textarea rows="3" cols="15" class="form-control input-remarks" placeholder="描述" name="description"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer" id="activiti-model-create-btn">
                <button type="submit" class="btn btn-save" >保存</button>
                <button type="button" class="btn btn-cancel" onclick="CreateModel.doClose()">关闭</button>
            </div>
        </form>


    </div>
</div>
</body>

</html>