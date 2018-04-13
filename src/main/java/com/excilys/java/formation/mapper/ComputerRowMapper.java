package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

public class ComputerRowMapper implements RowMapper<Computer>{

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company(rs.getLong(5), rs.getString(6));
        if (rs.wasNull()) {
            company = null;
        }
        return new Computer.ComputerBuilder()
                .withId(rs.getLong(1))
                .withName(rs.getString(2))
                .withIntroduced(ComputerMapper.INSTANCE.stringToLocalDateOrNull(rs.getDate(3)))
                .withDiscontinued(ComputerMapper.INSTANCE.stringToLocalDateOrNull(rs.getDate(4)))
                .withCompany(company)
                .build();
    }

}
