<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp" %>

<div id="main_img">
	<img src="images/main_img.jpg" style="border-radius:20px 20px 20px 20px;border:2px solid white;">
</div>
<div id="front">
	<h2>New Items</h2>
	<div id="bestProduct">
		<c:forEach items="${newProductList}" var="productVO">
			<div id="item">
				<a href="productDetail?pseq=${productVO.PSEQ}">
					<img src="/product_images/${productVO.IMAGE}">
					<h3>${productVO.NAME}-<fmt:formatNumber type="currency" value="${productVO.PRICE2}"/></h3>
				</a>
			</div>
		</c:forEach>
	</div>
</div>
<div class="clear"></div>
<div id="front">
	<h2>Best Items</h2>
	<div id="bestProduct">
		<c:forEach items="${bestProductList}" var="productVO">
			<div id="item">
				<a href="productDetail?pseq=${productVO.PSEQ}">
					<img src="/product_images/${productVO.IMAGE}">
					<h3>${productVO.NAME}-<fmt:formatNumber type="currency" value="${productVO.PRICE2}"/></h3>
				</a>
			</div>
		</c:forEach>
	</div>
</div>
<div class="clear"></div>

<%@ include file="include/footer.jsp" %>