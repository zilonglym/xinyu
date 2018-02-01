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
	    				<select id="user" class="easyui-combobox" name="user" style="width:220px;">  
	    					<option value="">请选择商家</option>
	    					<#list users as user>
	    						<option value="${user.id}">${user.subscriberName}</option>
	    					</#list>
						</select>  
	    			</td>
	    			<td>仓库:</td>
	    			<td>
	    				<select id="centro" class="easyui-combobox" name="centro" style="width:220px;">
	    					<#list centros as centro>
	    						<option value="${centro.id}">${centro.name}</option>
	    					</#list>
	    				</select>
	    			</td>
	    		</tr>		
	    		<tr>
	    			<td>单据类型:</td>
	    			<td>
	    				<select id="orderType" class="easyui-combobox" name="orderType" style="width:220px;">
	    					<option value="${key}" selected>${description}</option>
	    				</select>
	    			</td>
	    			<td>差异原因:</td>
	    			<td>
	    				<select class="easyui-combobox" id="reasonType" name="reasonType" style="width:220px;">
	    					<#list reasonTypes as reasonType>
	    						<option value="${reasonType.key}">${reasonType.description}</option>
	    					</#list>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" type="text" id="remark" name="remark" /></td>
	    		</tr>
	   	 	</table>
		</div>
		<div id="items">
			<div style="height:auto;padding:5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="batchAppend();">添加商品</a>
			</div>
			<table cellpadding="5" id="tb_orderDetail">
				<thread>
					
				</thread>
			</table>
			<div id="item"></div>
		</div>		    
	</body>
	<script type="text/javascript" src="${base}/scripts/system/admin/inventoryAdjust.js"></script>
</html>
