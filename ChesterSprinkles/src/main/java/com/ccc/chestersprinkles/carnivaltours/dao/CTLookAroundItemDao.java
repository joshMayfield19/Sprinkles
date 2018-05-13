package com.ccc.chestersprinkles.carnivaltours.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ccc.chestersprinkles.carnivaltours.model.CTLookAroundItem;

@Repository
public class CTLookAroundItemDao {
	@PersistenceContext
	private EntityManager lookAroundItemEntityManager;
	
	public void create(CTLookAroundItem ctLookAroundItem) {
		lookAroundItemEntityManager.persist(ctLookAroundItem);
	}
	
	public void update(CTLookAroundItem ctLookAroundItem) {
		lookAroundItemEntityManager.merge(ctLookAroundItem);
	}
	
	public CTLookAroundItem getCtLookAroundItem(String ctLookAroundItem) {
		return lookAroundItemEntityManager.find(CTLookAroundItem.class, ctLookAroundItem);
	}
	
	public List<CTLookAroundItem> getCtLookAroundItemByLocationId(String locationId) {
		List<CTLookAroundItem> items = lookAroundItemEntityManager.createQuery(
				"select e from CTLookAroundItem e where e.locationId = :locationId", CTLookAroundItem.class)
				.setParameter("locationId", locationId).getResultList(); 
		
		return items;
	}
	
	public List<CTLookAroundItem> getAllCtItems() {
		List<CTLookAroundItem> items = lookAroundItemEntityManager.createQuery(
				"select e from CTLookAroundItem e", CTLookAroundItem.class).getResultList(); 
		
		return items;
	}
}
