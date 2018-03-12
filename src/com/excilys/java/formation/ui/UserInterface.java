package com.excilys.java.formation.ui;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.excilys.java.formation.model.persistence.MySQLConnection;
import com.excilys.java.formation.model.service.CompanyService;
import com.excilys.java.formation.model.service.ComputerService;



public class UserInterface {
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException, IOException, ParseException {
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
	
		Scanner scanner = new Scanner(System.in);
		int featureChoice = scanner.nextInt();
		
		ComputerService computerS = new ComputerService(conn);
		CompanyService  companyS = new CompanyService(conn);
		switch (featureChoice) {
			case 1:
				computerS.printListComputers();
				break;
			case 2:
				companyS.printListCompanies();
				break;
			case 3:
				System.out.println("Enter id of computer");
				int computerID = scanner.nextInt();
				computerS.printComputerDetails(computerID);
				break;
		
			case 4:
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				System.out.println("Enter name");
				String name = scanner.next();
				System.out.println("Enter introduced");
			
				String time = scanner.next();
				time += " "+scanner.next();
				java.util.Date date = dateFormat.parse(time);
		        Timestamp ti = new Timestamp(date.getTime());
		        
				System.out.println("Enter discontinued");
				
				time = scanner.next();
				time += " "+scanner.next();
				date = dateFormat.parse(time);
		        Timestamp td = new Timestamp(date.getTime());
		        
				System.out.println("Enter company id");
				Long company_id = scanner.nextLong();
				System.out.println(name+" "+ti+" "+td+" "+company_id);
				computerS.createComputer(name, ti, td, company_id);
				
			default:
				break;
		}
		conn.close();
		scanner.close();

	}

}
