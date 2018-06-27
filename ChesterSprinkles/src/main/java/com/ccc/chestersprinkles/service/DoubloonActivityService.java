package com.ccc.chestersprinkles.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.DoubloonActivityDao;

@Transactional
@Service
public class DoubloonActivityService {
	@Autowired
	DoubloonActivityDao doubloonActivityDao;
	
	public void updateShoreleave(String shoreleaveDate, int pirateId) {
		doubloonActivityDao.updateShoreleave(shoreleaveDate, pirateId);
	}
	
	public void updateExplore(String exploreDate, int pirateId) {
		doubloonActivityDao.updateExplore(exploreDate, pirateId);
	}
	
	public void updateBattle(String battleDate, int pirateId) {
		doubloonActivityDao.updateBattle(battleDate, pirateId);
	}
	
	public void updateSetSail(String setSailDate, int pirateId) {
		doubloonActivityDao.updateSetSail(setSailDate, pirateId);
	}
	
	public void updateCommandStartEndDate(String startDate, String endDate, int pirateId) {
		doubloonActivityDao.updateCommandStartEndDate(startDate, endDate, pirateId);
	}
}
