$(document).ready(function(){
	local.initTable();
});
var local = {};
local.initTable = function(){
	$('#tb_local').datagrid({
	    url:ctx+'/pcLocal/localPlate/listData',
	    height:650,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	              {field:'id',hidden:true},
	    		  {field:'boxout',title:'货架',width:100},
	    		  {field:'shopName',title:'商家',width:120},
	    		  {field:'name',title:'名称',width:120},
	    		  {field:'sku',title:'属性',width:120},
	    		  {field:'barCode',title:'条码',width:120},
	    		  {field:'num',title:'数量',width:60},
	    		  {field:'batchCode',title:'批次号',width:150},
	    		  {field:'lastUpdate',title:'时间',width:135},
	    		  {field:'operate',title:'操作',width:100,formatter:function(value,row,index){
	    			  return "<a href='javascript:void(0);' onclick=\"local.edit(\'"+row.id+"'\);\">修改</a>";
	    		  }}
	    		]],
	    pagination:true,
	    onClickRow:function(index, row){
	    	initRecordTable(row.id);
	    }
	});
	
	var pagination =$('#tb_local').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200,500], 
        showPageList: true,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

local.clear = function(){
	$("#row").textbox('setValue');
	$("#boxOut").textbox('setValue');
	$("#code").textbox('setValue');
	$("#floor").textbox('setValue');
	$("#q").textbox('setValue');
}

local.exportData = function(){
	var shopId = $('#shopId').combobox('getValue');
	var row = $('#row').textbox('getValue');
	var boxOut = $('#boxOut').textbox('getValue');
	var floor = $('#floor').textbox('getValue');
	var code = $('#code').textbox('getValue');
	var state = $('#state').combobox('getValue');
	var q = $('#q').textbox('getValue');
	var url = ctx + "/pcLocal/localPlate/xls?shopId="+shopId+"&row="+row+"&boxOut="+boxOut+"&floor="+floor+"&code="+code+"&state="+state+"&q="+q;
	window.location.href = url;
}

function initRecordTable(id){

	$('#tb_record').datagrid({
	    url:ctx+'/pcLocal/record/listData',
	    height:200,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	    		  {field:'createDate',title:'操作时间',width:140},
	    		  {field:'person',title:'操作人',width:100},
	    		  {field:'state',title:'操作',width:80,formatter:function(value,row,index){
	    			  if(value=="UP"){
	    				  return "上架";
	    			  }else{
	    				  return "下架";
	    			  }
	    		  }},
	    		  {field:'name',title:'商品信息',width:120},
	    		  {field:'sku',title:'商品属性',width:120},
	    		  {field:'barCode',title:'商品条码',width:120},
	    		  {field:'num',title:'商品数量',width:60}  
	    		]],
	    pagination:false,
	    queryParams:{
	    	id:id
	    }
	});
}

local.search = function(){
	$('#tb_local').datagrid('load',{
		shopId:$('#shopId').combobox('getValue'),
		row:$('#row').textbox('getValue'),
		boxOut:$('#boxOut').textbox('getValue'),
		floor:$('#floor').textbox('getValue'),
		code:$('#code').textbox('getValue'),
		state:$('#state').combobox('getValue'),
		q:$('#q').textbox('getValue')
	});
}

local.searchItem = function(){
	$('#tb_groupItem').datagrid('load',{
		shopId:$("#sid").combobox('getValue'),
		q:$('#aq').textbox('getValue')
	});
}

local.initOld = function(){
	$('#tb_oldPlate').datagrid({
	    url:ctx+'/pcLocal/initLocalPlate',
	    height:500,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	              {field:'id',checkbox:true},
	    		  {field:'boxout',title:'货架',width:60},
	    		  {field:'shopName',title:'商家',width:70},
	    		  {field:'name',title:'名称',width:110},
	    		  {field:'barCode',title:'条码',width:110},
	    		  {field:'num',title:'数量',width:60}
	    		]],
	    pagination:true,
	    toolbar:'#oldTab',
	    queryParams:{
	    	row:$('#oRow').textbox('getValue'),
	    	boxOut:$('#oBoxOut').textbox('getValue'),
	    	floor:$('#oFloor').textbox('getValue'),
	    	code:$('#oCode').textbox('getValue'),
	    	state:$('#oState').combobox('getValue')
	    }
	});
	
	var pagination =$('#tb_oldPlate').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200,500], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条   共 {total} 条'
	});
}

