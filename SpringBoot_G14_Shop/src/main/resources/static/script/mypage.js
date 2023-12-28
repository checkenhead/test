function go_cart(){
	if(document.productDetailFrm.qty.value == ""){
		alert("수량을 입력하세요.");
		document.productDetailFrm.qty.focus();
	} else {
		document.productDetailFrm.action = "insertCart";
		document.productDetailFrm.method = "post";
		document.productDetailFrm.submit();
	}
}

function go_cart_delete(){
	var count = 0;
	var cartListFrm = document.cartListFrm;
	
	if(cartListFrm.cseq.length == undefined && cartListFrm.cseq.checked == true) {
		count++;
	} else {
		for(var i = 0; i < cartListFrm.cseq.length; i++){
			if(cartListFrm.cseq[i].checked == true){
				count++;
			}
		}
	}
	
	if(count == 0){
		alert("삭제할 항목을 선택하세요.");
	} else {
		cartListFrm.action = "deleteCart";
		cartListFrm.submit();
	}
}

function go_order_insert(){
	document.cartListFrm.action = "insertOrder";
	document.cartListFrm.submit();
}

function go_order(){
	document.productDetailFrm.action = "insertOderByPseq";
	document.productDetailFrm.method = "post";
	document.productDetailFrm.submit();
}

function go_order_end(oseq, odseq){
	location.href = "orderEnd?odseq=" + odseq + "&oseq=" + oseq;
}



function passCheck(qseq){
	var url = "passCheck?qseq=" + qseq;
	var opt = "toolbar=no, menubar=no, resizable=no, scrollbars=no, width=500, height=250";
	
	window.open(url, "Pass Check", opt);
}

function toggle_secret(){
	if(document.qnaWriteFrm.secret.checked == true){
		document.qnaWriteFrm.pass.disabled = false;
		document.qnaWriteFrm.pass.value = "";
	} else{
		document.qnaWriteFrm.pass.disabled = true;
	}
}




