<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>コース詳細</title>
    <%@ include file="header.jsp" %>
  </head>
  <body>
    <%@ include file="user-navbar.jsp" %>
    <div class="row">
	    <br>
	    <br>
	    <div class="col-md-9">
	    	<br>
	    	<h4 class="title">コース詳細</h4>
	    	<table class="table table=striped">
		    	<tr>
		    		<td>カテゴリ名</td>
		    		<td><c:out value="${ courseDto.category }" /><td>
		    	</tr>
		   		<tr>
		    		<td>コース名</td>
		    		<td><c:out value="${ courseDto.course }" /></td>
		    	</tr>
		   		<tr>
		    		<td>コース概要</td>
		    		<td><c:out value="${ courseDto.courseOverview }" /></td>
		    	</tr>
		   		<tr>
		    		<td>前提条件</td>
		    		<td><c:out value="${ courseDto.prerequisite }" /></td>
		    	</tr>	
		   		<tr>
		   			<td>ゴール</td>
		   			<td><c:out value="${ courseDto.goal }" /></td>
		   		</tr>
		   		<tr>
		   			<td>学習目安時間</td>
		   			<td><c:out value="${ courseDto.estimatedStudyTime }" />&nbsp;時間</td>
	   		</tr>
	   		</table>
	   		<div class="form-group row">
				<div class="mx-auto">
					<form action="problem-user" method="get">
						<c:if test="${ not empty user || courseDto.isFreeCourseFlg }">
							<a href="course-study?indexId=${ firstIndexDto.indexId }" class="btn btn-primary">最初から学習</a>
						</c:if>
						<c:choose>
						<c:when test="${ not empty user && not empty nextIndexDto }">
							<a href="course-study?indexId=${ nextIndexDto.indexId }" class="btn btn-warning">続きから学習</a>
						</c:when>
						<c:when test="${ not empty user && empty nextIndexDto }">
							<input type="hidden" name="courseId" value="${ courseDto.courseId }">
							<button type="submit" class="btn btn-warning">実践問題</button>
						</c:when>
						</c:choose>
					</form>
	   			</div>
	   		</div>
	    </div>
	    <div class="col-md-3">
	    	<br>
	    	<h4 class="title">目次一覧</h4>
	    	<br>
    		<c:forEach items="${ indexList }" var="indexDto">
    			<c:choose>
	    			<c:when test="${ not empty user || courseDto.isFreeCourseFlg }">
			    			<c:choose>
				    			<c:when test="${ indexDto.lecturesFinished }"><span>✔</span></c:when>
				    			<c:otherwise>&nbsp;&nbsp;&nbsp;</c:otherwise>
			    			</c:choose>
		    			<a href="course-study?indexId=${ indexDto.indexId }">
		    			<c:out value="${ indexDto.indexs }" />
	    				</a>
		    			<br>
		    		</c:when>
		    		<c:otherwise>
		    			<c:out value="${ indexDto.indexs }" />
		    			<br>
		    		</c:otherwise>
	    		</c:choose>
	    	</c:forEach>
		   	<c:if test="${ empty user && !courseDto.isFreeCourseFlg }">
		   		<br>
		    	<div class="message">有料コース　お問い合わせ先</div>
		    	<div class="message">kelonos@gmail.com</div>
		    </c:if>
	    </div>
    </div>
  </body>
</html>
