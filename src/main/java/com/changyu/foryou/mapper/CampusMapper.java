package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CampusAdmin;
import com.changyu.foryou.model.City;
import com.changyu.foryou.model.CityWithCampus;

public interface CampusMapper {
    int deleteByPrimaryKey(Integer campusId);

    int insert(Campus record);

    int insertSelective(Campus record);

    Campus selectByPrimaryKey(Integer campusId);

    int updateByPrimaryKeySelective(Campus record);

    int updateByPrimaryKey(Campus record);

    List<Campus> selectAllCampus(Map<String, Object> paramMap);

	List<CityWithCampus> getCampusWithCity(Map<String, Object> paramMap);

	Integer getIdByName(Map<String, Object> paramMap);
	
	Integer closeCampus(Map<String, Object> requestMap);
	
	Campus selectCampusById(Map<String, Object> paramMap);
	
	CampusAdmin getCampusIdByAdmin(Map<String, Object> paramMap);
	
	List<CampusAdmin> getAllCampusAdmin(Map<String, Object> paramMap);

	Integer updateCampusAdmin(Map<String, Object> paramMap);
	
	Integer addCampus(Map<String, Object> paramMap);

	Campus getCampusByOrder(Map<String, Object> paramMap);
	
	Integer deleteCampusAdmin(Map<String, Object> paramMap);
	
	Integer insertCampusAdmin(Map<String, Object> paramMap);
	
	List<City> selectAllCity();
	
	Integer insertCity(Map<String, Object> paramMap);
	
	City getCityByName(String cityName);
	
	Integer updateCampus(Map<String, Object> paramMap);

	String getOldPassword(Map<String, Object> paramMap);           //获取原密码

	int updateCampusAdminPassword(Map<String, Object> paramMap);   //更新密码

	String getCampusName(Integer campusId);
}