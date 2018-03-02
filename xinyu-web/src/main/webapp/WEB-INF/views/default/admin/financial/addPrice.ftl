<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>运费编辑</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>商家名称:</td>
	    			<td>
	    				<select id="user" name="user" class="easyui-combobox" style="width:220px;">
	    					<#list users as user>
								<option value="${user.id}">${user.subscriberName}</option>
							</#list>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>快递名称:</td>
	    			<td>
	    				<select id="express" name="express" class="easyui-combobox" style="width:220px;">
	    					<#list companys as company>
								<option value="${company.id}">${company.description}</option>
							</#list>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>首重重量（KG）:</td>
	    			<td><input class="easyui-textbox" type="text" name="firstCost" id="firstCost" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>首重价格（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="firstPrice" id="firstPrice" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>续重重量（KG）:</td>
	    			<td><input class="easyui-textbox" type="text" name="initialCost" id="initialCost" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>续重价格（元/KG）:</td>
	    			<td><input class="easyui-textbox" type="text" name="initialPrice" id="initialPrice" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>仓储费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="deliveryCost" id="deliveryCost" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>打包费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="otherCost" id="otherCost" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>派送费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="deliveryPrice" id="deliveryPrice" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>其他费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="otherPrice" id="otherPrice" style="width:220px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>区域信息:</td>
	    			<td><input class="easyui-textbox" type="text" name="city" id="city" data-options="multiline:true"  style="width:220px;height:80px;"></input></td>
	    		</tr>
	    </table>
	    <input type="hidden" id="id"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/price.js?v=1.01"></script>
</html>
