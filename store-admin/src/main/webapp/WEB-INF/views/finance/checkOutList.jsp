<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>扫描出库表</title>
	<script src="${ctx}/static/scripts/checkout.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-bottom:5px;margin-top:5px;">
			<select id="userId" class="easyui-combobox" name="userId">
				<option value="">全部</option>
				<c:forEach items="${users}" var="user">
					<c:if test="${user.shopName!=null and user.shopName!=''}">
						<option value="${user.id}">${user.shopName}</option>
					</c:if>
				</c:forEach>	
			</select>
			<select id="sysId" class="easyui-combobox" name="sysId">
				<option value="">全部</option>
				<c:forEach items="${expressList}" var="express">
					<option value="${express.id}">${express.description}</option>
				</c:forEach>	
			</select>
			<select id="status" class="easyui-combobox" name="status">
				<option value="">全部</option>
				<option value="SUCCESS">成功</option>
				<option value="FAIL">失败</option>
			</select>
			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'"/>  
			<input id="q" class="easyui-textbox" name="q"  data-options="prompt:'请输入物流单号/条形码/商品名称'"></input>
			<a href="javascript:checkout.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onClick="exportData()">出库导出</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onClick="exportNotData()">未出库导出</a>
			<a href="javascript:void(0);" onclick="buildCheckOut();" class="easyui-linkbutton">去除重复</a>
	</div>
	<div data-options="title:'扫描出库表'" style="padding:2px;">
		<table id="tb_checkout"  >
			<thead>
				<tr>
					
				</tr>
			</thead>
		</table>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	function exportData(){
		var beigainTime=$("#beigainTime").datetimebox('getValue');
		var lastTime=$("#lastTime").datetimebox('getValue');
		var userId=$("#userId").combobox('getValue');
		var sysId=$("#sysId").combobox('getValue');
		var q=$("#q").textbox('getValue');
		var status=$("#status").combobox('getValue');
		window.location=ctx+"/report/checkout/xls?startDate="+beigainTime+"&endDate="+lastTime+"&sysId="+sysId+"&userId="+userId+"&q="+q+"&status="+status;
	}
	function exportNotData(){
		var beigainTime=$("#beigainTime").datetimebox('getValue');
		var lastTime=$("#lastTime").datetimebox('getValue');
		window.location=ctx+"/report/checkout/sum/xls?startDate="+beigainTime+"&endDate="+lastTime;
	}
	function buildCheckOut(){
		var beginTime=$("#beigainTime").datetimebox('getValue');
		var lastTime=$("#lastTime").datetimebox('getValue');
		if(beginTime=='' || lastTime==''){
			alert("请选择时间段!");
			return;
		}
		$.post(ctx+"/checkout/build",{startDate:beginTime,endDate:lastTime},function(data){
			if(data && data.ret==1){
				alert("成功去除"+data.result+"条重复记录!");
			}
		})
	}
	</script>
</body>
</html>
