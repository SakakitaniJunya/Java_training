<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>請求確認</title>
    <%@ include file="header.jsp"%>
  </head>
  <body>
   　<%@ include file="corporator-navbar.jsp" %>
   　<!-- 完成したら直すこと  -->
	<div class="col-12">
   		<h4 class="title">請求確認</h4>
   		<div class="container">
		<table class="table table-striped">
			<tr>
				<th>年月</th>
				<th>利用人数</th>
				<th>休止人数</th>
				<th>請求金額</th>
			</tr>
			<c:forEach items="${ claimList }" var="claimDto">
				<tr>
					<td><c:out value="${ claimDto.claimYM }"/></td>
					<td><c:out value="${ claimDto.numberOfUsers }"/></td>
					<td><c:out value="${ claimDto.numberOfPause }"/></td>
					<td><c:out value="${ claimDto.expense }"/></td>
				</tr>
			</c:forEach>
		</table>
		</div>
   	</div>
  </body>
</html>