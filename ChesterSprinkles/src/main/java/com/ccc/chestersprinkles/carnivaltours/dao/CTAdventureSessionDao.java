package com.ccc.chestersprinkles.carnivaltours.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ccc.chestersprinkles.carnivaltours.model.CTAdventureSession;

@Repository
public class CTAdventureSessionDao {
	@PersistenceContext
	private EntityManager adventureEntityManager;
	
	public void create(CTAdventureSession ctAdventureSession) {
		adventureEntityManager.persist(ctAdventureSession);
	}
	
	public void update(CTAdventureSession ctAdventureSession) {
		adventureEntityManager.merge(ctAdventureSession);
	}
	
	public CTAdventureSession getCtAdventureSession(String ctAdventureSession) {
		return adventureEntityManager.find(CTAdventureSession.class, ctAdventureSession);
	}
}
