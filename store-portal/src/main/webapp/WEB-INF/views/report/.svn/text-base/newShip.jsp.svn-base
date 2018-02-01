<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>发货统计</title>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>    
	  	<a href="###" onclick="javascript:ship.search()" class="easyui-linkbutton">查询</a>
	</div>
	
	<table id="tb_ship"  >
		<thead>
			<tr>
	
			</tr>
		</thead>
	</table>
	<div id="loadingDiv" class="hint hide">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>
	<script type="text/javascript">
	var ctx="${ctx}";
	$(document).ready(function(){
		ship.initTable();
	});
	
	//日期格式化输出
	Date.prototype.format = function(format) {
	    var o = {
	        "M+": this.getMonth() + 1, // month
	        "d+": this.getDate(), // day
	        "h+": this.getHours(), // hour
	        "m+": this.getMinutes(), // minute
	        "s+": this.getSeconds(), // second
	        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
	        "S": this.getMilliseconds()
	    }
	    if (/(y+)/.test(format))
	        format = format.replace(RegExp.$1, (this.getFullYear() + "")
	            .substr(4 - RegExp.$1.length));
	    for (var k in o)
	        if (new RegExp("(" + k + ")").test(format))
	            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	    return format;
	}
	function formatDatebox(value) {
		if (value == null || value == '') {
			return '';
		}
		var dt;
		if (value instanceof Date) {
			dt = value;
		} else {
			dt = new Date(value);	
		}
		return dt.format("yyyy-MM-dd hh:mm:ss"); 
	}
	
	var ship={};
	ship.initTable=function(){
		$('#tb_ship').datagrid({
		    url:ctx+'/report/ship/listData',
		    height:850,
		    singleSelect:false,
		    rownumbers:true,
		    columns:[[
		        {field:'lastUpdateDate',title:'发货时间',width:120,formatter:formatDatebox},
		        {field:'buyerNick',title:'昵称',width:120},
		        {field:'remark',title:'订单单号',width:150},
		        {field:'expressCompany',title:'物流公司',width:80},
		        {field:'expressOrderno',title:'物流单号',width:150},
		        {field:'receiverName',title:'收件人',width:80},
		        {field:'items',title:'商品',width:250}
		    ]],
		    pagination:true
		});
		
		var pagination =$('#tb_ship').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数，默认为10 
			pageList:[100,200,250,500],
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}
	
	ship.search=function(){
		$('#tb_ship').datagrid('load',{
			startDate:$('#beigainTime').datetimebox('getValue'),
			endDate:$('#lastTime').datetimebox('getValue')
		});
	}
	</script>
</body>
</html>
