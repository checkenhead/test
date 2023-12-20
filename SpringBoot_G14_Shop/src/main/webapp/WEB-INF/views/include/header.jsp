<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shop</title>

<link rel="stylesheet" type="text/css" href="/css/shopping.css">
<script type="text/javascript" src="/script/member.js"></script>
<script type="text/javascript" src="/script/mypage.js"></script>

</head>
<body>

<div id="wrap">
<head>
	<div id="logo">
		<a href="/"><img src="/images/logo.png" width="180" height="100"></a>
	</div>
	<nav id="top_menu">
		<ul>
			<c:choose>
				<c:when test="${empty loginUser}">
					<li><a href="loginForm">Login</a></li><li><a href="contract">Join</a></li>
				</c:when>
				<c:otherwise>
					<li style="color:blue;font-weight:bold;font-size:100%;width:150px;">
						${loginUser.NAME}(${loginUser.USERID})로그인
					</li>
					<li><a href="memberEditForm">Edit</a></li>
					<li><a href="logout">Logout</a></li>
				</c:otherwise>
			</c:choose>
			<li><a href="cartList">Cart</a></li>
			<li><a href="myPage">MyPage</a></li>
			<li><a href="customer">QnA</a></li>
			<li><a href="admin">Admin</a></li>
		</ul>
	</nav>
	<nav id="category_menu">
		<ul>
			<li><a href="category?kind=1">Heels</a></li>
			<li><a href="category?kind=2">Boots</a></li>
			<li><a href="category?kind=3">Sandals</a></li>
			<li><a href="category?kind=4">Sneakers</a></li>
			<li><a href="category?kind=5">Slippers</a></li>
			<li><a href="category?kind=6">OnSales</a></li>
		</ul>
	</nav>
	<div class="clear"></div>
	<hr>
</head>