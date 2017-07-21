package com.changyu.foryou.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changyu.foryou.mapper.NewsMapper;
import com.changyu.foryou.model.News;
import com.changyu.foryou.model.SmallNews;
import com.changyu.foryou.service.NewsService;


@Service("/newsService")
public class NewsServiceImpl implements NewsService {
	private NewsMapper newsMapper;

	@Autowired
	public void setNewsMapper(NewsMapper newsMapper) {
		this.newsMapper = newsMapper;
	}

	public List<SmallNews> getSmallNews(Map<String,Object> map) {
		return newsMapper.getSmallNews(map);
	}

	public News getNewsById(Long newsId) {
		return newsMapper.selectByPrimaryKey(newsId);
	}

	public List<News> getPcAllNews(Map<String, Object> requestMap) {
		return newsMapper.getPcAllNews(requestMap);
	}

	public Integer addNews(News news) {
       return newsMapper.insert(news);		
	}

	@Override
	public int deleteById(String id) {
		return newsMapper.deleteByPrimaryKey(Long.valueOf(id));
	}

	
}
