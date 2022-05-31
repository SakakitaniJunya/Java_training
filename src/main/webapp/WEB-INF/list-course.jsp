<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>コース一覧</title>
    <%@ include file="header.jsp" %>
  </head>
  <body>
  	<%@ include file="user-navbar.jsp" %>
    <div class="col-12">
    	<h4 class="title">コース一覧</h4>
    	<div class="message">
   			<c:out value="${ message }" />
		</div>
		<c:forEach items="${ errorMessageList }" var="errorMessage">
			<div class="error-message">
				<c:out value="${ errorMessage }" />
			</div>
		</c:forEach>
    	<div class="message" style="color:grey">ctrl+Fを使って、学びたいコースを検索してみましょう</div>
	    	<table class="table table=striped">
	    		<tr>
	    			<th style="width: 10%">カテゴリ</th>
	    			<th style="width: 15%">コース</th>
	    			<th style="width: 50%">目次</th>
	    			<th style="width: 10%">学習目安時間</th>
	    			<th style="width: 10%"><br></th>
	    			<c:if test="${ not empty user }">
						<th style="width: 10%">点数</th>
					</c:if>
	    		</tr>
	    		<c:forEach items="${ courseList }" var="courseDto">
		    		<tr>
		    			<td><a href="list-course?categoryId=${ courseDto.categoryId }"><c:out value="${ courseDto.category }" /></a></td>
		    			<td><a href="course-details?courseId=${ courseDto.courseId }"><c:out value="${ courseDto.course }" /></a></td>
		    			<td><c:out value="${ courseDto.indexs }" /></td>
		    			<td><c:out value="${ courseDto.estimatedStudyTime }" />時間</td>
		    			<c:choose>
			    			<c:when test="${ not empty user }">
			    				<td>
			    					<form action="problem-user" method="get">
			    						<input type="hidden" name="courseId" value="${ courseDto.courseId }">
			    						<input type="hidden" name="isFreeCourseFlg" value="${ courseDto.isFreeCourseFlg }">
			    						<button type="submit" class="btn btn-primary btn-sm mr-4">実践問題</button>
			    					</form>
			    				</td>
			    			</c:when>
	    					<c:when test="${ courseDto.isFreeCourseFlg }">
	    						<td>
			    					<form action="problem-user" method="get">
			    						<input type="hidden" name="courseId" value="${ courseDto.courseId }">
			    						<input type="hidden" name="isFreeCourseFlg" value="${ courseDto.isFreeCourseFlg }">
			    						<button type="submit" class="btn btn-primary btn-sm mr-4">実践問題</button>
			    					</form>
	    						</td>
	    					</c:when>
	    					<c:otherwise>
	    						<td>
			    					<form action="problem-user" method="get">
			    						<input type="hidden" name="courseId" value="${ courseDto.courseId }">
			    						<button type="submit" class="btn btn-primary btn-sm mr-4" disabled>実践問題</button>
			    					</form>
	    						</td>
	    					</c:otherwise>
			    		</c:choose>
			    		<c:if test="${ not empty user && courseDto.score != 0}">
		    				<td>${ courseDto.score }</td>
		    			</c:if>
		    			<c:if test="${ not empty user && courseDto.score == 0}">
		    				<td><br></td>
		    			</c:if>
		    		</tr>
	    		</c:forEach>
	    	</table>
    </div>
  </body>
</html>