package com.ccc.chestersprinkles.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.PresentationDao;
import com.ccc.chestersprinkles.model.Presentation;

@Transactional
@Service
public class PresentationService {
	@Autowired
	private PresentationDao presentationDao;
	
	public Presentation getPresentationByChallenge(int challengeId){
		try {
			return presentationDao.getPresentationByChallengeId(challengeId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Presentation> getCurrentPresentation(){
		try {
			return presentationDao.getCurrentPresenetrs();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}