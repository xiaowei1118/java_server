package com.changyu.foryou.model;

import java.util.Date;
import java.util.List;


public class TogetherOrder {
    private String togetherId;
    
    private Short status;
    
    private Date togetherDate ;
    
    private List<SmallOrder> smallOrders;
    
	public String getTogetherId() {
		return togetherId;
	}
	public void setTogetherId(String togetherId) {
		this.togetherId = togetherId;
	}
	public List<SmallOrder> getSmallOrders() {
		return smallOrders;
	}
	public void setSmallOrders(List<SmallOrder> smallOrders) {
		this.smallOrders = smallOrders;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Date getTogetherDate() {
		return togetherDate;
	}
	public void setTogetherDate(Date togetherDate) {
		this.togetherDate = togetherDate;
	}
    
    
}
