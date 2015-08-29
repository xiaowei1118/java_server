package com.changyu.foryou.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.mapper.HotSearchMapper;
import com.changyu.foryou.model.HotSearch;
import com.changyu.foryou.service.HotSearchService;

@Service("/hotSearchService")
public class HotSearchServiceImpl implements HotSearchService {
    @Resource private HotSearchMapper hotSearchMapper;
    
	@Override
	public List<HotSearch> getHotSearchs(Map<String,Object> paramMap) {
		return hotSearchMapper.getHotSearchs(paramMap);
	}

	@Override
	public int setNot2Display(Map<String, Object> paramMap) {
		return hotSearchMapper.setNot2Display(paramMap);
	}

	@Override
	public int set2Display(Map<String, Object> paramMap) {
		return hotSearchMapper.set2Dispaly(paramMap);
	}

	@Override
	public int deleteHotSearchs(Map<String, Object> paramMap) {
		String[] hotIdsStrings=(String[])paramMap.get("hotIds");
		
		int flag=-1;
		System.out.println(JSON.toJSONString(hotIdsStrings));
		for (String id : hotIdsStrings) {
			flag=hotSearchMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return flag;
	}

	@Override
	public int insert(HotSearch hotSearch) {
		return hotSearchMapper.insertSelective(hotSearch);
	}

	@Override
	public int update(HotSearch hotSearch) {
		return hotSearchMapper.updateByPrimaryKeySelective(hotSearch);
	}

}
