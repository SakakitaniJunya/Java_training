<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
<!--
	#navbar {
		background-color: #F9FBFE;
		padding-top: 10px;
		padding-bottom: 10px;
	}
	#navbar_flexcontainer {
  		display: flex;
	}
	#nav-message {
		margin-right:50px;
	}
	#logo {
		margin-left:20px;
	}
	.right-item {
		margin-left: auto;
	}
	.menu-button {
		margin-right:20px;
	}
-->
</style>
<div id="navbar">
	<div id="navbar_flexcontainer">
		<a href="list-user">
			<img id="logo" src="img/CodeTrain.png" height="90" width="150" />
		</a>
		<div id="nav-message" class="right-item error-message" style="margin-top:2.2%">
			<c:out value="${ navbarMessage }"/>
		</div>
		<c:if test="${not empty corporator.lastName}">
			<div style="margin-top:2.2%">
				<c:out value="${ corporator.lastName += ' ' += corporator.firstName += 'さん、こんにちは　' }" />
			</div>
		</c:if>
		
		<c:if test="${ not empty corporator }">
			<form action="change-corporator-password" method="get" class="form-inline flexitem">
				<button type="submit" class="btn btn-outline-primary btn-sm menu-button">パスワード変更</button>
			</form>
			<form action="logout" method="post" class="form-inline flexitem">
				<button type="submit" class="btn btn-outline-danger btn-sm menu-button">ログアウト</button>
			</form>
		</c:if>
	</div>
</div>	





