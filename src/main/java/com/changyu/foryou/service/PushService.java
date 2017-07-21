package com.changyu.foryou.service;

public interface  PushService {
	void sendPush(String phone, String message, Integer count);

	void sendPushByTag(String tag, String string, int j);
}
