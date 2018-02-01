var inventoryCount={};

$(document).ready(function(){
	inventoryCount.initTable();
});

inventoryCount.initTable = function(){
	$('#tb_inventoryCount').datagrid({
	    url:ctx+'/inventoryCount/listData',
	    height:750,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	        {field:'id',title:'',hidden:true},
	        {field:'operateTime',title:'盘点时间',width:150},
	        {field:'user',title:'商家',width:150},
	        {field:'checkOrderCode',title:'盘点单号',width:260},
	        {field:'orderType',title:'订单类型',width:120},
	        {field:'adjustReasonType',title:'差异原因',width:120},
	        {field:'status',title:'单据状态',width:120,formatter:function(value,row,index){
	        	if(value=='SAVE'){
	        		return "本地保存";
	        	}else if(value=='WMS_UP_FAIL'){
	        		return "上传菜鸟失败";
	        	}else if(value=='WMS_UP_FINISH'){
	        		return "上传菜鸟成功";
	        	}else if(value=='CANCEL'){
	        		return "作废";
	        	}
	        }},
	        {field:'remark',title:'备注',width:120},
//	        {field:'operator',title:'操作',width:120,formatter:function(value,row,index){
//	        	return "<a href='javascript:void(0);' onclick=\"javascript:inventoryCount.editCount(\'"+row.id+"'\);\">修改</a>"
//	        }},
	        {field:'operator1',title:'操作',width:120,formatter:function(value,row,index){
	        	return "<input  type='button' value='上传菜鸟' onclick=\"inventoryCount.upInventoryCount(\'"+row.id+"\')\" />";
	        }},
	        {field:'operator2',title:'操作',width:180,formatter:function(value,row,index){
	        	return "<input  type='button' value='上传菜鸟' onclick=\"selectRow(\'"+row.id+"\')\" />"+"<input  type='button' value='取消' onclick=\"updateStatus(\'"+row.id+"\')\" />";
	        }}
	    ]],
	    pagination:true
	});
	
	var pagination = $('#tb_inventoryCount').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

function selectRow(id){
	$('#dialog').dialog({
	    title: '盘点单明细',
	    width: 650,
	    height: 550,
	    href: ctx+'/inventoryCount/f7Edit?id='+id,
	    closed: false,
	    cache: false,
	    modal: true,
	    onLoad:function() {
	    	initOrderItem(id);
        },
	    buttons: [{
                    text:'全部确认上传',
                    iconCls:'icon-ok',
                    handler:function(){ 
                    	var  trs   = $("#tb_order").datagrid("getRows");
                    	if(trs.length>0){
                    		$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
                    		comfirm(id,trs,0);
                    		$('#dialog').window('close');
                    	}else{
                    		$.messager.alert('提示','入库单异常,无法检测到详细信息.');
                    	}
                    }
                },{
                    text:'盘点单部分确认',
                    iconCls:'icon-ok',
                    handler:function(){ 
                    	var  trs   = $("#tb_order").datagrid("getRows");
                    	if(trs.length>0){
                    		$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
                    		comfirm(id,trs,1);
                    		$('#dialog').window('close');
                    	}else{
                    		$.messager.alert('提示','入库单异常,无法检测到详细信息.');
                    	}
                    	                 	                    	 
                    }
                },
                
                {
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                    }
                }]
	});

}

inventoryCount.search = function(){
	$('#tb_inventoryCount').datagrid('load', {
  		 userId:$("#userId").combobox('getValue'),
  		 orderType:$("#orderType").combobox('getValue'),
  		 status:$("#status").combobox('getValue'),
  		 searchText:$("#searchText").textbox('getValue')
	});
}


inventoryCount.upInventoryCount = function(id){
	$.messager.confirm('提示信息', '是否继续?', function(r) {
		if (r) {
			$.post(ctx + "/inventoryCount/upInventoryCount", {
				id:id
			}, function(data) {
				//$.messager.alert('提示', data.msg);
			});
		}
	});
	
}

