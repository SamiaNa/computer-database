package com.excilys.java.formation.ui;

import java.sql.SQLException;
import java.util.Scanner;

import com.excilys.java.formation.model.service.CompanyService;
import com.excilys.java.formation.model.service.ComputerService;



public class UserInterface {
	
	/*private static Date nextDate(Scanner sc) {
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
	}*/
	
/*
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
	
	private static void printComputerList(Scanner sc) throws SQLException {
		sc.nextLine();
		ComputerService computerService= new ComputerService ();
		while (true) {
			computerService.printPagedList();
			String s = sc.nextLine();
			switch (s) {
			case "n" : 
				computerService.printNextPage();
				break;
			case "p":
				computerService.printPrevPage();
				break;
			default:
				break;
			}
		}
	}
	
	*/
	
	private static void printComputerList() throws SQLException, ClassNotFoundException {
		Scanner scanner = ScannerHelper.getScanner();
		scanner.nextLine();
		ComputerService computerService = ComputerService.getService();
		computerService.printListComputers();
	}
	
	private static void printCompaniesList() throws SQLException, ClassNotFoundException {
		Scanner scanner = ScannerHelper.getScanner();
		scanner.nextLine();
		CompanyService companyService = CompanyService.getService();
		companyService.printCompaniesList();
		
	}
		
	public static void startUI() throws SQLException, ClassNotFoundException {
		System.out.println("Computer database application\n"+
				"Select operation:\n"+ 
				"1. List computers\n"+
				"2. List companies\n"+
				"3. Show computer details\n"+
				"4. Create a computer\n"+
				"5. Update a computer\n"+
				"6. Delete a computer\n");
		Scanner scanner = ScannerHelper.getScanner();
		int featureChoice = scanner.nextInt();
		switch(featureChoice) {
		case 1:
			printComputerList();
		case 2:
			printCompaniesList();
		}
		
	}
	public static void main (String [] args) throws ClassNotFoundException, SQLException{
		startUI();
	
	
/*		int featureChoice = scanner.nextInt();
		
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
		*/
	
	}

}
