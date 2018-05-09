package com.excilys.formation.console.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import com.excilys.formation.binding.mappers.ComputerDTOMapper;
import com.excilys.formation.service.validator.ValidatorException;
import com.excilys.formation.console.configuration.CLIConfiguration;
import com.excilys.formation.core.dtos.CompanyDTO;
import com.excilys.formation.core.dtos.ComputerDTO;
import com.excilys.formation.core.dtos.ComputerDTO.Builder;
import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.persistence.dao.UserDAO;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.excilys.formation.service.page.CompanyPage;
import com.excilys.formation.service.page.ComputerPage;
import com.excilys.formation.service.page.Page;
import com.excilys.formation.service.service.CompanyService;
import com.excilys.formation.service.service.ComputerService;
import com.excilys.formation.service.service.ServiceException;

@Component
public class UserInterface {

	private static final int PAGE_SIZE = 10;
	private static final String COMPUTER_URI = "http://localhost:8080/computer-database/ComputerDatabaseService/computer";
	private static final String COMPANY_URI = "http://localhost:8080/computer-database/CompanyDatabaseService/company";
	private ComputerService computerService;
	private CompanyService companyService;
	private Client client = ClientBuilder.newClient();
	private UserDAO userDAO;

	@Autowired
	public UserInterface(ComputerService computerService, CompanyService companyService,
			ComputerDTOMapper computerDTOMapper, UserDAO userDAO) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.userDAO = userDAO;

	}
	
	private void printComputerList(Scanner scanner) {
		scanner.nextLine();
		int pageNumber = 0;
		while (true) {
			List<ComputerDTO> computers = client
					.target(COMPUTER_URI)
					.path("/number/"+pageNumber+"/size/"+PAGE_SIZE)
					.request(MediaType.APPLICATION_JSON)
					.get(new GenericType<List<ComputerDTO>>(){});
			for (ComputerDTO computer : computers){
				System.out.println(computer);
			}
			System.out.println("p : previous page, n : next page, q : quit, g : goto page");
			switch (PageActionEnum.getAction(scanner.nextLine())) {
			case PREVIOUS:
				pageNumber --;
				break;
			case NEXT:
				pageNumber ++;
				break;
			case GOTO:
				System.out.println("Enter page number");
				pageNumber = scanner.nextInt();
				scanner.nextLine();
				break;
			case EXIT:
				return;
			case DEFAULT:
				break;
			}
		}
	}

	/*private void printCompanyList(Scanner scanner) {
		scanner.nextLine();
		int pageNumber = 0;
		while (true) {
			List<CompanyDTO> companies = client
					.target(COMPANY_URI)
					.path("/number/"+pageNumber+"/size/"+PAGE_SIZE)
					.request(MediaType.APPLICATION_JSON)
					.get(new GenericType<List<ComputerDTO>>(){});
			for (CompanyDTO computer : companies){
				System.out.println(computer);
			}
			System.out.println("p : previous page, n : next page, q : quit, g : goto page");
			switch (PageActionEnum.getAction(scanner.nextLine())) {
			case PREVIOUS:
				pageNumber --;
				break;
			case NEXT:
				pageNumber ++;
				break;
			case GOTO:
				System.out.println("Enter page number");
				pageNumber = scanner.nextInt();
				scanner.nextLine();
				break;
			case EXIT:
				return;
			case DEFAULT:
				break;
			}
		}
	}*/


	private void findCompanyByName(Scanner scanner) {
		System.out.println("Enter name :");
		scanner.nextLine();
		String name = scanner.nextLine();
		List<CompanyDTO> companies = client
				.target(COMPANY_URI)
				.path(name)
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<CompanyDTO>>(){});
		if (companies.size() == 0)
			System.out.println("No companies found");
		else {
			for (CompanyDTO c : companies) {
				System.out.println(c);
			}
		}
	}

	/**
	 * Prints the details of a computer
	 *
	 * @param scanner
	 * @param computerService
	 * @throws ServiceException
	 * @throws DAOException
	 * @throws ConnectionException
	 */
	private void printComputerByID(Scanner scanner) {
		System.out.println("Enter id of computer : ");
		try {
			Long computerId = scanner.nextLong();
			ComputerDTO computerDTO =   client
					.target(COMPUTER_URI)
					.path(String.valueOf(computerId))
					.request(MediaType.APPLICATION_JSON)
					.get(ComputerDTO.class);
			if(computerDTO != null) {
				System.out.println(computerDTO);
			} else {
				System.out.println("No computer found with id : " + computerId);
			}
		} catch (InputMismatchException e) {
			System.out.println("Numbers only are accepted as id");
		}
	}

	/**
	 * Creates a computer
	 *
	 * @param scanner
	 * @param computerService
	 * @throws ServiceException
	 * @throws DAOException
	 * @throws ConnectionException
	 * @throws ValidatorException
	 */
	private void createComputer(Scanner scanner){
		scanner.nextLine();
		System.out.println("Enter name");
		String name = scanner.nextLine();
		System.out.println("Enter date introduced (YYYY-MM-DD or null)");
		String introducedStr = scanner.nextLine();
		System.out.println("Enter date discontinued (YYYY-MM-DD or null)");
		String discontinuedStr = scanner.nextLine();
		System.out.println("Enter company id (or null)");
		String companyIdStr = scanner.nextLine();
		CompanyDTO companyDTO = new CompanyDTO();
		try {
			companyDTO.setId(Long.parseUnsignedLong(companyIdStr));
		} catch (NumberFormatException e) {
			companyDTO = null;
		}
		Builder computerDTOBuilder = new Builder();
		computerDTOBuilder.withName(name).withIntroduced(introducedStr).withDiscontinued(discontinuedStr)
		.withCompany(companyDTO);
		client
		.target(COMPUTER_URI)
		.request(MediaType.APPLICATION_JSON)
		.put(Entity.entity(computerDTOBuilder.build(), MediaType.APPLICATION_JSON));

	}

	private boolean updateAttribute(String attributeName, String varValue, Scanner scanner) {
		System.out.println("Current " + attributeName + " : " + varValue + ". Do you want do update ? (y/n)");
		String validation = scanner.next();
		scanner.nextLine();
		return validation.equalsIgnoreCase("y");
	}

	private void updateAttributes(Scanner scanner, Long computerId){
		ComputerDTO computerDTO = client
				.target(COMPUTER_URI)
				.path(String.valueOf(computerId))
				.request(MediaType.APPLICATION_JSON)
				.get(ComputerDTO.class);
		if (computerDTO == null) {
			System.out.println("No computer found with id " + computerId);
			return;
		}
		if (updateAttribute("name", computerDTO.getName(), scanner)) {
			System.out.println("Enter new name");
			computerDTO.setName(scanner.nextLine());
		}
		if (updateAttribute("date of introduction", computerDTO.getIntroduced(), scanner)) {
			System.out.println("Enter new date of introduction");
			computerDTO.setIntroduced(scanner.nextLine());
		}
		if (updateAttribute("date of discontinuation", computerDTO.getDiscontinued(), scanner)) {
			System.out.println("Enter new date of discontinuation");
			computerDTO.setDiscontinued(scanner.nextLine());
		}
		CompanyDTO companyDTO = computerDTO.getCompany();
		String companyIdStr;
		if (companyDTO == null) {
			companyIdStr = "null";
		} else {
			companyIdStr = String.valueOf(computerDTO.getCompany().getId());
		}
		if (updateAttribute("company id", companyIdStr, scanner)) {
			System.out.println("Enter company id");
			companyIdStr = scanner.nextLine();
			companyDTO = computerDTO.getCompany();
			try {
				companyDTO.setId(Long.parseUnsignedLong(companyIdStr));
			} catch (NumberFormatException | NullPointerException e) {
				companyDTO = null;
			}
			computerDTO.setCompany(companyDTO);
		}
		client.target(COMPUTER_URI).request(MediaType.APPLICATION_JSON)
		.put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
	}

	private void updateComputer(Scanner scanner) {
		System.out.println("Enter id of computer to update");
		try {
			Long computerId = scanner.nextLong();
			updateAttributes(scanner, computerId);
		} catch (InputMismatchException e) {
			System.out.println("Numbers only are accepted as id");
		}

	}

	private void deleteComputer(Scanner scanner){
		System.out.println("Enter id of computer : ");
		try {
			Long computerId = scanner.nextLong();
			client.target(COMPUTER_URI).path(String.valueOf(computerId)).request().delete();
		} catch (InputMismatchException e) {
			System.out.println("Only numbers are accepted as id");
		}
	}

	private void deleteCompany(Scanner scanner) {
		System.out.println("Enter id of company to delete :");
		try {
			Long companyId = scanner.nextLong();
			client.target(COMPANY_URI).path(String.valueOf(companyId)).toString();
		} catch (InputMismatchException e) {
			System.out.println("Only numbers are accepted as id");
		}
	}

	public void startUI(Scanner scanner) throws ServiceException, ValidatorException {
		System.out.println(userDAO.getUserInfo("user").getPassword());
		while (true) {
			System.out.println("Computer database application\n" + "Select operation:\n" + "1. List computers\n"
					+ "2. List companies\n" + "3. Show computer details (by id)\n" + "4. Create a computer\n"
					+ "5. Update a computer\n" + "6. Delete a computer\n" + "7. Find company by name\n"
					+ "8. Delete company by id\n" + "9. Quit");

			int featureChoice = 0;
			try {
				featureChoice = scanner.nextInt();
			} catch (InputMismatchException e) {
				return;
			}
			switch (CLIActionEnum.values()[featureChoice]) {
			case LIST_COMPUTERS:
				printComputerList(scanner);
				//printPagedList(scanner, new ComputerPage(computerService, 1, PAGE_SIZE));
				break;
			case LIST_COMPANIES:
				//printPagedList(scanner, new CompanyPage(companyService, 1, PAGE_SIZE));
				break;
			case COMPUTER_DETAILS:
				printComputerByID(scanner);
				break;
			case CREATE_COMPUTER:
				createComputer(scanner);
				break;
			case UPDATE_COMPUTER:
				updateComputer(scanner);
				break;
			case DELETE_COMPUTER:
				deleteComputer(scanner);
				break;
			case COMPANY_BY_NAME:
				findCompanyByName(scanner);
				break;
			case DELETE_COMPANY:
				deleteCompany(scanner);
				break;
			case EXIT:
				System.out.println("Bye!");
				return;
			default:
				System.out.println("Choose a number");
				break;
			}
			System.out.println("");
			scanner.nextLine();
		}

	}

	public static void main(String[] args) throws ValidatorException, ServiceException {
		ApplicationContext context = new AnnotationConfigApplicationContext(CLIConfiguration.class);
		context.getBean(UserInterface.class).startUI(new Scanner(System.in));
	}

}