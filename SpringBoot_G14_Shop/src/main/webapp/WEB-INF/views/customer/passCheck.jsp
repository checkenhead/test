<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pass check</title>

<script type="text/javascript">
	function inputCheck(){
		if(document.qnaPassCheckFrm.pass.value == ""){
			alert("비밀번호를 입력하세요.");
			document.qnaPassCheckFrm.pass.focus();
			return false;
		}
		else{
			return true;
		}
	}
</script>

</head>
<body>
	
	<div align="center">
		<h1>비밀번호 확인</h1>
		<form name="qnaPassCheckFrm" method="post" action="qnaPassCheck">
			<input type="hidden" name="qseq" value="${param.qseq}">
			<table style="width:80%;">
				<tr>
					<th>열람 비밀번호</th>
					<td><input type="password" name="pass" size="20"></td>
				</tr>
			</table>
			<input type="submit" value="확인" onClick="return inputCheck();">
			<br><br>
			${message}
		</form>
	</div>
	
</body>
</html>