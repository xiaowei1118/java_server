package com.changyu.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.controller.OrderController;
import com.changyu.foryou.model.CartGood;
import com.changyu.foryou.model.Order;
import com.changyu.foryou.model.SmallOrder;
import com.changyu.foryou.model.TogetherOrder;
import com.changyu.foryou.service.FoodService;
import com.changyu.foryou.service.OrderService;
import com.changyu.foryou.service.PushService;
import com.changyu.foryou.service.ReceiverService;
import com.changyu.foryou.service.UserService;
import com.changyu.foryou.tools.Constants;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class orderTest { 
	private static final Logger LOGGER = Logger
			.getLogger(orderTest.class);
	private OrderService orderService;
	private UserService userService;
	private FoodService foodService;
	private ReceiverService receiverService;

	private PushService pushService;

	@Autowired
	public void setReceiverService(ReceiverService receiverService) {
		this.receiverService = receiverService;
	}

	public PushService getPushService() {
		return pushService;
	}

	@Autowired
	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}

	@Autowired
	public void setUserServce(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}



	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
//	public void getOrderInMine(){
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		List<TogetherOrder> togetherOrdersList = new ArrayList<TogetherOrder>();
//
//		try {
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			//case 1
//			paramMap.put("phoneId", "18896554880");
//			paramMap.put("status", "a");
//		 
//		  
//			//case 2
//			
//			List<String> togetherIds = orderService.getTogetherId(paramMap);
//
//			System.out.println(JSON.toJSONString(togetherIds));
//
//			if (togetherIds.size() != 0) {
//				for (String togetherId : togetherIds) {
//					TogetherOrder togetherOrder = new TogetherOrder(); // 一单
//					togetherOrder.setTogetherId(togetherId);
//
//					paramMap.put("togetherId", togetherId);
//					List<SmallOrder> orderList = orderService
//							.getOrderListInMine(paramMap); // 一单里面的小订单
//					togetherOrder.setSmallOrders(orderList);
//					togetherOrder.setPayWay(orderList.get(0).getPayWay());
//					Short totalStatus=0;
//					if(orderList.get(0).getStatus()!=4)
//					{
//						totalStatus=orderList.get(0).getStatus();
//					}
//					else
//					{
//						totalStatus=5;
//						for (int i = 0; i < orderList.size(); i++) {
//							if (orderList.get(i).getIsRemarked() == 0) {
//								totalStatus = 4;
//							}
//						}
//					}
//					togetherOrder.setStatus(totalStatus);
//					togetherOrder.setTogetherDate(orderList.get(0)
//							.getTogetherDate());
//					togetherOrdersList.add(togetherOrder);
//				}
//
//				map.put(Constants.STATUS, Constants.SUCCESS);
//				map.put(Constants.MESSAGE, "获取订单成功");
//				map.put("orderList", JSON.parse(JSON
//						.toJSONStringWithDateFormat(togetherOrdersList,
//								"yyyy-MM-dd")));
//			} else {
//				map.put(Constants.STATUS, Constants.SUCCESS);
//				map.put(Constants.MESSAGE, "暂无订单");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			map.put(Constants.STATUS, Constants.FAILURE);
//			map.put(Constants.MESSAGE, "获取订单失败");
//		}
//		
//		LOGGER.debug("订单为:==================="+JSON.toJSONString(map));
//		return ;
//	}
	
	
	public void createOrder(){
		Map<String, Object> map = new HashMap<String, Object>();
//		Integer campusId=1;
//		String phoneId="18896554880";
//		Long foodId=(long) 10101;
//		case2
//		Integer campusId=4;
//		String phoneId="18896554880";
//		Long foodId=(long) 10101;
//		Integer foodCount=4;
//		case、3
//		Integer campusId=2;
//		String phoneId="18896554880";
//		Long foodId=(long) 10101;
//		Integer foodCount=0;
//		case4
//		Integer campusId=2;
//		String phoneId="18896554880";
//		Long foodId=(long) 111111111;
//		Integer foodCount=4;
//		case5
		Integer campusId=2;
		String phoneId="18896554880";
		Long foodId=(long) 10101;
		Integer foodCount=4;
		try {
			Order order = new Order(campusId, phoneId, foodId, foodCount);
			Long orderId=order.getOrderId();
			List<Order> oldOrders = orderService.selectOrder(order);

			// 待优化。。。。。。。将delete和insert改为一次操作
			if (oldOrders.size() != 0) {
				order.setOrderCount(foodCount
						+ oldOrders.get(0).getOrderCount());
				orderService.deleteCartGood(order);
			}
			int flag = orderService.insertSelectiveOrder(order);

			if (flag != -1 && flag != 0) {
				map.put("orderId", orderId);
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "生成订单成功");
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "生成订单失败");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "生成订单失败");
		}
		LOGGER.debug("+====================="+JSON.toJSONString(map)+"+============================================================");
		return ;
	}

	
	public void getOrderInMine() {
//		Short status=null;
//		String phoneId="18896554880";
//		case2
//		Short status=1;
	//	String phoneId="18896554880";
//		case3
//		Short status=2;
	//	String phoneId="18896554880";
//		case4
//		Short status=3;
	//	String phoneId="18896554880";
//		case5
//		Short status=4;
	//	String phoneId="18896554880";
//		case6
//		Short status=5;
	//	String phoneId="18896554880";
//     	case7
		Short status=null;
		String phoneId="18896554888";
		Map<String, Object> map = new HashMap<String, Object>();
		List<TogetherOrder> togetherOrdersList = new ArrayList<TogetherOrder>();

		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("phoneId", phoneId);
			paramMap.put("status", status);
			List<String> togetherIds = orderService.getTogetherId(paramMap);

			System.out.println(JSON.toJSONString(togetherIds));

			if (togetherIds.size() != 0) {
				for (String togetherId : togetherIds) {
					TogetherOrder togetherOrder = new TogetherOrder(); // 一单
					togetherOrder.setTogetherId(togetherId);

					paramMap.put("togetherId", togetherId);
					List<SmallOrder> orderList = orderService
							.getOrderListInMine(paramMap); // 一单里面的小订单
					togetherOrder.setSmallOrders(orderList);
					togetherOrder.setPayWay(orderList.get(0).getPayWay());
					Short totalStatus=0;
					if(orderList.get(0).getStatus()!=4)
					{
						totalStatus=orderList.get(0).getStatus();
					}
					else
					{
						totalStatus=5;
						for (int i = 0; i < orderList.size(); i++) {
							if (orderList.get(i).getIsRemarked() == 0) {
								totalStatus = 4;
							}
						}
					}
					togetherOrder.setStatus(totalStatus);
					togetherOrder.setTogetherDate(orderList.get(0)
							.getTogetherDate());
					togetherOrdersList.add(togetherOrder);
				}

				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取订单成功");
				map.put("orderList", JSON.parse(JSON
						.toJSONStringWithDateFormat(togetherOrdersList,
								"yyyy-MM-dd")));
			} else {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "暂无订单");
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取订单失败");
		}
		LOGGER.debug("+====================="+JSON.toJSONString(map)+"+============================================================");
		return ;
	}
	
	/**
	 * 获取购物车里面的收藏订单（未完成订单）
	 * 
	 * @param phoneId
	 *            手机号
	 * @return
	 */
	public void getUserOrder() {
		Map<String, Object> map = new HashMap<String, Object>();
		String phoneId="18896554880";
				Integer campusId=1;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("phoneId", phoneId);
			paramMap.put("campusId", campusId);

		
			List<CartGood> orderList = orderService.getOrderList(paramMap);
			if (orderList != null && orderList.size() != 0) {
				for (CartGood cartGood : orderList) {
					if (cartGood.getIsDiscount() != 0) {
						cartGood.setDiscountPrice(cartGood.getDiscountPrice()); // 获取折扣价
					}
					paramMap.put("foodId", cartGood.getFoodId());
					paramMap.put("campusId", campusId);
				}
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取购物车订单成功");
				map.put("orderList", orderList);
			} else {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "购物车里暂时还没有订单哦，亲");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取购物车订单失败");
		}
		LOGGER.debug("+====================="+JSON.toJSONString(map)+"+============================================================");
		return ;
	}
	
	
	/**
	 * 删除购物车中的某一订单
	 * 
	 * @param orderId
	 * @return
	 */
	public void  deleteUserOrder()
			 {
//				 Long orderId=Long.parseLong("1439952912983");
//				 String phoneId="18896554880";
//		case2
//		Long orderId=Long.parseLong("143995291298");
//		 String phoneId="18896554880";
//		 case3
			Long orderId=Long.parseLong("1439954475217");
			 String phoneId="18896554888";
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			int flag = orderService.deleteUserOrder(orderId, phoneId);
			if (flag != -1 && flag != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "删除订单成功");
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "删除订单失败");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "删除订单失败");
		}
		LOGGER.debug("+====================="+JSON.toJSONString(map)+"+============================================================");
		return;
	}
	
	
	/**
	 * 删除多条订单，订单号用逗号隔开
	 * 
	 * @param orderId
	 * @param phoneId
	 * @return
	 */
	
	public void deleteAllUserOrder(
			) {
		Map<String, Object> map = new HashMap<String, Object>();
//	    String orderId="1439952985662,1439954460497";
//	    String phoneId="18896554880";
//		case2
//		   String orderId="1439889845846";
//		    String phoneId="18896554880";
//		case3
//		   String orderId="143988984584";
//		    String phoneId="18896554880";
//		case4
		   String orderId="1439629286227,143988984584";
		    String phoneId="18035735959";
		try {
			int flag = 0;
			String[] orderIdStrings = orderId.split(",");
			for (String oneOrderId : orderIdStrings) {
				if (oneOrderId != null && !oneOrderId.trim().equals("")) {
					flag = orderService.deleteUserOrder(
							Long.valueOf(oneOrderId), phoneId);

					if (flag == -1 || flag == 0) {
						map.put(Constants.STATUS, Constants.FAILURE);
						map.put(Constants.MESSAGE, "删除订单失败");
						break;
					}
				}
			}

			if (flag != -1 && flag != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "删除订单成功");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "删除订单失败");
		}
	    LOGGER.debug("+====================="+JSON.toJSONString(map)+"+============================================================");
		

		return ;
	}
	
	
	/**
	 * 编辑订单的个数
	 * 
	 * @param orderId
	 *            订单号
	 * @param orderCount
	 *            订单数
	 * @return
	 */
	@RequestMapping("/editUserOrder")
	public void editUserOrder(
			) {
		Map<String, Object> map = new HashMap<String, Object>();
//		Long orderId=Long.parseLong("1439950791000") ;
//		String phoneId="18860902563";
//		case2
//		Long orderId=Long.parseLong("1439950791000") ;
//		String phoneId="18860902573";
//		Integer orderCount=4;
//		case3
		Long orderId=Long.parseLong("1439950791001") ;
		String phoneId="18860902563";
		Integer orderCount=4;
		try {
			int flag = orderService.editUserOrder(orderId, phoneId, orderCount);
			if (flag != 0 && flag != -1) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "编辑订单成功");
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "编辑订单失败");
			}

		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "编辑订单失败");
		}
		 LOGGER.debug("+====================="+JSON.toJSONString(map)+"+============================================================");
		return ;
	}
	
	
	@Test
	@RequestMapping("/orderToBuy")
	public void changeOrderStatus2Buy() {
//		String phoneId="18860902563";
//		String orderId="1439783739000,1439950791000";
//		String reserveTime="sssssss";
//		String message="ssssss";
//		Short payWay=1;
//		String rank="1438681568";
//		case2
//		String phoneId="18860902573";
//		String orderId="1439783739000,1439950791000";
//		String reserveTime="sssssss";
//		String message="ssssss";
//		Short payWay=1;
//		String rank="1438681568";
//		case3
//		String phoneId="18860902563";
//		String orderId="1439783739001,1439950791000";
//		String reserveTime="sssssss";
//		String message="ssssss";
//		Short payWay=1;
//		String rank="1438681568";
////		case4
//		String phoneId="18860902563";
//		String orderId="1439783739001,1439950791001";
//		String reserveTime="sssssss";
//		String message="ssssss";
//		Short payWay=1;
//		String rank="1438681568";
//		case5
//		String phoneId="18860902563";
//		String orderId="1439783739000,1439950791000";
//		String reserveTime=null;
//		String message="ssssss";
//		Short payWay=1;
//		String rank="1438681568";
////		case6
//		String phoneId="18860902563";
//		String orderId="1439783739000,1439950791000";
//		String reserveTime="sssssss";
//		String message=null;
//		Short payWay=1;
//		String rank="1438681568";
//		case7
//		String phoneId="18860902563";
//		String orderId="1439783739000,1439950791000";
//		String reserveTime="sssssss";
//		String message="ssssss";
//		Short payWay=1;
//		String rank="1438681568";
//		case8
		String phoneId="18860902563";
		String orderId="1439783739000,1439950791000";
		String reserveTime="sssssss";
		String message="ssssss";
		Short payWay=1;
		String rank="143868158";
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		/*
		 * map.put(Constants.STATUS, Constants.FAILURE);
		 * map.put(Constants.MESSAGE, "暑期间米奇暂停运营，非常抱歉，亲，下学期再见喽。");
		 */
		try {
			Calendar calendar = Calendar.getInstance();
			// 判断是否超出营业时间，营业时间为9：00--21：30
			/*
			 * if(calendar.get(Calendar.HOUR_OF_DAY)>22||(calendar.get(Calendar.
			 * HOUR_OF_DAY
			 * )>=21&&calendar.get(Calendar.MINUTE)>30)||calendar.get(
			 * Calendar.HOUR_OF_DAY)<9){ map.put(Constants.STATUS,
			 * Constants.FAILURE); map.put(Constants.MESSAGE,
			 * "米奇的营业时间为9：00--21：30，欢迎下次光顾。"); return map; }
			 */

			String[] orderString = orderId.split(",");
			int flag = 0;
			String togetherId = phoneId + String.valueOf(new Date().getTime());

			// boolean tag=true;
			for (String id : orderString) {
				// 这里做写入单价操作，还没有写！！！

				flag = orderService.changeOrderStatus2Buy(phoneId, id,
						togetherId, rank, reserveTime, message,payWay);

				// 更新库存和销量
				Order order = orderService.selectOneOrder(phoneId, id); // 获取该笔订单的消息

				paramMap.put("foodId", order.getFoodId());
				paramMap.put("orderCount", order.getOrderCount());
				foodService.changeFoodCount(paramMap); // 增加销量，减少存货

				if (flag == 0 || flag == -1) {
					break;
				}
			}

			if (flag != -1 && flag != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "下单成功，即将开始配送！");

				// 开启线程去访问极光推送
				/*
				 * new Thread(new Runnable() {
				 * 
				 * @Override public void run() { //向超级管理员推送，让其分发订单
				 * 
				 * //推送 //pushService.sendPushByTag("0",
				 * "一笔新的订单已经到达，请前往选单中查看，并尽早分派配送员进行配送。米奇零点。", 1);
				 * 
				 * Map<String, Object> paramterMap=new HashMap<String,Object>();
				 * List<String>
				 * superPhones=userService.getAllSuperAdminPhone(paramterMap);
				 * for(String phone:superPhones){
				 * 
				 * //推送 pushService.sendPush(phone,
				 * "一笔新的订单已经到达，请前往选单中查看，并尽早分派配送员进行配送。米奇零点。", 1); } } }).start();
				 */

			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "下单失败，请重新开始下单");
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "下单失败，请重新开始下单");
		}
		 LOGGER.debug("+====================="+JSON.toJSONString(map)+"+============================================================");
		return ;
	}
	

}
