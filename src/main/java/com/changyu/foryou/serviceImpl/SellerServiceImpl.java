package com.changyu.foryou.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changyu.foryou.mapper.SellerMapper;
import com.changyu.foryou.model.Sellers;
import com.changyu.foryou.service.SellerService;

/**
 * 商家账号管理
 * @author xiaowei
 *
 */
@Service("sellerService")
public class SellerServiceImpl implements SellerService {

	private SellerMapper sellerMapper;//操作用户信息

	@Autowired
	public void setSellerMapper(SellerMapper sellerMapper) {
		this.sellerMapper = sellerMapper;
	}



	
	public Sellers selectByCampusAdmin(String campusAdmin) {
		return sellerMapper.selectByCampusAdmin(campusAdmin);
	}


	public void updateLastLoginTime(Date date, String campusAdmin) {
		sellerMapper.updateLastLoginTime(date,campusAdmin);
		
	}
	
	public void addASeller(Sellers seller)
	{
		sellerMapper.insertSellective(seller);
	}

}
