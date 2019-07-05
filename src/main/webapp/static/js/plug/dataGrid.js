//////////////////////////////////////////
//activitiSoft corp. 2016-03-01
//Author :yu.xiao
//commits:数据列表
//////////////////////////////////////////

//类定义
activiti.Package("activiti");

activiti.DataGrid = function(dataGridId, initParam,disableEdit) {
	
	var grid = null;//
	var gridWidth = 0;
	var _this = this;
	var lastSel;
	var savesuccessfunc = $("#" + dataGridId).attr("savesuccessfunc");// 保存事件
	var colModel=[];
	var gridParams = {};
	var disEditRowIds= [];
	/* 初始化--私有方法  */
	var init = function() {
		var dataTable = $("#" + dataGridId);

		var height = dataTable.attr("height");// 高度
		if(activiti.IsNull(height)){
			height = dataTable.parent().height()-80;
		}
		dataTable.removeAttr("height");
		
//		var width = dataTable.attr("width");// 宽度
//		dataTable.removeAttr("width");
		
		var url = dataTable.attr("url");// 
		var idPropertyName = dataTable.attr("idPropertyName");// 将哪一列作为ID
		var showCheck = dataTable.attr("showCheck") == "true" // 是否有checkBox,true/false
		var rowNum = dataTable.attr("rowNum");// 每页多少行
		
		var enterWrap = dataTable.attr("enterWrap") == "true" ? true : false;//是否需要回车换行
		
//		if(rowNum!=null){
//			rowNum=10;
//		}else{
//			rowNum=-1;
//		}
		if(rowNum == null){
			rowNum = -1;
		}
//		var autoWidth = dataTable.attr("autoWidth") != null ? dataTable.attr("autoWidth") == "true" : true;// 
//		var forceFit = dataTable.attr("forceFit") != null ? dataTable.attr("forceFit") == "true" : true;// 
		
		var pagerid = dataTable.attr("pagerid");// 每页标识
		var pager = pagerid != null ? $('#' + pagerid) : null;

		var treeFlag = dataTable.attr("treeFlag") == "true";// 是否为树行结果
		var treeCode = dataTable.attr("treeCode");// 用哪一列折叠

		var onItemClick = dataTable.attr("onItemClick");// 单击事件
		var onItemDblClick = dataTable.attr("onItemDblClick");// 双击事件
		var rownumbers = dataTable.attr("rownumbers") == "true"; //是否显示
		var colNames = [];
//		var colModel = [];
		
		var queryArr = []; //查询条件
		
		var rowList= dataTable.attr("rowList") ? dataTable.attr("rowList").split(",") : null ;

		if(!rowList){
			rowList=["全部",10,20,50];
		}
		// 拼装colModel与colNames
		dataTable.find("td").each(function(i, n) {
			var td = $(n);
			var display = td.attr("display") == "true" ? true : false;// 是否展示，true展示，false不展示
			var displayName = td.attr("displayName");// 展示名称，如果display为false，此项可为空
			var width = td.attr("width");// 宽度
			var propertyName = td.attr("propertyName");// 字段编码
			var sortType = td.attr("sortType");// 排序类型
			var align = td.attr("align");// 对齐方式 left, center, right
			var formatter = td.attr("formatter");
			var editable = td.attr("editable") == "true" ? true : false;//是否可编辑
			var edittype = td.attr("edittype");//编辑模式
			var editoptions = td.attr("editoptions");//编辑模式下的其他参数
			//var editrules = td.attr("editrules");//编辑模式下的校验
			var initDate = td.attr("initDate");
			var dateFormat = td.attr("dateFormat");
			var dateViewModel = td.attr("dateViewModel");
			var query = td.attr('query');
			if (query) {
				var idx = i;
				if (showCheck) {
					idx++;
				}
				if (rownumbers) {
					idx++;
				}
				queryArr.push({
					idx:idx,
					queryType: query,
					text: displayName,
					name: 'query_'+propertyName
				});
			}
			colNames.push(displayName);
			var col = {
					name : propertyName ? propertyName : null,
					index : propertyName ? propertyName : null,
					width : width ? width : null,
					align : align ? align : null,
					sortable : false, // 禁用排序功能
					sorttype : sortType ? sortType : null,
					formatter : formatter != 'undefined' ? eval(formatter) : '',
					hidden : display == true ? false : true
					
			};
			if(!disableEdit && editable){
				col.editable = editable == true ? true : false;
				//编辑模式
				if(edittype != null && edittype != '' && edittype != 'undefined'){
					col.edittype = edittype;
				}
				//编辑模式下的其他参数
				if(editoptions != null && editoptions != '' && editoptions != 'undefined'){
					col.editoptions = $.parseJSON(editoptions);
				}
				if(initDate){
					var format= dateFormat || "YYYY-MM-DD";
					col.editoptions = {};
					col.editoptions.dataInit=function(element){
						
						if(dateViewModel === "time"){
							$(element).pickatime({
								format:"HH:i",
							});
						}else{
							
							$(element).datetimepicker({
								format:format,
								autoClose:true
							});
						}
						
						
						$(element).keydown(function(e){
							//回车事件
							if(e.keyCode == 13){
								//销毁dateTimePicker
								$(element).data("DateTimePicker").destroy();
							}
						})
					}
				}
				//编辑模式下的校验
				/*if(editrules != null && editrules != '' && editrules != 'undefined'){
					col.editrules = $.parseJSON(editrules);
				}*/
			}
			
			colModel.push(col);
		});
		//alert(activiti.ToJson(colModel));
		 gridParams = {
			url:url,
			datatype : "json",
			mtype : "POST",
			height : height,// 高度，表格高度。可为数值、百分比或'auto'
//			width : width,// 这个宽度不能为百分比
			autowidth : true,// 自动宽
			forceFit : false,// 当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit为false时，此属性会被忽略
			shrinkToFit : true,// 此属性用来说明当初始化列宽度时候的计算类型，如果为ture，则按比例初始化列宽度。如果为false，则列宽度使用colModel指定的宽度
			colNames : colNames,// 列名
			colModel : colModel,// 列模型
			rowList:rowList,//每页显示的记录数，格式为[10,20,30]
			viewrecords:true,//是否要显示总记录数信息
			scrollOffset: 18,
			treeGrid : treeFlag,// 是否为树型结构
			treeGridModel : treeFlag ? 'adjacency' : null,// 结构模型
			ExpandColumn : treeFlag == true ? treeCode : null,// 哪一列折叠
			ExpandColClick : true,// 折叠时

			multiselect : showCheck, // 带有复选框时，使用多选

			pager : pager,// 分页
			rowNum : rowNum,// 每页多少行，不分页时应该为-1,这里默认显示10
			rownumbers : rownumbers, // 是否显示序号列
			prmNames : {
				page : "page", // 表示请求页码的参数名称
				rows : "limit", // 表示请求行数的参数名称
//				sort: "sidx", // 表示用于排序的列名的参数名称
//				order: "sord", // 表示采用的排序方式的参数名称
//				search : "search" // 表示是否是搜索请求的参数名称
//				nd:"nd", // 表示已经发送请求的次数的参数名称
//				id:"id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
//				oper:"oper", // operation参数名称（我暂时还没用到）
//				editoper:"edit", // 当在edit模式中提交数据时，操作的名称
//				addoper:"add", // 当在add模式中提交数据时，操作的名称
//				deloper:"del", // 当在delete模式中提交数据时，操作的名称
//				subgridid:"id", // 当点击以载入数据到子表时，传递的数据名称
//				npage: null,
//				totalrows:"totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal
			},
			jsonReader : {
				repeatitems : false,
				root : "root", // json中代表实际模型数据的入口
				page : "page", // json中代表当前页码的数据
				total : "total", // json中代表页码总数的数据
				records : "records", // json中代表数据行总数的数据
				id : idPropertyName
			},

			onSelectRow : function(rowId, status) {
				if (onItemClick != null)
					eval(onItemClick + "('" + rowId + "')");
				
				if($.inArray(rowId,disEditRowIds) != -1)
					return;
				if(rowId && rowId !== lastSel){
			    	 //$("#" + dataGridId).restoreRow(lastSel);//在将被选中的行转为编辑模式前，判断是否已经存在编辑的行，存在则取消此行编辑模式还原为原始状态
			    	 //$("#" + dataGridId).saveRow(lastSel,_this._SAVESUCCESSFUNC);
			    	 $("#" + dataGridId).saveRow(lastSel);
//			    	 if(!activiti.IsNull(lastSel)){
//			    		 _this._SAVESUCCESSFUNC();
//			    	 }
			    	 //$("#" + dataGridId).jqGrid('saveRow',"rowid", false, 'clientArray');//仅保存数据到grid中，而不会发送ajax请求服务器
			    	 
			    	//区分数据是新增还是修改
			    	 var rowData = $("#" + dataGridId).getRowData(lastSel);
			    	 //var rowData = {};
			    	 if(rowData != null){
			    		 if(rowData.pageDateType != null && rowData.pageDateType == '' && rowData.pageDateType != undefined && rowData.pageDateType != 'undefined'){
			    			 rowData.pageDateType = 'UPDATE';
			    			 $("#" + dataGridId).setRowData(lastSel, rowData, null);//保存的时候修改成UPDATE
			    		 }
			    	 }
			    	 
			    	 lastSel=rowId; 
			    	 
			      }
			    $("#" + dataGridId).editRow(rowId, true);
			     
			},

			ondblClickRow : function(rowId, iRow, iCol, e) {
				if (onItemDblClick != null)
					eval(onItemDblClick + "('" + rowId + "')");
			},
				
			//加载成功回调函数
			loadComplete : function() {
//				alert();
			}
		};
		
		$.extend(gridParams,initParam);
		
		var parentEle = grid = $("#" + dataGridId).parent();
		
		grid = $("#" + dataGridId).jqGrid(gridParams);
		
		//生成查询条件
		var len = queryArr.length;
		if (len > 0) {
			var $queryBox = grid.parents('.ui-jqgrid.ui-widget.ui-widget-content.ui-corner-all').prev('.query-box')
			var $thTableColne = grid.parents('.ui-jqgrid-bdiv').prev('.ui-jqgrid-hdiv.ui-state-default.ui-corner-top')
				.find('table.ui-jqgrid-htable.ui-common-table').clone().addClass('queryTh');
			$thTableColne.find('th').attr('id', '')
			$thTableColne.find('th').html('').css({
				'border': 'none',
				'background': '#fff'
			})
			for (var i = 0; i < len; i++) {
				var div2 = '';
				var text = $.trim(queryArr[i].text);
				switch (queryArr[i].queryType) {
				case 'text':
					div2 = '<input type="text" class="form-control query" placeholder="'+text+'" name="'+
						queryArr[i].name+'">'
					break;
				case 'date':
					div2 = '<input type="text" class="form-control datetimepicker query" placeholder="'+text+'" name="'+
						queryArr[i].name+'">'
					break;
				case 'select':
					div2 = '<select name="'+queryArr[i].name+'" class="form-control query"></select>'	
					break;
				default:
					break;
				}
				$thTableColne.find('th').eq(queryArr[i].idx).html(div2).addClass('hasQuery')
			}
			var $targetGrid = $queryBox.next('.ui-jqgrid.ui-widget.ui-widget-content.ui-corner-all')
			$thTableColne.insertBefore($targetGrid.find('.ui-jqgrid-htable.ui-common-table'))
			$thTableColne.append($queryBox.wrap('<th></th>'))
			$thTableColne.find('.datetimepicker').datetimepicker({
				format: 'YYYY-MM-DD hh:mm:ss',
				autoClose: true,
				useCurrent: false
			})
		}
		//调整父元素宽度变更50以上调整
		parentEle.resize(function(e) {
			var width = parentEle.width();
			//很多页面的table外面没有套div，加这个设置高度会导致高度一直增长
//			//设置高度
//			var pHeight = parentEle.height()-80;
//			if(!activiti.IsNull(height) && pHeight < height){
//				grid.setGridHeight(pHeight);
//			}
//			console.info(height);
//			console.info(gridWidth);
			var _widthAdd = width-gridWidth;
			if(_widthAdd>20 || _widthAdd<-20){
				grid.setGridWidth(width-2);
				
//				//设置grid的高度
//				var _offsetTop = $("#middlexxxxxxxxx").offset().top;
//				var parentDiv = $("#middlexxxxxxxxx").parent();
//				if("mainContent" == parentDiv.prop("id")){
//					var height = parentDiv.height();
//					console.info(height);
//					grid.setGridHeight(height-_offsetTop-50);
//				}

			}
			gridWidth = width;
//			alert("宽度：" + width);
			
		});
		
		//编辑单元格事件失去焦点回调
		if(!activiti.IsNull(savesuccessfunc)){
			$("#gbox_" +dataGridId).on("blur",".editable",eval(savesuccessfunc));
		}
	}
	
	/* 获取所选行rowid */
	this.getSelectedId = function() {
		var rowid = grid.getGridParam("selrow");
		return rowid;
	};

	/* 获取所选行数据 */
	this.getSelectedItem = function() {
		var retObj = null;
		var rowid = grid.getGridParam("selrow");
		if (rowid != null) {
			retObj = grid.getRowData(rowid);
			if(!disableEdit){
				for(var key in retObj){
					var data = $($.parseHTML(retObj[key]))[0];
					if(data && (data.nodeName == "INPUT" || data.nodeName == "SELECT")){
						retObj[key] = document.getElementById(data.id).value;;
					}
				}
			}
		}
		return retObj;
	};

	this._SAVESUCCESSFUNC = function() {
		if (savesuccessfunc != null && savesuccessfunc != 'undefined')
			eval(savesuccessfunc + "()");
	}
	/* 获取勾选行 */
	this.getCheckedItems = function() {
		var retList = null;
		var rowids = grid.getGridParam("selarrrow");

		if (rowids != null && rowids.length > 0) {
			retList = [];
			for (var i = 0; i < rowids.length; i++) {
				var obj = grid.getRowData(rowids[i]);
				retList.push(obj);
			}
		}

		return retList;
	};
	

	/* 获取勾选行的id数组 */
	this.getCheckedIds = function() {
		var rowids = grid.getGridParam("selarrrow");
		return rowids;
	}
	
	/* 设置多打勾  */
	this.setCheckedItems = function(rowIds) {

		if (rowIds != null && rowIds.length > 0) {
			for (var i = 0; i < rowIds.length; i++) {
				grid.setSelection(rowIds[i]);
			}
		}

	}
	/* 设置单打勾 */
	this.setCheckedItem = function(rowId) {
		
		grid.setSelection(rowId);
	}

	/* 根据rowId获取数据 */
	this.getItemByRowId = function(rowId) {
		var retObj = grid.getRowData(rowId);
		return retObj;
	}
	
	/* 根据rowId获取子节点数据 */
	this.getChildItemsByRowId = function(rowId){
		var record = grid.getRowData(rowId);
		console.info(record);
		var children = grid.getNodeChildren(record);
		console.info(children);
		return children
	}
	
	
	/* 加载数据 */
	this.loadData = function(url, paramObj) {
		grid.setGridParam({
			postData : paramObj,
			url : url,
			page : 1
		});
		grid.trigger("reloadGrid");
	}

	/* 增加一行数据 */
	this.addItem = function(rowid,data, position, srcrowid){
		grid.addRowData(rowid,data, position, srcrowid);
	}
	
	/* 增加一行编辑行 */
	this.addEditItem = function(){
    	var rowData = {};
    	rowData.pageDateType = 'CREATE';
		//随机生成一个数字作为唯一的id
		var randomId = "JQGRID_"+Math.random();
    	//var randomId = new Date().getTime();
		next_rowid = randomId;
		$("#" + dataGridId).jqGrid('addRowData', next_rowid, rowData); // 增加下一行
		$("#" + dataGridId).jqGrid('editRow', next_rowid, true);
		$("#" + dataGridId).jqGrid("saveRow", next_rowid, null );
	}
	
	/* 树型结构增加子节点 */
	this.addChildItem = function(rowId,parentId,data){
		grid.addChildNode(rowId,parentId,data);
	}
	
	/* 返回所有ID  */
	this.getAllIds = function(){
		return grid.getDataIDs();
	}
	
	/* 返回所有数据 */
	this.getAllItems = function(){
		
		//区分数据是新增还是修改
//		if(lastSel != null && lastSel != '' && lastSel != undefined && lastSel != 'undefined'){
//			$("#" + dataGridId).jqGrid("saveRow", lastSel, null );
//			//$("#" + dataGridId).saveRow(lastSel,_this._SAVESUCCESSFUNC);
////			_this._SAVESUCCESSFUNC();
//			var rowData = $("#" + dataGridId).getRowData(lastSel);
//	    	 //var rowData = {};
//	    	 if(rowData != null){
//	    		 if(rowData.pageDateType != null && rowData.pageDateType == '' && rowData.pageDateType != undefined && rowData.pageDateType != 'undefined'){
//	    			 rowData.pageDateType = 'UPDATE';
//	    			 $("#" + dataGridId).setRowData(lastSel, rowData, null);//保存的时候修改成UPDATE
//	    		 }
//	    	 }
//		}
		
		var ids = grid.getDataIDs();
		var retList = null;
		if(ids!=null && ids.length>0){
			retList = [];
			for(var i=0;i<ids.length;i++){
				var obj = grid.getRowData(ids[i]);
				if(!disableEdit){
					for(var key in obj){
						var data = $($.parseHTML(obj[key]))[0];
						if(data && (data.nodeName == "INPUT" || data.nodeName == "SELECT")){
							obj[key] = document.getElementById(data.id).value;
						}
					}
				}
				retList.push(obj);
			}
		}
		
		return retList;
	}
	
	/* 移除节点  */
	this.removeItem = function(rowId){
		grid.delRowData(rowId);
	}
	
	/* 移除节点及子节点  */
	this.removeItemChild = function(rowId){
		grid.delTreeNode(rowId);
	}
	
	/* 判断 此节点是否存在 */
	this.isExist = function(rowId){
		var flag = false;
		var ids = grid.getDataIDs();
		if(ids!=null && ids.length>0){
			for(var i=0;i<ids.length;i++){
				if(rowId == ids[i]){
					flag = true;
					break;
				}
			}
		}
		var retObj = grid.getRowData(rowId);
		return flag;
	}
	
	this.setGridWidth = function(width){
		grid.setGridWidth(width);
	}

	//清除整个表格的数据
	this.clearData = function(){
		debugger;
		grid.clearGridData();
	}
	
	//更新数据
	this.updateItem = function(rowid, data, cssprop){
		grid.setRowData(rowid, data, cssprop);
	}
	
	//设置单元格
	this.setCell = function(rowid,colname, data, cssClass, properties){
		grid.setCell(rowid,colname, data, cssClass, properties);
	}
	
	//隐藏列
	this.hideCol = function(colname){
		grid.setGridParam().hideCol(colname).trigger("reloadGrid");
	}
	
	//显示列
	this.showCol = function(colname){
		grid.setGridParam().showCol(colname).trigger("reloadGrid");
	}
	
	//禁用编辑
	this.disableEdit = function(){
		colModel.forEach(function(i,n){
			if(i.hasOwnProperty("editable")){
				i.editable=false
			}
		});
		this.setGridParam({colModel:colModel});
		
	};
	
	//启用编辑
	this.enableEdit = function(){
		colModel.forEach(function(i,n){
			if(i.hasOwnProperty("editable")){
				i.editable=true;
			}
		});
		this.setGridParam({colModel:colModel},true);
		
	}
	
	//禁用列编辑
	this.disableColEdit = function(colname){
		if(colname){
			
			colname = typeof colname === 'string' ? [colname] : colname
			colModel.forEach(function(i,n){
				if($.inArray(i.name,colname) !== -1){
					
					i.editable=false;
				}
			});
			grid.setGridParam({colModel:colModel},true).trigger("reloadGrid")
		}else{
			console.error("请输入列名");
		}
		
	}
	
	//启用列编辑
	this.enableColEdit = function(colname){
		if(colname){
			colname = typeof colname === 'string' ? [colname] : colname
			colModel.forEach(function(i,n){
				if($.inArray(i.name,colname) !== -1){
					
					i.editable=true;
				}
			});
			grid.setGridParam({colModel:colModel},true).trigger("reloadGrid")
		}else{
			console.error("请输入列名");
		}
		
	}
	
	//设置表头
	this.setGroupHeaders = function(groupHeaders){
		var headers ={
		    useColSpanStyle: true, 
		    groupHeaders:[]  
		  }
		
		headers.groupHeaders = groupHeaders || [];
		
		grid.setGroupHeaders(headers)
		
	}
	
	//消除表头
	this.destroyGroupHeader = function(){
		grid.destroyGroupHeader();
	}
	
	//重新加载
	this.reloadGrid = function (){
		grid.trigger("reloadGrid");
	}
	
	//设置多选或者单选
	this.multiselect = function (bool){
		$.jgrid.gridUnload(dataGridId)
		gridParams.multiselect = bool;
		grid=$("#" + dataGridId).jqGrid(gridParams);
	}
	
	//重新设置参数
	this.setGridParam = function (param){
		$.jgrid.gridUnload(dataGridId)
		$.extend(gridParams,param);
		grid=$("#" + dataGridId).jqGrid(gridParams);
	},
	//禁用行编辑
	this.disEditRow = function (rowIds){
		if(!$.isArray(rowIds)){rowIds=[rowIds.toString()];}
		
		for(var i in rowIds){
			if(typeof rowIds[i] !== 'string'){
				rowIds[i] = rowIds[i].toString();
			}
		}
		disEditRowIds = rowIds;
	},
	//启动行编辑
	this.enEditRow = function (rowIds){
		
		if($.isArray(rowIds)){
			
			for(var i in rowIds){
				if(typeof rowIds[i] !== 'string'){
					rowIds[i] = rowIds[i].toString();
				}
			}
			
			for(var i in disEditRowIds){
				if($.inArray(disEditRowIds[i],rowIds) != -1)
					delete disEditRowIds[i];
			}
		}else{
			rowIds=rowIds.toString();
			
			for(var i in disEditRowIds){
				if(disEditRowIds[i] == rowIds)
					delete disEditRowIds[i]
			}
		}
		
	},
	
	
	init();// 初始化

};