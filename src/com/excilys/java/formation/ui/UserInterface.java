package com.excilys.java.formation.ui;

import java.sql.SQLException;
import java.util.Scanner;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.entities.ComputerStringAttributes;
import com.excilys.java.formation.model.persistence.NoComputerInResultSetException;
import com.excilys.java.formation.model.service.CompanyService;
import com.excilys.java.formation.model.service.ComputerService;
import com.excilys.java.formation.model.service.ValidatorException;



public class UserInterface {
	
	

	private static void printComputerList(Scanner scanner, ComputerService computerService)
				throws SQLException, ClassNotFoundException {
		scanner.nextLine();
		for (Computer c : computerService.getListComputers()) {
			System.out.println(c);
		}
	}
	
	private static void printCompaniesList(Scanner scanner, CompanyService companyService) 
			throws SQLException, ClassNotFoundException {
		scanner.nextLine();
		for (Company c : companyService.getCompaniesList()) {
			System.out.println(c);
		}
	}
	
	private static void printComputerByID(Scanner scanner, ComputerService computerService) throws SQLException, ClassNotFoundException {
		scanner.nextLine();
		System.out.println("Enter id of computer : ");
		String computerId = scanner.nextLine();
		try {
			System.out.println(computerService.getComputerById(computerId));
		} catch (NoComputerInResultSetException | ValidatorException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void createComputer(Scanner scanner, ComputerService computerService) throws SQLException, ClassNotFoundException {
		scanner.nextLine();
		System.out.println("Enter name");
		String name = scanner.nextLine();
		System.out.println("Enter date introduced (YYYY-MM-DD or null)");
		String introducedStr = scanner.nextLine();
		System.out.println("Enter date discontinued (YYYY-MM-DD or null)");
		String discontinuedStr = scanner.nextLine();
		System.out.println("Enter company id (or null)");
		String companyIdStr = scanner.nextLine();
		try {
		boolean created = computerService.createComputer(name, introducedStr, discontinuedStr, companyIdStr);
		if (created) {
			System.out.println("Successful creation");
		}else {
			System.out.println("Creation failed");
		}
		}catch(ValidatorException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private static boolean updateAttribute(String attributeName, String varValue, Scanner scanner) {
		System.out.println("Current "+attributeName+" : "+varValue+". Do you want do update ? (y/n)");
		String validation = scanner.next();
		scanner.nextLine();
		return validation.toLowerCase().equals("y");
	}
	
	private static void updateAttributes(Scanner scanner, ComputerService computerService, String idStr) 
			throws ClassNotFoundException, SQLException, NoComputerInResultSetException, ValidatorException {
		ComputerStringAttributes computerStr = new ComputerStringAttributes(computerService.getComputerById(idStr));
		if (updateAttribute("name", computerStr.getName(), scanner)) {
			System.out.println("Enter new name");
			computerStr.setName(scanner.nextLine());
		}	
		if (updateAttribute("name", computerStr.getIntroduced(), scanner)) {
			System.out.println("Enter new date of introduction");
			computerStr.setIntroduced(scanner.nextLine());
		}
		if (updateAttribute("date of discontinuation", computerStr.getDiscontinued(), scanner)) {
			System.out.println("Enter new date of discontinuation");
			computerStr.setDiscontinued(scanner.nextLine());
		}
		if (updateAttribute("company id", computerStr.getCompanyId(), scanner)) {
			System.out.println("Enter company id");
			computerStr.setDiscontinued(scanner.nextLine());
		}
		if (computerService.updateComputer(computerStr)) {
			System.out.println("Successful update");
		}else {
			System.out.println("Error : update not taken into account");
		}
	}
	
	private static void updateComputer(Scanner scanner, ComputerService computerService) throws  SQLException, ClassNotFoundException {
		scanner.nextLine();
		System.out.println("Enter id of computer to update");
		String idStr = scanner.nextLine();
		try {
			updateAttributes(scanner, computerService, idStr);
		}catch(NoComputerInResultSetException | ValidatorException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void deleteComputer(Scanner scanner, ComputerService computerService) throws SQLException, ClassNotFoundException {
		scanner.nextLine();
		System.out.println("Enter id of computer : ");
		String computerId = scanner.nextLine();
		try {
			boolean deleted = computerService.deleteComputer(computerId);
			if (deleted) {
				System.out.println("Successful deletion");
			}else {
				System.out.println("No computer found with id "+computerId);
			}
		} catch ( ValidatorException e) {
			System.out.println(e.getMessage());
		}
	}
		
	public static void startUI() throws SQLException, ClassNotFoundException {
		whileLoop :
			while (true) {
			System.out.println("Computer database application\n"+
					"Select operation:\n"+ 
					"1. List computers\n"+
					"2. List companies\n"+
					"3. Show computer details\n"+
					"4. Create a computer\n"+
					"5. Update a computer\n"+
					"6. Delete a computer\n"+
					"7. Quit");
			Scanner scanner = ScannerHelper.getScanner();
			ComputerService computerService = ComputerService.getService();
			int featureChoice = scanner.nextInt();
			switch(featureChoice) {
			case 1:
				printComputerList(scanner, computerService);
				break;
			case 2:
				CompanyService companyService = CompanyService.getService();
				printCompaniesList(scanner, companyService);
				break;
			case 3:
				printComputerByID(scanner, computerService);
				break;
			case 4:
				createComputer(scanner, computerService);
				break;
			case 5:
				updateComputer(scanner, computerService);
				break;
			case 6:
				deleteComputer(scanner, computerService);
				break;
			case 7:
				System.out.println("Bye!");
				break whileLoop;
			}
			System.out.println("");
		}
		
	}
	public static void main (String [] args) throws ClassNotFoundException, SQLException{
		startUI();
		ScannerHelper.getScanner().close();

	}

}
