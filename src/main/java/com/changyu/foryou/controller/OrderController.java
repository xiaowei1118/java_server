package com.changyu.foryou.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.changyu.foryou.model.BigOrder;
import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CartGood;
import com.changyu.foryou.model.DeliverChildOrder;
import com.changyu.foryou.model.DeliverOrder;
import com.changyu.foryou.model.Order;
import com.changyu.foryou.model.PCOrder;
import com.changyu.foryou.model.Preferential;
import com.changyu.foryou.model.Receiver;
import com.changyu.foryou.model.SmallOrder;
import com.changyu.foryou.model.SuperAdminOrder;
import com.changyu.foryou.model.TogetherOrder;
import com.changyu.foryou.service.CampusService;
import com.changyu.foryou.service.FoodService;
import com.changyu.foryou.service.OrderService;
import com.changyu.foryou.service.PushService;
import com.changyu.foryou.service.ReceiverService;
import com.changyu.foryou.service.UserService;
import com.changyu.foryou.tools.Constants;

/**
 * 处理订单控制器
 * 
 * @author 殿下
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	private OrderService orderService;
	private UserService userService;
	private FoodService foodService;
	private ReceiverService receiverService;
	private CampusService  campusService;
	private PushService pushService;

	@Autowired
	public void setReceiverService(ReceiverService receiverService) {
		this.receiverService = receiverService;
	}

	@Autowired
	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
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

	protected static final Logger LOG = LoggerFactory
			.getLogger(OrderController.class);

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * 生成购物车订单
	 * 
	 * @param phoneId
	 * @param foodId
	 * @param foodCount
	 * @param foodSpecial
	 * @return
	 */
	@RequestMapping("/createOrder")
	public @ResponseBody Map<String, Object> createOrder(
			@RequestParam Integer campusId, @RequestParam String phoneId,
			@RequestParam Long foodId, @RequestParam Integer foodCount){
		Map<String, Object> map = new HashMap<String, Object>();

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

		return map;
	}

	/**
	 * 获取下达的所有订单
	 * 
	 * @param phoneId
	 *            ,status
	 * @return
	 */
	@RequestMapping("/getOrderInMine")
	public @ResponseBody Map<String, Object> getOrderInMine(
			@RequestParam String phoneId, Short status, Integer page,
			Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TogetherOrder> togetherOrdersList = new ArrayList<TogetherOrder>();

		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("phoneId", phoneId);
			paramMap.put("status", status);
			if (limit != null && page != null) {
				paramMap.put("limit", limit);
				paramMap.put("offset", (page - 1) * limit);
			}
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
					togetherOrder.setTotalPrice(orderList.get(0).getTotalPrice());
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
		return map;
	}

	/**
	 * 弃用 获取下达的代发货订单
	 * 
	 * @param phoneId
	 * @return
	 */
	@RequestMapping("/getOrderInMineWait2Deliver")
	public @ResponseBody Map<String, Object> getOrderInMineWait2Deliver(
			@RequestParam String phoneId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<SmallOrder> orderList = orderService
					.getOrderListInMineWait2Deliver(phoneId);
			if (orderList.size() != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取代发货订单成功");
				map.put("orderList", orderList);
			} else {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "暂无代发货订单");
			}

		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取代发货订单失败");
		}
		return map;
	}

	/**
	 * 弃用 获取下达的正在配送订单
	 * 
	 * @param phoneId
	 * @return
	 */
	@RequestMapping("/getOrderInMineDeliver")
	public @ResponseBody Map<String, Object> getOrderInMineDeliver(
			@RequestParam String phoneId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<SmallOrder> orderList = orderService
					.getOrderListInMineDeliver(phoneId);
			if (orderList.size() != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取正在配送订单成功");
				map.put("orderList", orderList);
			} else {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "暂无订单配送中");
			}

		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取配送订单失败");
		}
		return map;
	}

	/**
	 * 弃用 获取完成订单
	 * 
	 * @param phoneId
	 * @return
	 */
	@RequestMapping("/getOrderInMineFinish")
	public @ResponseBody Map<String, Object> getOrderInMineFinish(
			@RequestParam String phoneId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<SmallOrder> orderList = orderService
					.getOrderListInMineFinish(phoneId);
			if (orderList.size() != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取完成订单成功");
				map.put("orderList", orderList);
			} else {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "暂无完成订单");
			}

		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取完成订单失败");
		}
		return map;
	}

	/**
	 * 获取购物车里面的收藏订单（未完成订单）
	 * 
	 * @param phoneId
	 *            手机号
	 * @return
	 */
	@RequestMapping("/getUserOrder")
	public @ResponseBody Map<String, Object> getUserOrder(
			@RequestParam String phoneId, @RequestParam Integer campusId,
			Integer limit, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("phoneId", phoneId);
			paramMap.put("campusId", campusId);

			if (limit != null && page != null) {
				paramMap.put("limit", limit);
				paramMap.put("offset", (page - 1) * limit);
			}
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

		return map;
	}

	/**
	 * 删除购物车中的某一订单
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/deleteUserOrder")
	public @ResponseBody Map<String, Object> deleteUserOrder(
			@RequestParam Long orderId, @RequestParam String phoneId) {
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

		return map;
	}

	/**
	 * 删除多条订单，订单号用逗号隔开
	 * 
	 * @param orderId
	 * @param phoneId
	 * @return
	 */
	@RequestMapping("/deleteAllUserOrder")
	public @ResponseBody Map<String, Object> deleteAllUserOrder(
			@RequestParam String orderId, @RequestParam String phoneId) {
		Map<String, Object> map = new HashMap<String, Object>();

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

		return map;
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
	public @ResponseBody Map<String, Object> editUserOrder(
			@RequestParam Long orderId, @RequestParam String phoneId,
			@RequestParam Integer orderCount) {
		Map<String, Object> map = new HashMap<String, Object>();

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
		return map;
	}

	/**
	 * 获取成功订单
	 * 
	 * @param phoneId
	 *            用户手机号
	 * @return
	 */
	@RequestMapping("/getUserSuccessOrder")
	public @ResponseBody Map<String, Object> getUserSuccessOrder(
			@RequestParam String phoneId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Order> orderList = orderService.getOrderSuccessList(phoneId);
			if (orderList != null && orderList.size() != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "获取订单成功");
				map.put("orderList", orderList);
			} else {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "暂时还没有订单哦，亲");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取订单失败");
		}

		return map;
	}

	/**
	 * 改变订单至下单状态
	 * 
	 * @param phoneId
	 * @param orderId
	 * @param message
	 *            留言
	 * @param reserveTime
	 *            预约送达时间
	 * @return
	 */
	@RequestMapping("/orderToBuy")
	public @ResponseBody Map<String, Object> changeOrderStatus2Buy(
			@RequestParam String phoneId, @RequestParam String orderId,
			@RequestParam String rank, String reserveTime, String message,@RequestParam Short payWay,
			@RequestParam Float totalPrice ,Integer preferentialsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		try {
			Calendar calendar = Calendar.getInstance();

			String[] orderString = orderId.split(",");
			int flag = 0;
			String togetherId = phoneId + String.valueOf(new Date().getTime());

			paramMap.put("orderId",orderString[0]);
			paramMap.put("phoneId",phoneId);
			Campus campus=campusService.getCampus(paramMap);   //根据订单获取该校区的详细情况

			//判断该校区是否正在营业
			if(campus.getStatus()==0){
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, campus.getCloseReason());
				return map;
			}

			Calendar runOpenTime=Calendar.getInstance();
			runOpenTime.setTime(campus.getOpenTime());
			Calendar runCloseTime=Calendar.getInstance();
			runCloseTime.setTime(campus.getCloseTime());
			//判断是否超出校区营业时间

			int openHour=runOpenTime.get(Calendar.HOUR_OF_DAY);     
			int openMinute=runOpenTime.get(Calendar.MINUTE);
			int closeHour=runCloseTime.get(Calendar.HOUR_OF_DAY);
			int closeMinute=runCloseTime.get(Calendar.MINUTE);

			if(calendar.get(Calendar.HOUR_OF_DAY)>closeHour
					||(calendar.get(Calendar.HOUR_OF_DAY)==closeHour&&calendar.get(Calendar.MINUTE)>closeMinute)
					||calendar.get(Calendar.HOUR_OF_DAY)<openHour
					||(calendar.get(Calendar.HOUR_OF_DAY)==openHour&&calendar.get(Calendar.MINUTE)<runOpenTime.get(openMinute))
					){ 
				StringBuffer message2=new StringBuffer();
				message2.append("fou优的营业时间为"+openHour+":");

				if(openMinute<10){
					message2.append("0"+openMinute);
				}else{
					message2.append(openMinute);
				}
				message2.append("--"+closeHour+":");
				if(openMinute<10){
					message2.append("0"+closeMinute);
				}else{
					message2.append(closeMinute);
				}

				map.put(Constants.STATUS,Constants.FAILURE); 
				map.put(Constants.MESSAGE,message2.toString()); 
				return map; 
			}
			
					//写入单价操作
			for (String id : orderString) {
				float price=(float) 0.0;
				paramMap.put("orderId",id);
				SmallOrder smallOrder=orderService.getOrderById(paramMap);
				if(smallOrder.getIsDiscount()==1)
				{
					price = (smallOrder.getDiscountPrice()*smallOrder.getOrderCount());
				}
				else
				{
					price = smallOrder.getPrice()*smallOrder.getOrderCount();
				}		
				flag = orderService.changeOrderStatus2Buy(phoneId, id,
						togetherId, rank, reserveTime, message,payWay,price,totalPrice);				
				Order order = orderService.selectOneOrder(phoneId, id); // 获取该笔订单的消息				
				paramMap.put("foodId", order.getFoodId());
				paramMap.put("orderCount", order.getOrderCount());
				paramMap.put("campusId",campus.getCampusId());
				foodService.changeFoodCount(paramMap); // 增加销量，减少存货
				if (flag == 0 || flag == -1) {
					break;
				}
			}

			DecimalFormat df = new DecimalFormat("0.0");
			if (flag != -1 && flag != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "下单成功，即将开始配送！");
				map.put("totalPrice",df.format(totalPrice));

				// 开启线程去访问极光推送

				new Thread(new Runnable() {

					@Override public void run() { //向超级管理员推送，让其分发订单

						//推送 
						//pushService.sendPushByTag("0","一笔新的订单已经到达，请前往选单中查看，并尽早分派配送员进行配送。For优。", 1);

						Map<String, Object> paramterMap=new HashMap<String,Object>();
						List<String>
						superPhones=userService.getAllSuperAdminPhone(paramterMap);
						for(String phone:superPhones){

							//推送
							pushService.sendPush(phone,"一笔新的订单已经到达，请前往选单中查看，并尽早分派配送员进行配送。for优。", 1); 
						}
					} 
				}).start();


			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "下单失败，请重新开始下单");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "下单失败，请重新开始下单");
		}

		return map;
	}

	/**
	 * 改变订单至配送状态
	 * 
	 * @param phoneId
	 * @param orderId
	 * @return
	 */
	@Deprecated
	@RequestMapping("/orderToDeliver")
	public @ResponseBody Map<String, Object> changeOrderStatus2Deliver(
			@RequestParam String adminPhone,
			@RequestParam final String togetherId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			int flag = orderService.changeOrderStatus2Deliver(adminPhone,
					togetherId);

			if (flag != -1 && flag != 0) {

				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "正在配送中！");

				// 开启线程访问服务器进行推送

				new Thread(new Runnable() {

					@Override public void run() { //推送
						String userPhone=userService.getUserPhone(togetherId);
						System.out.println(userPhone);
						pushService.sendPush(userPhone,
								"您有一笔订单正在配送中,请稍候。感谢您对For优的支持", 1);

					} }).start();


			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "点击配送失败，请重试");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "点击配送失败，请重试！");
		}
		return map;
	}

	/**
	 * 修改订单状态至完成
	 * 
	 * @param phoneId
	 * @param togetherId
	 * @return
	 */
	@Deprecated
	@RequestMapping("/orderToFinish")
	public @ResponseBody Map<String, Object> changeOrderStatus2Finish(
			@RequestParam String adminPhone, @RequestParam String togetherId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int flag = orderService.changeOrderStatus2Finish(adminPhone,
					togetherId);
			if (flag != -1 && flag != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "订单完成，谢谢您的惠顾！");

				final String userPhone = userService.getUserPhone(togetherId);


				new Thread(new Runnable() {

					@Override public void run() { //推送
						pushService.sendPush(userPhone,
								"您有一笔订单已完成交易,赶快去评价吧！For优欢迎您下次惠顾", 1);

					} }).start();


			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "点击完成订单失败，请重试");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "点击完成订单失败，请重试");
		}
		return map;
	}

	//
	// /**========================================================
	// * 超级管理员获取待发货单信息
	// * @param isSelected
	// * @return
	// */
	// @RequestMapping("/superAdminGetOrder")
	// public @ResponseBody Map<String, Object> superAdminGetOrder(@RequestParam
	// Integer isSelected){
	// Map<String, Object> map=new HashMap<String, Object>();
	// try {
	// List<SuperAdminOrder> orders=orderService.superAdminGetOrder(isSelected);
	//
	// for(SuperAdminOrder superAdminOrder:orders){
	// String togetherId=superAdminOrder.getTogetherId();
	//
	// List<DeliverChildOrder>
	// deliverChildOrders=orderService.getDeliverChildOrders(togetherId);
	// Float priceFloat=0f;
	//
	// //获取该笔订单总价
	// for(DeliverChildOrder deliverChildOrder:deliverChildOrders){
	// if(deliverChildOrder.getIsDiscount()==0){
	// priceFloat+=deliverChildOrder.getPrice()*deliverChildOrder.getOrderCount();
	// }else{
	// priceFloat+=deliverChildOrder.getDiscountPrice()*deliverChildOrder.getOrderCount();
	// }
	// }
	// superAdminOrder.setPrice(priceFloat);
	// }
	// map.put(Constants.STATUS, Constants.SUCCESS);
	// map.put(Constants.MESSAGE, "获取订单成功！");
	// map.put("orderList",JSONArray.parse(JSON.toJSONStringWithDateFormat(orders,
	// "yyyy-MM-dd")));
	// } catch (Exception e) {
	// map.put(Constants.STATUS, Constants.FAILURE);
	// map.put(Constants.MESSAGE, "获取订单失败！");
	// }
	//
	// return map;
	// }
	//
	//

	/**
	 * 超级管理员获取待发货单信息
	 * 
	 * @param isSelected
	 * @return
	 */
	@RequestMapping("/superAdminGetOrder")
	public @ResponseBody Map<String, Object> superAdminGetOrder(
			@RequestParam Integer isSelected, @RequestParam Integer campusId,
			Integer limit, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("isSelected", isSelected);
		requestMap.put("campusId", campusId);

		if (page != null && limit != null) {
			requestMap.put("limit", limit);
			requestMap.put("offset", (page - 1) * limit);
		} else {
			
		}
		try {
			List<SuperAdminOrder> orders = orderService
					.superAdminGetOrder(requestMap);
			for (SuperAdminOrder superAdminOrder : orders) {
				String togetherId = superAdminOrder.getTogetherId();

				List<DeliverChildOrder> deliverChildOrders = orderService
						.getDeliverChildOrders(togetherId);
				Float priceFloat = 0f;

				// 获取该笔订单总价
				for (DeliverChildOrder deliverChildOrder : deliverChildOrders) {
					if (deliverChildOrder.getIsDiscount() == 0) {
						priceFloat += deliverChildOrder.getPrice()
								* deliverChildOrder.getOrderCount();
					} else {
						priceFloat += deliverChildOrder.getDiscountPrice()
								* deliverChildOrder.getOrderCount();
					}
				}
				superAdminOrder.setPrice(priceFloat);
			}
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "获取订单成功！");
			map.put("orderList", JSONArray.parse(JSON
					.toJSONStringWithDateFormat(orders, "yyyy-MM-dd")));
		} catch (Exception e) {
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取订单失败！");
		}

		return map;
	}

	/**
	 * 给某一订单设置配送员
	 * 
	 * @param togetherId
	 * @param adminPhone
	 * @return
	 */
	@RequestMapping("/setDeliverAdmin")
	public @ResponseBody Map<String, Object> setDeliverAdmin(
			@RequestParam String togetherId,
			@RequestParam final String adminPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int flag = orderService.setDeliverAdmin(togetherId, adminPhone); // 设置配送员
			if (flag != -1 && flag != 0) {
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "设置配送员成功！");

				new Thread(new Runnable() {

					public void run() {
						// 推送
						pushService.sendPush(adminPhone,
								"For优提醒您，一笔新订单已达到，请及时配送，辛苦您了。", 1);

					}
				}).start();

			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "设置配送员失败！");
			}
		} catch (Exception e) {
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "设置配送员失败！");
		}

		return map;
	}

	/**
	 * 
	 * 配送员获取配送订单
	 * 
	 * @param phoneId
	 * @return
	 */
	@RequestMapping("/DeliverAdminGetOrder")
	public @ResponseBody Map<String, Object> deliverGetOrder(
			@RequestParam String phoneId, @RequestParam Integer campusId,
			Integer limit, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("phoneId", phoneId);
		requestMap.put("campusId", campusId);
		if (page != null && limit != null) {
			requestMap.put("offset", (page - 1) * limit);
			requestMap.put("limit", limit);
		} else {
			requestMap.put("offset", 0);
			requestMap.put("limit", 5);
		}
		try {
			// 获取一笔订单列表
			List<DeliverOrder> deliverOrders = orderService
					.deliverGetOrder(phoneId);

			for (DeliverOrder deliverOrder : deliverOrders) {
				String togetherId = deliverOrder.getTogetherId();
				// 获取订单食品集
				List<DeliverChildOrder> deliverChildOrders = orderService
						.getDeliverChildOrders(togetherId);
				Float priceFloat = 0f;

				// 获取该笔订单总价
				for (DeliverChildOrder deliverChildOrder : deliverChildOrders) {
					if (deliverChildOrder.getIsDiscount() == 0) {
						priceFloat += deliverChildOrder.getPrice()
								* deliverChildOrder.getOrderCount();
					} else {
						priceFloat += deliverChildOrder.getDiscountPrice()
								* deliverChildOrder.getOrderCount();
					}
				}
				deliverOrder.setTotalPrice(priceFloat);
				deliverOrder.setOrderList(deliverChildOrders);
			}

			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "获取订单成功！");
			map.put("orderList", JSONArray.parse(JSON
					.toJSONStringWithDateFormat(deliverOrders, "yyyy-MM-dd")));
		} catch (Exception e) {
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取订单失败！");
		}

		return map;
	}

	/**
	 * pc端获取简单的订单列表
	 * 
	 * @param status
	 * @param limit
	 * @param offset
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/getPCSimpleOrder")
	public @ResponseBody Map<String, Object> getPcOrders(Short status,
			Integer limit, Integer offset, String search) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<PCOrder> lists = orderService.getPCOrders(status, limit, offset,
				search);
		DecimalFormat df = new DecimalFormat("####.00");

		for (PCOrder order : lists) {
			// 如果是完成订单，直接显示交易价格，否则计算应收取的价格
			if (order.getPrice() == null) {
				if (order.getIsDiscount() == 0) {
					order.setPrice(Float.parseFloat(df.format(order
							.getFoodPrice() * order.getOrderCount())));
				} else {
					order.setPrice(Float.parseFloat(df.format(order
							.getDiscountPrice() * order.getOrderCount())));
				}
			}
		}

		JSONArray jsonArray = JSONArray.parseArray(JSON
				.toJSONStringWithDateFormat(lists, "yyyy-MM-dd"));

		long totalCount = orderService.getPCOrdersCount(status, search);
		map.put("rows", jsonArray);
		map.put("total", totalCount);
		return map;
	}

	/**
	 * 设置无效订单
	 * 
	 * @param togetherId
	 * @return
	 */
	@RequestMapping(value = "/setOrderInvalid", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> setOrderInvalid(
			@RequestParam String togetherId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("togetherId", togetherId);

			int flag = orderService.setOrderInvalid(parameterMap);
			if (flag != -1) {
				resultMap.put(Constants.STATUS, Constants.SUCCESS);
				resultMap.put(Constants.MESSAGE, "订单已取消");
			} else {
				resultMap.put(Constants.STATUS, Constants.FAILURE);
				resultMap.put(Constants.MESSAGE, "订单取消失败，请重试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "订单取消失败，请重试");
		}

		return resultMap;
	}

	/**
	 * 获取某日订单所有订单详情
	 * 
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "getOrdersByDate")
	@ResponseBody
	public Map<String, Object> getOrdersByDate(String date,
			@RequestParam Integer campusId, Integer limit, Integer page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("####.00");

		try {
			if (date.equals("") || date.equals("null"))
				date = null;
			else
				date = date.replace("年", "-").replace("月", "-")
				.replace("日", "");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("date", date);
			paramMap.put("campusId", campusId);

			if (page != null && limit != null) {
				paramMap.put("limit", limit);
				paramMap.put("offset", (page - 1) * limit);
			}

			System.out.println(date);
			List<DeliverOrder> deliverOrders = orderService
					.selectOrdersByDate(paramMap);
			Float totalPrice = 0f;
			for (DeliverOrder deliverOrder : deliverOrders) {
				String togetherId = deliverOrder.getTogetherId();
				System.out.println(JSON.toJSON(deliverOrder.getTogetherDate()));
				// 获取订单食品集
				paramMap.put("togetherId", togetherId);
				List<DeliverChildOrder> deliverChildOrders = orderService
						.getAllChildOrders(paramMap);
				Float priceFloat = 0f;

				// 获取该笔订单总价
				for (DeliverChildOrder deliverChildOrder : deliverChildOrders) {
					if (deliverChildOrder.getIsDiscount() == 0) {
						priceFloat += deliverChildOrder.getPrice()
								* deliverChildOrder.getOrderCount();
					} else {
						priceFloat += deliverChildOrder.getDiscountPrice()
								* deliverChildOrder.getOrderCount();
					}
				}
				totalPrice += priceFloat;
				deliverOrder.setTotalPrice(Float.parseFloat(df
						.format(priceFloat)));
				deliverOrder.setOrderList(deliverChildOrders);
			}

			resultMap.put("total_price", df.format(totalPrice));
			resultMap.put("counts", deliverOrders.size());
			resultMap.put("orderList", JSONArray.parse(JSON
					.toJSONStringWithDateFormat(deliverOrders,
							"yyyy-MM-dd HH:mm:ss")));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	/**
	 * 根据togetherId获取大订单信息
	 * 
	 * @param togetherId
	 */
	@RequestMapping("getBigOrderById")
	@ResponseBody
	public Map<String, Object> getBigOrderById(@RequestParam String togetherId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		Short status = 0;
		paramMap.put("togetherId", togetherId);
		BigOrder bigOrder = new BigOrder();
		bigOrder.setTogetherId(togetherId);
		List<SmallOrder> orders = orderService.getOrdersById(paramMap);
		if (orders.size() > 0 && orders!= null) {
			if (orders.get(0).getStatus() != 4) {
				status = orders.get(0).getStatus();
			} else {
				status = 5;
				for (int i = 0; i < orders.size(); i++) {
					if (orders.get(i).getIsRemarked() == 0) {
						status = 4;
					}
				}

			}
			Receiver receiver = receiverService.getReceiver(paramMap);
			Date date = orders.get(0).getTogetherDate();
			bigOrder.setDate(date);
			System.out.println(orders.get(0).getPayWay());
			bigOrder.setPayWay(orders.get(0).getPayWay());
			// 若order表里的price有信息
			/*
			 * if (orders!=null&&orders.size() > 0) { for (int i = 0; i <
			 * orders.size(); i++) { sum += orders.get(i).getPrice(); } }
			 */

//			if (orders.size() > 0 && orders != null) 
//			{
//				for(SmallOrder i:orders)
//				{
//					if(i.getIsDiscount()==1)
//					{
//						sum += i.getDiscountPrice()*i.getOrderCount();
//					}
//					else
//					{
//						sum += i.getPrice()*i.getOrderCount();
//					}
//				}
//			}
			Float sum=orders.get(0).getTotalPrice();
			if(sum!=null)
			{
				DecimalFormat df = new DecimalFormat("0.0");
				bigOrder.setTotalPrice(df.format(sum));
			}
			bigOrder.setOrders(orders);
			bigOrder.setReceiver(receiver);
			bigOrder.setStatus(status);
			resultMap.put("BigOrder",
					JSONArray.parse(JSON.toJSONStringWithDateFormat(bigOrder,
							"yyyy-MM-dd HH:mm:ss")));
			resultMap.put(Constants.STATUS, Constants.SUCCESS);
			resultMap.put(Constants.MESSAGE, "获取大订单信息成功");
		} else {
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "没有这个大订单");
		}

		return resultMap;
	}

	/**
	 * 修改订单状态
	 * @param adminPhone    ?????????????????????????????
	 * @param togetherId
	 * @return
	 */
	@RequestMapping("modifyOrderStatus")
	public @ResponseBody Map<String, Object> modifyOrderStatus(	@RequestParam final String togetherId, @RequestParam Short status, Long orderId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> requestMap = new HashMap<String, Object>();
		
		try {		
			requestMap.put("togetherId", togetherId);
			requestMap.put("status", status);	
			final String userPhone=orderService.getUserPhone(requestMap);          //获取用户手机号
			//final String adminPhone=orderService.getAdminPhone(requestMap);        //获取配送员手机号
			Integer flag = null;
			switch(status){
			case 0:
				//购物车
				//flag = orderService.modifyOrderStatus(requestMap);
				break;
			case 1:
				//待付款
				//flag = orderService.modifyOrderStatus(requestMap);
				break;
			case 2:
				//待确认
				flag = orderService.modifyOrderStatus(requestMap);
				break;
			case 3:
				//配送中
				flag = orderService.modifyOrderStatus(requestMap);
				new Thread(new Runnable() {

					@Override public void run() { //推送
						
						pushService.sendPush(userPhone,
								"您有一笔订单正在配送中,请稍候。感谢您对For优的支持", 1);

					} }).start();
				break;
			case 4:
				//待评价
				flag = orderService.modifyOrderStatus(requestMap);
				new Thread(new Runnable() {

					@Override public void run() { //推送
						
						pushService.sendPush(userPhone,
								"您有一笔订单已经完成,赶快去评价吧。感谢您对For优的支持", 1);

					} }).start();
				break;
			case 5:
				//小订单已完成
				requestMap.put("orderId", orderId);
				requestMap.put("isRemarked", Integer.valueOf(1));
				requestMap.put("status", 4);
				flag = orderService.modifyOrderStatus(requestMap);
				break;
			default:
				break;
			}
			resultMap.put(Constants.STATUS, Constants.SUCCESS);
			resultMap.put(Constants.MESSAGE, "更改状态成功");
			resultMap.put("flag", flag);
		} catch (Exception e) {
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "更改状态失败");
		}
		
		return resultMap;
	}


	/**
	 * 删除订单（status=4）
	 *@param togetherId
	 */
	@RequestMapping("/deleteOrder")
	public @ResponseBody Map<String, Object> deleteOrder(@RequestParam String togetherId)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("togetherId", togetherId);
		List<SmallOrder> orders=orderService.getOrdersById(paramMap);
		if(orders.size()>0&&orders!=null)
		{
			orderService.deleteOrder(paramMap);
			resultMap.put(Constants.STATUS, Constants.SUCCESS);
			resultMap.put(Constants.MESSAGE, "删除订单成功");
		}
		else
		{
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "订单不存在,删除订单失败");
		}

		return resultMap;
	}

	/**
	 * 商品详情处立即购买
	 * @param campusId
	 * @param phoneId
	 * @param foodId
	 * @param foodCount
	 * @return
	 */

	@RequestMapping("/purchaseImmediately")
	public @ResponseBody Map<String, Object> purchaseImmediately(
			@RequestParam Integer campusId, @RequestParam String phoneId,
			@RequestParam Long foodId, @RequestParam Integer foodCount)
			{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		try {
			Order order = new Order(campusId, phoneId, foodId, foodCount);
			paramMap.put("orderId",order.getOrderId());
			int flag = orderService.insertSelectiveOrder(order);

			if (flag == -1 && flag == 0) {			
				resultMap.put(Constants.STATUS, Constants.FAILURE);
				resultMap.put(Constants.MESSAGE, "生成订单失败");
			}

			SmallOrder smallOrder=orderService.getOrderById(paramMap);
			resultMap.put("order", smallOrder);
			resultMap.put(Constants.STATUS, Constants.SUCCESS);
			resultMap.put(Constants.MESSAGE, "订单详情：");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "生成订单失败");			
		}
		return resultMap;

			}
	
	/**
	 * 根据id获取满减信息
	 * @param preferentialId
	 * @return
	 */
	@RequestMapping("/getPreferentialById")
	public @ResponseBody Map<String, Object> getPreferentialById(@RequestParam Integer preferentialId)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Preferential preferential=orderService.getPreferentialById(preferentialId);
		resultMap.put("preferential",preferential);
		resultMap.put(Constants.STATUS,Constants.SUCCESS);
		resultMap.put(Constants.MESSAGE,"获取成功");
		return resultMap;
	}
	
	/**
	 * 获取满减信息
	 * @return
	 */
	@RequestMapping("/getPreferentials")
	public @ResponseBody Map<String, Object> getPreferentialList(@RequestParam Integer campusId)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("campusId", campusId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Preferential> preferentials=orderService.getPreferential(paramMap);
		resultMap.put("preferential",preferentials);
		resultMap.put(Constants.STATUS,Constants.SUCCESS);
		resultMap.put(Constants.MESSAGE,"获取成功");
		return resultMap;
	}
}
