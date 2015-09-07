package com.changyu.test;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.alibaba.fastjson.JSONArray;
import com.changyu.foryou.model.Feedback;
import com.changyu.foryou.model.Users;
import com.changyu.foryou.service.OrderService;
import com.changyu.foryou.service.UserService;
import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.Md5;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class userTest {
	private static final Logger LOGGER = Logger
			.getLogger(userTest.class);
	
	private UserService userService;
	private OrderService orderService;
	public OrderService getOrderService() {
		return orderService;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public UserService getUserservice() {
		return userService;
	}

	@Autowired
	public void setUserservice(UserService userservice) {
		this.userService = userservice;
	}
	
	
	//取得用户信息
	
	public void selectUserById() {
		//case1
//		String phone = "18896554880";
//		case2
		String phone = "18860902573";
		LOGGER.debug("用户信息为:==================="+JSON.toJSONString(userService.selectByUsername(phone)));
		return ;
	}
	
	// 将某一用户设置为管理员
		
	public void setUserAdmin(){
//		case1
//		String phone = "18896554880";
//		case2
		String phone = "18860902573";
		LOGGER.debug("==================="+JSON.toJSONString(userService.setUserAdmin(phone,2)));
		return ;
	}
	
	/**
	 * 将某一用户设为常规用户
	 * @param phone
	 * @return
	 */
	
	@Test
	public void setUserCommon(){
//		case1
//		String phone = "18896554880";
//		case2
		String phone = "18860902573";
		LOGGER.debug("==================="+JSON.toJSONString(userService.setUserCommon(phone,2)));
		return ;
	}

	/**
	 * 将某一用户设置为超级管理员
	 * @param phone
	 * @return
	 */
	
	public void setUserSuperAdmin(){
//		case1
		String phone = "18896554880";
//		case2
//		String phone = "18860902573";
		LOGGER.debug("==================="+JSON.toJSONString(userService.setUserSuperAdmin(phone,2)));
		return ;
	}

	/**
	 * 取得所有用戶信息
	 * @return
	 */
	
	public 
	void getAllUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer limit=null;
		Integer offset=null;
//		case1
//		String order="desc";
//		String sort="lastLoginDate";
//		String search="188";
//		case2
//		String order="desc";
//		String sort="lastLoginDate";
//		String search="小未";
//		case3
//		String order="desc";
//		String sort="lastLoginDate";
//		String search="小未11111111";
//		case4
//		String order="desc";
//		String sort="createTime";
//		String search="188";
//		case5
//		String order="desc";
//		String sort="defaultAddress";
//		String search="188";
//		case6
//		String order="desc";
//		String sort=null;
//		String search="188";
//		case7
//		String order="desc";
//		String sort="11111";
//		String search="188";
//		case8
//		String order="asc";
//		String sort="createTime";
//		String search="188";
//		case9
		String order="111";
		String sort="createTime";
		String search="188";
		
		

		if(sort!=null&&sort.equals("lastLoginDate")){
			sort="last_login_date";
		}

		if(sort!=null&&sort.equals("createTime")){
			sort="create_time";
		}
		
		if(sort!=null&&sort.equals("defaultAddress")){
			sort="default_address";
		}
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
		}

		JSONArray  json=JSONArray.parseArray(JSON.toJSONStringWithDateFormat(userService.getAllUser(limit,offset,sort,order,search), "yyyy-MM-dd"));
		map.put("total", userService.getUserCount(search));
		map.put("rows", json);
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}

	
	/**
	 * 用户登陆
	 * @param phone
	 * @param password
	 * @return
	 */
	
	public void toLogin() {
		String phone=null;
		String password=null;
		Map<String, Object> map = new HashMap<String, Object>();
//		case1
//		phone= "18896554880";
//		password="123456789";
//		case2
//		phone= "1889655488";
//		password="123456789";
//		case3
		phone= "18896554880";
		password="12345678";

		if (phone!=null&&password!=null&&!phone.trim().equals("") && !password.trim().equals("")) {
			Users users = userService.selectByUsername(phone);
			if (users != null) {
				if (users.getPassword().equals(Md5.GetMD5Code(password))) {
					map.put(Constants.STATUS, Constants.SUCCESS);
					map.put(Constants.MESSAGE, "登陆成功");
					map.put("type", users.getType());
//					HttpSession session=request.getSession();
//					session.setAttribute("type", users.getType());
//					session.setAttribute("phone", users.getPhone());
					Date date=new Date();
					userService.updateLastLoginTime(date,phone);					
				} else {
					map.put(Constants.STATUS, Constants.FAILURE);
					map.put(Constants.MESSAGE, "账号或密码错误，请检查后输入");
				}
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "账号或密码错误，请检查后输入");
			}
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}
	
	/**
	 * 检查用户是否已经注册
	 * @param phone
	 * @return
	 */
	
	public void checkUserIsExist() {
		String phone = null;
		Map<String, Object> map = new HashMap<String, Object>();
//		case1
//		phone="18896554880";
//		case2
		phone="18896554881";
		if (userService.selectByUsername(phone) != null) {
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "该账号已经被注册");
		} else {
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "该账号可以注册");
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}
	
	/**
	 * 用户注册
	 * @param phone
	 * @param password
	 * @param nickname
	 * @return
	 */
	
	public void registerIn() {
		 String phone=null;
		 String password=null;
		 String nickname=null;
		Map<String, Object> map = new HashMap<String, Object>();
//		case1
//		phone="18812345678";
//		password="1111";
//		nickname="11";
//		case2
//		phone="188145678";
//		password="1111";
//		nickname="11";
//		case3
//		password="1111";
//		nickname="11";
//		case4
//		phone="18812345678";
//		nickname="11";
//		case5
		phone="18812345678";
		password="1111";

		
		
		try {
			if (!phone.equals("") && phone.length() == 11
					&& !password.equals("")) {
				String passwordMd5=Md5.GetMD5Code(password);

				Users users = new Users(phone, passwordMd5, nickname);
				userService.addUsers(users);
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "注册成功");
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "注册失败");
			}
		} catch (Exception e) {
			map.put(Constants.STATUS, Constants.FAILURE); // 捕捉异常
			map.put(Constants.MESSAGE, "注册失败");
			LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
			return ;
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}
	
	 /** 用户更改密码
	 * @param phone
	 * @param newPassword
	 * @return
	 */
	
	public void resetPassword() {
//		case1
//		String phone="18896554880";
//		String newPassword="123456789";

		String phone="18896554888";
		String newPassword="123456789";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Users users = userService.selectByUsername(phone);
			if (users != null) {
				userService.updatePassword(phone,Md5.GetMD5Code(newPassword));  //对密码做md5加密
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "修改密码成功");
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "修改密码失败");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "修改密码失败");
			
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
	
		return;
	}
	
	
	/**
	 * 更新用户个人信息
	 * @param phone
	 * @param password
	 * @param nickname
	 * @param type
	 * @return
	 */
	
	public void updateUserInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
