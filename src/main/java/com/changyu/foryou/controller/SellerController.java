package com.changyu.foryou.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changyu.foryou.model.Sellers;
import com.changyu.foryou.service.SellerService;
import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.Md5;

@Controller
@RequestMapping("/seller")
public class SellerController {
	private SellerService sellerService;

	public SellerService getSellerService() {
		return sellerService;
	}

	@Autowired
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	
	/**
	 * 测试
	 * */
	@RequestMapping("/hello")
	public @ResponseBody Map<String,Object> sayHello()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		int flag=sellerService.test();
		map.put("flag",flag);
		return map;
	}
	
	
	/**
	 * 商家登录
	 * @param campusAdmin
	 * @param password
	 * */
	@RequestMapping("/toLogin")
	public @ResponseBody Map<String, Object> toLogin(
			@RequestParam String campusAdmin, @RequestParam String password,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (campusAdmin != null && password != null
				&& !campusAdmin.trim().equals("")
				&& !password.trim().equals("")) {
			Sellers sellers = sellerService.selectByCampusAdmin(campusAdmin);
			if (sellers != null) {
				if (sellers.getPassword().equals(Md5.GetMD5Code(password))) {
					map.put(Constants.STATUS, Constants.SUCCESS);
					map.put(Constants.MESSAGE, "登陆成功");
					map.put("type", sellers.getType());
					HttpSession session = request.getSession();
					session.setAttribute("type", sellers.getType());
					session.setAttribute("campusAdmin",
							sellers.getCampusAdmin());
					Date date = new Date();
					sellerService.updateLastLoginTime(date, campusAdmin);
				} else {
					map.put(Constants.STATUS, Constants.FAILURE);
					map.put(Constants.MESSAGE, "账号或密码错误，请检查后输入");
				}
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "账号或密码错误，请检查后输入");
			}
		}

		return map;
	}
	
	/**
	 * 根据商家id查找商家数据
	 
	 * */
	
	@RequestMapping("/getSellerById")
	public @ResponseBody Map<String, Object> getSellerById(String campusAdmin)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Sellers sellers = sellerService.selectByCampusAdmin(campusAdmin);
		map.put("seller",sellers);		
		return map;
	}
	
	@RequestMapping("/checkSellerIsExist")
	public @ResponseBody Map<String, Object> checkSellerIsExist(String campusAdmin)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Sellers sellers = sellerService.selectByCampusAdmin(campusAdmin);
		if(sellers==null)
		{
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "该用户名不存在，可以注册");
		}
		else
		{
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "该用户名已存在，请换一个名字");			
		}
		
		return map;	
	}
	

	
	
}