package com.changyu.foryou.mapper;

import com.changyu.foryou.model.PushMessage;

public interface PushMessageMapper {
    int deleteByPrimaryKey(Integer pushId);

    int insert(PushMessage record);

    int insertSelective(PushMessage record);

    PushMessage selectByPrimaryKey(Integer pushId);

    int updateByPrimaryKeySelective(PushMessage record);

    int updateByPrimaryKey(PushMessage record);
}