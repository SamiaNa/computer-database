package com.excilys.java.formation.page;

import com.excilys.java.formation.persistence.DAOException;

public abstract class Page {

	protected int offset;
	protected int size;
	protected int dbSize;

	public abstract void nextPage() throws DAOException, ClassNotFoundException;
	public abstract void prevPage() throws DAOException, ClassNotFoundException;
	public abstract void getPage(int pageNumber) throws DAOException, ClassNotFoundException;
	public abstract void printPage();


	public void offsetNextPage(int dbSize) throws DAOException, ClassNotFoundException{
		if (offset + size <= dbSize) {
			offset += size;
		}
	}

	public void offsetPrevPage() throws DAOException, ClassNotFoundException{
		offset -= size;
		if (offset < 0) {
			offset = 0;
		}
	}

	public void offsetGetPage(int pageNumber, int dbSize) throws DAOException, ClassNotFoundException{
		if (pageNumber <= 0) {
			offset = 0;
		}
		else if ((pageNumber - 1) * size <= dbSize) {
			offset = ((pageNumber - 1) * size);
		}else {
			offset = dbSize - size;
		}
	}



}
