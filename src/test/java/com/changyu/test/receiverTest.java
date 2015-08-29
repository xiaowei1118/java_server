package com.changyu.test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.changyu.foryou.model.Receiver;
import com.changyu.foryou.service.OrderService;
import com.changyu.foryou.service.ReceiverService;
import com.changyu.foryou.tools.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class receiverTest {
	private static final Logger LOGGER = Logger
			.getLogger(receiverTest.class);

	private ReceiverService receiverService;
    private OrderService orderService;
    
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public ReceiverService getReceiverService() {
		return receiverService;
	}

	@Autowired
	public void setReceiverService(ReceiverService receiverService) {
		this.receiverService = receiverService;
	}


	/**
	 * 添加收貨人信息
	 * @param phoneId 用戶手机，id
	 * @param phone  收货人手机号
	 * @param name   收货人名字
	 * @param address  收货人地址
	 * @param campusId 校区号
	 * @return
	 */
	public void addReceiver(){
//		String phoneId="18896554880";
//		String phone="18896554880";
//		String name="ssssss";
//		String address="三里屯";
//		Integer campusId=2;
//		case2
		String phoneId="18896552573";
		String phone="18896554880";
		String name="ssssss";
		String address="三里屯";
		Integer campusId=2;
		Map<String, Object> map=new HashMap<String ,Object>();
		Receiver receiver=new Receiver(phoneId,phone,name,address,campusId);

		try {

			//通过时间生成该记录的序列号，和phoneId一起唯一表志收货人信息
			Calendar calendar=Calendar.getInstance();
			receiver.setRank(String.valueOf(calendar.getTimeInMillis()));


			if(receiverService.getReceiverCounts(phoneId)!=0){
				receiver.setTag((short)1);
			}else{
				receiver.setTag((short)0);
			}

			System.out.println(JSON.toJSONString(receiver));

			if(receiverService.insert(receiver)!=-1){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "添加成功！");
			}else{
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "添加失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "添加失败！");
		}
		LOGGER.debug("========================="+JSON.toJSONString(map)+"==================================");
		return ;
	}


	/**
	 * 根据用户id获取用户存下来的收货人信息
	 * @param phoneId
	 * @return
	 */
	
	@Test
	public void relectReceiver(){
//		String phoneId="18896554880";
//		case2
		String phoneId="18896555533";
		
		Map<String, Object> map=new HashMap<String ,Object>();
		try {
			List<Receiver> receivers=receiverService.selectByPhoneId(phoneId);
			map.put("receivers", receivers);
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "获取成功");
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "获取失败！");
		}
		LOGGER.debug("========================="+JSON.toJSONString(map)+"==================================");
		return ;
	}

	/**
	 * 更改收货人信息
	 * @param phoneId  用户id
	 * @param rank    收货人序列，主键
	 * @param address  收货人地址
	 * @param name    收货人姓名
	 * @param phone   收货人手机号
	 * @param campusId 校区
	 * @return
	 */
	@Test
	public void updateReceiver(){
//			case1
//	        String phoneId="18896554880";
//	        String rank="1439974693087";
//		    String address="体育馆";
//		    String name="施施";
//		    String phone="1144332244";
//		    Integer campusId=3;
//		case2
//      String phoneId="18860902563";
//      String rank="1439974693087";
//	    String address="体育馆";
//	    String name="施施";
//	    String phone="1144332244";
//	    Integer campusId=3;
//		case3
//        String phoneId="18896554880";
//        String rank="1439974693087";
//	    String address=null;
//	    String name="施施";
//	    String phone="1144332244";
//	    Integer campusId=3;
//		case4;
//	    String phoneId="18896554880";
//        String rank="1439974693087";
//	    String address=null;
//	    String name=null;
//	    String phone="1144332244";
//	    Integer campusId=3;
//		case5
//	    String phoneId="18896554880";
//        String rank="1439974693087";
//	    String address=null;
//	    String name=null;
//	    String phone=null;
//	    Integer campusId=2;
//		case6
	    String phoneId="18896554880";
        String rank="1439974693087";
	    String address=null;
	    String name="施施";
	    String phone="1144332244";
	    Integer campusId= Integer.parseInt(null);
		Map<String, Object> map=new HashMap<String ,Object>();
		try {
			Receiver receiver=new Receiver(phoneId,phone,name,address,campusId);
			receiver.setRank(rank);
			if(receiverService.updateByPrimaryKeySelective(receiver)!=-1){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "更新收货人信息成功");
			}else{
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "更新收货人信息失败");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "更新收货人信息失败！");
		}
		LOGGER.debug("========================="+JSON.toJSONString(map)+"==================================");
		return ;
	}

	/**
	 * 设置默认收货地址
	 * @param phoneId  用户id
	 * @param rank   收货人序列号
	 * @return
	 */

	@Test
	public void setDefaultAddress(){
//		case1
//		String phoneId="18896554880";
//		String rank="1439974693087";
//		case2
		String phoneId="18896554880";
		String rank="1439969449000";
		Map<String, Object> map=new HashMap<String ,Object>();
		try{
			receiverService.setRecevierTag(phoneId);   //将原先的默认收货地址改成非默认
			if(receiverService.setDefaultAddress(phoneId, rank)!=-1){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "设置默认收货地址成功");
			}else{
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "设置默认收货地址失败");
			}
		}catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "设置默认收货地址失败！");
		}
		LOGGER.debug("========================="+JSON.toJSONString(map)+"==================================");
		return ;
	}

	
	/**
	 * 删除某个收货人地址
	 * @param phoneId
	 * @param rank
	 * @return
	 */	
	@Test
	public void deleteReceiver(){
//		String phoneId="18896554880";
//		String rank="1439973213527";
//		case2
//		String phoneId="18896554880";
//		String rank="1439974693087";
//		case3
		String phoneId="18896554880";
		String rank="1439974693011";
		
		Map<String, Object> map=new HashMap<String ,Object>();
		try {
      
			int flag=receiverService.deleteByPrimaryKey(phoneId, rank);
			if(flag!=-1&&flag!=0){
				map.put(Constants.STATUS, Constants.SUCCESS);
				map.put(Constants.MESSAGE, "删除地址成功!");
			}else{
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "删除地址失败！");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "删除地址失败！");
		}
		LOGGER.debug("========================="+JSON.toJSONString(map)+"==================================");
		return ;
	}
}
