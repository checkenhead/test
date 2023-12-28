package com.tjoeun.g14.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.g14.dao.IOrderDao;

@Service
public class OrderService {
	@Autowired
	IOrderDao odao;

	public void insertOrder(HashMap<String, Object> paramMap) {
		odao.insertOrder(paramMap);
	}

	public void getOrderByOseq(HashMap<String, Object> paramMap) {
		odao.getOrderByOseq(paramMap);
		int totalPrice = 0;
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
		
		for(HashMap<String, Object> ovo : list) {
			totalPrice += Integer.parseInt(ovo.get("PRICE2").toString()) * Integer.parseInt(ovo.get("QTY").toString());
		}
		
		paramMap.put("totalPrice", totalPrice);
		paramMap.put("orderDetail", list.get(0));
	}

	public void insertOrderByPseq(HashMap<String, Object> paramMap) {
		odao.insertOrderByPseq(paramMap);
	}

	public void getOrderListIng(HashMap<String, Object> paramMap) {
		odao.getOseqListIng(paramMap);
		paramMap.put("finalList", toSumup(paramMap));
	}

	public void getOrderListAll(HashMap<String, Object> paramMap) {
		odao.getOseqListAll(paramMap);
		paramMap.put("finalList", toSumup(paramMap));
	}
	
	private ArrayList<HashMap<String, Object>> toSumup(HashMap<String, Object> paramMap) {
		ArrayList<HashMap<String, Object>> finalList = new ArrayList<HashMap<String, Object>>();
		
		for(HashMap<String, Object> vo : (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor")) {
			int oseq = Integer.parseInt(vo.get("OSEQ").toString());

			paramMap.put("oseq", oseq);
			paramMap.put("ref_cursor", null);
			odao.getOrderByOseq(paramMap);
			
			ArrayList<HashMap<String, Object>> ordervo = (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
			
			HashMap<String, Object> fistItem = ordervo.get(0);
			
			fistItem.put("PNAME", fistItem.get("PNAME").toString() + (ordervo.size()>1?(" 외 " + (ordervo.size()-1) + "건"):(" 1건")));
			
			int totalPrice = 0;
			
			for(HashMap<String, Object> item : ordervo) {
				totalPrice += Integer.parseInt(item.get("PRICE2").toString()) * Integer.parseInt(item.get("QTY").toString());
			}
			
			fistItem.put("PRICE2", totalPrice);
			
			finalList.add(fistItem);
		}
		
		return finalList;
	}

	public void orderEnd(int odseq) {
		odao.orderEnd(odseq);
	}
}