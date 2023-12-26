<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub04/sub_image_menu.jsp" %>

<article>
	<h2>1:1 고객 게시판</h2>
	<h3>고객님의 질문에 1:1 답변을 드립니다.</h3>
	<form name="qnaListFrm" method="post">
		<table id="cartList">
			<tr><th>번호</th><th>제목</th><th>등록일</th><th>답변</th></tr>
			<c:forEach items="${qnaList}" var="qnaVO">
				<tr><td>${qnaVO.QSEQ}</td>
				<td>
					<c:choose>
						<c:when test="${qnaVO.SECRET == 'Y'}">
							<a href="#" onClick="passCheck('${qnaVO.QSEQ}')">${qnaVO.SUBJECT}</a>&nbsp;
							<img src="/images/key.png" style="width:20px;vertical-align:middle;">
						</c:when>
						<c:otherwise>
							<a href="qnaView?qseq=${qnaVO.QSEQ}">${qnaVO.SUBJECT}</a>
						</c:otherwise>
					</c:choose>
				</td>
				<td><fmt:formatDate type="date" value="${qnaVO.INDATE}"/></td>
				<td>
					<c:choose>
						<c:when test="${empty qnaVO.REPLY}">N</c:when>
						<c:otherwise>Y</c:otherwise>
					</c:choose>
				</td></tr>
			</c:forEach>
		</table>
	</form>
</article>

<%@ include file="../include/footer.jsp" %>