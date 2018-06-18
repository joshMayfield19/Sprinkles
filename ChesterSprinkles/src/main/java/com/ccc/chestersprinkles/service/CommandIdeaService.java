package com.ccc.chestersprinkles.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.CommandIdeaDao;
import com.ccc.chestersprinkles.model.CommandIdea;

@Transactional
@Service
public class CommandIdeaService {
	@Autowired
	private CommandIdeaDao commandIdeaDao;
	
	public List<CommandIdea> getAllCommandIdeas(){
		try {
			return commandIdeaDao.getAllIdeas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
