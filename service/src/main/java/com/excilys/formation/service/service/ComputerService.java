package com.excilys.formation.service.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.service.validator.ComputerValidator;
import com.excilys.formation.service.validator.ValidatorException;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.persistence.dao.CompanyDAOJdbc;
import com.excilys.formation.persistence.dao.ComputerDAOJdbc;
import com.excilys.formation.persistence.daoexceptions.DAOException;

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

	public List<Computer> getComputerList(long offset, long size) {
		return computerDAO.get(offset, size);
	}

	public List<Computer> getByOrder(String orderBy, String by, long offset, long size) throws DAOException {
		return computerDAO.getByOrder(orderBy, by, offset, size);
	}

	public List<Computer> getByOrder(String orderBy, String by, String name, long offset, long size)
			throws DAOException {
		return computerDAO.getByOrder(orderBy, by, name, offset, size);
	}

	public List<Computer> getByName(String name, long offset, long size) {
		return computerDAO.getByName(name, offset, size);
	}

	public Optional<Computer> getComputerById(Long computerId) {
		return computerDAO.getComputerById(computerId);

	}

	@Transactional(rollbackFor = Exception.class)
	public long createComputer(Computer computer) throws ValidatorException {
		computerValidator.checkComputer(companyDAO, computer);
		computerValidator.checkDates(computer);
		return computerDAO.createComputer(computer);

	}

	@Transactional(rollbackFor = Exception.class)
	public void updateComputer(Computer computer) throws ValidatorException {
		logger.info("Update Computer, {}", computer);
		computerValidator.checkComputer(companyDAO, computer);
		computerValidator.checkDates(computer);
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

	public long count()   {
		return computerDAO.count();
	}

	public long count(String name)  {
		return computerDAO.count(name);
	}

}