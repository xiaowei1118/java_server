package com.changyu.foryou.tools;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushInterface {

    //向全体发送推送
    public static PushPayload buildPushObject_all_all_alert(String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(message))
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(message)
                        .addExtra("from", "foryou")
                        .build())
                .build();
    }

    //向别名发送推送,android和ios
    public static PushPayload buildPushObject_android_and_ios_alias_alert(String alertString, String phone) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(phone))
                .setNotification(Notification.alert(alertString))
                .setOptions(Options.newBuilder().setApnsProduction(false
                ).build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(alertString)
                        .addExtra("from", "foryou")
                        .build())
                .build();
    }

    //通过tag推送
    public static PushPayload buildPushObject_android_and_ios_tag_alert(
            String message, String tag) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.alert(message))
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(message)
                        .addExtra("from", "foryou")
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_android__alertWithTitle(String message, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(message, title, null))
                .build();
    }

    public static PushPayload buildPushObject_android_and_ios() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title").build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String message, String msg_content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(message)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content(msg_content))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String msg_content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }

    public static PushPayload bulidPushObject_ios_alert(String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(message))
                .setMessage(Message.newBuilder()
                        .setMsgContent(message)
                        .addExtra("from", "mickey")
                        .build())
                .build();
    }

    public static PushPayload bulidPushObject_android_alert(String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(message))
                .setMessage(Message.newBuilder()
                        .setMsgContent(message)
                        .addExtra("from", "mickey")
                        .build())
                .build();
    }


}
