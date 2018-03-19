package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.ComputerDAOImpl;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;


public enum ComputerService {

	INSTANCE;

	public List<Computer> getComputerList() throws SQLException, ClassNotFoundException {
		return ComputerDAOImpl.INSTANCE.getAll();
	}

	public List<Computer> getComputerList(int offset, int size) throws ClassNotFoundException, SQLException {
		return ComputerDAOImpl.INSTANCE.get(offset, size);
	}

	public Optional<Computer> getComputerById(Long computerId) throws SQLException, ClassNotFoundException, ValidatorException{
		return ComputerDAOImpl.INSTANCE.getComputerById(computerId);
	}

	public Computer createComputer(Computer computer) throws ValidatorException, ClassNotFoundException, SQLException {
		ComputerValidator.INSTANCE.checkDates(computer);
		computer.setId(ComputerDAOImpl.INSTANCE.createComputer(computer));
		return computer;
	}

	public boolean updateComputer(Computer computer) throws ClassNotFoundException, SQLException, ValidatorException {
		return ComputerDAOImpl.INSTANCE.update(computer);
	}

	public boolean deleteComputer(Long computerId) throws ClassNotFoundException, SQLException, ValidatorException {
		return ComputerDAOImpl.INSTANCE.delete(computerId);
	}

	public int count () throws ClassNotFoundException, SQLException {
		return ComputerDAOImpl.INSTANCE.count();
	}

}
