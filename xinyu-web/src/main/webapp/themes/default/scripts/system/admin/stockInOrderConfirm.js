$(document).ready(function(){
	stockInComfirmOrder.initTable();
});

var stockInComfirmOrder={};
stockInComfirmOrder.initTable=function(){
	$('#tb_stockInComfirmOrder').datagrid({
		url:ctx+'/inventory/stockInComfirmOrderListData',
	    height:750,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#userId").combobox('getValue'),
	    	searchText:$("#searchText").textbox('getValue')
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'ck',checkbox:true},
	        {field:'id',title:'ID',width:120,hidden:true},
	        {field:'orderCreateTime',title:'创建日期',width:120},
	        {field:'userName',title:'商铺名称',width:180},
	        {field:'erpOrderCode',title:'入库单号',width:150},
	        {field:'orderType',title:'单据类型',width:150},
	        {field:'orderCode',title:'快递编码',width:150},
	        {field:'orderNo',title:'快递单号',width:150},
	        {field:'reason',title:'入库原因',width:150},
	        {field:'remark',title:'备注信息',width:150},
	        {field:'operator',title:'操作',width:180,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);'  onclick='selectRow();'>入库单明细</a>"+"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='comfirm()'>确认</a>";
	        }}
	       
	    ]],
	    pagination:true
	});
	
	var pagination =$('#tb_stockInComfirmOrder').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

stockInComfirmOrder.search=function(){
	$('#tb_stockInComfirmOrder').datagrid('load', {
   		 userId:$("#userId").combobox('getValue'),
   		 searchText:$("#searchText").textbox('getValue')
	});
}

function selectRow(){
	var row=$('#tb_stockInComfirmOrder').datagrid("getChecked");
	if(row==null){
		$.messager.alert('错误','请选择订单！！');
	}else{
		$('#dialog').dialog({
		    title: '出库单明细',
		    width: 800,
		    height: 580,
		    href: ctx+'/inventory/f7StockInOrderItemList?id='+row.id,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){ 
	                    	$("#dialog").hide();
	                    	$('#dialog').window('close');	                    	 
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $("#dialog").hide();
	                       $('#dialog').window('close');
	                    }
	                }]
		});
	}
	
}

function comfirm(){
	var row=$('#tb_stockInComfirmOrder').datagrid("getChecked");
	if(row==null){
		$.messager.alert('错误','请选择订单！！');
	}else{
		$.messager.alert('系统提示','功能还在开发中不可用！');
	}
}
