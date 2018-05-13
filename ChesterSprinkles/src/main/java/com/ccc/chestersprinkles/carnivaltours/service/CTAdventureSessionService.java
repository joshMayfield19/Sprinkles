package com.ccc.chestersprinkles.carnivaltours.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.carnivaltours.dao.CTAdventureSessionDao;
import com.ccc.chestersprinkles.carnivaltours.model.CTAdventureSession;

@Transactional
@Service
public class CTAdventureSessionService {
	@Autowired
	private CTAdventureSessionDao ctAdventureSessionDao;
	
	public CTAdventureSession getCtAdventureSession(String adventureId){
		return ctAdventureSessionDao.getCtAdventureSession(adventureId);
	}
	
	public void createAdventureSession(CTAdventureSession adventureSession) {
		ctAdventureSessionDao.create(adventureSession);
	}
	
	public void updateAdventureSession(CTAdventureSession adventureSession) {
		ctAdventureSessionDao.update(adventureSession);
	}
}
