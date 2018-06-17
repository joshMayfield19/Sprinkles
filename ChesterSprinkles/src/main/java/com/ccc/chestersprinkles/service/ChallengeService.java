package com.ccc.chestersprinkles.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.ChallengeDao;
import com.ccc.chestersprinkles.model.Challenge;

@Transactional
@Service
public class ChallengeService {
	@Autowired
	private ChallengeDao challengeDao;
	
	public Challenge getCurrentChallenge(){
		try {
			return challengeDao.getCurrentChallenge();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Challenge> getChallenges(){
		try {
			return challengeDao.getChallenges();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
