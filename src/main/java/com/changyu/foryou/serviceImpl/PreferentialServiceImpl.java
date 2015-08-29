package com.changyu.foryou.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changyu.foryou.mapper.PreferentialMapper;
import com.changyu.foryou.model.Preferential;
import com.changyu.foryou.service.PreferentialService;

@Service("preferentialService")
public class PreferentialServiceImpl implements PreferentialService {
	@Autowired
	private PreferentialMapper preferentialMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer preferentialId) {
		// TODO Auto-generated method stub
		return preferentialMapper.deleteByPrimaryKey(preferentialId);
	}

	@Override
	public int insert(Preferential record) {
		// TODO Auto-generated method stub
		return preferentialMapper.insert(record);
	}

	@Override
	public int insertSelective(Preferential record) {
		// TODO Auto-generated method stub
		return preferentialMapper.insert(record);
	}

	@Override
	public Preferential selectByPrimaryKey(Integer preferentialId) {
		// TODO Auto-generated method stub
		return preferentialMapper.selectByPrimaryKey(preferentialId);
	}

	@Override
	public int updateByPrimaryKeySelective(Preferential record) {
		// TODO Auto-generated method stub
		return preferentialMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Preferential record) {
		// TODO Auto-generated method stub
		return preferentialMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Preferential> getPreferential(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return preferentialMapper.getPreferential(paramMap);
	}

}
