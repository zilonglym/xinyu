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
	    				<select id="userId" class="easyui-combobox" name="userId" style="width:220px;"> 
	    					<#list users as user>
	    						<option value="${user.id}" <#if inventoryCount.user.id==user.id>selected</#if>>${user.subscriberName}</option>
	    					</#list>	    					
						</select>  
	    			</td>
	    			<td>盘点单号:</td>
	    			<td><input id="checkOrderCode" name="checkOrderCode" class="easyui-textbox" type="text" value="${inventoryCount.checkOrderCode}" style="width:220px;"/></td>
	    		</tr>
	    		<tr>
	    			<td>外部编码:</td>
	    			<td><input class="easyui-textbox" type="text" id="outBizCode" name="outBizCode" value="${inventoryCount.outBizCode}" style="width:220px;"/></td>
	    		</tr>
	    		<tr>
	    			<td>单据类型:</td>
	    			<td>
	    				<select id="orderType" class="easyui-combobox" name="orderType" style="width:220px;">  
	    					<#list types as type>
	    						<option value="${type.key}" <#if inventoryCount.orderType.key==type.key>selected</#if>>${type.description}</option>
	    					</#list>
						</select>  
	    			</td>
	    			<td>差异原因:</td>
	    			<td>
	    				<select id="reason" class="easyui-combobox" name="reason" style="width:220px;">   
	    					<#list reasons as reason>
	    						<option value="${reason.key}" <#if inventoryCount.adjustReasonType.key==reason.key>selected</#if>>${reason.description}</option>
	    					</#list>
						</select>  
	    			</td>
	    		</tr>
	    		<tr>	    			
	    			<td>责任编码:</td>
	    			<td><input class="easyui-textbox" type="text" id="responsibilityCode" name="responsibilityCode" value="${inventoryCount.responsibilityCode}" style="width:220px;"/></td>
	    			<td>调整主单:</td>
	    			<td><input class="easyui-textbox" type="text" id="adjustBizKey" name="adjustBizKey" value="${inventoryCount.adjustBizKey}" style="width:220px;"/></td>
	    		</tr>
	    		<tr>	
	    			<td>备注:</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="remark" name="remark" type="text" class="easyui-textbox" value="${inventoryCount.remark}" style="width:220px;"/>
	    			</td>
	    		</tr>
	   	 	</table>
	   	 	<input type="hidden" id="id" value="${inventoryCount.id}"/>
		</div>
		<div id="items">
			<table cellpadding="5" id="tb_order">
				<thread>
					
				</thread>
			</table>
		</div>		    
	</body>
	
	<script type="text/javascript" src="${base}/scripts/system/admin/inventoryCount.js"></script>
</html>
