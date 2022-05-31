	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザ一覧</title>
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
   　<%@ include file="corporator-navbar.jsp" %>
   　<!-- 完成したら直すこと  -->
	<div class="col-12">
   		<h4 class="title">ユーザ一覧</h4>
   		<div class="message">
   			<c:out value="${ message }" />
		</div>
		<c:forEach items="${ errorMessageList }" var="errorMessage">
			<div class="error-message">
				<c:out value="${ errorMessage }" />
			</div>
		</c:forEach>
   		<div class="form-group row">
			<div class="mx-auto">
	   			<form action="user-progress" method="get">
	   				<input type="hidden" name="corporationId" value="${ corporator.corporationId }">
	  				<input type="submit" class="btn btn-secondary btn-sm" value="学習履歴一覧">
				   	<input type="submit" class="btn btn-secondary btn-sm" value="請求確認" formaction="expense-claim">
	   			</form>
   			</div>
   		</div>
   		
   		<table class="table table-striped">
   			<tr>
	   			<th>メールアドレス</th>
	   			<th>姓</th>
	   			<th>名</th>
	   			<th>状態</th>
	   			<th></th>
	   			<th>パスワード</th>
	   		</tr>
	   		
	   		<form action="add-user" method="post">
	   			<tr>
		   			<td><input type="text" name="mail" maxlength="255" class="form-control" placeholder="メールアドレスを入力してください"></td>
		   			<td><input type="text" name="lastName" maxlength="30" class="form-control" placeholder="姓を入力してください"></td>
		   			<td><input type="text" name="firstName" maxlength="30" class="form-control" placeholder="名を入力してください"></td>
		   			<td style="color: green">活動</td>
		   			<td><button type="submit" class="btn btn-primary btn-sm">登録</button></td>	
		   			<td></td>
	   			</tr>
	   		</form>	   		
	   		<c:forEach items="${ userList }" var="userDto" varStatus="status">
	   			<form action="edit-user" method="post">
	   				<input type="hidden" name="userId" value="${ userDto.userId }">
	   				<tr>
		   				<td class="text${ status.count }"><c:out value="${ userDto.email }" /></td>
		   				<td class="text${ status.count }"><c:out value="${ userDto.lastName }" /></td>
		   				<td class="text${ status.count }"><c:out value="${ userDto.firstName }" /></td>
		   				
		   				<td class="input${ status.count }" style="display:none"><input type="text" name="email" class="form-control" value="${ userDto.email }" maxlength="255" /></td>
						<td class="input${ status.count }" style="display:none"><input type="text" name="lastName" class="form-control" value="${ userDto.lastName }" maxlength="30" /></td>
						<td class="input${ status.count }" style="display:none"><input type="text" name="firstName" class="form-control" value="${ userDto.firstName }" maxlength="30" /></td>
						<c:choose>
						<c:when test="${ not empty userDto.deleteAt }">
								<td class="text${ status.count }" style="color: blue"><c:out value="削除予定" /></td>
								<td class="input${ status.count }" style="display:none">
								<input  type="radio" name="activity" value="0" />
								<label>活動</label>
								<br>
								<input type="radio" name="activity" value="2" />
								<label for="deletly">休止予定</label>
								<br>
								<input type="radio" name="activity" value="3" checked="checked" />
								<label for="pausely" >削除予定</label>
								</td>
						</c:when>
						<c:when test="${ not empty userDto.pauseAt && dateObj <= userDto.pauseAt }">
								<td class="text${ status.count }" style="color: blue"><c:out value="休止予定" /></td>
								<td class="input${ status.count }" style="display:none">
								<input type="radio" name="activity" value="0" />
								<label>活動</label>
								<br>
								<input type="radio" name="activity" value="2" checked="checked" />
								<label>休止予定</label>
								<br>
								<input type="radio" name="activity" value="3" />
								<label>削除予定</label>
								</td>
						</c:when>
						<c:when test="${ dateObj >= userDto.pauseAt }">
								<td class="text${ status.count }" style="color: red"><c:out value="休止" /></td>
								<td class="input${ status.count }" style="display:none">
								<input type="radio"  name="activity" value="2" checked="checked" />
								<label for="active">休止</label>
								<br>
								<input type="radio" name="activity" value="0" />
								<label for="deletly">活動</label>
								<br>
								<input type="radio" name="activity" value="3" />
								<label for="pausely" >削除予定</label>
								</td>
						</c:when>
						<c:otherwise>
							<td class="text${ status.count }" style="color: green"><c:out value="活動" /></td>
								<td class="input${ status.count }" style="display:none">
								<input type="radio"  name="activity" value="0" checked="checked" />
								<label for="active">活動</label>
								<br>
								<input type="radio" name="activity" value="2" />
								<label for="deletly">休止予定</label>
								<br>
								<input type="radio" name="activity" value="3" />
								<label for="pausely" >削除予定</label>
							</td>
						</c:otherwise>
						</c:choose>
			   				<c:choose>
			   				
			   					<c:when test="${ userDto.resetFlg }">
				   					<td>
					   					<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})" disabled>
					   					<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">
					   				</td>
					   				<td>                                                                                                        
					   					<input type="submit" id="delete-button${ status.count }" class="btn btn-secondary btn-sm" value="初期化" formaction="reset-password-user" onclick="return changepasswordConfirm()" >
										<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">
					   				</td>
					   			</c:when>
					   			<c:otherwise>
					   				<td>
					   					<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})">
					   					<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">
					   				</td>
					   				<td>                                                                                                        
					   					<input type="submit" id="delete-button${ status.count }" class="btn btn-secondary btn-sm" value="初期化" formaction="reset-password-user" onclick="return changepasswordConfirm()">
										<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">
					   				</td>
					   			</c:otherwise>	
					   		</c:choose>		
	   				</tr>
	   			</form>
	   		</c:forEach>
   		</table>
   </div>
</body>
</html>