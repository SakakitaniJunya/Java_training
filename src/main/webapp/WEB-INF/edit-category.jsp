<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>カテゴリ編集</title>
		 <%@ include file="header.jsp" %>
	</head>
	<body>
		<%@ include file="operator-navbar.jsp" %>
		<div class="col-12">
			<h4 class="title">カテゴリ編集</h4>
			<c:forEach items="${ errorMessageList }" var="errorMessage">
				<div class="error-message">
					<c:out value="${ errorMessage }" />
				</div>
			</c:forEach>
			<br>
			<form action="edit-category" method="post">
				<input type="hidden" name="categoryId" value="${ categoryDto.categoryId }"/>
				<input type="hidden" name="updateNumber" value="${ categoryDto.updateNumber }"/>
				<div class="form-group row">
					<label class="col-sm-2 head">カテゴリ</label>
					<div class="col-8">
						<textarea name="category" style="resize: none;" class="form-control" maxlength="30" rows="1"><c:out value="${ categoryDto.category }" /></textarea>
					</div>
				</div>
				<div class="form-group row">
					<div class="mx-auto">
						<button type="reset" class="btn btn-secondary">リセット</button>
						<button type="submit" formaction="edit-category" class="btn btn-primary">更新</button>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>