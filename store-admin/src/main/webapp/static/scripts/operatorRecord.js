$(document).ready(function(){
	operatorRecord.initTable();
});

var operatorRecord={};
operatorRecord.initTable=function(){
	$('#tb_operatorRecord').datagrid({
		url:ctx+"/operatorRecord/listData",
		height:850,
		loadMsg:"正在加载中.......",
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		remoteSort:false,
		columns:[[
		  {field:'id',title:'唯一标识',width:100,hidden:true},
		  {field:'user',title:'商家',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },				
		  {field:'createDate',title:'操作时间',width:250,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'person',title:'操作人',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'ipaddr',title:'操作IP',width:250,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'operatorModel',title:'操作项目',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'description',title:'操作描述',width:350,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'status',title:'状态',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  }
		]]
	});
	
	
	$('#tb_operatorRecord').datagrid('getPager').pagination({
		showPageList:false,
		pageSize: 100,
		pageList:[100,200,500],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
	
}

operatorRecord.search=function(){
	$('#tb_operatorRecord').datagrid('load',{
		startTime:$('#beigainTime').datetimebox('getValue'),
		endTime:$('#lastTime').datetimebox('getValue'),
		personId:$('#selectPerson').combobox('getValue'),
		userId:$('#selectUser').combobox('getValue'),
		model:$('#selectModels').combobox('getValue'),
		q:$('#q').textbox('getValue')
	});
}
