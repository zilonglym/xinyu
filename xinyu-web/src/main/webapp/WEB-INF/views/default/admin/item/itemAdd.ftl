<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>新建商品</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>商家信息：</td>
	    			<td>
	    				<select id="userId" class="easyui-combobox" name="userId" style="width:220px;">   
   							<#list users as user>
   								<option value="${user.id}">${user.subscriberName}</option>
   							</#list>
						</select>  
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>商品编码:</td>
	    			<td><input class="easyui-textbox" type="text" name="itemCode" id="itemCode" data-options="required:true" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>商品名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="itemName" id="itemName" data-options="required:true" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>商品条码:</td>
	    			<td><input class="easyui-textbox" type="text" name="barCode" id="barCode"></input></td>	
	    		</tr>
	    		<tr>
	    			<td>商品规格:</td>
	    			<td><input class="easyui-textbox" type="text" name="specification" id="specification"></input></td>
	    		</tr>
	    		<tr>
	    			<td>商品颜色:</td>
	    			<td><input class="easyui-textbox" type="text" name="color" id="color"></input></td>
	    		</tr>
	    		<tr>
	    			<td>商品净重:</td>
	    			<td><input class="easyui-textbox" type="text" name="grossWeight" id="grossWeight"></input></td>
	    		</tr>
	    		<tr>
	    			<td>商品毛重:</td>
	    			<td><input class="easyui-textbox" type="text" name="wmsGrossWeight" id="wmsGrossWeight"></input></td>
	    		</tr>
	    </table>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/item.js"></script>
</html>
