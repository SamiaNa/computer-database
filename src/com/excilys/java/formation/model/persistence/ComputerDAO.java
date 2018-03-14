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
	
	public boolean createComputer(Computer c) throws SQLException, ClassNotFoundException {
		Connection connection = MySQLConnection.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("INSERT INTO computer "+
					"(name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)");
			stmt.setString(1, c.getName());
			setBigIntOrNull(c.getCompanyId(), stmt, 2);
			setDateOrNull(c.getIntroduced(), stmt, 3);
			setDateOrNull(c.getDiscontinued(), stmt, 4);
			int res = stmt.executeUpdate();
			connection.commit();
			return res == 1;		
		} catch(SQLException se) {
			connection.rollback();
			throw se;
		}finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
/*
	public List<Computer> get(int offset, int size) throws SQLException {
		PreparedStatement stmt = null;
		List<Computer> computers = new ArrayList<>();
		try {
			conn.setAutoCommit(false);
			String query = "SELECT * FROM computer LIMIT ?,? ";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, offset);
			stmt.setInt(2, size);
			ResultSet res = stmt.executeQuery();
			conn.commit();
			while (res.next()) {
				computers.add(new Computer (res.getLong(1), res.getString(2), res.getDate(3), res.getDate(4), res.getLong(5)));
			}
					
		}catch(SQLException se) {
			
			for (Throwable e : se) {
				System.out.println("Problem : "+e);	
			}
			conn.rollback();
			
		}finally {
			
			if (stmt != null) {
				stmt.close();
			}
			
		}
		return computers;

	}
	*/
/*

	public void createComputer(Computer c) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			if (c.getCompany_id() != null) {
				String selectQuery = "SELECT id FROM company WHERE id = ?";
				stmt = conn.prepareStatement(selectQuery);
				stmt.setLong(1, c.getCompany_id());
				ResultSet rSet = stmt.executeQuery();
				if (!rSet.next()) {
					System.out.println("No existing company with id "+c.getId());
					return;
				}
				stmt.close();
			}
			String insertQuery = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)";
			stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, c.getName());
			if (c.getIntroduced() == null) {
				stmt.setNull(3, java.sql.Types.DATE);
			}else {
				stmt.setDate(3, java.sql.Date.valueOf(c.getIntroduced().toString()));
			}

			if (c.getDiscontinued()== null) {
				stmt.setNull(4, java.sql.Types.DATE);
			}else {
				stmt.setDate(4, java.sql.Date.valueOf(c.getDiscontinued().toString()));
			}
			if (c.getCompany_id() == null) {
				stmt.setNull(2, java.sql.Types.BIGINT);
			}else {
				stmt.setLong(2, c.getCompany_id());
			}
			int res = stmt.executeUpdate();
			conn.commit();
	
			if (res == 1) {
				System.out.println("Successful creation");
			}else {
				System.out.println("Creation failed");
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public void delete(long id) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "DELETE FROM computer WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setLong(1,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful deletion");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public void updateName (long id, String name) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET name = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setLong(2,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public void updateIntroduced (long id, Date t) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET introduced = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			if (t == null) {
				stmt.setNull(1, java.sql.Types.DATE);
			}else {
				stmt.setDate(1, java.sql.Date.valueOf(t.toString()));
			}
			stmt.setLong(2, id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	public void updateDiscontinued (long id, Date t) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
			String query = "UPDATE computer SET discontinued = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			if (t == null) {
				stmt.setNull(1, java.sql.Types.DATE);
			}else {
				stmt.setDate(1, java.sql.Date.valueOf(t.toString()));
			}
			stmt.setLong(2,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	public void updateCompanyID (long id, long companyId) throws SQLException {
		PreparedStatement stmt = null;
		try {
			
			conn.setAutoCommit(false);
				String selectQuery = "SELECT id FROM company WHERE id = ?";
				stmt = conn.prepareStatement(selectQuery);
				stmt.setLong(1, companyId);
				ResultSet rSet = stmt.executeQuery();
				if (!rSet.next()) {
					System.out.println("No existing company with id "+companyId);
					System.out.println("No modification made to company_id");
					return;	
				}
				stmt.close();
			String query = "UPDATE computer SET company_id = ? WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, companyId);
			stmt.setLong(2,id);
			int res = stmt.executeUpdate();
			conn.commit();

			if (res == 1) {
				System.out.println("Successful update");
			}else {
				System.out.println("No computer found with id "+id);
			}
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		
		}
	}
	
	public int count() throws SQLException {
		PreparedStatement stmt = null;
		int count = - 1;
		try {
			
			conn.setAutoCommit(false);
			String selectQuery = "SELECT count(id) FROM computer";
			stmt = conn.prepareStatement(selectQuery);
			ResultSet rSet = stmt.executeQuery();
			rSet.next();
			count = rSet.getInt(1);
			stmt.close();
			conn.commit();
			return count;
			
				
		} catch(SQLException se) {
		
		for (Throwable e : se) {
			System.out.println("Problem : "+e);	
		}
		conn.rollback();
		
		}finally {
		
			if (stmt != null) {
				stmt.close();
			}
		}
	return -1;
	}
	
	*/
	
}
