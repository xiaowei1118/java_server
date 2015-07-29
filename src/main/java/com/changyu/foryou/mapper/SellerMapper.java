package com.changyu.foryou.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.changyu.foryou.model.Sellers;

public interface SellerMapper {


	Sellers selectByCampusAdmin(String campusAdmin);

	void updateLastLoginTime(@Param(value="date")Date date, @Param(value="campusAdmin") String campusAdmin
			);

	int insertSellective(Sellers seller); 

}
