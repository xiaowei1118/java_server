package com.changyu.foryou.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.changyu.foryou.model.Food;
import com.changyu.foryou.model.ShortFood;
import com.changyu.foryou.model.VeryShortFood;

public interface FoodMapper {
    int deleteByPrimaryKey(Map<String, Object> paramMap);

    int insert(Food record);

    int insertSelective(Food record);

    Food selectByPrimaryKey(Map<String, Object> paramMap);

    int updateByPrimaryKeySelective(Food record);

    int updateByPrimaryKey(Food record);

	List<Food> getAllFoods(Map<String, Object> paramMap);        //web管理端获取所有的食品

	List<ShortFood> getFoodListDiscount(Map<String, Object> paramMap);   //获取打折商品
 
	List<ShortFood> getFoodListFresh(Map<String, Object> paramMap);      //获取新品食品

	List<ShortFood> getFoodListWelcome(Map<String, Object> paramMap);   //获取受欢迎商品


	List<ShortFood> selectFoods(Map<String, Object> paramMap);

	List<ShortFood> selectFoodsByTwoTags(Map<String, Object> paramMap);

	int changeFoodNumber(Map<String, Object> paramMap);   //更新销量和减少库存

	List<VeryShortFood> selectHomeFood(Map<String, Object> paramMap);

}