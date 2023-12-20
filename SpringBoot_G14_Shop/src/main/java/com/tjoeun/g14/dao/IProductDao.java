package com.tjoeun.g14.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tjoeun.g14.dto.ProductVO;

@Mapper
public interface IProductDao {

	/* mybatis Direct SQL 방식
	public List<ProductVO> getNewList();
	public List<ProductVO> getBestList();
	*/
	
	public void getBestNewList(HashMap<String, Object> paramMap);

}
