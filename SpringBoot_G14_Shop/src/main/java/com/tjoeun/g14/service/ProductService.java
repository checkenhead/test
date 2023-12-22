package com.tjoeun.g14.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.g14.dao.IProductDao;

@Service
public class ProductService {
	
	@Autowired
	IProductDao pdao;
	
	/* mybatis Direct SQL 방식
	public HashMap<String, Object> getBestNewList() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		result.put("newList", pdao.getNewList());
		result.put("bestList", pdao.getBestList());
		
		return result;
	}
	*/
	
	public void getBestNewList(HashMap<String, Object> paramMap) {
		pdao.getBestNewList(paramMap);
	}

	public void getKindList(HashMap<String, Object> paramMap) {
		pdao.getKindList(paramMap);
	}

	public void getProduct(HashMap<String, Object> paramMap) {
		pdao.getProductDetail(paramMap);
	}
}
