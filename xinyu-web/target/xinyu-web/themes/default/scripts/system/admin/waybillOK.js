$(document).ready(function(){
	waybill.initTable();
});
var waybill={};
waybill.initTable=function(){
	$('#tb_waybill').datagrid({
		url:ctx+'/express/waitsOk/listData',
	    height:750,
	    singleSelect:false,
	    remoteSort:false,
	    queryParams:{
	       userId:$("#userId").combobox('getValue'),
	       searchType:$("#searchType").combobox('getValue'),
	  	   sysId:$("#sysId").combobox('getValue'),
	  	   searchText:$("#searchText").textbox('getValue'),
	  	   txtno:$("#txtno").textbox('getValue'),
	  	   receiveState:$("#receiveState").combobox('getValue'),
	  	   beigainTime:$("#beigainTime").datetimebox('getValue'),
	  	   lastTime:$("#lastTime").datetimebox('getValue')
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'id',checkbox:true},
	        {field:'printDate',title:'打印时间',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'batchNumber',title:'打印批次号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'userName',title:'商铺名称',width:180,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'items',title:'商品',width:250,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'nick',title:'昵称',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'name',title:'收件人',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'phone',title:'联系方式',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'address',title:'目的地',width:400,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'company',title:'快递公司',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderNo',title:'运单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'createDate',title:'创建日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'status',hidden:true}
	    ]],
	    rowStyler: function(index,row){
			if (row.status =='WMS_DELETED'){
				return 'background-color:#6293BB;color:#fff;font-weight:bold;';
			}else if(row.status =='WMS_CANCEL'){
				return 'background-color:red;color:#fff;font-weight:bold;';
			}
		},
	    pagination:true
	});
	
	var pagination =$('#tb_waybill').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 200,//每页显示的记录条数，默认为10 
		pageList:[200,500,800,1000],
        showPageList: true,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页'
	});
}

waybill.search=function(){
	$('#tb_waybill').datagrid('load',{
	   userId:$("#userId").combobox('getValue'),
	   searchType:$("#searchType").combobox('getValue'),
	   sysId:$("#sysId").combobox('getValue'),
	   searchText:$("#searchText").textbox('getValue'),
	   txtno:$("#txtno").textbox('getValue'),
	   receiveState:$("#receiveState").combobox('getValue'),
	   beigainTime:$("#beigainTime").datetimebox('getValue'),
	   lastTime:$("#lastTime").datetimebox('getValue')
	 })
}