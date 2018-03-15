package com.excilys.java.formation.model.persistence;

import java.util.List;

import com.excilys.java.formation.entities.Computer;

import java.util.ArrayList;
import java.sql.*;

public class ComputerDAO {
	
	private static ComputerDAO computerDAO;
	
	
	public static ComputerDAO getDAO() {
		if (computerDAO == null) {
			computerDAO = new ComputerDAO();
		}
		return computerDAO;
	}
	
	/**
	 * Returns the list of all computers in the database
	 * @return ArrayList of Computer
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> getAll() throws ClassNotFoundException, SQLException{
		Connection connection = MySQLConnection.getConnection();
		ComputerMapper computerMapper = ComputerMapper.getMapper();
		Statement stmt = null;
		List<Computer> computers = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM computer");
			computers = computerMapper.createComputerListFromResultSet(res);
			connection.commit();			
		}catch(SQLException se) {		
			MySQLConnection.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return computers;
	}
		
	/**
	 * Returns a list of all computers between lines offset and offset + size
	 * @param offset from which to start selecting lines
	 * @param size the maximum number of lines to select
	 * @return an ArrayList of computers
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Computer> get(int offset, int size) throws ClassNotFoundException, SQLException{
		Connection connection = MySQLConnection.getConnection();
		ComputerMapper computerMapper = ComputerMapper.getMapper();
		PreparedStatement stmt = null;
		List<Computer> computers = new ArrayList<>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT * FROM computer LIMIT ?,?");
			stmt.setInt(1, offset);
			stmt.setInt(2, size);
			ResultSet res = stmt.executeQuery();
			computers = computerMapper.createComputerListFromResultSet(res);
			connection.commit();			
		}catch(SQLException se) {		
			MySQLConnection.printExceptionList(se);
			connection.rollback();		
		}finally {
			if (stmt != null) {
				stmt.close();
			}
			connection.close();
		}
		return computers;
	}
	
	/**
	 * Returns the computer with the specified id in the database
	 * @param id the primary key of the computer to find
	 * @return a Computer object
	 * @throws SQLException
	 * @throws NoComputerInResultSetException if there is no computer with the corresponding id
	 * @throws ClassNotFoundException
	 */
	
	public Computer getComputerById(long id) throws SQLException, NoComputerInResultSetException, ClassNotFoundException  {
		Connection connection = MySQLConnection.getConnection();
		ComputerMapper computerMapper = ComputerMapper.getMapper();
		PreparedStatement stmt = null;
		Computer c = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT * FROM computer WHERE id = ?");
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			c = computerMapper.createComputerFromResultSet(res, id);
			} catch(SQLException se) {
				MySQLConnection.printExceptionList(se);
				connection.rollback();
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return c;
	}

	private void setDateOrNull(Date d, PreparedStatement stmt, int position) throws SQLException {
		if (d == null) {
			stmt.setNull(position, java.sql.Types.DATE);
		}else {
			stmt.setDate(position, d);
		}
	}
	
	private void setBigIntOrNull(Long l, PreparedStatement stmt, int position) throws SQLException {
		if (l == null) {
			stmt.setNull(position, java.sql.Types.BIGINT);
		}else {
			stmt.setLong(position, l);
		}
	}
	
	public Long createComputer(Computer c) throws SQLException, ClassNotFoundException {
		Connection connection = MySQLConnection.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("INSERT INTO computer "+
					"(name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			System.out.println(c.getName()+" "+c.getCompanyId()+" "+c.getIntroduced()+" "+c.getDiscontinued());
			stmt.setString(1, c.getName());
			setBigIntOrNull(c.getCompanyId(), stmt, 2);
			setDateOrNull(c.getIntroduced(), stmt, 3);
			setDateOrNull(c.getDiscontinued(), stmt, 4);
			stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			res.next();
			long id = res.getLong(1);
			connection.commit();
			return id;		
		} catch(SQLException se) {
			connection.rollback();
			throw se;
		}finally {
			connection.close();
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public boolean checkComputerById(long id) throws SQLException, ClassNotFoundException  {
		Connection connection = MySQLConnection.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("SELECT * FROM computer WHERE id = ?");
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			connection.commit();
			return res.next();
			} catch(SQLException se) {
				MySQLConnection.printExceptionList(se);
				connection.rollback();
			}finally {
				connection.close();
				if (stmt != null) {
					stmt.close();
				}
			}
		return false;
	}
	
	
	
	public boolean update (Computer c) throws SQLException, ClassNotFoundException {
		Connection connection = MySQLConnection.getConnection();
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement("UPDATE computer "+
						"SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");
			stmt.setString(1, c.getName());
			setDateOrNull(c.getIntroduced(), stmt, 2);
			setDateOrNull(c.getDiscontinued(), stmt, 3);
			setBigIntOrNull(c.getCompanyId(), stmt, 4);
			stmt.setLong(5, c.getId());
			int res = stmt.executeUpdate();
			connection.commit();
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
	
	public boolean delete(long id) throws SQLException, ClassNotFoundException {
		Connection connection = MySQLConnection.getConnection();
		
		PreparedStatement stmt = null;
		try {
			connection.setAutoCommit(false);
			String query = "DELETE FROM computer WHERE id = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1,id);
			int res = stmt.executeUpdate();
			connection.commit();
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
	
	public int count() throws SQLException, ClassNotFoundException {
		PreparedStatement stmt = null;
		Connection connection = MySQLConnection.getConnection();
		int count = - 1;
		try {
			connection.setAutoCommit(false);
			String selectQuery = "SELECT count(id) FROM computer";
			stmt = connection.prepareStatement(selectQuery);
			ResultSet rSet = stmt.executeQuery();
			rSet.next();
			count = rSet.getInt(1);
			stmt.close();
			connection.commit();
			return count;
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		connection.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		}
	return -1;
	}
	
	
	
}
