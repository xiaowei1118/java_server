package com.changyu.foryou.service;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.HotSearch;

public interface HotSearchService {

	List<HotSearch> getHotSearchs(Map<String,Object> paramMap);

	int setNot2Display(Map<String, Object> paramMap);

	int set2Display(Map<String, Object> paramMap);

	int deleteHotSearchs(Map<String, Object> paramMap);

	int insert(HotSearch hotSearch);

	int update(HotSearch hotSearch);

}
