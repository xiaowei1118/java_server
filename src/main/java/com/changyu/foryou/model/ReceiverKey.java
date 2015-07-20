package com.changyu.foryou.model;

public class ReceiverKey {
    private String rank;

    private String phoneId;

    public ReceiverKey(){
    	
    }
     
    public ReceiverKey(String phoneId, String rank) {
		this.rank = rank;
		this.phoneId = phoneId;
	}

	public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId == null ? null : phoneId.trim();
    }
}