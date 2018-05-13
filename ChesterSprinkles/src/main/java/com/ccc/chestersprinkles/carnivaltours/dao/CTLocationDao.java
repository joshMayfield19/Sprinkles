package com.ccc.chestersprinkles.carnivaltours.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ccc.chestersprinkles.carnivaltours.model.CTLocation;

@Repository
public class CTLocationDao {
	@PersistenceContext
	private EntityManager locationEntityManager;
	
	public void create(CTLocation ctLocation) {
		locationEntityManager.persist(ctLocation);
	}
	
	public void update(CTLocation ctLocation) {
		locationEntityManager.merge(ctLocation);
	}
	
	public CTLocation getCtLocation(String ctLocation) {
		return locationEntityManager.find(CTLocation.class, ctLocation);
	}
}
