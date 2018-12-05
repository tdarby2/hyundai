package edu.elon.contact;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;



import java.sql.Connection;

class DatabaseTest {
	
	/**
	 * Tests addition to database based on first name value given/ gets janky when database is cleared but works
	 */
	@Test
	void testAddToDb() {
		String userName = "root";
		String password = "mysqluser";
		String ip = "localhost";
		String dbName = "contacts";
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		Connection conn;
		ResultSet rs;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(connString, userName, password);
			String insertStatement= "INSERT INTO contact (first_name, middle_name, last_name, email, major) VALUES('Miloh', 'H', 'Ramirez', 'mramirez@elon.edu', 'Computer Science')";
			PreparedStatement statement = conn.prepareStatement(insertStatement, Statement.CLOSE_ALL_RESULTS);
			statement.executeUpdate();
			rs = statement.executeQuery("SELECT id,first_name, middle_name, last_name, email, major FROM contact WHERE id = 6");
			rs.next();
			assertEquals("Miloh", rs.getString("first_name"));
			
			System.out.println(rs.getString("first_name"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Tests removing a row from the database
	 */
	@Test
	void testRemoveFromDb() {
		String userName = "root";
		String password = "mysqluser";
		String ip = "localhost";
		String dbName = "contacts";
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		Connection conn;
		ResultSet rs;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(connString, userName, password);
			String insertStatement= "INSERT INTO contact (first_name, middle_name, last_name, email, major) VALUES('Miloh', 'H', 'Ramirez', 'mramirez@elon.edu', 'Computer Science')";
			PreparedStatement statement = conn.prepareStatement(insertStatement, Statement.CLOSE_ALL_RESULTS);
			rs = statement.executeQuery("SELECT id,first_name, middle_name, last_name, email, major FROM contact ");
			rs.next();
			String removeStatement = "DELETE FROM contact WHERE id = 1";
			PreparedStatement stmt = conn.prepareStatement(removeStatement);
			stmt.executeUpdate();
			assertEquals("James", rs.getString("first_name"));
			System.out.println(rs.getString("first_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests updating a row in the database
	 */
	@Test
	void testUpdateDbEntry() {
		String userName = "root";
		String password = "mysqluser";
		String ip = "localhost";
		String dbName = "contacts";
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		Connection conn;
		ResultSet rs;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(connString, userName, password);
			String updateStatement = "UPDATE contact SET first_name = 'John'";
			PreparedStatement statement = conn.prepareStatement(updateStatement, Statement.CLOSE_ALL_RESULTS);
			rs = statement.executeQuery("SELECT id,first_name, middle_name, last_name, email, major FROM contact");
			rs.next();
			PreparedStatement stmt = conn.prepareStatement(updateStatement);
			stmt.executeUpdate();
			assertEquals("John", rs.getString("first_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Tests clearing the entire database
	 */
	@Test
	void testClearDb() {
		String userName = "root";
		String password = "mysqluser";
		String ip = "localhost";
		String dbName = "contacts";
		String connString = "jdbc:mysql://"+ip+"/"+dbName;
		Connection conn;
		ResultSet rs;
		
		try {
			conn = DriverManager.getConnection(connString, userName, password);
			String clearDBStatement = "DELETE FROM contact";
			PreparedStatement statement = conn.prepareStatement(clearDBStatement);
			rs = statement.executeQuery("SELECT id,first_name, middle_name, last_name, email, major FROM contact");
			statement.executeUpdate();
			assertEquals(null, rs.getString("first_name"));
		} catch (SQLException e) {
			System.out.println(e);
		} 
	}

}
