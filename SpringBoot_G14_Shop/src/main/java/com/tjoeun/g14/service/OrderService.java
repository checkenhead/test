package com.tjoeun.g14.service;

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
}
