package com.changyu.foryou.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.changyu.foryou.service.UserService;
import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.JpushInterface;

@Controller
@RequestMapping("/push")
public class PushController {
	protected static final Logger log = LoggerFactory.getLogger(PushController.class);
	private static final String appKey = "6b618bf4a73419c8e351240e";
	private static final String masterSecret = "a80ba2c3934895be10ae9a75";
    private UserService userService;
    
    @Autowired
    public void setUserService(UserService userService) {
		this.userService = userService;
	}
    
	@RequestMapping(value="pushPlatForm")
	private @ResponseBody String pushPlatForm(@RequestParam String push_data,@RequestParam Integer campusId,String pushDeviceAndroid,String pushDeviceIos,String alert, String phone) {

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 1);
		PushPayload payload=null;
		
		
		try {
			if(push_data==null||push_data.trim().equals("")||(pushDeviceAndroid==null&&pushDeviceIos==null)){
				return Constants.FAILURE;
			}

			if(alert.equals("1")){          //群推
				if(pushDeviceAndroid!=null&&pushDeviceIos!=null){
					payload = JpushInterface.buildPushObject_all_all_alert(push_data);
				}else if(pushDeviceAndroid == null){
					payload=JpushInterface.bulidPushObject_ios_alert(push_data);
				}else {
					payload=JpushInterface.bulidPushObject_android_alert(push_data);
				}
			}else if(alert.equals("4")){              //向个体用户推送,默认向android和ios用户同时推，不管有没有选中android和ios
				if(phone==null||phone.trim().equals("")){
					return Constants.FAILURE;
				}else{
					payload = JpushInterface.buildPushObject_android_and_ios_alias_alert(push_data,phone);
				}
			}else{
				//payload = JpushInterface.buildPushObject_android_and_ios_tag_alert(push_data,String.valueOf((Integer.valueOf(alert)-2)));
				Map<String,Object> paramMap=new HashMap<>();
				Integer type=Integer.valueOf(alert)-2;
				paramMap.put("type",type);
				paramMap.put("campusId", campusId);
				List<String> phones=userService.getUserByType(paramMap);
				for (String phone1 : phones) {
					payload = JpushInterface.buildPushObject_android_and_ios_alias_alert(push_data,phone1);
					PushResult result = jpushClient.sendPush(payload);
					log.info("Got result - " + result);	
				}
				return Constants.SUCCESS;
			}
			PushResult result = jpushClient.sendPush(payload);
			log.info("Got result - " + result);

			return Constants.SUCCESS;
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
			return Constants.FAILURE;

		} catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return Constants.FAILURE;
		}
	}
	@RequestMapping(value="pushAll")
	private @ResponseBody void pushAndSend(@RequestParam String message) {

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 1);

		PushPayload payload = JpushInterface.buildPushObject_all_all_alert(message);

		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info("Got result - " + result);

		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
		}
	}

	@RequestMapping(value="pushByTag")
	private @ResponseBody void pushByTag(@RequestParam String message,String tag) {

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 1);

		PushPayload payload = JpushInterface.buildPushObject_android_and_ios_tag_alert(message,tag);

		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info("Got result - " + result);

		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
		}
	}

	@RequestMapping(value="pushByAlias")
	private @ResponseBody void pushByAlias(@RequestParam String message,String alias) {

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 1);

		PushPayload payload = JpushInterface.buildPushObject_android_and_ios_alias_alert(message,alias);

		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info("Got result - " + result);

		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
		}
	}

}
