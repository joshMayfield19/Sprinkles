package com.ccc.chestersprinkles.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.PirateShipDao;
import com.ccc.chestersprinkles.model.PirateShip;

@Transactional
@Service
public class PirateShipService {
	@Autowired
	private PirateShipDao pirateShipDao;
	
	public List<PirateShip> getTopShips(){
		try {
			return pirateShipDao.getTopShips();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public PirateShip getShipById(int shipId){
		try {
			return pirateShipDao.getShipById(shipId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public void updatePoints(int points, int shipId) {
		pirateShipDao.updatePoints(points, shipId);
	}
	
	public void updateCaptainByShipId(int shipId, int captain) {
		pirateShipDao.updateCaptainByShipId(shipId, captain);
	}
	
	public void updateShipCrew(PirateShip pirateShip) {
		pirateShipDao.updateShipCrew(pirateShip);
	}
	
	public PirateShip getShipToAddCrew() {
		return pirateShipDao.getShipToAddCrew();
	}
}
