package com.changyu.foryou.model;

import java.util.Date;

public class SuperAdminOrder {
	private String togetherId;
	private Date togetherDate;
	private String address;
	private String userPhone;
	private String adminPhone;
	private String adminName;
	private Float price;
    private String reserveTime;
    private String message;
    private String chargeId;
    
	public Date getTogetherDate() {
		return togetherDate;
	}
	public void setTogetherDate(Date togetherDate) {
		this.togetherDate = togetherDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getAdminPhone() {
		return adminPhone;
	}
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getTogetherId() {
		return togetherId;
	}
	public void setTogetherId(String togetherId) {
		this.togetherId = togetherId;
	}

	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

}
