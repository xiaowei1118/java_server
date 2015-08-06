package com.changyu.foryou.service;

import java.util.Date;

import com.changyu.foryou.model.Sellers;


public interface SellerService {

	public Sellers selectByCampusAdmin(String campusAdmin);

	public void updateLastLoginTime(Date date, String campusAdmin);

	public void addASeller(Sellers seller);
}
