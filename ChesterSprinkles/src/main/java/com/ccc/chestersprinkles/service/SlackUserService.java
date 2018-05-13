package com.ccc.chestersprinkles.service;

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
		return slackUserDao.getSlackUser(slackId);
	}
	
	public List<SlackUser> getSlackUsers(){
		return slackUserDao.getSlackUsers();
	}
}
