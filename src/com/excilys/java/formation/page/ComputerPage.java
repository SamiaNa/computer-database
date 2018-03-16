package com.excilys.java.formation.page;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.service.ComputerService;

public class ComputerPage extends Page {
	
	
	private List<Computer> computers;
	private ComputerService computerService;
	
	public ComputerPage(int size) throws SQLException, ClassNotFoundException {
		this.offset = 0;
		this.size = size;
		this.computerService = ComputerService.INSTANCE;
		this.computers = computerService.getComputerList(offset, size);
		this.dbSize = computerService.count();
	}
	
	public void updateList() throws ClassNotFoundException, SQLException {
		this.computers = computerService.getComputerList(offset, size);
	}
	
	public void nextPage() throws SQLException, ClassNotFoundException{
		dbSize = computerService.count();
		super.offsetNextPage(dbSize);
		updateList();
	}
	
	public void prevPage() throws SQLException, ClassNotFoundException{
		super.offsetPrevPage();
		updateList();
	}
	
	public void getPage(int pageNumber) throws SQLException, ClassNotFoundException{
		this.dbSize = computerService.count();
		super.offsetGetPage(pageNumber, dbSize);
		updateList();
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
