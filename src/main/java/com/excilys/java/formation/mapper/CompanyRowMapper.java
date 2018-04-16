package com.excilys.java.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.java.formation.entities.Company;

public class CompanyRowMapper implements RowMapper<Company>{


    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Company(rs.getLong(1), rs.getString(2));
    }

}
