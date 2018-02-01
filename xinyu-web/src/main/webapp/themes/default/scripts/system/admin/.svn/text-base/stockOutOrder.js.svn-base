$(document).ready(function(){
	stockOutOrder.initTable();
});

var stockOutOrder={};
stockOutOrder.initTable=function(){
	$('#tb_stockOutOrder').datagrid({
		url:ctx+'/outOrderConfirm/stockOutOrderList/listData',
	    height:650,
	    singleSelect:true,
	    rownumbers:true,
	    queryParams:{
	    	userId:$("#userId").combobox('getValue'),
	    	orderType:$("#orderTypes").combobox('getValue'),
	    	status:$("#status").combobox('getValue'),
	    	searchText:$("#searchText").textbox('getValue')
	    },
	    loadMsg:'数据正在加载中，请耐心等待.......',
	    columns:[[
	        {field:'ck',checkbox:true},
	        {field:'id',hidden:true},
	        {field:'orderCreateTime',title:'创建日期',width:120},
	        {field:'userName',title:'商铺名称',width:100},
	        {field:'orderCode',title:'菜鸟单号',width:150},
	        {field:'orderType',title:'单据类型',width:150},
	        {field:'sendTime',title:'出库时间',width:130},
	        {field:'mode',title:'出库方式',width:80},
	        {field:'remark',title:'备注信息',width:80},
	        {field:'status',title:'单据状态',width:100,formatter:function(value,row,index){
	        	if(value=='SAVE'){
	        		return "等待接收中";
	        	}else if(value=='WMS_CONFIRMWAITING'){
	        		return "等待出库中";
	        	}else if(value=='WMS_CONFIRMING'){
	        		return "批次上传中";
	        	}else if(value=='WMS_CONFIRM_FINISH'){
	        		return "上传菜鸟成功";
	        	}else if(value=='CANCEL'){
	        		return "已作废";
	        	}
	        }},
	        {field:'operator',title:'操作',width:200,formatter:function(value,row,index){    
	        	return "&nbsp;<input  type='button' value='接收' onclick=\"updateStatus(\'"+row.id+"\',\'WMS_ACCEPT\')\" />&nbsp;<input  type='button' value='拒收' onclick=\"updateStatus(\'"+row.id+"\',\'WMS_REJECT\')\" />&nbsp;<input  type='button' value='确认' onclick=\"selectRow(\'"+row.id+"\')\" />";	        	
	        }}
	    ]],
	    rowStyler:function(index,row){
	    	if(row.status=='CANCEL'){
	    		return 'background-color:red;';
	    	}
	    },
	    pagination:true,
	    onDblClickRow:function(rowIndex, rowData){
	    	if(rowData!=null){
	    		initStockOrderItem(rowData.id);
	    	}
	    }
	});
	
	var pagination =$('#tb_stockOutOrder').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function initStockOrderItem(id){	 
	var editRow = undefined;
	$('#tb_outOrderItem').datagrid({
	    url:ctx+'/outOrderConfirm/stockOutOrderItem/listData',
	    height:100,
	    queryParams:{
	    	id:id
	    },
	    columns:[[
	  	        {field:'orderItemId',hidden:'true'},
		        {field:'itemId',hidden:'true'},
		        {field:'itemName',title:'商品名称',width:100},
		        {field:'itemCode',title:'商品编码',width:100},
		        {field:'itemSku',title:'商品属性',width:80},
		        {field:'barCode',title:'商品条码',width:80},
		        {field:'num1',title:'正品',width:60},
		        {field:'num2',title:'残次品',width:60},      
		        {field:'num3',title:'机损',width:60},      
		        {field:'num4',title:'箱损',width:60}            
		    ]]
	});
}

stockOutOrder.search=function(){
	$('#tb_stockOutOrder').datagrid('load', {
		userId:$("#userId").combobox('getValue'),
    	orderType:$("#orderTypes").combobox('getValue'),
    	status:$("#status").combobox('getValue'),
    	searchText:$("#searchText").textbox('getValue')
	});
}

function updateStatus(id,status){
	$.messager.confirm('提示信息', '是否继续?', function() {
			//$.messager.progress({ title:'请稍后', msg:'操作正在处理中...' });
			$("#tb_stockOutOrder").datagrid("loading", "请耐心等待，数据正在处理过程中……");
			$.post(ctx + "/outOrderConfirm/statusUpload", {
				id:id,
				status:status
			}, function(data) {
				//$.messager.progress('close');
				$.messager.alert('提示', data.msg);
				$('#tb_stockOutOrder').datagrid('load',{
					userId:$("#userId").combobox('getValue'),
			    	orderType:$("#orderTypes").combobox('getValue'),
			    	status:$("#status").combobox('getValue'),
			    	searchText:$("#searchText").textbox('getValue')
				});
			});		
	});

}

function initConfirm(id){ 	
	$('#tb_confirms').datagrid({
	    url:ctx+'/outOrderConfirm/confirmDataListByOrderId',
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
	        {field:'confirmDate',title:'时间',width:100},
	        {field:'itemName',title:'商品名称',width:100},
	        {field:'num',title:'正品数量',width:100},
	        {field:'num1',title:'残次数量',width:100}
	    ]]
	});
} 

