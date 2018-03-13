package com.excilys.java.formation.ui;

import java.sql.*;
import java.util.Scanner;
import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.model.persistence.MySQLConnection;
import com.excilys.java.formation.model.service.CompanyService;
import com.excilys.java.formation.model.service.ComputerService;



public class UserInterface {
	
	private static Date nextDate(Scanner sc) {
		String time = sc.nextLine();
		Date ti;
		if (time.toLowerCase().equals("null") || time.equals("")) {
			 ti = null;
		}else {
			ti = Date.valueOf(time);
		}
	    return ti;
	}
	
	private static boolean validation(String attributeName, Object varObj, Scanner sc) {
		String varName = varObj == null ? "null" : varObj.toString();
		System.out.println("Current "+attributeName+" : "+varName+". Do you want do update ? (y/n)");
		String validation = sc.next();
		if (validation.toLowerCase().equals("y")) {
			System.out.println("Enter new "+varName);
			sc.nextLine();
			return true;
		}
		return false;
	}
	
	private static void printComputerList(Scanner sc, ComputerService computerS) throws SQLException {
		sc.nextLine();
		while (true) {
			computerS.printPagedList();
			String s = sc.nextLine();
			if (s.equals("n")) {
				computerS.printNextPage();
			}
			if (s.equals("q")) {
				break;
			}
			if (s.equals("p")) {
				computerS.printPrevPage();
			}
		}
	}
	
	
	private static void createComputer(Scanner sc, ComputerService computerS) throws SQLException {
		System.out.println("Enter name");
		
		sc.nextLine();
		String name = sc.nextLine();
		
		while (name.equals("")) {
			System.out.println("Name can't be an empty string");
			name = sc.nextLine();
		}
		
		System.out.println("Enter introduced");
		String time = sc.nextLine();
		Date ti;
		if (time.toLowerCase().equals("null") || time.equals("")) {
			 ti = null;
		}else {
			ti = Date.valueOf(time);
		}
        
        
		System.out.println("Enter discontinued");
		
		time = sc.nextLine();
		Date td;
		if (time.toLowerCase().equals("null") || time.equals("")) {
			 td = null;
		}else {
			td = Date.valueOf(time);
		}
   		        
		System.out.println("Enter company id");
		String companyIdStr = sc.nextLine();
		Long companyId;
		if (companyIdStr.toLowerCase().equals("null") || companyIdStr.equals("")) {
			companyId = null;
		}else {
			companyId = Long.parseLong(companyIdStr);
		}
		System.out.println(name+" "+ti+" "+td+" "+companyId);
		computerS.createComputer(name, ti, td, companyId);
		
	}
	
	
	private static void updateComputer(Scanner sc, ComputerService computerS) throws  SQLException {
		System.out.println("Enter id of computer to update");
		long id = sc.nextLong();
		Computer c = computerS.getComputerDetails(id);
		if (c == null) {
			System.out.println("No computer with id : "+id);
		}else {

			if (validation("name", c.getName(), sc)) {
				String newName = sc.nextLine();
				computerS.updateComputerName(id, newName);
			}
			if (validation("date of introduction", c.getIntroduced(), sc)) {
				Date d = nextDate(sc);
				computerS.updateComputerIntroduced(id, d, c.getDiscontinued());
			}
			if (validation("date of discontinuation", c.getDiscontinued(), sc)) {
				Date d = nextDate(sc);
				computerS.updateComputerDiscontinued(id, c.getIntroduced(), d);
			}
			if (validation("company_id", c.getCompany_id(), sc)) {
				Long newCompanyId = sc.nextLong();
				computerS.updateComputerCompanyID(id, newCompanyId);
			}
			
		}
		
	}
	public static void main (String [] args) throws ClassNotFoundException, SQLException{

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
				printComputerList(scanner, computerS);
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
				createComputer(scanner, computerS);
				break;
			
			case 5:
				updateComputer(scanner, computerS);
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
