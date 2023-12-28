<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/admin/header.jsp"%>
<%@ include file="../../include/admin/sub_menu.jsp"%>

<article>
<h1>Q&amp;A 게시글 리스트</h1>  
<form name="frm" method="post">
<table style="float: right;">
	<tr><td>제목+내용 검색 
		<input type="text" name="key" value="${key}" > 
		    <input class="btn" type="button" value="검색"   onClick="go_search('adminQnaList');">
	    </td></tr>
</table>
</form>
<table id="orderList"  align="center">
	<tr><th>번호(답변여부)</th> <th>제목</th> <th>작성자</th><th>작성일</th></tr>
  	<c:forEach items="${qnaList}" var="qnaVO">
    	<tr><td>${qnaVO.QSEQ}  
      		<c:choose>          
        		<c:when test='${empty qnaVO.REPLY}'>(미처리)</c:when>
        		<c:otherwise>(답변처리완료)</c:otherwise>
      		</c:choose></td>
      		<td>
	      		<a href="#" onClick="javascript:go_view('${qnaVO.QSEQ}')">
		      		${qnaVO.SUBJECT}&nbsp;
		      		<c:if test="${qnaVO.SECRET == 'Y'}">
		      			<img src="/images/key.png" style="width:20px;vertical-align:middle;">
		      		</c:if>
	      		</a>
      		</td>
      		<td> ${qnaVO.USERID} </td><td> <fmt:formatDate value="${qnaVO.INDATE}"/></td></tr>
    </c:forEach>
</table><br>
<jsp:include page="../../include/paging/paging.jsp">
	<jsp:param name="command" value="adminQnaList" />
</jsp:include>
</article>

<%@ include file="../../include/admin/footer.jsp"%>