package com.ccc.chestersprinkles.service;

import java.sql.SQLException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.ParrotPhraseDao;
import com.ccc.chestersprinkles.model.ParrotPhrase;

@Transactional
@Service
public class ParrotPhraseService {
	@Autowired
	private ParrotPhraseDao parrotPhraseDao;
	
	public ParrotPhrase getPhraseById(int phraseId){
		try {
			return parrotPhraseDao.getPhraseById(phraseId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
