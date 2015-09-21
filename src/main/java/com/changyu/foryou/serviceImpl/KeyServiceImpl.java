package com.changyu.foryou.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changyu.foryou.mapper.AppKeyMapper;
import com.changyu.foryou.service.KeyService;

@Service("keyService")
public class KeyServiceImpl implements KeyService{
    private AppKeyMapper keyMapper;

    @Autowired
    public void setKeyMapper(AppKeyMapper keyMapper) {
		this.keyMapper = keyMapper;
	}
    
	@Override
	public String SelectKey(Map<String, Object> paramMap) {
		return keyMapper.SelectKey(paramMap);
	}
   
}
