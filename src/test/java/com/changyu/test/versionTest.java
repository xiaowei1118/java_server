package com.changyu.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.tools.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class versionTest {
	private static final Logger LOGGER = Logger
			.getLogger(versionTest.class);
	
	@Test
	/**
	 * 测试获得版本号
	 * @return
	 */
	public void getVersion(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ios_version", "1.0.1");
		map.put("android_version", "1.1.0");
		map.put(Constants.STATUS, Constants.SUCCESS);
		map.put(Constants.MESSAGE, "获取版本号成功");
		LOGGER.debug("================================="+JSON.toJSONString(map)+"=============================================");
		return;
	}

}
