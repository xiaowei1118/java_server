package com.changyu.foryou.model;

import java.util.Date;

public class FoodComment {
    private Long foodId;
    private Integer campusId;
    
    private String comment;

    private String foodName;
    
    private Date date;

    private Short grade;

    private String imgUrl;
    
    private String nickName;
    
    private String phone;

    private Short tag;
    
    private Short isHidden;
    
    private Integer orderCount;
    
    private Long orderId;

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Short getGrade() {
        return grade;
    }

    public void setGrade(Short grade) {
        this.grade = grade;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Short getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Short isHidden) {
		this.isHidden = isHidden;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}