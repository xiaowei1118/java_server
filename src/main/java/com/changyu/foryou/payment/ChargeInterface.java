package com.changyu.foryou.payment;

import java.util.HashMap;
import java.util.Map;

import com.changyu.foryou.tools.Constants;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;

public class ChargeInterface {
	/**
	 * pingpp 管理平台对应的 API key
	 */
	public static String apiKey = Constants.apiKey;
	/**
	 * pingpp 管理平台对应的应用 ID
	 */
	public static String appId = Constants.appId;
	
	 public static Charge charge(String orderId,Integer amount) {
		    Pingpp.apiKey = apiKey;
	        Charge charge = null;
	        Map<String, Object> chargeMap = new HashMap<String, Object>();
	        chargeMap.put("amount", amount);                        //金额，以分为单位
	        chargeMap.put("currency", "cny");
	        chargeMap.put("subject", "Your Subject");            //商品的标题
	        chargeMap.put("body", "Your Body");                  //商品的描述
	        chargeMap.put("order_no", orderId);        //订单号  唯一
	        chargeMap.put("channel", "alipay");               //支付渠道
	        chargeMap.put("client_ip", "127.0.0.1");              //发起支付请求的客户端ip
	        Map<String, String> app = new HashMap<String, String>();
	        app.put("id",appId);
	        chargeMap.put("app", app);
	        try {
	            //发起交易请求
	            charge = Charge.create(chargeMap);
	            System.out.println(charge);
	        } catch (PingppException e) {
	            e.printStackTrace();
	        }
	        return charge;
	    }

}
