activiti.FormExt = function(formId,initParam) {
	
	var formJq = $("#" + formId);// jquery对表单对象
	var formValidator = null;//校验器
	
	var _this = this;
	
	/* 初始化表单 */
	var initForm = function(){
		
		/* 初始化选择框，包括单选与多选 */
		var select = formJq.find('.select');
		if(select!=null){
			// select.select2({
			// 	// 去掉这个属性可加上过滤器
			// 	minimumResultsForSearch : "-1"
			// });
		}
		
		/* 初始化附件上传 */
		// var fileUpload = formJq.find('.file-styled');
		// if(fileUpload!=null){
		// 	fileUpload.uniform({
		// 		wrapperClass : 'bg-primary',
		// 		fileButtonHtml : '<i class="icon-googleplus5"></i>'
		// 	});
		// }
		
		/* 初始化日历 */
		var dataTime = formJq.find('.datetimepicker');
		if(dataTime!=null){
			$.each(dataTime,function(i,field){
				var format = $(field).attr("format");
				$(field).datetimepicker({
					format:format
				});
				
			})
		}
		
		
		/* 初始化图片上传 */
		formJq.find('input[inputType="image"]').each(function(){
			var name = $(this).prop("name");
			//alert(name);
			var onclickName = "activiti.openModalWin('"+name+"')";
			var inputHtml = '<input name="'+name+'_TEXT" type="text" readonly="readonly" class="form-control" value="" placeholder="选择弹窗">';
			var buttonHtml = '<span class="input-group-btn">';
			buttonHtml += '<button id="'+name+'_BUTTON" class="btn btn-primary" type="button" data-toggle="modal" onclick="'+onclickName+'">选择</button>';
			buttonHtml += '</span>';
			$(this).before(inputHtml);
			$(this).after(buttonHtml);
			//alert(name+"_TEXT");
		})
/*		var imageUpload = formJq.find('input[inputType="image"]');
		if(imageUpload!=null){
			console.info(imageUpload);
		}*/
		
		/* 初始化附件上传 */
		formJq.find('input[inputType="file"]').each(function(){
			var name = $(this).prop("name");
			//alert(name);
			var onclickName = "activiti.openModalWin('"+name+"')";
			var inputHtml = '<input name="'+name+'_TEXT" type="text" readonly="readonly" class="form-control" value="" placeholder="选择弹窗">';
			var buttonHtml = '<span class="input-group-btn">';
			buttonHtml += '<button id="'+name+'_BUTTON" class="btn btn-primary" type="button" data-toggle="modal" onclick="'+onclickName+'">选择</button>';
			buttonHtml += '</span>';
			$(this).before(inputHtml);
			$(this).after(buttonHtml);
		})
		
		/* 初始化地图 */
		formJq.find('input[inputType="map"]').each(function(){
			var name = $(this).prop("name");
			//alert(name);
			var onclickName = "activiti.openModalWin('"+name+"')";
			var inputHtml = '<input name="'+name+'_TEXT" type="text" readonly="readonly" class="form-control" value="" placeholder="选择弹窗">';
			var buttonHtml = '<span class="input-group-btn">';
			buttonHtml += '<button class="btn btn-primary" type="button" data-toggle="modal" onclick="'+onclickName+'">选择</button>';
			buttonHtml += '</span>';
			$(this).before(inputHtml);
			$(this).after(buttonHtml);
		})

	};
	
	/* 初始化校验器--私有方法 */
	var initValidator = function(){
		/* 校验器 */
		var input = formJq.find('input');
		var messageObj = null;
		if(input!=null){
			$.each(input,function(i,field){
				var message = $(field).attr("message");
				if(message!=null){
					if(messageObj==null){
						messageObj={};
					}
					var name = $(field).attr("name");
					var msgArr = message.split(",");
					if(msgArr.length>1){
						var _tmpObj = {};
						for(var i=0;i<msgArr.length;i++){
							var _tmp = msgArr[i].split(":");
							_tmpObj[_tmp[0]] = _tmp[1];
						}
						messageObj[name] = _tmpObj;
					}else{
						messageObj[name] = message;
					}
				}
			});
		}
//		alert(ToJson(messageObj));
		formValidator = $("#" + formId).validate({
			errorClass: 'validation-error-label',
			messages :messageObj
		});
	};
	
	
	/* 获取表单值 */
	this.formToArr = function() {
		var values = formJq.serializeArray();
		return values;
	}
	
	/* 获取表单值 */
	this.formToObject = function() {
		var values = formJq.serializeArray();
		var retObj = {};
		for (var i = 0; i < values.length; i++) {
			var name = values[i].name;
			var value = values[i].value;
			retObj[name] = value;
		}
		
		//处理多选
		var select = formJq.find('.select[multiple="multiple"]');
		if(select!=null){
			$.each(select,function(i,field){
				var name = field.name;
				var value = $(field).val();
				retObj[name] = value;
			})
		}
		return retObj;
	}
	
	/* 对表单设置值 */
	this.objectToForm = function(paramObj){
		for(name in paramObj){
			var obj = formJq.find(':input[name="'+name+'"]');
			if(obj != null && obj.length > 0){
				if(obj[0].nodeName == "SELECT"){//SELECT元素
					obj.select2("val", paramObj[name]);
				}else if(obj[0].type == "radio"){
					formJq.find(':input[name="'+name+'"][value = "'+paramObj[name]+'"]').attr("checked",true);
				}else{
					obj.val(paramObj[name]);
				}
			}
			
			var value = paramObj[name];
			if(value != null && value != '' && value != undefined && value != 'undefined'){
				var inputType = obj.attr("inputType");
				if(inputType != null && inputType != '' && inputType != undefined && inputType != 'undefined'){
					if(inputType == "image" || inputType == "file"){
						var valSize = value.split(",").length;
						$('input[name="'+name+'_TEXT"]').val(valSize+"个文件被选中");
						//console.info(name+"_TEXT");
					}else if(inputType == "map"){
					/*	var geoc = new BMap.Geocoder();   
						var p = value.split(",");
						var pt = new BMap.Point(p[0],p[1]);
						geoc.getLocation(pt, function(rs){
							var addComp = rs.addressComponents;
							//alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + 
									//addComp.streetNumber);
							var address = addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber;
							//alert(address);
							$('input[name="'+name+'_TEXT"]').val(address);
						});  */
					}
				}
			}else{
				$('input[name="'+name+'_TEXT"]').val("");
			}
			
		}
	}
	
	/* 获取某一元素的值 */
	this.getValue = function(eleName){
		var obj = formJq.find(':input[name="'+eleName+'"]');
		var value = obj.val();
		return value;
	}
	
	/* 对某一元素赋值 */
	this.setValue = function(eleName,value){
		var obj = formJq.find(':input[name="'+eleName+'"]');
		if(obj[0].nodeName == "SELECT"){//SELECT元素
			obj.select2("val", value);
		}else if(obj[0].type == "radio"){
			formJq.find(':input[name="'+eleName+'"][value = "'+value+'"]').attr("checked",true);
		}else{
			obj.val(value);
		}
		if(value != null && value != '' && value != undefined && value != 'undefined'){
			var inputType = obj.attr("inputType");
			if(inputType != null && inputType != '' && inputType != undefined && inputType != 'undefined'){
				if(inputType == "image" || inputType == "file"){
					var valSize = value.split(",").length;
					$('input[name="'+eleName+'_TEXT"]').val(valSize+"个文件被选中");
					//console.info(eleName+"_TEXT");
				}else if(inputType == "map"){
				/*	var geoc = new BMap.Geocoder();   
					var p = value.split(",");
					var pt = new BMap.Point(p[0],p[1]);
					geoc.getLocation(pt, function(rs){
						var addComp = rs.addressComponents;
						//alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + 
								//addComp.streetNumber);
						var address = addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber;
						//alert(address);
						$('input[name="'+eleName+'_TEXT"]').val(address);
					});  */
				}
			}
		}else{
			$('input[name="'+eleName+'_TEXT"]').val("");
		}
	}
	
	//只获取地址的值
	this.getAddress = function(eleName){
		var value = $('input[name="'+eleName+'"]').attr("address");
		return value;
	}
	
	//只对地址设置值
	this.setAddress = function(eleName,value){
		$('input[name="'+eleName+'"]').attr("address", value);
		$('input[name="'+eleName+'_TEXT"]').val(value);
		//var obj = formJq.find(':input[name="'+eleName+'_TEXT"]');
		//obj.val(value);
	}
	
	/* 获取sel 对应的文本 */
	this.getSelText = function(eleName){
		var obj = formJq.find(':input[name="'+eleName+'"]').select2('data');
		var ret = null;
		if(obj.length!=null && obj.length > 0){
			ret = [];
			for(var i=0;i<obj.length;i++){
				ret.push(obj[i].text);
			}
		}else{
			ret = obj.text;
		}
		return ret;
	}
	
	/* 设置select标签待选值 */
	this.selOptionAddAll = function(eleName, objectArry, textName, valueName,isClear){
		var select = formJq.find(':input[name="'+eleName+'"]');
		if(isClear){//清空原数据
			select.empty();
		}
		if(objectArry!=null && objectArry.length>0){
			for(var i=0;i<objectArry.length;i++){
//				if(objectArry[i][valueName] == selectedValue){
//					select.append("<option value='" + objectArry[i][valueName] + "' selected>&nbsp;" + objectArry[i][textName] + "</option>");
//				}else{
//					
//				}
				select.append("<option value='" + objectArry[i][valueName] + "'>&nbsp;" + objectArry[i][textName] + "</option>");
			}
		}
	}
	
	//获取某一元素的对象
	this.getObject = function(eleName){
		var object = formJq.find(':input[name="'+eleName+'"]');
		return object;
	}
	
	//获取该表单对象
	this.getFormJq = function(){
		return formJq;
	}
	
	/* 松验 */
	this.validate = function(){
		return formValidator.form();
	}
	
	//重置表单
	this.reset = function(){
		formJq[0].reset();
	}
	
	initForm();
	initValidator();
};
