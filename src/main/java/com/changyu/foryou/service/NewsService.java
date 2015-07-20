package com.changyu.foryou.service;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.News;
import com.changyu.foryou.model.SmallNews;


public interface NewsService {

	List<SmallNews> getSmallNews(Map<String,Object> map);

	News getNewsById(Long newsId);

	List<News> getPcAllNews(Map<String, Object> requestMap);

	Integer addNews(News news);

	int deleteById(String id);

}
