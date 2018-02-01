<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<script src="${ctx}/static/scripts/auditTrade.js?v=2.02" type="text/javascript"></script>
	<script type="text/javascript">
	var ctx="${ctx}";
	
	
	function auditSfArea(){
	//获取选中的ID
	var rows=$('#auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单审核','请选择要审核的订单!!');
		return;
	}
	var ids="";
	for(var i=0;i<rows.length;i++){
		var obj=rows[i];
		ids+=obj.val+",";
	}
	window.open("${ctx}/trade/toAuditArea?tradeIds="+ids);
}

function auditYUNDAArea(typeStr){
		$.messager.confirm('自动审核', '是否自动审核'+typeStr+'?', function(r){
                if (r){
                   //获取选中的ID
					var rows=$('#auditTable').datagrid("getChecked");
					if(rows==null || rows==''){
						$.messager.alert('订单审核','请选择要审核的订单!!');
						return;
					}
					var ids="";
					for(var i=0;i<rows.length;i++){
						var obj=rows[i];
						ids+=obj.val+",";
					}
					window.open("${ctx}/trade/toAuditAreaYUNDA/"+typeStr+"?tradeIds="+ids);
                }
       });
}


function addSplit(){
	//获取选中的ID
	var rows=$('#auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单拆单核','请选择要拆单的订单!!');
		return;
	}
	if(rows.length!=1){
		$.messager.alert('订单拆单核','只能对单一订单进行操作!!');
		return;
	}
	var  id  = rows[0].lgAging ;
	var  tradeId  = rows[0].val ;
	$("#dialog").dialog({
		title: '拆单信息',
	    width: 900,
	    height: 600,
	    resizable: true,
	    closed: false,
	    cache: false,
	    draggable: true,
	    shadow: true,
	    href: ctx+'/trade/toSplitOrder',
	    modal: true,
	    onLoad:function() {
	    	initSplitTable(id);
        },
	    buttons: [{
                    text:'按数量拆单',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var rows=$('#splitTable').datagrid("getChecked");
                 		var json="[";
						for(var i=0;i<rows.length;i++){
								var  obj = rows[i];
							    json=json+"{";
								json=json+"detailId:'"+obj.id+"',";
								json=json+"quantity:'"+$("#split"+obj.id).val()+"'},";
						}
						var  date = "{id:"+tradeId+",date:"+json.substr(0,json.length -1) +"]}";
		   				date  = JSON.stringify(date); 
		   			  	$.messager.progress({
	             		    title: '请稍等',
	             		    msg: '数据处理中，请稍等...',
	             		    text: '正在处理.......'
	             		});
	               		$.post(ctx+"/store/shipOrder/splitShipOrder",{json:date},function(ret){
	               		 	  $.messager.progress('close');
	                          $.messager.alert('拆单结果',ret.msg);
	                          if(ret.code=='200'){
	                             $('#dialog').window('close');
	                             audit.searchList();
	                          }
	                    }); 
					 }}, 
					 {
                    text:'按行拆单',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var rows=$('#splitTable').datagrid("getChecked");
                 		var json="[";
						for(var i=0;i<rows.length;i++){
								var  obj = rows[i];
							    json=json+"{";
								json=json+"detailId:'"+obj.id+"'},";
						}
						var  date = "{id:"+tradeId+",date:"+json.substr(0,json.length -1) +"]}";
		   				date  = JSON.stringify(date); 
		   				$.messager.progress({
	             		    title: '请稍等',
	             		    msg: '数据处理中，请稍等...',
	             		    text: '正在处理.......'
	             		});
	               		$.post(ctx+"/store/shipOrder/splitShipOrderLine",{json:date},function(ret){
	               		 	 $.messager.progress('close');
	                          $.messager.alert('拆单结果',ret.msg);
	                          if(ret.code=='200'){
	                             $('#dialog').window('close');
	                             audit.searchList();
	                          }
	                    }); 
					 }}, 
					      
                   {
                    text:'取消',
                    handler:function(){
                        $('#dialog').window('close');
                    }
                }]	
	});
}

