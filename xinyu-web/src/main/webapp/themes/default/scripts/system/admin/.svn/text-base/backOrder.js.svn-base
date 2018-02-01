$(document).ready(function(){
	backOrder.initTable();
});

var backOrder={};
backOrder.initTable=function(){
	$('#tb_backOrder').datagrid({
		url:ctx+'/orderBack/listData',
	    height:750,
	    remoteSort:false,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	q:$("#q").val()
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'id',checkbox:true},
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
	        {field:'items',title:'商品',width:250,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderCode',title:'原始单号',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'returnCode',title:'退回单号',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'description',title:'备注',width:250,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'account',title:'操作人',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'operate',title:'操作',width:80,formatter:function(value, row, index){
	        		return "<input  type='button' value='修改' onclick=\"backOrder.edit(\'"+row.id+"\')\" />";
	        	}
	        }
	    ]],
	    pagination:true
	});
	
	var pagination =$('#tb_backOrder').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}


backOrder.search=function(){
	$('#tb_backOrder').datagrid('load', {
   		userId:$("#userId").combobox('getValue'),
   		q:$("#q").textbox('getValue')
	});
}

backOrder.add=function(){
	$('#dialog').dialog({
		title: '添加信息',
	    width: 680,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/orderBack/toAddBackOrder',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"orderCode:'"+$("#orderCode").textbox("getValue")+"',";
                     json=json+"returnCode:'"+$("#returnCode").textbox("getValue")+"',";
                     json=json+"reason:'"+$("#reason").combobox("getValue")+"',";
                     json=json+"description:'"+$("#description").textbox("getValue")+"'}";
                     var rows = $('#tb_backOrderItem').datagrid("getRows");
                     var dateStr = JSON.stringify(rows); 
                     console.info(dateStr);
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/orderBack/save",{json:json,rows:dateStr},function(data){
                    	 $.messager.progress('close');
                    	 $('#dialog').window('close');
                    	 $.messager.alert('提示',data.msg);
                     });
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_person').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	});
}

backOrder.edit=function(id){
	$('#dialog').dialog({
		title: '修改信息',
	    width: 680,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/orderBack/toEditBackOrder',
	    queryParams:{
	    	id:id
	    },
	    onLoad:function() {
	    	initOrderItem(id);
        },
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"orderCode:'"+$("#orderCode").textbox("getValue")+"',";
                     json=json+"returnCode:'"+$("#returnCode").textbox("getValue")+"',";
                     json=json+"reason:'"+$("#reason").combobox("getValue")+"',";
                     json=json+"description:'"+$("#description").textbox("getValue")+"'}";
                     var rows = $('#tb_backOrderItem').datagrid("getRows");
                     var dateStr = JSON.stringify(rows); 
                     console.info(dateStr);
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/orderBack/update",{json:json,rows:dateStr},function(data){
                    	 $.messager.progress('close');
                    	 $('#dialog').window('close');
                    	 $.messager.alert('提示',data.msg);
                     });
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_person').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	});
}

function initOrderItem(id){
	var url = ctx+'/orderBack/initBackItem';
	var editRow = undefined;
	$('#tb_backOrderItem').datagrid({
	    url:url,
	    height:450,
	    rownumbers:true,
	    queryParams:{
	    	id:id
	    },
	    columns:[[
	              {field:'itemId',hidden:true},
	              {field:'userId',hidden:true},
	              {field:'shopName',title:'商家',width:100},
	              {field:'itemCode',title:'编码',width:100},
	              {field:'itemName',title:'名称',width:100},
	              {field:'itemSku',title:'属性',width:70},
	              {field:'barCode',title:'条码',width:70},       
	              {field:'num',title:'数量',width:70,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_backOrderItem').datagrid('selectRow', index).datagrid('editCell', {
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
		width:500,
		height:650,
		closed:false,
		cache:false,
		href:ctx+'/orderBack/itemList',
		modal:true,
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$('#item').dialog('close');
				var rows = $('#tb_item').datagrid('getChecked');
				var ids="";
				for(var i=0;i<rows.length;i++){
              		var obj=rows[i];
              		ids+=obj.id+",";
              	}
				initAddTable(ids);
			}
		},{
            text:'取消',
            handler:function(){
               $('#item').window('close');
           }
      }]
	});
}

function initAddTable(ids){ 
	var url = ctx+'/orderBack/initOrderItem';
	var editRow = undefined;
	$('#tb_backOrderItem').datagrid({
	    url:url,
	    height:450,
	    rownumbers:true,
	    queryParams:{
	    	ids:ids
	    },
	    columns:[[
	              {field:'itemId',hidden:true},
	              {field:'userId',hidden:true},
	              {field:'shopName',title:'商家',width:120},
	              {field:'itemCode',title:'编码',width:150},
	              {field:'itemName',title:'名称',width:150},
	              {field:'itemSku',title:'属性',width:80},
	              {field:'barCode',title:'条码',width:80},       
	              {field:'num',title:'数量',width:60,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_backOrderItem').datagrid('selectRow', index).datagrid('editCell', {
	    		            index : index,
	    		            field : field
	    		        });
	    		        editIndex = index;
	    		    }
	    	 	}
		});
	
}

backOrder.initItems = function(){	 
	var url = ctx+'/orderBack/initItem';
	var editRow = undefined;
	var orderCode = $("#orderCode").textbox("getValue");
	$('#tb_backOrderItem').datagrid({
	    url:url,
	    height:450,
	    rownumbers:true,
	    queryParams:{
	    	orderCode:orderCode
	    },
	    columns:[[
	              {field:'itemId',hidden:true},
	              {field:'userId',hidden:true},
	              {field:'shopName',title:'商家',width:100},
	              {field:'itemCode',title:'编码',width:100},
	              {field:'itemName',title:'名称',width:100},
	              {field:'itemSku',title:'属性',width:70},
	              {field:'barCode',title:'条码',width:70},       
	              {field:'num',title:'数量',width:70,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_backOrderItem').datagrid('selectRow', index).datagrid('editCell', {
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
            var opts = $('#tb_backOrderItem').datagrid('options');
            var fields = $('#tb_backOrderItem').datagrid('getColumnFields', true).concat(
                    $('#tb_backOrderItem').datagrid('getColumnFields'));
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_backOrderItem').datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            $('#tb_backOrderItem').datagrid('beginEdit', param.index);
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_backOrderItem').datagrid('getColumnOption', fields[i]);
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
  if ($('#tb_backOrderItem').datagrid('validateRow', editIndex)) {
      $('#tb_backOrderItem').datagrid('endEdit', editIndex);
      editIndex = undefined;
      return true;
  } else {
      return false;
  }
}

