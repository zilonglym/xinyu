$(document).ready(function(){
	count.initTable();
});
var count={};
count.initTable=function(){
	$("#tb_count").datagrid({
		url:ctx+"/record/countRecordData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:false,
		fitColumns:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'userName',title:'商家名',width:150},
		  {field:'yunda',title:'韵达',width:100,formatter:function(value,row,index){
			  if(row.yunda){
				  return row.yunda;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'yto',title:'圆通',width:100,formatter:function(value,row,index){
			  if(row.yto){
				  return row.yto;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'zto',title:'中通',width:100,formatter:function(value,row,index){
			  if(row.zto){
				  return row.zto;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sto',title:'申通',width:100,formatter:function(value,row,index){
			  if(row.sto){
				  return row.sto;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'htky',title:'汇通',width:100,formatter:function(value,row,index){
			  if(row.htky){
				  return row.htky;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'gto',title:'国通',width:100,formatter:function(value,row,index){
			  if(row.gto){
				  return row.gto;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'ems',title:'EMS经济快递',width:100,formatter:function(value,row,index){
			  if(row.ems){
				  return row.ems;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'postb',title:'邮政小包',width:100,formatter:function(value,row,index){
			  if(row.postb){
				  return row.postb;
			  }else{
				  return 0;
			  }
		  }},
		  {field:'sf',title:'顺丰',width:100,formatter:function(value,row,index){
			  if(row.sf){
				  return row.sf;
			  }else{
				  return 0;
			  }
		  }}, 
		  {field:'sum',title:'合计',width:100,formatter:function(value,row,index){
			  if(row.sum){
				  return row.sum;
			  }else{
				  return 0;
			  }
		  }}
		]]
	});
	//分页显示插件
	$('#tb_count').datagrid('getPager').pagination({
		showPageList:false,
		pageSize: 100,
		pageList:[100,200],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

count.search=function(){
	$('#tb_count').datagrid('load',{
		userName:$("#userName").combobox('getValue'),
		beigainTime:$("#beigainTime").combobox('getValue'),
		lastTime:$("#lastTime").combobox('getValue')
	});
}

count.refresh=function(){
	$('#tb_count').datagrid('load',{
		name:null
	});
}
