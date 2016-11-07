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
    
    public FoodCommentMapper getFoodCommentMapper() {
		return foodCommentMapper;
	}

    @Autowired
	public void setFoodCommentMapper(FoodCommentMapper foodCommentMapper) {
		this.foodCommentMapper = foodCommentMapper;
	}

	public FoodSpecialMapper getFoodSpecialMapper() {
		return foodSpecialMapper;
	}

    @Autowired
	public void setFoodSpecialMapper(FoodSpecialMapper foodSpecialMapper) {
		this.foodSpecialMapper = foodSpecialMapper;
	}

	public FoodMapper getFoodMapper() {
		return foodMapper;
	}

    @Autowired
	public void setFoodMapper(FoodMapper foodMapper) {
		this.foodMapper = foodMapper;
	}

	public FoodCategoryMapper getFoodCategoryMapper() {
		return foodCategoryMapper;
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

//	//获取食品二级分类
//	public List<FoodCategory> getSecondCategories(Integer id) {		
//		return foodCategoryMapper.getSecondCategoryes(id);
//	}

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
		int flag=0;
		//flag= foodSpecialMapper.changeFoodCount(paramMap);
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

	public List<HomeCategory> getHomeCategoryInfo(Map<String, Object> paramMap) {
		return foodCategoryMapper.getHomeCategoryInfo(paramMap);
	}

	public Integer getAllCategoryCount() {
		
		return foodCategoryMapper.getAllCategoryCount();
	}

	public Integer uploadHomeFoodByFoodId(Map<String, Object> paramMap) {
		return foodMapper.uploadHomeFoodByFoodId(paramMap);
	}

	public Integer updateInfoByFoodId(Map<String, Object> paramMap) {
		return foodMapper.updateInfoByFoodId(paramMap);
	}

	public Integer cancelRecommend(Map<String, Object> paramMap) {
		return foodMapper.cancelRecommend(paramMap);
	}

	public Integer calCommentCount(Map<String, Object> paramMap) {
		return foodCommentMapper.calCommentCount(paramMap);
	}

	public Integer addCategoryWhenAddCampus(Map<String, Object> paramMap) {
		return foodCategoryMapper.addCategoryWhenAddCampus(paramMap);
	}

	public List<FoodWithSales> getTopFive(Map<String, Object> paramMap) {
		return foodMapper.getTopFive(paramMap);
	}

	public Integer addFoodCountById(Map<String, Object> paramMap) {
		return foodMapper.addFoodCountById(paramMap);
	}

	public Integer addFoodCount(Map<String, Object> paramMap) {
		return foodMapper.addFoodCount(paramMap);
	}

	public String getFoodHomeImage(Map<String, Object> paramMap) {
		return foodMapper.getFoodHomeImage(paramMap);
	}

	public String getDetailImg(Map<String, Object> paramMap) {
		return foodMapper.getDetailImg(paramMap);
	}
	
}

