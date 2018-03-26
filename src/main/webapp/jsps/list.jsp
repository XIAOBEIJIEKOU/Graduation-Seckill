<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/tag.jsp" %> 
<!DOCTYPE html>
<html>
   <head>
      <title>秒杀列表页</title>
      <%@include file="common/head.jsp" %>
   </head>
   <h1 align="center">秒杀列表页</h1>
   <body>
		<table class="table table-striped">
		  <thead>
		    <tr>
		      <th>名称</th>
		      <th>库存</th>
		      <th>开始时间</th>
		      <th>结束时间</th>
		      <th>创建时间</th>
		      <th>详情</th>
		    </tr>
		  </thead>
		  <tbody>
		  <c:forEach var="item" items="${list}">
		  	 <tr>
		      <td>${item.name}</td>
		      <td>${item.number}</td>
		      <td>
		      	<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		      </td>
		      <td>
		      	<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		      </td>
		      <td>
		      	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		      </td>
		      <td>
		      	<a class="btn btn-info" href="/seckill/${item.seckillId}/detail" target="_blank">点击详情</a>
		      </td>
		    </tr>
		  </c:forEach>
		  </tbody>
		</table>		
      <%@include file="common/foot.jsp" %>
   </body>
</html>
