<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>コース作成</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<%@ include file="operator-navbar.jsp" %>
		<div class="col-12">
			<h4 class="title">コース作成</h4>
			<br>
			<form action="create-course" method="post">
				<div class="form-group row">
					<label class="col-form-label col-2 head">カテゴリ</label>
					<div class="col-sm-8">
						<select name="categoryId" class="form-control">
							<c:forEach items="${ categoryList }" var="categoryDto" varStatus="status">
								<option value="${ categoryDto.categoryId }"><c:out value="${ categoryDto.category }"/></option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-form-label col-2 head">コース</label>
					<div class="col-8">
						<textarea name="course" style="resize: none;" class="form-control" maxlength="30" rows="1"><c:out value="${ courseDto.course }" /></textarea>
					</div>
				</div>
				<br>
				<div class="form-group row">
					<label class="col-form-label col-2 head">学習目安時間</label>
					<div class="col-8">
						<input type="number" name="estimatedStudyTime" style="resize: none;" class="form-control" ><c:out value="${ courseDto.estimatedStudyTime }" />
					</div>
				</div>
				<br>
				<div class="form-group row">
					<label class="col-form-label col-2 head">コース概要</label>
					<div class="col-8">
						<textarea name="courseOverview" style="resize: none;" class="form-control" maxlength="65535" rows="4"><c:out value="${ courseDto.courseOverview }" /></textarea>
					</div>
				</div>
				<br>
				<div class="form-group row">
					<label class="col-form-label col-2 head">前提条件</label>
					<div class="col-8">
						<textarea name="prerequisite" style="resize: none;" class="form-control" maxlength="255" rows="4"><c:out value="${ courseDto.prerequisite }" /></textarea>
					</div>
				</div>
				<br>
				<div class="form-group row">
					<label class="col-form-label col-2 head">ゴール</label>
					<div class="col-8">
						<textarea name="goal" style="resize: none;" class="form-control" maxlength="65535" rows="4"><c:out value="${ courseDto.goal }" /></textarea>
					</div>
				</div>
				<div class="form-group row">
					<div class="mx-auto">
						<button type="submit" class="btn btn-primary">作成</button>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>