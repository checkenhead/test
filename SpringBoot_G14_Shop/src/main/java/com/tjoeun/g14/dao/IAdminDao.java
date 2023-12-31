package com.tjoeun.g14.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminDao {

	void getAdmin(HashMap<String, Object> paramMap);

	void adminGetAllCount(HashMap<String, Object> paramMap);

	void getProductList(HashMap<String, Object> paramMap);

	void insertProduct(HashMap<String, Object> paramMap);

	void updateProduct(HashMap<String, Object> paramMap);

	void getOrderList(HashMap<String, Object> paramMap);

	void updateResult(int odseq);

	void getMemberList(HashMap<String, Object> paramMap);

	void memberToggleUseyn(HashMap<String, Object> paramMap);

	void updateReply(HashMap<String, Object> paramMap);

	void getAdminQnaList(HashMap<String, Object> paramMap);

	void getBannerList(HashMap<String, Object> paramMap);

	void insertBanner(HashMap<String, Object> paramMap);

}
