package com.changyu.foryou.service;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Food;
import com.changyu.foryou.model.FoodCategory;
import com.changyu.foryou.model.FoodComment;
import com.changyu.foryou.model.FoodSpecial;
import com.changyu.foryou.model.FoodWithSales;
import com.changyu.foryou.model.HomeCategory;
import com.changyu.foryou.model.ShortFood;
import com.changyu.foryou.model.ShortFoodWithIm;
import com.changyu.foryou.model.VeryShortFood;

public interface FoodService {
	int deleteCategoryByPrimaryKey(Map<String, Object> paramMap);

	int insertCategorySelective(FoodCategory record);

	FoodCategory selectCategoryByPrimaryKey(Map<String, Object> paramMap);

	int updateCategoryByPrimaryKeySelective(FoodCategory record);

	int deleteFoodByPrimaryKey(Map<String, Object> paramMap);

	int insertFoodSelective(Food record);

	Food selectFoodByPrimaryKey(Map<String, Object> paramMap);

	int updateFoodByPrimaryKeySelective(Food record);

	List<FoodCategory>getFirstCategory(Map<String, Object> paramMap);

	List<FoodSpecial> getFoodSpecial(Map<String, Object> paramMap);

	int insertSelective(FoodComment record);

	Long getCommentCountsById(Map<String, Object> paramMap);

	List<Food> getAllFoods(Map<String, Object> paramMap);

	Integer getFoodSpecialCount(Map<String, Object> paramMap);

	String getSpecialName(Map<String, Object> paramMap);

	int getSpecialCount(Map<String, Object> paramMap);

	int addFoodSpecial(FoodSpecial foodSpecial);

	Integer getSpecialMax(Map<String, Object> paramMap);

	Integer getFoodCommentCount(Map<String, Object> paramMap);

	Integer insertFoodComment(FoodComment foodComment);

	List<ShortFood> getFoodListDiscount(Map<String, Object> paramMap);

	List<ShortFood> getFoodListFresh(Map<String, Object> paramMap);

	List<ShortFood> getFoodListWelcome(Map<String, Object> paramMap);

	Integer changeFoodCount(Map<String, Object> paramMap);

	Float getAvageGrade(Map<String, Object> paramMap);

	void deleteFoodSpecial(FoodSpecial foodSpecial);

	void updateFoodSpecial(FoodSpecial foodSpecial);

	List<ShortFoodWithIm> selectFoods(Map<String, Object> paramMap);

	List<ShortFoodWithIm> selectFoodsByTwoTags(Map<String, Object> paramMap);

	List<FoodComment> getCommentInfoById(Map<String, Object> paramMap);

	Object getAllComments(Map<String, Object> paramMap);

	Integer deleteFoodCommentById(Map<String, Object> paramMap);

	List<VeryShortFood> selectHomeFood(Map<String, Object> paramMap);

	List<FoodCategory> getAllFoodCategories(Map<String,Object> paramMap);

	List<HomeCategory> getHomeCategoryInfo(Map<String, Object> paramMap);

	//void deleteFoodByParentCategory(String categoryId);
	
	Integer getAllCategoryCount();
	
	Integer uploadHomeFoodByFoodId(Map<String, Object> paramMap);
	
	Integer updateInfoByFoodId(Map<String, Object> paramMap);
	
	Integer cancelRecommend(Map<String, Object> paramMap);

	Integer calCommentCount(Map<String, Object> paramMap);
	
	public Integer addCategoryWhenAddCampus(Map<String, Object> paramMap);
	
	List<FoodWithSales> getTopFive(Map<String, Object> paramMap);
	
	Integer addFoodCountById(Map<String, Object> paramMap);//添加库存
	
	Integer addFoodCount(Map<String, Object> paramMap);//添加库存

	String getFoodHomeImage(Map<String, Object> paramMap);  //获取原来的首页图片

	String getDetailImg(Map<String, Object> paramMap);   //获取详情图片
}
