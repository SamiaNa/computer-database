package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

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
			computers.add(new Computer(res.getLong(1),
					res.getString(2),
					toLocalDateOrNull(res.getDate(3)),
					toLocalDateOrNull(res.getDate(4)),
					new Company(res.getLong(5), res.getString(6))));
		}
		return computers;
	}

	public Optional<Computer> createComputerFromResultSet(ResultSet res, long id) throws SQLException {
		if (res.next()) {
			Computer c = new Computer(res.getLong(1),
					res.getString(2),
					toLocalDateOrNull(res.getDate(3)),
					toLocalDateOrNull(res.getDate(4)), null);
			Company company = new Company(res.getLong(5), res.getString(6));
			if (res.wasNull()) company = null;
			c.setCompany(company);
			return Optional.of(c);
		}
		else {
			return Optional.ofNullable(null);
		}
	}

}