function initSplitTable(id){ 
	var editRow = undefined;
	$('#splitTable').datagrid({
	    url:ctx+'/trade/ajax/shipOrderdata',
	    height:850,
	    queryParams:{
	    	 orderId:id
	    },
	    columns:[[
	    	{field:'id',checkbox:true},
	        {field:'title',title:'商品名称',width:400},
	        {field:'num',title:'数量',width:200},
	        {field:'msg',title:'拆分数量',width:300,
	        	formatter: function(value,row,index){
				   return "<input type='text'  id='split"+row.id+"' value=''/>";
				}
			}
	    ]]
	});
	}	
	</script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
	<form action="${ctx}/trade/waits/batch">
		   	店铺选择:
		    <select id="selectUser" name="userId" class="easyui-combobox" style="width:190px;">
			<option value='0'>全部</option> 
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
			</select>
			快递：
			<select id="selectCompany" name="company" class="easyui-combobox" style="width:110px;">
				<option value=''>全部</option> 
				<option value='SF'>顺丰</option>
				<option value='YUNDA'>韵达</option> 
				<option value='YTO'>圆通</option> 
				<option value='EYB'>EMS经济</option>
				<option value='POSTB'>邮政小包</option>
				<option value='STO'>申通</option>				
				<option value='HTKY'>汇通</option>		
			</select>
		<span id="city_div">
		 	 省份:
		 	 <select id="selectState" name="state" class="easyui-combobox" data-options="valueField:'id', textField:'no'" style="width:120px">	
		 	 <option value="0">全部</option>
			<c:forEach items="${stateList}" var="obj">
				<option value="${obj.description}">${obj.description}</option>
			</c:forEach>		
			</select>	 
	   	</span>
	   	重量
	   	<select name="weight_x" id="weight_x" style="width: 50px;" class="easyui-combobox" >
	   		<option value=">=">&gt;=</option>
	   		<option value="=">=</option>
	   		<option value="&lt;=">&lt;= </option>
	   	</select>
	   	 <input class="easyui-textbox" id="weight" name="weight" style="width: 60px;" >
		乡镇村组:
			<select name="others" id="others" style="width: 80px;" class="easyui-combobox" >
				<option value="0">所有</option>
				<option value="1">包含</option>
				<option value="2">不包含</option>
			</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>    
	    <input id="q" name="q" type="text" style="width:150px;" class="easyui-textbox" data-options="prompt:'输入关键字进行查询 ...'">
	   	<a  id="call" href="javascript:audit.searchList();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	   	<a class="easyui-linkbutton" data-toggle="modal" href="#myModal" >批量审核</a>
	   	<a class="easyui-linkbutton" data-toggle="modal" href="#mySplitModal" >批量拆单</a>
	   		<a class="easyui-linkbutton" data-toggle="modal" href="javascript:auditSfArea();" >顺风</a>
	   		<a class="easyui-linkbutton" data-toggle="modal" href="javascript:auditYUNDAArea('YUNDA411353');" >韵达411353</a>
	   		<a class="easyui-linkbutton" data-toggle="modal" href="javascript:auditYUNDAArea('YUNDA');" >韵达</a>
	   		<a class="easyui-linkbutton" data-toggle="modal" href="javascript:auditYUNDAArea('YTO');" >圆通</a>
	   		<a class="easyui-linkbutton" data-toggle="modal" href="javascript:addSplit();" >自定义拆单</a>
	    </span>
	    
	    
	    
	</form>
	</div>
	<div data-options="title:'批量审核'" style="padding:2px;">
	<table id="auditTable">
		<thead>
		<tr>
		<th>商铺</th>
		<th>支付日期</th>
		<th>订单来源</th>
		<th class="span3">寄送地址</th>
		<th class="span4">购买商品</th>
		<th class="span3">重量(KG)</th>
		<th class="span3">备注</th>	
		</tr>
		</thead>
	</table>
	</div>
	<div>
		<div id="content"></div>
	</div>
	
	
	<div class="modal hide fade" id="myModal">
 		<div class="modal-header">
    		<a class="close" data-dismiss="modal">批量审核</a>
    		<h3>批量审核订单</h3>
  		</div>
  		<div class="modal-body">
    		<p>
    		<span id="tids"></span>
            快递公司选择
            <select name="expressCompany" id="expressCompany">
		    	<option value="-1">未选择</option>
				<c:forEach items="${itemList}" var="item">
					<option value="${item.value}">${item.description}</option>
				</c:forEach>
		    </select>
		    </p>
  		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
	    	<a href="javascript:audit.auditTrade();" class="btn btn-primary">审核通过</a>
	  	</div>
	</div>
	
	<div class="modal hide fade" id="mySplitModal">
 		<div class="modal-header">
    		<a class="close" data-dismiss="modal">批量审核</a>
    		<h3>批量审核订单</h3>
  		</div>
  		<div class="modal-body">
    		<p>
    		<span id="tidsSplit"></span>
               快速拆单模式
            <select name="type" id="type">
		    	<option value="line">行</option>
		    	<option value="count">数量</option>
		    </select>
		    </p>
		 拆单数量(选择数量拆单模式时)
		    <select name="num" id="num">
		    	<option value="1">1</option>
		    	<option value="2">2</option>
		    	<option value="3">3</option>
		    	<option value="4">4</option>
		    	<option value="5">5</option>
		    	<option value="6">6</option>
		    	<option value="7">7</option>
		    	<option value="8">8</option>
		    	<option value="9">9</option>
		    	<option value="10">10</option>
		    	<option value="11">11</option>
		    	<option value="12">12</option>
		    	<option value="16">16</option>
		    	<option value="18">18</option>
		    	<option value="24">24</option>
		    </select>
  		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
	    	<a href="javascript:audit.splitTrade();" class="btn btn-primary">拆单通过</a>
	  	</div>
	</div>
	 <div id="dialog"></div>
</body>
</html>