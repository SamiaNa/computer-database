package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.NoComputerInResultSetException;

public enum ComputerMapper {
	
	INSTANCE; 
	
	public LocalDate toLocalDateOrNull(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}
	
	public List<Computer> createComputerListFromResultSet(ResultSet res) throws SQLException {
		List <Computer> computers = new ArrayList<>();
		while (res.next()) {
			computers.add(new Computer (res.getLong(1), 
						res.getString(2),
						toLocalDateOrNull(res.getDate(3)), 
						toLocalDateOrNull(res.getDate(4)),
						new Company(res.getLong(6), res.getString(7))));
		}
		return computers;
	}
	
	public Computer createComputerFromResultSet(ResultSet res, long id) throws SQLException, NoComputerInResultSetException {
		if (res.next()) {
			return new Computer (res.getLong(1),
						res.getString(2), 
						toLocalDateOrNull(res.getDate(3)), 
						toLocalDateOrNull(res.getDate(4)),
						new Company(res.getLong(6), res.getString(7)));}
		else {
			throw new NoComputerInResultSetException ("No computer found with id : "+id);
		}
	}

}
