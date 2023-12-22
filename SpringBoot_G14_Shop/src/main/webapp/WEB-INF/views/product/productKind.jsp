<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub02/sub_image_menu.jsp" %>

<article>
	
	<h2>Items</h2>
	<c:forEach items="${productKindList}" var="productVO">
		<div id="item">
			<a href="productDetail?pseq=${productVO.PSEQ}">
				<img src="/product_images/${productVO.IMAGE}">
				<h3>${productVO.NAME}</h3><p><fmt:formatNumber type="currency" value="${productVO.PRICE2}"/></p>
			</a>
		</div>
	</c:forEach>
	<div class="clear"></div>
</article>

<%@ include file="../include/footer.jsp" %>