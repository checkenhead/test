package com.tjoeun.g14.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tjoeun.g14.dto.MemberVO;
import com.tjoeun.g14.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class MemberController {
	
	@Autowired
	MemberService ms;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "member/login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("mdto") @Valid MemberVO membervo,
			BindingResult result, HttpServletRequest request, Model model) {
		String url = "member/login";
		if(result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
		} else if(result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		} else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", membervo.getUserid());
			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);
			
			ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			if(list == null && list.size() == 0) {
				model.addAttribute("message", "아이디가 없습니다.");
			} else {
				HashMap<String, Object> memberMap = list.get(0);
				
				if(memberMap.get("USEYN").equals("N")) {
					model.addAttribute("message", "탈퇴 절차 진행 중인 아이디입니다. 관리자에게 문의하세요.");
				} else if(!memberMap.get("PWD").equals(membervo.getPwd())) {
					model.addAttribute("message", "비밀번호가 틀립니다.");
				}else if(memberMap.get("PWD").equals(membervo.getPwd())) {
					request.getSession().setAttribute("loginUser", memberMap);
					url = "redirect:/";
				}
			}
		}
		
		return url;
	}
}
