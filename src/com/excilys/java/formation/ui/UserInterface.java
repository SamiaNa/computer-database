package com.excilys.java.formation.ui;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.model.persistence.MySQLConnection;
import com.excilys.java.formation.model.service.CompanyService;
import com.excilys.java.formation.model.service.ComputerService;



public class UserInterface {
	
	private static Date nextDate(Scanner sc) throws ParseException {
		String time = sc.nextLine();
		Date ti;
		if (time.toLowerCase().equals("null") || time.equals("")) {
			 ti = null;
		}else {
			ti = Date.valueOf(time);
		}
	    return ti;
	}
	
	private static boolean validation(String question, Scanner sc, String varName) {
		System.out.println(question);
		String validation = sc.next();
		if (validation.toLowerCase().equals("y")) {
			System.out.println("Enter new "+varName);
			sc.nextLine();
			return true;
		}
		return false;
	}
	
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
				while (true) {
					computerS.printPagedList();
					scanner.nextLine();
					String s = scanner.nextLine();
					if (s.equals("n")) {
						computerS.printNextPage();
					}
					break;
				}
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
				
				System.out.println("Enter name");
				String name = scanner.next();
				
				System.out.println("Enter introduced");
				scanner.nextLine();
				String time = scanner.nextLine();
				Date ti;
				if (time.toLowerCase().equals("null") || time.equals("")) {
					 ti = null;
				}else {
					ti = Date.valueOf(time);
				}
		        
		        
				System.out.println("Enter discontinued");
				
				time = scanner.nextLine();
				Date td;
				if (time.toLowerCase().equals("null") || time.equals("")) {
					 td = null;
					 System.out.println("ici");
				}else {
					td = Date.valueOf(time);
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
				Computer c = computerS.getComputerDetails(id);
				if (c == null) {
					System.out.println("No computer with id : "+id);
				}else {
		
					String question = "Current name : "+c.getName()+". Do you want to update the name?";
					if (validation(question, scanner, "name")) {
						String newName = scanner.nextLine();
						computerS.updateComputerName(id, newName);
					}
					
					question = "Current date of introduction : "+c.getIntroduced()+". Do you want to update the date?";
					if (validation(question, scanner, "date")) {
						Date t = nextDate(scanner);
						computerS.updateComputerIntroduced(id, t, c.getDiscontinued());
					}
					
					question = "Current date of discontinuation: "+c.getDiscontinued()+". Do you want to update the date?";
					if (validation(question, scanner, "date")) {
						Date t = nextDate(scanner);
						computerS.updateComputerDiscontinued(id, c.getIntroduced(), t);
					}
					
					question = "Current company id : "+c.getCompany_id()+". Do you want to update the id?";
					if (validation(question, scanner, "id")) {
						Long newCompanyId = scanner.nextLong();
						computerS.updateComputerCompanyID(id, newCompanyId);
					}
					
				}
				
				break;
				
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
