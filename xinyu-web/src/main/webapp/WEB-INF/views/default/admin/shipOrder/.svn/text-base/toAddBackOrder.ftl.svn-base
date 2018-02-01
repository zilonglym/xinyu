<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>新建</title>
</head>
<body>
	<div id="order">
		<table cellpadding="5">
				<tr>
	    			<td>原始单号:</td>
	    			<td><input id="orderCode" name="orderCode" class="easyui-textbox" type="text" style="width:200px;"/></td>
	    			<td>退回单号:</td>
	    			<td><input class="easyui-textbox" type="text" id="returnCode" name="returnCode" style="width:200px;"/></td> 
	    		</tr>
	    		<tr>	
	    			<td>退回原因:</td>
	    			<td>
	    				<select id="reason" class="easyui-combobox" name="reason" style="width:200px;">   		
	    					<option value="错发">错发</option>
	    					<option value="破损">破损</option>
	    					<option value="拒收">拒收</option>
	    					<option value="拦截">拦截</option>
	    					<option value="漏发">漏发</option>
	    					<option value="其他">其他</option>
						</select>  
	    			</td>				    				    			
	    			<td>备注:</td>			
	    			<td><input class="easyui-textbox" type="text" id="description" name="description" style="width:200px;"/></td> 
	    		</tr>
	   	 	</table>
		</div>
		<div id="items">
			<div style="height:auto;padding:5px;">			
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="batchAppend();">添加商品</a>
			</div>
			<table cellpadding="5" id="tb_backOrderItem">
				<thread>
					
				</thread>
			</table>
		</div>	
		<div id="item"></div>	
		<script>
		var ctx="${ctx}";
		$('#orderCode').textbox({
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
			keyup: function(event){
				if(event.keyCode == 13) {
 					backOrder.initItems();
					}
				}
			})
		});
		</script>    
	</body>
	<script type="text/javascript" src="${base}/scripts/system/admin/backOrder.js"></script>
</html>
