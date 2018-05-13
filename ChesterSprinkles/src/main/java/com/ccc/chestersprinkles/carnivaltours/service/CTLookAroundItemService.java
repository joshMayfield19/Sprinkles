package com.ccc.chestersprinkles.carnivaltours.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.carnivaltours.dao.CTLookAroundItemDao;
import com.ccc.chestersprinkles.carnivaltours.model.CTLookAroundItem;

@Transactional
@Service
public class CTLookAroundItemService {
	@Autowired
	private CTLookAroundItemDao ctLookAroundItemDao;
	
	public CTLookAroundItem getCtLookAroundItem(String lookAroundItemId){
		return ctLookAroundItemDao.getCtLookAroundItem(lookAroundItemId);
	}
	
	public List<CTLookAroundItem> getCtLookAroundItemByLocationId(String locationId) {
		return ctLookAroundItemDao.getCtLookAroundItemByLocationId(locationId);
	}
	
	public List<CTLookAroundItem> getAllCtItems() {
		return ctLookAroundItemDao.getAllCtItems();
	}
}
