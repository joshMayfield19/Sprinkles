package com.ccc.chestersprinkles.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.PiratePointsHistoryDao;
import com.ccc.chestersprinkles.model.PiratePointsHistory;

@Transactional
@Service
public class PiratePointsHistoryService {
	@Autowired
	private PiratePointsHistoryDao piratePointsHistoryDao;
	
	public void addNewEvent(PiratePointsHistory piratePointsHistory) {
		piratePointsHistoryDao.addNewEvent(piratePointsHistory);
	}
	
	public List<PiratePointsHistory> getHistoryByPirateId(int pirateId) {
		return piratePointsHistoryDao.getHistoryByPirateId(pirateId);
	}
}
