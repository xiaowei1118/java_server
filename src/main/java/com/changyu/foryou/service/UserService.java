package com.changyu.foryou.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Feedback;
import com.changyu.foryou.model.Users;

public interface UserService {
	Users selectByUsername(String id);//根据用户手机号获取用户信息

	void addUsers(Users users);

	int updatePassword(String phone, String newPassword);

	int updateUserInfo(Users users);

	void updateLastLoginTime(Date date,String phone);

	List<Users> getAllUser(Integer limit, Integer offset, String sort, String order, String search);
	
	int addFeedbackSuggestion(Feedback record);

	Integer getUserCount(String search);

	Integer setUserAdmin(String phone, Integer campusId);

	Integer setUserCommon(String phone, Integer campusId);

	Integer setUserSuperAdmin(String phone, Integer campusId);

	int updateImageUrl(String imageUrl, String phone);

	String getImageUrl(String phone);

	List<Users> getDeliverAdmin(Map<String, Object> paramMap);

	int setUserToken(String phoneId, String token);

	List<Feedback> getFeedbacks(Map<String, Object> paramMap);

	String getUserToken(String togetherId);

	int clearOldToken(String token);

	String getUserPhone(String togetherId);

	List<String> getAllSuperAdminPhone(Map<String, Object> paramterMap);

	String getUserTokenByPhone(Map<String, Object> paramterMap);

	Integer getCountsByDevice(Map<String, Object> paramMap);

	List<Users> selectByPhoneAndPassword(Map<String, Object> paramMap);
	
	Users checkLogin(String phone);

	List<String> getUserByType(Map<String, Object> paramMap);
}