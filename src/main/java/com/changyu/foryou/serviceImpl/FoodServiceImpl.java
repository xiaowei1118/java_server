package com.changyu.foryou.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changyu.foryou.mapper.FoodCategoryMapper;
import com.changyu.foryou.mapper.FoodCommentMapper;
import com.changyu.foryou.mapper.FoodMapper;
import com.changyu.foryou.mapper.FoodSpecialMapper;
import com.changyu.foryou.model.Food;
import com.changyu.foryou.model.FoodCategory;
import com.changyu.foryou.model.FoodComment;
import com.changyu.foryou.model.FoodSpecial;
import com.changyu.foryou.model.FoodSpecialKey;
import com.changyu.foryou.model.FoodWithSales;
import com.changyu.foryou.model.HomeCategory;
import com.changyu.foryou.model.ShortFood;
import com.changyu.foryou.model.ShortFoodWithIm;
import com.changyu.foryou.model.VeryShortFood;
import com.changyu.foryou.service.FoodService;

/**
 * 食品管理服务类（包括食品分类）
 * @author 殿下
 *2014/12/13
 */
@Service("/foodService")
public class FoodServiceImpl implements FoodService{
    private FoodMapper foodMapper;
    private FoodCategoryMapper foodCategoryMapper;
    private FoodSpecialMapper foodSpecialMapper;
    private FoodCommentMapper foodCommentMapper;
    
    @Autowired
	public void setFoodCommentMapper(FoodCommentMapper foodCommentMapper) {
		this.foodCommentMapper = foodCommentMapper;
	}

    @Autowired
	public void setFoodSpecialMapper(FoodSpecialMapper foodSpecialMapper) {
		this.foodSpecialMapper = foodSpecialMapper;
	}


    @Autowired
	public void setFoodMapper(FoodMapper foodMapper) {
		this.foodMapper = foodMapper;
	}

	@Autowired
	public void setFoodCategoryMapper(FoodCategoryMapper foodCategoryMapper) {
		this.foodCategoryMapper = foodCategoryMapper;
	}

	public int deleteCategoryByPrimaryKey(Map<String,Object> paramMap) {
		return foodCategoryMapper.deleteByPrimaryKey(paramMap);
	}

	public int insertCategorySelective(FoodCategory record) {
		return foodCategoryMapper.insertSelective(record);
	}

	public FoodCategory selectCategoryByPrimaryKey(Map<String,Object> paramMap) {
		return foodCategoryMapper.selectByPrimaryKey(paramMap);
	}

