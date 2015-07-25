package com.changyu.foryou.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CityWithCampus;
import com.changyu.foryou.service.CampusService;
import com.changyu.foryou.tools.Constants;

@Controller
@RequestMapping("/campus")
public class CampusController {
	private CampusService campusService;

	public CampusService getCampusService() {
		return campusService;
	}

	@Autowired
	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	/**
	 * 获取校区
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("getAllCampus")
	public @ResponseBody 
	 Map<String,Object> getAllCampus(Integer limit,Integer page){
		Map<String,Object> resultMap=new HashMap<String,Object>();

		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();
			if(page!=null&&limit!=null){
			   paramMap.put("limit", limit);
			   paramMap.put("offset", (page-1)*limit);
			}
		 
		    
		    List<Campus> campus=campusService.getAllCampus(paramMap);
		    
	    	resultMap.put(Constants.STATUS, Constants.SUCCESS);
	    	resultMap.put(Constants.MESSAGE, "获取校区成功！");
	    	resultMap.put("campus", campus);
		   
		} catch (Exception e) {
			e.getStackTrace();
			resultMap.put(Constants.STATUS, Constants.SUCCESS);
	    	resultMap.put(Constants.MESSAGE, "获取校区失败！");
		}
		return resultMap;
	}
	
	/**
	 * 获取相应的校区和城市
	 * @return
	 */
	@RequestMapping("/getCampusAndCity")
	public @ResponseBody Map<String,Object> getCampusByCity(){
		Map<String,Object> resultMap=new HashMap<String,Object>();

		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();	 
		    
		    List<CityWithCampus> campus=campusService.getCampusWithCity(paramMap);
		    
	    	resultMap.put(Constants.STATUS, Constants.SUCCESS);
	    	resultMap.put(Constants.MESSAGE, "获取校区成功！");
	    	resultMap.put("campus", campus);
		   
		} catch (Exception e) {
			e.getStackTrace();
			resultMap.put(Constants.STATUS, Constants.FAILURE);
	    	resultMap.put(Constants.MESSAGE, "获取校区失败！");
		}
		return resultMap;
	}
	
	/**
	 * 根据校区名获取校区Id
	 */
	
	@RequestMapping("/getIdByName")
	public @ResponseBody Map<String,Object> getIdByName(@RequestParam String campusName){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();	
			paramMap.put("campusName", campusName.trim());
			Integer campusId=campusService.getIdByName(paramMap);
			map.put(Constants.STATUS, Constants.SUCCESS);
	    	map.put(Constants.MESSAGE, "获取校区Id成功！");
			map.put("campusId", campusId);	
			
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
	    	map.put(Constants.MESSAGE, "获取校区Id失败！");
		}
		
		return map;
	}
	

}