local.initNew = function(){
	$('#tb_newPlate').datagrid({
	    url:ctx+'/pcLocal/initLocalPlate',
	    height:500,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	              {field:'id',checkbox:true},
	              {field:'boxout',title:'货架',width:60},
	    		  {field:'shopName',title:'商家',width:70},
	    		  {field:'name',title:'名称',width:110},
	    		  {field:'barCode',title:'条码',width:110},
	    		  {field:'num',title:'数量',width:60}
	    		]],
	    pagination:true,
	    toolbar:'#newTab',
	    queryParams:{
	    	row:$('#nRow').textbox('getValue'),
	    	boxOut:$('#nBoxOut').textbox('getValue'),
	    	floor:$('#nFloor').textbox('getValue'),
	    	code:$('#nCode').textbox('getValue'),
	    	state:$('#nState').combobox('getValue')
	    }
	});
	
	var pagination =$('#tb_newPlate').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200,500], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前{from} - {to} 条   共 {total} 条'
	});
}


local.move = function(){
		$('#dialog').dialog({
			title: '库位调整',
		    width: 1200,
		    height: 590,
		    closed: false,
		    cache: false,
		    href: ctx+'/pcLocal/localPlate/toMove',
		    modal: false,
		    onLoad:function() {
		    	local.initOld();
		    	local.initNew();
	        },
		    buttons: [{
	                    text:'确认',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var oldRow = $('#tb_oldPlate').datagrid('getSelected');          
	                    	var newRow = $('#tb_newPlate').datagrid('getSelected'); 
	                    	$.messager.confirm("提示","是否确认执行库位调整，该操作不可逆？",function(data){
	                    		 $.messager.progress({
	 	                 		    title: '请稍等',
	 	                 		    msg: '数据处理中，请稍等...',
	 	                 		    text: '正在处理.......'
	 	                 		});
	                    		 $.post(ctx+"/pcLocal/localPlate/save",{oldId:oldRow.id,newId:newRow.id},function(data){
	                    			 $.messager.progress('close');
	    	                    	 $.messager.alert("提示",data.msg);
	    	                    	 $('#dialog').window('close');
	    		                       $('#tb_local').datagrid('load',{
	    		                   		shopId:$('#shopId').combobox('getValue'),
	    		                   		row:$('#row').combobox('getValue'),
	    		                   		boxOut:$('#boxOut').combobox('getValue'),
	    		                   		floor:$('#floor').combobox('getValue'),
	    		                   		code:$('#code').combobox('getValue'),
	    		                   		state:$('#state').combobox('getValue'),
	    		                   		q:$('#q').textbox('getValue')	                   	
	    	                  		 });
	    	                     }); 
	                    	});   
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_local').datagrid('load',{
	                   		shopId:$('#shopId').combobox('getValue'),
	                   		row:$('#row').combobox('getValue'),
	                   		boxOut:$('#boxOut').combobox('getValue'),
	                   		floor:$('#floor').combobox('getValue'),
	                   		code:$('#code').combobox('getValue'),
	                   		state:$('#state').combobox('getValue'),
	                   		q:$('#q').textbox('getValue')	                   	
                  		 });
	                    }
	                }]
		});
}

