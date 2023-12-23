package com.tjoeun.g14.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.g14.dao.ICartDao;

@Service
public class CartService {
	@Autowired
	ICartDao cdao;

	public void insertCart(HashMap<String, Object> paramMap) {
		cdao.insertCart(paramMap);
	}

	public void getCartList(HashMap<String, Object> paramMap) {
		cdao.getCartList(paramMap);
		
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		
		int totalPrice = 0;
		
		for(HashMap<String, Object> cart : list) {
			totalPrice += Integer.parseInt(cart.get("QTY").toString()) * Integer.parseInt(cart.get("PRICE2").toString());
		}
		
		paramMap.put("totalPrice", totalPrice);
	}

	public void deleteCart(HashMap<String, Object> paramMap) {
		String[] cseqArr = (String[])paramMap.get("cseqArr");
		
		for(String cseq : cseqArr) {
			cdao.deleteCart(cseq);
		}
	}
	
}
