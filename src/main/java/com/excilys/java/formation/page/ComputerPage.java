package com.excilys.java.formation.page;

import java.util.List;

import com.excilys.java.formation.entities.Computer;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.service.ComputerService;

public class ComputerPage extends Page {


	private List<Computer> computers;
	private ComputerService computerService;

	public ComputerPage(int size) throws DAOException, ClassNotFoundException {
		this.offset = 0;
		this.size = size;
		this.computerService = ComputerService.INSTANCE;
		this.computers = computerService.getComputerList(offset, size);
		this.dbSize = computerService.count();
	}

	public void updateList() throws ClassNotFoundException, DAOException {
		this.computers = computerService.getComputerList(offset, size);
	}

	@Override
	public void nextPage() throws DAOException, ClassNotFoundException{
		dbSize = computerService.count();
		super.offsetNextPage(dbSize);
		updateList();
	}

	@Override
	public void prevPage() throws DAOException, ClassNotFoundException{
		super.offsetPrevPage();
		updateList();
	}

	@Override
	public void getPage(int pageNumber) throws DAOException, ClassNotFoundException{
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

	@Override
	public void printPage() {
		for (Computer c : this.computers) {
			System.out.println(c);
		}
	}
}
