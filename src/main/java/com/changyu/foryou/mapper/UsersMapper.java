package com.changyu.foryou.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.changyu.foryou.model.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(String phone);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String phone);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    
    //**新增方法
	int updatePassword(@Param(value="phone")String phone, @Param(value="newPassword")String newPassword);

	void updateLastLoginTime(@Param(value="date")Date date, @Param(value="phone")String phone);

	List<Users> getAllUser(@Param(value="limit")Integer limit, @Param(value="offset")Integer offset, @Param(value="sort")String sort, @Param(value="order")String order, @Param(value="search")String search);

	Integer getUserCount(@Param(value="search")String search);

	Integer setUserAdmin(@Param(value="phone")String phone, @Param(value="campusId")Integer campusId);

	Integer setUserCommon(@Param(value="phone")String phone,@Param(value="campusId")Integer campusId);

	Integer setUserSuperAdmin(@Param(value="phone")String phone, @Param(value="campusId")Integer campusId);

	int updateUserImage(@Param(value="imageUrl")String imageUrl, @Param(value="phone")String phone);

	String getImageUrl(@Param(value="phone")String phone);

	List<Users> getDeliverAdmin(Map<String, Object> paramMap);

	int setUserToken(@Param(value="phone")String phoneId, @Param(value="token")String token);

	String getUserToken(@Param(value="togetherId")String togetherId);

	int clearOldToken(@Param(value="token")String token);

	String getUserPhone(@Param(value="togetherId")String togetherId);

	List<String> getAllSuperAdminPhone(Map<String, Object> paramterMap);      //获取所有的超级管理员

	String getUserTokenByPhone(Map<String, Object> paramterMap);      //获取用户token

	Integer getCountsByDevice(Map<String, Object> paramMap);   //获取不同设备用户的个数

	List<Users> selectByPhoneAndPassword(Map<String, Object> paramMap);
	
	Users checkLogin(String phone);

	List<String> getUserByType(Map<String, Object> paramMap);
}