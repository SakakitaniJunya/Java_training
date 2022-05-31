<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>コース学習</title>
     <%@ include file="header.jsp" %>
  </head>
  <body>
    <%@ include file="user-navbar.jsp" %>
    <div class="row">
	    <br>
	    <br>
	    <div class="container">
	    	<div class="col-md-9">
		    	<br>
		    	<h4 class="title"><c:out value="${ indexDto.course }" /></h4>
		    	<br>
		    	<div class="alert alert-light" role="alert" style="color:black;">
			    	<h5><c:out value="${ indexDto.indexs }" /></h5>
			    	<br>
			    	<p style="white-space:pre-wrap;width:800px;margin:10px;" ><c:out value="${ indexDto.content }" /></p>
		    	<br>
		    	</div>
		   		<div class="form-group row">
					<div class="mx-auto">
					<form action="course-study" method="post">
						<c:if test="${ not empty user || courseDto.isFreeCourseFlg }">
							<a href="list-course" class="btn btn-secondary">コース一覧に戻る</a>
						</c:if>
						<c:choose>
						<c:when test="${ not empty user && not empty nextIndexDto }">
							<input type="hidden" name="indexId" value="${ indexDto.indexId }">
							<input type="hidden" name="nextIndexId" value="${ nextIndexDto.indexId }">
							<button type="submit" class="btn btn-primary">次へ</button>
						</c:when>
						<c:when test="${ not empty user && empty nextIndexDto }">
							<input type="hidden" name="indexId" value="${ indexDto.indexId }">
							<button type="submit" class="btn btn-primary">受講完了</button>
						</c:when>
						</c:choose>
						</form>
		   			</div>
		   		</div>
	   		</div>
	    </div>
	    <div class="col-md-3">
	    	<br>
	    	<h4 class="title">目次一覧</h4>
	    	<br>
    		<c:forEach items="${ indexList }" var="indexDto" varStatus="status">
    			<c:choose>
	    			<c:when test="${ not empty user || courseDto.isFreeCourseFlg }">
		    			<c:choose>	
			    			<c:when test="${ indexDto.lecturesFinished }"><span>✔</span></c:when>
			    			<c:otherwise>&nbsp;&nbsp;&nbsp;</c:otherwise>
		    			</c:choose>
		    			<a href="course-study?indexId=${ indexDto.indexId }" >
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
	    </div>
    </div>
  </body>
</html>