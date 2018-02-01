$(document).ready(function(){
	inventoryAdjust.initTable();
});

var inventoryAdjust={};

inventoryAdjust.initTable=function(){
	$('#tb_inventoryAdjust').datagrid({
	    url:ctx+'/inventoryAdjust/listData',
	    height:750,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	      {field:'id',hidden:true},
	      {field:'operateTime',title:'时间',width:100},
	      {field:'outBizCode',title:'外部编码',width:100},
	      {field:'orderType',title:'单据类型',width:100},
	      {field:'adjustReasonType',title:'差异原因',width:100},
	      {field:'confirmType',title:'确认类型',width:100},
	      {field:'remark',title:'备注信息',width:100},
	      {field:'operator',title:'操作',width:100,formatter:function(value,row,index){
	    	  return "<input  type='button' value='上传菜鸟' onclick=\"inventoryAdjust.upInventoryAdjust(\'"+row.id+"\')\" />";
	      }},
	    ]],
	    pagination:true
	});
	
	var pagination =$('#tb_inventoryAdjust').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

inventoryAdjust.search=function(){
	$('#tb_inventoryAdjust').datagrid('load', {
		userId:$("#userId").combobox('getValue'),
    	searchText:$("#searchText").textbox('getValue')
	});
}

inventoryAdjust.upInventoryAdjust = function(id){
	$.messager.confirm('提示信息', '是否继续?', function(r) {
		if (r) {
			$.post(ctx + "/inventoryAdjust/upInventoryAdjustUplaod", {
				id:id
			}, function(data) {
				$.messager.alert('提示', data.msg);
			});
		}
	});
	
}

inventoryAdjust.add = function(type){
	$('#dialog').dialog({
		title: '新建库存调整单',
	    width: 600,
	    height: 800,
	    closed: false,
	    cache: false,
	    href: ctx+'/inventoryAdjust/f7Add',
	    queryParams:{
	    	type:type
	    },
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){ 
                    	json="{";
                    	json=json+"userId:'"+$("#user").combobox("getValue")+"',";
                    	json=json+"centroId:'"+$("#centro").combobox("getValue")+"',";
                    	json=json+"orderType:'"+$("#orderType").combobox("getValue")+"',";
                    	json=json+"reasonType:'"+$("#reasonType").combobox("getValue")+"',";
                    	json=json+"remark:'"+$("#remark").textbox("getValue")+"'}";
                    	var rows  =$('#tb_orderDetail').datagrid("getRows");
                    	var dateStr = JSON.stringify(rows); 
                    	$.messager.progress({
                		    title: '请稍等',
                		    msg: '数据处理中，请稍等...',
                		    text: '正在处理.......'
                		});
                    	$.post(ctx+"/inventoryAdjust/save",{json:json,rows:dateStr},function(data){
                    		$.messager.progress('close');
                    		if(data.msg=="success"){
                    	
                   			 $.messager.alert("成功","设置成功！！！！！");
                   			 $('#dialog').window('close');
                   			 $('#tb_inventoryAdjust').datagrid('load',{
                       			 name:null
                       		 });	 
                       	 }else{
                       		
                       		 $.messager.alert("失败", data.msg);
                       	 	}
                    	});
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_inventoryAdjust').datagrid('load',{
              			 name:null
              		 });
                    }
              }]
	});
}

function batchAppend(){
	var userId = $('#user').combobox('getValue');
	$('#item').dialog({
		title:'商品列表',
		width:500,
		height:650,
		closed:false,
		cache:false,
		href:ctx+'/inventoryAdjust/item/list',
		queryParams:{
			userId:userId
		},
		modal:true,
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$('#item').dialog('close');
				var rows = $('#tb_inventoryItem').datagrid('getChecked');
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
	var url = ctx+'/inventoryAdjust/newItemList/listData';
	var editRow = undefined;
	$('#tb_orderDetail').datagrid({
	    url:url,
	    height:600,
	    rownumbers:true,
	    queryParams:{
	    	dataStr:dataStr
	    },
	    columns:[[
	              {field:'itemId',hidden:true},
	              {field:'itemName',title:'商品名称',width:100},
	              {field:'color',title:'商品属性',width:70},
	              {field:'barCode',title:'条码',width:70},
	              {field:'num11',title:'正品库存',width:70},
	              {field:'num12',title:'残品库存',width:70},         
	              {field:'num',title:'转换数量',width:70,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_orderDetail').datagrid('selectRow', index).datagrid('editCell', {
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
            var opts = $('#tb_orderDetail').datagrid('options');
            var fields = $('#tb_orderDetail').datagrid('getColumnFields', true).concat(
                    $('#tb_orderDetail').datagrid('getColumnFields'));
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_orderDetail').datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            $('#tb_orderDetail').datagrid('beginEdit', param.index);
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_orderDetail').datagrid('getColumnOption', fields[i]);
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
  if ($('#tb_orderDetail').datagrid('validateRow', editIndex)) {
      $('#tb_orderDetail').datagrid('endEdit', editIndex);
      editIndex = undefined;
      return true;
  } else {
      return false;
  }
}

