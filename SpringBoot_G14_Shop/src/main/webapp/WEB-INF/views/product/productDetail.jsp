<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub02/sub_image_menu.jsp" %>
<article>
	<h1> Item </h1>
	<div id="itemdetail">
		<form  method="post" name="productDetailFrm">
			<fieldset>
				<legend> Item detail Info</legend>
				<span style="float: left;">
					<img src="/product_images/${productVO.IMAGE}" style="border-radius:20px 20px 20px 20px;"/>
				</span>
       			<h2>${productVO.NAME}</h2>
				<label>가 격 :</label><p><fmt:formatNumber type="currency" value="${productVO.PRICE2}"/></p>
	   			<label>수 량 :</label><input  type="text" name="qty" size="2" value="1">
	   			<br>
	   			<input  type="hidden" name="pseq" value="${productVO.PSEQ}">
	   			<br>
			</fieldset> 
			<div class="clear"></div>
   			<div>
   				<h3 style="font-size:150%;">${productVO.CONTENT}</h3>
   			</div>
   			<br><br>
			<div class="clear"></div>
			<div id="buttons">
				<input type="button" value="장바구니에 담기" class="submit" onClick="go_cart()">
			    <input type="button" value="즉시 구매" class="submit" onClick="go_order()">
			    <input type="button" value="메인으로" class="submit" onClick="location.href='/'">
        	</div>
		</form>
	</div>
</article>

<%@ include file="../include/footer.jsp" %>