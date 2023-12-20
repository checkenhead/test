<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub01/sub_image_menu.jsp" %>

<article>
	<form name="loginfrm" action="login" method="post">
		<fieldset>
			<legend>
				Login&nbsp;&nbsp;&nbsp;&nbsp;
				<span style="font-size:80%;font-weight:bold;">${message}</span>
			</legend>
			<label>User ID</label><input type="text" name="userid" value="${mdto.userid}"><br>
			<label>Password</label><input type="password" name="pwd">
			<div>
				<input type="submit" value="로그인" style="width:200px;">
				<input type="button" value="일반회원가입" onClick="location.href='contract'" style="width:200px;">
				<input type="button" value="아이디/비밀번호 찾기" onClick="" style="width:200px;">
				<hr>
				<a href="kakaostart"><img src="/images/kakao.png" style="width:300px;"></a>
				<img src="/images/naver.png" style="width:300px;">
				<img src="/images/google.png" style="width:300px;">
				<img src="/images/facebook.png" style="width:300px;">
			</div>
		</fieldset>
	</form>

</article>

<%@ include file="../include/footer.jsp" %>