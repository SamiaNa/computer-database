package com.excilys.java.formation.service;

import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.ComputerDAOImpl;
import com.excilys.java.formation.persistence.implementations.ConnectionException;
import com.excilys.java.formation.persistence.implementations.DAOConstraintException;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public enum ComputerService {

    INSTANCE;

    public List<Computer> getComputerList() throws DAOException, ConnectionException {
        return ComputerDAOImpl.INSTANCE.getAll();
    }

    public List<Computer> getComputerList(int offset, int size) throws ConnectionException, DAOException {
        return ComputerDAOImpl.INSTANCE.get(offset, size);
    }

    public List<Computer> getComputerListByName(String name) throws ConnectionException, DAOException{
        return ComputerDAOImpl.INSTANCE.getByName(name);
    }
    public Optional<Computer> getComputerById(Long computerId)
            throws DAOException, ConnectionException {
        return ComputerDAOImpl.INSTANCE.getComputerById(computerId);
    }

    public Optional<Computer> createComputer(Computer computer)
            throws ValidatorException, ConnectionException, DAOException {
        ComputerValidator.INSTANCE.checkDates(computer);
        try {
            computer.setId(ComputerDAOImpl.INSTANCE.createComputer(computer));
        } catch (DAOConstraintException e) {
            return Optional.empty();
        }
        return Optional.of(computer);
    }

    public boolean updateComputer(Computer computer) throws ConnectionException, DAOException {
        return ComputerDAOImpl.INSTANCE.update(computer);
    }

    public boolean deleteComputer(Long computerId) throws ConnectionException, DAOException {
        return ComputerDAOImpl.INSTANCE.delete(computerId);
    }

    public int count() throws ConnectionException, DAOException {
        return ComputerDAOImpl.INSTANCE.count();
    }

}
