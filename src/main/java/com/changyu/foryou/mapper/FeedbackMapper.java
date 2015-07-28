package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Feedback;

public interface FeedbackMapper {
    int insert(Feedback record);

    int insertSelective(Feedback record);

	List<Feedback> getFeedbacks(Map<String, Object> paramMap);
}