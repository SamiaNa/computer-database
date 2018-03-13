package com.excilys.java.formation.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.mapper.Computer;
import com.excilys.java.formation.model.persistence.ComputerDAO;

public class ComputerPageService {
/*
	private int offset;
	private int size;
	private ComputerDAO compDAO;
	int dbSize;
	
	
	// En cas d'add ou de delete décalage ??
	public ComputerPageService(int size, Connection conn) throws SQLException {
		this.offset = 0;
		this.size = size;
		this.compDAO = new ComputerDAO(conn);
		dbSize = this.compDAO.count();
	}
	
	public List<Computer> getPage () throws SQLException{
		return compDAO.get(offset, size);
	}
	
	
	public List<Computer> getNextPage() throws SQLException{
		offset += size;
		if (offset + size > dbSize) {
			offset = dbSize - size;
		}
		return getPage();
	}
	
	public List<Computer> getPrevPage() throws SQLException{
		offset -= size;
		if (offset < 0) {
			offset = 0;
		}
		return getPage();
	}*/
}
