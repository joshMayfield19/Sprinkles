package com.ccc.chestersprinkles.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.DoubloonItemHistoryDao;

@Transactional
@Service
public class DoubloonItemHistoryService {
	@Autowired
	private DoubloonItemHistoryDao doubloonItemHistoryDao;
	
	public void addNewItemPurchase(int pirateId, String command, String notes, int price) {
		doubloonItemHistoryDao.addNewEvent(pirateId, command, notes, price);
	}
}
