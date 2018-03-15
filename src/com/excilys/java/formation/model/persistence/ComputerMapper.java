package com.excilys.java.formation.model.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.excilys.java.formation.entities.Computer;

public class ComputerMapper {
	
	private static ComputerMapper computerMapper;
	
	private ComputerMapper () {

	}
	
	public static ComputerMapper getMapper() {
		if (computerMapper == null) {
			computerMapper = new ComputerMapper();
		}
		return computerMapper;
	}
	
	public List<Computer> createComputerListFromResultSet(ResultSet res) throws SQLException {
		List <Computer> computers = new ArrayList<>();
		while (res.next()) {
			computers.add(new Computer (res.getLong(1), res.getString(2), 
						res.getDate(3), res.getDate(4), res.getLong(5)));
		}
		return computers;
	}
	
	public Computer createComputerFromResultSet(ResultSet res, long id) throws SQLException, NoComputerInResultSetException {
		if (res.next()) {
			return new Computer (res.getLong(1), res.getString(2), 
				res.getDate(3), res.getDate(4), res.getLong(5));}
		else {
			throw new NoComputerInResultSetException ("No computer found with id : "+id);
		}
	}

}
