<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>新建入库单</title>
	<script>
	var ctx="${ctx}";
	</script>
	<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/InitArtDialog.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/plugins/iframeTools.source.js" type="text/javascript"></script>
	<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
	
</head>
<body>
	
	<select id="selectUser" name="userId">
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
	<input type="text" id="description" name="description" style="width:350px;height: 30px;" />
	<button  class="btn btn-primary" onclick="javascript:storage.save();">&nbsp;确&nbsp;定&nbsp;</button>
	<table id="contentTable" class="table table-striped table-condensed" >
		<thead><tr>
		<th>序号</th>
		<th>商品编号</th>
		<th>商品名称</th>
		<th>出库数量</th>
		<th>条形码</th>
		<th>操作</th>
		</tr></thead>
		<tbody key="demo">
		<tr key="demo"  rowIndex = "1">
			<td key="seq">1</td>
			<td><input readonly="readonly" style="width:150px;" /><input type="hidden" /><button key="item">...</button></td>
			<td></td>
			<td><input style="width:150px;"/></td>
			<td><input style="width:150px;"/></td>
			<td><button key="add">+</button>
				<button key="remove">-</button>
				
			</td>
		</tr>
		</tbody>
	</table>
	<input type="hidden" id="status" name="status" value="${status}" />
	<script>
	
		var storage={};
		storage.initBtnClick=function(){
			$("button[key='item']").each(function(){
				$(this).unbind("click");
				$(this).bind("click",function(){
					storage.btnClickAdd(this);
				});
			});
			$("button[key='remove']").each(function(){
				$(this).unbind("click");
				$(this).bind("click",function(){
					var row=$("button[key='remove']").length;
					if(row>1){
						$(this).parent().parent().remove();
						storage.refreshSeq();
					}else{
						alert("入库商品数量不能少于一");
					}
				});
			});
			$("button[key='add']").each(function(){
				$(this).unbind("click");
				$(this).bind("click",function(){
					var tr=$("tr[key='demo']");
					var txt;					
					if(tr.length==1){
						var t=$(tr).html();
						t="<td key='seq' >"+(tr.length+1)+t.substr(t.indexOf("</td>"));
						txt='<tr key="demo" rowIndex="'+(tr.length+1)+'" >'+t+'</tr>';
					}else{
						var t=$(tr[0]).html();
						t="<td key='seq' >"+(tr.length+1)+t.substr(t.indexOf("</td>"));
						txt='<tr key="demo" rowIndex="'+(tr.length+1)+'" >'+t+'</tr>';
					}
					var obj=$(txt);
					$($(obj).find("td")[2]).html("");
					$(obj).appendTo("tbody[key='demo']");
					storage.initBtnClick();	
				});
			});
		};
		storage.refreshSeq=function(){
			var seq=1;
			$("td[key='seq']").each(function(){
				$(this).html(seq);
				seq++;
			});
		};
		
		var  rowIndex  =  0;
		storage.btnClickAdd=function  btnClickAdd(obj){
			rowIndex = $(obj).parent().parent().find("td[key='seq']").html();
			var searchText=$("#selectUser").val();
			art.dialog.data("returnFunName",setItemValue);
			var url=ctx+'/f7index/item?searchText='+searchText;
			var param    = {};
			param.id     = "showRoomTable";
			param.title  = "选择商品";
			param.lock   = true;
			param.width  = 600;
			param.height = 520;   
			param.skin="aero";
			art.dialog.open(url,param);
		}
		storage.save=function(){
			var json="[";
			$("tr[key='demo']").each(function(){
				var row=$(this);
				json=json+"{";
				json=json+"itemId:'"+$($(row).find("td")[1]).find("input").eq(1).val()+"',";
				json=json+"quantity:'"+$($(row).find("td")[3]).find("input").val()+"',";
				json=json+"barCode:'"+$($(row).find("td")[4]).find("input").val();
				json=json+"'},"
				
			});
			var  date = "{userId:"+$("#selectUser").val()+",date:"+json.substr(0,json.length -1) +"]}";
		    date  = JSON.stringify(date)
		    var url =    ctx+"/storage/saveStorage"
		$.ajax({
            type: "POST",
            dataType: "json",
            url: url,
           data: { json: date ,status:$("#status").val(),description:$("#description").val()},
            success: function(msg){
            		alert("添加成功");
            		art.dialog.close();
            		window.location.reload();
            	}
			});
		}
		
		storage.initBtnClick();	
		
		function setItemValue(data){
			var rows =  $("tr[rowindex='"+rowIndex+"']").find("td");
			var r=$("tr[rowindex='"+rowIndex+"']");
			$(rows).eq(2).html(data.title);
			$(rows).eq(1).find("input").eq(0).val(data.code);
			$(rows).eq(1).find("input").eq(1).val(data.id);
			
		}
	</script>
</body>
</html>
