package com.tjoeun.g14.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.g14.dao.IAdminDao;
import com.tjoeun.g14.dto.Paging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {
	@Autowired
	IAdminDao adao;

	public void getAdmin(HashMap<String, Object> paramMap) {
		adao.getAdmin(paramMap);
	}

	public void getProductList(HashMap<String, Object> paramMap) {
		HttpServletRequest request = (HttpServletRequest) paramMap.get("request");
		HttpSession session = request.getSession();

		int page = 1;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", page);
		} else if (session.getAttribute("page") != null) {
			page = (int) session.getAttribute("page");
		} else {
			session.removeAttribute("page");
		}

		String key = "";

		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
			session.setAttribute("key", key);
		} else if (session.getAttribute("key") != null) {
			key = (String) session.getAttribute("key");
		} else {
			session.removeAttribute("key");
		}

		Paging paging = new Paging();

		paging.setPage(page);

		paramMap.put("cnt", 0);
		paramMap.put("key", key);
		paramMap.put("tablename", "product");

		adao.adminGetAllCount(paramMap);

		paging.setTotalCount(Integer.parseInt(paramMap.get("cnt").toString()));
		paging.calPaging();

		paramMap.put("startNum", paging.getStartNum());
		paramMap.put("endNum", paging.getEndNum());
		paramMap.put("key", key);

		adao.getProductList(paramMap);

		paramMap.put("paging", paging);
	}

	public void insertProduct(HashMap<String, Object> paramMap) {
		adao.insertProduct(paramMap);
	}

	public void updateProduct(HashMap<String, Object> paramMap) {
		adao.updateProduct(paramMap);
	}

	public HashMap<String, Object> getOrderList(HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();

		if (request.getParameter("first") != null) {
			session.removeAttribute("page");
			session.removeAttribute("key");
		}

		int page = 1;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", page);
		} else if (session.getAttribute("page") != null) {
			page = (int) session.getAttribute("page");
		} else {
			session.removeAttribute("page");
		}

		String key = "";

		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
			session.setAttribute("key", key);
		} else if (session.getAttribute("key") != null) {
			key = (String) session.getAttribute("key");
		} else {
			session.removeAttribute("key");
		}

		Paging paging = new Paging();

		paging.setPage(page);

		paramMap.put("cnt", 0);
		paramMap.put("key", key);
		paramMap.put("tablename", "order_view");

		adao.adminGetAllCount(paramMap);

		paging.setTotalCount(Integer.parseInt(paramMap.get("cnt").toString()));
		paging.calPaging();

		paramMap.put("startNum", paging.getStartNum());
		paramMap.put("endNum", paging.getEndNum());
		paramMap.put("key", key);
		paramMap.put("ref_cursor", null);

		adao.getOrderList(paramMap);

		paramMap.put("paging", paging);

		return paramMap;
	}

	public void updateOrderResult(int[] result) {
		for (int odseq : result) {
			adao.updateResult(odseq);
		}
	}

	public HashMap<String, Object> getMemberList(HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();

		if (request.getParameter("first") != null) {
			session.removeAttribute("page");
			session.removeAttribute("key");
		}

		int page = 1;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", page);
		} else if (session.getAttribute("page") != null) {
			page = (int) session.getAttribute("page");
		} else {
			session.removeAttribute("page");
		}

		String key = "";

		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
			session.setAttribute("key", key);
		} else if (session.getAttribute("key") != null) {
			key = (String) session.getAttribute("key");
		} else {
			session.removeAttribute("key");
		}
		
		Paging paging = new Paging();
		
		paging.setPage(page);
		
		paramMap.put("cnt", 0);
		paramMap.put("key", key);
		paramMap.put("tablename", "member");
		
		adao.adminGetAllCount(paramMap);
		
		paging.setTotalCount(Integer.parseInt(paramMap.get("cnt").toString()));
		paging.calPaging();
		
		paramMap.put("startNum", paging.getStartNum());
		paramMap.put("endNum", paging.getEndNum());
		paramMap.put("key", key);
		paramMap.put("ref_cursor", null);
		
		adao.getMemberList(paramMap);
		
		paramMap.put("paging", paging);
		
		return paramMap;
	}

	public void memberToggleUseyn(HashMap<String, Object> paramMap) {
		adao.memberToggleUseyn(paramMap);
	}

	public HashMap<String, Object> getQnaList(HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();

		if (request.getParameter("first") != null) {
			session.removeAttribute("page");
			session.removeAttribute("key");
		}

		int page = 1;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", page);
		} else if (session.getAttribute("page") != null) {
			page = (int) session.getAttribute("page");
		} else {
			session.removeAttribute("page");
		}

		String key = "";

		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
			session.setAttribute("key", key);
		} else if (session.getAttribute("key") != null) {
			key = (String) session.getAttribute("key");
		} else {
			session.removeAttribute("key");
		}
		
		Paging paging = new Paging();
		
		paging.setPage(page);
		
		paramMap.put("cnt", 0);
		paramMap.put("key", key);
		paramMap.put("tablename", "qna");
		
		adao.adminGetAllCount(paramMap);
		
		paging.setTotalCount(Integer.parseInt(paramMap.get("cnt").toString()));
		paging.calPaging();
		
		paramMap.put("startNum", paging.getStartNum());
		paramMap.put("endNum", paging.getEndNum());
		paramMap.put("key", key);
		paramMap.put("ref_cursor", null);
		
		adao.getAdminQnaList(paramMap);
		
		paramMap.put("paging", paging);
		
		return paramMap;
	}

	public void updateReply(HashMap<String, Object> paramMap) {
		adao.updateReply(paramMap);
	}

	public void getBannerList(HashMap<String, Object> paramMap) {
		adao.getBannerList(paramMap);
	}

	public void insertBanner(HashMap<String, Object> paramMap) {
		adao.insertBanner(paramMap);
	}
}
