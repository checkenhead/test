<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sub01/sub_image_menu.jsp" %>

<article>
	<h2>Update Profile</h2>${message}
	<form name="updateFrm" action="updateMember" method="post">
		<fieldset>
			<legend>Basic Info</legend>
			<label>User ID</label>
			<input type="text" name="userid" size="12" value="${mdto.userid}" readonly>
			<input type="text" name="provider" value="${mdto.provider}" readonly><br>
			<c:choose>
				<c:when test="${mdto.provider == 'kakao'}">
					<label>Password</label><input type="password" name="pwd" value="kakao" readonly><br>
					<label>Retype Password</label><input type="password" name="pwdCheck" value="kakao" readonly><br>
				</c:when>
				<c:otherwise>
					<label>Password</label><input type="password" name="pwd"><br>
					<label>Retype Password</label><input type="password" name="pwdCheck"><br>
				</c:otherwise>
			</c:choose>
			<label>Name</label><input type="text" name="name" size="23" value="${mdto.name}"><br>
			<label>Tel</label><input type="text" name="Tel" size="23" value="${mdto.tel}"><br>
			<label>Email</label><input type="text" name="email" size="23" value="${mdto.email}"><br>
			</fieldset>
			<fieldset>
			<legend>Optional</legend>
			<label>Zip Code</label>
			<input type="text" id="sample6_postcode" size="34" name="zipnum" value="${mdto.zipnum}" readonly>
			<input type="button" class="dup" value="우편번호 찾기" onClick="sample6_execDaumPostcode();"><br>
			<label>Address</label>
			<input type="text" id="sample6_address" size="50" name="address1" value="${mdto.address1}" readonly><br>
			<label>Address Detail</label>
			<input type="text" id="sample6_detailAddress" size="50" name="address2" value="${mdto.address2}"><br>
			<label>Address Extra</label>
			<input type="text" id="sample6_extraAddress" size="50" name="address3" value="${mdto.address3}"><br>
			
			<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script>
				function sample6_execDaumPostcode() {
              	new daum.Postcode( {
              		oncomplete: function(data) {
              			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
      
						// 각 주소의 노출 규칙에 따라 주소를 조합한다.
						// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
						var addr = ''; // 주소 변수
						var extraAddr = ''; // 참고항목 변수
      
						//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							addr = data.roadAddress;
						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							addr = data.jibunAddress;
						}
      
						// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
						if(data.userSelectedType === 'R'){
							// 법정동명이 있을 경우 추가한다. (법정리는 제외)
							// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
							if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
								extraAddr += data.bname;
							}
							// 건물명이 있고, 공동주택일 경우 추가한다.
							if(data.buildingName !== '' && data.apartment === 'Y'){
								extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
							}
							// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
							if(extraAddr !== ''){
								extraAddr = ' (' + extraAddr + ')';
							}
							// 조합된 참고항목을 해당 필드에 넣는다.
							document.getElementById("sample6_extraAddress").value = extraAddr;
                      
						} else {
							document.getElementById("sample6_extraAddress").value = '';
						}
      
	                      // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                      document.getElementById('sample6_postcode').value = data.zonecode;
	                      document.getElementById("sample6_address").value = addr;
	                      // 커서를 상세주소 필드로 이동한다.
	                      document.getElementById("sample6_detailAddress").focus();
						}
					}).open();
          		}
			</script><br>
		<div class="clear"></div>
		<div id="buttons">
			<input type="submit" class="submit" value="정보 수정">
			<input type="button" class="cancel" value="메인으로" onClick="location.href='/'">
		</div>
		</fieldset>
		
	</form>

</article>

<%@ include file="../include/footer.jsp" %>