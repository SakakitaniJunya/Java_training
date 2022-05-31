<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>テキスト編集</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<%@ include file="operator-navbar.jsp" %>
		<div class="col-12">
			<h4 class="title">テキスト編集</h4>
			<c:forEach items="${ errorMessageList }" var="errorMessage">
				<div class="error-message">
					<c:out value="${ errorMessage }" />
				</div>
			</c:forEach>
			<br>
			<form action="edit-text" method="post">
				<input type="hidden" name="indexId" value="${ indexDto.indexId }"/>
				<input type="hidden" name="updateNumber" value="${ indexDto.updateNumber }"/>
				<div class="form-group row">
					<label class="col-form-label col-2 head">コース名</label>
					<div class="col-sm-8">
						<input type="hidden" name="courseId" value="${ indexDto.courseId }"/>
						<input type="hidden" name="course" value="${ indexDto.course }"/>
						<div class="form-control">
							<c:out value="${ indexDto.course }"/>
						</div>
					</div>
				</div>
				<br>
				<div class="form-group row">
					<label class="col-form-label col-2 head">目次名</label>
					<div class="col-8">
						<textarea name="indexs" style="resize: none;" class="form-control" maxlength="30" rows="1"><c:out value="${ indexDto.indexs }" /></textarea>
					</div>
				</div>
				<br>
				<div class="form-group row">
					<label class="col-form-label col-2 head">テキスト内容</label>
					<div class="col-8">
						<textarea name="content" style="resize: none;" class="form-control" maxlength="65535" rows="15"><c:out value="${ indexDto.content }" /></textarea>
					</div>
				</div>								
				<div class="form-group row">
					<div class="mx-auto">
						<button type="reset" class="btn btn-secondary">リセット</button>
						<button type="submit" formaction="edit-text" class="btn btn-primary">更新</button>
					</div>
				</div>				
			</form>			
		</div>
	</body>
</html>