package com.changyu.foryou.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.service.PayService;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;

@Controller
@RequestMapping("/pay")
public class PayController {
    private PayService payService;

    private static final Logger LOGGER = Logger
			.getLogger(PayController.class);
	public PayService getPayService() {
		return payService;
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
	
	@RequestMapping("/payWebHooksForSuccess")
	public void webHooksForPaySuccess(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
        if ("charge.succeeded".equals(event.getType())) {
        	LOGGER.info(JSON.toJSONString(event));     //打印返回的数据
            response.setStatus(200);
        } else if ("refund.succeeded".equals(event.getType())) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
	}
}
