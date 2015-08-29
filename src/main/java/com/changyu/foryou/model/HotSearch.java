package com.changyu.foryou.model;

public class HotSearch {
    private Integer hotId;

    private String displayName;

    private String searchTag;

    private Long createTime;

    private Byte isDisplay;

    private Integer campusId;

   

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag == null ? null : searchTag.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Byte getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Byte isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

	public Integer getHotId() {
		return hotId;
	}

	public void setHotId(Integer hotId) {
		this.hotId = hotId;
	}
}