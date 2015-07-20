package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.changyu.foryou.model.FoodSpecial;
import com.changyu.foryou.model.FoodSpecialKey;

public interface FoodSpecialMapper {
    int deleteByPrimaryKey(FoodSpecialKey key);

    int insert(FoodSpecial record);

    int insertSelective(FoodSpecial record);

    FoodSpecial selectByPrimaryKey(FoodSpecialKey key);

    int updateByPrimaryKeySelective(FoodSpecial record);

    int updateByPrimaryKey(FoodSpecial record);

	List<FoodSpecial> getFoodSpecialByFoodId(Map<String, Object> paramMap);

	String getSpecialName(Map<String, Object> paramMap);

	int addFoodSpecial(FoodSpecial foodSpecial);

	int getSpecialCount(Map<String, Object> paramMap);

	Integer getSpecialMax(Map<String, Object> paramMap);

	int changeFoodCount(Map<String, Object> paramMap);

	Integer getFoodSpecialCount(Map<String, Object> paramMap);
}