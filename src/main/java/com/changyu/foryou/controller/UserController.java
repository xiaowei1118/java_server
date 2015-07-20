package com.changyu.foryou.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.changyu.foryou.model.Feedback;
import com.changyu.foryou.model.Users;
import com.changyu.foryou.service.OrderService;
import com.changyu.foryou.service.UserService;
import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.Md5;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;
	private OrderService orderService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * 取得用户信息
	 * @param phone
	 * @return 
	 */
	@RequestMapping(value="/getUser",method=RequestMethod.POST)
	public @ResponseBody
	Users selectUserById(String phone) {
		return userService.selectByUsername(phone);
	}

	/**
	 * 将某一用户设置为管理员
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="setUserAdmin")
	public @ResponseBody Integer setUserAdmin(@RequestParam String phone){
		return userService.setUserAdmin(phone);
	}

	/**
	 * 将某一用户设为常规用户
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="setUserCommon")
	public @ResponseBody Integer setUserCommon(@RequestParam String phone){
		return userService.setUserCommon(phone);
	}

	/**
	 * 将某一用户设置为超级管理员
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="setUserSuperAdmin")
	public @ResponseBody Integer setUserSuperAdmin(@RequestParam String phone){
		return userService.setUserSuperAdmin(phone);
	}

	/**
	 * 取得所有用戶信息
	 * @return
	 */
	@RequestMapping(value="/getAllUser")
	public @ResponseBody
	Map<String, Object> getAllUser(Integer limit,Integer offset,String sort,String order,String search) {
		Map<String, Object> map = new HashMap<String, Object>();

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
		return map;
	}

	/**
	 * 用户登陆
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/toLogin",method=RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> toLogin(@RequestParam String phone,@RequestParam String password,HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (phone!=null&&password!=null&&!phone.trim().equals("") && !password.trim().equals("")) {
			Users users = userService.selectByUsername(phone);
			if (users != null) {
				if (users.getPassword().equals(Md5.GetMD5Code(password))) {
					map.put(Constants.STATUS, Constants.SUCCESS);
					map.put(Constants.MESSAGE, "登陆成功");
					map.put("type", users.getType());
					HttpSession session=request.getSession();
					session.setAttribute("type", users.getType());
					session.setAttribute("phone", users.getPhone());
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

		return map;
	}

	/**
	 * 检查用户是否已经注册
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/checkUserIsExist",method=RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> checkUserIsExist(@RequestParam String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userService.selectByUsername(phone) != null) {
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "该账号已经被注册");
		} else {
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "该账号可以注册");
		}
		return map;
	}

	/**
	 * 用户注册
	 * @param phone
	 * @param password
	 * @param nickname
	 * @return
	 */
	@RequestMapping(value="/registerIn",method=RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> registerIn(@RequestParam String phone,
			@RequestParam String password, @RequestParam String nickname) {
		Map<String, Object> map = new HashMap<String, Object>();

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
			return map;
		}

		return map;
	}


	/**
	 * 用户更改密码
	 * @param phone
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/resetPassword",method=RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> resetPassword(@RequestParam String phone,
			@RequestParam String newPassword) {
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
		return map;
	}

	/**
	 * 更新用户个人信息
	 * @param phone
	 * @param password
	 * @param nickname
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/updateUserInfo")
	public @ResponseBody Map<String,Object> updateUserInfo(@RequestParam String phone,String nickname,String type){
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Users users=new Users();
			users.setPhone(phone);
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
		return map;
	}


	/**
	 * 获取我的用户总信息
	 * @param phoneId 用户id
	 * @return
	 */
	@RequestMapping(value="mineInfo")
	public @ResponseBody Map<String, Object> getMineInfo(@RequestParam String phone){
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			Users users=userService.selectByUsername(phone);
			Map<String, Object> counts=orderService.getOrderSummaryCount(phone);

			if(users!=null){		
				users.setPassword(null);
				map.put("userInfo", users);
				map.put("waitDeliveryOrder", counts.get("wait"));
				map.put("waitReceiveOrder",counts.get("deliver"));
				map.put("waitCommentOrder", counts.get("comment"));
				map.put(Constants.STATUS,Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取数据成功");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.MESSAGE, "获取用户信息失败！");
			map.put(Constants.STATUS, Constants.FAILURE);
		}

		return map;
	}

	/**
	 * 提交用户反馈信息
	 * @param phoneId
	 * @param suggestion
	 * @return
	 */
	@RequestMapping(value="feedbackMessage")
	public @ResponseBody Map<String, Object> feedbackMessage(@RequestParam String phoneId,@RequestParam String suggestion){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Feedback feedback=new Feedback();
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

		return map;
	}


	/**
	 * 更新用户头像
	 * @param image
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/uploadUserImage")
	public @ResponseBody Map<String, Object> uploadNewsImage(@RequestParam String image, HttpServletRequest request)throws IOException{
		String phone=request.getParameter("phoneId");

		Map<String, Object> map = new HashMap<String, Object>();
		image=image.replaceAll(" ", "+");

		String realPath = request.getSession().getServletContext().getRealPath("/"); 		
		realPath=realPath.replace("SJFood", "MickeyImage");
		realPath=realPath.concat("/users/");
		String fileNameString=new Random().nextLong()+""+new Date().getTime()+".jpg";


		if (image == null) {
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "没有文件");
		}

		@SuppressWarnings("restriction")
		BASE64Decoder decoder = new BASE64Decoder();
		try {


			//Base64解码
			@SuppressWarnings("restriction")
			byte[] b = decoder.decodeBuffer(image);
			for(int i=0;i< b.length;++i)
			{
				if(b[i]< 0)
				{//调整异常数据
					b[i]+=256;
				}
			}
			//生成jpeg图片
			String imgFilePath = realPath+fileNameString;//新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);    
			out.write(b);
			out.flush();
			out.close();

			//删除原头像
			String orignUrl=userService.getImageUrl(phone);
			if(orignUrl!=null){
				String[] temp=orignUrl.split("/");
				String imageName=temp[(temp.length-1)];

				String name=realPath+imageName;

				File file=new File(name);
				if(file.isFile()){
					file.delete();//删除
				}
			}

			String imageUrl=Constants.localIp+"/users/"+fileNameString;
			Integer flag=userService.updateImageUrl(imageUrl, phone);
			if(flag==1){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "头像更新成功!");
			}else{
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "头像更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "头像更新失败");
		}

		return map;
	}


	@RequestMapping("/uploadAndroidLog")
	public @ResponseBody Map<String, Object> uploadAndroidLog(@RequestParam String log, HttpServletRequest request)throws IOException{

		Map<String, Object> map = new HashMap<String, Object>();

		String realPath = request.getSession().getServletContext().getRealPath("/"); 		
		realPath=realPath.replace("SJFood", "MickeyImage");
		realPath=realPath.concat("/android/");
		String fileNameString=new Random().nextInt(100)+""+new Date().getTime()+"log.log";

		if (log == null) {
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "没有文件");
			return map;
		}

		@SuppressWarnings("restriction")
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//Base64解码
			@SuppressWarnings("restriction")
			byte[] b = decoder.decodeBuffer(log);
			for(int i=0;i< b.length;++i)
			{
				if(b[i]< 0)
				{//调整异常数据
					b[i]+=256;
				}
			}
			//生成jpeg图片
			String imgFilePath = realPath+fileNameString;//新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);    
			out.write(b);
			out.flush();
			out.close();

			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "log日志上传成功!");

		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "log日志上传失败！");
		}

		return map;
	}

	/**
	 * 获取配送员名单
	 * @return
	 */
	@RequestMapping(value="getDeliverAdmin")
	public @ResponseBody Map<String,Object> getDeliverAdmin() {
		Map<String, Object> map=new HashMap<String, Object>();

		try{
			List<Users> users=userService.getDeliverAdmin();
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "获取配送员成功!");
			map.put("deliverAdmins", users);
		}catch(Exception e){
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取配送员失败！");
		}

		return map;
	}

	/**
	 * ios post token到数据表
	 * @param phoneId
	 * @param token
	 * @return
	 */
	@RequestMapping(value="postIOSToken")
	public @ResponseBody Map<String, Object> postIosToken(String phoneId,String token){
		Map<String, Object> map=new HashMap<String, Object>();

		try{
			userService.clearOldToken(token);                   //如果该token以前别的用户使用过，就将该
			int flag=userService.setUserToken(phoneId,token);
			
			if(flag!=0&flag!=-1){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "设置用户token成功!");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "设置用户token失败");
		}

		return map;
	}

	/**
	 * 获取用户反馈
	 * @return
	 */
	@RequestMapping(value="/getFeedbacks")
	public @ResponseBody JSONArray getFeedbacks(){
		try {
			List<Feedback> feedbacks=userService.getFeedbacks();
			return JSONArray.parseArray(JSON.toJSONStringWithDateFormat(feedbacks, "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 忘记密码的跳转
	 * @return
	 */
	@RequestMapping(value="/forgetPassword")
	public  String forgetPassword(){	
		return "redirect:/kidding.html";
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout")
	public  String logout(HttpServletRequest request){	
		request.getSession().removeAttribute("phone");
		return "redirect:/login.html";
	}
	
	/**
	 * 获取用户类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getUserType")
	public @ResponseBody Short getUserType(HttpServletRequest request){
		return (Short) request.getSession().getAttribute("type");
	}
	
	/**
	 * 获取用户设备占比
	 */
	@RequestMapping(value="/getUserDevicePercent")
	public @ResponseBody Map<String,Object> getUserDevicePercent(){
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
		return resultMap;
	}
}
