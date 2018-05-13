package com.ccc.chestersprinkles.carnivaltours.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.carnivaltours.dao.CTLocationDao;
import com.ccc.chestersprinkles.carnivaltours.model.CTLocation;

@Transactional
@Service
public class CTLocationService {
	@Autowired
	private CTLocationDao ctLocationDao;
	
	public CTLocation getCtLocation(String locationId){
		return ctLocationDao.getCtLocation(locationId);
	}
}
