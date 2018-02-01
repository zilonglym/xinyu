<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
int current =  page.getNumber() + 1;
int begin = Math.max(1, current - paginationSize/2);
int end = Math.min(begin + (paginationSize - 1), page.getTotalPages());

request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
%>

<div class="pagination">
	<ul>
		 <% if (page.hasPreviousPage()){%>
               	<li><a href="?page=1&searchText=${searchText}">&lt;&lt;</a></li>
                <li><a href="?page=${current-1}&searchText=${searchText}">&lt;</a></li>
         <%}else{%>
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
         <%} %>
 
		
			<c:if test="${end <8}">
				<c:forEach var="i" begin="${begin}" end="${end}">
	            <c:choose>
	                <c:when test="${i == current}">
	                    <li class="active"><a href="?page=${i}&searchText=${searchText}">${i}</a></li>
	                </c:when>
	                <c:otherwise>
	                    <li><a href="?page=${i}&searchText=${searchText}">${i}</a></li>
	                </c:otherwise>
	            </c:choose>
	             </c:forEach>
	         </c:if>
	         <c:if test="${end>=8}">
	         	<c:if test="${current>2}">
		         	<li><a>...</a></li>
	                <li><a href="?page=${current-2}&searchText=${searchText}">${current-2}</a></li>
	                <li><a href="?page=${current-1}&searchText=${searchText}">${current-1}</a></li>
               	</c:if>
                <li class="active"><a href="?page=${current}&searchText=${searchText}">${current}</a></li>
               <c:choose>
                <c:when  test="${(current+2)<=end}">
	                <li><a href="?page=${current+1}&searchText=${searchText}">${current+1}</a></li>
	                <li><a href="?page=${current+2}&searchText=${searchText}">${current+2}</a></li>
	            </c:when>
	             <c:when  test="${(current+1)<=end}">
	               <li><a href="?page=${current+1}&searchText=${searchText}">${current+1}</a></li>
	            </c:when>
	             </c:choose>
	             <c:if test="${(current+2)<end}">
	             	<li><a>...</a></li>
	             </c:if>
	         </c:if>
	  	 <% if (page.hasNextPage()){%>
               	<li><a href="?page=${current+1}&searchText=${searchText}">&gt;</a></li>
                <li><a href="?page=${page.totalPages}&searchText=${searchText}">&gt;&gt;</a></li>
         <%}else{%>
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
         <%} %>

	</ul>
</div>

