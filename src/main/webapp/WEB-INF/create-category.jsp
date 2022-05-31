<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>カテゴリ作成</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<%@ include file="operator-navbar.jsp" %>
		<div class="col-12">
			<h4 class="title">カテゴリ作成</h4>
			<br>
			<form action="create-category" method="post">
				<div class="form-group row">
					<label class="col-form-label col-2 head">カテゴリ</label>
					<div class="col-8">
						<textarea name="category" style="resize: none;" class="form-control" maxlength="30" rows="1"><c:out value="${ categoryDto.category }" /></textarea>
					</div>
				</div>
				<br>
				<div class="form-group row">
					<div class="mx-auto">
						<button type="submit" class="btn btn-primary">作成</button>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>