package com.changyu.foryou.model;

import java.util.Date;

public class PushMessage {
    private Integer pushId;

    private String title;

    private String content;

    private String pushTag;

    private String alias;

    private Date createTime;

    private Integer validLength;

    private String isSendNow;

    private Short deviceTag;

    public Integer getPushId() {
        return pushId;
    }

    public void setPushId(Integer pushId) {
        this.pushId = pushId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPushTag() {
        return pushTag;
    }

    public void setPushTag(String pushTag) {
        this.pushTag = pushTag == null ? null : pushTag.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getValidLength() {
        return validLength;
    }

    public void setValidLength(Integer validLength) {
        this.validLength = validLength;
    }

    public String getIsSendNow() {
        return isSendNow;
    }

    public void setIsSendNow(String isSendNow) {
        this.isSendNow = isSendNow == null ? null : isSendNow.trim();
    }

    public Short getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceTag(Short deviceTag) {
        this.deviceTag = deviceTag;
    }
}