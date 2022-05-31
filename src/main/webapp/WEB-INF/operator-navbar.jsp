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
	<c:choose>
		<c:when test="${ not empty operator }">
			<a href="operator-message">
				<img id="logo" src="img/CodeTrain.png" height="90" width="150" />
			</a>
		</c:when>
		<c:otherwise>
			<img id="logo" src="img/CodeTrain.png" height="90" width="150" />
		</c:otherwise>
	</c:choose>
		<div id="nav-message" class="right-item error-message" style="margin-top:2.2%">
			<c:out value="${ navbarMessage }" />
		</div>
		<c:if test="${ not empty operator.lastName}">
			<div style="margin-top:2.2%">
				<c:out value="${ operator.lastName += ' ' += operator.firstName += 'さん、こんにちは　' }" />
			</div>
		</c:if>
		

		<c:if test="${ not empty operator}">
			<form action="change-operator-password" method="get" class="form-inline flexitem">
				<button type="submit" class="btn btn-outline-primary btn-sm menu-button">パスワード変更</button>
			</form>
			<br>
			<c:if test="${ operator.authority == 0 }">
				<form action="operator-list" method="get" class="form-inline flexitem">
					<button type="submit" class="btn btn-outline-primary btn-sm menu-button">運営管理者一覧</button>
				</form>
			</c:if>
			<form action="list-corporation" method="get" class="form-inline flexitem">
				<button type="submit" class="btn btn-outline-primary btn-sm menu-button">法人一覧</button>
			</form>
			<form action="list-course-operator" method="get" class="form-inline flexitem">
				<button type="submit" class="btn btn-outline-primary btn-sm menu-button">コース一覧</button>
			</form>
			<form action="operator-message" method="get" class="form-inline flexitem">
				<button type="submit" class="btn btn-outline-primary btn-sm menu-button">お問い合わせ</button>
			</form>
			<form action="logout" method="post" class="form-inline flexitem">
				<button type="submit" class="btn btn-outline-danger btn-sm menu-button">ログアウト</button>
			</form>
		</c:if>
	</div>
</div>