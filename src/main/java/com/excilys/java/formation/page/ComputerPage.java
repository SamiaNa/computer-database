package com.excilys.java.formation.page;

import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;

public class ComputerPage extends Page {

    private static volatile ComputerPage instance;

    private ComputerPage() {

    }

    public static synchronized ComputerPage getPage() {
        if (instance == null) {
            instance = new ComputerPage();
        }
        return instance;
    }


    public List<Computer> getPage(int pageNumber, int pageSize) throws ClassNotFoundException, DAOException {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        int offset = super.getOffset(pageNumber, pageSize);
        return ComputerService.INSTANCE.getComputerList(offset, pageSize);
    }

    @Override
    public void printPage(int pageNumber, int pageSize) throws ClassNotFoundException, DAOException {
        List<Computer> computers = getPage(pageNumber, pageSize);
        for (Computer c : computers) {
            System.out.println(c);
        }
    }

}
