package com.excilys.java.formation.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyMapper;

public enum CompanyDAOImpl implements CompanyDAO {

	INSTANCE;

	private final String SELECT = "SELECT id, name FROM company;";
	private final String SELECT_LIMIT = "SELECT id, name FROM company LIMIT ?,?;";
	private final String SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ?;";
	private final String SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?;";
	private final String COUNT = "SELECT COUNT(id) FROM company;";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Company> getAll() throws SQLException, ClassNotFoundException{
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		List<Company> companies = new ArrayList<>();
		try (Connection connection = ConnectionManager.INSTANCE.open();
				PreparedStatement stmt = connection.prepareStatement(SELECT);){
			ResultSet res = stmt.executeQuery();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {
			for (Throwable e : se) {
				logger.error(e.toString());
			}
		}
		return companies;
	}

	@Override
	public List<Company> get(int offset, int size) throws SQLException, ClassNotFoundException{
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		List<Company> companies = new ArrayList<>();
		try (Connection connection = ConnectionManager.INSTANCE.open();
				PreparedStatement stmt = connection.prepareStatement(SELECT_LIMIT);){
			stmt.setInt(1, offset);
			stmt.setInt(2, size);
			ResultSet res = stmt.executeQuery();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {
			for (Throwable e : se) {
				logger.error(e.toString());
			}
		}
		return companies;
	}


	@Override
	public List<Company> getByName(String name) throws SQLException, ClassNotFoundException{
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		List<Company> companies = new ArrayList<>();
		try (Connection connection = ConnectionManager.INSTANCE.open();
				PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME);){
			stmt.setString(1, "%"+name+"%");
			ResultSet res = stmt.executeQuery();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {
			for (Throwable e : se) {
				logger.error(e.toString());
			}
		}
		return companies;
	}


	@Override
	public boolean checkCompanyById(long id) throws SQLException, ClassNotFoundException  {
		try (Connection connection = ConnectionManager.INSTANCE.open();
				PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID);){
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			return res.next();
		} catch(SQLException se) {
			for (Throwable e : se) {
				logger.error(e.toString());
			}
		}
		return false;
	}


	@Override
	public int count() throws ClassNotFoundException, SQLException {
		try (Connection connection = ConnectionManager.INSTANCE.open();
				PreparedStatement stmt = connection.prepareStatement(COUNT);){
			ResultSet res = stmt.executeQuery();
			res.next();
			return res.getInt(1);
		} catch(SQLException se) {
			for (Throwable e : se) {
				logger.error(e.toString());
			}
		}
		return -1;
	}
}

