package com.tjoeun.g14.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tjoeun.g14.dto.KakaoProfile;
import com.tjoeun.g14.dto.KakaoProfile.KakaoAccount;
import com.tjoeun.g14.dto.KakaoProfile.KakaoAccount.Profile;
import com.tjoeun.g14.dto.MemberVO;
import com.tjoeun.g14.dto.OAuthToken;
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
	public String login(@ModelAttribute("mdto") @Valid MemberVO membervo, BindingResult result,
			HttpServletRequest request, Model model) {
		String url = "member/login";

		//아이디/비밀번호 validation
		if (result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
		} else if (result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		} else {//validation 통과 후
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			//jsp에서 전달 받은 userid와 빈커서를 인자로 getMember호출(프로시저 호출)
			paramMap.put("userid", membervo.getUserid());
			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);

			//userid로 검색한 리스트 저장
			ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			
			//검색 결과가 없다면
			if (list == null || list.size() == 0) {
				model.addAttribute("message", "아이디가 없습니다.");
			} else {//검색 결과가 있다면
				HashMap<String, Object> memberMap = list.get(0);

				//탈퇴/비밀번호 확인
				if (memberMap.get("USEYN").equals("N")) {
					model.addAttribute("message", "탈퇴 절차 진행 중인 아이디입니다. 관리자에게 문의하세요.");
				} else if (memberMap.get("PROVIDER").equals("kakao")) {
					model.addAttribute("message", "Kakao계정은 Kakao로 로그인해주세요.");
				} else if (!memberMap.get("PWD").equals(membervo.getPwd())) {
					model.addAttribute("message", "비밀번호가 틀립니다.");
				} else if (memberMap.get("PWD").equals(membervo.getPwd())) {
					//로그인 처리
					request.getSession().setAttribute("loginUser", memberMap);
					url = "redirect:/";
				}
			}
		}

		return url;
	}

	@GetMapping("/kakaostart")
	public @ResponseBody String kakaostart() {
		String start = "<script type='text/javascript'>location.href='https://kauth.kakao.com/oauth/authorize?"
				+ "client_id=3d1a8896e9eece72dd49e23ba329185e&redirect_uri=http://localhost:8070/kakaoLogin"
				+ "&response_type=code'</script>";

		return start;
	}

	@RequestMapping("/kakaoLogin")
	public String loginKakao(HttpServletRequest request) throws UnsupportedEncodingException, IOException {

		String code = request.getParameter("code");
		String endpoint = "https://kauth.kakao.com/oauth/token";
		URL url = new URL(endpoint); // import java.net.URL;
		String bodyData = "grant_type=authorization_code"
				+ "&client_id=3d1a8896e9eece72dd49e23ba329185e"
				+ "&redirect_uri=http://localhost:8070/kakaoLogin"
				+ "&code=" + code;

		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // import java.net.HttpURLConnection;
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoOutput(true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		bw.write(bodyData);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String input = "";
		StringBuilder sb = new StringBuilder(); // 조각난 String 을 조립하기위한 객체
		while ((input = br.readLine()) != null) {
			sb.append(input);
			System.out.println(input); // 수신된 토큰을 콘솔에 출력합니다
		}
		Gson gson = new Gson();
		OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
		String endpoint2 = "https://kapi.kakao.com/v2/user/me";
		URL url2 = new URL(endpoint2);

		HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
		conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
		conn2.setDoOutput(true);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
		String input2 = "";
		StringBuilder sb2 = new StringBuilder();
		while ((input2 = br2.readLine()) != null) {
			sb2.append(input2);
			System.out.println(input2);
		}

		Gson gson2 = new Gson();
		KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
		KakaoAccount ac = kakaoProfile.getAccount();
		Profile pf = ac.getProfile();

		// 회원 가입 및 로그인 정보 세션 저장
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", kakaoProfile.getId());
		paramMap.put("ref_cursor", null);
		
		ms.getMember(paramMap);
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		
		
		//db조회 결과가 없으면 회원가입(레코드 추가)
		if(list == null || list.size() == 0) {
			paramMap.put("userid", kakaoProfile.getId());
			paramMap.put("tel", "kakao");
			paramMap.put("email", "kakao");
			paramMap.put("name", pf.getNickname());
			paramMap.put("provider", "kakao");
			ms.joinKakao(paramMap);
			
			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);
			list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		}
		
		HashMap<String, Object> memberMap = list.get(0);
		request.getSession().setAttribute("loginUser", memberMap);
		
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		request.getSession().removeAttribute("loginUser");

		return "redirect:/";
	}
	
	@GetMapping("/contract")
	public String contract() {
		return "member/contract";
	}
	
	@PostMapping("/joinForm")
	public String joinForm() {
		return "member/joinForm";
	}
	
	@GetMapping("/idCheckForm")
	public ModelAndView idCheckForm(@RequestParam("userid") String userid) {
		ModelAndView mav = new ModelAndView();
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("ref_cursor", null);
		ms.getMember(paramMap);
		//userid로 검색한 결과가 ArrayList로 ref_cursor에 저장됩니다.
		//1. 해시맵에서 리스트를 추출
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		
		//2. 리스트에 검색결과가 있는지 확인하여 결과 저장
		mav.addObject("result", ((list == null || list.size() == 0)?-1:1));
		mav.addObject("userid", userid);
		mav.setViewName("member/idCheck");
		
		return mav;
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute("mdto") @Valid MemberVO membervo,
			BindingResult result,
			@RequestParam(value = "reid", required = false) String reid,
			@RequestParam(value = "pwdCheck", required = false) String pwdCheck,
			Model model) {
		String url = "member/joinForm";
		
		//validation
		if(result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
		} else if(reid == null || reid.equals("") || !reid.equals(membervo.getUserid())) {
			model.addAttribute("message", "아이디 중복체크를 해주세요.");
		} else if(result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		} else if(pwdCheck == null || pwdCheck.equals("") || !pwdCheck.equals(membervo.getPwd())) {
			model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다.");
		} else if(result.getFieldError("name") != null) {
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
		} else if(result.getFieldError("tel") != null) {
			model.addAttribute("message", result.getFieldError("tel").getDefaultMessage());
		} else if(result.getFieldError("email") != null) {
			model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
		} else {
			//모두 정상 입력했을 경우
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("userid", membervo.getUserid());
			paramMap.put("pwd", membervo.getPwd());
			paramMap.put("name", membervo.getName());
			paramMap.put("tel", membervo.getTel());
			paramMap.put("email", membervo.getEmail());
			paramMap.put("zipnum", membervo.getZipnum());
			paramMap.put("address1", membervo.getAddress1());
			paramMap.put("address2", membervo.getAddress2());
			paramMap.put("address3", membervo.getAddress3());
			
			ms.insertMember(paramMap);
			model.addAttribute("message", "회원가입이 완료되었습니다. 로그인하세요.");
			url = "member/login";
		}
		
		return url;
	}
	
	@GetMapping("/memberEditForm")
	public ModelAndView memberEditForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		//세션으로부터 현재 로그인된 유저정보 저장
		HashMap<String, Object> loginUser = (HashMap<String, Object>) request.getSession().getAttribute("loginUser");
		
		MemberVO mdto = new MemberVO();
		mdto.setUserid((String)loginUser.get("USERID"));
		mdto.setName((String)loginUser.get("NAME"));
		mdto.setTel((String)loginUser.get("TEL"));
		mdto.setEmail((String)loginUser.get("EMAIL"));
		mdto.setZipnum((String)loginUser.get("ZIPNUM"));
		mdto.setAddress1((String)loginUser.get("ADDRESS1"));
		mdto.setAddress2((String)loginUser.get("ADDRESS2"));
		mdto.setAddress3((String)loginUser.get("ADDRESS3"));
		mdto.setProvider((String)loginUser.get("PROVIDER"));
		
		mav.addObject("mdto", mdto);
		mav.setViewName("member/memberUpdateForm");
		
		return mav;
	}
	
	@PostMapping("/updateMember")
	public String updateMember(
			@ModelAttribute("mdto") @Valid MemberVO membervo,
			BindingResult result,
			@RequestParam(value = "pwdCheck", required = false) String pwdCheck,
			HttpServletRequest request,
			Model model) {
		
		String url = "member/memberUpdateForm";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		//validation
		if(result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
		} else if(result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		} else if(pwdCheck == null || pwdCheck.equals("") || !pwdCheck.equals(membervo.getPwd())) {
			model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다.");
		} else if(result.getFieldError("name") != null) {
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
		} else if(result.getFieldError("tel") != null) {
			model.addAttribute("message", result.getFieldError("tel").getDefaultMessage());
		} else if(result.getFieldError("email") != null) {
			model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
		} else {
			paramMap.put("userid", membervo.getUserid());
			paramMap.put("pwd", membervo.getPwd());
			paramMap.put("name", membervo.getName());
			paramMap.put("tel", membervo.getTel());
			paramMap.put("email", membervo.getEmail());
			paramMap.put("zipnum", membervo.getZipnum());
			paramMap.put("address1", membervo.getAddress1());
			paramMap.put("address2", membervo.getAddress2());
			paramMap.put("address3", membervo.getAddress3());
			
			//update
			ms.updateMember(paramMap);
			
			//loginUser 업데이트 된 정보로 갱신
			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);
			
			request.getSession().setAttribute("loginUser", ((ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")).get(0));
			
			url = "redirect:/";
		}
		
		return url;
	}
	
	@GetMapping("/deleteMember")
	public String deleteMember(HttpServletRequest request, Model model) {
		HashMap<String, Object> loginUser = (HashMap<String, Object>)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null) {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("userid", loginUser.get("USERID"));
			
			ms.deleteMember(paramMap);
			
			model.addAttribute("message", "회원탈퇴가 등록되었습니다.");
		}
		
		return "member/login";
	}
}
