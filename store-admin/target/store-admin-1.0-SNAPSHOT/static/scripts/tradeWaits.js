$(document).ready(function(){
	trade.initTable();
});
var trade={};
trade.initTable=function(){
	$('#tb_trade').datagrid({
		url:ctx+"/trade/waits/listData",
		height:850,
		loadMsg:"正在加载中.......",
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		fitColumns:true,
		remoteSort:false,
		columns:[[
		  {field:'id',checkbox:true},
		  {field:'shopName',title:'商家名',width:150,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'createDate',title:'付款时间',width:150,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'name',title:'收件人',width:80,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'phone',title:'联系方式',width:160,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'address',title:'收件地址',width:200,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'remark',title:'备注',width:200,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  }
		]]
	});
	$('#tb_trade').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
}

trade.search=function(){
	$("#tb_trade").datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		startDate:$('#beigainTime').datetimebox('getValue'),
		endDate:$('#lastTime').datetimebox('getValue'),
		q:$('#q').textbox('getValue')
	});
}

trade.refresh=function(){
	$("#tb_trade").datagrid('load',{
		name:null
	});
}

trade.edit=function(){
	var row=$("#tb_trade").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改信息',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/trade/waits/f7Edit?id='+row.id,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var json="{";
	                        json=json+"id:'"+$("#id").val()+"',";
	                        json=json+"userId:'"+$("#userId").val()+"',";
	                        json=json+"receiverName:'"+$("#receiverName").textbox("getValue")+"',";
	                        json=json+"receiverMobile:'"+$("#receiverMobile").textbox("getValue")+"',";
	                        json=json+"receiverPhone:'"+$("#receiverPhone").textbox("getValue")+"',";
	                        json=json+"receiverState:'"+$("#receiverState").textbox("getValue")+"',";
	                        json=json+"receiverCity:'"+$("#receiverCity").textbox("getValue")+"',";
	                        json=json+"receiverDistrict:'"+$("#receiverDistrict").textbox("getValue")+"',";
	                        json=json+"receiverAddress:'"+$("#receiverAddress").textbox("getValue")+"',";
	                        json=json+"sellerMemo:'"+$("#remark").textbox("getValue")+"'}"                              
	                     $.post(ctx+"/trade/waits/save",{json:json},function(data){
	                    	 if(data&&data.ret=="success"){
	                    		 alert("修改成功！");
	                    		 $('#dialog').window('close');
	  	                       	 $('#tb_trade').datagrid('load',{
	                    			 name:null
	                    		 });
	                    	 }else{
	                    		 alert("修改失败！");
	                    	 }
	                     }); 
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_trade').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		alert("请勾选需操作行！");
	}
}