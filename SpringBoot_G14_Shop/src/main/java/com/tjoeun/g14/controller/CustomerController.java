package com.tjoeun.g14.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tjoeun.g14.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class CustomerController {
	@Autowired
	CustomerService cs;
	
	@GetMapping("/customer")
	public String customer() {
		return "customer/intro";
	}
	
	@GetMapping("qnaList")
	public String qnaList(HttpServletRequest request, Model model) {
		//HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("ref_cursor", null);
		
		cs.getQnaList(paramMap);
		
		model.addAttribute("qnaList", paramMap.get("ref_cursor"));
		
		return "customer/qnaList";
	}
	
	@GetMapping("/passCheck")
	public ModelAndView passCheck(@RequestParam("qseq") int qseq) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("qseq", qseq);
		mav.setViewName("customer/passCheck");
		
		return mav;
	}
	
	@PostMapping("/qnaPassCheck")
	public String qnaPassCheck(@RequestParam("qseq") int qseq, @RequestParam("pass") String pass, Model model) {
		String url = "customer/passCheck";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("qseq", qseq);
		paramMap.put("ref_cursor", null);
		
		cs.getQna(paramMap);
		
		HashMap<String, Object> qvo = ((ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor")).get(0);
		
		if(!qvo.get("PASS").equals(pass)) {
			model.addAttribute("message", "비밀번호가 틀립니다.");
		}else {
			model.addAttribute("qseq", qseq);
			url = "customer/checkSuccess";
		}
		
		return url;
	}
	
	@GetMapping("qnaView")
	public ModelAndView qnaView(@RequestParam("qseq") int qseq) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("qseq", qseq);
		paramMap.put("ref_cursor", null);
		
		cs.getQna(paramMap);
		
		mav.addObject("qnaVO", ((ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor")).get(0));
		mav.setViewName("customer/qnaView");
		
		return mav;
	}
	
	@GetMapping("/qnaWriteForm")
	public String qnaWriteForm(HttpServletRequest request) {
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		String url = "member/login";
		
		if(loginUser != null) {
			url = "customer/qnaWrite";
		}
		
		return url;
	}
	
	@PostMapping("/qnaWrite")
	public String qnaWrite(
			@RequestParam(value="secret", required=false, defaultValue="N") String secret,
			@RequestParam(value="pass", required=false, defaultValue="") String pass,
			@RequestParam("subject") @Valid String subject,
			@RequestParam("content") @Valid String content,
			BindingResult result,
			HttpServletRequest request,
			Model model) {
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		String url = "member/login";
		
		if(loginUser != null) {
			if(result.getFieldError("subject") != null) {
				model.addAttribute("message", result.getFieldError("subject").getDefaultMessage());
			} else if(result.getFieldError("content") != null) {
				model.addAttribute("message", result.getFieldError("content").getDefaultMessage());
			} else if(secret.equals("Y") && pass.equals("")) {
				model.addAttribute("message", "비밀번호를 입력하세요.");
			}else {
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				
				paramMap.put("userid", loginUser.get("USERID"));
				paramMap.put("subject", subject);
				paramMap.put("content", content);
				paramMap.put("secret", secret);
				paramMap.put("pass", secret.equals("N")?pass:null);
				
				cs.insertQna(paramMap);
				
				url = "redirect:/qnaList";
			}	
		}
		
		return url;
	}
}
