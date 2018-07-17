package com.ccc.chestersprinkles.service;

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
		return pirateDao.getTopFivePirates();
	}
	
	public List<Pirate> getTopPlankWalkers(){
		return pirateDao.getTopPlankWalkers();
	}
	
	public Pirate getPirateBySlackId(String slackId){
		return pirateDao.getPirateBySlackId(slackId);
	}
	
	public List<Pirate> getPiratesByShipId(int shipId) {
		return pirateDao.getPiratesByShipId(shipId);
	}
	
	public Pirate getPirateByName(String firstName, String lastName) {
		return pirateDao.getPirateByName(firstName, lastName);
	}
	
	public void updatePoints(int points, int totalPoints, int userId) {
		pirateDao.updatePoints(points, totalPoints, userId);
	}
	
	public void updateWalkThePlank(int plankNum, int pirateId) {
		pirateDao.updateWalkThePlank(plankNum, pirateId);
	}
	
	public void updateDoubloons(int doubloons, int pirateId) {
		pirateDao.updateDoubloons(doubloons, pirateId);
	}
	
	public void updateDoubloonsActivation(Pirate pirate) {
		pirateDao.updateDoubloonsActivation(pirate);
	}
	
	public void updateTopFivePirate(Pirate pirate) {
		pirateDao.updateTopFivePirate(pirate);
	}
	
	public void updateNameChange(int pirateId, String name, int newDoubloonCount) {
		pirateDao.updateNameChange(pirateId, name, newDoubloonCount);
	}
	
	public void updatePollyCommand(int pirateId, int newDoubloonCount) {
		pirateDao.updatePollyCommand(pirateId, newDoubloonCount);
	}
	
	public void updateChannelId(int pirateId, String channel) {
		pirateDao.updateChannelId(pirateId, channel);
	}
	
	public void updateZeroPoints() {
		pirateDao.updateZeroPoints();
	}
	
	public void addNewPirate(int shipId, String pirateName) {
		pirateDao.addNewPirate(shipId, pirateName);
	}
}
