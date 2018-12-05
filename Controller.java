/*******************************************************************************
 * Copyright (c) 2018 Trevor Darby.
 * All rights reserved.
 *******************************************************************************/

package edu.elon.contact;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Controller implements ControllerInterface {
	ModelInterface model;
	SwingGUI view;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String major;
	private String userName;
	private String password;
	private String ip;
	private String dbName;
	private String insertStatement;
	private String updateStatement;
	private String removeStatement;
	private String clearDBStatement;
	private int id;
	private int index = 1;
	
	Connection conn = null;
	Statement statement = null;
	ResultSet rs = null;
	
	public Controller(ModelInterface model) {
		this.model = model;
		view = new SwingGUI(model, this);
	}
	
	/**
	 * Adds entry to DB
	 * @param item
	 * @return item
	 */
	public void addEntry() {
		userName = model.getUserName();
		password = model.getPassword();
		ip = model.getIP();
		dbName = model.getDbName();
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		view.setData();
		
		
		try {
			conn = DriverManager.getConnection(connString, userName, password);
			
			firstName = model.getFirstName();
			middleName = model.getMiddleName();
			lastName = model.getLastName();
			email = model.getEmail();
			major = model.getMajor();
			insertStatement= "INSERT INTO contact (first_name, middle_name, last_name, email, major) VALUES("+"'"+ firstName +"'"+", " +"'"+ middleName +"'"+ ", " +"'"+ lastName +"'"+ ", " + "'"+ email +"'"+ ", " + "'"+major+"'" +")";
			PreparedStatement statement = conn.prepareStatement(insertStatement, Statement.CLOSE_ALL_RESULTS);
			statement.executeUpdate();
			view.update();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Updates entry from DB
	 * @param item
	 * @return item
	 * @throws SQLException 
	 */
	public void updateEntry() throws SQLException {
		userName = model.getUserName();
		password = model.getPassword();
		ip = model.getIP();
		dbName = model.getDbName();
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		view.setData();
		view.update();
		
		
		try {
			conn = DriverManager.getConnection(connString, userName, password);
			firstName = model.getFirstName();
			middleName = model.getMiddleName();
			lastName = model.getLastName();
			email = model.getEmail();
			major = model.getMajor();
			rs = statement.executeQuery("SELECT id,first_name, middle_name, last_name, email, major FROM contact");
			rs.next();
			id = rs.getInt("id");
			updateStatement = "UPDATE contact SET first_name =" + "'" +firstName +"'" +
					",middle_name = " + "'" +middleName + "'" +",last_name = " + "'" +lastName+"'"  +
					",email = " +"'" + email +"'" + ",major = " +"'" + major +"'" + "WHERE id = '"+id+"'";
			System.out.println(id);
			PreparedStatement stmt = conn.prepareStatement(updateStatement);
			stmt.executeUpdate();
			view.update();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				conn.close();
			}
			if (statement != null) {
				conn.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	
	}
	
	/**
	 * Removes entry from DB
	 */
	public void removeEntry() throws SQLException{
		userName = model.getUserName();
		password = model.getPassword();
		ip = model.getIP();
		dbName = model.getDbName();
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		view.setData();
			try {
			conn = DriverManager.getConnection(connString, userName, password);
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT id,first_name, middle_name, last_name, email, major FROM contact");
			rs.next();
			id = rs.getInt("id");
			removeStatement = "DELETE FROM contact WHERE id = ' " + id + "'";
			PreparedStatement stmt = conn.prepareStatement(removeStatement);
			stmt.executeUpdate();
			view.update();
			} catch (SQLException e) {
			  System.out.println(e);
			} finally {
				if (rs != null) {
					conn.close();
				}
				if (statement != null) {
					conn.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
	}
	
	/**
	 * Clears all DB entries
	 */
	public void clearDB() {
		userName = model.getUserName();
		password = model.getPassword();
		ip = model.getIP();
		dbName = model.getDbName();
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		
		try {
			conn = DriverManager.getConnection(connString, userName, password);
			clearDBStatement = "DELETE FROM contact";
			PreparedStatement statement = conn.prepareStatement(clearDBStatement);
			statement.executeUpdate();
			view.update();
		} catch (SQLException e) {
			System.out.println(e);
		} 
		
	}
	
	/**
	 * Gets next DB entry to display
	 * @throws SQLException 
	 */
	public void nextEntry() throws SQLException {
		userName = model.getUserName();
		password = model.getPassword();
		ip = model.getIP();
		dbName = model.getDbName();
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		
		
			try {
				conn = DriverManager.getConnection(connString, userName, password);
				statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = statement.executeQuery("SELECT id,first_name, middle_name, last_name, email, major FROM contact WHERE id ='" +index+"'");
				if(rs.next()) { 
					model.setFirstName(rs.getString("first_name"));
					model.setMiddleName(rs.getString("middle_name"));
					model.setLastName(rs.getString("last_name"));
					model.setEmail(rs.getString("email"));
					model.setMajor(major = rs.getString("major"));
					incrementId();
					view.update();
				} else {
					decrementId();
				}
			} catch (SQLException e) {
				System.out.println(e);
			} 
			
	}
	

	
	/**
	 * Gets previous DB entry to display
	 * @return
	 */
	public void previousEntry() {
		userName = model.getUserName();
		password = model.getPassword();
		ip = model.getIP();
		dbName = model.getDbName();
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		

			try {
				conn = DriverManager.getConnection(connString, userName, password);
				statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = statement.executeQuery("SELECT first_name, middle_name, last_name, email, major FROM contact WHERE id = '" +index+ "'");
				
				if(rs.next()) {
					model.setFirstName(rs.getString("first_name"));
					model.setMiddleName(rs.getString("middle_name"));
					model.setLastName(rs.getString("last_name"));
					model.setEmail(rs.getString("email"));
					model.setMajor(major = rs.getString("major"));
					decrementId();
					view.update();
				}
			} catch (SQLException e) {
				System.out.println(e);
			} 
			
	
	}
	/**
	 * Increments Id by 1
	 * 
	 */
	private void incrementId() {
		index = index + 1;
		System.out.println(index);	
	}
	/**
	 * Decrements Id by 1
	 * 
	 */
	private void decrementId() {
		index = index -1;
		if (index == 0) {
			index = 1;
		}
		System.out.println(index);
	}
	/**
	 * Connects the program to the database
	 * @throws SQLException 
	 * @throws ClassNotFoundException
	 */
	public void connectToDb() throws SQLException, ClassNotFoundException {
		
		view.setConnectionData();
		userName = model.getUserName();
		password = model.getPassword();
		ip = model.getIP();
		dbName = model.getDbName();
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(connString, userName, password);
			view.enableMenuItems();
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT first_name, middle_name, last_name, email, major FROM contact");
			view.createLabelsAndText();
			System.out.println("Connected");
		} catch (SQLException e) {
			System.out.println(e);
			view.connectionError();
		} finally {
			if (rs != null) {
				conn.close();
			}
			if (statement != null) {
				conn.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/**
	 * Changes the buttons if cancel is selected
	 */
	public void cancel() {
		view.ButtonTwoPanel();
	}
}
