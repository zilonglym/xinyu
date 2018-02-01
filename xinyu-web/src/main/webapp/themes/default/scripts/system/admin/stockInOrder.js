
$(document).ready(function(){
	stockInOrder.initTable();
});

var stockInOrder={};
stockInOrder.initTable=function(){
	$('#tb_stockInOrder').datagrid({
		url:ctx+'/inOrderConfirm/stockInOrderListData',
	    height:650,
	    singleSelect:true,
	    rownumbers:true,
	    queryParams:{
	    	userId:$("#userId").combobox('getValue'),
	    	searchText:$("#searchText").textbox('getValue'),
	    	orderType:$("#orderType").combobox('getValue'),
	    	orderStatus:$("#orderStatus").combobox('getValue')
	    },
	    columns:[[
	        {field:'id',checkbox:true},
	        {field:'source',hidden:true},
	        {field:'orderCreateTime',title:'创建日期',width:130},
	        {field:'userName',title:'商铺名称',width:150},
	        {field:'orderCode',title:'菜鸟单号',width:150},
	        {field:'erpOrderCode',title:'ERP入库单号',width:150},
	        {field:'orderType',title:'单据类型',width:75},
	        {field:'returnReason',title:'入库原因',width:150},
	        {field:'status',title:'单据状态',width:100,formatter:function(value,row,index){
	        	if(value=='SAVE'){
	        		return "等确认中";
	        	}else if(value=='WMS_CONFIRMWAITING'){
	        		return "等待入库中";
	        	}else if(value=='WMS_CONFIRMING'){
	        		return "批次上传中";
	        	}else if(value=='WMS_CONFIRM_FINISH'){
	        		return "上传菜鸟成功";
	        	}else if(value=='CANCEL'){
	        		return "作废";
	        	}
	        }},
	        {field:'remark',title:'备注信息',width:150},
	        {field:'operator',title:'操作',width:95,formatter:function(value,row,index){
	        	return "<input  type='button' value='接收' onclick=\"updateStatus(\'"+row.id+"\',\'WMS_ACCEPT\')\" /><input  type='button' value='拒收' onclick=\"updateStatus(\'"+row.id+"\',\'WMS_REJECT\')\" />";
	        }} ,
	        {field:'operator1',title:'操作',width:80,formatter:function(value,row,index){
	        	return "<input  type='button' value='确认入库' onclick=\"selectRow(\'"+row.id+"\')\" />";
	        }}
	    ]],
	    onClickRow:function(rowIndex,rowData){
	    	
        },
	    pagination:true
	});
	
	var pagination =$('#tb_stockInOrder').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function initItem(id){ 	
	var editRow = undefined;	
	$('#tb_stockInOrderItem').datagrid({
	    url:ctx+'/inOrderConfirm/stockInOrderItemListData',
	    height:150,
	    queryParams:{
	    	id:id
	    },
	    columns:[[
	        {field:'orderItemId',title:'',hidden:'true'},
	        {field:'itemId',title:'',hidden:'true'},
	        {field:'itemName',title:'商品名称',width:100},
	        {field:'itemCode',title:'商品编码',width:100},
	        {field:'itemSku',title:'商品属性',width:80},
	        {field:'barCode',title:'商品条码',width:80},
	        {field:'type',title:'商品类型',width:80},
	        {field:'num',title:'数量',width:100}
	    ]]
	});
} 

function initConfirm(id){ 	
	$('#tb_confirms').datagrid({
	    url:ctx+'/inOrderConfirm/confirmDateListByOrderId',
	    height:150,
	    queryParams:{
	    	id:id
	    },
	    columns:[[
	       {field:'bizCode',title:'批次码',width:100},
	        {field:'type',title:'提交类型',width:100,formatter:function(value,row,index){
	        	if(value==1){
	        		return "部分提交";
	        	}else if(value==0){
	        		return "全部提交";
	        	}
	        }},
	        {field:'date',title:'时间',width:100},
	        {field:'itemName',title:'商品名称',width:100},
	        {field:'num',title:'正品数量',width:100},
	        {field:'num1',title:'残次数量',width:100}
	    ]]
	});
} 


stockInOrder.search=function(){
	$('#tb_stockInOrder').datagrid('load', {
		userId:$("#userId").combobox('getValue'),
    	searchText:$("#searchText").textbox('getValue'),
    	orderType:$("#orderType").combobox('getValue'),
    	orderStatus:$("#orderStatus").combobox('getValue')
	});
}


function updateStatus(id,status){
	$.messager.confirm('提示信息', '是否继续?', function() {
		$("#tb_stockInOrder").datagrid("loading", "请耐心等待，数据正在处理过程中……");
		$.post(ctx + "/inOrderConfirm/statusUpload", {
			id:id,
			status:status
		}, function(data) {
			$.messager.alert('提示', data.msg);
			$('#tb_stockInOrder').datagrid('load', {
				userId:$("#userId").combobox('getValue'),
		    	searchText:$("#searchText").textbox('getValue'),
		    	orderType:$("#orderType").combobox('getValue'),
		    	orderStatus:$("#orderStatus").combobox('getValue')
			});
		});	
	});
}


function selectRow(id){
		$('#dialog').dialog({
		    title: '出库单明细',
		    width: 900,
		    height: 550,
		    href: ctx+'/inOrderConfirm/f7StockInOrderItemList?id='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    onLoad:function() {
		    	initOrderItem(id);
		    	initConfirm(id);
	        },
		    buttons: [{
	                    text:'确认入库单完成',
	                    iconCls:'icon-ok',
	                    handler:function(){ 
	                    	var  trs   = $("#tb_orderItem").datagrid("getRows");
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
	                    text:'入库单部分确认',
	                    iconCls:'icon-ok',
	                    handler:function(){ 
	                    	var  trs   = $("#tb_orderItem").datagrid("getRows");
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
	                    text:'终结单据状态',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                       var  trs   = $("#tb_orderItem").datagrid("getRows");
	                    	if(trs.length>0){
	                    		$.messager.progress({
	                    		    title: '请稍等',
	                    		    msg: '数据处理中，请稍等...',
	                    		    text: '正在处理.......'
	                    		});
	                    		finalConfirm(id,trs);
	                    		$('#dialog').window('close');
	                    	}else{
	                    		$.messager.alert('提示','入库单异常,无法检测到详细信息.');
	                    	}
	                    }
	                }]
		});
	
}

function initOrderItem(id){ 
	
	var editRow = undefined;
	
	$('#tb_orderItem').datagrid({
	    url:ctx+'/inOrderConfirm/stockInOrderItemListData',
	    height:130,
	    queryParams:{
	    	id:id
	    },
	    onLoadSuccess:function(){
	    		//设置显示可编辑字段
	        	var rows  =$('#tb_orderItem').datagrid("getRows");
		    	$.each(rows,function(i,n){
	                  $('#tb_orderItem').treegrid('beginEdit', i);
	                  $('#cancelBtn'+i).hide();
	            })
	    },
	    columns:[[
	        {field:'orderItemId',title:'',hidden:'true'},
	        {field:'itemId',title:'',hidden:'true'},
	        {field:'itemName',title:'商品名称',width:100},
	        {field:'itemCode',title:'商品编码',width:100},
	        {field:'itemSku',title:'商品属性',width:80},
	        {field:'barCode',title:'商品条码',width:80},
	        {field:'type',title:'商品类型',width:80},
	        {field:'num',title:'数量',width:100},
	        {field:'num1',title:'正品数量',width:100,editor:{type:'numberbox',options:{precision:0}}},
	        {field:'num2',title:'残次品数量',width:100,editor:{type:'numberbox',options:{precision:0}}},      
	        {field:'operator',title:'操作',width:100,formatter:function(value,row,index){
	        	return "<input id='confirmBtn"+index+"'  type='button' value='确认数量' onclick=\"confirmData("+index+")\" /><input id='cancelBtn"+index+"' type='button' value='取消' onclick=\"cancelData("+index+")\" />";
	        }}      
	           
	    ]]
	});
} 

function confirmData(obj){
	var rowSelect   =$('#tb_orderItem').datagrid("getRows",obj);
	if(rowSelect!=""){
		 $('#tb_orderItem').treegrid('endEdit', obj);
		 $('#confirmBtn'+obj).hide();
		
	}
}

function cancelData(obj){
	var rowSelect   =$('#tb_orderItem').datagrid("getRows",obj);
	if(rowSelect!=""){
		 $('#tb_orderItem').treegrid('beginEdit', obj);
		 $('#confirmBtn'+obj).show();
		 $('#cancelBtn'+obj).hide();
	}
}

function  finalConfirm(id,obj){
	var dateStr=JSON.stringify(obj); 
	$.messager.confirm('确定入库上传菜鸟', '入库单检测【0】库存终结单据状态?此入库以后将不允许操作', function(r) {
		if (r) {
			$.post(ctx + "/inOrderConfirm/finalConfirm", {
				id:id,
				data :dateStr
			}, function(data) {
				$.messager.progress('close');
				$.messager.alert('提示', data.msg);
			});
		}
	});
	
}

function comfirm(id,obj,type){
	var dateStr=JSON.stringify(obj); 
	$.messager.confirm('确定入库上传菜鸟', '此处将直接修改库存，是否继续?', function(r) {
		if (r) {
			$.post(ctx + "/inOrderConfirm/saveStockInOrderConfirm", {
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

stockInOrder.batchCancel = function (){
	var rows = $("#tb_stockInOrder").datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('入库单','请选择要拒收的入库单!!');
		return;
	}
	var ids="";
	for(var i=0;i<rows.length;i++){
		var obj=rows[i];
		ids+=obj.id+",";
	}
	
	$.messager.confirm('提示信息', '是否继续?', function() {
		$("#tb_stockInOrder").datagrid("loading", "请耐心等待，数据正在处理过程中……");
		$.post(ctx + "/inOrderConfirm/statusUploadBatch", {
			ids:ids
		}, function(data) {
			$.messager.alert('提示', data.msg);
			$('#tb_stockInOrder').datagrid('load', {
				userId:$("#userId").combobox('getValue'),
		    	searchText:$("#searchText").textbox('getValue'),
		    	orderType:$("#orderType").combobox('getValue'),
		    	orderStatus:$("#orderStatus").combobox('getValue')
			});
		});	
	});

}


