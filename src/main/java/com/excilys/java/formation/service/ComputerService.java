package com.excilys.java.formation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.implementations.CompanyDAOImpl;
import com.excilys.java.formation.persistence.implementations.ComputerDAOImpl;
import com.excilys.java.formation.persistence.implementations.DAOException;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

@Service
@EnableTransactionManagement
public class ComputerService {

    private static Logger logger = LoggerFactory.getLogger(ComputerService.class);
    @Autowired
    private ComputerDAOImpl computerDAO ;

    @Autowired
    private CompanyDAOImpl companyDAO;

    private ComputerValidator computerValidator = ComputerValidator.INSTANCE;

    public List<Computer> getComputerList() throws ServiceException {
        try {
            return computerDAO.getAll();
        } catch (DAOException e) {
            logger.error("Exception in getComputerList()", e);
            throw new ServiceException(e);
        }
    }

    public List<Computer> getComputerList(int offset, int size) throws ServiceException {
        try {
            return computerDAO.get(offset, size);
        } catch (DAOException e) {
            logger.error("Exception in getComputerList({}, {})", offset, size, e);
            throw new ServiceException(e);
        }
    }

    public List<Computer> getByOrder(String orderBy, String by, int offset, int size) throws ServiceException, ValidatorException {
        try {
            return computerDAO.getByOrder(orderBy, by, offset, size);
        } catch (DAOException e) {
            logger.error("Exception in getByOrder({}, {})", orderBy, by,  e);
            throw new ServiceException(e);
        }
    }

    public List<Computer> getByOrder(String orderBy, String by, String name, int offset, int size) throws ServiceException, ValidatorException {
        try {
            ComputerValidator.INSTANCE.checkName(name);
            return computerDAO.getByOrder(orderBy, by, name, offset, size);
        } catch (DAOException e) {
            logger.error("Exception in getByOrder({}, {}, {})", orderBy, by, name, e);
            throw new ServiceException(e);
        }
    }
    public List<Computer> getByName(String name, int offset, int size) throws ServiceException {
        try {
            ComputerValidator.INSTANCE.checkName(name);
            return computerDAO.getByName(name, offset, size);
        } catch (DAOException e) {
            logger.error("Exception in getComputerListByName({})", name, e);
            throw new ServiceException(e);
        }catch (ValidatorException e) {
            logger.error("Name validation error {} ", name, e);
            return new ArrayList<>();
        }
    }

    public Optional<Computer> getComputerById(Long computerId) throws ServiceException {
        try {
            return computerDAO.getComputerById(computerId);
        } catch (DAOException e) {
            logger.error("Exception in getComptuerById({})", computerId, e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public Optional<Computer> createComputer(Computer computer) throws ServiceException, ValidatorException {
        computerValidator.checkComputer(companyDAO, computer);
        try {
            Optional<Long> computerId = computerDAO.createComputer(computer);
            if (computerId.isPresent()) {
                computer.setId(computerId.get());
                return Optional.of(computer);
            } else {
                return Optional.empty();
            }
        } catch (DAOException e) {
            logger.error("Exception in createComputer({})", computer, e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void updateComputer(Computer computer) throws ServiceException, ValidatorException {
        computerValidator.checkComputer(companyDAO, computer);
        try {
            computerDAO.update(computer);
        } catch (DAOException e) {
            logger.error("Exception in updateComputer({})", computer, e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteComputer(Long computerId) throws ServiceException {
        try {
            computerDAO.delete(computerId);
        } catch (DAOException e) {
            logger.error("Exception in deleteComputer({})", computerId, e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteComputer(List<Long> computerIds) throws ServiceException {
        try {
            computerDAO.delete(computerIds);
        } catch (DAOException e) {
            logger.error("Exception in deleteComputer({})", computerIds);
            throw new ServiceException(e);
        }

    }

    public int count() throws ServiceException {
        try {
            return computerDAO.count();
        } catch (DAOException e) {
            logger.error("Exception in count()", e);
            throw new ServiceException(e);
        }
    }

    public int count(String name) throws ServiceException {
        try {
            ComputerValidator.INSTANCE.checkName(name);
            return computerDAO.count(name);
        } catch (DAOException e) {
            logger.error("Exception in count()", e);
            throw new ServiceException(e);
        } catch (ValidatorException e) {
            logger.error("Name validation error {} ", name, e);
            return 0;
        }
    }

}