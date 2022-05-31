<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>実践問題作成/編集</title>
		<%@ include file="header.jsp" %>
		<script type="text/javascript">
		<!--
		// テキストエリアの高さ自動調整
		// -->
		</script>
	</head>
	<body>
		<%@ include file="operator-navbar.jsp" %>
		
	    	<div class="container">
	    		<div class="col-12">
		    		<c:choose>
						<c:when test="${ createFlg }">
							<h4 class="title">実践問題作成</h4>
						</c:when>
						<c:otherwise>
							<h4 class="title">実践問題編集</h4>
						</c:otherwise>
					</c:choose>
					<c:forEach items="${ errorMessageList }" var="errorMessage">
						<div class="error-message">
							<c:out value="${ errorMessage }" />
						</div>
					</c:forEach>
					<form action="edit-problem" method="post">
						<input type="hidden" name="courseId" value="${ courseId }">
						<c:forEach items="${ problemList }" var="problemDto" varStatus="status">
							<h5 class="title" style="color:black">問${ status.count }</h5>
							<label class="col-md-1">問題文</label>
							<div class="row">
								<div class="col-md-11">
									<input type="hidden" name="problemId${ status.count }" value="${ problemDto.problemId }">
									<input type="hidden" name="updateNumber${ status.count }" value="${ problemDto.updateNumber }">
									<input type="text" name="problemStatement${ status.count }" class="form-control" value="${ problemDto.problemStatement }" maxlength="100"/>
								</div>
							</div>
							<br>
							<label class="col-md-1">問題</label>
							<div class="row">
								<div class="col-md-11">
									<textarea name="problem${ status.count }" class="form-control" rows="10" maxlength="500">${ problemDto.problem }</textarea>
								</div>
							</div>
							<br>
							<label class="col-md-1">選択肢</label>
							<c:forEach items="${ problemDto.selectionResultList }" var="selectionResultDto" varStatus="selectionStatus">
								<div class="row">
									<input type="hidden" name="selectionResultId${ status.count }${ selectionStatus.count }" value="${ selectionResultDto.selectionResultId }">
									<c:if test="${ createFlg }">
										<c:choose>
											<c:when test="${ problemDto.correctSelectId == selectionStatus.count }">
												<label><input type="radio" name="correctSelection${ status.count }" value="${ selectionStatus.count }" checked="checked">${ selectionStatus.count }</label>
											</c:when>
											<c:otherwise>
												<label><input type="radio" name="correctSelection${ status.count }" value="${ selectionStatus.count }">${ selectionStatus.count }</label>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${ !createFlg }">
										<c:choose>
											<c:when test="${ selectionResultDto.selectionResultId == problemDto.correctSelectId }">
												<input type="hidden" name="correctSelectIdNumber" value="${ selectionStatus.count }">
												<label><input type="radio" name="correctSelection${ status.count }" value="${ selectionResultDto.selectionResultId }" checked="checked">${ selectionStatus.count }</label>
											</c:when>
											<c:otherwise>
												<label><input type="radio" name="correctSelection${ status.count }" value="${ selectionStatus.count }">${ selectionStatus.count }</label>
											</c:otherwise>
										</c:choose>
									</c:if>
									<div class="col-md-11">
										<input type="text" name="selection${ status.count }${ selectionStatus.count }" class="form-control" value="${ selectionResultDto.selection }" maxlength="100">
									</div>
								</div>
							</c:forEach>
							<br>
							<label class="col-md-1">結果</label>
							<c:forEach items="${ problemDto.selectionResultList }" var="selectionResultDto" varStatus="resultStatus">
									<div class="row">
										<label>${ resultStatus.count }</label>
										<div class="col-md-11">
											<textarea name="result${ status.count }${ resultStatus.count }" class="form-control" rows="5" maxlength="300">${ selectionResultDto.result }</textarea>
										</div>
									</div>
							</c:forEach>
							<br>
							<br>
							<br>
						</c:forEach>
						<div class="form-group row">
							<div class="mx-auto">
							<c:choose>
								<c:when test="${ createFlg }">
									<button type="submit" class="btn btn-primary mr-4">登録</button>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-primary mr-4">更新</button>
								</c:otherwise>
							</c:choose>
							</div>
						</div>
						<br>
						<br>
						<br>
					</form>
				</div>
			</div>
	</body>
</html>