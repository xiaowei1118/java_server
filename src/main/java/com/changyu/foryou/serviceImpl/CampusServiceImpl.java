package com.changyu.foryou.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changyu.foryou.mapper.CampusMapper;
import com.changyu.foryou.mapper.FoodCategoryMapper;
import com.changyu.foryou.mapper.FoodMapper;
import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CampusAdmin;
import com.changyu.foryou.model.City;
import com.changyu.foryou.model.CityWithCampus;
import com.changyu.foryou.service.CampusService;
import com.changyu.foryou.tools.Constants;

@Service("campusService")
public class CampusServiceImpl implements CampusService {
	private CampusMapper campusMapper;
	@Resource(name="foodCategoryMapper")
	private FoodCategoryMapper foodCategoryMapper;

	@Autowired
	public void setCampusMapper(CampusMapper campusMapper) {
		this.campusMapper = campusMapper;
	}

	public List<Campus> getAllCampus(Map<String, Object> paramMap) {
		return campusMapper.selectAllCampus(paramMap);
	}

	public List<CityWithCampus> getCampusWithCity(Map<String, Object> paramMap) {
		return campusMapper.getCampusWithCity(paramMap);
	}

	public Integer getIdByName(Map<String, Object> paramMap) {
		return campusMapper.getIdByName(paramMap);
	}

	public Integer closeCampus(Map<String, Object> requestMap) {
		// TODO Auto-generated method stub
		return campusMapper.closeCampus(requestMap);
	}

	public Campus getCampusById(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return campusMapper.selectCampusById(paramMap);
	}

	public CampusAdmin getCampusIdByAdmin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return campusMapper.getCampusIdByAdmin(paramMap);
	}

	public List<CampusAdmin> getAllCampusAdmin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return campusMapper.getAllCampusAdmin(paramMap);
	}

	public Integer updateCampusAdmin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return campusMapper.updateCampusAdmin(paramMap);
	}

	public Map<String, Object> addCampus(Map<String, Object> paramMap) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		// 只有在添加完校区且添加了8个默认分类之后才算添加校区成功
		Integer addCampus = campusMapper.addCampus(paramMap);
		int count = 0;
		if (addCampus != -1 && addCampus != 0) {
			// 说明添加校区成功
			//现在添加分类
			//#{categoryId},#{campusId},#{category},#{imgUrl},#{parentId},#{tag},#{serial},#{isOpen}
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			int campusId = campusMapper.getIdByName(paramMap);
			int categoryId = 0;
			String category = null;
			String imgUrl = null;
			Integer parentId = 0;
			Short tag = 1;
			Integer serial = -1;
			Short isOpen = 1;
			for(int i=1; i<9; i++){
				//int length = String.valueOf(paramMap.get("campusId")).length();
				categoryId = campusId*100+i;
				serial = i;
				switch(i){
				case 1:
					category = "早餐上门";
					break;
				case 2:
					category = "家政服务";
					break;
				case 3:
					category = "水果上门";
					break;
				case 4:
					category = "快递代取";
					break;
				case 5:
					category = "小优推荐";
					break;
				case 6:
					category = "最新体验";
					break;
				case 7:
					category = "特惠秒杀";
					break;
				case 8:
					category = "更多分类";
					break;
				}
				categoryMap.put("categoryId", categoryId);
				categoryMap.put("campusId", campusId);
				categoryMap.put("category", category);
				categoryMap.put("imgUrl", imgUrl);
				categoryMap.put("parentId", parentId);
				categoryMap.put("tag", tag);
				categoryMap.put("serial", serial);
				categoryMap.put("isOpen", isOpen);

				count += foodCategoryMapper.addCategoryWhenAddCampus(categoryMap);
			}
			if(count<8){
				// 说明添加校区失败
				responseMap.put(Constants.STATUS, Constants.FAILURE);
				responseMap.put(Constants.MESSAGE, "添加校区失败");
			}else{
				responseMap.put(Constants.STATUS, Constants.SUCCESS);
				responseMap.put(Constants.MESSAGE, "添加校区成功");
			}
			//foodCategoryMapper.addCategoryWhenAddCampus(paramMap);
		} else {
			// 说明添加校区失败
			responseMap.put(Constants.STATUS, Constants.FAILURE);
			responseMap.put(Constants.MESSAGE, "添加校区失败");
		}
		return responseMap;
	}
	public Campus getCampus(Map<String, Object> paramMap) {
		return campusMapper.getCampusByOrder(paramMap);
	}

	public Integer deleteCampusAdmin(Map<String, Object> paramMap) {
		return campusMapper.deleteCampusAdmin(paramMap);
	}

	public Integer addCampusAdmin(Map<String, Object> paramMap) {
		return campusMapper.insertCampusAdmin(paramMap);
	}

	public List<City> getAllCity() {
		return campusMapper.selectAllCity();
	}

	public Integer addCity(Map<String, Object> paramMap) {
		return campusMapper.insertCity(paramMap);
	}

	public City getCityByName(String cityName) {
		return campusMapper.getCityByName(cityName);
	}

	public Integer updateCampus(Map<String, Object> paramMap) {
		return campusMapper.updateCampus(paramMap);
	}

	public String getOldPassword(Map<String, Object> paramMap) {
		return campusMapper.getOldPassword(paramMap);
	}

	public int updateCampusAdminPassword(Map<String, Object> paramMap) {
		return campusMapper.updateCampusAdminPassword(paramMap);
	}

	public String getCampusName(Integer campusId) {
		return campusMapper.getCampusName(campusId);
	}

}
