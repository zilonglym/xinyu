<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>商品选择</title>
	<script>
	var ctx="${ctx}";
	</script>
	<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/InitArtDialog.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/plugins/iframeTools.source.js" type="text/javascript"></script>
	<style>
		
		.pagination {
	margin:20px 0
}
.pagination ul {
	display:inline-block;
	*display:inline;
	margin-bottom:0;
	margin-left:0;
	-webkit-border-radius:4px;
	-moz-border-radius:4px;
	border-radius:4px;
	*zoom:1;
	-webkit-box-shadow:0 1px 2px rgba(0,0,0,0.05);
	-moz-box-shadow:0 1px 2px rgba(0,0,0,0.05);
	box-shadow:0 1px 2px rgba(0,0,0,0.05)
}
.pagination ul>li {
	display:inline
}
.pagination ul>li>a,.pagination ul>li>span {
	float:left;
	padding:4px 12px;
	line-height:20px;
	text-decoration:none;
	background-color:#fff;
	border:1px solid #ddd;
	border-left-width:0
}
.pagination ul>li>a:hover,.pagination ul>.active>a,.pagination ul>.active>span {
	background-color:#f5f5f5
}
.pagination ul>.active>a,.pagination ul>.active>span {
	color:#999;
	cursor:default
}
.pagination ul>.disabled>span,.pagination ul>.disabled>a,.pagination ul>.disabled>a:hover {
	color:#999;
	cursor:default;
	background-color:transparent
}
.pagination ul>li:first-child>a,.pagination ul>li:first-child>span {
	border-left-width:1px;
	-webkit-border-bottom-left-radius:4px;
	border-bottom-left-radius:4px;
	-webkit-border-top-left-radius:4px;
	border-top-left-radius:4px;
	-moz-border-radius-bottomleft:4px;
	-moz-border-radius-topleft:4px
}
.pagination ul>li:last-child>a,.pagination ul>li:last-child>span {
	-webkit-border-top-right-radius:4px;
	border-top-right-radius:4px;
	-webkit-border-bottom-right-radius:4px;
	border-bottom-right-radius:4px;
	-moz-border-radius-topright:4px;
	-moz-border-radius-bottomright:4px
}
.pagination-centered {
	text-align:center
}
.pagination-right {
	text-align:right
}
.pagination-large ul>li>a,.pagination-large ul>li>span {
	padding:11px 19px;
	font-size:17.5px
}
.pagination-large ul>li:first-child>a,.pagination-large ul>li:first-child>span {
	-webkit-border-bottom-left-radius:6px;
	border-bottom-left-radius:6px;
	-webkit-border-top-left-radius:6px;
	border-top-left-radius:6px;
	-moz-border-radius-bottomleft:6px;
	-moz-border-radius-topleft:6px
}
.pagination-large ul>li:last-child>a,.pagination-large ul>li:last-child>span {
	-webkit-border-top-right-radius:6px;
	border-top-right-radius:6px;
	-webkit-border-bottom-right-radius:6px;
	border-bottom-right-radius:6px;
	-moz-border-radius-topright:6px;
	-moz-border-radius-bottomright:6px
}
.pagination-mini ul>li:first-child>a,.pagination-small ul>li:first-child>a,.pagination-mini ul>li:first-child>span,.pagination-small ul>li:first-child>span {
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
	-moz-border-radius-bottomleft:3px;
	-moz-border-radius-topleft:3px
}
.pagination-mini ul>li:last-child>a,.pagination-small ul>li:last-child>a,.pagination-mini ul>li:last-child>span,.pagination-small ul>li:last-child>span {
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
	-moz-border-radius-topright:3px;
	-moz-border-radius-bottomright:3px
}
.pagination-small ul>li>a,.pagination-small ul>li>span {
	padding:2px 10px;
	font-size:11.9px
}
.pagination-mini ul>li>a,.pagination-mini ul>li>span {
	padding:0 6px;
	font-size:10.5px
}
	</style>
</head>
<body>
	<div style="height: 410px;">
	<table id="contentTable"  border="0" width="580" cellpadding="1" cellspacing="1" style="max-height:360px;">
		<thead style="background-color: #9A9A9A">
			<th>选择</th>
			<th>编号</th>
			<th>名称</th>
		</thead>
	
	<tbody style="background-color: #F9F9F9">
		<c:forEach items="${list.content}" var="item">
			<tr onclick="selectRow(this);" >
				<td><input type="radio" value="${item.id}" name="rdo"  /></td>
				<td>${item.code}</td>
				<td>${item.title}</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	<tags:f7_pagination page="${list}" paginationSize="15"/>
</div>
	<button onclick="selectOk();">确定</button>
	<button onclick="selectCancel();">取消</button>
	<script>
		function selectRow(obj){
			$(obj).find("input").attr("checked","checked");
		}
		function selectOk(){
			var trObj={};
			
			$("#contentTable").find("input[checked='checked']").each(function(){
				trObj.id=$(this).val();
				trObj.title=$(this).parent().parent().find("td")[2].innerHTML;
				trObj.code=$(this).parent().parent().find("td")[1].innerHTML;
			});
			if(window.dialogArguments){
					window.opener[window.dialogArguments].call(null,objValue);
					window.open('','_self');   
				    window.opener=null;
				    if(art.dialog.data("noClose")== 1){
				    	
				    }else{
				    	selectCancel();
				    }
				}else{
					if (artDialog.open.origin[art.dialog.data("returnFunName")] == undefined) {
						art.dialog.data("returnFunName").call(null, trObj);
					}
					else {
						
						artDialog.open.origin[art.dialog.data("returnFunName")].call(null, trObj);
					}
					if(art.dialog.data("noClose")== 1){
				    	
				    }else{
				    	selectCancel();
				    }
				}
		}
		
		function selectCancel(){
			art.dialog.close();
		}
	</script>
</body>
</html>
