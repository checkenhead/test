package com.tjoeun.g14.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.g14.dao.ICustomerDao;

@Service
public class CustomerService {
	@Autowired
	ICustomerDao cdao;

	public void getQnaList(HashMap<String, Object> paramMap) {
		cdao.getQnaList(paramMap);
	}

	public void getQna(HashMap<String, Object> paramMap) {
		cdao.getQna(paramMap);
	}

	public void insertQna(HashMap<String, Object> paramMap) {
		cdao.insertQna(paramMap);
	}
}
