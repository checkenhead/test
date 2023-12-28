package com.tjoeun.g14.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tjoeun.g14.dto.Paging;
import com.tjoeun.g14.service.AdminService;
import com.tjoeun.g14.service.CustomerService;
import com.tjoeun.g14.service.ProductService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
	@Autowired
	AdminService as;

	@Autowired
	ProductService ps;

	@Autowired
	CustomerService cs;

	@Autowired
	ServletContext context;

	@GetMapping("/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}

	@PostMapping("/adminLogin")
	public String adminLogin(@RequestParam(value = "adminid", required = false) String adminid,
			@RequestParam(value = "pwd", required = false) String pwd, HttpServletRequest request, Model model) {
		String url = "admin/adminLoginForm";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		model.addAttribute("adminid", adminid);

		if (adminid == null || adminid.equals("")) {
			model.addAttribute("message", "아이디를 입력하세요.");
		} else if (pwd == null || pwd.equals("")) {
			model.addAttribute("message", "비밀번호를 입력하세요.");
		} else {
			paramMap.put("adminid", adminid);
			paramMap.put("ref_cursor", null);

			as.getAdmin(paramMap);

			ArrayList<HashMap<String, Object>> resultList = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");

			if (resultList == null || resultList.size() == 0) {
				model.addAttribute("message", "아이디가 없습니다.");
			} else {
				HashMap<String, Object> avo = resultList.get(0);
				if (avo.get("PWD") == null) {
					model.addAttribute("message", "비밀번호 오류, 관리자에게 문의하세요.");
				} else if (!avo.get("PWD").equals(pwd)) {
					model.addAttribute("message", "비밀번호가 틀립니다.");
				} else if (avo.get("PWD").equals(pwd)) {
					request.getSession().setAttribute("loginAdmin", avo);
					url = "redirect:/productList";
				}
			}
		}

		return url;
	}

	@GetMapping("/productList")
	public ModelAndView productList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> loginAdmin = (HashMap<String, Object>) request.getSession().getAttribute("loginAdmin");

		if (loginAdmin == null) {
			mav.setViewName("admin/adminLoginForm");
		} else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("request", request);
			paramMap.put("ref_cursor", null);

			as.getProductList(paramMap);
			// HashMap<String, Object> resultMap = as.getProductList(request);

			mav.addObject("productList", paramMap.get("ref_cursor"));
			mav.addObject("paging", paramMap.get("paging"));
			mav.addObject("key", paramMap.get("key"));
			mav.setViewName("admin/product/productList");
		}

		return mav;
	}

	@GetMapping("/adminProductDetail")
	public ModelAndView adminProductDetail(@RequestParam("pseq") int pseq) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("pseq", pseq);
		paramMap.put("ref_cursor", null);

		ps.getProduct(paramMap);

		HashMap<String, Object> pvo = ((ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")).get(0);

		String[] kindList = { "Heels", "Boots", "Sandals", "Sneakers", "Slippers", "OnSale" };

		mav.addObject("kind", kindList[Integer.parseInt(pvo.get("KIND").toString()) - 1]);
		mav.addObject("productVO", pvo);
		mav.setViewName("admin/product/productDetail");

		return mav;
	}

	@GetMapping("/adminLogout")
	public String adminLogout(HttpServletRequest request) {
		request.getSession().removeAttribute("loginAdmin");

		return "redirect:/adminLogin";
	}

	@GetMapping("/productWriteForm")
	public String productWriteForm(HttpServletRequest request, Model model) {
		String[] kindList = { "Heels", "Boots", "Sandals", "Sneakers", "Slippers", "OnSale" };

		model.addAttribute("kindList", kindList);

		return "admin/product/productWriteForm";
	}

	@PostMapping("/fileup")
	@ResponseBody // 자신을 호출하는 곳으로 리턴되는 데이터를 갖고 이동하여 페이지에 표시하라는 의미
	public HashMap<String, Object> fileup(@RequestParam("fileimage") MultipartFile file, HttpServletRequest request,
			Model model) {
		String path = context.getRealPath("/product_images");

		Calendar today = Calendar.getInstance();
		long t = today.getTimeInMillis();

		String filename = file.getOriginalFilename();
		String fn1 = filename.substring(0, filename.indexOf("."));
		String fn2 = filename.substring(filename.indexOf("."));
		String uploadPath = path + "/" + fn1 + t + fn2;

		System.out.println("경로 : " + uploadPath);

		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			file.transferTo(new File(uploadPath));
			result.put("STATUS", 1);
			result.put("FILENAME", fn1 + t + fn2);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			result.put("STATUS", 0);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("STATUS", 0);
		}

		return result;
	}

	@PostMapping("/productWrite")
	public String productWrite(@RequestParam("name") String name, @RequestParam("kind") String kind,
			@RequestParam("price1") int price1, @RequestParam("price2") int price2, @RequestParam("price3") int price3,
			@RequestParam("content") String content, @RequestParam("image") String image, HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("name", name);
		paramMap.put("kind", kind);
		paramMap.put("price1", price1);
		paramMap.put("price2", price2);
		paramMap.put("price3", price3);
		paramMap.put("content", content);
		paramMap.put("image", image);

		as.insertProduct(paramMap);

		return "redirect:/productList";
	}

	@GetMapping("/productUpdateForm")
	public ModelAndView productUpdateForm(@RequestParam("pseq") int pseq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("pseq", pseq);
		paramMap.put("ref_cursor", null);

		ps.getProduct(paramMap);

		HashMap<String, Object> pvo = ((ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")).get(0);

		String[] kindList = { "Heels", "Boots", "Sandals", "Sneakers", "Slippers", "OnSale" };

		mav.addObject("kindList", kindList);
		mav.addObject("kind", kindList[Integer.parseInt(pvo.get("KIND").toString()) - 1]);
		mav.addObject("productVO", pvo);
		mav.setViewName("admin/product/productUpdate");

		return mav;
	}

	@PostMapping("/productUpdate")
	public String productUpdate(@RequestParam("pseq") String pseq, @RequestParam("name") String name,
			@RequestParam("kind") String kind, @RequestParam("price1") int price1, @RequestParam("price2") int price2,
			@RequestParam("price3") int price3, @RequestParam("content") String content,
			@RequestParam(value = "useyn", required = false, defaultValue = "N") String useyn,
			@RequestParam(value = "bestyn", required = false, defaultValue = "N") String bestyn,
			@RequestParam("oldfilename") String oldfilename,
			@RequestParam(value = "image", required = false, defaultValue = "") String image,
			HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("pseq", pseq);
		paramMap.put("name", name);
		paramMap.put("kind", kind);
		paramMap.put("price1", price1);
		paramMap.put("price2", price2);
		paramMap.put("price3", price3);
		paramMap.put("content", content);
		paramMap.put("useyn", useyn);
		paramMap.put("bestyn", bestyn);
		paramMap.put("price3", price3);
		paramMap.put("image", image.equals("") ? oldfilename : image);

		as.updateProduct(paramMap);

		return "redirect:/adminProductDetail?pseq=" + pseq;
	}

	@GetMapping("/adminOrderList")
	public ModelAndView adminOrderList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> loginAdmin = (HashMap<String, Object>) request.getSession().getAttribute("loginAdmin");

		if (loginAdmin == null) {
			mav.setViewName("admin/adminLoginForm");
		} else {
			HashMap<String, Object> resultMap = as.getOrderList(request);

			mav.addObject("orderList", (ArrayList<HashMap<String, Object>>) resultMap.get("ref_cursor"));
			mav.addObject("paging", (Paging) resultMap.get("paging"));
			mav.addObject("key", (String) resultMap.get("key"));
			mav.setViewName("admin/order/orderList");
		}

		return mav;
	}

	@GetMapping("/orderUpdateResult")
	public String orderUpdateResult(@RequestParam("result") int[] result) {

		as.updateOrderResult(result);

		return "redirect:/adminOrderList";
	}

	@GetMapping("/memberList")
	public ModelAndView memberList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> loginAdmin = (HashMap<String, Object>) request.getSession().getAttribute("loginAdmin");

		if (loginAdmin == null) {
			mav.setViewName("admin/adminLoginForm");
		} else {
			HashMap<String, Object> resultMap = as.getMemberList(request);

			mav.addObject("memberList", (ArrayList<HashMap<String, Object>>) resultMap.get("ref_cursor"));
			mav.addObject("paging", (Paging) resultMap.get("paging"));
			mav.addObject("key", (String) resultMap.get("key"));
			mav.setViewName("admin/member/memberList");
		}

		return mav;
	}

	@GetMapping("/memberToggleUseyn")
	public String memberToggleUseyn(@RequestParam("userid") String userid, @RequestParam("useyn") String useyn) {

		useyn = useyn.equals("Y") ? "N" : "Y";

		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("userid", userid);
		paramMap.put("useyn", useyn);

		as.memberToggleUseyn(paramMap);

		return "redirect:/memberList";
	}

	@GetMapping("/adminQnaList")
	public ModelAndView adminQnaList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> loginAdmin = (HashMap<String, Object>) request.getSession().getAttribute("loginAdmin");

		if (loginAdmin == null) {
			mav.setViewName("admin/adminLoginForm");
		} else {
			HashMap<String, Object> resultMap = as.getQnaList(request);

			mav.addObject("qnaList", (ArrayList<HashMap<String, Object>>) resultMap.get("ref_cursor"));
			mav.addObject("paging", (Paging) resultMap.get("paging"));
			mav.addObject("key", (String) resultMap.get("key"));
			mav.setViewName("admin/qna/qnaList");
		}

		return mav;
	}

	@GetMapping("/adminQnaView")
	public ModelAndView adminQnaView(@RequestParam("qseq") int qseq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> loginAdmin = (HashMap<String, Object>) request.getSession().getAttribute("loginAdmin");

		if (loginAdmin == null) {
			mav.setViewName("admin/adminLoginForm");
		} else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("qseq", qseq);
			paramMap.put("ref_cursor", null);

			cs.getQna(paramMap);

			mav.addObject("qnaVO", ((ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")).get(0));

			mav.setViewName("admin/qna/qnaView");
		}

		return mav;
	}

	@PostMapping("/adminUpdateReply")
	public String adminUpdateReply(@RequestParam("qseq") int qseq, @RequestParam("reply") String reply,
			HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("qseq", qseq);
		paramMap.put("reply", reply);

		as.updateReply(paramMap);

		return "redirect:/adminQnaView?qseq=" + qseq;
	}
	
	@GetMapping("/adminBannerList")
	public ModelAndView adminBannerList() {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("ref_cursor", null);
		
		as.getBannerList(paramMap);
		
		mav.addObject("bannerList", paramMap.get("ref_cursor"));
		mav.setViewName("admin/banner/bannerList");
		
		return mav;
	}
	
	@GetMapping("/newBannerWrite")
	public String newBannerWrite() {
		return "admin/banner/writeBanner";
	}
	
	@PostMapping("/insertBanner")
	public String insertBanner(
			@RequestParam("oseq") int oseq,
			@RequestParam("subject") String subject,
			@RequestParam("image") String image) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("oseq", oseq);
		paramMap.put("subject", subject);
		paramMap.put("image", image);
		paramMap.put("useyn", oseq==6?"N":"Y");
		
		as.insertBanner(paramMap);
		
		return "redirect:/adminBannerList";
	}
}
