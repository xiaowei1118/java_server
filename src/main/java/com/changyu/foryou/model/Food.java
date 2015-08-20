package com.changyu.foryou.model;

import java.util.Date;
import java.util.List;

public class Food {
	private Long foodId;

	private String name;

	private Float price;

	private Float discountPrice;

	private Float grade;

	private String imgUrl;

	private String info;

	private Integer foodCount;
	
	private Date modifyTime;

	private Short status;

	private String foodFlag;

	private Short tag;

	private Short isDiscount;

	private Integer categoryId;

	private Float primeCost;

	private Long saleNumber;
    
	private Long commentNumber;
	
	//private List<FoodSpecial> foodSpecial;
	
	private Integer campusId;
	
	private Short toHome;
	
	private String homeImage;
	
	private String message;
	
	private Short isFullDiscount;	
	
	public Short getIsFullDiscount() {
		return isFullDiscount;
	}
	public void setIsFullDiscount(Short isFullDiscount) {
		this.isFullDiscount = isFullDiscount;
	}
	public Food(){
		
	}
	public Food(Integer campusId,Long foodId2, String name2, Float price2, Float discountPrice2, String imageurl, String info, Short status2, String foodFlag2, Short isDiscount2, Integer categoryId2, Float primeCost2){
		foodId=foodId2;
		name=name2;
		price=price2;
		discountPrice=discountPrice2;
		this.imgUrl=imageurl;
		this.info=info;
		status=status2;
		foodFlag=foodFlag2;
		isDiscount=isDiscount2;
		categoryId=categoryId2;
		primeCost=primeCost2;
		modifyTime=new Date();
		saleNumber=0l;
		this.campusId=campusId;
	}
	public Food(Long foodId2, String name2, String price2,
			String discountPrice2, String grade2, String imgUrl2, String info2,
			String status2, String foodCount2, String foodFlag2, String tag2,
			String isDiscount2, String categoryId2, String primeCost2) {
		foodId=foodId2;

		if(name2!=null){
			name=name2;
		}

		if(price2!=null){
			name=name2;
		}
		
		price=Float.valueOf(price2);
		
		if(discountPrice2!=null){
		   discountPrice=Float.valueOf(discountPrice2);
		}
		
		if(grade2!=null){
			grade=Float.valueOf(grade2);
		}

		if(imgUrl2!=null){
			imgUrl=imgUrl2;
		}
		
		if(info2!=null){
			info=info2;
		}
		
		if(status2!=null){
			status=Short.valueOf(status2);
		}
		
		if(foodFlag2!=null){
			foodFlag=foodFlag2;
		}
		
		if(tag2!=null){
			tag=Short.valueOf(tag2);
		}
		
		if(isDiscount2!=null){
			isDiscount=Short.valueOf(isDiscount2);
		}
		
		if(categoryId2!=null){
			categoryId=Integer.valueOf(categoryId2);
		}
		
		if(primeCost2!=null){
			primeCost=Float.valueOf(primeCost2);
		}
		
		modifyTime=new Date();
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl == null ? null : imgUrl.trim();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info == null ? null : info.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getFoodFlag() {
		return foodFlag;
	}

	public void setFoodFlag(String foodFlag) {
		this.foodFlag = foodFlag == null ? null : foodFlag.trim();
	}

	public Short getTag() {
		return tag;
	}

	public void setTag(Short tag) {
		this.tag = tag;
	}

	public Short getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Short isDiscount) {
		this.isDiscount = isDiscount;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Float getPrimeCost() {
		return primeCost;
	}

	public void setPrimeCost(Float primeCost) {
		this.primeCost = primeCost;
	}

	public Long getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(Long saleNumber) {
		this.saleNumber = saleNumber;
	}
	/*public List<FoodSpecial> getFoodSpecial() {
		return foodSpecial;
	}
	public void setFoodSpecial(List<FoodSpecial> foodSpecial) {
		this.foodSpecial = foodSpecial;
		this.foodCount=0;
		for (int i = 0; i < foodSpecial.size(); i++) {
			this.foodCount+=foodSpecial.get(i).getFoodCount();
		}
	}*/
	public Integer getFoodCount() {
		return foodCount;
	}
	public void setFoodCount(Integer foodCount) {
		this.foodCount = foodCount;
	}
	public Long getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Long commentNumber) {
		this.commentNumber = commentNumber;
	}
	public Integer getCampusId() {
		return campusId;
	}
	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}
	public Short getToHome() {
		return toHome;
	}
	public void setToHome(Short toHome) {
		this.toHome = toHome;
	}
	public String getHomeImage() {
		return homeImage;
	}
	public void setHomeImage(String homeImage) {
		this.homeImage = homeImage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}