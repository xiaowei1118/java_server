package com.changyu.foryou.payment;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.tools.Constants;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

public class ChargeInterface {
    /**
     * pingpp 管理平台对应的 API key
     */
    public static String apiKey = Constants.apiKey;
    /**
     * pingpp 管理平台对应的应用 ID
     */
    public static String appId = Constants.appId;

    public static Charge charge(String channel, String orderId, Float amount, String clientIp) {
        Pingpp.apiKey = apiKey;
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put("amount", (int) (amount * 100));                        //金额，以分为单位
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "For优商品");            //商品的标题
        chargeMap.put("body", "For优商品值得信赖");                  //商品的描述
        chargeMap.put("order_no", orderId);        //订单号  唯一
        chargeMap.put("channel", channel);               //支付渠道
        chargeMap.put("client_ip", clientIp);              //发起支付请求的客户端ip
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
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

    public static Refund Refund(String chargeId, Float price) {
        Pingpp.apiKey = apiKey;
        Charge ch;
        try {
            ch = Charge.retrieve(chargeId);
            Map<String, Object> refundMap = new HashMap<>();
            System.out.println("charge==" + JSON.toJSONString(ch));
            refundMap.put("amount", (int) (price * 100));
            refundMap.put("description", "For优商品正常退款");
            return ch.getRefunds().create(refundMap);
        } catch (AuthenticationException | InvalidRequestException | APIException | APIConnectionException | ChannelException e) {
            e.printStackTrace();
        }

        return null;
    }

}
