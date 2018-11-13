package com.ccc.chestersprinkles.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.chestersprinkles.dao.ManagerAssociationDao;
import com.ccc.chestersprinkles.model.ManagerAssociation;

@Transactional
@Service
public class ManagerAssociationService {
	@Autowired
	private ManagerAssociationDao managerAssociationDao;
	
	public List<ManagerAssociation> getManagerAssociationByManagerPirateId(int pirateId) {
		return managerAssociationDao.getDirectReportsByManagerId(pirateId);
	}
	
	public ManagerAssociation getManagerAssociationByDirectReportPirateId(int pirateId) {
		return managerAssociationDao.getManagerByDirectReportId(pirateId);
	}
	
	public List<ManagerAssociation> getManagerAssociationByManagerName(String name) {
		return managerAssociationDao.getDirectReportsByManagerName(name);
	}
	
	public ManagerAssociation getManagerAssociationByDirectReportName(String name) {
		return managerAssociationDao.getManagerByDirectReportName(name);
	}
}
