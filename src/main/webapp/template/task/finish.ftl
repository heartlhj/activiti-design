<!DOCTYPE html>
<html lang="en">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<head>
    <!--页面title-->
    <div class="modal-header bg-primary">
        <button type="button" class="close" data-dismiss="modal" onclick="activiti.task.finish.doClose()">&times;</button>
        <h6 class="modal-title">审批</h6>
    </div>
<script type="text/javascript" src="${contextPath}/static/js/ini/task-finish.js"></script>

</head>

<body>

<div id="finish-div" class="tab-content">
    <div class="tab-pane fade in active" id="finish">
        <form class="form-horizontal" id="task-model-finish-form" action="#" method="post">
            <div>
                <br/>
            </div>
            <div class="panel panel-flat new-panel">
                <input type="hidden"  class="form-control" name="id" >
                <input type="hidden"  class="form-control" name="processInstanceId" >
                <input type="hidden"  class="form-control" name="executionId" >
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label class="col-md-4 control-label text-right" ><span style="color:red">*</span>流程参数:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="paramMap"   placeholder="流程参数">
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label class="col-md-4 control-label text-right" >备注:</label>
                                <div class="col-md-8">
                                    <textarea rows="3" cols="15" class="form-control input-remarks" placeholder="描述" name="description"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer" id="task-model-finish-btn">
                <button type="button" class="btn btn-save" onclick="activiti.task.finish.doSubmit()">提交</button>
                <button type="button" class="btn btn-cancel" onclick="activiti.task.create.doClose()">关闭</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>