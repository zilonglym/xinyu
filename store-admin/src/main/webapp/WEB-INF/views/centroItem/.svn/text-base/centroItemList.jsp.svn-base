<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<title>区域列表</title>
		<link rel="stylesheet" type="text/css" href="${base}/static/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${base}/static/jquery-easyui/themes/icon.css">
		<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/util/syUtil.js"></script>
	</head>
	<script type="text/javascript">
	var treegrid;
	$(function() {
		treegrid = $('#treegrid').treegrid({
			url :"${ctx}/centroItem/getCentroItemList",
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
				}
			},'-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {					treegrid.treegrid('load');
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					treegrid.treegrid('unselectAll');
				}
			}, '-' ],
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : false,
			nowrap : false,
			animate : false,
			border : false,
			pagination:true,//显示分页  
            pageSize:20,//分页大小  
            pageList:[5,10,15,20],//每页的个数  
			idField : 'id',
			treeField : 'name',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 150,
				hidden : true
			}, {
				field : 'name',
				title : '仓库名称',
				width : 180
			} ] ],
			columns : [ [ {
				field : 'size',
				title : '大小',
				width : 250
			},{
				field : 'address',
				title : '地址',
				width : 250
			}] ],
			onExpand : function(row) {
				treegrid.treegrid('unselectAll');
			},
			onCollapse : function(row) {
				treegrid.treegrid('unselectAll');
			}
		});
	});
	function append() {
		var p = sy.dialog({
			title : '添加区域',
			href : '${ctx}/centroItem/toAddCentroItem',
			width : 440,
			height : 210,
			buttons : [ {
				text : '确定',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url :'${ctx}/centroItem/saveCentroItem',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.json.success) {
								treegrid.treegrid('reload');
								p.dialog('close');
							}
							$.messager.show({
								msg : json.json.msg,
								title : '提示'
							});
						}
					});
				}
			} ],
		});
	}
</script>
<table id="treegrid"> </table>
</html>