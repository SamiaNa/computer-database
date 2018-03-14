package com.excilys.java.formation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Computer;

public class ComputerPage {
	
	private int offset;
	private int size;
	private int dbSize;
	List<Computer> computers;
	ComputerService computerService;
	
	public ComputerPage(int size) throws SQLException, ClassNotFoundException {
		this.offset = 0;
		this.size = size;
		this.computerService = ComputerService.getService();
		this.computers = computerService.getComputersList(offset, size);
		this.dbSize = computerService.count();
	}
	
	
	public void nextPage() throws SQLException, ClassNotFoundException{
		this.dbSize = computerService.count();
		if (offset + size <= dbSize) {
			offset += size;
		}
		this.computers = computerService.getComputersList(offset, size);
		
	}
	
	public void prevPage() throws SQLException, ClassNotFoundException{
		this.dbSize = computerService.count();
		offset -= size;
		if (offset < 0) {
			offset = 0;
		}
		this.computers = computerService.getComputersList(offset, size);
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void printPage() {
		for (Computer c : this.computers) {
			System.out.println(c);
		}
	}
}
