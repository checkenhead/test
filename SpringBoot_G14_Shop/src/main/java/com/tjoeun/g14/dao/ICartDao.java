package com.tjoeun.g14.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICartDao {

	void insertCart(HashMap<String, Object> paramMap);

	void getCartList(HashMap<String, Object> paramMap);

	void deleteCart(String cseq);

}
