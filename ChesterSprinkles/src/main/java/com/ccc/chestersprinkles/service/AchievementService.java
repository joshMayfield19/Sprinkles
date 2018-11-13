package com.ccc.chestersprinkles.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.AchievementDao;
import com.ccc.chestersprinkles.model.Achievement;

@Transactional
@Service
public class AchievementService {
	@Autowired
	private AchievementDao achievementDao;
	
	public Achievement getAchievementByPirateId(int pirateId) {
		return achievementDao.getAchievementByPirateId(pirateId);
	}
	
	public void updateNewlyUnemployed(String newlyUnemployedString, int pirateId) {
		achievementDao.updateNewlyUnemployed(newlyUnemployedString, pirateId);
	}
	
	public void updateElectraSlide(String electraSlideString, int pirateId) {
		achievementDao.updateElectraSlide(electraSlideString, pirateId);
	}
	
	public void updateSelfPlank(boolean selfPlank, int pirateId) {
		achievementDao.updateSelfPlank(selfPlank, pirateId);
	}
	
	public void addNewAchievement() {
		achievementDao.addNewAchievement();
	}
}
