<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>実践問題</title>
		<%@ include file="header.jsp" %>
	</head>
	<body>
		<%@ include file="user-navbar.jsp" %>
		<div class="container">
			<div class="col-12">
				<h4 class="title">実践問題</h4>
					<div class="message">
		    			<c:out value="${ message }" />
		    		</div>
				<br>
				<h6>問題${ problemNumber + 1 }：<c:out value="${ problemDto.problemStatement }" /></h6>
				<form action="problem-user" method="post">
					<input type="hidden" name="problemNumber" value="${ problemNumber }" />
					<div class="row">
						<div class="col-sm-8">
							<pre style="background-color:#F2F2F2"><code><c:out value="${ problemDto.problem }" /></code></pre>
						</div>
					</div>
					<c:forEach items="${ problemDto.selectionResultList }" var="selectionResultDto" varStatus="status">
							<c:if test="${ selectionResultDto.selectionResultId == resultDto.selectedSelectionId  || selectionResultDto.selectionResultId == selection}">
								<c:choose>
									<c:when test="${ selectionResultDto.selectionResultId == problemDto.correctSelectId}">
										<div class="row">
											<div class="col-sm-8">
												<div class="alert alert-success" role="alert"><pre><code>結果：<c:out value="${ selectionResultDto.result }" /></code></pre></div>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="row">
											<div class="col-sm-8">
												<div class="alert alert-danger" role="alert"><pre><code>結果：<c:out value="${ selectionResultDto.result }"/></code></pre></div>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</c:if>
					</c:forEach>
					<c:forEach items="${ problemDto.selectionResultList }" var="selectionResultDto" varStatus="status">
						<div class="row">
							<div class="col-sm-8">
								<c:choose>
									<c:when test="${ selectionResultDto.selectionResultId == resultDto.selectedSelectionId || selectionResultDto.selectionResultId == selection}">
										<div class="radio">
											<input type="radio" name="selection" value="${ selectionResultDto.selectionResultId }" checked>
											<label for="selection${ status.count }"><c:out value="${ selectionResultDto.selection }" /></label>
										</div>
									</c:when>
									<c:otherwise>
										<div class="radio">
											<input type="radio" name="selection" value="${ selectionResultDto.selectionResultId }">
											<label for="selection${ status.count }"><c:out value="${ selectionResultDto.selection }"/></label>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:forEach>
					<div class="row">
						<div class="col-sm-8">
							<div class="form-group row">
								<div class="mx-auto">
									<a href="list-course" class="btn btn-secondary">コース一覧に戻る</a>
									<c:choose>
									<c:when test="${ (resultDto.selectedSelectionId == problemDto.correctSelectId || problemDto.correctSelectId == selection) && problemNumber != 9}">
										<input type="hidden" name="courseId" value="${ problemDto.courseId }">
										<button type="submit" formmethod="get" class="btn btn-primary">次へ</button>
									</c:when>
									<c:when test="${ (resultDto.selectedSelectionId == problemDto.correctSelectId || problemDto.correctSelectId == selection) && problemNumber == 9}">
										<input type="hidden" name="finishFlg" value="1">
										<button type="submit" formmethod="get" class="btn btn-primary">完了</button>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="courseId" value="${ problemDto.courseId }">
										<input type="hidden" name="problemId" value="${ problemDto.problemId }">
										<button type="submit" class="btn btn-primary">実行</button>
									</c:otherwise>
									</c:choose>
					   			</div>
					   		</div>
				   		</div>
			   		</div>
				</form>
			</div>
		</div>
	</body>
</html>