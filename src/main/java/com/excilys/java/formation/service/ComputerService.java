package com.excilys.java.formation.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.CompanyDAOJdbc;
import com.excilys.java.formation.persistence.implementations.ComputerDAOJdbc;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

@Service
@EnableTransactionManagement
public class ComputerService {

    private static Logger logger = LoggerFactory.getLogger(ComputerService.class);
    private ComputerDAOJdbc computerDAO;
    private CompanyDAOJdbc companyDAO;
    private ComputerValidator computerValidator;

    @Autowired
    public ComputerService(ComputerDAOJdbc computerDAO, CompanyDAOJdbc companyDAO,
            ComputerValidator computerValidator) {
        this.computerDAO = computerDAO;
        this.companyDAO = companyDAO;
        this.computerValidator = computerValidator;
    }

    public List<Computer> getComputerList() {
        return computerDAO.getAll();
    }

    public List<Computer> getComputerList(int offset, int size) {
        return computerDAO.get(offset, size);
    }

    public List<Computer> getByOrder(String orderBy, String by, int offset, int size) throws ValidatorException {
        return computerDAO.getByOrder(orderBy, by, offset, size);
    }

    public List<Computer> getByOrder(String orderBy, String by, String name, int offset, int size)
            throws ValidatorException {
        computerValidator.checkName(name);
        return computerDAO.getByOrder(orderBy, by, name, offset, size);
    }

    public List<Computer> getByName(String name, int offset, int size) throws ValidatorException {
        computerValidator.checkName(name);
        return computerDAO.getByName(name, offset, size);
    }

    public Optional<Computer> getComputerById(Long computerId) throws ServiceException {
        try {
            return computerDAO.getComputerById(computerId);
        } catch (DAOException e) {
            logger.error("Exception in getComptuerById({})", computerId, e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public long createComputer(Computer computer) throws ServiceException, ValidatorException {
        try {
            computerValidator.checkComputer(companyDAO, computer);
            return computerDAO.createComputer(computer);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateComputer(Computer computer) throws ValidatorException {
        logger.info("Update Computer, {}", computer);
        computerValidator.checkComputer(companyDAO, computer);
        logger.info("UPDATE Computer, after check");
        computerDAO.update(computer);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteComputer(Long computerId) {
        computerDAO.delete(computerId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteComputer(List<Long> computerIds) {
        computerDAO.delete(computerIds);
    }

    public int count() throws ServiceException {
        return computerDAO.count();
    }

    public int count(String name) throws ValidatorException {
        computerValidator.checkName(name);
        return computerDAO.count(name);
    }

}