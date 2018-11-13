package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.ManagerAssociation;

public class ManagerAssociationDao {
	private static final String GET_BY_MGR_PIRATE_ID = "select manager_assoc_id, mgr_pirate_id, mgr_name, mgr_level, dr_pirate_id, dr_name, dr_level from manager_assoc where mgr_pirate_id = ?";
	private static final String GET_BY_DR_PIRATE_ID = "select manager_assoc_id, mgr_pirate_id, mgr_name, mgr_level, dr_pirate_id, dr_name, dr_level from manager_assoc where dr_pirate_id = ?";
	private static final String GET_BY_MGR_NAME = "select manager_assoc_id, mgr_pirate_id, mgr_name, mgr_level, dr_pirate_id, dr_name, dr_level from manager_assoc where mgr_name = ?";
	private static final String GET_BY_DR_NAME = "select manager_assoc_id, mgr_pirate_id, mgr_name, mgr_level, dr_pirate_id, dr_name, dr_level from manager_assoc where dr_name = ?";
	
	public List<ManagerAssociation> getDirectReportsByManagerId(int pirateId) {
		List<ManagerAssociation> managerAssociations = new ArrayList<ManagerAssociation>(); 
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_BY_MGR_PIRATE_ID);
	    	stmt.setInt(1, pirateId);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	ManagerAssociation managerAssociation = new ManagerAssociation();
	        	managerAssociation.setAssociationId(rs.getInt(1));
	        	managerAssociation.setManagerPirateId(rs.getInt(2));
	        	managerAssociation.setManagerName(rs.getString(3));
	        	managerAssociation.setManagerLevel(rs.getString(4));
	        	managerAssociation.setDirectReportPirateId(rs.getInt(5));
	        	managerAssociation.setDirectReportName(rs.getString(6));
	        	managerAssociation.setDirectReportLevel(rs.getString(7));
	        	
	        	managerAssociations.add(managerAssociation);
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
		    		SqliteDao.closeDb(con);
	        	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	    }
	    
	    return managerAssociations;
	}
	
	public List<ManagerAssociation> getDirectReportsByManagerName(String name) {
		List<ManagerAssociation> managerAssociations = new ArrayList<ManagerAssociation>(); 
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(GET_BY_MGR_NAME);
	    	stmt.setString(1, name);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	ManagerAssociation managerAssociation = new ManagerAssociation();
	        	managerAssociation.setAssociationId(rs.getInt(1));
	        	managerAssociation.setManagerPirateId(rs.getInt(2));
	        	managerAssociation.setManagerName(rs.getString(3));
	        	managerAssociation.setManagerLevel(rs.getString(4));
	        	managerAssociation.setDirectReportPirateId(rs.getInt(5));
	        	managerAssociation.setDirectReportName(rs.getString(6));
	        	managerAssociation.setDirectReportLevel(rs.getString(7));
	        	
	        	managerAssociations.add(managerAssociation);
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
		    		SqliteDao.closeDb(con);
	        	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	    }
	    
	    return managerAssociations;
	}
	
	public ManagerAssociation getManagerByDirectReportId(int pirateId) {
		ManagerAssociation association = new ManagerAssociation();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
			stmt = con.prepareStatement(GET_BY_DR_PIRATE_ID);
			stmt.setInt(1, pirateId);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	association.setAssociationId(rs.getInt(1));
	        	association.setManagerPirateId(rs.getInt(2));
	        	association.setManagerName(rs.getString(3));
	        	association.setManagerLevel(rs.getString(4));
	        	association.setDirectReportPirateId(rs.getInt(5));
	        	association.setDirectReportName(rs.getString(6));
	        	association.setDirectReportLevel(rs.getString(7));
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
		    		SqliteDao.closeDb(con);
	        	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	    }
	    
	    return association;
	}
	
	public ManagerAssociation getManagerByDirectReportName(String name) {
		ManagerAssociation association = new ManagerAssociation();
		
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
			stmt = con.prepareStatement(GET_BY_DR_NAME);
			stmt.setString(1, name);
	    	
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	association.setAssociationId(rs.getInt(1));
	        	association.setManagerPirateId(rs.getInt(2));
	        	association.setManagerName(rs.getString(3));
	        	association.setManagerLevel(rs.getString(4));
	        	association.setDirectReportPirateId(rs.getInt(5));
	        	association.setDirectReportName(rs.getString(6));
	        	association.setDirectReportLevel(rs.getString(7));
	        }
	    } catch (SQLException e ) {
	        //JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
		    		SqliteDao.closeDb(con);
	        	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	    }
	    
	    return association;
	}
}
