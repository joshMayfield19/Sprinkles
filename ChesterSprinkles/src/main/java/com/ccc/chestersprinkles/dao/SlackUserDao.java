package com.ccc.chestersprinkles.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ccc.chestersprinkles.carnivaltours.model.CTLookAroundItem;
import com.ccc.chestersprinkles.model.SlackUser;

@Repository
public class SlackUserDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(SlackUser slackUser) {
		entityManager.persist(slackUser);
	}
	
	public void update(SlackUser slackUser) {
		entityManager.merge(slackUser);
	}
	
	public SlackUser getSlackUser(String slackId) {
		return entityManager.find(SlackUser.class, slackId);
	}
	
	public List<SlackUser> getSlackUsers() {
		List<SlackUser> users = entityManager.createQuery(
				"select e from SlackUser e", SlackUser.class).getResultList(); 
		
		return users;
	}
}
