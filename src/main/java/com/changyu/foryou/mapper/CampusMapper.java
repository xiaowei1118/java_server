package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CampusAdmin;
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
}