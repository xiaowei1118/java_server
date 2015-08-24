package com.changyu.foryou.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changyu.foryou.payment.ChargeInterface;
import com.changyu.foryou.service.PayService;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;

@Controller
@RequestMapping("/pay")
public class PayController {
    private PayService payService;

	public PayService getPayService() {
		return payService;
	}

	@Autowired
	public void setPayService(PayService payService) {
		this.payService = payService;
	}
    
    @RequestMapping("/paytest")
    public @ResponseBody Map<String,Object> payTest(String togetherId,Integer amount){
    	Map<String,Object> resultMap=new HashMap<String,Object>();
    	Charge charge=ChargeInterface.charge(togetherId, amount);
    	resultMap.put("charge", charge);
    	return resultMap;
    }
}