inventoryCount.editCount = function(id){
	if(id){
		$('#dialog').dialog({
			title: '修改',
		    width: 650,
		    height: 800,
		    closed: false,
		    cache: false,
		    href: ctx+'/inventoryCount/f7Edit?id='+id,
		    modal: true,
		    onLoad:function() {
		    	initOrderItem(id);
	        },
		    buttons: [{
	            text:'保存',
	            iconCls:'icon-ok',
	            handler:function(){ 
	            	json="{";
	            	json=json+"id:'"+$("#id").val()+"',";
	            	json=json+"userId:'"+$("#userId").combobox("getValue")+"',";
	            	json=json+"checkOrderCode:'"+$("#checkOrderCode").textbox("getValue")+"',";
	            	json=json+"outBizCode:'"+$("#outBizCode").textbox("getValue")+"',";
	            	json=json+"orderType:'"+$("#orderType").combobox("getValue")+"',";
	            	json=json+"reason:'"+$("#reason").combobox("getValue")+"',";
	            	json=json+"responsibilityCode:'"+$("#responsibilityCode").textbox("getValue")+"',";
	            	json=json+"adjustBizKey:'"+$("#adjustBizKey").textbox("getValue")+"',";
	            	json=json+"remark:'"+$("#remark").textbox("getValue")+"'}";
	            	var rows  =$('#tb_order').datagrid("getRows");
	            	var dateStr = JSON.stringify(rows); 
	            	$.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
	            	$.post(ctx+"/inventoryCount/save",{json:json,rows:dateStr},function(data){
	            		$.messager.progress('close');
	            		if(data.ret=="success"){
	            			
	           			 $.messager.alert("成功","设置成功！！！！！");
	           			 $('#dialog').window('close');
	           			 $('#tb_inventoryCount').datagrid('load',{
	               			 name:null
	               		 });	 
	               	 }else{
	               		
	               		 $.messager.alert("失败","新建失败！！！！！");
	               	 	}
	            	});
	            }
	        },{
	            text:'确认',
	            iconCls:'icon-ok',
	            handler:function(){
	               $('#dialog').window('close');
	               $('#tb_inventoryCount').datagrid('load',{
	      			 name:null
	      		 });
	            }
	        },{
	            text:'取消',
	            handler:function(){
	               $('#dialog').window('close');
	               $('#tb_inventoryCount').datagrid('load',{
	            	   name:null
	             });
	           }
	        }]
		});
	}else{
		$.messager.alert("失败","请勾选需操作的信息！！！");
	}	
}



inventoryCount.add = function(){
	$('#dialog').dialog({
		title: '新建盘点单',
	    width: 650,
	    height: 800,
	    closed: false,
	    cache: false,
	    href: ctx+'/inventoryCount/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){ 
                    	json="{";
                    	json=json+"id:'"+$("#id").val()+"',";
                    	json=json+"userId:'"+$("#user").combobox("getValue")+"',";
                    	json=json+"checkOrderCode:'"+$("#checkOrderCode").textbox("getValue")+"',";
                    	json=json+"outBizCode:'"+$("#outBizCode").textbox("getValue")+"',";
                    	json=json+"orderType:'"+$("#orderTypes").combobox("getValue")+"',";
                    	json=json+"reason:'"+$("#reason").combobox("getValue")+"',";
                    	json=json+"responsibilityCode:'"+$("#responsibilityCode").textbox("getValue")+"',";
                    	json=json+"adjustBizKey:'"+$("#adjustBizKey").textbox("getValue")+"',";
                    	json=json+"remark:'"+$("#remark").textbox("getValue")+"'}";
                    	var rows  =$('#tb_order').datagrid("getRows");
                    	var dateStr = JSON.stringify(rows); 
                    	$.messager.progress({
                		    title: '请稍等',
                		    msg: '数据处理中，请稍等...',
                		    text: '正在处理.......'
                		});
                    	$.post(ctx+"/inventoryCount/save",{json:json,rows:dateStr},function(data){
                    		$.messager.progress('close');
                    		if(data.ret=="success"){
                    			
                   			 $.messager.alert("成功","设置成功！！！！！");
                   			 $('#dialog').window('close');
                   			 $('#tb_inventoryCount').datagrid('load',{
                       			 name:null
                       		 });	 
                       	 }else{
                       		
                       		 $.messager.alert("失败","新建失败！！！！！");
                       	 	}
                    	});
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_inventoryCount').datagrid('load',{
              			 name:null
              		 });
                    }
              }]
	});
}


//确认上传
function comfirm(id,obj,type){
	var dateStr=JSON.stringify(obj); 
	$.messager.confirm('确定上传菜鸟', '此处将直接修改库存，是否继续?', function(r) {
		if (r) {
			$.post(ctx + "/inventoryCount/upInventoryCount", {
				id:id,
				type:type,
				data :dateStr
			}, function(data) {
				$.messager.progress('close');
				$.messager.alert('提示', data.msg);
			});
		}
	});
}

function updateStatus(id){
	$.post(ctx +"/inventoryCount/updateStatus", {id:id}, function(data) {
		if(data.ret=="success"){
			$.messager.alert('成功', "取消成功！");
		}else{
			$.messager.alert('错误', data.msg);
		}
	});
}

