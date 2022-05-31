<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>学習履歴一覧</title>
		 <%@ include file="header.jsp" %>
		  <script type="text/javascript">
	<!--
	function firstMatch(src, words) {
	    let min_index = src.length;
	    let return_found;
	    for (let i in words) {
	        let regExp = new RegExp(words[i], 'i');
	        let found = src.match(regExp);
	        if (found != null) {
	            if (min_index > found.index) {
	                min_index = found.index;
	                return_found = found;
	            }
	        }
	    }
	    return return_found;
	}

	window.onload = function() {
	    let ids = ['name', 'category', 'course', 'indexs'];
	    for (let i in ids) {
	        for (j = 0; j < document.getElementsByClassName(ids[i]).length; j++) {
	            let src = document.getElementById(ids[i] + j).innerHTML;
	            let words = document.getElementById('keyword').value.split(' ');
	            let dest = "";
	            let found = firstMatch(src, words);
	            while (found != null) {
	                dest += src.slice(0, found.index) + "<span class='highlight'>" + src.slice(found.index, found.index + found[0].length) + "</span>";
	                src = src.slice(found.index + found[0].length);
	                found = firstMatch(src, words);
	            }
	            document.getElementById(ids[i] + j).innerHTML = dest + src;
	        }
	    }
	};
		
	// -->
</script>
	</head>
	<body>
		<%@ include file="corporator-navbar.jsp" %>
				<div class="col-12">
    		<h4 class="title">学習履歴一覧</h4>
    		<div class="message">
    			<c:out value="${ message }" />
    		</div>
    	</div>
	
		<%-- 検索機能 --%>
		<div style="text-align:center">
		<form action="search-user-progress" method="post">
			<c:choose>
				<c:when test="${ not empty keyword }">
					<input id="keyword" type="text" name="keyword" style="width:500px; height:30px;" value="${ keyword }" autofocus>
				</c:when>
				<c:otherwise>
					<input type="text" style="width:500px; height:30px;" name="keyword" placeholder="検索対象：名前、カテゴリ、コース、学習履歴" autofocus>
						</c:otherwise>
			</c:choose>
			
			
			
			<input type="submit" value="検索" class="btn btn-primary">
		</form>
		</div>
		
		<%-- 学習履歴機能 --%>
		<div class="container">
		<table class="table table-striped">
			<tr>
				<th>時間</th>
				<th>名前</th>
				<th>カテゴリ</th>
				<th>コース</th>
				<th>学習履歴</th>
			</tr>
			<c:forEach items="${ progressList }" var="progressDto" varStatus="status">
				<tr>
					<td><c:out value="${ progressDto.lecturesFinishTime }"/></td>
					<td id="name${status.index}"  class="name"><c:out value="${ progressDto.lastName } ${ progressDto.firstName }"/></td>
					<td id="category${status.index}"  class="category"><c:out value="${ progressDto.category }"/></td>
					<td id="course${status.index}"  class="course"><c:out value="${ progressDto.course }"/></td>
					<td id="indexs${status.index}"  class="indexs"><c:out value="${ progressDto.indexs }"/>を受講完了</td>
				</tr>
			</c:forEach>
		</table>
		</div>
	</body>
</html>