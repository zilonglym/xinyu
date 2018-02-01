$(document).ready(function(){
	profit.initTable();
});
var profit={};
profit.initTable=function(){
	$("#tb_profit").datagrid({
		url:ctx+"/record/profit/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:false,
		fitColumns:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'userName',title:'商家名称',width:100},
		  {field:'yunda_income ',title:'韵达收入',width:100,formatter:function(value,row,index){
			  if(row.yunda_income){
				  return row.yunda_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'yunda_expend',title:'韵达支出',width:100,formatter:function(value,row,index){
			  if(row.yunda_expend){
				  return row.yunda_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'yunda_profits ',title:'韵达利润',width:100,formatter:function(value,row,index){
			  if(row.yunda_profits){
				  return row.yunda_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'yto_income',title:'圆通收入',width:100,formatter:function(value,row,index){
			  if(row.yto_income){
				  return row.yto_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'yto_expend',title:'圆通支出',width:100,formatter:function(value,row,index){
			  if(row.yto_expend){
				  return row.yto_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'yto_profits',title:'圆通利润',width:100,formatter:function(value,row,index){
			  if(row.yto_profits){
				  return row.yto_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sf_income',title:'顺丰收入',width:100,formatter:function(value,row,index){
			  if(row.sf_income){
				  return row.sf_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sf_expend',title:'顺丰支出',width:100,formatter:function(value,row,index){
			  if(row.sf_expend){
				  return row.sf_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sf_profits',title:'顺丰利润',width:100,formatter:function(value,row,index){
			  if(row.sf_profits){
				  return row.sf_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'ems_income',title:'EMS经济收入',width:100,formatter:function(value,row,index){
			  if(row.ems_income){
				  return row.ems_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'ems_expend',title:'EMS经济支出',width:100,formatter:function(value,row,index){
			  if(row.ems_expend){
				  return row.ems_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'ems_profits',title:'EMS经济利润',width:100,formatter:function(value,row,index){
			  if(row.ems_profits){
				  return row.ems_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'postb_income',title:'邮政小包收入',width:100,formatter:function(value,row,index){
			  if(row.postb_income){
				  return row.postb_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'postb_expend',title:'邮政小包支出',width:100,formatter:function(value,row,index){
			  if(row.postb_expend){
				  return row.postb_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'postb_profits',title:'邮政小包利润',width:100,formatter:function(value,row,index){
			  if(row.postb_profits){
				  return row.postb_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'zto_income',title:'中通收入',width:100,formatter:function(value,row,index){
			  if(row.zto_income){
				  return row.zto_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'zto_expend',title:'中通支出',width:100,formatter:function(value,row,index){
			  if(row.zto_expend){
				  return row.zto_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'zto_profits',title:'中通利润',width:100,formatter:function(value,row,index){
			  if(row.zto_profits){
				  return row.zto_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sto_income',title:'申通收入',width:100,formatter:function(value,row,index){
			  if(row.sto_income){
				  return row.sto_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sto_expend',title:'申通支出',width:100,formatter:function(value,row,index){
			  if(row.sto_expend){
				  return row.sto_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sto_profits',title:'申通利润',width:100,formatter:function(value,row,index){
			  if(row.sto_profits){
				  return row.sto_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'htky_income',title:'汇通收入',width:100,formatter:function(value,row,index){
			  if(row.htky_income){
				  return row.htky_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'htky_expend',title:'汇通支出',width:100,formatter:function(value,row,index){
			  if(row.htky_expend){
				  return row.htky_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'htky_profits',title:'汇通利润',width:100,formatter:function(value,row,index){
			  if(row.htky_profits){
				  return row.htky_profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'gto_income',title:'国通收入',width:100,formatter:function(value,row,index){
			  if(row.gto_income){
				  return row.gto_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'gto_expend',title:'国通支出',width:100,formatter:function(value,row,index){
			  if(row.gto_expend){
				  return row.gto_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'gto_profits',title:'国通利润',width:100,formatter:function(value,row,index){
			  if(row.gto_profits){
				  return row.profits;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'total_income',title:'总收入',width:100,formatter:function(value,row,index){
			  if(row.total_income){
				  return row.total_income;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'total_expend',title:'总支出',width:100,formatter:function(value,row,index){
			  if(row.total_expend){
				  return row.total_expend;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'total_profits',title:'总利润',width:100,formatter:function(value,row,index){
			  if(row.total_profits){
				  return row.total_profits;
			  }else{
				  return 0;
			  }
		  }}
		]],
	});
	
	$('#tb_profit').datagrid('getPager').pagination({
		showPageList:false,
		pageSize: 100,
		pageList:[100,200],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

profit.search=function(){
	$('#tb_profit').datagrid('load',{
		userName:$("#userName").combobox('getValue'),
		beigainTime:$("#beigainTime").combobox('getValue'),
		lastTime:$("#lastTime").combobox('getValue')
	});
}

profit.refresh=function(){
	$('#tb_profit').datagrid('load',{
		name:null
	});
}