package com.ccc.chestersprinkles.carnivaltours.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ccc.chestersprinkles.carnivaltours.model.CTInteraction;

@Repository
public class CTInteractionDao {
	@PersistenceContext
	private EntityManager interactionEntityManager;
	
	public void create(CTInteraction ctInteraction) {
		interactionEntityManager.persist(ctInteraction);
	}
	
	public void update(CTInteraction ctInteraction) {
		interactionEntityManager.merge(ctInteraction);
	}
	
	public CTInteraction getCtInteraction(String ctInteraction) {
		return interactionEntityManager.find(CTInteraction.class, ctInteraction);
	}
	
	public List<CTInteraction> getCtInteractionsByLocationId(String locationId) {
		List<CTInteraction> interactions = interactionEntityManager.createQuery(
				"select e from CTInteraction e where e.locationId = :locationId", CTInteraction.class)
				.setParameter("locationId", locationId).getResultList(); 
		
		return interactions;
	}
}
