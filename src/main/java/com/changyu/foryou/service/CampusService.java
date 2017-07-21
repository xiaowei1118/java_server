package com.changyu.foryou.service;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CampusAdmin;
import com.changyu.foryou.model.City;
import com.changyu.foryou.model.CityWithCampus;

public interface CampusService {

	List<Campus> getAllCampus(Map<String, Object> paramMap);

	List<CityWithCampus> getCampusWithCity(Map<String, Object> paramMap);

	Integer getIdByName(Map<String, Object> paramMap);

	Integer closeCampus(Map<String, Object> requestMap);
	
	Campus getCampusById(Map<String, Object> paramMap);
	
	CampusAdmin getCampusIdByAdmin(Map<String, Object> paramMap);
	
	List<CampusAdmin> getAllCampusAdmin(Map<String, Object> paramMap);
	
	Integer updateCampusAdmin(Map<String, Object> paramMap);
	
	Map<String,Object> addCampus(Map<String, Object> paramMap);

	Campus getCampus(Map<String, Object> paramMap);      //从订单信息里面获取校区信息
	
	Integer deleteCampusAdmin(Map<String,Object> paramMap);		//删除某校区的某管理员
	
	Integer addCampusAdmin(Map<String, Object> paramMap);	//添加校区管理员
	
	List<City> getAllCity();			///返回所有城市
	
	Integer addCity(Map<String, Object> paramMap);		//添加城市
	
	City getCityByName(String cityName);
	
	Integer updateCampus(Map<String, Object> paramMap);

	String getOldPassword(Map<String, Object> paramMap);

	int updateCampusAdminPassword(Map<String, Object> paramMap);

	String getCampusName(Integer campusId);          //根据校区id获取校区名称
}
