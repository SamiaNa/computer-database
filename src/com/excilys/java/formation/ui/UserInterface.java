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
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException, IOException, ParseException{
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
				
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("Enter name");
				String name = scanner.next();
				
				
				System.out.println("Enter introduced");
				scanner.nextLine();
				String time = scanner.nextLine();
				Timestamp ti;
				if (time.toLowerCase().equals("null") || time.equals("")) {
					 ti = null;
				}else {
					java.util.Date date = dateFormat.parse(time);
			        ti = new Timestamp(date.getTime());
				}
		        
		        
				System.out.println("Enter discontinued");
				
				time = scanner.nextLine();
				Timestamp td;
				if (time.toLowerCase().equals("null") || time.equals("")) {
					 td = null;
					 System.out.println("ici");
				}else {
					java.util.Date date = dateFormat.parse(time);
			        td = new Timestamp(date.getTime());
				}
		   		        
				System.out.println("Enter company id");
				String companyIdStr = scanner.nextLine();
				Long companyId;
				if (companyIdStr.toLowerCase().equals("null") || companyIdStr.equals("")) {
					companyId = null;
				}else {
					companyId = Long.parseLong(companyIdStr);
				}
				System.out.println(name+" "+ti+" "+td+" "+companyId);
				computerS.createComputer(name, ti, td, companyId);
				break;
			
			case 5:
				System.out.println("Enter id of computer to update");
				long id = scanner.nextLong();
				System.out.println("Enter new name :");
				scanner.nextLine();
				String newName = scanner.nextLine();
				computerS.updateComputer(id, newName);
				
				
			case 6:
				System.out.println("Enter id of computer to delete");
				Long computerId = scanner.nextLong();
				computerS.deleteComputer(computerId);
			default:
				break;
		}
		conn.close();
		scanner.close();

	}

}
