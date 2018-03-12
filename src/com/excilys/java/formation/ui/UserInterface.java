package com.excilys.java.formation.ui;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import com.excilys.java.formation.model.persistence.MySQLConnection;
import com.excilys.java.formation.model.service.ComputerService;

public class UserInterface {
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Computer database application");
		Connection conn = null;
		try {
			conn = MySQLConnection.getConnection();
		} catch (ClassNotFoundException|SQLException e) {
			System.out.println("Unable to initialize the connection to the database");
			return;
		}
		System.out.println("Select operation:");
		System.out.println("1. List computers");
		System.out.println("2. List companies");
		System.out.println("3. Show computer details");
		System.out.println("4. Create a computer");
		System.out.println("5. Update a computer");
		System.out.println("6. Delete a computer");
		
		ComputerService cs = new ComputerService(conn);
		Scanner scanner = new Scanner(System.in);
		int featureSelector = scanner.nextInt();
		switch (featureSelector) {
		case 0: cs.printListComputers();
			   break;
			default:
				break;
		}
		conn.close();
		scanner.close();

	}

}
