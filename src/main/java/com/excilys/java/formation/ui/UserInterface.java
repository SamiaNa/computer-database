package com.excilys.java.formation.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.entities.Computer.StringToComputerBuilder;
import com.excilys.java.formation.entities.ComputerStringAttributes;
import com.excilys.java.formation.page.CompanyPage;
import com.excilys.java.formation.page.ComputerPage;
import com.excilys.java.formation.page.Page;
import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.persistence.NoComputerInResultSetException;
import com.excilys.java.formation.service.CompanyService;
import com.excilys.java.formation.service.ComputerService;
import com.excilys.java.formation.validator.ValidatorException;


public class UserInterface {

    private static final int PAGE_SIZE = 10;

    private static void printElements(Page page) {
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
    private static void printPagedList(Scanner scanner, Page page) throws ConnectionException, DAOException {
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

    private static void findCompanyByName(Scanner scanner) throws ConnectionException, DAOException {
        CompanyService companyService = CompanyService.INSTANCE;
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
     * @throws DAOException
     * @throws ConnectionException
     */
    private static void printComputerByID(Scanner scanner, ComputerService computerService)
            throws DAOException, ConnectionException {
        System.out.println("Enter id of computer : ");
        try {
            Long computerId = scanner.nextLong();
            Optional<Computer> optComp = computerService.getComputerById(computerId);
            if (optComp.isPresent()) {
                System.out.println(optComp.get());
            } else {
                System.out.println("No computer found with id : " + computerId);
            }
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Numbers only are accepted as id");
        }
    }

    /**
     * Creates a computer
     *
     * @param scanner
     * @param computerService
     * @throws DAOException
     * @throws ConnectionException
     * @throws ValidatorException
     */
    private static void createComputer(Scanner scanner, ComputerService computerService)
            throws DAOException, ConnectionException {
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
            StringToComputerBuilder builder = new StringToComputerBuilder();
            builder.setName(name).setIntroduced(introducedStr).setDiscontinued(discontinuedStr)
            .setCompany(companyIdStr);
            Optional<Computer> optComp = computerService.createComputer(builder.build());
            if (optComp.isPresent()) {
                System.out.println("Successful creation with id " + optComp.get().getId());
            } else {
                System.out.println("Constraint violation, make sure company exists in database");
            }
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean updateAttribute(String attributeName, String varValue, Scanner scanner) {
        System.out.println("Current " + attributeName + " : " + varValue + ". Do you want do update ? (y/n)");
        String validation = scanner.next();
        scanner.nextLine();
        return validation.toLowerCase().equals("y");
    }

    private static void updateAttributes(Scanner scanner, ComputerService computerService, Long computerId)
            throws ConnectionException, DAOException, NoComputerInResultSetException, ValidatorException {
        Optional<Computer> optComputer = computerService.getComputerById(computerId);
        if (!optComputer.isPresent()) {
            System.out.println("No computer found with id " + computerId);
            return;
        }
        ComputerStringAttributes computerStr = new ComputerStringAttributes(optComputer.get());
        if (updateAttribute("name", computerStr.getName(), scanner)) {
            System.out.println("Enter new name");
            computerStr.setName(scanner.nextLine());
        }
        if (updateAttribute("date of introduction", computerStr.getIntroduced(), scanner)) {
            System.out.println("Enter new date of introduction");
            computerStr.setIntroduced(scanner.nextLine());
        }
        if (updateAttribute("date of discontinuation", computerStr.getDiscontinued(), scanner)) {
            System.out.println("Enter new date of discontinuation");
            computerStr.setDiscontinued(scanner.nextLine());
        }
        if (updateAttribute("company id", computerStr.getCompanyId(), scanner)) {
            System.out.println("Enter company id");
            computerStr.setCompanyId(scanner.nextLine());
        }
        if (computerService.updateComputer(new StringToComputerBuilder().build(computerStr))) {
            System.out.println("Successful update");
        } else {
            System.out.println("Error : update not taken into account");
        }
    }

    private static void updateComputer(Scanner scanner, ComputerService computerService)
            throws DAOException, ConnectionException {
        System.out.println("Enter id of computer to update");
        try {
            Long computerId = scanner.nextLong();
            updateAttributes(scanner, computerService, computerId);
        } catch (NoComputerInResultSetException | ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Numbers only are accepted as id");
        }

    }

    private static void deleteComputer(Scanner scanner, ComputerService computerService)
            throws DAOException, ConnectionException {
        System.out.println("Enter id of computer : ");
        try {
            Long computerId = scanner.nextLong();
            boolean deleted = computerService.deleteComputer(computerId);
            if (deleted) {
                System.out.println("Successful deletion");
            } else {
                System.out.println("No computer found with id " + computerId);
            }
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Only numbers are excepted as id");
        }
    }

    public static void startUI(Scanner scanner) throws DAOException, ConnectionException, ValidatorException {
        while (true) {
            System.out.println("Computer database application\n" + "Select operation:\n" + "1. List computers\n"
                    + "2. List companies\n" + "3. Show computer details (by id)\n" + "4. Create a computer\n"
                    + "5. Update a computer\n" + "6. Delete a computer\n" + "7. Find company by name\n" + "8. Quit");

            ComputerService computerService = ComputerService.INSTANCE;
            int featureChoice = 0;
            try {
                featureChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
            }
            switch (CLIActionEnum.values()[featureChoice]) {
            case LIST_COMPUTERS:
                printPagedList(scanner, new ComputerPage(1, PAGE_SIZE));
                break;
            case LIST_COMPANIES:
                printPagedList(scanner, new CompanyPage(1, PAGE_SIZE));
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
                findCompanyByName(scanner);
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

    public static void main(String[] args) throws ConnectionException, DAOException, ValidatorException {
        Scanner scanner = new Scanner(System.in);
        startUI(scanner);
        scanner.close();

    }

}
