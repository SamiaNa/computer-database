package com.excilys.java.formation.persistence;

import java.util.List;
import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.mapper.CompanyMapper;
import java.util.ArrayList;
import java.sql.*;

public enum CompanyDAOImpl implements CompanyDAO {
	
	INSTANCE;
	
	private final String SELECT = "SELECT * FROM company;";
	private final String SELECT_LIMIT = "SELECT * FROM company LIMIT ?,?;";
	private final String SELECT_BY_NAME = "SELECT * FROM company WHERE name LIKE ?;";
	private final String SELECT_BY_ID = "SELECT * FROM company WHERE id = ?;";
	private final String COUNT = "SELECT COUNT(id) FROM company;";


	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.CompanyDAO#getAll()
	 */
	@Override
	public List<Company> getAll() throws SQLException, ClassNotFoundException{
		Connection connection = ConnectionManager.getConnection();
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		Statement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery(SELECT);
			connection.commit();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {		
			ConnectionManager.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return companies;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.CompanyDAO#get(int, int)
	 */
	@Override
	public List<Company> get(int offset, int size) throws SQLException, ClassNotFoundException{
		Connection connection = ConnectionManager.getConnection();
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		PreparedStatement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(SELECT_LIMIT);
			stmt.setInt(1, offset);
			stmt.setInt(2, size);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {		
			ConnectionManager.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return companies;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.CompanyDAO#getByName(java.lang.String)
	 */
	@Override
	public List<Company> getByName(String name) throws SQLException, ClassNotFoundException{
		Connection connection = ConnectionManager.getConnection();
		CompanyMapper companyMapper = CompanyMapper.INSTANCE;
		PreparedStatement stmt = null;
		List<Company> companies = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(SELECT_BY_NAME);
			stmt.setString(1, "%"+name+"%");
			ResultSet res = stmt.executeQuery();
			connection.commit();
			companies = companyMapper.createCompanyListFromResultSet(res);
		}catch(SQLException se) {		
			ConnectionManager.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return companies;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.CompanyDAO#checkCompanyById(long)
	 */
	@Override
	public boolean checkCompanyById(long id) throws SQLException, ClassNotFoundException  {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(SELECT_BY_ID);
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			return res.next();
			} catch(SQLException se) {
				ConnectionManager.printExceptionList(se);
				connection.rollback();
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.CompanyDAO#count()
	 */
	@Override
	public int count() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(COUNT);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			res.next();
			return res.getInt(1);
			} catch(SQLException se) {
				ConnectionManager.printExceptionList(se);
				connection.rollback();
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return -1;
	}
}

