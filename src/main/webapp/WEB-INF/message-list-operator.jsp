<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html charset=UTF-8">
<title>お問い合わせ一覧</title>
<%@ include file="header.jsp"%>
</head>
<body>
 　<%@ include file="operator-navbar.jsp" %>
	<div class="container col-10">
   		<h4 class="title">利用者連絡一覧</h4>
   			<div class="message">
	   			<c:out value="${ message }" />
	   			<c:forEach items="${ errorMessageList }" var="errorMessage">
	   				<c:out value="${ errorMessage }" />
	   			</c:forEach>
	   		    <br>
   		    </div>
   		    	<h5 style="color: red" >未返信</h5>
   		<table class="table table-bordered">
   			<tr>
	   			<th style="width: 20%">メールアドレス</th>
	   			<th style="width: 10%">ユーザ名</th>
	   			<th style="width: 15%">社名</th>
	   			<th style="width: 40%">お問い合わせ内容</th>
	   			<th style="width: 15%">送信時間</th>
	   		</tr>
			<c:forEach items="${ newMessageList }" var="newMessage">
				<tr>
			   		<td><c:out value="${ newMessage.email }" /></td>
		   			<td><c:out value="${ newMessage.lastName } ${ newMessage.firstName }" /></td>
		   			<td><c:out value="${ newMessage.corporationName }" /></td>
		   			<td><a href="send-message-operator?userId=${ newMessage.userId }&userName=${ newMessage.lastName } ${ newMessage.firstName }"><c:out value="${ newMessage.message }" /></a></td>
		   			<td><c:out value="${ newMessage.messageAt }" /></td>
		   		</tr>
	   		</c:forEach>
	   	</table>
   </div>
   <br>
   <br>
   <div class="container col-10">
		<h5 style="color: green" >返信済み</h5>
   		<table class="table table-bordered" >
   			<tr>
	   			<th style="width: 20%">メールアドレス</th>
	   			<th style="width: 10%">ユーザ名</th>
	   			<th style="width: 15%">社名</th>
	   			<th style="width: 40%">返信内容</th>
	   			<th style="width: 15%">送信時間</th>
	   		</tr>
   			<c:forEach items="${ messageList}" var="message">
				<tr>
		   			<td><c:out value="${ message.email }" /></td>
		   			<td><c:out value="${ message.lastName } ${ message.firstName }" /></td>
		   			<td><c:out value="${ message.corporationName }" /></td>
		   			<td><a href="send-message-operator?userId=${ message.userId }&userName=${ message.lastName } ${ message.firstName }"><c:out value="${ message.message }" /></a></td>
		   			<td><c:out value="${ message.messageAt }" /></td>
	   			</tr>
	 		</c:forEach>
   
   </table>
   </div>
</body>
</html>