package com.changyu.foryou.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.changyu.foryou.model.CartGood;
import com.changyu.foryou.model.DeliverChildOrder;
import com.changyu.foryou.model.DeliverOrder;
import com.changyu.foryou.model.MiniOrder;
import com.changyu.foryou.model.Order;
import com.changyu.foryou.model.PCOrder;
import com.changyu.foryou.model.SmallOrder;
import com.changyu.foryou.model.SuperAdminOrder;

public interface OrderMapper {
    int deleteByPrimaryKey(@Param(value="orderId")Long orderId,@Param(value="phoneId") String phoneId);

    int insert(Order record);

    int insertSelective(Order record);

    
    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	List<CartGood> getOrderListByPhone(Map<String, Object> paramMap);

	List<Order> getSuccessOrder(String phoneId);

	String getSpecialName(Integer foodSpecial);

	int updateOrderCount(@Param(value="orderId")Long orderId, @Param(value="phoneId")String phoneId, @Param(value="orderCount")Integer orderCount);

	int changeOrderStatus2Buy(@Param(value="orderId")Long orderId, @Param(value="phoneId")String phoneId, @Param(value="togetherId")String togetherId, @Param(value="rank")String rank, @Param(value="reserveTime")String reserveTime, @Param(value="message")String message,@Param(value="payWay")Short payWay,@Param(value="price")Float price,@Param(value="totalPrice")Float totalPrice);

	int changeOrderStatus2Deliver(@Param(value="togetherId")String togetherId, @Param(value="phoneId")String phoneId);

	int changeOrderStatus2Finish(@Param(value="togetherId")String togetherId, @Param(value="phoneId")String phoneId);

	int deleteAllUserOrder(String phoneId);
	List<SmallOrder> getOrderListInMineWait2Deliver(String phoneId);

	List<SmallOrder> getOrderListInMineDeliver(String phoneId);

	List<SmallOrder> getOrderListInMineFinish(String phoneId);

	List<Order> selectOrder(Order order);

	void deleteCartGood(Order order);

	Order selectPersonOrder(@Param(value="phoneId")String phoneId, @Param(value="orderId")Long orderId);

	void updateOrderRemarked(@Param(value="phone")String phoneId, @Param(value="orderId")Long orderId);

	List<SuperAdminOrder> superAdminGetOrder(Map<String,Object> paramMap);

	int getExitOrderUserRank(@Param(value="phone")String phoneId, @Param(value="rank")String rank);

	int setDeliverAdmin(@Param(value="togetherId")String togetherId, @Param(value="adminPhone")String adminPhone);

	Order selectByPrimaryKey(@Param(value="orderId")Long orderId, @Param(value="phoneId")String phoneId);

	List<DeliverOrder> deliverGetOrder(Map<String,Object> paramMap);

	List<DeliverChildOrder> getDeliverChildOrders(@Param(value="togetherId")String togetherId);

	List<PCOrder> getPCOrders(@Param(value="status")Short status,@Param(value="time") Date time,@Param(value="limit")Integer limit, @Param(value="offset")Integer offset, @Param(value="search")String search);

	List<PCOrder> getPCOrdersWithOutAdmin(@Param(value="status")Short status,@Param(value="time") Date time,@Param(value="limit")Integer limit, @Param(value="offset")Integer offset, @Param(value="search")String search);

	Long getPCOrdersCount(@Param(value="status")Short status, @Param(value="time")Date time, @Param(value="search")String search);

	int setOrderInvalid(Map<String, Object> parameterMap);

	List<DeliverOrder> selectOrdersByDate(Map<String, Object> paramMap);

	List<DeliverChildOrder> getAllChildOrder(Map<String, Object> paramMap);

	List<String> getTogetherId(Map<String, Object> map);

	List<SmallOrder> getOrderListInMine(Map<String, Object> map);

	Date getTogetherDate(Map<String, Object> paramMap);

	List<SmallOrder> getOrdersById(Map<String, Object> paramMap);
	
	Integer modifyOrderStatus(Map<String, Object> paramMap);

	void deleteOrder(Map<String, Object> paramMap);

	SmallOrder getOrderById(Map<String, Object> paramMap);
 
	String getUserPhone(Map<String, Object> requestMap);              //获取用户手机号

	String getAdminPhone(Map<String, Object> requestMap);             //获取配送员手机号

	Integer updateOrderPrice(Map<String, Object> paramMap);

	Float selectTotalPriceByTogetherId(@Param(value="togetherId")String togetherId);

	int updateOrderStatusAndAmount(Map<String, Object> paramMap);     //支付成功后更新订单和价格

	Integer getCampusIdByTogetherId(Map<String, Object> paramMap);    //获取某笔订单的校区号

	CartGood getOrderByOrderId(Map<String, Object> paramMap);            //获取某笔小订单

	Integer cancelOrderWithRefund(Map<String, Object> paramMap);

	List<SuperAdminOrder> getPCOrdersNew(Map<String, Object> paramMap);

	int updateCancelRefund(Map<String, Object> paramMap);            //取消退款

	String getChargeId(Map<String, Object> paramMap);

	Integer updateRefundStatus(Map<String, Object> paramMap);   //更新退款信息状态

	Integer updateorderStatusRefundSuccess(Map<String, Object> paramMap);  //将状态置为11

	Integer getMiniOrderByPhone(Map<String, Object> paramMap);	//获取订单状态对应的数量
	
	Integer getSalesInfoByCampusId(Map<String, Object> paramMap);	//根据校区和时间段获取销售量
	
	Float getTradeVolumeByCampusId(Map<String, Object> paramMap);	//获取指定时间段和指定校区的订单交易额

	List<PCOrder> getPCSimpleOrders(Map<String, Object> paramMap);

	long getPCSimpleOrdersCount(Map<String, Object> paramMap);

	List<SuperAdminOrder> getPCInvalidOrders(Map<String, Object> paramMap);  //获取无效订单

	int deleteOrderTrue(Map<String, Object> paramMap);                       //删除无效订单

	int deleteStatus7Order(String phone);              //删除无效订单（从商品详情点立即购买，却没有支付的

	List<Order> getAllOrderByTogetherId(String orderNo);   //
}