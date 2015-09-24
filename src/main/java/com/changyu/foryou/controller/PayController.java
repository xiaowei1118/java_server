package com.changyu.foryou.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.model.Order;
import com.changyu.foryou.service.FoodService;
import com.changyu.foryou.service.OrderService;
import com.changyu.foryou.service.PayService;
import com.changyu.foryou.service.PushService;
import com.changyu.foryou.service.UserService;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Refund;
import com.pingplusplus.model.Webhooks;

@Controller
@RequestMapping("/pay")
public class PayController {
	private PayService payService;
	private OrderService orderService;
    private UserService userService;
    private PushService pushService;
    private FoodService foodService;
    
    private static final Logger LOGGER = Logger
			.getLogger(PayController.class);
    
	public PushService getPushService() {
		return pushService;
	}

	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}

	@Autowired
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}
	
	public PayService getPayService() {
		return payService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Autowired
	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	/*@RequestMapping("/paytest")
    public @ResponseBody Map<String,Object> payTest(String togetherId,Integer amount){
    	Map<String,Object> resultMap=new HashMap<String,Object>();
    	Charge charge=ChargeInterface.charge(togetherId, amount);
    	resultMap.put("charge", charge);
    	return resultMap;
    }*/

	@RequestMapping("/webHooksForPayAndRefund")
	public void webHooksForPaySuccess(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException{
		request.setCharacterEncoding("UTF8");

		//获取头部所有信息
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println(key+" "+value);
		}
		// 获得 http body 内容
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String string;
		while ((string = reader.readLine()) != null) {
			buffer.append(string);
		}
		reader.close();
		// 解析异步通知数据
		Event event = Webhooks.eventParse(buffer.toString());
		if ("charge.succeeded".equals(event.getType())) {       //支付成功的回调			
			doPaySuccess(buffer.toString());      //事务处理
			response.setStatus(200);
		} else if ("refund.succeeded".equals(event.getType())) {  //退款的回调
			doRefundSuccess(buffer.toString());       //退款事务处理
			response.setStatus(200);
		} else {
			response.setStatus(500);
		}
	}
	
	
	/**
	 * 退款操作
	 * @param buffer
	 * @return
	 */
	private int doRefundSuccess(String buffer) {
		Refund refund = (Refund)Webhooks.parseEvnet(buffer); 
		LOGGER.info(JSON.toJSONString(refund));
		
		Map<String,Object> paramMap=new HashMap<String,Object>();

		String togetherId=refund.getOrderNo();
		paramMap.put("togetherId",togetherId);
		final double price=refund.getAmount()*1.0/100;
		
		Integer flag=orderService.updateOrderStatusRefundSuccess(paramMap);
		final String phone=orderService.getUserPhone(paramMap);        //根据订单号获取用户手机号
		new Thread(new Runnable() {               //开启极光推送，通知用户退款成功

			@Override public void run() { //向超级管理员推送，让其分发订单

				//推送 
				pushService.sendPush(phone,"您的一笔金额为"+price+"的订单已经退回到您的账户中，请及时查看。For优。", 5);

			} 
		}).start();
		
		return flag;
	}

	/**支付成功**/
	public int doPaySuccess(String buffer){
		Charge charge = (Charge)Webhooks.parseEvnet(buffer); 
		
		//System.out.println(JSON.toJSONString(charge));
		//获得charge对象
		Map<String,Object> paramMap=new HashMap<String,Object>();

		String chargeId=charge.getId();
		paramMap.put("togetherId",charge.getOrderNo());
		paramMap.put("amount",charge.getAmount()*1.0/100);
		paramMap.put("chargeId",chargeId);
		System.out.println(paramMap);
		int flag=orderService.updateOrderStatusAndAmount(paramMap);         //支付完成后更新订单状态以及更新价格 ,以及chargeId
		
		List<Order> orders = orderService.getAllOrdersByTogetherId(charge.getOrderNo()); // 获取该笔订单的消息				
		for(Order order:orders){
			paramMap.put("foodId", order.getFoodId());
			paramMap.put("orderCount", order.getOrderCount());
			paramMap.put("campusId",order.getCampusId());
			foodService.changeFoodCount(paramMap); // 增加销量，减少存货
	    }
		
		final Integer campusId=orderService.getCampusIdByTogetherId(paramMap);
		// 开启线程去访问极光推送

		new Thread(new Runnable() {

			@Override public void run() { //向超级管理员推送，让其分发订单

				//推送 
				//pushService.sendPushByTag("0","一笔新的订单已经到达，请前往选单中查看，并尽早分派配送员进行配送。For优。", 1);

				Map<String, Object> paramterMap=new HashMap<String,Object>();
				paramterMap.put("campusId",campusId);
				List<String> superPhones=userService.getAllSuperAdminPhone(paramterMap);
				for(String phone:superPhones){

					//推送
					pushService.sendPush(phone,"一笔新的订单已经到达，请前往选单中查看，并尽早分派配送员进行配送。for优。", 1); 
				}
			} 
		}).start();
		return flag;
	}
	
}
