/*******************************************************************************
 * Copyright (c) 2018 Trevor Darby.
 * All rights reserved.
 *******************************************************************************/
package edu.elon.contact;

public class ContactApplication {

	/**
	 * Main method for ContactApplication, runs DB application
	 * @param args
	 */
	public static void main(String[] args) {	
		ModelInterface model = new Model();
		new Controller(model);      
	}

}
