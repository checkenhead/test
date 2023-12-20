<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8">

<div id="sub_img">
	<img src="/images/member/sub_img.jpg" style="border-radius:20px 20px 20px 20px;">
</div>
<div class="clear"></div>
<nav id="sub_menu">
	<ul>
		<c:choose>
			<c:when test="${empty loginUser}">
				<li><a href="loginForm">Login</a></li>
				<li><a href="contract">Join</a></li>
				<li><a href="#" onClick="find_id_pw();">Find ID/Password</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="memberEditForm">Edit Member</a></li>
				<li><a href="logout">Logout</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</nav>