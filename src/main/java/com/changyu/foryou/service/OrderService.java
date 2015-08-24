package com.changyu.foryou.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

public interface OrderService {

	List<CartGood> getOrderList(Map<String, Object> paramMap);

	List<Order> getOrderSuccessList(String phoneId);

	int deleteUserOrder(Long orderId,String phoneId);

	int editUserOrder(Long orderId, String phoneId, Integer orderCount);

	int insertSelectiveOrder(Order order);

	int changeOrderStatus2Buy(String phoneId, String orderId,String togetherId,String rank, String reserveTime, String message,Short payWay,Float price,Float totalPrice);

	int changeOrderStatus2Deliver(String phoneId, String togetherId);

	int changeOrderStatus2Finish(String phoneId, String orderId);

	int deleteAllUserOrder(String phoneId);

	//	List<SmallOrder> getOrderListInMine(String phoneId);

	List<SmallOrder> getOrderListInMineWait2Deliver(String phoneId);

	List<SmallOrder> getOrderListInMineDeliver(String phoneId);

	List<SmallOrder> getOrderListInMineFinish(String phoneId);

	//List<SmallOrder> getOrderListInMine(String phoneId, String togetherId, Short status);

	Map<String, Object> getOrderSummaryCount(String phone);

	List<Order> selectOrder(Order order);

	void deleteCartGood(Order order);

	Order selectPersonOrder(String phoneId, Long orderId);

	void updateOrderRemarked(String phoneId, Long orderId);

	List<SuperAdminOrder> superAdminGetOrder(Map<String,Object> paramMap);

	int getExitOrderUserRank(String phoneId, String rank);

	int setDeliverAdmin(String togetherId, String adminPhone);

	Order selectOneOrder(String phoneId, String orderId);

	List<DeliverOrder> deliverGetOrder(String phoneId);

	List<DeliverChildOrder> getDeliverChildOrders(String togetherId);

	List<PCOrder> getPCOrders(Short status,Integer limit, Integer offset, String search);

	Long getPCOrdersCount(Short status,String search);

	int setOrderInvalid(Map<String, Object> parameterMap);

	List<DeliverOrder> selectOrdersByDate(Map<String, Object> paramMap);

	List<DeliverChildOrder> getAllChildOrders(Map<String, Object> paramMap);

	List<String> getTogetherId(Map<String, Object> paramMap);

	List<SmallOrder> getOrderListInMine(Map<String, Object> paramMap);

	Date getTogetherDate(Map<String, Object> paramMap);

	List<SmallOrder> getOrdersById(Map<String, Object> paramMap);

	Integer modifyOrderStatus(Map<String, Object> paramMap);

	void deleteOrder(Map<String, Object> paramMap);

	SmallOrder getOrderById(Map<String, Object> paramMap);

	String getUserPhone(Map<String, Object> requestMap);

	String getAdminPhone(Map<String, Object> requestMap);

	Preferential getPreferentialById(Integer preferentialId);

	List<Preferential> getPreferential(Map<String, Object> paramMap);

	Integer updateOrderPrice(Map<String, Object> paramMap);

	Integer updateOrder(Order order);



	

	
}
