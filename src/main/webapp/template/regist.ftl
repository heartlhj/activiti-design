  <#assign base=request.contextPath />
 	<div class="modal fade" id="myModalRegist" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					 <div class="modal-header">
                    <button type="button" id="login_close" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="loginModalLabel" style="font-size: 18px;">注册</h4>
                </div>
                <div class="modal-body">
                    
<div class="pageContent">

	<form action="${base}/admin/sysUser/save" method="post"  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
                 
                      <div class="modal-body"> 
                      <div class="row">
						  		   <div class="col-md-12">
                             <div class="form-group">
	                             <label for="txt_departmentname" class="col-sm-2 control-label text-right"">账号：</label>
								<div class="col-sm-9">
							     <input class="form-control" maxlength="50" name="account_l" placeholder="请输入邮箱账号/手机号" type="text">
							    </div>
						    </div>
						     </div>
						    </div>
						    
						  <div class="row">
						     &nbsp;
						      </div>
						       <div class="row">
						  		   <div class="col-md-12">
						    <div class="form-group">
							     <label for="inputPassword" class="col-sm-2 control-label text-right"">密码：</label>
								<div class="col-sm-9">
							    <input type="password" class="form-control input-sm"  name= "userPassword" placeholder="请输入密码">
							    </div>
                             </div>
                             
                             </div>
                             </div>
                          </div>  
                        </form>
                            <div class="modal-footer">
			                   	 <button type="submit" class="btn btn-primary">保存</button>
			               		 <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			                </div>

                </div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>

