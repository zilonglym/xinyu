$(document).ready(function(){
	waybill.initTable();
});
var waybill={};
waybill.initTable=function(){
	$('#tb_waybill').datagrid({
		url:ctx+'/express/waits/listData',
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
	    	{field:'tmsId',hidden:true},
	        {field:'id',checkbox:true},
	        {field:'buyerMemo',title:'买家留言',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'sellerMemo',title:'卖家留言',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'createDate',title:'创建日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'userName',title:'商铺名称',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'items',title:'商品',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'tmsWeight',title:'重量',width:50,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'nick',title:'昵称',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'name',title:'收件人',width:90,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'phone',title:'联系方式',width:90,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'address',title:'目的地',width:150,sortable:true,
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
	        {field:'remark',title:'备注',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderCode',title:'菜鸟单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'operate',title:'操作',width:85,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);'  onclick=\"writeCode(\'"+row.tmsId+"'\);\">填写物流单号</a>";
	        }},
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
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
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


function writeCode(id){
	$('#dialog').dialog({
		title: '填写物流单号',
	    width: 350,
	    height: 150,
	    closed: false,
	    cache: false,
	    href: ctx+'/express/writeCode?id='+id,
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){  
                     var id = $("#id").val(); 
                     var tmsId = $("#tmsId").val(); 
                     var orderNo = $("#orderNo").textbox("getValue");
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/express/saveCode",{id:id,orderNo:orderNo,tmsId:tmsId},function(data){
                    	 $.messager.progress('close');
                    	 $.messager.alert("提示",data.msg);
                    	 $('#dialog').window('close');
                     	})
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                    }
                }]
	});
}