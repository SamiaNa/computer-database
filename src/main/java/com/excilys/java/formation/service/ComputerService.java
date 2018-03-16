package com.excilys.java.formation.service;

import java.sql.SQLException;
import java.util.List;
import com.excilys.java.formation.persistence.*;
import com.excilys.java.formation.validator.*;
import com.excilys.java.formation.entities.*;
import com.excilys.java.formation.entities.Computer.StringToComputerBuilder;


public enum ComputerService {

	INSTANCE;
	
	public List<Computer> getComputerList() throws SQLException, ClassNotFoundException {
		return ComputerDAOImpl.INSTANCE.getAll();
	}
	
	public List<Computer> getComputerList(int offset, int size) throws ClassNotFoundException, SQLException {
		return ComputerDAOImpl.INSTANCE.get(offset, size);
	}
	
	public Computer getComputerById(String strId) throws SQLException, ClassNotFoundException, 
											NoComputerInResultSetException, ValidatorException{
		Long id = Validator.getLongPrimId(strId);
		return ComputerDAOImpl.INSTANCE.getComputerById(id);
	}
	
	
	public Computer createComputer(Computer computer) throws ValidatorException, ClassNotFoundException, SQLException {
		ComputerValidator.INSTANCE.checkDates(computer);
		computer.setId(ComputerDAOImpl.INSTANCE.createComputer(computer));
		return computer;		
	}
	
	public boolean updateComputer(ComputerStringAttributes compStr) throws ClassNotFoundException, SQLException, ValidatorException {
		StringToComputerBuilder computerBuilder = new StringToComputerBuilder();
		computerBuilder.build(compStr);
		return ComputerDAOImpl.INSTANCE.update(computerBuilder.build(compStr));
	}

	public boolean deleteComputer(String strId) throws ClassNotFoundException, SQLException, ValidatorException {
		ComputerDAO computerDAO = ComputerDAOImpl.INSTANCE;
		Long id;
		id = Validator.getLongPrimId(strId);
		return computerDAO.delete(id);
	}
	
	public int count () throws ClassNotFoundException, SQLException {
		return ComputerDAOImpl.INSTANCE.count();
	}
	
}
