package com.ccc.chestersprinkles.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.BottleEventDao;
import com.ccc.chestersprinkles.model.BottleEvent;

@Transactional
@Service
public class BottleEventService {
	@Autowired
	private BottleEventDao bottleEventDao;
	
	public BottleEvent getBottleEventByPirateId(int pirateId){
		return bottleEventDao.getBottleEventByPirateId(pirateId);
	}
	
	public void updateDoubloons(int doubloons, int pirateId){
		bottleEventDao.updateDoubloons(doubloons, pirateId);
	}
	
	public void updatePoints(int points, int pirateId){
		bottleEventDao.updatePoints(points, pirateId);
	}
	
	public void updateDates(String startDate, String endDate, int collected, int pirateId){
		bottleEventDao.updateDates(startDate, endDate, collected, pirateId);
	}
	
	public void addNewBottleEvent(BottleEvent bottleEvent){
		bottleEventDao.addNewBottleEvent(bottleEvent);
	}
}
