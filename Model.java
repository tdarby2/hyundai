/*******************************************************************************
 * Copyright (c) 2018 Trevor Darby.
 * All rights reserved.
 *******************************************************************************/
package edu.elon.contact;

import java.util.ArrayList;

public class Model implements ModelInterface {
	 private ArrayList<DatabaseObserver> dbObserver = new ArrayList<DatabaseObserver>();
	 private String firstName;
	 private String middleName;
	 private String lastName;
	 private String email;
	 private String major;
	 private String userName;
	 private String password;
	 private String ip;
	 private String dbName;
	 private String tableName;
	

	public Model() {
		
	}

	/**
	 * Sets first name
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		notifyDbObservers();
	}

	/**
	 * Gets first name
	 * @return firstName
	 */
	@Override
	public String getFirstName() {
		return firstName;		
	}

	/**
	 * Sets middle name
	 */
	@Override
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
		notifyDbObservers();
		
	}
	/**
	 * gets middle name
	 * @return middle name
	 */
	@Override
	public String getMiddleName() {
		return middleName;		
	}

	/**
	 * Sets last name
	 */
	@Override
	public  void setLastName(String lastName) {
		this.lastName = lastName;
		notifyDbObservers();

	}
	/**
	 * gets last name
	 * @return lastName
	 */
	@Override
	public String getLastName() {
		return lastName;		
	}

	/**
	 * Sets email
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
		notifyDbObservers();
		
	}
	/**
	 * Gets email address
	 * @return email
	 */
	@Override
	public String getEmail() {
		return email;		
	}

	/**
	 * Sets major
	 */
	@Override
	public void setMajor(String major) {
		this.major = major;
		notifyDbObservers();
	}
	/**
	 * Gets major
	 * @return major
	 */
	@Override
	public String getMajor() {
		return major;
	}
	/**
	 * Sets user name for login
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
		notifyDbObservers();
	}
	/**
	 * Gets user name
	 * @return userName
	 */
	@Override
	public String getUserName() {
		return userName;
	}
	/**
	 * Sets login password
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
		notifyDbObservers();
	}
	/**
	 * Gets the password
	 * @return password
	 */
	@Override
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the IP address
	 */
	@Override
	public void setIP(String ip) {
		this.ip = ip;
		notifyDbObservers();
	}
	/**
	 * gets the IP address
	 * @return ip
	 */
	@Override
	public String getIP() {
		return ip;
	}
	/**
	 * Sets database name
	 */
	@Override
	public void setDbName(String dbName) {
		this.dbName = dbName;
		notifyDbObservers();
	}
	/**
	 * Gets database name
	 * @return firstName
	 */
	@Override
	public String getDbName() {
		return dbName;
	}
	/**
	 * Sets the name of the table in mysql
	 */
	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
		notifyDbObservers();
	}
	/**
	 * Gets the name of the table
	 * @return firstName
	 */
	@Override
	public String getTableName() {
		return tableName;
	}
	
	
	/**
	 * Registers object as an observer
	 */
	@Override
    public void registerObserver (DatabaseObserver o) {
        dbObserver.add(o);
    }
	/**
	 * Removes object as an observer
	 */
    @Override
    public void unregisterObserver (DatabaseObserver o) {
    	dbObserver.remove(o);
    }
    /**
	 * Notify's registered observers of changes
	 */
    public void notifyDbObservers() {
    	((DatabaseObserver) dbObserver).update();
    }






}
