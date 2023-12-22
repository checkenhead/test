<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID Check</title>

<link rel="stylesheet" type="text/css" href="/css/shopping.css">
<script type="text/javascript" src="/script/member.js"></script>

</head>
<body>
	
	<div id="wrap">
		<h1>ID 중복확인</h1>
		<form name="idCheckFrm" method="get" action="idCheckForm">
			User ID<input type="text" name="userid" value="${userid}">
			<input type="submit" class="submit" value="검색">
			<div style="margin-top:20px;">
				<c:if test="${result == 1}">
					${userid}는 이미 사용 중입니다.
					<script type="text/javascript">opener.document.joinFrm.userid.value = "";</script>
				</c:if>
				<c:if test="${result == -1}">
					${userid}는 사용 가능한 ID입니다.
					<input type="button" value="이 아이디 사용" class="cancel" onClick="id_ok('${userid}');">
				</c:if>
			</div>
		</form>
	</div>
	
</body>
</html>