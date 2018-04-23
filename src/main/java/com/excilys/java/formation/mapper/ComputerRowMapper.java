package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;

@Component
public class ComputerRowMapper implements RowMapper<Computer>{

    @Autowired
    private ComputerMapper computerMapper;

    private Logger logger = LoggerFactory.getLogger("ComputerRowMapper.class");
    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company(rs.getLong(5), rs.getString(6));
        if (rs.wasNull()) {
            company = null;
        }
        return new Computer.ComputerBuilder()
                .withId(rs.getLong(1))
                .withName(rs.getString(2))
                .withIntroduced(sqlDateToLocalDateOrNull(rs.getDate(3)))
                .withDiscontinued(sqlDateToLocalDateOrNull(rs.getDate(4)))
                .withCompany(company)
                .build();
    }

    public LocalDate sqlDateToLocalDateOrNull(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }

}
