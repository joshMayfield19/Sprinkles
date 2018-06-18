package com.ccc.chestersprinkles.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.ChallengeIdeaDao;
import com.ccc.chestersprinkles.model.ChallengeIdea;

@Transactional
@Service
public class ChallengeIdeaService {
	@Autowired
	private ChallengeIdeaDao challengeIdeaDao;
	
	public List<ChallengeIdea> getAllChallengeIdeas(){
		try {
			return challengeIdeaDao.getAllIdeas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