//		case1
//	    String phone="18896554880";
//		String nickname="我是小未";
//		String type="1";
//		String sex="1";
//		String academy="计算机";
//		String qq="1111111111";
//		String weiXin="111";
//		case2
//		 String phone="18896554888";
//			String nickname="我是小未";
//			String type="1";
//			String sex="1";
//			String academy="计算机";
//			String qq="1111111111";
//			String weiXin="111";
//		case3
//		 String phone="18896554880";
//			String nickname=null;
//			String type="1";
//			String sex="1";
//			String academy="计算机";
//			String qq="1111111111";
//			String weiXin="111";
//		case4:
		
//		String phone="18896554880";
//		String nickname="我是小未";
//		String type="3";
//     	String sex="1";
//		String academy="计算机";
//		String qq="1111111111";
//		String weiXin="111";
//		CASE5
//		String phone="18896554880";
//		String nickname="我是小未";
//		String type="2";
//     	String sex=null;
//		String academy="计算机";
//		String qq="1111111111";
//		String weiXin="111";
//		case6
//		String phone="18896554880";
//		String nickname="我是小未";
//		String type="2";
//     	String sex="3";
//		String academy="计算机";
//		String qq="1111111111";
//		String weiXin="111";
//		case7
//		String phone="18896554880";
//		String nickname="我是小未";
//		String type="1";
//		String sex="1";
//		String academy=null;
//		String qq="1111111111";
//		String weiXin="111";
//		case8
//		String phone="18896554880";
//		String nickname="我是小未";
//		String type="1";
//		String sex="1";
//		String academy="计算机";
//		String qq=null;
//		String weiXin="111";
//		case9
//		String phone="18896554880";
//		String nickname="我是小未";
//		String type="1";
//		String sex="1";
//		String academy="计算机";
//		String qq="1ss1111111";
//		String weiXin="111";
//		case10
		String phone="18896554880";
		String nickname="我是小未";
		String type="1";
		String sex="1";
		String academy="计算机";
		String qq="1111111111";
		String weiXin=null;
		try {
			Users users=new Users();
			users.setPhone(phone);
			if(weiXin!=null)
			{
				users.setWeiXin(weiXin);
			}
			
			if(qq!=null)
			{
				users.setQq(qq);
			}
			
			if(academy!=null)
			{
				users.setAcademy(academy);
			}
			
			if(sex!=null){
				users.setSex((short)Integer.parseInt(sex));
			}
			
			if(nickname!=null){
				users.setNickname(nickname);
			}


			if(type!=null){
				users.setType((short)Integer.parseInt(type));
			}

			if(userService.updateUserInfo(users)!=-1){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "修改用户信息成功");
			}else{
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "修改用户信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "修改用户信息失败");
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}
	
	
	public void getMineInfo(){
//		case1
//		String phone="18896554880";
//		case2
		String phone="18896554888";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Users users=userService.selectByUsername(phone);
			Map<String, Object> counts=orderService.getOrderSummaryCount(phone);
			paramMap.put("phoneId", phone);
			paramMap.put("status", 1);
			List<String> togetherId1=orderService.getTogetherId(paramMap);
			paramMap.put("status", 2);
			List<String> togetherId2=orderService.getTogetherId(paramMap);
			paramMap.put("status", 3);
			List<String> togetherId3=orderService.getTogetherId(paramMap);
			paramMap.put("status", 4);
			List<String> togetherId4=orderService.getTogetherId(paramMap);
			paramMap.put("status", 5);
			List<String> togetherId5=orderService.getTogetherId(paramMap);
			if(users!=null){		
				users.setPassword(null);
				map.put("userInfo", users);
				//map.put("waitDeliveryOrder", counts.get("wait"));
				//map.put("waitReceiveOrder",counts.get("deliver"));
				//map.put("waitCommentOrder", counts.get("comment"));
							
				map.put(Constants.STATUS,Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取数据成功");
				map.put("waitPayOrder", togetherId1.size());
				map.put("waitMakeSureOrder", togetherId2.size());
				map.put("distribution", togetherId3.size());
				map.put("waitCommentOrder", togetherId4.size());
				map.put("doneOrder", togetherId5.size());
			
			}	
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.MESSAGE, "获取用户信息失败！");
			map.put(Constants.STATUS, Constants.FAILURE);
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}
	
	
	/**
	 * 提交用户反馈信息
	 * @param phoneId
	 * @param suggestion
	 * @return
	 */
	
	public void feedbackMessage(){
		Map<String, Object> map = new HashMap<String, Object>();
////		case1
//		Integer campusId=1;
//		String phoneId="18896554880";
//		String suggestion="你们的网站做得很好";
//		case2
//		Integer campusId=s;
//		String phoneId="18896554880";
//		String suggestion="你们的网站做得很好";
////		case3
//		Integer campusId=1;
//    	String phoneId="18896554888";
//		String suggestion="你们的网站做得很好";
//		case4
		Integer campusId=1;
    	String phoneId="18896554888";
		String suggestion="";
		
		try{
			Feedback feedback=new Feedback();
			feedback.setCampusId(campusId);
			feedback.setPhoneId(phoneId);
			feedback.setSuggestion(suggestion);
			Calendar calendar=Calendar.getInstance();
			Date date=calendar.getTime();   //设置反馈时的日期
			feedback.setDate(date);

			if(userService.addFeedbackSuggestion(feedback)!=-1){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "添加意见成功");
			}else{
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "添加意见失败");
			}
		}catch(Exception e){
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "添加意见失败");
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}
	
	/**
	 * 获取配送员名单
	 * @return
	 */
	
	public void getDeliverAdmin() {
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("campusId", 1);
		
		try{
			List<Users> users=userService.getDeliverAdmin(paramMap);
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "获取配送员成功!");
			map.put("deliverAdmins", users);
		}catch(Exception e){
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取配送员失败！");
		}
		LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
		return ;
	}
	
	/**
	 * ios post token到数据表
	 * @param phoneId
	 * @param token
	 * @return
	 */
	
	/**
	 * 获取用户设备占比
	 */
	public void getUserDevicePercent(){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		
		try {
			DecimalFormat df = new DecimalFormat("##.0");
		    Map<String,Object> paramMap=new HashMap<String,Object>();
		    paramMap.put("device","0");   //设备号0表示ios
			Integer iosCount=userService.getCountsByDevice(paramMap);
			paramMap.put("device","1");
			Integer androidCount=userService.getCountsByDevice(paramMap);
			resultMap.put("android",Float.valueOf(df.format(androidCount*1.0/(androidCount+iosCount)*100)));
			resultMap.put("ios",Float.valueOf(df.format(iosCount*1.0/(androidCount+iosCount)*100)));
		} catch (Exception e) {
			e.getStackTrace();
		}
		LOGGER.debug("==================="+JSON.toJSONString(resultMap)+"======================================================");
		return ;
	}
	
	/**
	 * 获取用户反馈
	 * @return
	 */
	public void getFeedbacks(){
		try {
			Integer campusId=2;
			Map<String, Object>paramMap=new HashMap<String, Object>();
			paramMap.put("campusId", campusId);
			
			
			List<Feedback> feedbacks=userService.getFeedbacks(paramMap);
			System.out.println(JSONArray.parseArray(JSON.toJSONStringWithDateFormat(feedbacks, "yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ;
	}

	@Test
	/**
	 * 用户更改密码(用老密码更改）
	 * @param phone
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	
	public void changePassword()
			{
//		case1
//		String phone="18896554880";
//		String oldPassword="123456789";
//		String newPassword="123456789";
////		case2
//		String phone="18896554888";
//		String oldPassword="123456789";
//		String newPassword="123456789";
//		case3
		String phone="18896554880";
		String oldPassword="1234567";
		String newPassword="123456789";		
		Map<String, Object> map=new HashMap<String, Object>();
				try {
					Map<String, Object> paramMap=new HashMap<String, Object>();
					paramMap.put("phone",phone);
					String passwordMd5=Md5.GetMD5Code(oldPassword);
					paramMap.put("password",passwordMd5);
					List<Users> users=userService.selectByPhoneAndPassword(paramMap);
					if(users.size()==0)
					{
						map.put(Constants.STATUS, Constants.FAILURE);
						map.put(Constants.MESSAGE, "更改失败，原密码错误");
					}
					else
					{
						userService.updatePassword(phone,Md5.GetMD5Code(newPassword));  //对密码做md5加密
						map.put(Constants.STATUS, Constants.SUCCESS);
						map.put(Constants.MESSAGE, "修改密码成功");
					}	
					
				} catch (Exception e) {
					e.printStackTrace();	
					map.put(Constants.STATUS, Constants.FAILURE);
					map.put(Constants.MESSAGE, "修改密码失败");					
				}
				LOGGER.debug("==================="+JSON.toJSONString(map)+"======================================================");
				return ;	
	}
	
}
