package com.ccc.chestersprinkles.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.SlackUserDao;
import com.ccc.chestersprinkles.model.SlackUser;

@Transactional
@Service
public class SlackUserService {
	@Autowired
	private SlackUserDao slackUserDao;
	
	public SlackUser getSlackUser(String slackId){
		try {
			return slackUserDao.getSlackUser(slackId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<SlackUser> getSlackUsers(){
		try {
			return slackUserDao.getSlackUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public void addNewSlackUser(SlackUser slackUser) {
		slackUserDao.addNewSlackUser(slackUser);
	}
}
