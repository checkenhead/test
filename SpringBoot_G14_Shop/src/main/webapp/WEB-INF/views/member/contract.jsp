<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub01/sub_image_menu.jsp" %>

<article>
	<form name="contract" id="join" action="joinForm" method="post">
		언제나 새로운 즐거움이 가득한 Shoes Shop의 회원가입 페이지입니다.<br>
		Shoes Shop의 회원가입은 무료이며, 회원님의 개인정보는 '정보통신망이용촉진 및 정보보호 등에 관한 법률'에 의해 회원님의 동의없이 제 3자에게 제공하지 않으며,
		철저히 보호되고 있사오니 안심하고 이용하시기 바랍니다.<br><br>
		<textarea rows="15" cols="100"> <%@ include file="../include/contract.txt" contentType="charset=UTF-8" %> </textarea><br><br>
		<div style="text-align:center;">
			<input type="radio" name="okon">동의함&nbsp;&nbsp;&nbsp;<input type="radio" name="okon" checked>동의안함
		</div>
		<input type="button" class="submit" style="float:left;" value="다음" onClick="go_next();">
	</form>
</article>

<%@ include file="../include/footer.jsp" %>