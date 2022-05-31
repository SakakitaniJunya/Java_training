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
		return window.confirm("法人アカウント情報を本当に更新しますか？");
	};
	function changepasswordConfirm() {
		return window.confirm("パスワードを本当に初期化しますか？");
	};
	
	function deleteConfirm() {
		return window.confirm("本当に削除しますか？");
	};
	function turnBack(num) {
		$(".text" + num).show();
		$(".input" + num).hide();
		
		$(".position" + num).attr("disabled", true);
		
		$("#editMode" + num).show();
		$("#turnBack" + num).hide();
		
		
		$("#delete-button" + num).show();
		$("#edit-button" + num).hide();
	};
	function editUser(num) {
		$(".text" + num).hide();
		$(".input" + num).show();
		
		$(".position" + num).attr("disabled", false);
		
		$("#editMode" + num).hide();
		$("#turnBack" + num).show();
		
		
		$("#delete-button" + num).hide();
		$("#edit-button" + num).show();
	};
//-->
</script>

</head>
<body>
　<%@ include file="operator-navbar.jsp" %>
	<div class="col-12">
		<h4 class="title">法人アカウント一覧</h4>
   		<div class="message">
   			<c:out value="${ message }" />
		</div>
		<c:forEach items="${ errorMessageList }" var="errorMessage">
			<div class="error-message">
				<c:out value="${ errorMessage }" />
			</div>
		</c:forEach>
		<table class="table table-border">
			<tr>
	   			<th>メールアドレス</th>
	   			<th>姓</th>
	   			<th>名</th>
	   			<th></th>
	   		</tr>
	   		<tr>
	   			<form action="add-corporator" method="post">
	   				<input type="hidden" name="corporationId" value="${ corporationId }">
		   			<td><input type="text" name="email" maxlength="200" class="form-control" placeholder="メールアドレスを入力してください"></td>
		   			<td><input type="text" name="lastName" maxlength="30" class="form-control" placeholder="姓を入力してください"></td>
		   			<td><input type="text" name="firstName" maxlength="30" class="form-control" placeholder="名を入力してください"><br></td>
		   			<c:choose>
			   			<c:when test="${ operator.authority == 2 }">
			   				<td><input type="submit" class="btn btn-primary btn-sm" value="登録" disabled></td>
			   			</c:when>
			   			<c:otherwise>
			   				<td><input type="submit" class="btn btn-primary btn-sm" value="登録"></td>
			   			</c:otherwise>
		   			</c:choose>
		   			
	   			</form>
	   		</tr>
	   		<c:forEach items="${ corporatorList }" var="corporatorDto" varStatus="status">
				<form action="edit-corporator" method="post">
					<tr>
						<td class="text${ status.count }"  ><c:out value="${ corporatorDto.email }"></c:out></td>
		   				<td class="text${ status.count }" ><c:out value="${ corporatorDto.lastName }"></c:out></td>
		   				<td class="text${ status.count }" ><c:out value="${ corporatorDto.firstName }"></c:out></td>
		   				<td class="input${ status.count }" style="display:none"><input type="text" name="email" class="form-control" value="${ corporatorDto.email }" maxlength="200" /></td>
						<td class="input${ status.count }" style="display:none"><input type="text" name="lastName" class="form-control" value="${ corporatorDto.lastName }" maxlength="30"/></td>
						<td class="input${ status.count }" style="display:none"><input type="text" name="firstName" class="form-control" value="${ corporatorDto.firstName }" maxlength="30"/></td>
	   				<c:choose>
				   		<c:when test="${ corporatorDto.resetFlg && operator.authority != 2}">
					   			<td>
						   			<input type="hidden" name="corporatorId" value="${ corporatorDto.corporatorId }">
				   					<input type="hidden" name="corporationId" value="${ corporationId }">
						   			<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})" disabled>
						   			<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">
				                                                                                                        
						   			<input type="submit" id="delete-button${ status.count }" class="btn btn-danger btn-sm" value="削除" formaction="delete-corporator?corporatorId=${ corporatorDto.corporatorId }" onclick="return deleteConfirm()" >
									<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">
						   			<input type="submit" class="btn btn-secondary btn-sm" value="初期化" formaction="reset-password-corporator" onclick="return changepasswordConfirm()" >			
						   			
						   		</td>
						 </c:when>
						 <c:when test="${ !corporatorDto.resetFlg && operator.authority != 2}">
						   		<td>
						   			<input type="hidden" name="corporatorId" value="${ corporatorDto.corporatorId }">
				   					<input type="hidden" name="corporationId" value="${ corporationId }">
						   			<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})" >
						   			<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">
				                                                                                                        
						   			<input type="submit" id="delete-button${ status.count }" class="btn btn-danger btn-sm" value="削除" formaction="delete-corporator?corporatorId=${ corporatorDto.corporatorId }" onclick="return deleteConfirm()" >
									<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">
						   			<input type="submit" class="btn btn-secondary btn-sm" value="初期化" formaction="reset-password-corporator" onclick="return changepasswordConfirm()" >			
						   			
						   		</td>
						  </c:when>	
						  <c:otherwise>
								<td>
						   			<input type="hidden" name="corporatorId" value="${ corporatorDto.corporatorId }">
				   					<input type="hidden" name="corporationId" value="${ corporationId }">
						   			<input type="button" id="editMode${status.count}" class="btn btn-warning btn-sm" value="編集" onclick="editUser(${status.count})" disabled>
						   			<input type="button" id="turnBack${ status.count }" class="btn btn-secondary btn-sm" value="戻す" onclick="turnBack(${ status.count })" style="display:none">
				                                                                                                        
						   			<input type="submit" id="delete-button${ status.count }" class="btn btn-danger btn-sm" value="削除" formaction="delete-corporator?corporatorId=${ corporatorDto.corporatorId }" onclick="return deleteConfirm()" disabled>
									<input type="submit" id="edit-button${ status.count }" class="btn btn-primary btn-sm" value="更新" onclick="return editConfirm()" style="display:none">
						   			<input type="submit" class="btn btn-secondary btn-sm" value="初期化" formaction="reset-password-corporator" onclick="return changepasswordConfirm()" disabled>			
						   			
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