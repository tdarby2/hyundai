/*******************************************************************************
 * Copyright (c) 2018 Trevor Darby.
 * All rights reserved.
 *******************************************************************************/
package edu.elon.contact;

public interface ModelInterface {

	public void setFirstName(String string);
	
	public String getFirstName();
	
	public void setMiddleName(String string);
	
	public String getMiddleName();
	
	public void setLastName(String string);
	
	public String getLastName();
	
	public void setEmail(String string);
	
	public String getEmail();
	
	public void setMajor(String string);
	
	public String getMajor();
	
	public void setUserName(String string);
	
	public String getUserName();
	
	public void setPassword(String string);
	
	public String getPassword();
	
	public void setIP(String string);
	
	public String getIP();
	
	public void setDbName(String string);
	
	public String getDbName();
	
	public void setTableName(String string);
	
	public String getTableName();
	
	public void registerObserver (DatabaseObserver o);
    
	public void unregisterObserver (DatabaseObserver o);
}
