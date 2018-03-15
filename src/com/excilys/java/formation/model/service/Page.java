package com.excilys.java.formation.model.service;

import java.sql.SQLException;

public abstract class Page {
	
	protected int offset;
	protected int size;
	protected int dbSize;
	
	public abstract void nextPage() throws SQLException, ClassNotFoundException;
	public abstract void prevPage() throws SQLException, ClassNotFoundException;
	public abstract void getPage(int pageNumber) throws SQLException, ClassNotFoundException;
	public abstract void printPage();

	
	public void offsetNextPage(int dbSize) throws SQLException, ClassNotFoundException{
		if (offset + size <= dbSize) {
			offset += size;
		}
	}
	
	public void offsetPrevPage() throws SQLException, ClassNotFoundException{
		offset -= size;
		if (offset < 0) {
			offset = 0;
		}
	}
	
	public void offsetGetPage(int pageNumber, int dbSize) throws SQLException, ClassNotFoundException{
		if (pageNumber <= 0) {
			System.out.println("ICI0");
			offset = 0;
		}
		else if ((pageNumber - 1) * size <= dbSize) {
			System.out.println("ICI1");
			offset = ((pageNumber - 1) * size);
		}else {
			System.out.println("ICI2");
			offset = dbSize - size;
		}
	}
	
	

}
