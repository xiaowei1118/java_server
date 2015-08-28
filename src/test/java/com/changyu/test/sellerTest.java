package com.changyu.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.model.Sellers;
import com.changyu.foryou.service.SellerService;
import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.Md5;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class sellerTest {
	private static final Logger LOGGER = Logger
			.getLogger(sellerTest.class);
	private SellerService sellerService;

	public SellerService getSellerService() {
		return sellerService;
	}

	@Autowired
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	

	/**
	 * 商家登录
	 * @param campusAdmin
	 * @param password
	 * */

	@Test
	public void toLogin() {
//		case1
//		String campusAdmin="18860902711";
//		String password="123456";
//		case2
//		String campusAdmin="18860902711";
//		String password="1236";
//		case3
		String campusAdmin="18860902722";
		String password="123456";
		Map<String, Object> map = new HashMap<String, Object>();
		if (campusAdmin != null && password != null
				&& !campusAdmin.trim().equals("")
				&& !password.trim().equals("")) {
			Sellers sellers = sellerService.selectByCampusAdmin(campusAdmin);
			if (sellers != null) {
				//if (sellers.getPassword().equals(Md5.GetMD5Code(password))) {
				if (sellers.getPassword().equals(password)) {
					map.put(Constants.STATUS, Constants.SUCCESS);
					map.put(Constants.MESSAGE, "登陆成功");
					map.put("type", sellers.getType());
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
		LOGGER.debug("+++++++++++==================="+JSON.toJSONString(map)+"_____________________________________________________");
		return ;
	}
	
	/**
	 * 根据商家id查找商家数据
	 
	 * */
	
	@Test
	public void getSellerById()
	{
//		String campusAdmin="18860902711";
//		case2
		String campusAdmin="18860902712";
		Map<String, Object> map = new HashMap<String, Object>();
		Sellers sellers = sellerService.selectByCampusAdmin(campusAdmin);
		map.put("seller",sellers);	
		LOGGER.debug("+++++++++++==================="+JSON.toJSONString(map)+"_____________________________________________________");
		return ;
	}
	
	/**
	 * 商家注册
	 * @param campusAdmin
	 * @param password
	 * @param campusId
	 * @return
	 */
	
    @Test
	public void registerIn()
	{
//		case1.2
//		String campusAdmin="18860902573";
//		String password="123456";
//		Integer campusId=1;
//		case3
//		String campusAdmin="18860902574";
//		String password="   ";
//		Integer campusId=1;
//		case4
		String campusAdmin="  ";
		String password="123456";
		Integer campusId=1;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!campusAdmin.equals("")	&& !password.equals("")) 
			{
				String passwordMd5 = Md5.GetMD5Code(password);
				Sellers seller = new Sellers();
				seller.setCampusAdmin(campusAdmin);
				seller.setPassword(passwordMd5);
				seller.setCampusId(campusId);
				sellerService.addASeller(seller);	
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "注册成功");
			    }				
				else
				{
					map.put(Constants.STATUS, Constants.FAILURE);
					map.put(Constants.MESSAGE, "注册失败2");
				}
			}
		    catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "注册失败");

		}
		LOGGER.debug("+++++++++++==================="+JSON.toJSONString(map)+"_____________________________________________________");
		return ;
	}	
	

}
