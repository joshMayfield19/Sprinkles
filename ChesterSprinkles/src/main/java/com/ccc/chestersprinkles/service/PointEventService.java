package com.ccc.chestersprinkles.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.PointEventDao;
import com.ccc.chestersprinkles.model.PointEvent;

@Transactional
@Service
public class PointEventService {
	@Autowired
	private PointEventDao eventDao;
	
	public List<PointEvent> getEvents(){
		return eventDao.getEvents();
	}
}