local.edit = function(id){
	if(id){
		$('#dialog').dialog({
			title: '库位调整',
		    width: 600,
		    height: 460,
		    closed: false,
		    cache: false,
		    href: ctx+'/pcLocal/localPlate/toEdit',
		    modal: false,
		    queryParams:{
		    	id:id
		    },
		    onLoad:function() {
		    	initItem(id);
	        },
		    buttons: [{
	                    text:'确认',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	 var json="{";
	                         json=json+"id:'"+$("#id").val()+"',";
	                         json=json+"row:'"+$("#eRow").textbox("getValue")+"',";
	                         json=json+"boxOut:'"+$("#eBoxOut").textbox("getValue")+"',";
	                         json=json+"floor:'"+$("#eFloor").textbox("getValue")+"',";
	                         json=json+"code:'"+$("#eCode").textbox("getValue")+"',}";
	                         var rows  =$('#tb_itemDetail').datagrid("getRows");
	                     	 var dateStr = JSON.stringify(rows); 
	                     	$.messager.confirm("提示","是否确认修改该库位？",function(data){
	                    		 $.messager.progress({
	 	                 		    title: '请稍等',
	 	                 		    msg: '数据处理中，请稍等...',
	 	                 		    text: '正在处理.......'
	 	                 		});
	                    		 $.post(ctx+"/pcLocal/localPlate/update",{json:json,rows:dateStr},function(data){
	                    			 $.messager.progress('close');
	    	                    	 $.messager.alert("提示",data.msg);
	    	                    	 $('#dialog').window('close');
	    		                     $('#tb_local').datagrid('load',{
	    		                   		shopId:$('#shopId').combobox('getValue'),
	    		                   		row:$('#row').combobox('getValue'),
	    		                   		boxOut:$('#boxOut').combobox('getValue'),
	    		                   		floor:$('#floor').combobox('getValue'),
	    		                   		code:$('#code').combobox('getValue'),
	    		                   		state:$('#state').combobox('getValue'),
	    		                   		q:$('#q').textbox('getValue')	                   	
	    	                  		 });
	    	                     }); 
	                    	});   
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_local').datagrid('load',{
	                   		shopId:$('#shopId').combobox('getValue'),
	                   		row:$('#row').combobox('getValue'),
	                   		boxOut:$('#boxOut').combobox('getValue'),
	                   		floor:$('#floor').combobox('getValue'),
	                   		code:$('#code').combobox('getValue'),
	                   		state:$('#state').combobox('getValue'),
	                   		q:$('#q').textbox('getValue')	                   	
                  		 });
	                    }
	                }]
		});

	}
}

function initItem(id){ 
	var url = ctx+'/pcLocal/localPlate/detail/listData';
	var editRow = undefined;
	$('#tb_itemDetail').datagrid({
	    url:url,
	    height:380,
	    rownumbers:true,
	    toolbar:"#toolbar",
	    queryParams:{
	    	id:id
	    },
	    columns:[[
	              {field:'id',hidden:true},
	              {field:'shopName',title:'商家',width:80},
	              {field:'name',title:'品名',width:250},
	              {field:'sku',title:'属性',width:80},
	              {field:'barCode',title:'条码',width:80},       
	              {field:'num',title:'数量',width:60,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_itemDetail').datagrid('selectRow', index).datagrid('editCell', {
	    		            index : index,
	    		            field : field
	    		        });
	    		        editIndex = index;
	    		    }
	    	 	}
		});
}

function batchAppend(){
	$('#item').dialog({
		title:'商品列表',
		width:600,
		height:650,
		closed:false,
		cache:false,
		href:ctx+'/pcLocal/localPlate/f7Item/list',
		modal:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#item').dialog('close');
				var rows = $('#tb_groupItem').datagrid('getChecked');
				var dateStr = JSON.stringify(rows);
				initNewAddTable(dateStr);
			}
		},{
            text:'取消',
            handler:function(){
               $('#item').window('close');
           }
      }]
	});
}

function initNewAddTable(dataStr){ 
	var url = ctx+'/pcLocal/localPlate/newItem/listData';
	var editRow = undefined;
	$('#tb_itemDetail').datagrid({
	    url:url,
	    height:600,
	    rownumbers:true,
	    queryParams:{
	    	dataStr:dataStr
	    },
	    columns:[[
	              {field:'id',hidden:true},
	              {field:'shopName',title:'商家',width:80},
	              {field:'name',title:'品名',width:300},
	              {field:'sku',title:'属性',width:80},
	              {field:'barCode',title:'条码',width:80},       
	              {field:'num',title:'数量',width:60,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_itemDetail').datagrid('selectRow', index).datagrid('editCell', {
	    		            index : index,
	    		            field : field
	    		        });
	    		        editIndex = index;
	    		    }
	    	 	}
		});
}


$.extend($.fn.datagrid.methods, {
    editCell : function(jq, param) {
        return jq.each(function() {
            var opts = $('#tb_itemDetail').datagrid('options');
            var fields = $('#tb_itemDetail').datagrid('getColumnFields', true).concat(
                    $('#tb_itemDetail').datagrid('getColumnFields'));
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_itemDetail').datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            $('#tb_itemDetail').datagrid('beginEdit', param.index);
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_itemDetail').datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

var editIndex = undefined;
//结束编辑 
function endEditing() {
  if (editIndex == undefined) {
      return true
  }
  if ($('#tb_itemDetail').datagrid('validateRow', editIndex)) {
      $('#tb_itemDetail').datagrid('endEdit', editIndex);
      editIndex = undefined;
      return true;
  } else {
      return false;
  }
}