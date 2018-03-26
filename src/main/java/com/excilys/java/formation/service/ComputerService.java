package com.excilys.java.formation.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.ComputerDAOImpl;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.validator.CompanyValidator;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public enum ComputerService {

    INSTANCE;
    private static Logger logger = LoggerFactory.getLogger(ComputerService.class);
    private static final ComputerDAOImpl computerDAO = ComputerDAOImpl.INSTANCE;
    private static final ComputerValidator computerValidator = ComputerValidator.INSTANCE;
    private static final CompanyValidator companyValidator = CompanyValidator.INSTANCE;

    public List<Computer> getComputerList() throws ServiceException {
        try {
            return computerDAO.getAll();
        }catch(DAOException e) {
            logger.error("Exception in getComputerList()", e);
            throw new ServiceException(e);
        }
    }

    public List<Computer> getComputerList(int offset, int size) throws ServiceException {
        try {
            return computerDAO.get(offset, size);
        }catch(DAOException e) {
            logger.error("Exception in getComputerList({}, {})", offset, size, e);
            throw new ServiceException(e);
        }
    }

    public List<Computer> getComputerListByName(String name) throws ServiceException {
        try {
            return computerDAO.getByName(name);
        }catch(DAOException e) {
            logger.error("Exception in getComputerListByName({})", name, e);
            throw new ServiceException(e);
        }
    }

    public Optional<Computer> getComputerById(Long computerId) throws ServiceException {
        try {
            return computerDAO.getComputerById(computerId);
        }catch(DAOException e) {
            logger.error("Exception in getComptuerById({})", computerId, e);
            throw new ServiceException(e);
        }
    }

    public Optional<Computer> createComputer(Computer computer) throws ServiceException, ValidatorException {
        computerValidator.checkComputer(computer);
        try {
            Optional<Long> computerId = computerDAO.createComputer(computer);
            if (computerId.isPresent()) {
                computer.setId(computerId.get());
                return Optional.of(computer);
            }else {
                return Optional.empty();
            }
        } catch (DAOException e) {
            logger.error("Exception in createComputer({})", computer, e);
            throw new ServiceException(e);
        }
    }

    public boolean updateComputer(Computer computer) throws ServiceException, ValidatorException   {
        computerValidator.checkComputer(computer);
        try {
            return computerDAO.update(computer);
        }catch (DAOException e) {
            logger.error("Exception in updateComputer({})", computer, e);
            throw new ServiceException(e);
        }
    }

    public boolean deleteComputer(Long computerId) throws ServiceException {
        try {
            return computerDAO.delete(computerId);
        }catch(DAOException e) {
            logger.error("Exception in deleteComputer({})", computerId, e);
            throw new ServiceException(e);
        }
    }

    public int count() throws ServiceException {
        try {
            return computerDAO.count();
        }catch(DAOException e) {
            logger.error("Exception in count()", e);
            throw new ServiceException(e);
        }
    }

}
