
var picking={};
picking.initTable=function(){
	$('#contentTable').datagrid({
	    url:ctx+'/picking/list',
	    height:750,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	        {field:'createDate',title:'分拣日期',width:120},
	        {field:'shopName',title:'单据编号',width:180},
	        {field:'items',title:'单据状态',width:250},
	        {field:'addressInfo',title:'操作人',width:400},
	        {field:'operator',title:'操作',width:120,formatter:function(value,row,index){
	        	return "<a   onclick='selectRow("+value+");'>确认</a> <a href='###' onclick='delTrade("+value+")'>查看</a>";
	        }}
	    ]],
	    pagination:true,
	    toolbar:[{
	    	text:'新建',
	    	iconCls:'icon-add',
	    	handler:function(){
	    		picking.toAdd();
	    	}
	    }]
	});
	
	var pagination =$('#contentTable').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}
picking.initPickingAddTable=function(){
	$('#pickingTable').datagrid({
	    url:ctx+'/picking/addListData', 
	    height:500,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	        {field:'createDate',title:'分拣日期',width:120},
	        {field:'shopName',title:'单据编号',width:120},
	        {field:'items',title:'单据状态',width:150},
	        {field:'addressInfo',title:'操作人',width:100},
	        {field:'operator',title:'操作',width:120,formatter:function(value,row,index){
	        	return "<a   onclick='selectRow("+value+");'>确认</a> <a href='###' onclick='delTrade("+value+")'>查看</a>";
	        }}
	    ]],
	    pagination:true,
	    toolbar:[{
	    	text:'批量设置拣货仓库',
	    	iconCls:'icon-add',
	    	handler:function(){
	    		alert(1);
	    	}
	    }]
	});
}
picking.toAdd=function(){
	$('#dialog').dialog({
	    title: '新建分拣单',
	    width: 800,
	    height: 600,
	    closed: false,
	    cache: false,
	    href: ctx+'/picking/f7add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                    
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                    }
                }]
	});
}
