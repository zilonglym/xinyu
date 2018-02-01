<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div style="padding: 5px;">
	<form method="post" id="centroItemAdd_from" >
		<table class="tableForm">
			<tr>
			<th style="width: 70px;">仓库</th>
				<td>
					<select  name="name"  class="easyui-validatebox">
					<c:forEach items="${centros}" var="centro">
				<option value='${centro.id}'>
				${centro.name}
				</option>
			</c:forEach>
					</select>
				</td>
			</tr>
		
			<tr>
				<th style="width: 70px;">大小</th>
				<td><input name="size" class="easyui-validatebox"
					style="width: 155px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 50px;">商品数量</th>
				<td><input name="itemQuantity" 
					value="" style="width:120px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 50px;">地址</th>
				<td><input name="address" class="easyui-validatebox"
					value="" style="width:120px;" />
				</td>
			</tr>
		</table>
	</form>
</div>

