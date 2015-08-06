package com.changyu.foryou.model;

import java.util.Date;
import java.util.List;

public class BigOrder {
	private String togetherId;
	
	private Double totalPrice;
	
	private Date date;
	
	private Receiver receiver;
	
	private List<Order> orders;



	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getTogetherId() {
		return togetherId;
	}

	public void setTogetherId(String togetherId) {
		this.togetherId = togetherId;
	}

}
