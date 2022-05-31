<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<a href="index.jsp">
			<img id="logo" src="img/CodeTrain.png" height="90" width="150" />
		</a>
		<div id="nav-message" class="right-item error-message" style="margin-top:2.2%">
			<c:out value="${ navbarMessage }" />
		</div>
		<c:if test="${ not empty user.lastName}">
			<div style="margin-top:2.2%">
				<c:out value="${ user.lastName += ' ' += user.firstName += 'さん、こんにちは　' }" />
			</div>
		</c:if>

<!--  -->
		<c:choose>
			<c:when test="${ empty user }">
				<form id="form-nav" action="user-login" method="post" class="form-inline flexitem">
					<input type="email" name="id" class="form-control form-control-sm" placeholder="メールアドレス">
					<input type="password" name="password" class="form-control form-control-sm" placeholder="パスワード">
					<input type="hidden" name="uri" value="${ requestScope.uri }">
					<button type="submit" class="btn btn-outline-primary btn-sm menu-button">ログイン</button>
				</form>
			</c:when>
			<c:otherwise>
				<form id="form-nav" action="change-user-password" method="get" class="form-inline flexitem">
					<button type="submit" class="btn btn-outline-primary btn-sm menu-button">パスワード変更</button>
				</form>
				<form  action="user-message" method="get" class="form-inline flexitem">
					<button type="submit" class="btn btn-outline-primary btn-sm menu-button">お問い合わせ</button>
				</form>
				<form action="user-logout" method="post" class="form-inline flexitem">
					<button type="submit" class="btn btn-outline-danger btn-sm menu-button">ログアウト</button>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</div>