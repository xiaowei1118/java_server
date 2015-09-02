package com.changyu.foryou.model;

public class TradeInfo {
	private Integer orderCount;
	private Float tradeVolume;
	private Float tradeVolumeAliPay;
	private Float tradeVolumeWeChatPay;
	private String infoDateType;
	
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Float getTradeVolume() {
		return tradeVolume;
	}
	public void setTradeVolume(Float tradeVolume) {
		this.tradeVolume = tradeVolume;
	}
	public Float getTradeVolumeAliPay() {
		return tradeVolumeAliPay;
	}
	public void setTradeVolumeAliPay(Float tradeVolumeAliPay) {
		this.tradeVolumeAliPay = tradeVolumeAliPay;
	}
	public Float getTradeVolumeWeChatPay() {
		return tradeVolumeWeChatPay;
	}
	public void setTradeVolumeWeChatPay(Float tradeVolumeWeChatPay) {
		this.tradeVolumeWeChatPay = tradeVolumeWeChatPay;
	}
	public String getInfoDateType() {
		return infoDateType;
	}
	public void setInfoDateType(String infoDateType) {
		this.infoDateType = infoDateType;
	}
}
