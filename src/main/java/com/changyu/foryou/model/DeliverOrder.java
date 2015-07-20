package com.changyu.foryou.model;

import java.util.Date;
import java.util.List;

public class DeliverOrder {

	private String togetherId;

	private String nickName;

	private Short status;

	private Float totalPrice;

	private String address;   //配送地址

	private String customePhone;  

	private Date togetherDate;
	
	private String reserveTime;
	
	private String message;

	private String adminName;
	
	private List<DeliverChildOrder> orderList;

	public String getTogetherId() {
		return togetherId;
	}



	public void setTogetherId(String togetherId) {
		this.togetherId = togetherId;
	}



	public String getNickName() {
		return nickName;
	}



	public void setNickName(String nickName) {
		this.nickName = nickName;
	}



	public Short getStatus() {
		return status;
	}



	public void setStatus(Short status) {
		this.status = status;
	}



	public Float getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCustomePhone() {
		return customePhone;
	}



	public void setCustomePhone(String customePhone) {
		this.customePhone = customePhone;
	}



	public Date getTogetherDate() {
		return togetherDate;
	}



	public void setTogetherDate(Date togetherDate) {
		this.togetherDate = togetherDate;
	}



	public List<DeliverChildOrder> getOrderList() {
		return orderList;
	}



	public void setOrderList(List<DeliverChildOrder> orderList) {
		this.orderList = orderList;
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



	public String getAdminName() {
		return adminName;
	}



	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}




}