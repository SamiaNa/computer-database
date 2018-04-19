package com.excilys.java.formation.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.configuration.cli.CLIConfiguration;
import com.excilys.java.formation.dto.CompanyDTO;
import com.excilys.java.formation.dto.ComputerDTO;
import com.excilys.java.formation.dto.ComputerDTO.Builder;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerDTOMapper;
import com.excilys.java.formation.page.CompanyPage;
import com.excilys.java.formation.page.ComputerPage;
import com.excilys.java.formation.page.Page;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.service.ServiceException;
import com.excilys.java.formation.validator.ValidatorException;

@Component
public class UserInterface {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerDTOMapper computerDTOMapper;




    private  void printElements(Page page) {
        if (page instanceof ComputerPage) {
            ComputerPage computerPage = (ComputerPage) page;
            for (Computer c : computerPage.getElements()) {
                System.out.println(c);
            }
        }
        else if (page instanceof CompanyPage) {
            CompanyPage compantPage = (CompanyPage) page;
            for (Company c : compantPage.getElements()) {
                System.out.println(c);
            }
        }
    }
    private  void printPagedList(Scanner scanner, Page page) throws ValidatorException, ServiceException {
        scanner.nextLine();
        page.getPage(1, PAGE_SIZE);
        while (true) {
            printElements(page);
            System.out.println("p : previous page, n : next page, q : quit, g : goto page");
            switch (PageActionEnum.getAction(scanner.nextLine())) {
            case PREVIOUS:
                page.prevPage();
                break;
            case NEXT:
                page.nextPage();
                break;
            case GOTO:
                System.out.println("Enter page number");
                page.getPage(scanner.nextInt(), PAGE_SIZE);
                scanner.nextLine();
                break;
            case EXIT:
                return;
            case DEFAULT:
                break;
            }
        }
    }

    private  void findCompanyByName(Scanner scanner, CompanyService companyService ) throws ServiceException   {
        System.out.println("Enter name :");
        scanner.nextLine();
        List<Company> companies = companyService.getCompaniesByName(scanner.nextLine());
        if (companies.size() == 0)
            System.out.println("No companies found");
        else {
            for (Company c : companies) {
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
    private  void printComputerByID(Scanner scanner, ComputerService computerService) throws ServiceException
    {
        System.out.println("Enter id of computer : ");
        try {
            Long computerId = scanner.nextLong();
            Optional<Computer> optComp = computerService.getComputerById(computerId);
            if (optComp.isPresent()) {
                System.out.println(optComp.get());
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
    private  void createComputer(Scanner scanner, ComputerService computerService) throws ServiceException {
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
            CompanyDTO companyDTO = new CompanyDTO();
            try {
                companyDTO.setId(Long.parseUnsignedLong(companyIdStr));
            }catch (NumberFormatException e) {
                companyDTO = null;
            }
            Builder computerDTOBuilder = new Builder();
            computerDTOBuilder.withName(name)
            .withIntroduced(introducedStr)
            .withDiscontinued(discontinuedStr)
            .withCompany(companyDTO);
            computerService.createComputer(computerDTOMapper.toComputer(computerDTOBuilder.build()));

        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private  boolean updateAttribute(String attributeName, String varValue, Scanner scanner) {
        System.out.println("Current " + attributeName + " : " + varValue + ". Do you want do update ? (y/n)");
        String validation = scanner.next();
        scanner.nextLine();
        return validation.toLowerCase().equals("y");
    }

    private  void updateAttributes(Scanner scanner, ComputerService computerService, Long computerId) throws  ServiceException {
        Optional<Computer> optComputer = computerService.getComputerById(computerId);
        if (!optComputer.isPresent()) {
            System.out.println("No computer found with id " + computerId);
            return;
        }
        ComputerDTO computerDTO = computerDTOMapper.toDTO(optComputer.get());
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
        }else {
            companyIdStr = String.valueOf(computerDTO.getCompany().getId());
        }
        if (updateAttribute("company id", companyIdStr, scanner)) {
            System.out.println("Enter company id");
            companyIdStr = scanner.nextLine();
            companyDTO = computerDTO.getCompany();
            try {
                companyDTO.setId(Long.parseUnsignedLong(companyIdStr));
            }catch (NumberFormatException | NullPointerException e) {
                companyDTO = null;
            }
            computerDTO.setCompany(companyDTO);
        }

    }

    private  void updateComputer(Scanner scanner, ComputerService computerService) throws  ServiceException {
        System.out.println("Enter id of computer to update");
        try {
            Long computerId = scanner.nextLong();
            updateAttributes(scanner, computerService, computerId);
        } catch (InputMismatchException e) {
            System.out.println("Numbers only are accepted as id");
        }

    }

    private  void deleteComputer(Scanner scanner, ComputerService computerService) throws ServiceException{
        System.out.println("Enter id of computer : ");
        try {
            Long computerId = scanner.nextLong();
            computerService.deleteComputer(computerId);

        } catch (InputMismatchException e) {
            System.out.println("Only numbers are accepted as id");
        }
    }

    private  void deleteCompany(Scanner scanner, CompanyService companyService) throws ServiceException{
        System.out.println("Enter id of company to delete :");
        try {
            Long companyId = scanner.nextLong();
            companyService.delete(companyId);
        }catch(InputMismatchException e) {
            System.out.println("Only numbers are accepted as id");
        }
    }

    public  void startUI(Scanner scanner) throws ServiceException,  ValidatorException {
        while (true) {
            System.out.println("Computer database application\n" + "Select operation:\n" + "1. List computers\n"
                    + "2. List companies\n" + "3. Show computer details (by id)\n" + "4. Create a computer\n"
                    + "5. Update a computer\n" + "6. Delete a computer\n" + "7. Find company by name\n"
                    + "8. Delete company by id\n" + "9. Quit"
                    );


            int featureChoice = 0;
            try {
                featureChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                return;
            }
            switch (CLIActionEnum.values()[featureChoice]) {
            case LIST_COMPUTERS:
                printPagedList(scanner, new ComputerPage(computerService, 1, PAGE_SIZE));
                break;
            case LIST_COMPANIES:
                printPagedList(scanner, new CompanyPage(companyService, 1, PAGE_SIZE));
                break;
            case COMPUTER_DETAILS:
                printComputerByID(scanner, computerService);
                break;
            case CREATE_COMPUTER:
                createComputer(scanner, computerService);
                break;
            case UPDATE_COMPUTER:
                updateComputer(scanner, computerService);
                break;
            case DELETE_COMPUTER:
                deleteComputer(scanner, computerService);
                break;
            case COMPANY_BY_NAME:
                findCompanyByName(scanner, companyService);
                break;
            case DELETE_COMPANY:
                deleteCompany(scanner, companyService);
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