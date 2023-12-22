package com.tjoeun.g14.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tjoeun.g14.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	ProductService ps;
	
	@GetMapping("/")
	public ModelAndView root() {
		
		ModelAndView mav = new ModelAndView();
		/* mybatis Direct SQL 방식
		HashMap<String, Object> result = ps.getBestNewList();
		mav.addObject("newProductList", result.get("newList"));
		mav.addObject("bestProductList", result.get("bestList"));
		*/
		
		//Oracle Procedure 방식
		//paramMap : 프로시저에 전달될 커서변수를 포함하는 맵, 필요한 전달인수값들을 같이 넣고 전달하는 방식
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ref_cursor1", null);
		paramMap.put("ref_cursor2", null);
		
		ps.getBestNewList(paramMap); //프로시저 out변수로 사용할 객체를 담고있는 paramMap 전달
		
		//ArrayList<HashMap<String, Object>> list1 = (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor1");
		//ArrayList<HashMap<String, Object>> list2 = (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor2");
		mav.addObject("bestProductList", paramMap.get("ref_cursor1"));
		mav.addObject("newProductList", paramMap.get("ref_cursor2"));
		
		mav.setViewName("index");
		
		return mav;
	}
	
	@GetMapping("/category")
	public ModelAndView category(@RequestParam("kind") String kind) {
		ModelAndView mav = new ModelAndView();
		
		//해당 상품목록을 select
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("kind", kind);
		paramMap.put("ref_cursor", null);
		ps.getKindList(paramMap);
		
		mav.addObject("productKindList", paramMap.get("ref_cursor"));
		mav.setViewName("product/productKind");
		
		return mav;
	}
	
	@GetMapping("/productDetail")
	public ModelAndView productDetail(@RequestParam("pseq") int pseq) {
		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("pseq", pseq);
		paramMap.put("ref_cursor", null);
		ps.getProduct(paramMap);
		
		mav.addObject("productVO", ((ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor")).get(0));
		mav.setViewName("product/productDetail");
		
		return mav;
	}
}