function initOrderItem(orderId){ 
	var editRow = undefined;
	$('#tb_order').datagrid({
	    url:ctx+'/inventoryCount/inventoryCountItem/listData',
	    height:550,
	    queryParams:{
	    	orderId:orderId
	    },
	    onLoadSuccess:function(){
    		//设置显示可编辑字段
        	var rows  =$('#tb_order').datagrid("getRows");
	    	$.each(rows,function(i,n){
                  $('#tb_order').treegrid('beginEdit', i);
                  $('#cancelBtn'+i).hide();
            })
	    },
	    columns:[[
	    	{field:'id',hidden:true},
	    	{field:'itemKey',title:'itemKey',hidden:true},
	        {field:'itemName',title:'商品名称',width:200},
	        {field:'sku',title:'商品属性',width:100},
	        {field:'num1',title:'正品',width:70,editor:{type:'numberbox',options:{precision:0}}},
	        {field:'num2',title:'残次品',width:70,editor:{type:'numberbox',options:{precision:0}}},      
	        {field:'num3',title:'机损',width:70,editor:{type:'numberbox',options:{precision:0}}},      
	        {field:'num4',title:'箱损',width:70,editor:{type:'numberbox',options:{precision:0}}},      
	        {field:'operator',title:'操作',width:90,formatter:function(value,row,index){
	        	return "<input id='confirmBtn"+index+"'  type='button' value='确认数量' onclick=\"confirmData("+index+")\" /><input id='cancelBtn"+index+"' type='button' value='取消' onclick=\"cancelData("+index+")\" />";
	        }}      
	    ]]
	});
}	

function confirmData(obj){
	var rowSelect   =$('#tb_order').datagrid("getRows",obj);
	if(rowSelect!=""){
		 $('#tb_order').treegrid('endEdit', obj);
		 $('#confirmBtn'+obj).hide();
		
	}
}

function cancelData(obj){
	var rowSelect   =$('#tb_order').datagrid("getRows",obj);
	if(rowSelect!=""){
		 $('#tb_order').treegrid('beginEdit', obj);
		 $('#confirmBtn'+obj).show();
		 $('#cancelBtn'+obj).hide();
	}
}


function batchAppend(){
	var userId = $('#user').combobox('getValue');
	$('#item').dialog({
		title:'商品列表',
		width:500,
		height:650,
		closed:false,
		cache:false,
		href:ctx+'/inventoryCount/item/list',
		modal:true,
		queryParams:{
		   userId:userId
		},
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$('#item').dialog('close');
				var rows = $('#tb_inventoryItem').datagrid('getChecked');
				var dateStr = JSON.stringify(rows);
				initNewAddTable(dateStr);
			}
		},{
            text:'取消',
            handler:function(){
               $('#item').window('close');
           }
      }]
	});
}

function initNewAddTable(dataStr){ 
	var url = ctx+'/inventoryCount/newItemList/listData';
	var editRow = undefined;
	$('#tb_order').datagrid({
	    url:url,
	    height:600,
	    rownumbers:true,
	    queryParams:{
	    	dataStr:dataStr
	    },
	    columns:[[
	              {field:'itemId',hidden:true},
	              {field:'itemName',title:'商品名称',width:100},
	              {field:'color',title:'商品属性',width:70},
	              {field:'barCode',title:'条码',width:70},
	              {field:'num11',title:'正品库存',width:70},
	              {field:'num1',title:'正品实库',width:70,editor:{type:'numberbox',options:{precision:0}}},   
	              {field:'num12',title:'残品库存',width:70},         
	              {field:'num2',title:'残品实库',width:70,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_order').datagrid('selectRow', index).datagrid('editCell', {
	    		            index : index,
	    		            field : field
	    		        });
	    		        editIndex = index;
	    		    }
	    	 	}
		});
	
}

$.extend($.fn.datagrid.methods, {
    editCell : function(jq, param) {
        return jq.each(function() {
            var opts = $('#tb_order').datagrid('options');
            var fields = $('#tb_order').datagrid('getColumnFields', true).concat(
                    $('#tb_order').datagrid('getColumnFields'));
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_order').datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            $('#tb_order').datagrid('beginEdit', param.index);
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_order').datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

var editIndex = undefined;
//结束编辑 
function endEditing() {
  if (editIndex == undefined) {
      return true
  }
  if ($('#tb_order').datagrid('validateRow', editIndex)) {
      $('#tb_order').datagrid('endEdit', editIndex);
      editIndex = undefined;
      return true;
  } else {
      return false;
  }
}
