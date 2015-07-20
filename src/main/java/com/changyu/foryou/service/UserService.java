package com.changyu.foryou.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Feedback;
import com.changyu.foryou.model.Users;


public interface UserService {
	Users selectByUsername(String id);

	void addUsers(Users users);

	int updatePassword(String phone, String newPassword);

	int updateUserInfo(Users users);

	void updateLastLoginTime(Date date,String phone);

	List<Users> getAllUser(Integer limit, Integer offset, String sort, String order, String search);
	
	int addFeedbackSuggestion(Feedback record);

	Integer getUserCount(String search);

	Integer setUserAdmin(String phone);

	Integer setUserCommon(String phone);

	Integer setUserSuperAdmin(String phone);

	int updateImageUrl(String imageUrl, String phone);

	String getImageUrl(String phone);

	List<Users> getDeliverAdmin();

	int setUserToken(String phoneId, String token);

	List<Feedback> getFeedbacks();

	String getUserToken(String togetherId);

	int clearOldToken(String token);

	String getUserPhone(String togetherId);

	List<String> getAllSuperAdminPhone(Map<String, Object> paramterMap);

	String getUserTokenByPhone(Map<String, Object> paramterMap);

	Integer getCountsByDevice(Map<String, Object> paramMap);
}