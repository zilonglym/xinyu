<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>新建仓库调整单</title>
	<script>
	var ctx="${ctx}";
	
	</script>
</head>
<body>
	<div id="order">
		<table cellpadding="5">
				<tr>
	    			<td>商家:</td>
	    			<td>
	    				<select id="user" class="easyui-combobox" name="user" style="width:170px;">  
	    					<option value="">请选择商家</option>
	    					<#list users as user>
	    						<option value="${user.id}">${user.subscriberName}</option>
	    					</#list>
						</select>  
	    			</td>
	    			<td>商品名称:</td>
	    			<td><input class="easyui-textbox" type="text" id="itemName" name="itemName" style="width:170px;"/></td>
	    		</tr>
	    		<tr>
	    			<td>条形码:</td>
	    			<td><input class="easyui-textbox" type="text" id="barCode" name="barCode" style="width:170px;"/></td>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" type="text" id="remark" name="remark" style="width:170px;"/></td>
	    		</tr>
	    		<tr>
	    			<td>是否启用:</td>
	    			<td>
	    				<select id="state" class="easyui-combobox" name="state" style="width:170px;">  
	    					<option value="true" selected>启用</option>
	    					<option value="false">禁用</option>
						</select>  
	    			</td>
	    			<td>优先级:</td>
	    			<td>
	    				<select id="priority" class="easyui-combobox" name="priority" style="width:170px;">  
	    					<option value="1" selected>1</option>
	    					<option value="2">2</option>
						</select>  
	    			</td>
	    		</tr>
	   	 	</table>
	   	 	<input type="hidden" id="id" value=""/>
		</div>
		<div id="items">
			<div style="height:auto;padding:5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="batchAppend();">添加商品</a>
			</div>
			<table cellpadding="5" id="tb_itemDetail">
				<thread>
					
				</thread>
			</table>
			<div id="item"></div>
		</div>		    
	</body>
	<script type="text/javascript" src="${base}/scripts/system/admin/group.js"></script>
</html>
