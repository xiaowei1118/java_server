package com.changyu.foryou.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changyu.foryou.mapper.FeedbackMapper;
import com.changyu.foryou.mapper.UsersMapper;
import com.changyu.foryou.model.Feedback;
import com.changyu.foryou.model.Users;
import com.changyu.foryou.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {
	private UsersMapper usersMapper;         //操作用户信息
	private FeedbackMapper feedbackMapper;   //操作用户反馈表

	@Autowired
	public void setFeedbackMapper(FeedbackMapper feedbackMapper) {
		this.feedbackMapper = feedbackMapper;
	}

	@Autowired
	public void setUsersMapper(UsersMapper usersMapper) {
		this.usersMapper = usersMapper;
	}

	public Users selectByUsername(String phone) {
		return usersMapper.selectByPrimaryKey(phone);
	}


	public void addUsers(Users users) {
		usersMapper.insertSelective(users);
	}

	public int updatePassword(String phone, String newPassword) {
		return usersMapper.updatePassword(phone, newPassword);
	}

	public int updateUserInfo(Users users) {
		return usersMapper.updateByPrimaryKeySelective(users);
	}


    @Transactional(rollbackFor=Exception.class)
	public void updateLastLoginTime(Date date,String phone) {
		usersMapper.updateLastLoginTime(date,phone);
	}


	public int addFeedbackSuggestion(Feedback record) {

		return feedbackMapper.insert(record);
	}

	public List<Users> getAllUser(Integer limit, Integer offset, String sort,
			String order,String search) {
		return usersMapper.getAllUser(limit,offset,sort,order,search);
	}

	public Integer getUserCount(String search) {
		return usersMapper.getUserCount(search);
	}

	public Integer setUserAdmin(String phone,Integer campusId) {
		return usersMapper.setUserAdmin(phone,campusId);
	}

	public Integer setUserCommon(String phone,Integer campusId) {
		return usersMapper.setUserCommon(phone,campusId);
	}

	public Integer setUserSuperAdmin(String phone,Integer campusId) {
		return usersMapper.setUserSuperAdmin(phone,campusId);
	}

	public int updateImageUrl(String imageUrl, String phone) {
		return usersMapper.updateUserImage(imageUrl,phone);
	}

	public String getImageUrl(String phone) {
		return usersMapper.getImageUrl(phone);
	}

	public List<Users> getDeliverAdmin(Map<String, Object> paramMap) {
		return usersMapper.getDeliverAdmin(paramMap);
	}

	public int setUserToken(String phoneId, String token) {
		return usersMapper.setUserToken(phoneId,token);
	}

	public List<Feedback> getFeedbacks(Map<String, Object> paramMap) {
		return feedbackMapper.getFeedbacks(paramMap);
	}

	public String getUserToken(String togetherId) {
		return usersMapper.getUserToken(togetherId);
	}

	public int clearOldToken(String token) {
		return usersMapper.clearOldToken(token);
	}

	public String getUserPhone(String togetherId) {	
		return usersMapper.getUserPhone(togetherId);
	}

	@Override
	public List<String> getAllSuperAdminPhone(Map<String, Object> paramterMap) {
		return usersMapper.getAllSuperAdminPhone(paramterMap);
	}

	@Override
	public String getUserTokenByPhone(Map<String, Object> paramterMap) {
		return usersMapper.getUserTokenByPhone(paramterMap);
	}

	@Override
	public Integer getCountsByDevice(Map<String, Object> paramMap) {
		return usersMapper.getCountsByDevice(paramMap);
	}
	
	public List<Users> selectByPhoneAndPassword(Map<String, Object> paramMap)
	{
		return usersMapper.selectByPhoneAndPassword(paramMap);
	}

	@Override
	public Users checkLogin(String phone) {
		return usersMapper.checkLogin(phone);
	}

	@Override
	public List<String> getUserByType(Map<String, Object> paramMap) {
		return usersMapper.getUserByType(paramMap);
	}

}
