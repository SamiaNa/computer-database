package com.excilys.java.formation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.java.formation.entities.Company;


public class CompanyPage {
	
	private int offset;
	private int size;
	private int dbSize;
	List<Company> companies;
	CompanyService companyService;
	
	public CompanyPage(int size) throws SQLException, ClassNotFoundException {
		this.offset = 0;
		this.size = size;
		this.companyService = CompanyService.getService();
		this.companies = companyService.getCompaniesList(offset, size);
		this.dbSize = companyService.count();
		
	}
	
	public void nextPage() throws SQLException, ClassNotFoundException{
		this.dbSize = companyService.count();
		if (offset + size <= dbSize) {
			offset += size;
		}
		this.companies = companyService.getCompaniesList(offset, size);
		
	}
	
	public void prevPage() throws SQLException, ClassNotFoundException{
		this.dbSize = companyService.count();
		offset -= size;
		if (offset < 0) {
			offset = 0;
		}
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
}
