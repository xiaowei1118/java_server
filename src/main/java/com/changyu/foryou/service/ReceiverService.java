package com.changyu.foryou.service;


import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.Receiver;

public interface ReceiverService {
	int deleteByPrimaryKey(String phoneId,String rank);

	int insertSelective(Receiver record);

	Receiver selectByPrimaryKey(String phoneId,String rank);

	int updateByPrimaryKeySelective(Receiver record);

	int setDefaultAddress(String phone,String rank);

	int getReceiverCounts(String phoneId);

	List<Receiver> selectByPhoneId(String phoneId);

	int insert(Receiver receiver);

	int setReceiverTag(String phoneId);

	Receiver getReceiver(Map<String, Object> paramMap);
}
