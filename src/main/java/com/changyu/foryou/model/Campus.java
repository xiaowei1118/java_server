package com.changyu.foryou.model;

import java.sql.Timestamp;


public class Campus {
	private Integer campusId;

	private String campusName;

	private Integer cityId;
	
	private String cityName;

	private Timestamp openTime;

	private Timestamp closeTime;

	private Short status;

	private String closeReason;

	private String customService;
	/* private Double locationX;

    private Double locationY;*/

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName == null ? null : campusName.trim();
	}

	public Integer getCityId() {
		return cityId;
	}

	public Timestamp getOpenTime() {
		return openTime;
	}

	public String getCustomService() {
		return customService;
	}

	public void setCustomService(String customService) {
		this.customService = customService;
	}

	public void setOpenTime(Timestamp openTime) {
		this.openTime = openTime;
	}

	public Timestamp getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/* public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }*/
}