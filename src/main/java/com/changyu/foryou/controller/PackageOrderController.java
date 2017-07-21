package com.changyu.foryou.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changyu.foryou.model.DeliverCom;
import com.changyu.foryou.model.PackageOrder;
import com.changyu.foryou.service.PackageService;
import com.changyu.foryou.tools.Constants;

@Controller
@RequestMapping("/package")
public class PackageOrderController {
    private PackageService packageService;

	@Autowired
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}
     
	
	/**
	 * 获取快递公司列表
	 * @return
	 */
	@RequestMapping(value="/getDeliverCom")
	public @ResponseBody Map<String,Object> getDeliverCom(Integer limit,Integer page){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		try {
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("limit",limit);
			
			if(limit!=null){
				paramMap.put("offset",(page-1)*limit);
			}else{
				paramMap.put("offset",null);
			}
			
		    List<DeliverCom> deliverCom = packageService.getDeliverCom(paramMap);	
		    resultMap.put("deliverCom", deliverCom);
		    resultMap.put(Constants.STATUS,Constants.SUCCESS);
		    resultMap.put(Constants.MESSAGE, "获取快递公司成功");
		} catch (Exception e) {
			e.getStackTrace();
			resultMap.put(Constants.STATUS,Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "获取快递公司失败");
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="createPackageOrder")
	public @ResponseBody Map<String,Object> createPackageOrder(String phoneId,String reserveTime,Float price,String rank,short deliverType,String message,String deliverPhone){
	    Map<String,Object> resultMap=new HashMap<String,Object>(); 
	    
	    try{
	    	PackageOrder packageOrder=new PackageOrder(phoneId,reserveTime,rank,price,deliverType,message,deliverPhone);
	    	int flag=packageService.insert(packageOrder);
	    	if(flag!=-1&&flag!=0){
	    		resultMap.put(Constants.STATUS,Constants.SUCCESS);
		    	resultMap.put(Constants.MESSAGE,"生成订成功");
	    	}else{
	    		resultMap.put(Constants.STATUS,Constants.FAILURE);
		    	resultMap.put(Constants.MESSAGE,"生成订单失败，请重试！");
	    	}
	    }catch(Exception e){
	    	e.getStackTrace();
	    	resultMap.put(Constants.STATUS,Constants.FAILURE);
	    	resultMap.put(Constants.MESSAGE,"生成订单失败，请重试！");
	    }
	    
	    return resultMap;
	}
	
	/**
	 * 为快递无忧设置配送员
	 * @param adminPhone
	 * @return
	 */
	@RequestMapping(value="/setPackageAdmin")
	public @ResponseBody Map<String, Object> setPackageAdmin(String adminPhone){
		  Map<String,Object> resultMap=new HashMap<String,Object>(); 
		  try {
			  Map<String,Object> paramMap=new HashMap<String,Object>();
			  int flag=packageService.setPackageAdmin(paramMap);
			  if(flag!=-1&&flag!=0){
				resultMap.put(Constants.STATUS,Constants.SUCCESS);
			    resultMap.put(Constants.MESSAGE,"设置配送员成功！");
			  }else{
				resultMap.put(Constants.STATUS,Constants.FAILURE);
			    resultMap.put(Constants.MESSAGE,"设置配送员失败，请重试！");
			  }
		} catch (Exception e) {
			e.getStackTrace();
			resultMap.put(Constants.STATUS,Constants.FAILURE);
	    	resultMap.put(Constants.MESSAGE,"设置配送员失败，请重试！");
		}
		return resultMap;
	}
}
