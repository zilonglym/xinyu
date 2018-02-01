$(document).ready(function(){
	record.initTable();
});

var record={};
record.initTable=function(){
	$("#tb_record").datagrid({
		url:ctx+"/record/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:false,
		fitColumns:true,
		remoteSort:false,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'id',title:'唯一标识',width:100,hidden:true},
		  {field:'createTime',title:'创建时间',width:120,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'userName',title:'商家名称',width:120,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'state',title:'目的地省份',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'center',title:'目的地',width:150,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'expressCompany',title:'物流公司',width:120,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'expressOrderNo',title:'物流单号',width:150,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'weight',title:'扫描重量(KG)',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'price',title:'应收运费(元)',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'cost',title:'应付运费(元)',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'importTime',title:'导入时间',width:120,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  }
		]]
	});
	
	//分页显示插件
	$('#tb_record').datagrid('getPager').pagination({
		showPageList:true,
		pageSize:100,
		pageList:[100,200,500],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

record.search=function(){
	$("#tb_record").datagrid('load',{
		userName:$("#userName").combobox('getValue'),
		expressName:$("#expressName").combobox('getValue'),
		q:$("#q").textbox('getValue'),
		beigainTime:$("#beigainTime").datetimebox('getValue'),
		lastTime:$("#lastTime").datetimebox('getValue')
	});
}

record.refresh=function(){
	$("#tb_record").datagrid('load',{
		name:null
	});
}

//运费计算
record.operate=function(){
	$("#dialog").dialog({
		title: '运费计算',
	    width: 350,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: ctx+'/record/f7Parse',
	    modal: false,
	    buttons: [{
                    text:'关闭',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_record').datagrid('load',{
              			 name:null
              		 });
                    }
                }]	
	});
}