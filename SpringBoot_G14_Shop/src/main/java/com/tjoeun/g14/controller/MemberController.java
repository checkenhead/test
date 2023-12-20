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
import org.springframework.web.bind.annotation.ResponseBody;

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

		if (result.getFieldError("userid") != null) {
			model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
		} else if (result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		} else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userid", membervo.getUserid());
			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);

			ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
			if (list == null || list.size() == 0) {
				model.addAttribute("message", "아이디가 없습니다.");
			} else {
				HashMap<String, Object> memberMap = list.get(0);

				if (memberMap.get("USEYN").equals("N")) {
					model.addAttribute("message", "탈퇴 절차 진행 중인 아이디입니다. 관리자에게 문의하세요.");
				} else if (!memberMap.get("PWD").equals(membervo.getPwd())) {
					model.addAttribute("message", "비밀번호가 틀립니다.");
				} else if (memberMap.get("PWD").equals(membervo.getPwd())) {
					request.getSession().setAttribute("loginUser", memberMap);
					url = "redirect:/";
				}
			}
		}

		return url;
	}

	// 3d1a8896e9eece72dd49e23ba329185e
	@GetMapping("/kakaostart")
	public @ResponseBody String kakaostart() {
		String str = "<script type='text/javascript'>location.href='https://kauth.kakao.com/oauth/authorize?"
				+ "client_id=3d1a8896e9eece72dd49e23ba329185e&redirect_uri=http://localhost:8070/kakaoLogin"
				+ "&response_type=code'</script>";

		return str;
	}

	@RequestMapping("/kakaoLogin")
	public String loginKakao(HttpServletRequest request) throws UnsupportedEncodingException, IOException {

		String code = request.getParameter("code");
		String endpoint = "https://kauth.kakao.com/oauth/token";
		URL url = new URL(endpoint); // import java.net.URL;
		String bodyData = "grant_type=authorization_code&";
		bodyData += "client_id=3d1a8896e9eece72dd49e23ba329185e&";
		bodyData += "redirect_uri=http://localhost:8070/kakaoLogin&";
		bodyData += "code=" + code;

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
}
