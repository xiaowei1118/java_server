package com.changyu.foryou.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.changyu.foryou.model.Campus;
import com.changyu.foryou.model.CampusAdmin;
import com.changyu.foryou.model.CityWithCampus;
import com.changyu.foryou.service.CampusService;
import com.changyu.foryou.service.FoodService;
import com.changyu.foryou.tools.Constants;

@Controller
@RequestMapping("/campus")
public class CampusController {
	private CampusService campusService;
	
	@Autowired
	private FoodService foodService;

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
	 * @param campusName
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
	
	//一键关店
	/**
	 * 
	 * @param campusId
	 * @param closeTime
	 * @param reason 关店原因
	 * @param status 关店传0，开店传1。
	 * @return
	 */
	@RequestMapping("/closeCampus")
	public @ResponseBody Map<String, Object> closeCampus(@RequestParam Integer campusId, @RequestParam String closeReason, @RequestParam Short status){
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Map<String, Object> responseMap = new HashMap<String,Object>();
		try{
			//Calendar calendar=Calendar.getInstance();
			//Date date=calendar.getTime();   //设置反馈时的日期
			requestMap.put("campusId", campusId);
			//requestMap.put("closeTime", date);
			requestMap.put("closeReason", closeReason);
			requestMap.put("status", status);
			Integer isClosed = campusService.closeCampus(requestMap);
			
			responseMap.put("isClosed", isClosed);
			responseMap.put(Constants.STATUS, Constants.SUCCESS);
			responseMap.put(Constants.MESSAGE, "关店成功！");
		}catch(Exception e){
			e.getStackTrace();
			responseMap.put(Constants.STATUS, Constants.FAILURE);
			responseMap.put(Constants.MESSAGE, "关店失败！");
		}
		return responseMap;
	}

	//selectByPrimaryKey
	/**
	 * @param campusName
	 * 根据校区id获取校区
	 */
	
	@RequestMapping("/getCampusById")
	public @ResponseBody Map<String,Object> getCampusById(@RequestParam Integer campusId){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();	
			paramMap.put("campusId", campusId);
			Campus campus=campusService.getCampusById(paramMap);
			map.put(Constants.STATUS, Constants.SUCCESS);
	    	map.put(Constants.MESSAGE, "获取校区成功！");
			map.put("campus", JSON.toJSON(campus));	
			
		} catch (Exception e) {
			e.getStackTrace();
			map.put(Constants.STATUS, Constants.FAILURE);
	    	map.put(Constants.MESSAGE, "获取校区失败！");
		}
		
		return map;
	}
	
	@RequestMapping("getCampusIdByAdmin")
	public @ResponseBody Map<String, Object> getCampusIdByAdmin(@RequestParam String campusAdminName){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Map<String,Object> requestMap = new HashMap<String,Object>();

		requestMap.put("campusAdmin", campusAdminName);
		
		CampusAdmin campusAdminInfo = campusService.getCampusIdByAdmin(requestMap);
		
		responseMap.put("CampusAdmin", campusAdminInfo);
		
		return responseMap;
	}
	
	/**
	 * 返回校区管理员
	 * @return
	 */
	@RequestMapping("getAllCampusAdmin")
	public @ResponseBody JSONArray getAllCampusAdmin(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", 0);
		
		List<CampusAdmin> campusAdmins = campusService.getAllCampusAdmin(paramMap);
		JSONArray array = JSON.parseArray(JSON.toJSONStringWithDateFormat(campusAdmins, "yyyy-MM-dd"));
		
		return array;
	}
	
	/**
	 * 修改校区管理员
	 * @param campusId
	 * @param campusAdminName
	 * @return
	 */
	@RequestMapping("/updateCampusAdmin")
	public @ResponseBody Map<String, Object> updateCampusAdmin(@RequestParam Integer campusId, @RequestParam String campusAdminName){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("campusId", campusId);
		paramMap.put("campusAdmin", campusAdminName);
		
		Integer result = campusService.updateCampusAdmin(paramMap);
		
		if(result==0||result==-1){
			//没更新成功
			responseMap.put(Constants.STATUS, Constants.FAILURE);
			responseMap.put(Constants.MESSAGE, "修改校区的管理员失败！");
		}else{
			responseMap.put(Constants.STATUS, Constants.SUCCESS);
			responseMap.put(Constants.MESSAGE, "修改校区的管理员成功！");
		}
		
		return responseMap;
	}
	
	/**
	 * 添加校区，默认添加8个分类
	 * @param campusId
	 * @param campusName
	 * @param cityId
	 * @param openTime
	 * @param closeTime
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("addCampus")
	public @ResponseBody Map<String, Object> addCampus(@RequestParam Integer campusId, @RequestParam String campusName, @RequestParam Integer cityId, @RequestParam String openTime, @RequestParam String closeTime, @RequestParam Short status) throws ParseException{
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date openTimeDate = sdf.parse(openTime);
		Date closeTimeDate = sdf.parse(closeTime);
		
		paramMap.put("campusId", null);
		paramMap.put("campusName", campusName);
		paramMap.put("cityId", cityId);
		paramMap.put("openTime", openTimeDate);
		paramMap.put("closeTime", closeTimeDate);
		paramMap.put("status", status);					//默认开启校区
		
		campusService.addCampus(paramMap);
		return responseMap;
	}
}
