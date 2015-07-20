package com.changyu.foryou.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.changyu.foryou.service.PushService;
import com.changyu.foryou.tools.JpushInterface;

@Service("pushService")
public class PushServiceImpl implements PushService{
	private static final String appKey = "e9e04345a608f9fb23a3b61b";
	private static final String masterSecret = "70a4b68027698fe532d95436";
	
	private  static final Logger log = LoggerFactory.getLogger(PushServiceImpl.class);

	//通过别名推送
	public  void sendPush(String phone, String message, Integer count) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, count);   //count表示离线消息保持时间

		PushPayload payload = JpushInterface.buildPushObject_android_and_ios_alias_alert(message,phone);

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

	//推送通过tag
	public void sendPushByTag(String tag, String message, int count) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, count);   //count表示离线消息保持时间

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

}
