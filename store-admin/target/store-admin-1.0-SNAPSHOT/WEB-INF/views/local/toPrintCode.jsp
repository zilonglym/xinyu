<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>打印条码</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/localItem.js" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>品名:</td>
	    			<td><input class="easyui-textbox" type="text" name="itemName" id="itemName"  value="${localItem.name}" disabled></input></td>
	    		</tr>
	    		<tr>
	    			<td>sku属性:</td>
	    			<td><input class="easyui-textbox" type="text" name="sku" id="sku" value="${localItem.sku}" disabled></input></td>
	    		</tr>
	    		<tr>
	    			<td>入库编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="code" id="code" data-options="required:true" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>入库数量:</td>
	    			<td><input class="easyui-textbox" type="text" name="anum" id="anum" data-options="required:true" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>数量:</td>
	    			<td>
	    				<select id="isHigh" class="easyui-combobox"  name="isHigh">
							<option value="false">标板：${localItem.num}</option> 
							<option value="true">自定义</option> 
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>自定义数量:</td>
	    			<td><input class="easyui-textbox" type="text" name="cnum" id="cnum"></input></td>
	    		</tr>
	    		<tr>
	    			<td>生产日期:</td>
	    			<td><input class="easyui-datetimebox" name="birthDate" id="birthDate" data-options="prompt:'请选择时间'"/></td>
	    		</tr>
	    </table>
	     <input type="hidden" id="id" value="${localItem.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
