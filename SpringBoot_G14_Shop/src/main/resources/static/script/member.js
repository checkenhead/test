function go_next(){
	if(document.contract.okon[1].checked == true){
		alert("약관에 동의가 필요합니다.");
	} else {
		document.contract.action = "joinForm";
		document.contract.submit();
	}
}

function id_check(){
	if(document.joinFrm.userid.value == ""){
		alert("아이디 중복체크를 해주세요.");
		document.joinFrm.userid.focus();
		return;
	}
	
	var url = "idCheckForm?userid=" + document.joinFrm.userid.value;
	var opt = "toolbar=no, menubar=no, resizable=no, scrollbars=no, width=500, height=250";
	
	window.open(url, "ID Check", opt);
}

function id_ok(userid){
	opener.joinFrm.userid.value = userid;
	opener.joinFrm.reid.value = userid;
	self.close();
}