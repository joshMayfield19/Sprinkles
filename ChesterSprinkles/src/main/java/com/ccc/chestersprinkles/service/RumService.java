package com.ccc.chestersprinkles.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.RumDao;
import com.ccc.chestersprinkles.model.Rum;

@Transactional
@Service
public class RumService {
	@Autowired
	private RumDao rumDao;
	
	public void addNewEvent(Rum rum) {
		rumDao.addNewRum(rum);
	}
	
	public List<Rum> getMyRumsGiven(int pirateId) {
		return rumDao.getRumsGiven(pirateId);
	}
	
	public List<Rum> getMyRumsGotten(int pirateId) {
		return rumDao.getRumsGotten(pirateId);
	}
}
