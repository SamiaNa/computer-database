package com.excilys.java.formation.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.model.persistence.ComputerDAO;

public class ComputerPageService {

	private int offset;
	private int size;
	private Connection conn;
	private ComputerDAO compDAO;
	
	public ComputerPageService(int size, Connection conn) {
		this.offset = 0;
		this.size = size;
		this.conn = conn;
		this.compDAO = new ComputerDAO(conn);
	}
	
	public List<Computer> getPage () throws SQLException{
		return compDAO.get(offset, size);
	}
	

	
	public List<Computer> getNextPage() throws SQLException{
		offset += size;
		return getPage();
	}
	
	public List<Computer> getPrevPage() throws SQLException{
		offset -= size;
		return getPage();
	}
}
