<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>コース一覧</title>
    <%@ include file="header.jsp" %>
	<script type="text/javascript">
	<!--
	function deleteConfirm() {
		return window.confirm("コースを本当に削除しますか？");
	}
	// -->
	</script>
  </head>
  <body>
  	<%@ include file="operator-navbar.jsp" %>
    <div class="col-12">
    	<br>
    	<h4 class="title">コース一覧</h4>
    	<br>
    	<div class="message">
   			<c:out value="${ message }" />
		</div>
		<c:forEach items="${ errorMessageList }" var="errorMessage">
			<div class="error-message">
				<c:out value="${ errorMessage }" />
			</div>
		</c:forEach>
    	<br>
    	<div class="form-group row">
			<div class="mx-auto">
				<form action="create-category" method="get" class="form-inline flexitem">
				<c:choose>
					<c:when test="${ operator.authority == 1 }">
						<button type="submit" class="btn btn-primary btn-sm mr-4" style="background-color:#blue" disabled>カテゴリ作成</button>
		    			<button type="submit" class="btn btn-primary btn-sm mr-4" formaction="create-course" style="background-color:#blue" disabled>コース作成</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn btn-primary btn-sm mr-4" style="background-color:#blue">カテゴリ作成</button>
	    				<button type="submit" class="btn btn-primary btn-sm mr-4" formaction="create-course" style="background-color:#blue">コース作成</button>
					</c:otherwise>
				</c:choose>
	    		</form>
			</div>
		</div>
    	<br>
    	<table class="table table-border">
    		<tr>
    			<th style="width: 10%">カテゴリ</th>
    			<th style="width: 15%">コース</th>
    			<th style="width: 50%">目次</th>
    			<th style="width: 10%">学習目安時間</th>
    			<th style="width: 10%"><br></th>
    			<th style="width: 10%"><br></th>
    			<th style="width: 10%"><br></th>
    		</tr>
    		<c:forEach items="${ courseList }" var="courseDto">
	    		<tr>
	    			<td><a href="edit-category?categoryId=${ courseDto.categoryId }"><c:out value="${ courseDto.category }" /></a></td>
	    			<td><a href="edit-course?courseId=${ courseDto.courseId }"><c:out value="${ courseDto.course }" /></a></td>
	    			<td><c:out value="${ courseDto.indexs }" /></td>
	    			<td><c:out value="${ courseDto.estimatedStudyTime }" />時間</td>
	    			<td>
	    				<form action="list-text" method="get" class="form-inline flexitem">
	    					<input type="hidden" name="courseId" value="${ courseDto.courseId }"/>
							<button type="submit" class="btn btn-info btn-sm mr-4">テキスト一覧</button>
	    				</form>
	    			</td>
	    			<td>
	    				<form action="edit-problem" method="get" class="form-inline flexitem">
	    					<input type="hidden" name="courseId" value="${ courseDto.courseId }">
	    					<c:choose>
								<c:when test="${ operator.authority == 1 }">
									<button type="submit" class="btn btn-warning btn-sm mr-4" disabled>問題作成/編集</button>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-warning btn-sm mr-4">問題作成/編集</button>
								</c:otherwise>
							</c:choose>
	    				</form>
	    			</td>
	    			<td>
	    				<form action="delete-course" method="post" class="form-inline flexitem">
	    					<input type="hidden" name="courseId" value="${ courseDto.courseId }"/>	   
	    					<input type="hidden" name="categoryId" value="${ categoryId }"/>
	    					<input type="hidden" name="updateNumber" value="${ courseDto.updateNumber }"/>		    
	    					<c:choose>
								<c:when test="${ operator.authority == 1 }">
									<button type="submit" class="btn btn-danger btn-sm mr-4" onclick="return deleteConfirm()" disabled>削除</button>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-danger btn-sm mr-4" onclick="return deleteConfirm()">削除</button>
								</c:otherwise>
							</c:choose>					
	    				</form>
	    			</td>
	    		</tr>
    		</c:forEach>
    	</table>
    </div>
  </body>
</html>