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
	
	public List<Pirate> getTopFivePiratesToDeactivate(){
		return pirateDao.getTopFivePiratesToDeactivate();
	}
	
	public List<Pirate> getTopPlankWalkers(){
		return pirateDao.getTopPlankWalkers();
	}
	
	public List<Pirate> getBottlePirates(){
		return pirateDao.getBottlePirates();
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
	
	public void updateBottleCommand(int pirateId, int newDoubloonCount) {
		pirateDao.updateBottleCommand(pirateId, newDoubloonCount);
	}
	
	public void updateMutinyCommand(int pirateId, int newDoubloonCount) {
		pirateDao.updateMutinyCommand(pirateId, newDoubloonCount);
	}
	
	public void updatePlankSniperCommand(int pirateId, int newDoubloonCount) {
		pirateDao.updatePlankSniperCommand(pirateId, newDoubloonCount);
	}
	
	public void updateLootDateCommand(int pirateId, int newDoubloonCount, String lootDate) {
		pirateDao.updateLootDateCommand(pirateId, newDoubloonCount, lootDate);
	}
	
	public void updateLotDateCommand(int pirateId, int newDoubloonCount, String lotDate) {
		pirateDao.updateLootDateCommand(pirateId, newDoubloonCount, lotDate);
	}
	
	public void updateUsePlankSniperCommand(int pirateId) {
		pirateDao.updateUsePlankSniperCommand(pirateId);
	}
	
	public void updateUseMutinyCommand(int pirateId) {
		pirateDao.updateUseMutinyCommand(pirateId);
	}
	
	public void updateUseRumCommand(int pirateId) {
		pirateDao.updateUseRumCommand(pirateId);
	}
	
	public void updateGetRumCommand(int pirateId) {
		pirateDao.updateGetRumCommand(pirateId);
	}
}
