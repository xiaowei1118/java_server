package com.changyu.foryou.model;

import java.util.Date;

public class PackageOrder {
    private String packageId;

    private String phoneId;

    private Date createTime;

    private String reserveTime;

    private String rank;

    private Byte tag;

    private Float price;
    
    private Byte deliverType;

    private String message;
    
    private String deliverPhone;
    
    private String adminPhone;
    
    public PackageOrder(){
    	
    }
    
    public PackageOrder(String phoneId2, String reserveTime2, String rank2,Float price,
			short deliverType2, String message,String deliverPhone) {
    	createTime=new Date();
    	phoneId=phoneId2;
    	reserveTime=reserveTime2;
    	rank=rank2;
    	deliverType=(byte)deliverType2;
    	rank=rank2;
    	tag=(short)1;
    	this.message=message;
    	packageId=phoneId2+""+new Date().getTime();
    	this.price=price;
    	this.deliverPhone=deliverPhone;
	}

	public String getPackeageId() {
        return packageId;
    }

    public void setPackeageId(String packeageId) {
        this.packageId = packeageId == null ? null : packeageId.trim();
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId == null ? null : phoneId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime == null ? null : reserveTime.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public Byte getTag() {
        return tag;
    }

    public void setTag(Byte tag) {
        this.tag = tag;
    }

    public Byte getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Byte deliverType) {
        this.deliverType = deliverType;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getDeliverPhone() {
		return deliverPhone;
	}

	public void setDeliverPhone(String deliverPhone) {
		this.deliverPhone = deliverPhone;
	}
}