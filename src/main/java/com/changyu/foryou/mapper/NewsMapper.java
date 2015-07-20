package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.News;
import com.changyu.foryou.model.SmallNews;

public interface NewsMapper {
    int deleteByPrimaryKey(Long newsId);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Long newsId);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

	List<SmallNews> getSmallNews(Map<String,Object> map);

	List<News> getPcAllNews(Map<String, Object> requestMap);
}