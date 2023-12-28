function go_detail(pseq){
	location.href="adminProductDetail?pseq=" + pseq;
}

function go_search(requestName){
	document.frm.action = requestName + "?page=1";
	document.frm.method = "get";
	document.frm.submit();
}

function go_mov(){
	location.href="productList";
}

function go_mod(pseq){
	location.href="productUpdateForm?pseq=" + pseq;
}

function go_wrt(){
	location.href="productWriteForm";
}

function go_save(){
	var frm = document.frm;
	
	if(frm.name.value == ""){
		alert("상품명을 입력하세요.");
		frm.name.focus();
	}else if(frm.price1.value == ""){
		alert("원가를 입력하세요.");
		frm.price1.focus();
	}else if(frm.price2.value == ""){
		alert("판매가를 입력하세요.");
		frm.price2.focus();
	}else if(frm.content.value == ""){
		alert("상품설명을 입력하세요.");
		frm.content.focus();
	}else if(frm.image.value == ""){
		alert("상품 이미지가 없습니다.");
		frm.image.focus();
	}else{
		frm.action = "productWrite";
		frm.submit();
	}
}

function cal(){
	if(!Number.isNaN(document.frm.price1.value) && !Number.isNaN(document.frm.price2.value)){
		document.frm.price3.value = document.frm.price2.value - document.frm.price1.value;
	}else{
		document.frm.price3.value = "";
	}
}

function go_mod_save(){
	var frm = document.frm;
	
	if(frm.name.value == ""){
		alert("상품명을 입력하세요.");
		frm.name.focus();
	}else if(frm.price1.value == ""){
		alert("원가를 입력하세요.");
		frm.price1.focus();
	}else if(frm.price2.value == ""){
		alert("판매가를 입력하세요.");
		frm.price2.focus();
	}else if(frm.content.value == ""){
		alert("상품설명을 입력하세요.");
		frm.content.focus();
	}else{
		if(confirm("수정하시겠습니까?")){
		frm.action = "productUpdate";
		frm.submit();
		}
	}
}

function go_order_update(){
	var count = 0;
	var adminOrderListFrm = document.frm;
	
	if(adminOrderListFrm.result.length == undefined && adminOrderListFrm.result.checked == true) {
		count++;
	} else {
		for(var i = 0; i < adminOrderListFrm.result.length; i++){
			if(adminOrderListFrm.result[i].checked == true){
				count++;
			}
		}
	}
	
	if(count == 0){
		alert("업데이트할 항목을 선택하세요.");
	} else {
		adminOrderListFrm.action = "orderUpdateResult";
		adminOrderListFrm.submit();
	}
}

function toggle_useyn(userid, useyn){
	location.href = "memberToggleUseyn?userid=" + userid + "&useyn=" + useyn;
}

function go_view(qseq){
	location.href = "adminQnaView?qseq=" + qseq;
}

function go_reply(){
	if(document.frm.reply.value == ""){
		alert("내용을 입력하세요.");
		return;
	} else {
		document.frm.action = "adminUpdateReply";
		document.frm.submit();
	}
}

function go_banner_save(){
	if(document.frm.oseq.value == ""){
		alert("디스플레이 순서를 선택하세요.");
		return;
	} else if(document.frm.subject.value == ""){
		alert("제목을 입력하세요.");
		return;
	} else if(document.frm.image.value == ""){
		alert("사진이 없습니다.");
		return;
	} else{
		document.frm.action = "insertBanner";
		document.frm.submit();
	}
}

