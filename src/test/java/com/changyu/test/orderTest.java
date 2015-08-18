package com.changyu.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.model.SmallOrder;
import com.changyu.foryou.model.TogetherOrder;
import com.changyu.foryou.service.OrderService;
import com.changyu.foryou.tools.Constants;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class orderTest { 
	private static final Logger LOGGER = Logger
			.getLogger(orderTest.class);
	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Test
	public void getOrderInMine(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<TogetherOrder> togetherOrdersList = new ArrayList<TogetherOrder>();

		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			//case 1
			paramMap.put("phoneId", "18896554880");
			paramMap.put("status", "a");
		 
		  
			//case 2
			
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
		
		LOGGER.debug("订单为:==================="+JSON.toJSONString(map));
		return ;
	}

}
