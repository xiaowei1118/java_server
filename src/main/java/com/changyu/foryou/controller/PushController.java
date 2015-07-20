package com.changyu.foryou.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.JpushInterface;

@Controller
@RequestMapping("/push")
public class PushController {
	protected static final Logger log = LoggerFactory.getLogger(PushController.class);
	private static final String appKey = "e9e04345a608f9fb23a3b61b";
	private static final String masterSecret = "70a4b68027698fe532d95436";

	@RequestMapping(value="pushPlatForm")
	private @ResponseBody String pushPlatForm(@RequestParam String push_data,String pushDeviceAndroid,String pushDeviceIos,String alert, String phone) {

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 1);
		PushPayload payload=null;
		
		
		try {
			if(push_data==null||push_data.trim().equals("")||(pushDeviceAndroid==null&&pushDeviceIos==null)){
				return Constants.FAILURE;
			}

			if(alert.equals("1")){          //群推
				if(pushDeviceAndroid!=null&&pushDeviceIos!=null){
					payload = JpushInterface.buildPushObject_all_all_alert(push_data);
				}else if(pushDeviceAndroid==null&&pushDeviceIos!=null){
					payload=JpushInterface.bulidPushObject_ios_alert(push_data);
				}else if(pushDeviceAndroid!=null&&pushDeviceIos==null){
					payload=JpushInterface.bulidPushObject_android_alert(push_data);
				}
			}else if(alert.equals("4")){              //向个体用户推送,默认向android和ios用户同时推，不管有没有选中android和ios
				if(phone==null||phone.trim().equals("")){
					return Constants.FAILURE;
				}else{
					payload = JpushInterface.buildPushObject_android_and_ios_alias_alert(push_data,phone);
				}
			}else{
				payload = JpushInterface.buildPushObject_android_and_ios_tag_alert(push_data,String.valueOf((Integer.valueOf(alert)-2)));		
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

	/*@RequestMapping("/pushIOS")
    public @ResponseBody void IOSPushTest(){
		try {
			 List<String> tokens=new ArrayList<String>();  
		        tokens.add("eb9cd021be0ff3e622a3ad9b54d3996724c6f36c7a03bfbb37a7c3e38f2324e3");  
		       // tokens.add("dc2cf037bd4465c851b1d96a86b0a028307bc7e443435b6fafe93c2957bb415c");  
		        //String path="D:/push_mickeyfood_developer.p12";
		        String path=this.getClass().getResource("").getPath()+"push_mickeyfood_developer.p12";
		        path=path.substring(1).replace("%20", " ");
		        System.out.println(path);
		        String password="mickeyfood";  
		        String message="{'aps':{'alert':'iphone推送测试 www.baidu.com'}}";  
		        Integer count=1;  
		        boolean sendCount=false;  
		        sendpush(tokens, path, password, message, count, sendCount);  
		} catch (Exception e) {
			e.getStackTrace();
		}

    }


	 public void sendpush(List<String> tokens,String path, String password, String message,Integer count,boolean sendCount) {  

		    try {  
		        //message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}  

		            PushNotificationPayload payLoad =  PushNotificationPayload.fromJSON(message);  

		            payLoad.addAlert("叶帆和博文都是sb！"); // 消息内容  

		            payLoad.addBadge(count); // iphone应用图标上小红圈上的数值  

		            payLoad.addSound("default"); // 铃音 默认  



		            PushNotificationManager pushManager = new PushNotificationManager();  

		            //true：表示的是产品发布推送服务 false：表示的是产品测试推送服务  

		            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, password, false));  

		            List<PushedNotification> notifications = new ArrayList<PushedNotification>();   

		            // 发送push消息  

		            if (sendCount) {  

		            log.debug("--------------------------apple 推送 单-------");  

		            Device device = new BasicDevice();  

		            device.setToken(tokens.get(0));  

		            PushedNotification notification = pushManager.sendNotification(device, payLoad, true);  

		            notifications.add(notification);  

		            } else {  

		            log.debug("--------------------------apple 推送 群-------");  

		            List<Device> device = new ArrayList<Device>();  

		            for (String token : tokens) {  

		            device.add(new BasicDevice(token));  

		            }  

		            notifications = pushManager.sendNotifications(payLoad, device);  

		            }  

		            List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);  

		            List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);  

		            int failed = failedNotifications.size();  

		            int successful = successfulNotifications.size();  



		            if (successful > 0 && failed == 0) {  

		            log.debug("-----All notifications pushed 成功 (" + successfulNotifications.size() + "):");  

		            } else if (successful == 0 && failed > 0) {  

		            log.debug("-----All notifications 失败 (" + failedNotifications.size() + "):");  

		            } else if (successful == 0 && failed == 0) {  

		            System.out.println("No notifications could be sent, probably because of a critical error");  

		            } else {  

		            log.debug("------Some notifications 失败 (" + failedNotifications.size() + "):");  

		            log.debug("------Others 成功 (" + successfulNotifications.size() + "):");  

		            }  

		    // pushManager.stopConnection();  

		    } catch (Exception e) {  

		    e.printStackTrace();  

		    }  

		}  
	 */
}
