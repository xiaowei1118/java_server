package com.changyu.foryou.model;

import java.util.Date;

/**
 * 用户web管理端订单接口
 * @author 殿下
 *
 */
public class PCOrder {
    
    private String togetherId;
    
    private String phone;
    
    private String name; //食品名称

    private Float price;  //食品总价格

    private String adminName;
    
    private Integer orderCount;
    
    private String address;   //配送地址
       
    private Short isDiscount;
    
    private Float discountPrice;   //折扣价
    
    private Float foodPrice;
    
    private String receiverPhone;   //收货人手机号
	
	private Date togetherDate;    

    private String adminPhone;     //配送人手机号
    
	public String getTogetherId() {
		return togetherId;
	}


	public void setTogetherId(String togetherId) {
		this.togetherId = togetherId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public Integer getOrderCount() {
		return orderCount;
	}


	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Date getTogetherDate() {
		return togetherDate;
	}


	public void setTogetherDate(Date togetherDate) {
		this.togetherDate = togetherDate;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getReceiverPhone() {
		return receiverPhone;
	}


	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}


	public Short getIsDiscount() {
		return isDiscount;
	}


	public void setIsDiscount(Short isDiscount) {
		this.isDiscount = isDiscount;
	}


	public Float getDiscountPrice() {
		return discountPrice;
	}


	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}


	public Float getFoodPrice() {
		return foodPrice;
	}


	public void setFoodPrice(Float foodPrice) {
		this.foodPrice = foodPrice;
	}


	public String getAdminPhone() {
		return adminPhone;
	}


	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	

  
}