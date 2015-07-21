package com.changyu.foryou.model;

import java.util.Set;

public class CityWithCampus extends City{
	private Set<Campus> campuses;

	public Set<Campus> getCampuses() {
		return campuses;
	}

	public void setCampuses(Set<Campus> campuses) {
		this.campuses = campuses;
	}
	

}
