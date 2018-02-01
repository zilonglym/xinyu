<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>新建仓库盘点单</title>
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
	    			<td>盘点单号:</td>
	    			<td><input id="checkOrderCode" name="checkOrderCode" class="easyui-textbox" type="text" style="width:220px;"/></td>
	    		</tr>
	    		<tr>
	    			<td>外部编码:</td>
	    			<td><input class="easyui-textbox" type="text" id="outBizCode" name="outBizCode" style="width:220px;"  placeholder="不得少于9个字符长度"/></td> 				    				    			
	    		</tr>
	    		<tr>
	    			<td>单据类型:</td>
	    			<td>
	    				<select id="orderTypes" class="easyui-combobox" name="orderTypes" style="width:220px;">   
	    					<#list types as type>
	    						<option value="${type.key}">${type.description}</option>
	    					</#list>
						</select>  
	    			</td>
	    			<td>差异原因:</td>
	    			<td>
	    				<select id="reason" class="easyui-combobox" name="reason" style="width:220px;">   
	    					<#list reasons as reason>
	    						<option value="${reason.key}">${reason.description}</option>
	    					</#list>
						</select>  
	    			</td>
	    		</tr>
	    		<tr>	    			
	    			<td>责任编码:</td>
	    			<td><input class="easyui-textbox" type="text" id="responsibilityCode" name="responsibilityCode" style="width:220px;"/></td>
	    			<td>调整主单:</td>
	    			<td><input class="easyui-textbox" type="text" id="adjustBizKey" name="adjustBizKey" style="width:220px;"/></td>
	    		</tr>
	    		<tr>	
	    			<td>备注:</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="remark" name="remark" type="text" class="easyui-textbox" style="width:220px;"/>
	    			</td>
	    		</tr>
	   	 	</table>
	   	 	<input type="hidden" id="id" value=""/>
		</div>
		<div id="items">
			<div style="height:auto;padding:5px;">			
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="batchAppend();">添加商品</a>
			</div>
			<table cellpadding="5" id="tb_order">
				<thread>
					
				</thread>
			</table>
			<div id="item"></div>
		</div>		    
	</body>
	<script type="text/javascript" src="${base}/scripts/system/admin/inventoryCount.js"></script>
</html>
