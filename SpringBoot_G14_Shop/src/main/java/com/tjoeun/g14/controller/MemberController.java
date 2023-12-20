package com.tjoeun.g14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tjoeun.g14.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	MemberService ms;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "member/login";
	}
}
