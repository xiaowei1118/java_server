package com.changyu.foryou.model;

import java.util.Date;
import java.util.List;

public class BigOrder {
	private String togetherId;
	
	private String totalPrice;
	
	private Date date;
	
	private Receiver receiver;
	
	private List<SmallOrder> orders;
	
	private Short status;
	
	private Short payWay;

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public List<SmallOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<SmallOrder> orders) {
		this.orders = orders;
	}

	public String getTogetherId() {
		return togetherId;
	}

	public void setTogetherId(String togetherId) {
		this.togetherId = togetherId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getPayWay() {
		return payWay;
	}

	public void setPayWay(Short payWay) {
		this.payWay = payWay;
	}

}
