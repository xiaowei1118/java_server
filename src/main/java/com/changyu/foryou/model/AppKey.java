package com.changyu.foryou.model;

public class AppKey {
    private String key;

    private String secrect;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getSecrect() {
        return secrect;
    }

    public void setSecrect(String secrect) {
        this.secrect = secrect == null ? null : secrect.trim();
    }
}