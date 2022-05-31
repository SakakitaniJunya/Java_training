<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>法人一覧</title>
<%@ include file="header.jsp"%>
 <script type="text/javascript">
<!--
	function editConfirm() {
		return window.confirm("ユーザ情報を本当に更新しますか？");
	};
	function changepasswordConfirm() {
		return window.confirm("ユーザパスワードを本当に初期化しますか？");
	};
	
	function resetConfirm() {
		return window.confirm("ユーザのパスワードを本当に初期化しますか？");
	};
	function turnBack(num) {
		$(".text" + num).show();
		$(".input" + num).hide();
		$("#editMode" + num).show();
		$("#turnBack" + num).hide();
		$("#delete-button" + num).show();
		$("#edit-button" + num).hide();
	};
	function editUser(num) {
		$(".text" + num).hide();
		$(".input" + num).show();
		$("#editMode" + num).hide();
		$("#turnBack" + num).show();
		$("#delete-button" + num).hide();
		$("#edit-button" + num).show();
	};
// -->
</script>
</head>
<body>
　<%@ include file="operator-navbar.jsp" %>
	<div class="col-12">
		<h4 class="title">法人一覧</h4>
   		<div class="message">
   			<c:out value="${ message }" />
		</div>
		<c:forEach items="${ errorMessageList }" var="errorMessage">
			<div class="error-message">
				<c:out value="${ errorMessage }" />
			</div>
		</c:forEach>
		<table class="table table-striped">
			<tr>
	   			<th>会社名</th>
	   			<th>ドメイン名</th>
	   			<th>請求先</th>
	   			<th></th>
	   			<th></th>
	   		</tr>
			<tr>
	   			<form action="add-corporation" method="post">
		   			<td><input type="text" name="companyName" maxlength="255" class="form-control" placeholder="会社名を入力してください"></td>
		   			<td><input type="text" name="domain" maxlength="255" class="form-control" placeholder="ドメイン名を入力してください"></td>
		   			<td><input type="text" name="billingAddress" maxlength="500" class="form-control" placeholder="郵便番号、住所、電話番号、部署、担当者を入力してください"><br></td>
		   			<c:choose>
			   			<c:when test="${ operator.authority == 2 }">
		   					<td><input type="submit" class="btn btn-primary btn-sm" value="登録" disabled></td>
		   				</c:when>
		   				<c:otherwise>
		   					<td><input type="submit" class="btn btn-primary btn-sm" value="登録"></td>
		   				</c:otherwise>
		   			</c:choose>
	   			</form>
	   			<td></td>
	   		</tr>
			<c:forEach items="${ corporationList }" var="corporationDto" varStatus="status">
				
				<form action="edit-corporation" method="post">
					<tr>
					<c:choose>
						<c:when test="${ not empty corporationDto.finishAt && corporationDto.finishAt <=  dateObj }">
							<td class="text${ status.count }"  style="color:#696969"><c:out value="${ corporationDto.companyName }"></c:out></td>
			   				<td class="text${ status.count }"  style="color:#696969"><c:out value="${ corporationDto.domain }"></c:out></td>
			   				<td class="text${ status.count }"  style="color:#696969"><c:out value="${ corporationDto.billingAddress }"></c:out></td>
			   				<td class="input${ status.count }" style="display:none"><input type="text" name="companyName" class="form-control" value="${ corporationDto.companyName }" maxlength="200" /></td>
							<td class="input${ status.count }" style="display:none"><input type="text" name="domain" class="form-control" value="${ corporationDto.domain }" maxlength="400"/></td>
							<td class="input${ status.count }" style="display:none"><input type="text" name="billingAddress" class="form-control" value="${ corporationDto.billingAddress}" maxlength="400"/></td>
			   				<input type="hidden" name="corporationId" value="${ corporationDto.corporationId }">
						</c:when>
						<c:otherwise>
							<td class="text${ status.count }"><a href="list-corporator?corporationId=${ corporationDto.corporationId }"><c:out value="${ corporationDto.companyName }"></c:out></a></td>
			   				<td class="text${ status.count }"><c:out value="${ corporationDto.domain }"></c:out></td>
			   				<td class="text${ status.count }"><c:out value="${ corporationDto.billingAddress }"></c:out></td>
			   				<td class="input${ status.count }" style="display:none"><input type="text" name="companyName" class="form-control" value="${ corporationDto.companyName }" maxlength="200" /></td>
							<td class="input${ status.count }" style="display:none"><input type="text" name="domain" class="form-control" value="${ corporationDto.domain }" maxlength="400"/></td>
							<td class="input${ status.count }" style="display:none"><input type="text" name="billingAddress" class="form-control" value="${ corporationDto.billingAddress}" maxlength="400"/></td>
			   				<input type="hidden" name="corporationId" value="${ corporationDto.corporationId }">
						</c:otherwise>
					</c:choose>				
				<c:choose>
					<c:when test="${ empty corporationDto.finishAt && corporationDto.startAt <=  dateObj }">
							<td class="text${ status.count }" style="color: blue"><c:out value="契約中" /></td>
							<td class="input${ status.count }" style="display:none">
							<input type="radio" name="position" value="0" checked="checked"/>
							<label>契約中</label>
							<br>
							<input type="radio" name="position" value="1" />
							<label>解約予定</label>
							</td>
						</c:when>
						<c:when test="${ empty corporationDto.finishAt && corporationDto.startAt >  dateObj }">
							<td class="text${ status.count }" style="color: green"><c:out value="開始予定" /></td>
							<td class="input${ status.count }" style="display:none">
							<input type="radio" name="position" value="3" checked="checked">
							<label>開始予定</label>
							<br>
							<input type="radio" name="position" value="2" >
							<label>解約</label>
							</td>
						</c:when>
						<c:when test="${ not empty corporationDto.finishAt && corporationDto.finishAt <=  dateObj }">
							<td class="text${ status.count }" style="color: red"><c:out value="解約済" /></td>
							<td class="input${ status.count }" style="display:none">
							<input type="radio" name="position" value="6" checked="checked" disabled>
							<label>解約済</label>
							</td>
						</c:when>
			
						<c:when test="${ not empty corporationDto.finishAt && corporationDto.finishAt > dateObj }">
							<td class="text${ status.count }" ><c:out value="解約予定" /></td>
							<td class="input${ status.count }" style="display:none">
							<input type="radio" name="position" value="5" checked="checked" >
							<label>解約予定</label>
							<br>
							<input type="radio" name="position" value="4" >
							<label>契約中</label>
							</td>
						</c:when>
						<c:otherwise>
							<h5>エラー</h5>
						</c:otherwise>
					</c:choose>	
			<td>
					<c:choose>
						<c:when test="${ not empty corporationDto.finishAt && corporationDto.finishAt <=  dateObj && operator.authority != 2 }">
	 						<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})" disabled>
							<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">		                                                                                                        
							<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">
						</c:when>
						<c:when test="${ (empty corporationDto.finishAt || corporationDto.finishAt >=  dateObj) && operator.authority != 2 }">
	 						<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})" >
							<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">		                                                                                                        
							<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">	
						</c:when>
						<c:otherwise>
							<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})" disabled>
							<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">		                                                                                                        
							<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">	
						</c:otherwise>
					</c:choose>	
			</td>
			</tr>
			</form>
			</c:forEach>
		</table>
		</div>
</body>
</html>