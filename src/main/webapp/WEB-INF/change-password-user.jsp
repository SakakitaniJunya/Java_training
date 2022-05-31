<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>パスワード変更</title>
    <%@ include file="header.jsp"%>
</head>
<body>
  <%@ include file="user-navbar.jsp" %>
	 <div class="col-12">
		<h4 class="title">パスワード変更</h4>
		<c:forEach items="${ errorMessaList }" var="errorMessage">
				<div class="error-message">
			<c:out value="${ errorMessage }" />
				</div>
		</c:forEach>
		<div class="message"><c:out value="${ message }" /></div>
		<br>
		<form action="change-user-password" method="post">
			
				<c:choose>
					<c:when test="${ isChangeRequired }">
							<div class="form-group row">
						<label class="col-form-label col-2 head">新しいパスワード</label>
						<div class="col-8">
							<input type="password" name="newPassword" class="form-control" maxlength="30" placeholder="新しいパスワードを入力してください">
						</div>
					</div>
					<div class="form-group row">
						<label class="col-form-label col-2 head">パスワード(確認用)</label>
						<div class="col-8">
							<input type="password" name="newPassword2" class="form-control" maxlength="30" placeholder="もう一度入力してください">
						</div>
					</div>
					<div class="form-group row">
						<div class="mx-auto">
							<input type="hidden" name="uri" value="${ requestScope.uri }">
							<button type="submit" formaction="reset-new-password-user" class="btn btn-primary">登録</button>
						</div>
					</div>
					</c:when>
					<c:otherwise>
						<div class="form-group row">
						<label class="col-form-label col-2 head">現在のパスワード</label>
							<div class="col-8">
								<input type="password" name="oldPassword" class="form-control" maxlength="30" placeholder="現在パスワードを入力してください">
							</div>	
					</div>
					<div class="form-group row">
						<label class="col-form-label col-2 head">新しいパスワード</label>
						<div class="col-8">
							<input type="password" name="newPassword" class="form-control" maxlength="30" placeholder="新しいパスワードを入力してください">
						</div>
					</div>
					<div class="form-group row">
						<label class="col-form-label col-2 head">パスワード(確認用)</label>
						<div class="col-8">
							<input type="password" name="newPassword2" class="form-control" maxlength="30" placeholder="もう一度入力してください">
						</div>
					</div>
					<div class="form-group row">
						<div class="mx-auto">
							<input type="hidden" name="uri" value="${ requestScope.uri }">
							<button type="submit" class="btn btn-primary">登録</button>
						</div>
						</div>
					</c:otherwise>
				</c:choose>
			
		</form>
	</div>



	
</body>
</html>