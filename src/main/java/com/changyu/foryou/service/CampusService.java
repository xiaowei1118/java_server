package com.changyu.foryou.service;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CampusAdmin;
import com.changyu.foryou.model.CityWithCampus;

public interface CampusService {

	List<Campus> getAllCampus(Map<String, Object> paramMap);

	List<CityWithCampus> getCampusWithCity(Map<String, Object> paramMap);

	Integer getIdByName(Map<String, Object> paramMap);

	public Integer closeCampus(Map<String, Object> requestMap);
	
	public Campus getCampusById(Map<String, Object> paramMap);
	
	public CampusAdmin getCampusIdByAdmin(Map<String, Object> paramMap);
	
	public List<CampusAdmin> getAllCampusAdmin(Map<String, Object> paramMap);
	
	public Integer updateCampusAdmin(Map<String, Object> paramMap);
	
	public Map<String,Object> addCampus(Map<String, Object> paramMap);

	Campus getCampus(Map<String, Object> paramMap);      //从订单信息里面获取校区信息
}
