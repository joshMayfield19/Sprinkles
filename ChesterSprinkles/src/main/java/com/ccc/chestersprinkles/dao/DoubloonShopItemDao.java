package com.ccc.chestersprinkles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ccc.chestersprinkles.model.DoubloonShopItem;

public class DoubloonShopItemDao {
	private static final String GET_ITEMS = "select doubloon_shop_item, item_desc, item_price, item_quantity, item_command from doubloon_shop_item order by item_price asc";
	private static final String GET_ITEM_BY_COMMAND = "select doubloon_shop_item, item_desc, item_price, item_quantity, item_command from doubloon_shop_item"
			+ " where item_command=?";
	private static final String UPDATE_QUANTITY = "update doubloon_shop_item set item_quantity = ? where doubloon_shop_item = ?";
	
	public List<DoubloonShopItem> getAllItems() {
		List<DoubloonShopItem> shopItems = new ArrayList<DoubloonShopItem>();

		Connection con = null;
		Statement stmt = null;

		try {
			con = SqliteDao.openDb();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(GET_ITEMS);
			while (rs.next()) {
				DoubloonShopItem shopItem = new DoubloonShopItem();
				shopItem.setItemId(rs.getInt("doubloon_shop_item"));
				shopItem.setItemDescription(rs.getString("item_desc"));
				shopItem.setItemPrice(rs.getInt("item_price"));
				shopItem.setItemQuantity(rs.getInt("item_quantity"));
				shopItem.setItemCommand(rs.getString("item_command"));
				shopItems.add(shopItem);
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

		return shopItems;
	}

	public DoubloonShopItem getItemByCommand(String command) {
		DoubloonShopItem shopItem = new DoubloonShopItem();

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = SqliteDao.openDb();
			stmt = con.prepareStatement(GET_ITEM_BY_COMMAND);
			stmt.setString(1, command);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				shopItem.setItemId(rs.getInt("doubloon_shop_item"));
				shopItem.setItemDescription(rs.getString("item_desc"));
				shopItem.setItemPrice(rs.getInt("item_price"));
				shopItem.setItemQuantity(rs.getInt("item_quantity"));
				shopItem.setItemCommand(rs.getString("item_command"));
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

		return shopItem;
	}
	
	public void updateQuantity(int itemId, int quantity) {
		Connection con = null;
		PreparedStatement stmt = null;
	
	    try {
			con = SqliteDao.openDb();
	    	stmt = con.prepareStatement(UPDATE_QUANTITY);
	    	
	    	stmt.setInt(1, quantity);
	    	stmt.setInt(2, itemId);

			// execute update SQL stetement
	    	stmt.executeUpdate();
	    	
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
	}
}
