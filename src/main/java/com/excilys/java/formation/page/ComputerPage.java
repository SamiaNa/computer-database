package com.excilys.java.formation.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;

public class ComputerPage extends Page{


    private List<Computer> elements;

    public ComputerPage() {
        this.pageNumber = 0;
        this.size = DEFAULT_SIZE;
        this.elements = new ArrayList<>();
    }

    public ComputerPage(int pageNumber, int size) {
        this.pageNumber = pageNumber;
        this.size = size;
        this.elements = new ArrayList<>();
    }

    public void updateList() throws DAOException, ConnectionException {
        this.elements = ComputerService.INSTANCE.getComputerList(pageNumber, size);
    }

    @Override
    public void getPage(int pageNumber, int pageSize) throws  ConnectionException, DAOException{
        this.count = ComputerService.INSTANCE.count();
        this.size = pageSize;
        this.pageNumber = super.offsetGetPage(pageNumber, count);
        updateList();
    }

    @Override
    public void nextPage() throws  ConnectionException, DAOException {
        this.count = ComputerService.INSTANCE.count();
        super.offsetNextPage(count);
        updateList();
    }

    @Override
    public void prevPage() throws  ConnectionException, DAOException {
        super.offsetPrevPage();
        updateList();

    }

    public List<Computer> getElements(){
        return this.elements;
    }





}
