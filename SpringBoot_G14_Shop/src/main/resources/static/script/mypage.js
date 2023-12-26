function go_cart(){
	if(document.productDetailFrm.qty.value == ""){
		alert("수량을 입력하세요.");
		document.productDetailFrm.qty.focus();
	} else {
		document.productDetailFrm.action = "insertCart";
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


