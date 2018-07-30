package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.PiratePointsHistory;

public class PiratePointsHistoryDao {
	private static final String ADD_NEW_EVENT = "INSERT INTO pirate_points_hist (pirate_id, points, event, date_of_event) "
			+ "VALUES (?, ?, ?, ?)";
	private static final String GET_HISTORY_BY_ID = "select points, event, date_of_event from pirate_points_hist where pirate_id = ? order by hist_id asc";

	public void addNewEvent(PiratePointsHistory piratePointsHistory) {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = SqliteDao.openDb();
			stmt = con.prepareStatement(ADD_NEW_EVENT);

			stmt.setInt(1, piratePointsHistory.getPirateId());
			stmt.setInt(2, piratePointsHistory.getPoints());
			stmt.setString(3, piratePointsHistory.getEvent());
			stmt.setString(4, piratePointsHistory.getDateOfEvent());

			// execute update SQL stetement
			stmt.executeUpdate();

		} catch (SQLException e) {
			// JDBCTutorialUtilities.printSQLException(e);
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
	}

	public List<PiratePointsHistory> getHistoryByPirateId(int pirateId) {
		List<PiratePointsHistory> piratePointsHistories = new ArrayList<PiratePointsHistory>();

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = SqliteDao.openDb();
			stmt = con.prepareStatement(GET_HISTORY_BY_ID);
			stmt.setInt(1, pirateId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				PiratePointsHistory piratePointsHistory = new PiratePointsHistory();
				piratePointsHistory.setPoints(rs.getInt(1));
				piratePointsHistory.setEvent(rs.getString(2));
				piratePointsHistory.setDateOfEvent(rs.getString(3));
				
				piratePointsHistories.add(piratePointsHistory);
				
			}

		} catch (SQLException e) {
			// JDBCTutorialUtilities.printSQLException(e);
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

		return piratePointsHistories;
	}
}
