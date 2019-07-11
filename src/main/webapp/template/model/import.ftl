<!DOCTYPE html>
<html lang="en">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />

<head>
    <!--页面title-->
    <div class="modal-header bg-primary">
        <button type="button" class="close" data-dismiss="modal" onclick="activiti.model.import.doClose()">&times;</button>
        <h6 class="modal-title">模型导入</h6>
    </div>
<script type="text/javascript" src="${contextPath}/static/js/ini/import.js"></script>

</head>

<body>

<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="basicMessage">
        <form class="form-horizontal" id="activiti-model-import--form"  enctype="multipart/form-data" action="#" method="post">
            <div>
                <br/>
            </div>
            <div class="panel panel-flat new-panel">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label class="col-md-4 control-label text-right" >选择文件:</label>
                                <div class="col-md-8">
                                    <input type="file" name="file" id="activiti-import"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer" id="activiti-model-import-btn">
                <button type="button" class="btn btn-save" onclick="activiti.model.import.doSubmit()">导入</button>
                <button type="button" class="btn btn-cancel" onclick="activiti.model.import.doClose()">取消</button>
            </div>
        </form>
    </div>
</div>
</body>

</html>