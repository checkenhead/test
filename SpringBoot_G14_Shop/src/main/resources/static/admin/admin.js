function go_detail(pseq){
	location.href="adminProductDetail?pseq=" + pseq;
}

function go_search(requestName){
	document.frm.action = requestName + "?page=1";
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