package com.changyu.foryou.model;

/**
 * 主页八个模块，并标志其是否开通
 * @author xiaowei
 *
 */
public class HomeCategory {
    private Integer categoryId;
    private Short serial;
    private Short isOpen;
	
	public Short getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Short isOpen) {
		this.isOpen = isOpen;
	}
	public Short getSerial() {
		return serial;
	}
	public void setSerial(Short serial) {
		this.serial = serial;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
    
    
}
