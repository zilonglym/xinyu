<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<script>
		var ctx="${ctx}";
	</script>
</head>
<body>
	<legend><small>批量上传商品</small></legend>
    <div class="tab-content">
    	<!-- 批量商品上传 -->
    	<div class="tab-pane active" id="upload">
		<form id="uploadForm" action="${ctx}/item/submitImportItem" method="post" enctype="multipart/form-data" class="form-horizontal">
		<fieldset>
			<div >
				<div class="controls">
					<input type="file" name="file"/> 
					<select id="id" name="id" class="easyui-combobox">
						<c:forEach items="${userList}" var="user">
							<option value='${user.id}'  
							<c:if test="${user.id == userId}">
								selected='selected'
							</c:if>
							>${user.shopName}</option>
						</c:forEach>
					</select>
					
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
		</form>
		</div>
		
	</div>
</body>
</html>