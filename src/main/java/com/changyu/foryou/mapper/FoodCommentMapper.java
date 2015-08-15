package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.FoodComment;

public interface FoodCommentMapper {
    int insert(FoodComment record);

    int insertSelective(FoodComment record);

	Long getCommentCountsById(Map<String, Object> paramMap);

	Integer getFoodCommentCount(Map<String, Object> paramMap);

	Float getAvageGrade(Map<String, Object> paramMap);

	List<FoodComment> getCommentsById(Map<String, Object> paramMap);

	List<FoodComment> getAllComments(Map<String, Object> paramMap);

	Integer deleteFoodComment(Map<String, Object> paramMap);

	Integer calCommentCount(Map<String, Object> paramMap);
}