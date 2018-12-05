/*******************************************************************************
 * Copyright (c) 2018 Trevor Darby.
 * All rights reserved.
 *******************************************************************************/

package edu.elon.contact;

import java.sql.SQLException;

/**
 * @author admin
 *
 */
public interface ControllerInterface {
	public void addEntry();
	
	public void updateEntry() throws SQLException ;
	
	public void removeEntry() throws SQLException;
	
	public void clearDB();
	
	public void nextEntry() throws SQLException;
	
	public void previousEntry();
	
	public void connectToDb() throws SQLException, ClassNotFoundException ;
	
	public void cancel();
}
