package com.changyu.foryou.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changyu.foryou.mapper.CampusMapper;
import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CampusAdmin;
import com.changyu.foryou.model.CityWithCampus;
import com.changyu.foryou.service.CampusService;

@Service("campusService")
public class CampusServiceImpl implements CampusService {
    private CampusMapper campusMapper;
    
    @Autowired
	public void setCampusMapper(CampusMapper campusMapper) {
		this.campusMapper = campusMapper;
	}
    
	public List<Campus> getAllCampus(Map<String, Object> paramMap) {
		return campusMapper.selectAllCampus(paramMap);
	}

	@Override
	public List<CityWithCampus> getCampusWithCity(Map<String, Object> paramMap) {
		return campusMapper.getCampusWithCity(paramMap);
	}

	@Override
	public Integer getIdByName(Map<String, Object> paramMap) {
		return campusMapper.getIdByName(paramMap);
	}

	@Override
	public Integer closeCampus(Map<String, Object> requestMap) {
		// TODO Auto-generated method stub
		return campusMapper.closeCampus(requestMap);
	}

	@Override
	public Campus getCampusById(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return campusMapper.selectCampusById(paramMap);
	}

	@Override
	public CampusAdmin getCampusIdByAdmin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return campusMapper.getCampusIdByAdmin(paramMap);
	}

	@Override
	public List<CampusAdmin> getAllCampusAdmin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return campusMapper.getAllCampusAdmin(paramMap);
	}

	

}
