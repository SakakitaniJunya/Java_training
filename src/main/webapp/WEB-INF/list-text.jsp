<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>テキスト一覧</title>
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
    		<h4 class="title">テキスト一覧</h4>
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
					<form action="create-text" method="get" class="form-inline flexitem">
						<input type="hidden" name="courseId" value="${ courseId }"/>
						<c:choose>
							<c:when test="${ operator.authority == 1 }">
								<button type="submit" class="btn btn-primary btn-sm mr-4" disabled>テキスト作成</button>
							</c:when>
							<c:otherwise>
								<button type="submit" class="btn btn-primary btn-sm mr-4">テキスト作成</button>
							</c:otherwise>
						</c:choose>
	    			</form>
				</div>
			</div> 
			<br>
			<c:if test="${ not empty indexList }">
	    	<table class="table table=striped">
    			<tr>
    				<th>目次名</th>
    				<th>内容</th>
    				<th><br></th>
    				<th><br></th>
    			</tr>
    			<c:forEach items="${ indexList }" var="indexDto" varStatus="status">
	    		
		    		
		    		<tr>
		    			<td><c:out value="${ indexDto.indexs }" /></td>
		    			<td><c:out value="${ indexDto.content }" /></td>
		    			<td>
		    				<form action="edit-text" method="get" class="form-inline flexitem">
		    					<input type="hidden" name="indexId" value="${ indexDto.indexId }">
		    					<input type="hidden" name="courseId" value="${ courseId }">
		    					<input type="hidden" name="indexs" value="${ indexDto.indexs }">
		    					<input type="hidden" name="content" value="${ indexDto.content }">
		    					<input type="hidden" name="updateNumber" value="${ indexDto.updateNumber }"/>
		    					<c:choose>
									<c:when test="${ operator.authority == 1 }">
										<button type="submit" class="btn btn-warning btn-sm mr-4" style="background-color:#34eb56" disabled>編集</button>
									</c:when>
									<c:otherwise>
										<button type="submit" class="btn btn-warning btn-sm mr-4">編集</button>
									</c:otherwise>
								</c:choose>
		    				</form>
		    			</td>
		    			<td>
		    				<form action="delete-text" method="post" class="form-inline flexitem">
		    					<input type="hidden" name="indexId" value="${ indexDto.indexId }">
		    					<input type="hidden" name="courseId" value="${ courseId }">	    
		    					<input type="hidden" name="updateNumber" value="${ indexDto.updateNumber }"/>
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
    		</c:if>		   			 	
		</div>
	</body>
</html>