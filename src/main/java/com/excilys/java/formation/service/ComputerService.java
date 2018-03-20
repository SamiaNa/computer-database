package com.excilys.java.formation.service;

import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAOImpl;
import com.excilys.java.formation.persistence.DAOConstraintException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public enum ComputerService {

    INSTANCE;

    public List<Computer> getComputerList() throws DAOException, ClassNotFoundException {
        return ComputerDAOImpl.INSTANCE.getAll();
    }

    public List<Computer> getComputerList(int offset, int size) throws ClassNotFoundException, DAOException {
        return ComputerDAOImpl.INSTANCE.get(offset, size);
    }

    public Optional<Computer> getComputerById(Long computerId)
            throws DAOException, ClassNotFoundException, ValidatorException {
        return ComputerDAOImpl.INSTANCE.getComputerById(computerId);
    }

    public Optional<Computer> createComputer(Computer computer)
            throws ValidatorException, ClassNotFoundException, DAOException {
        ComputerValidator.INSTANCE.checkDates(computer);
        try {
            computer.setId(ComputerDAOImpl.INSTANCE.createComputer(computer));
        } catch (DAOConstraintException e) {
            return Optional.empty();
        }
        return Optional.of(computer);
    }

    public boolean updateComputer(Computer computer) throws ClassNotFoundException, DAOException, ValidatorException {
        return ComputerDAOImpl.INSTANCE.update(computer);
    }

    public boolean deleteComputer(Long computerId) throws ClassNotFoundException, DAOException, ValidatorException {
        return ComputerDAOImpl.INSTANCE.delete(computerId);
    }

    public int count() throws ClassNotFoundException, DAOException {
        return ComputerDAOImpl.INSTANCE.count();
    }

}