	public int updateCategoryByPrimaryKeySelective(FoodCategory record) {
		return foodCategoryMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteFoodByPrimaryKey(Map<String,Object> paramMap) {
		return foodMapper.deleteByPrimaryKey(paramMap);
	}

	public int insertFoodSelective(Food record) {
		return foodMapper.insertSelective(record);
	}

	public Food selectFoodByPrimaryKey(Map<String,Object> paramMap) {
		return foodMapper.selectByPrimaryKey(paramMap);
	}

	public int updateFoodByPrimaryKeySelective(Food record) {
		return foodMapper.updateByPrimaryKeySelective(record);
	}

	//获取食品一级分类
	public List<FoodCategory> getFirstCategory(Map<String,Object> paramMap) {
		return foodCategoryMapper.getFirstCategory(paramMap);
	}


	//根据食品分类，和食品标签查询食品
	public List<ShortFoodWithIm> selectFoods(Map<String,Object> paramMap) {

		return foodMapper.selectFoods(paramMap);
	}

	public List<FoodSpecial> getFoodSpecial(Map<String,Object> paramMap) {
		return foodSpecialMapper.getFoodSpecialByFoodId(paramMap);
	}

	//通过两个标签模糊查询（两个标签以空格隔开）
	public List<ShortFoodWithIm> selectFoodsByTwoTags(Map<String,Object> paramMap) {
		
		return foodMapper.selectFoodsByTwoTags(paramMap);
		
	}

	public int insertSelective(FoodComment record) {
		return 0;
	}

	public Long getCommentCountsById(Map<String,Object> paramMap) {
		return foodCommentMapper.getCommentCountsById(paramMap);
	}

	public List<FoodComment> getCommentInfoById(Map<String,Object> paramMap) {
		return foodCommentMapper.getCommentsById(paramMap);
	}

	public List<Food> getAllFoods(Map<String,Object> paramMap) {
		return foodMapper.getAllFoods(paramMap);
	}

	public Integer getFoodSpecialCount(Map<String,Object> paramMap) {
		return foodSpecialMapper.getFoodSpecialCount(paramMap);
	}

	public String getSpecialName(Map<String,Object> paramMap) {		
		return foodSpecialMapper.getSpecialName(paramMap);
	}

	public List<FoodCategory> getAllFoodSecondCategories() {
		return foodCategoryMapper.getAllFoodSecondCategories();
	}

	public List<FoodCategory> getAllFoodFirstCategories() {
		return foodCategoryMapper.getAllFoodFirstCategories();
	}

	public int addFoodSpecial(FoodSpecial foodSpecial) {		
		return foodSpecialMapper.addFoodSpecial(foodSpecial);
	}

	public int getSpecialCount(Map<String,Object> paramMap) {
		return foodSpecialMapper.getSpecialCount(paramMap);
	}

	//获取special_id的最大值
	public Integer getSpecialMax(Map<String,Object> paramMap) {		
		return foodSpecialMapper.getSpecialMax(paramMap);
	}

	public List<FoodComment> getAllComments(Map<String,Object> paramMap) {
		return foodCommentMapper.getAllComments(paramMap);
	}

	public Integer insertFoodComment(FoodComment foodComment) {
		return foodCommentMapper.insertSelective(foodComment);
	}

	public List<ShortFood> getFoodListFresh(Map<String,Object> paramMap) {
		return foodMapper.getFoodListFresh(paramMap);
	}

	public List<ShortFood> getFoodListWelcome(Map<String,Object> paramMap) {
		return foodMapper.getFoodListWelcome(paramMap);
	}

	public List<ShortFood> getFoodListDiscount(Map<String,Object> paramMap) {
		return foodMapper.getFoodListDiscount(paramMap);
	}

	public Integer changeFoodCount(Map<String,Object> paramMap) {
		int flag;
		flag=foodMapper.changeFoodNumber(paramMap);
		return flag;		
	}

	public Integer deleteFoodCommentById(Map<String,Object> paramMap) {
		return foodCommentMapper.deleteFoodComment(paramMap);
	}

	public Float getAvageGrade(Map<String,Object> paramMap) {
		return foodCommentMapper.getAvageGrade(paramMap);
	}

	public void deleteFoodSpecial(FoodSpecial foodSpecial) {
		FoodSpecialKey foodSpecialKey=foodSpecial;
		
		 foodSpecialMapper.deleteByPrimaryKey(foodSpecialKey); //删除食品的某一口味
	}

	public void updateFoodSpecial(FoodSpecial foodSpecial) {
		foodSpecialMapper.updateByPrimaryKey(foodSpecial);
	}

	public Integer getFoodCommentCount(Map<String, Object> paramMap) {
		return foodCommentMapper.getFoodCommentCount(paramMap);
	}
	
	public List<VeryShortFood> selectHomeFood(Map<String, Object> paramMap)
	{
		return foodMapper.selectHomeFood(paramMap);
	}
	
	public List<FoodCategory> getAllFoodCategories(Map<String, Object> paramMap)
	{
		return foodCategoryMapper.getAllFoodCategories(paramMap);
	}

	@Override
	public List<HomeCategory> getHomeCategoryInfo(Map<String, Object> paramMap) {
		return foodCategoryMapper.getHomeCategoryInfo(paramMap);
	}

	@Override
	public Integer getAllCategoryCount() {
		
		return foodCategoryMapper.getAllCategoryCount();
	}

	@Override
	public Integer uploadHomeFoodByFoodId(Map<String, Object> paramMap) {
		return foodMapper.uploadHomeFoodByFoodId(paramMap);
	}

	@Override
	public Integer updateInfoByFoodId(Map<String, Object> paramMap) {
		return foodMapper.updateInfoByFoodId(paramMap);
	}

	@Override
	public Integer cancelRecommend(Map<String, Object> paramMap) {
		return foodMapper.cancelRecommend(paramMap);
	}

	@Override
	public Integer calCommentCount(Map<String, Object> paramMap) {
		return foodCommentMapper.calCommentCount(paramMap);
	}

	@Override
	public Integer addCategoryWhenAddCampus(Map<String, Object> paramMap) {
		return foodCategoryMapper.addCategoryWhenAddCampus(paramMap);
	}

	@Override
	public List<FoodWithSales> getTopFive(Map<String, Object> paramMap) {
		return foodMapper.getTopFive(paramMap);
	}

	@Override
	public Integer addFoodCountById(Map<String, Object> paramMap) {
		return foodMapper.addFoodCountById(paramMap);
	}

	@Override
	public Integer addFoodCount(Map<String, Object> paramMap) {
		return foodMapper.addFoodCount(paramMap);
	}

	@Override
	public String getFoodHomeImage(Map<String, Object> paramMap) {
		return foodMapper.getFoodHomeImage(paramMap);
	}

	@Override
	public String getDetailImg(Map<String, Object> paramMap) {
		return foodMapper.getDetailImg(paramMap);
	}
	
}

