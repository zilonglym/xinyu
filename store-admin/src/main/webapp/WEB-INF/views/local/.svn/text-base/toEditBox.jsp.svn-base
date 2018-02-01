<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>修改库位</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/local.js" type="text/javascript"></script>
</head>
<body>
		<div id="toolbar">
			<table cellpadding="2">
	    		<tr>
	    			<td>货架:</td>
	    			<td>
	    				<input class="easyui-textbox" style="width:50px;" type="text" name="eRow" id="eRow" disabled value="${localPlate.row}"></input>
	    				-
	    				<input class="easyui-textbox" style="width:50px;" type="text" name="eBoxOut" id="eBoxOut" disabled value="${localPlate.boxOut}"></input>
	    				-
	    				<input class="easyui-textbox" style="width:50px;" type="text" name="eFloor" id="eFloor" disabled value="${localPlate.floor}"></input>
	    				-
	    				<input class="easyui-textbox" style="width:50px;" type="text" name="eCode" id="eCode" disabled value="${localPlate.code}"></input>
	    			</td>
	    			<td> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="batchAppend();">修改商品</a></td>
	    		</tr>		
	  	  	</table>
	    	 <input type="hidden" id="id" value="${localPlate.id}"/> 	
	     </div>
	     <div id="items">
			<table cellpadding="5" id="tb_itemDetail">
				<thread>
					
				</thread>
			</table>
			<div id="item"></div>
		</div>		    
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
