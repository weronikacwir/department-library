package deptlib;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.sql.Statement;
import java.sql.Types;

public class Library {
	
	private Library() {}
	
	private static Connection getDBConnection() throws SQLException {
		
        String url = "jdbc:mysql://localhost/db_name?useSSL=false";
        String username = "user";
        String password = "password";
        
        return DriverManager.getConnection(url, username, password);
	}
	
	public static boolean isPermittedAction(HttpServletRequest request) {
		boolean permit = false;
		
		if(request.getSession().getAttribute("user") != null) {
			permit = true;
		}
		
		return permit;
	}
	
	public static void addItem(String type, String name, String additionalInfo) 
			throws SQLException {
		
		Connection c = null;
		try {
			c = getDBConnection();
			String sql = "INSERT INTO " + LibraryItem.TABLE + 
					     	" (" + 
					     		LibraryItem.TYPE + ", " + 
					     		LibraryItem.NAME + ", " + 
					     		LibraryItem.ADDITIONAL_INFO + 
					     	") " + 
					     "VALUES " + "(?, ?, ?)";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setString(2, name);
			pstmt.setString(3, additionalInfo);
			pstmt.execute();
		} finally {
			if (c != null) c.close();
		}
		
	}

	public static void checkOutItem(String itemId, String cin, String name, 
			                        Date borrowed, Date due)
			throws SQLException {

		Connection c = null;
		try {
			c = getDBConnection();
			String sql;
			PreparedStatement pstmt;

			// add entry to library_log table
			sql = "INSERT INTO " + LogEntry.TABLE + 
					" (" + 
						LogEntry.ITEM + ", " + 
						LogEntry.CIN + ", " + 
						LogEntry.NAME + ", " + 
						LogEntry.BORROWED + ", " + 
						LogEntry.DUE + 
					") " + 
					"VALUES " + "(?, ?, ?, ?, ?)";

			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, itemId);
			pstmt.setString(2, cin);
			pstmt.setString(3, name);
			pstmt.setDate(4, borrowed);
			if (due == null) {
				pstmt.setNull(5, Types.DATE);
			} else {
				pstmt.setDate(5, due);
			}
			pstmt.execute();

			// update library_items table
			sql = "UPDATE " + LibraryItem.TABLE + 
				  " SET " + LibraryItem.AVAILABLE + "=?" + 
				  " WHERE " + LibraryItem.ID + "=?";

			pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, false);
			pstmt.setString(2, itemId);
			pstmt.executeUpdate();
		} finally {
			if (c != null) c.close();
		}
	}	
	
	public static void editItem(String id, String type, String name, String additionalInfo) 
			throws SQLException {
		
		Connection c = null;
		try {
			c = getDBConnection();
			String sql = "UPDATE " + LibraryItem.TABLE + 
					     " SET " + 
			             	LibraryItem.TYPE + "=? ," + 
			             	LibraryItem.NAME + "=? ," + 
			             	LibraryItem.ADDITIONAL_INFO + "=? " + 
					     "WHERE " + LibraryItem.ID + "=?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setString(2, name);
			pstmt.setString(3, additionalInfo);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
		} finally {
			if (c != null) c.close();
		}
	}

	public static LibraryItem getItem(String id) throws SQLException {
		
		LibraryItem item = null;
		
		// retrieve item from database
		Connection c = null;
		try {
			c = getDBConnection();
			String sql = "SELECT * FROM " + LibraryItem.TABLE + 
					     " WHERE " + LibraryItem.ID + "=?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				item = readItem(rs);
			} else {
				throw new SQLException(pstmt.toString() + " did not return any results");
			} 
		} finally {
			if (c != null) c.close();
		}
		
		return item;
	}
	
	public static List<LibraryItem> getItemList() throws SQLException {
		
		List<LibraryItem> items = new ArrayList<>();
		
		// retrieve items from the database
		Connection c = null;
	    try {
			c = getDBConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + LibraryItem.TABLE);
			// read the results into list
			while (rs.next()) {
				items.add(readItem(rs));
			} 
		} finally {
			if (c != null) c.close();
		}
		
		return items;
	}
	
	public static List<LogEntry> getItemLog(String itemId) 
			throws SQLException {
		
		List<LogEntry> log = new ArrayList<>();
		
		// retrieve the log from the database
		Connection c = null;
	    try {
			c = getDBConnection();
			String sql = "SELECT * FROM " + LogEntry.TABLE +
					     " WHERE "    + LogEntry.ITEM + " =?" +
					     " ORDER BY " + LogEntry.BORROWED + " DESC" + ", " +
					                    LogEntry.ID + " DESC";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();
			// read the results into list
			while (rs.next()) {
				log.add(readLogEntry(rs));
			} 
		} finally {
			if (c != null) c.close();
		}
		
		return log;
	}

	public static void returnItem(String itemId, Date returned) 
			throws SQLException {
		
		Connection c = null;
		
		try {
			c = getDBConnection();
			String sql;
			PreparedStatement pstmt;
			
			// update the entry in library_log table
			// to show when the item was returned
			sql = "UPDATE " + LogEntry.TABLE + 
				  " SET "   + LogEntry.RETURNED + "=?" +
				  " WHERE " + LogEntry.ITEM + "=?" +
				  " AND "   + LogEntry.RETURNED + " IS NULL";
			
			pstmt = c.prepareStatement(sql);
			pstmt.setDate(1, returned);
			pstmt.setString(2, itemId);
			pstmt.executeUpdate();
		
			// update the item in library_items table
			// to show that the item is available
			sql = "UPDATE " + LibraryItem.TABLE + 
			      " SET "   + LibraryItem.AVAILABLE + "=?" + 
				  " WHERE " + LibraryItem.ID + "=?";
				
			pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			pstmt.setString(2, itemId);
			pstmt.executeUpdate();
		}
		finally {
			if (c != null) c.close();
		}
		
	}
	
	public static void addItemType(String newType)
			throws SQLException {
		
		Connection c = null;
		try {
			c = getDBConnection();
			String sql = "INSERT INTO " + LibraryType.TABLE + 
					     " VALUES (?)";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, newType);
			pstmt.execute();
		} finally {
			if (c != null) c.close();
		}
		
	}
	
	public static void editItemType(String originalType, String editedType)
			throws SQLException {

		Connection c = null;
		try {
			c = getDBConnection();
			String sql;
			PreparedStatement pstmt;
			
			// update the types table
			sql = "UPDATE " + LibraryType.TABLE + 
				  " SET " +  LibraryType.TYPE + "=?" + 
				  " WHERE " + LibraryType.TYPE + "=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, editedType);
			pstmt.setString(2, originalType);
			pstmt.executeUpdate();
			
			// update all the entries with the type in the types table
			sql = "UPDATE " + LibraryItem.TABLE + 
				  " SET " + LibraryItem.TYPE + "=?" +
				  " WHERE " + LibraryItem.TYPE + "=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, editedType);
			pstmt.setString(2, originalType);
			pstmt.executeUpdate();
			
		} finally {
			if (c != null) c.close();
		}
		
	}
	
	public static List<String> getItemTypes() 
			throws SQLException {
		List<String> types = new ArrayList<>();
		
		// retrieve the types from the database
		Connection c = null;
	    try {
			c = getDBConnection();
			String sql = "SELECT * FROM " + LibraryType.TABLE +
					     " ORDER BY " + LibraryType.TYPE;
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// read the results into list
			while (rs.next()) {
				types.add(rs.getString(LibraryType.TYPE));
			} 
		} finally {
			if (c != null) c.close();
		}
		
		return types;
	}
				
	public static boolean validateUser(String username, String pw)
			throws SQLException {
		
		LibraryUser user = null;
		
		// retrieve the user from the database
		Connection c = null;
		try {
			c = getDBConnection();
			String sql = "SELECT * FROM " + LibraryUser.TABLE + 
					     " WHERE " + LibraryUser.NAME + "=?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user = readUser(rs);
			} 
		} finally {
			if (c != null) c.close();
		}
		
		return user.validate(pw);
	}

	private static LibraryItem readItem(ResultSet rs) throws SQLException {
		LibraryItem item = new LibraryItem (rs.getInt(LibraryItem.ID),
				                            rs.getString(LibraryItem.TYPE),
				                            rs.getString(LibraryItem.NAME),
				                            rs.getString(LibraryItem.ADDITIONAL_INFO),
				                            rs.getBoolean(LibraryItem.AVAILABLE));
		return item;
	}

	private static LogEntry readLogEntry(ResultSet rs) throws SQLException {
		LogEntry entry = new LogEntry(rs.getInt(LogEntry.ID),
				                      rs.getInt(LogEntry.ITEM),
				                      rs.getInt(LogEntry.CIN),
				                      rs.getString(LogEntry.NAME),
				                      rs.getDate(LogEntry.BORROWED),
				                      rs.getDate(LogEntry.DUE),
				                      rs.getDate(LogEntry.RETURNED));
		return entry;
	}

	private static LibraryUser readUser(ResultSet rs) 
			throws SQLException {
		
		LibraryUser user = new LibraryUser(rs.getString(LibraryUser.NAME),
				                           rs.getString(LibraryUser.PASSWORD));
		return user;
	}

}
