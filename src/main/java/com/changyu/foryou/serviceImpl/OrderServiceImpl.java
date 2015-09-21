package com.changyu.foryou.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.mapper.OrderMapper;
import com.changyu.foryou.mapper.PreferentialMapper;
import com.changyu.foryou.model.CartGood;
import com.changyu.foryou.model.DeliverChildOrder;
import com.changyu.foryou.model.DeliverOrder;
import com.changyu.foryou.model.Order;
import com.changyu.foryou.model.PCOrder;
import com.changyu.foryou.model.Preferential;
import com.changyu.foryou.model.SmallOrder;
import com.changyu.foryou.model.SuperAdminOrder;
import com.changyu.foryou.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	private OrderMapper orderMapper;
	
	private PreferentialMapper preferentialMapper;

	public OrderMapper getOrderMapper() {
		return orderMapper;
	}

	@Autowired
	public void setOrderMapper(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}
    
	@Autowired
	public void setPreferentialMapper(PreferentialMapper preferentialMapper) {
		this.preferentialMapper = preferentialMapper;
	}

	public List<Preferential> getPreferential(Map<String, Object> paramMap) {
		return preferentialMapper.getPreferential(paramMap);
	}
	
	public List<CartGood> getOrderList(Map<String,Object> paramMap) {
		return orderMapper.getOrderListByPhone(paramMap);
	}

	public List<Order> getOrderSuccessList(String phoneId) {
		return orderMapper.getSuccessOrder(phoneId);
	}

	public int deleteUserOrder(Long orderId, String phoneId) {
		return orderMapper.deleteByPrimaryKey(orderId,phoneId);
	}
	public int editUserOrder(Long orderId, String phoneId,Integer orderCount) {
		return orderMapper.updateOrderCount(orderId,phoneId,orderCount);
	}

	public int insertSelectiveOrder(Order order) {
		return orderMapper.insertSelective(order);
	}

	public int changeOrderStatus2Buy(String phoneId, String orderId,String togetherId,String rank,String reserveTime,String message,Short payWay,Float price,Float totalPrice) {

		return orderMapper.changeOrderStatus2Buy(Long.valueOf(orderId),phoneId,togetherId,rank,reserveTime,message,payWay,price,totalPrice);
	}

	public int changeOrderStatus2Deliver(String phoneId, String togetherId) {
		return orderMapper.changeOrderStatus2Deliver(togetherId, phoneId);
	}

	public int changeOrderStatus2Finish(String phoneId, String orderId) {
		return orderMapper.changeOrderStatus2Finish(orderId, phoneId);
	}

	public int deleteAllUserOrder(String phoneId) {	
		return orderMapper.deleteAllUserOrder(phoneId);
	}

	public List<SmallOrder> getOrderListInMine(Map<String,Object> map) {	
		return orderMapper.getOrderListInMine(map);
	}

	public List<SmallOrder> getOrderListInMineWait2Deliver(String phoneId) {
		return orderMapper.getOrderListInMineWait2Deliver(phoneId);
	}

	public List<SmallOrder> getOrderListInMineDeliver(String phoneId) {
		return orderMapper.getOrderListInMineDeliver(phoneId);
	}

	public List<SmallOrder> getOrderListInMineFinish(String phoneId) {
		return orderMapper.getOrderListInMineFinish(phoneId);
	}

	public List<String> getTogetherId(Map<String,Object> map) {
		return orderMapper.getTogetherId(map);
	}

	public Map<String, Object> getOrderSummaryCount(String phone) {
	/*	List<String> waitToDeliver=orderMapper.getTogetherId(phone,(short) 1); 
		List<String> deliver=orderMapper.getTogetherId(phone, (short)2);
		List<String> waitComment=orderMapper.getTogetherId(phone, (short)3);
     */
		Map<String, Object> map=new HashMap<String, Object>();

		/*map.put("wait",waitToDeliver.size());
		map.put("deliver", deliver.size());
		map.put("comment", waitComment.size());*/
		return map;
	}

	public List<Order> selectOrder(Order order) {
		return orderMapper.selectOrder(order);
	}

	public void deleteCartGood(Order order) {
		orderMapper.deleteCartGood(order);		
	}

	public Order selectPersonOrder(String phoneId, Long orderId) {
		return orderMapper.selectPersonOrder(phoneId,orderId);
	}

	public void updateOrderRemarked(String phoneId, Long orderId) {
		orderMapper.updateOrderRemarked(phoneId,orderId);		
	}

	public List<SuperAdminOrder> superAdminGetOrder(Map<String, Object> paramMap) {
		return orderMapper.superAdminGetOrder(paramMap);
	}

	//弃用，因为现在删除只是将其置为无效
	//删除收货人地址时，检查该地址是否在订单中已使用，否者不能删除
	public int getExitOrderUserRank(String phoneId, String rank) {
		return orderMapper.getExitOrderUserRank(phoneId,rank);
	}

	//设置配送员
	public int setDeliverAdmin(String togetherId, String adminPhone) {
		return orderMapper.setDeliverAdmin(togetherId,adminPhone);
	}

	public Order selectOneOrder(String phoneId, String orderId) {
		return orderMapper.selectByPrimaryKey(Long.valueOf(orderId),phoneId);
	}


	//配送员获取配送订单
	public List<DeliverOrder> deliverGetOrder(Map<String, Object> paramMap) {
		return orderMapper.deliverGetOrder(paramMap);
	}

	public List<DeliverChildOrder> getDeliverChildOrders(String togetherId) {
		return orderMapper.getDeliverChildOrders(togetherId);
	}

	public List<PCOrder> getPCOrders(Short status,Integer limit,Integer offset,String search) {
		Calendar calendar=Calendar.getInstance();

		if(status!=1){
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);            //设定默认只显示近两个月的数据
			return orderMapper.getPCOrders(status,calendar.getTime(),limit,offset,search);
		}else{
			calendar.add(Calendar.DAY_OF_MONTH, -14);        //代发货订单默认显示两周内订单
			return orderMapper.getPCOrdersWithOutAdmin(status, calendar.getTime(),limit,offset,search);
		}
	}

	public Long getPCOrdersCount(Short status, String search) {
		Calendar calendar=Calendar.getInstance();

		if(status!=1){
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);            //设定默认只显示近两个月的数据		
		}else{
			calendar.add(Calendar.DAY_OF_MONTH, -14);        //代发货订单默认显示两周内订单
		}
		
		return orderMapper.getPCOrdersCount(status,calendar.getTime(),search);
	}

	public int setOrderInvalid(Map<String, Object> parameterMap) {
		return orderMapper.setOrderInvalid(parameterMap);
	}

	public List<DeliverOrder> selectOrdersByDate(Map<String, Object> paramMap) {
		return orderMapper.selectOrdersByDate(paramMap);
	}

	public List<DeliverChildOrder> getAllChildOrders(
			Map<String, Object> paramMap) {
		return orderMapper.getAllChildOrder(paramMap);
	}

	public Date getTogetherDate(Map<String, Object> paramMap) {
		return orderMapper.getTogetherDate(paramMap);
	}

	public List<SmallOrder> getOrdersById(Map<String, Object> paramMap) {
		return orderMapper.getOrdersById(paramMap);
	}


	public Integer modifyOrderStatus(Map<String, Object> paramMap) {
		return orderMapper.modifyOrderStatus(paramMap);
	}

	public void deleteOrder(Map<String, Object> paramMap) {
		orderMapper.deleteOrder(paramMap);
	}

	public SmallOrder getOrderById(Map<String, Object> paramMap) {
		return orderMapper.getOrderById(paramMap);
	}

	public String getUserPhone(Map<String, Object> requestMap) {
		return orderMapper.getUserPhone(requestMap);
	}

	public String getAdminPhone(Map<String, Object> requestMap) {
		return orderMapper.getAdminPhone(requestMap);
	}

	public Preferential getPreferentialById(Integer preferentialId) {
		return preferentialMapper.selectByPrimaryKey(preferentialId);
	}

	public PreferentialMapper getPreferentialMapper() {
		return preferentialMapper;
	}

	
	public Integer updateOrderPrice(Map<String, Object> paramMap) {
		return orderMapper.updateOrderPrice(paramMap);
	}

	public Integer updateOrder(Order order) {
		return orderMapper.updateByPrimaryKeySelective(order);
	}


	@Override
	public Float getTotalPriceByTogetherId(String togetherId) {
		// TODO Auto-generated method stub
		return orderMapper.selectTotalPriceByTogetherId(togetherId);
	}

	public int updateOrderStatusAndAmount(Map<String, Object> paramMap) {
		return orderMapper.updateOrderStatusAndAmount(paramMap);
	}

	/**
	 * 获取某笔订单的校区状态
	 * @param paramMap
	 * @return
	 */
	public Integer getCampusIdByTogetherId(Map<String, Object> paramMap) {
		return orderMapper.getCampusIdByTogetherId(paramMap);
	}
	
	/**
	 * 计算折扣和优惠后的价格
	 */
	public  Float getPriceDiscounted(String[] orderId,int campusId,String phoneId){
		//获取满减的信息
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("campusId",campusId);
		paramMap.put("phoneId", phoneId);
		System.out.println(paramMap);
		List<Preferential> prefers = getPreferential(paramMap);
		
		System.out.println(JSON.toJSONString(prefers));
		Float discountPrice=0f;         //折扣之后的总价
		Float fullDiscount=0f;                //满减商品之后的总价
		System.out.println(JSON.toJSONString(orderId));
		for (String id : orderId) {
			paramMap.put("orderId",Long.parseLong(id));
			Float price = 0f;              //temp
			CartGood order=orderMapper.getOrderByOrderId(paramMap);
			System.out.println("order is"+JSON.toJSONString(order));
			if(order.getIsDiscount()==1){
				System.out.println(order.getDiscountPrice());
				price = order.getOrderCount()*order.getDiscountPrice();
			}else{
			    price = order.getOrderCount()*order.getPrice();
			}
			
			if(order.getIsFullDiscount()==1){
				fullDiscount+=price;
			}
			
			discountPrice+=price;
		}
		
		Integer discount=0;  
		for (Preferential preferential : prefers) {
			if(fullDiscount>=preferential.getNeedNumber()){
				discount=preferential.getDiscountNum();		
				break;
			}
		}
		return discountPrice-discount;
	}

	@Override
	public Integer cancelOrderWithRefund(Map<String, Object> paramMap) {
		return orderMapper.cancelOrderWithRefund(paramMap);
	}

	@Override
	public List<SuperAdminOrder> getPCOrders(Map<String, Object> paramMap) {
		return orderMapper.getPCOrdersNew(paramMap);
	}

	@Override
	public int updateCancelRefund(Map<String, Object> paramMap) {
		return orderMapper.updateCancelRefund(paramMap);
	}

	@Override
	public String getChargeId(Map<String, Object> paramMap) {
		return orderMapper.getChargeId(paramMap);       //获取支付的chargeId
	}

	@Override
	public Integer updateRefundStatus(Map<String, Object> paramMap) {
		return orderMapper.updateRefundStatus(paramMap);
	}

	//退款完成时将状态置为11
	public Integer updateOrderStatusRefundSuccess(Map<String, Object> paramMap) {
		return orderMapper.updateorderStatusRefundSuccess(paramMap);
	}

	public Integer getMiniOrderByPhone(Map<String, Object> paramMap) {
		return orderMapper.getMiniOrderByPhone(paramMap);
	}

	public Integer getSalesInfoByCampusId(Map<String, Object> paramMap) {
		return orderMapper.getSalesInfoByCampusId(paramMap);
	}

	public Float getTradeVolumeByCampusId(Map<String, Object> paramMap) {
		return orderMapper.getTradeVolumeByCampusId(paramMap);
	}

	public List<PCOrder> getPCSimpleOrders(Map<String, Object> paramMap) {
		return orderMapper.getPCSimpleOrders(paramMap);
	}

	public long getPCSimpleOrdersCount(Map<String, Object> paramMap) {
		return orderMapper.getPCSimpleOrdersCount(paramMap);
	}

	public List<SuperAdminOrder> getPCInvalidOrders(Map<String, Object> paramMap) {
		return orderMapper.getPCInvalidOrders(paramMap);
	}

	@Override
	public int deleteOrderTrue(Map<String, Object> paramMap) {
		return orderMapper.deleteOrderTrue(paramMap);
	}

	@Override
	public void deleteStatus7Order(String phone) {
         orderMapper.deleteStatus7Order(phone);		
	}

	@Override
	public List<Order> getAllOrdersByTogetherId(String orderNo) {
		return orderMapper.getAllOrderByTogetherId(orderNo);
	}
	

}
