<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html charset=UTF-8">
<title>お問い合わせ</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<c:choose>
		<c:when test="${ empty operator }">
		<%@ include file="user-navbar.jsp"%>
		<div class="message">
			<c:out value="${ message }" />
		</div>
		<div class="container">
			<h4 class="title">利用者連絡</h4>
				<div align="right">
					<form action="user-message" method="post" style="margin-right: 80px">
						<textarea name="message" maxlength="300" rows="8" cols="60"></textarea>
						<input type="hidden" name="userId" value="${ userId }">
						<button type="submit" class="btn btn-primary">送信</button>
						<br>
					</form>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<%@ include file="operator-navbar.jsp"%>
			<div class="message">
				<c:out value="${ message }" />
			</div>
			<div class="container">
				<h4 class="title">利用者連絡返信</h4>
					<div align="left">
						<form action="send-message-operator" method="post" style="margin-left: 80px">
							<textarea name="message" maxlength="300" rows="8" cols="60"></textarea>
							<input type="hidden" name="userId" value="${ userId }">
							<input type="hidden" name="operatorId" value="${ operatorId }">
							<button type="submit" class="btn btn-primary">送信</button>
							<br>
						</form>
					</div>
				</div>
		</c:otherwise>
	</c:choose>
	<br>
	<div class="container">
			<c:forEach items="${ messageList }" var="messageList">
				<c:choose>
					<c:when test="${ messageList.operatorId == 0 && not empty user}">
						<div align="right">
							<h5 style="color: blue; margin-right: 80px"><c:out value="${ user.lastName }" />&nbsp;<c:out value="${ messageList.messageAt}" /></h5>
							<form action="tweet" method="post" style="margin-right: 80px">
								<textarea name="b" maxlength="3000" rows="8" cols="60" disabled><c:out value="${ messageList.message }" /></textarea> 
							</form>
						</div>
						<br>
					</c:when>	
					<c:when test="${ messageList.operatorId == 0 && empty user }">
						<div align="right">
							<h5 style="color: blue; margin-right: 80px"><c:out value="${ userName }" />&nbsp;<c:out value="${ messageList.messageAt}" /></h5>
							<form action="tweet" method="post" style="margin-right: 80px">
								<textarea name="b" maxlength="3000" rows="8" cols="60" disabled><c:out value="${ messageList.message }" /></textarea> 
							</form>
						</div>
						<br>
					</c:when>
					<c:otherwise>	
						<div align="left">
							<h5 style="color: red; margin-left: 80px" >運営&nbsp;<c:out value="${ messageList.messageAt}" /></h5>
								<textarea name="a" maxlength="3000" rows="8" cols="60" style="margin-left: 80px" disabled><c:out value="${ messageList.message }" /></textarea>
							<br>
						</div>
					</c:otherwise>
				</c:choose>	
			</c:forEach> 
		</div>
</body>
</html>