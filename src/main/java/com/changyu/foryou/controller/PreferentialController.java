package com.changyu.foryou.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.changyu.foryou.model.Preferential;
import com.changyu.foryou.service.CampusService;
import com.changyu.foryou.service.PreferentialService;
import com.changyu.foryou.tools.Constants;

@Controller
@RequestMapping("preferential")
/**
 * 满减优惠
 * @author Simon Sun
 *
 */
public class PreferentialController {
	@Resource(name="preferentialService")
	private PreferentialService preferentialService;
	
	@Resource(name="campusService")
	private CampusService campusService;
	
	/**
	 * 添加满减优惠
	 * @param needNumber
	 * @param discountNum
	 * @param campusId
	 * @return
	 */
	@RequestMapping("addPref")
	@ResponseBody
	public Map<String, Object> addPref(@RequestParam Integer needNumber,@RequestParam Integer discountNum,Integer campusId, String campusName){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Preferential preferential =new Preferential();
		preferential.setPreferentialId(null);
		preferential.setNeedNumber(needNumber);
		preferential.setDiscountNum(discountNum);
		if(campusId==null){
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("campusName", campusName);
			preferential.setCampusId(campusService.getIdByName(tempMap));
		}else{
			preferential.setCampusId(campusId);
		}
		
		int flag = preferentialService.insert(preferential);
		if(flag!=0&&flag!=-1){
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "添加成功");
		}else{
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "添加失败");
		}
		return map;
	}
	
	/**
	 * 删除满减优惠
	 * @param preferentialId
	 * @return
	 */
	@RequestMapping("deletePref")
	@ResponseBody
	public Map<String, Object> deletePref(@RequestParam Integer preferentialId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		int flag = preferentialService.deleteByPrimaryKey(preferentialId);
		if(flag!=0&&flag!=-1){
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "删除成功");
		}else{
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "删除失败");
		}
		return map;
	}
	
	/**
	 * 更新满减优惠
	 * @param preferentialId
	 * @param needNumber
	 * @param discountNum
	 * @param campusId
	 * @return
	 */
	@RequestMapping("updatePref")
	@ResponseBody
	public Map<String, Object> updatePref(@RequestParam Integer preferentialId, @RequestParam Integer needNumber, @RequestParam Integer discountNum, @RequestParam Integer campusId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Preferential preferential =new Preferential();
		preferential.setPreferentialId(preferentialId);
		preferential.setNeedNumber(needNumber);
		preferential.setDiscountNum(discountNum);
		preferential.setCampusId(campusId);
		int flag = preferentialService.updateByPrimaryKey(preferential);
		if(flag!=0&&flag!=-1){
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "更新成功");
		}else{
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "更新失败");
		}
		return map;
	}
	
	/**
	 * 获取满减优惠信息（客户端分页）
	 * @param campusId
	 * @return
	 */
	@RequestMapping("getAllPref")
	@ResponseBody
	public JSONArray getAllPref(Integer campusId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("campusId", campusId);
		
		List<Preferential> list = preferentialService.getPreferential(paramMap);
		
		return (JSONArray) JSON.toJSON(list);
	}
}
