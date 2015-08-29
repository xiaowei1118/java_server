package com.changyu.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.service.HotSearchService;
import com.changyu.foryou.tools.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class HotSearchTest {
	private static final Logger LOGGER = Logger
			.getLogger(HotSearchTest.class);
	
	@Resource private HotSearchService hotSearchService;
	
	 @Test
	 public void setNot2Display(){
		   Map<String,Object> resultMap=new HashMap<>();
		  
		   try {
			   Map<String,Object> paramMap=new HashMap<String,Object>();
			   paramMap.put("campusId",1);
			   paramMap.put("hotId",4);
			   int flag=hotSearchService.setNot2Display(paramMap);
			   if(flag!=-1){
					  resultMap.put(Constants.STATUS,Constants.SUCCESS);
					  resultMap.put(Constants.MESSAGE, "修改成功");
				  }else{
					  resultMap.put(Constants.STATUS,Constants.FAILURE);
					  resultMap.put(Constants.MESSAGE, "修改失败");
			   }
			  
			   assertEquals(1,flag);
			} catch (Exception e) {
				resultMap.put(Constants.STATUS,Constants.FAILURE);
				resultMap.put(Constants.MESSAGE, "修改失败");
				 LOGGER.info(JSON.toJSONString(e));
			}
		   
		   LOGGER.info(JSON.toJSONString(resultMap));
	   }
}
