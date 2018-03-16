package com.excilys.java.formation.persistence;

import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.mapper.ComputerMapper;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;

public enum ComputerDAOImpl implements ComputerDAO {
	
	INSTANCE;
	
	private static String SELECT_ALL_JOIN = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id;";
	private static String SELECT_LIMIT = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ?,?;";
	private static String SELECT_BY_ID_JOIN = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? ;";
	private static String INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?);";
	private static String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static String DELETE = "DELETE FROM computer WHERE id = ?;";
	private static String COUNT = "SELECT COUNT(id) FROM computer";
	
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.ComputerDAO#getAll()
	 */
	@Override
	public List<Computer> getAll() throws ClassNotFoundException, SQLException{
		Connection connection = ConnectionManager.open();
		ComputerMapper computerMapper = ComputerMapper.INSTANCE;
		PreparedStatement stmt = null;
		List<Computer> computers = new ArrayList<>();
		try {
			stmt = connection.prepareStatement(SELECT_ALL_JOIN);
			ResultSet res = stmt.executeQuery();
			computers = computerMapper.createComputerListFromResultSet(res);
		}catch(SQLException se) {		
			ConnectionManager.printExceptionList(se);	
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return computers;
	}
		
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.ComputerDAO#get(int, int)
	 */
	@Override
	public List<Computer> get(int offset, int size) throws ClassNotFoundException, SQLException{
		Connection connection = ConnectionManager.open();
		ComputerMapper computerMapper = ComputerMapper.INSTANCE;
		PreparedStatement stmt = null;
		List<Computer> computers = new ArrayList<>();
		try {
			stmt = connection.prepareStatement(SELECT_LIMIT);
			stmt.setInt(1, offset);
			stmt.setInt(2, size);
			ResultSet res = stmt.executeQuery();
			computers = computerMapper.createComputerListFromResultSet(res);
		}catch(SQLException se) {		
			ConnectionManager.printExceptionList(se);
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return computers;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.ComputerDAO#getComputerById(long)
	 */
	
	@Override
	public Computer getComputerById(long id) throws SQLException, NoComputerInResultSetException, ClassNotFoundException  {
		Connection connection = ConnectionManager.open();
		ComputerMapper computerMapper = ComputerMapper.INSTANCE;
		PreparedStatement stmt = null;
		Computer c = null;
		try {
			stmt = connection.prepareStatement(SELECT_BY_ID_JOIN);
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			c = computerMapper.createComputerFromResultSet(res, id);
			} catch(SQLException se) {
				ConnectionManager.printExceptionList(se);
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return c;
	}

	private void setDateOrNull(LocalDate d, PreparedStatement stmt, int position) throws SQLException {
		if (d == null) {
			stmt.setNull(position, java.sql.Types.DATE);
		}else {
			stmt.setDate(position, Date.valueOf(d));
		}
	}
	
	
	private void setCompanyIdOrNull(Company c, PreparedStatement stmt, int position) throws SQLException {
		if (c == null) {
			stmt.setNull(position, java.sql.Types.BIGINT);
		}else {
			stmt.setLong(position, c.getId());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.ComputerDAO#createComputer(com.excilys.java.formation.entities.Computer)
	 */
	@Override
	public Long createComputer(Computer c) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectionManager.open();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, c.getName());
			setCompanyIdOrNull(c.getCompany(), stmt, 2);
			setDateOrNull(c.getIntroduced(), stmt, 3);
			setDateOrNull(c.getDiscontinued(), stmt, 4);
			stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			res.next();
			long id = res.getLong(1);
			return id;		
		} catch(SQLException se) {
			throw se;
		}finally {
			connection.close();
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	

	
	
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.ComputerDAO#update(com.excilys.java.formation.entities.Computer)
	 */
	@Override
	public boolean update (Computer c) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectionManager.open();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(UPDATE);
			stmt.setString(1, c.getName());
			setDateOrNull(c.getIntroduced(), stmt, 2);
			setDateOrNull(c.getDiscontinued(), stmt, 3);
			setCompanyIdOrNull(c.getCompany(), stmt, 4);
			stmt.setLong(5, c.getId());
			int res = stmt.executeUpdate();
			return res == 1;
		} catch(SQLException se) {
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
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
	 * @see com.excilys.java.formation.persistence.ComputerDAO#delete(long)
	 */
	@Override
	public boolean delete(long id) throws SQLException, ClassNotFoundException {
		Connection connection = ConnectionManager.open();
		
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(DELETE);
			stmt.setLong(1,id);
			int res = stmt.executeUpdate();
			return res == 1;
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		
		}finally {
			connection.close();
			if (stmt != null) {
				stmt.close();
			}
		
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.java.formation.persistence.ComputerDAO#count()
	 */
	@Override
	public int count() throws SQLException, ClassNotFoundException {
		PreparedStatement stmt = null;
		Connection connection = ConnectionManager.open();
		int count = - 1;
		try {
			stmt = connection.prepareStatement(COUNT);
			ResultSet rSet = stmt.executeQuery();
			rSet.next();
			count = rSet.getInt(1);
			stmt.close();
			return count;
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		
		}finally {
			connection.close();
			if (stmt != null) {
				stmt.close();
			}
		}
	return -1;
	}
	
	
	
}
