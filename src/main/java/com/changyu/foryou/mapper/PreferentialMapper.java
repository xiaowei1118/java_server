package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Preferential;

public interface PreferentialMapper {
    int deleteByPrimaryKey(Integer preferentialId);

    int insert(Preferential record);

    int insertSelective(Preferential record);

    Preferential selectByPrimaryKey(Integer preferentialId);

    int updateByPrimaryKeySelective(Preferential record);

    int updateByPrimaryKey(Preferential record);

	List<Preferential> getPreferential(Map<String, Object> paramMap);

}