package com.tjoeun.g14.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjoeun.g14.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
	@Autowired
	OrderService os;
	
	@PostMapping("/insertOrder")
	public String insertOrder(HttpServletRequest request) {
		String url = "member/login";
		
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
			
			url = "redirect:/orderList?oseq=" + paramMap.get("oseq").toString();
		}
		
		return url;
	}
	
	@GetMapping("/orderList")
	public String orderList(@RequestParam("oseq") int oseq, HttpServletRequest request, Model model) {
		String url = "member/login";
		
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("oseq", oseq);
			paramMap.put("ref_cursor", null);
			
			os.getOrderByOseq(paramMap);
			
			model.addAttribute("orderList", paramMap.get("ref_cursor"));
			model.addAttribute("totalPrice", paramMap.get("totalPrice"));
			
			url = "mypage/orderList";
		}
		
		return url;
	}
	
	@PostMapping("/insertOderByPseq")
	public String insertOderByPseq(@RequestParam("pseq") int pseq, @RequestParam("qty") int qty, HttpServletRequest request) {
		String url = "member/login";
		
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("userid", loginUser.get("USERID"));
			paramMap.put("pseq", pseq);
			paramMap.put("qty", qty);
			paramMap.put("oseq", 0);
			
			os.insertOrderByPseq(paramMap);
			
			url = "redirect:/orderList?oseq=" + paramMap.get("oseq").toString();
		}
		
		return url;
	}
	
	@GetMapping("/myPage")
	public String myPage(HttpServletRequest request, Model model) {
		String url = "member/login";
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", loginUser.get("USERID"));
			paramMap.put("ref_cursor", null);
			
			os.getOrderListIng(paramMap);
			
			model.addAttribute("orderList", paramMap.get("finalList"));
			model.addAttribute("title", "진행 중인 주문내역");
			url = "mypage/myPage";
		}
		
		return url;
	}
	
	@GetMapping("/orderAll")
	public String orderAll(HttpServletRequest request, Model model) {
		String url = "member/login";
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", loginUser.get("USERID"));
			paramMap.put("ref_cursor", null);
			
			os.getOrderListAll(paramMap);
			
			model.addAttribute("orderList", paramMap.get("finalList"));
			model.addAttribute("title", "총 주문내역");
			url = "mypage/myPage";
		}
		
		return url;
	}
	
	@GetMapping("/orderDetail")
	public String orderDetail(@RequestParam("oseq") int oseq, HttpServletRequest request, Model model) {
		String url = "member/login";
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("oseq", oseq);
			paramMap.put("ref_cursor", null);
			
			os.getOrderByOseq(paramMap);
			
			model.addAttribute("orderList", paramMap.get("ref_cursor"));
			model.addAttribute("orderDetail", paramMap.get("orderDetail"));
			model.addAttribute("totalPrice", paramMap.get("totalPrice"));
			
			url = "mypage/orderDetail";
		}
		
		return url;
	}
}
