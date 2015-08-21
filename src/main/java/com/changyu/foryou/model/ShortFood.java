package com.changyu.foryou.model;

public class ShortFood {
	private Long foodId;
    private Integer campusId;
    
	private String name;

	private Float price;

	private Integer foodCount;
	
	public Integer getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(Integer foodCount) {
		this.foodCount = foodCount;
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
		this.name = name;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Short getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Short isDiscount) {
		this.isDiscount = isDiscount;
	}

	public Long getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(Long saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Short getIsFullDiscount() {
		return isFullDiscount;
	}

	public void setIsFullDiscount(Short isFullDiscount) {
		this.isFullDiscount = isFullDiscount;
	}

	private Float discountPrice;

	private String imgUrl;

	private Short isDiscount;

	private Long saleNumber;
	
	private Short isFullDiscount;
}
