$(document).ready(function(){
	pick.initTable();
});
var pick={};
pick.initTable=function(){
	$('#tb_pick').datagrid({
	    url:ctx+'/trade/send/pickings/listData',
	    height:850,
	    singleSelect:true,
	    rownumbers:true,
	    pagination:true, 
	    singleSelect:false,
	    remoteSort:false,
	    columns:[[
	        {field:'ck',checkbox:true},
	        {field:'id',title:'ID',width:20,hidden:true},
	        {field:'createDate',title:'时间',width:200,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'shopName',title:'商家',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'name',title:'收件人',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'company',title:'物流公司',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'orderno',title:'物流单号',width:200,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'items',title:'商品明细',width:400,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        }
	    ]]  
	});
	
	var pagination =$('#tb_pick').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200,250,500], 
        showPageList: true,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

pick.search=function(){
	$('#tb_pick').datagrid('load', {
  		 userId:$("#selectUser").combobox('getValue'),
  		 cpCode:$("#waybill").combobox('getValue')
	});
}

pick.submit=function(){
	var rows = $('#tb_pick').datagrid('getChecked'); 
	var ids = [];
	for(var i=0;i<rows.length;i++){
    	var row=rows[i];
    	var id=row.id;
    	ids.push(id); 
    }
	if (ids.length==0) {
			alert('你还没有选择任何货单！');
		} else {
			$.post(ctx+"/trade/send/submits",{ids:ids.toString()},function(data){
				if(data.ret=='success'){
					 alert("操作成功！");
					 $('#tb_pick').datagrid('load', {
				  		 userId:$("#selectUser").combobox('getValue'),
				  		 cpCode:$("#waybill").combobox('getValue')
					});
				}else{
					alert("操作失败！");
					 $('#tb_pick').datagrid('load', {
				  		 userId:$("#selectUser").combobox('getValue'),
				  		 cpCode:$("#waybill").combobox('getValue')
					});
				}
			});
		}
}
