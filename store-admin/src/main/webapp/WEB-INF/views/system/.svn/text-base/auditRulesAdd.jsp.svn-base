<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>
		<c:if test="${empty auditRules}">
		添加审单规则信息</c:if>
		<c:if test="${!empty auditRules}">
		修改审单规则信息
		</c:if>
	</title>
	<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
</head>

<body>	
	<form id="frm" name="frm" method="post" action="${ctx}/sys/auditRules/submit">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td colspan="2" style="text-align: center;color: red;">多个省市地区之间请用逗号分隔</td>
		</tr>
		<tr>
			<td>商家:</td>
			<td>
				<select id="selectUser" name="userId" > 
					<c:forEach items="${userList}" var="user">
						<option value='${user.id}'  
						<c:if test="${user.id == audit.user.id}">
							selected='selected'
						</c:if>
						>${user.shopName}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>快递:</td>
			<td>
				<select id="waybill" name="waybill">
						<c:forEach items="${itemList}" var="item">
							<option value="${item.value}" 	<c:if test="${item.value == audit.expressCompany}">
								selected='selected'
						</c:if>>${item.description}</option>
						</c:forEach>
				</select>
				
			</td>
		</tr>
		<tr>
			<td>到达省份:</td>
			<td>
				<textarea name="includes" id="includes"><c:if test="${audit!=null}">${audit.includes}</c:if></textarea>
			</td>
		</tr>
		<tr>
			<td>不到地区:</td>
			<td>
				<textarea name="exincludes" id="exincludes"><c:if test="${audit!=null}">${audit.exincludes}</c:if></textarea>
			</td>
		</tr>
		<tr>
			<td>起始重量:</td><td><input  type="text"  name="startWegiht" id="startWegiht"  value="<c:if test="${audit!=null}">${audit.startWegiht}</c:if>"/></td>
		</tr>
		<tr>
			<td>结束重量:</td><td><input  type="text"  name="endWegiht" id="endWegiht" value="<c:if test="${audit!=null}">${audit.endWegiht}</c:if>"/></td>
		</tr>
		
		<tr>
			<td  colspan="2" align="left">
				<input type="button" value="保存" onclick="check();"/>
				<input type="button" value="取消" onclick="location.href='${ctx}/sys/auditRules/list'"/>
			</td>
		</tr>
		<input type="hidden" id="id" name="id" value="<c:if test="${audit!=null}">${audit.id}</c:if>"/>
	</table>
	</form>
	<script>
	var ctx="${ctx}";
		function check(){
			var user=$("#selectUser").val();
			var waybill=$("#waybill").val();
			var includes=$("#includes").val();
			var exincludes=$("#exincludes").val();
			var startWegiht=$("#startWegiht").val();
			var endWegiht=$("#endWegiht").val();
			var id=$("#id").val();
			if(includes==''||includes==null){
				alert("到达省市不能为空!");
				return false;
			}
			//判断这个商家这快递是否已存在规则
			$.post(ctx+"/sys/auditRules/isHave",{id:user,comp:waybill},function(data){
				if(data.ret==0 || id!=''){
					document.getElementById("frm").submit();
				}else{
					alert("此商家已有该快递的设置规则!");
				}
			});
			
		}
	</script>
</body>
</html>
