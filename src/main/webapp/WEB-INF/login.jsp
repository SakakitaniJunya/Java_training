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
		<img id="logo" src="img/CodeTrain.png" height="90" width="150" />
	</div>
</div>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ログインページ</title>
    <%@ include file="header.jsp"%>
  </head>
  <body>
  	<h4 class="title">ログイン画面</h4>
		<div class="error-message">
			<c:out value="${ message }" />
		</div>
  	<br>
	  	<form action="login" method="post">
		  	<div class="form-group row">
				<label class="col-form-label col-2 head">ユーザ名</label>
				<div class="col-8">
					<input type="text" name="id" class="form-control" placeholder="メールアドレス">
				</div>
			</div>
			<br>
		  	<div class="form-group row">
				<label class="col-form-label col-2 head">パスワード</label>
				<div class="col-8">
					<input type="password" name="password" class="form-control" placeholder="パスワード">
				</div>
			</div>
			<br>
			<div class="form-group row">
				<div class="mx-auto">
					<button type="submit" class="btn btn-outline-primary menu-button">ログイン</button>
				</div>
			</div>
	    </form>
  </body>
</html>