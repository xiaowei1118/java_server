package com.changyu.foryou.service;

import java.util.List;
import java.util.Map;

import com.changyu.foryou.model.DeliverCom;
import com.changyu.foryou.model.PackageOrder;

public interface PackageService {

	List<DeliverCom> getDeliverCom(Map<String, Object> paramMap);

	int insert(PackageOrder packageOrder);

	int setPackageAdmin(Map<String, Object> paramMap);
    
}
