package com.changyu.foryou.mapper;

import java.util.Map;

import com.changyu.foryou.model.AppKey;

public interface AppKeyMapper {
    int insert(AppKey record);

    int insertSelective(AppKey record);

	String SelectKey(Map<String, Object> paramMap);   //搜索
}