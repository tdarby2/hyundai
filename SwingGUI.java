/*******************************************************************************
 * Copyright (c) 2018 Trevor Darby.
 * All rights reserved.
 *******************************************************************************/
package edu.elon.contact;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class SwingGUI implements DatabaseObserver {

		private JFrame frame = new JFrame();
		private JPanel panel = new JPanel();
		private JPanel connectPanel = new JPanel();
		private JMenuBar menuBar = new JMenuBar();
		
		private JTextField firstNameText = new JTextField(20);
		private JTextField lastNameText = new JTextField(20);
		private JTextField middleNameText = new JTextField(20);
		private JTextField emailText = new JTextField(20);
		private JTextField majorText = new JTextField(20);
		
		private JTextField userNameText = new JTextField(20);
		private JTextField passwordText = new JTextField(20);
		private JTextField ipAddressText = new JTextField(20);
		private JTextField dbNameText = new JTextField(20);
		private JTextField tableNameText = new JTextField(20);
		
		private JLabel firstNameLabel = new JLabel("First Name");
		private JLabel lastNameLabel = new JLabel("Last Name");
		private JLabel middleNameLabel = new JLabel("Middle Name");
		private JLabel emailLabel = new JLabel("Email");
		private JLabel majorLabel = new JLabel("Major");
		
		private JLabel userNameLabel = new JLabel("User Name");
		private JLabel passwordLabel = new JLabel("Password");
		private JLabel ipLabel = new JLabel("IP Address");
		private JLabel dbLabel = new JLabel("Database Name");
		private JLabel tableLabel = new JLabel("Table Name");
		
		private JButton previousButton = new JButton("Previous");
		private JButton nextButton = new JButton("Next");
		private JButton connectOkButton = new JButton("OK");
		private JButton addOkButton = new JButton("OK");
		private JButton cancelButton = new JButton("Cancel");
		
		private JMenu fileMenu = new JMenu("File");
		private JMenu editMenu = new JMenu("Edit");
		
		private JMenuItem clearDB = new JMenuItem("Clear DB");
		private JMenuItem connect = new JMenuItem("Connect");
		private JMenuItem add = new JMenuItem("Add");
		private JMenuItem remove = new JMenuItem("Remove");
		private JMenuItem update = new JMenuItem("Update");
		private JMenuItem exit = new JMenuItem("Exit");
		
		MigLayout layout = new MigLayout();
		ModelInterface model;
		ControllerInterface controller;

		/**
		 * Constructor for the view, takes in the ModelInterface and Controller Interface as arguments
		 */
		public SwingGUI(ModelInterface model, ControllerInterface controller){
			this.model = model;
			this.controller = controller;
			model.registerObserver((DatabaseObserver)this);
			createMenuBar();
			createLabelsAndText();
			frame.setSize(400, 280);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
		}
		/**
		 * Adds menubar to Jpanel
		 */
		private void createMenuBar() {
			add.setEnabled(false);
			remove.setEnabled(false);
			update.setEnabled(false);
			clearDB.setEnabled(false);
					
			fileMenu.add(clearDB);
			fileMenu.add(connect);
			fileMenu.add(exit);
			editMenu.add(add);
			editMenu.add(remove);
			editMenu.add(update);
			
			add.addActionListener(new MenuListener());
			remove.addActionListener(new MenuListener());
			update.addActionListener(new MenuListener());
			connect.addActionListener(new MenuListener());
			clearDB.addActionListener(new MenuListener());
			exit.addActionListener(new MenuListener());
			
			menuBar.add(fileMenu);
			menuBar.add(editMenu);
			panel.add(menuBar);
			frame.setJMenuBar(menuBar);
			frame.add(panel);
			
		}
		/**
		 * Adds labels and text fields to original Jpanel
		 */
		public void createLabelsAndText() {
			frame.remove(connectPanel);
			frame.add(panel);
			frame.setTitle("Contact Display View");
			panel.setLayout(layout);
			panel.add(firstNameLabel);
			panel.add(firstNameText, "wrap");
			panel.add(middleNameLabel);
			panel.add(middleNameText, "wrap");
			panel.add(lastNameLabel);
			panel.add(lastNameText, "wrap");
			panel.add(emailLabel);
			panel.add(emailText, "wrap");
			panel.add(majorLabel);
			panel.add(majorText, "wrap");
			panel.add(previousButton, "gapleft 100");
			panel.add(nextButton);
			previousButton.addActionListener(new ButtonListener());
			nextButton.addActionListener(new ButtonListener());
		}
		/**
		 * Creates the connection screen for the application
		 */
		private void ConnectPanel() {
			connectPanel.setLayout(layout);
			connectPanel.add(userNameLabel);
			connectPanel.add(userNameText, "wrap");
			userNameText.setText("root");
			connectPanel.add(passwordLabel);
			connectPanel.add(passwordText, "wrap");
			passwordText.setText("mysqluser");
			connectPanel.add(ipLabel);
			connectPanel.add(ipAddressText, "wrap");
			ipAddressText.setText("localhost");
			connectPanel.add(dbLabel);
			connectPanel.add(dbNameText, "wrap");
			dbNameText.setText("contacts");
			connectPanel.add(tableLabel);
			connectPanel.add(tableNameText, "wrap");
			tableNameText.setText("contact");
			connectPanel.add(connectOkButton, "center");
			connectOkButton.addActionListener(new ButtonListener());
		}
		/**
		 * Enables the menu items after a connection is made in controller
		 */
		public void enableMenuItems() {
			add.setEnabled(true);
			remove.setEnabled(true);
			update.setEnabled(true);
			clearDB.setEnabled(true);
		}
		
		/**
		 * Changes the buttons from previous and next to ok and cancel
		 */
		public void ButtonOnePanel() {
			panel.remove(previousButton);
			panel.remove(nextButton);
			panel.add(addOkButton);
			panel.add(cancelButton);
			cancelButton.addActionListener(new ButtonListener());
			addOkButton.addActionListener(new ButtonListener());
		}
		
		/**
		 * Changes the buttons from ok and cancel to next and previous
		 */
		public void ButtonTwoPanel() {
			panel.remove(addOkButton);
			panel.remove(cancelButton);
			panel.add(nextButton);
			panel.add(previousButton);
		}
		
		/**
		 * Helper method for controller to set text field values
		 */
		public void setViewText() {
			firstNameText.setText(model.getFirstName());
			middleNameText.setText(model.getMiddleName());
			lastNameText.setText(model.getLastName());
			emailText.setText(model.getEmail());
			majorText.setText(model.getMajor());
		}
		
		/**
		 * Sets the values in the model from the controller
		 */
		public void setData() {
			model.setFirstName(firstNameText.getText());
			model.setMiddleName(middleNameText.getText());
			model.setLastName(lastNameText.getText());
			model.setEmail(emailText.getText());
			model.setMajor(majorText.getText());
		}
		
		/**
		 * Helper method for controller to set the connection data to be accsessed
		 */
		public void setConnectionData() {
			model.setUserName(userNameText.getText());
			model.setPassword(passwordText.getText());
			model.setIP(ipAddressText.getText());
			model.setDbName(dbNameText.getText());
			model.setTableName(tableNameText.getText());
			
		}
		
		/**
		 * Displays error message
		 */
		public void connectionError() {
			JOptionPane.showMessageDialog(connectPanel,"You did not correctly specify db parameters, please try again.");
		}

		/**
		 * Class that holds action listener implementation for the menu items
		 */
		class MenuListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == connect) {
					frame.remove(panel);
					ConnectPanel();
					frame.add(connectPanel);
					
					
				}
				if (e.getSource() == clearDB) {
					controller.clearDB();
				}
				if (e.getSource() == add) {
					frame.remove(connectPanel);
					frame.add(panel);
					ButtonOnePanel();
					
				}
				if (e.getSource() == remove) {
					frame.remove(connectPanel);
					frame.add(panel);
					ButtonTwoPanel();
					try {
						controller.removeEntry();
					} catch (SQLException e1) {
						System.out.println(e);
					}
				}
				if (e.getSource() == update) {
					frame.remove(connectPanel);
					frame.add(panel);
					ButtonTwoPanel();
					try {
						controller.updateEntry();
					} catch (SQLException e1) {
						System.out.println(e);
					}
				}
				if (e.getSource() == exit) {
					frame.dispose();
				}	     	
	        }
	    }

		/**
		 * Inner class to handle implementation of buttons on the bottom of jpanels
		 */
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == connectOkButton) {
					try {
						controller.connectToDb();
					} catch (SQLException | ClassNotFoundException e1) {
						System.out.println(e1);
					}
				}
				if (e.getSource() == nextButton) {
					try {
						controller.nextEntry();
					} catch (SQLException e1) {
						System.out.println(e);
					}
				}
				if (e.getSource() == previousButton) {
					controller.previousEntry();
				}
				if (e.getSource() == cancelButton) {
					controller.cancel();
				}
				
				if (e.getSource() == addOkButton) {
					controller.addEntry();
				}
	        }
		}

		/**
		 * Updates the gui text fields via observer pattern and called through controller
		 */
		@Override
		public void update() {
			firstNameText.setText(model.getFirstName());
			middleNameText.setText(model.getMiddleName());
			lastNameText.setText(model.getLastName());
			emailText.setText(model.getEmail());
			majorText.setText(model.getMajor());
			userNameText.setText(model.getUserName());
			passwordText.setText(model.getPassword());
			ipAddressText.setText(model.getIP());
			dbNameText.setText(model.getDbName());
			tableNameText.setText(model.getTableName());
			
		}
		
	
	}

	