package com.excilys.java.formation.page;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;
import com.excilys.java.formation.service.CompanyService;


public class CompanyPage extends Page{
	

	List<Company> companies;
	CompanyService companyService;
	
	public CompanyPage(int size) throws SQLException, ClassNotFoundException {
		this.offset = 0;
		this.size = size;
		this.companyService = CompanyService.getService();
		this.companies = companyService.getCompaniesList(offset, size);
		this.dbSize = companyService.count();
		
	}
	
	public void updateList() throws SQLException, ClassNotFoundException{
		this.companies = companyService.getCompaniesList(offset, size);
	}
	
	public void nextPage() throws SQLException, ClassNotFoundException{
		this.dbSize = companyService.count();
		super.offsetNextPage(dbSize);
		updateList();
		
	}
	
	public void prevPage() throws SQLException, ClassNotFoundException{
		super.offsetPrevPage();
		this.companies = companyService.getCompaniesList(offset, size);
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void printPage() {
		for (Company c : this.companies) {
			System.out.println(c);
		}
	}

	public void getPage(int pageNumber) throws SQLException, ClassNotFoundException {
		this.dbSize = companyService.count();
		super.offsetGetPage(pageNumber, dbSize);
		this.companies = companyService.getCompaniesList(offset, size);
	}

}
