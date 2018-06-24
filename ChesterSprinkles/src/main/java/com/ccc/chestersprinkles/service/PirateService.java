package com.ccc.chestersprinkles.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.PirateDao;
import com.ccc.chestersprinkles.model.Pirate;

@Transactional
@Service
public class PirateService {
	@Autowired
	private PirateDao pirateDao;
	
	public List<Pirate> getTopFivePirates(){
		try {
			return pirateDao.getTopFivePirates();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Pirate getPirateByUser(int userId){
		try {
			return pirateDao.getPirateByUser(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public Pirate getPirateByName(String firstName, String lastName) {
		return pirateDao.getPirateByName(firstName, lastName);
	}
	
	public void updatePoints(int points, int pirateId) {
		pirateDao.updatePoints(points, pirateId);
	}
	
	public void updateWalkThePlank(int plankNum, int pirateId) {
		pirateDao.updateWalkThePlank(plankNum, pirateId);
	}
}
