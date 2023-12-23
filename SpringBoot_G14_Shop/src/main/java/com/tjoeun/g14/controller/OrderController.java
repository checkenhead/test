package com.tjoeun.g14.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tjoeun.g14.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
	@Autowired
	OrderService os;
	
	@GetMapping("insertOrder")
	public String insertOrder(HttpServletRequest request) {
		String url = "member/login";
		int oseq = 0;
		
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			//1. 카트테이블에서 로그인 유저의 장바구니 목록 조회
			//2. 상품목록을 orders 테이블과 order_detail에 추가
			//3. 카트에서 주문한 목록 삭제
			// oseq 주문번호 리턴
			
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", loginUser.get("USERID"));
			paramMap.put("oseq", 0);
			
			os.insertOrder(paramMap);
			oseq = Integer.parseInt(paramMap.get("oseq").toString());
			
			url = "redirect:/orderList?oseq=" + oseq;
		}
		
		return url;
	}
}
