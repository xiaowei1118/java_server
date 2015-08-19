package com.changyu.foryou.mapper;

import com.changyu.foryou.model.Preferential;

public interface PreferentialMapper {
    int deleteByPrimaryKey(Integer preferentialId);

    int insert(Preferential record);

    int insertSelective(Preferential record);

    Preferential selectByPrimaryKey(Integer preferentialId);

    int updateByPrimaryKeySelective(Preferential record);

    int updateByPrimaryKey(Preferential record);
}