function selectRow(id){
		$('#dialog').dialog({
		    title: '出库单明细',
		    width: 650,
		    height: 700,
		    href: ctx+'/outOrderConfirm/f7StockOutOrderItemList?id='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    onLoad:function() {
		    	initOrderItem(id);
 		    	initConfirm(id);
	        },
		    buttons: [{
	                    text:'确认出库',
	                    iconCls:'icon-ok',
	                    handler:function(){ 
	                    	var  trs   = $("#tb_orderItem").datagrid("getRows");
	                    	var company = $("#company").combobox("getValue");
	                    	var orderNo = $("#orderNo").textbox("getValue");
	                    	var remark = $("#remark").textbox("getValue");
	                    	var packageLength = $("#packageLength").numberbox("getValue");
	                    	var packageWidth = $("#packageWidth").numberbox("getValue");
	                    	var packageHeight = $("#packageHeight").numberbox("getValue");
	                    	if(trs.length>0){
	                    		$.messager.progress({
	                    		    title: '请稍等',
	                    		    msg: '数据处理中，请稍等...',
	                    		    text: '正在处理.......'
	                    		});
	                    		comfirm(id,trs,0,company,orderNo,packageLength,packageWidth,packageHeight,remark);
	                    	}else{
	                    		$.messager.alert('提示','出库单异常,无法检测到详细信息.');
	                    		$('#dialog').window('close');
	                    	}    	 	                    	 
	                    }
	                },{
	                    text:'部分出库',
	                    iconCls:'icon-ok',
	                    handler:function(){ 
	                    	var  trs   = $("#tb_orderItem").datagrid("getRows");
	                    	var company = $("#company").combobox("getValue");
	                    	var remark = $("#remark").textbox("getValue");
	                    	var orderNo = $("#orderNo").textbox("getValue");
	                    	var packageLength = $("#packageLength").numberbox("getValue");
	                    	var packageWidth = $("#packageWidth").numberbox("getValue");
	                    	var packageHeight = $("#packageHeight").numberbox("getValue");
	                    	if(trs.length>0){
	                    		$.messager.progress({
	                    		    title: '请稍等',
	                    		    msg: '数据处理中，请稍等...',
	                    		    text: '正在处理.......'
	                    		});
	                    		comfirm(id,trs,1,company,orderNo,packageLength,packageWidth,packageHeight,remark);
	                    	}else{
	                    		$.messager.alert('提示','出库单异常,无法检测到详细信息.');
	                    		$('#dialog').window('close');
	                    	}    	 	                    	 
	                    }
	                },{
	                    text:'取消出库',
	                    handler:function(){
	                       $('#dialog').window('close');
	                    }
	                }]
		});
	
}

function initOrderItem(id){ 
	var editRow = undefined;
	$('#tb_orderItem').datagrid({
	    url:ctx+'/outOrderConfirm/stockOutOrderItem/listData',
	    height:250,
	    queryParams:{
	    	id:id
	    },
	    columns:[[
	  	        {field:'orderItemId',hidden:'true'},
		        {field:'itemId',hidden:'true'},
		        {field:'itemName',title:'商品名称',width:100},
		        {field:'itemCode',title:'商品编码',width:100},
		        {field:'itemSku',title:'商品属性',width:80},
		        {field:'barCode',title:'商品条码',width:80},
		        {field:'num1',title:'正品',width:60,editor:{type:'numberbox',options:{precision:0}}},
		        {field:'num2',title:'残次品',width:60,editor:{type:'numberbox',options:{precision:0}}},      
		        {field:'num3',title:'机损',width:60,editor:{type:'numberbox',options:{precision:0}}},      
		        {field:'num4',title:'箱损',width:60,editor:{type:'numberbox',options:{precision:0}}}            
		    ]],
	 onClickCell:function(index, field){
		    if (endEditing()) {
		        $('#tb_orderItem').datagrid('selectRow', index).datagrid('editCell', {
		            index : index,
		            field : field
		        });
		        editIndex = index;
		    }
	 	}    
	});
}

function comfirm(id,obj,type,company,orderNo,packageLength,packageWidth,packageHeight,remark){
	var dateStr=JSON.stringify(obj); 
	$.messager.confirm('确定出库上传菜鸟', '此处将直接修改库存，是否继续?', function(r) {
		if (r) {
			$.post(ctx + "/outOrderConfirm/saveStockOutOrderConfirm", {
				id:id,
				remark:remark,
				type:type,
				company :company,
				orderNo :orderNo,
				packageLength :packageLength,
				packageWidth :packageWidth,
				packageHeight :packageHeight,
				data :dateStr
			}, function(data) {
				$.messager.progress('close');
				$.messager.alert('提示', data.msg);
				$('#dialog').window('close');
			});
		}
	});
}


$.extend($.fn.datagrid.methods, {
    editCell : function(jq, param) {
        return jq.each(function() {
            var opts = $('#tb_orderItem').datagrid('options');
            var fields = $('#tb_orderItem').datagrid('getColumnFields', true).concat(
                    $('#tb_orderItem').datagrid('getColumnFields'));
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_orderItem').datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            $('#tb_orderItem').datagrid('beginEdit', param.index);
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_orderItem').datagrid('getColumnOption', fields[i]);
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
  if ($('#tb_orderItem').datagrid('validateRow', editIndex)) {
      $('#tb_orderItem').datagrid('endEdit', editIndex);
      editIndex = undefined;
      return true;
  } else {
      return false;
  }
}