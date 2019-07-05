activiti.utils = function() {
	var _modalWinObj = {};

	var getModalHTML = function(eleId){
		var html = [];
		html.push('<div id="'+eleId+'" class="modal fade">');
		html.push('</div>');
		return html.join("");
	}
	
	
	function getDateForStringDate(strDate){
	    //切割年月日与时分秒称为数组
	    var s = strDate.split(" "); 
	    if(s.length ==1){
	    	var s1 = s[0].split("-"); 
	    	return new Date(s1[0],s1[1]-1,s1[2]);
	    }else{
	    	var s1 = s[0].split("-"); 
		    var s2 = s[1].split(":");
		    if(s2.length==2){
		        s2.push("00");
		    }
		    return new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1],s2[2]);
	    }
	   
	}
	
	return {
		Invoke:function(url,paramObj,dateType){
			var ret = null;
			$.ajax({
				url : url,
				type: 'POST', 
				async : true,
				data : paramObj,
				dataType: dateType,
				success : function(msg) {
					if(msg == 'success'){
						activiti.Alert("操作成功");
						ret = msg;
					}else if(msg == 'failure'){
						activiti.Alert("操作失败");
						ret = msg;
					}else if(msg != null && msg != ""){
						ret = $.parseJSON(msg);
					}
				}

			});
			return ret;
		},
		InvokeHasCalkBack:function(url,paramObj,dateType,contentType,callback){
			var ret = null;
			$.ajax({
				url : url,
				type: 'POST', 
				async : true,
				data : paramObj,
				contentType:contentType,
				dataType: dateType,
				success : function(msg) {
					callback(msg);
				},
				error:function(){
					activiti.Alert("操作失败");
				}
			});
		},
		
		jsonToData:function(obj,param){
			for(var i = 0 ;i < obj.length;i++){
				var label = $(obj[i]);
				var clazz = label.attr("id")
				label.text(param[clazz]);
			}
			
		},
		formatDate : function(cellValue,options,rowObject){
			if(cellValue == "" || cellValue == null){
				return "";
			}
			if(!activiti.IsNull(cellValue)){
				var date = getDateForStringDate(cellValue);
				return date.format("yyyy-MM-dd");
			}else{
				return "0000-00-00";
			}

		},
		
		//数字四舍五入（保留n位小数）
		getFourOutFiveIn : function(number,n){
		  n = n ? parseInt(n) : 0; 
		  if (n <= 0) return Math.round(number); 
		  number = Math.round(number * Math.pow(10, n)) / Math.pow(10, n); 
		  return number; 
		},
		
		//金额取2位小数
		formatMoney : function(cellValue,options,rowObject){
			if(cellValue == "" || cellValue == null){
				return "0.00";
			}
			if(!activiti.IsNull(cellValue)){
				var value = Number(cellValue).toFixed(2);
				//value =  (value || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
				return value;
			}else{
				return "0.00";
			}
		},

		//单价取2位小数
		formatPrice : function(cellValue,options,rowObject){
			if(cellValue == "" || cellValue == null){
				return "0.00";
			}
			if(!activiti.IsNull(cellValue)){
				var value = Number(cellValue).toFixed(2);
				//value =  (value || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
				return value;
			}else{
				return "0.00";
			}
		},
		
		//数量取4位小数
		formatNumber : function(cellValue,options,rowObject){
			if(cellValue == "" || cellValue == null){
				return "0.0000";
			}
			if(!activiti.IsNull(cellValue)){
				var loanMoney = Number(cellValue).toFixed(4);
				//loanMoney =  (loanMoney || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
				return loanMoney;
			}else{
				return "0.0000";
			}
		},
		
		//短日期加一天（2018-01-01》2018-01-02）
		dayAddOne : function(strDate){
			var now = new Date(strDate);
			now.setDate(now.getDate()+1);  
			var year = now.getFullYear();
            var month =(now.getMonth() + 1).toString();
            var day = (now.getDate()).toString();
            if (month.length == 1) {
                month = "0" + month;
            }
            if (day.length == 1) {
                day = "0" + day;
            }
            var dateTime = year + "-" + month + "-" +  day;
			return dateTime;
		},
		
		banIcon: function(temp,ulId,banMethod){
			if(temp == null){
				checkBtnState(ulId,[]);
				return;
			}
			
			for(var i=0;i<temp.length;i++){
				if(banMethod != undefined){
					if(banMethod(temp[i])){
						return;
					}
				}
			}
			checkBtnState(ulId,[]);
		},
		banIcon2: function(temp,ulId,banMethod){
			if(temp == null){
				checkBtnState(ulId,[]);
				return;
			}
			var ids = [];
			
			for(var i=0;i<temp.length;i++){
				if(banMethod != undefined){
					var tempArray = banMethod(temp[i]);
					for(var j=0;j<tempArray.length;j++){
						ids.push(tempArray[j]);
					}
				}
			}
			checkBtnState(ulId,ids);
		},
		/**
		 * temp 选择的列表
		 * ulId ul的Id
		 * banMethod 自己禁用的逻辑
		 * banId 不可多选的要禁止的a便签ID数组
		 */
		banIcon3: function(temp,ulId,banMethod,banIds){
			if(temp == null){
				activiti.utils.checkBtnState(ulId,[]);
				return;
			}
			var ids = [];
			for(var i=0;i<temp.length;i++){
				if(temp.length >1){
					ids = banIds;
				}
				if(banMethod != undefined){
					var tempArray = banMethod(temp[i]);
					for(var j=0;j<tempArray.length;j++){
						ids.push(tempArray[j]);
					}
				}
			}
			activiti.utils.checkBtnState(ulId,ids);
		},
		
		showModalWin:function(){
			var id = "shade"
			eleId = 'primary_'+id;
			var obj = {
				eleId:eleId
			};
			_modalWinObj[id] = obj;
			var html = getModalHTML(eleId);
			$('#modalWin').after(html);
			$('#'+obj.eleId).css({
				 width: '100%',
				 background:'blue',
				 "position": "absolute",
				 "z-index": 999999,
			});
			$('#'+obj.eleId).modal('show');
		},
		
		hideModalWin:function(){
			var id = "shade";
			var obj = _modalWinObj[id];
			$('#'+obj.eleId).modal('hide');//隐藏窗口
			_modalWinObj[id] = null;
		},
		sleep: function(milliSeconds){
		    var startTime = new Date().getTime(); // get the current time    
		    while (new Date().getTime() < startTime + milliSeconds);
		},
		checkBtnState:function(ul,ids){
			if(activiti.IsNull(ids)){
				$("#"+ul+" a").show();
			}else{
			    for(var i =0;i<ids.length;i++){
			    	var temp = ids[i];
			    	$("#"+ul+" #"+temp).hide();
				}
			}
			
		},
		/* 远程方法调用--未测试  */
		InvokeMethodAsyn:function(url,paramObj,flag,fn){
			$.ajax({
				url : url,
				type: 'POST', 
				async : true,
				data : paramObj,
				dataType: 'json',
				success : function(msg) {
					if(!activiti.IsNull(msg)){
						if(msg.returnCode == '999' && flag){//后台抛出异常
							if(!activiti.IsNull(msg.returnMsg)){
								activiti.Alert(msg.returnMsg);
							}else{
								activiti.Alert("操作失败");
							}
						}else if(msg.returnCode == '1000' && flag){
							activiti.Alert("操作成功");
						}
						if(fn!=null){
							if(activiti.IsNull(msg.returnData)){//兼容以前代码外面并没有套处理结果DealResult那一层
								fn(msg);
							}else{
								fn(msg.returnData);
							}
						}
					}
				}

			});
		},
		
		vailSelect:function(id){
			var div =  $("#"+id+" div[requsetSelect]");
			var sign  = false;
			for(var i = 0 ;i< div.length;i++){
				temp = $(div[i]);
				var selectpicker = $("#"+id+" div[requsetSelect]:eq("+i+") .selectpicker")
				if(selectpicker.selectpicker('val') == ""){
					temp.append("<label id='receiptDate-error' class='validation-error-label' for='receiptDate' style='display: block;'>required:请选择</label>");
					sign = true;
				}
			}
			return sign;
		},
		removeHTMLTag:function(str){
			str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
		    str = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
		    //str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
		    str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
		    str=str.replace(/(^\s*)|(\s*$)/g, "");//去除前后空格
		    return str;
		},
		setSum:function(id,obj){
			var vs  = $("#"+id+" ul li .value_span");
			for(var i=0;i<vs.length;i++){
				var idString = $(vs[i]).attr("id");
				var id = idString.substring(6,idString.length);
				var value = obj[id];
				$(vs[i]).text(value);
			}
		},
		initSum:function(paramObj){
			activiti.InvokeMethodAsyn(paramObj.url,paramObj.param,function(msg){
				activiti.utils.setSum(paramObj.id,msg);
			});
		},
		setSumColumn:function(sourceId,paramObj){
			$(".colum_"+sourceId).remove();
			activiti.InvokeMethodAsyn(paramObj.url,paramObj.param,function(msg){
				$(".colum_"+sourceId).remove();
				var dw = $("table[aria-labelledby='gbox_"+sourceId+"']").width();
				debugger;
				$("#gview_"+sourceId+ " .ui-jqgrid-bdiv").append("<div class='colum_"+sourceId+" countDiv' style='width:"+dw+"px'></div>");

				var th = $("#gview_"+sourceId+ " .ui-common-table th");
				var tableHtml = "<table id='sum_"+sourceId+"' class='sum_table'><tr>";
				for(var i=0;i<th.length;i++){
					var temp = $(th[i]);
					var width = temp.css("width");
					var style = "";
					if(i==0){
						var widthNum =parseFloat(width.substring(0,width.indexOf("px"))-1);
						width = widthNum+"px";
						style ="width:"+width+";border-left:none;";
					}else{
						style ="width:"+width;
					}
					var display = temp.css("display");
					var idString = temp.attr("id");
					var id = idString.substring(sourceId.length+1,idString.length);
					if(display != "none"){
						tableHtml += "<td style='"+style+"' ml='"+id+"' align='center'></td>";
					}else{
						tableHtml += "<td style='width:0px;border:none' ml='"+id+"' align='left'></td>";
					}
				}
				tableHtml += "</tr></table>";
				$(".colum_"+sourceId).append(tableHtml);
				var td = $("td[ml]");
				for(var j=0;j<td.length;j++){
					var te = $(td[j]);
					var ml = te.attr("ml");
					var number = msg[ml];
					var fomartNumber = activiti.utils.toThousands(number);
					te.text(fomartNumber);
				}
			});
		},
		addSumColumn:function(gridObj,grid_id,url,param){
			debugger;
			var ths = $("#gview_" + grid_id + " th");
			var th_2 = ths[1];//id列
			var id_2 = $(th_2).attr("id");
			var id = id_2.substring(grid_id.length+1);
			activiti.InvokeMethodAsyn(url,param,function(msg){
				debugger;
				if(null != msg){
					var list = msg.root;
					var item = list[0];
					gridObj.addItem(item[id],item);
				}
			});
		},
		showButton : function(ulId){
			$("ul[id='" + ulId + "']").find("li[class='on']").show();
		},
		hideButton : function(aIds){
			if(aIds != null && aIds.length > 0){
				for(var i in aIds){
					var aId = aIds[i];
					if(aId != null && aId != ""){
						$("#" + aId).hide().addClass("on");
					}
				}
			}
		},
		toThousands:function(s) {
			  if(isNaN(s) || s ==null){  
	                return "";  
	            }  
	        else{  
	                s=parseFloat(s);//去除首位输入的0，如002，小数不影响parseFloat字符串转数字  
	                s=s.toString();  
	                s = s.replace(/^(\d*)$/, "$1.");  
	                s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");  
	                s = s.replace(".", ",");  
	                var re = /(\d)(\d{3},)/;  
	                while (re.test(s))  
	                    s = s.replace(re, "$1,$2");  
	                s = s.replace(/,(\d\d)$/, ".$1");  
	                return s.replace(/^\./, "0.");  
	             }        
		}
		
	}
}();

$(function(){
	 $(".selectpicker").change(function(){
		 $("div[requsetSelect] label[class='validation-error-label']").remove();
	 })
});



 
