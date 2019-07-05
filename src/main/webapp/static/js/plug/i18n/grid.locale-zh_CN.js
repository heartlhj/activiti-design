/**
 * jqGrid English Translation
 * Tony Tomov tony@trirand.com
 * http://trirand.com/blog/ 
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
**/
/*global jQuery, define */
(function( factory ) {
	"use strict";
	if ( typeof define === "function" && define.amd ) {
		// AMD. Register as an anonymous module.
		define([
			"jquery",
			"../grid.base"
		], factory );
	} else {
		// Browser globals
		factory( jQuery );
	}
}(function( $ ) {

$.jgrid = $.jgrid || {};
if(!$.jgrid.hasOwnProperty("regional")) {
	$.jgrid.regional = [];
}
$.jgrid.regional["zh_CN"] = {
	defaults : {
		recordtext: "  页  共  {2}  条",
		emptyrecords: "没有找到记录",
		loadtext: "加载中...",
		pgtext : "第 {0} 页   共  {1}",
		notext: "第  0  页   共  0  页  共  0  条"
	},
	search : {
		caption: "查找...",
		Find: "确定",
		Reset: "重置",
		odata : ['=', '<>', '<', '<=','>','>=', '以XX开始','不以XX开始','is in','is not in','以XX结尾','不以XX结尾','包含','不包含'],
		groupOps: [	{ op: "并且", text: "all" },	{ op: "或者",  text: "any" }	],
		matchText: " 匹配",
		rulesText: " 规则"
	},
	edit : {
		addCaption: "添加记录",
		editCaption: "编辑记录",
		bSubmit: "提交",
		bCancel: "取消",
		bClose: "关闭",
		saveData: "数据已经修改,要保存修改吗?",
		bYes : "是",
		bNo : "否",
		bExit : "取消",
		msg: {
			required:"必填项",
			number:"必须是数字",
			minValue:"请填大于或等于 ",
			maxValue:"请填小于或等于 ",
			email: "e-mail格式不正确",
			integer: "请输入整数",
			date: "日期格式错误",
			url: "URL 格式错误 ('http://' 或者 'https://' 开头)"
		}
	},
	view : {
		caption: "显示",
		bClose: "关闭"
	},
	del : {
		caption: "删除",
		msg: "删除选中行?",
		bSubmit: "删除",
		bCancel: "取消"
	},
	nav : {
		edittext: "",
		edittitle: "编辑选中行",
		addtext:"",
		addtitle: "添加新行",
		deltext: "",
		deltitle: "删除选中行",
		searchtext: "",
		searchtitle: "查找",
		refreshtext: "",
		refreshtitle: "刷新",
		alertcap: "警告",
		alerttext: "请选择行",
		viewtext: "",
		viewtitle: "查看选定的行"
	},
	col : {
		caption: "显示/隐藏列",
		bSubmit: "提交",
		bCancel: "取消"
	},
	errors : {
		errcap : "错误",
		nourl : "没有设置url",
		norecords: "没有记录",
		model : "加载数据出错"
	},
	formatter : {
		integer : {thousandsSeparator: " ", defaultValue: '0'},
		number : {decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'},
		currency : {decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, prefix: "", suffix:"", defaultValue: '0.00'},
		date : {
			dayNames:   [
				"日", "一", "二", "三", "四", "五", "六",
				"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",
			],
			monthNames: [
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
				"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"
			],
			AmPm : ["am","pm","AM","PM"],
			S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th'},
			srcformat: 'Y-m-d',
			newformat: 'd/m/Y',
			masks : {
				ISO8601Long:"Y-m-d H:i:s",
				ISO8601Short:"Y-m-d",
				ShortDate: "n/j/Y",
				LongDate: "l, F d, Y",
				FullDateTime: "l, F d, Y g:i:s A",
				MonthDay: "F d",
				ShortTime: "g:i A",
				LongTime: "g:i:s A",
				SortableDateTime: "Y-m-d\\TH:i:s",
				UniversalSortableDateTime: "Y-m-d H:i:sO",
				YearMonth: "F, Y"
			},
			reformatAfterEdit : false
		},
		baseLinkUrl: '',
		showAction: '',
		target: '',
		checkbox : {disabled:true},
		idName : 'id'
	}
};
}));
