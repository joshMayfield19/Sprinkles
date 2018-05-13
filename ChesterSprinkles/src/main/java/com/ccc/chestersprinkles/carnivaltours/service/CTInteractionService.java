package com.ccc.chestersprinkles.carnivaltours.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.carnivaltours.dao.CTInteractionDao;
import com.ccc.chestersprinkles.carnivaltours.model.CTInteraction;

@Transactional
@Service
public class CTInteractionService {
	@Autowired
	private CTInteractionDao ctInteractionDao;
	
	public CTInteraction getCtInteraction(String interactionId){
		return ctInteractionDao.getCtInteraction(interactionId);
	}
	
	public List<CTInteraction> getCtInteractionsByLocationId(String locationId) {
		return ctInteractionDao.getCtInteractionsByLocationId(locationId);
	}
}
