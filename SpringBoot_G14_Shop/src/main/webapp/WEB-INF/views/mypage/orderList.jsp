<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub03/sub_image_menu.jsp" %>

<article>
<h2> Order List </h2>
<form name="orderListFrm" method="post">
	<c:choose>
	<c:when test="${orderList.size() == 0}">
		<h3 style="color: red;text-align: center;">주문한 내역이 없습니다.</h3>
	</c:when>
	<c:otherwise>
		<table id="cartList">
			<tr><th>상품명</th><th>수 량</th><th>가 격</th><th>주문일</th><th>진행상태</th></tr>
			<c:forEach items="${orderList}" var="orderVO">
				<tr><td><a href="productDetail?pseq=${orderVO.PSEQ}">
					<h3> ${orderVO.PNAME} </h3></a></td><td> ${orderVO.QTY} </td>
   					<td><fmt:formatNumber value="${orderVO.PRICE2*orderVO.QTY}" type="currency"/></td>      
	   				<td><fmt:formatDate value="${orderVO.INDATE}" type="date"/></td>      
       				<td>처리 진행 중</td></tr>
			</c:forEach>
			<tr>
				<th colspan="2"> 총 액 </th>
				<th colspan="2"><fmt:formatNumber value="${totalPrice}" type="currency"/></th>
       			<th>주문처리가 완료되었습니다.</th>
       		</tr> 
		</table>
	</c:otherwise>
</c:choose>
<div class="clear"></div>
<div id="buttons" style="float: right">
	<input type="button" value="쇼핑 계속하기" class="cancel"  onclick="location.href='/'">
</div>	
</form>
</article>


 <%@ include file="../include/footer.jsp" %>