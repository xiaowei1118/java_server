package com.changyu.foryou.model;

public class Receiver extends ReceiverKey {
    private String phone;

    private String name;

    private String address;

    private Short tag;
    
    private Integer campusId;
    
    private String campusName;

    public Receiver(){
    	
    }
    
    public Receiver(String phoneId,String phone, String name, String address,Integer campusId) {
		super.setPhoneId(phoneId);
		this.phone = phone;
		this.name = name;
		this.address = address;
		this.campusId=campusId;
	}


	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

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
		this.campusName = campusName;
	}
}