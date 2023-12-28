package com.tjoeun.g14.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOrderDao {

	void insertOrder(HashMap<String, Object> paramMap);

	void getOrderByOseq(HashMap<String, Object> paramMap);

	void insertOrderByPseq(HashMap<String, Object> paramMap);

	void getOseqListIng(HashMap<String, Object> paramMap);

	void getOseqListAll(HashMap<String, Object> paramMap);

	void orderEnd(int odseq);

}
