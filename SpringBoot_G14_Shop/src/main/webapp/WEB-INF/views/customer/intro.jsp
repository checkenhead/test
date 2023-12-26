<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub04/sub_image_menu.jsp" %>

<article>
	<h2>회사 소개</h2>
	Shoes Shop은 어쩌고 저쩌고...
	<br><br><hr>
	<h2>오시는 길</h2>
	-서울특별시 서대문구 신촌로94 그랜드플라자<br>
	-전화 +82 2 123 1234<br>
	-팩스 +82 2 123 1233<br>
	<br>
	<!-- * 카카오맵 - 지도퍼가기 -->
	<!-- 1. 지도 노드 -->
	<div id="daumRoughmapContainer1703573893686" class="root_daum_roughmap root_daum_roughmap_landing"></div>
	
	<!--
		2. 설치 스크립트
		* 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.
	-->
	<script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>
	
	<!-- 3. 실행 스크립트 -->
	<script charset="UTF-8">
		new daum.roughmap.Lander({
			"timestamp" : "1703573893686",
			"key" : "2hddw",
			"mapWidth" : "640",
			"mapHeight" : "360"
		}).render();
	</script>
	<br>
	<h3>전철</h3>
	-2호선 신촌역 7/8번 출구
	<br>
	<h3>버스</h3>
	-마을버스 마포10
	<br>
</article>

<%@ include file="../include/footer.jsp" %>