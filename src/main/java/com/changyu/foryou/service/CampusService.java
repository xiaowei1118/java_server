package com.changyu.foryou.service;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CityWithCampus;

public interface CampusService {

	List<Campus> getAllCampus(Map<String, Object> paramMap);

	List<CityWithCampus> getCampusWithCity(Map<String, Object> paramMap);

	Integer getIdByName(Map<String, Object> paramMap);

	public Integer closeCampus(Map<String, Object> requestMap);
}
