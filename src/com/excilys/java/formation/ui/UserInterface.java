package com.excilys.java.formation.ui;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import com.excilys.java.formation.model.service.CompanyService;
import com.excilys.java.formation.model.service.ComputerService;
import com.excilys.java.formation.model.service.ComputerValidator;



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
	
	
	private static void printComputerByID() throws SQLException, ClassNotFoundException {
		Scanner scanner = ScannerHelper.getScanner();
		scanner.nextLine();
		System.out.println("Enter id of computer : ");
		String computerId = scanner.nextLine();
		ComputerService computerService = ComputerService.getService();
		computerService.printComputerById(computerId);
	}
	
	/* MOVE VALIDATION TO SERVICE 
	private static void createComputer() throws SQLException {
		Scanner scanner = ScannerHelper.getScanner();
		scanner.nextLine();
		ComputerValidator computerValidator = ComputerValidator.getValidator();
		ComputerService computerService = ComputerService.getService();
		
		System.out.println("Enter name");
		String name = scanner.nextLine();
		computerValidator.checkName(name);

		System.out.println("Enter introduced");
		String introducedStr = scanner.nextLine();
		Date introducedDate = computerValidator.getDate(introducedStr);

		System.out.println("Enter discontinued");
		String discontinuedStr = scanner.nextLine();
		Date discontinuedDate = computerValidator.getDate(discontinuedStr);
		computerValidator.checkDates(introducedDate, discontinuedDate);
		
		System.out.println("Enter company id");
		String companyIdStr = scanner.nextLine();
		Long companyId = computerValidator.checkCompanyId(companyIdStr);
		
		// Ajouter verif date
		computerService.createComputer(name, introducedDate, discontinuedDate, companyId);
		
	}
*/
		
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
			break;
		case 2:
			printCompaniesList();
			break;
		case 3:
			printComputerByID();
			break;
		/*case 4:
			createComputer();*/
		}
		
	}
	public static void main (String [] args) throws ClassNotFoundException, SQLException{
		startUI();
	
	
			/*	
			case 3:
				
		
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
