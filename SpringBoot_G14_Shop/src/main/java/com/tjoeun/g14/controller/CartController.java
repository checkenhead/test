package com.tjoeun.g14.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjoeun.g14.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {
	@Autowired
	CartService cs;
	
	@PostMapping("/insertCart")
	public String insertCart(@RequestParam("pseq") int pseq,
			@RequestParam("qty") int qty,
			HttpServletRequest request,
			Model model) {
		String url = "member/login";
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		//로그인 체크
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("userid", loginUser.get("USERID"));
			paramMap.put("pseq", pseq);
			paramMap.put("qty", qty);
			
			cs.insertCart(paramMap);
			
			url = "redirect:/cartList";
		}
		
		return url;
	}
	
	@GetMapping("/cartList")
	public String cartList(HttpServletRequest request, Model model) {

		String url = "member/login";
		
		//로그인한 유저의 카트리스트를 조회하여 cartList.jsp로 이동
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("userid", loginUser.get("USERID"));
			paramMap.put("ref_cursor", null);
			
			cs.getCartList(paramMap);
			
			model.addAttribute("cartList", paramMap.get("ref_cursor"));
			model.addAttribute("totalPrice", paramMap.get("totalPrice"));
			
			url = "mypage/cartList";
		}
		
		return url;
	}
	
	@PostMapping("/deleteCart")
	public String deleteCart(HttpServletRequest request,
			@RequestParam("cseq") String[] cseqArr) {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cseqArr", cseqArr);
		cs.deleteCart(paramMap);
		
		return "redirect:/cartList";
	}
	
}
