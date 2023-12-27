<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/sub04/sub_image_menu.jsp"%>

<article>
	<h2>1:1 고객 게시판</h2>${message}
	<h3>고객님의 질문에 대해서 운영자가 1:1 답변을 드립니다.</h3>
	<form name="qnaWriteFrm" method="post" action="qnaWrite">
		<fieldset>
			<legend>QnA Info</legend>
			<label>Title</label><input type="text" name="subject" size="60"><br>
			<label>Secret Mode</label><input type="checkbox" name="secret" value="Y" onChange="toggle_secret();">비밀글로하기
			<input type="password" name="pass" size="15" disabled><br>
			<label>Content</label>
			<textarea rows="8" cols="65" name="content"></textarea>
			<br>
		</fieldset>
		<div class="clear"></div>
		<div id="buttons" style="float: right">
			<input type="submit" value="글쓰기" class="submit">
			<input type="button" value="목록보기" class="cancel" onClick="location.href='qnaList'">
			<input type="button" value="쇼핑 계속하기" class="cancel" onclick="location.href='/'">
		</div>
	</form>
</article>

<%@ include file="../include/footer.jsp"%>