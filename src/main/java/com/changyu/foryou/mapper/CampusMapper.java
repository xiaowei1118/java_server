package com.changyu.foryou.mapper;

import com.changyu.foryou.model.Campus;

public interface CampusMapper {
    int deleteByPrimaryKey(Integer campusId);

    int insert(Campus record);

    int insertSelective(Campus record);

    Campus selectByPrimaryKey(Integer campusId);

    int updateByPrimaryKeySelective(Campus record);

    int updateByPrimaryKey(Campus record);
}