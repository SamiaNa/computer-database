package com.excilys.java.formation.entities;

public class ComputerStringAttributes {


	/*
	 * Used to carry all the attributes of a computer as strings, 
	 * between the interface and the validator for the update operation
	 */
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;
			

	public ComputerStringAttributes (Computer c) {
		this.id = String.valueOf(c.getId());
		this.name = String.valueOf(c.getName());
		this.introduced = c.getIntroduced() == null ? "null" : String.valueOf(c.getIntroduced());
		this.discontinued = c.getDiscontinued() == null ? "null" : String.valueOf(c.getDiscontinued());
		this.companyId = c.getCompanyId() == null ? "null" : String.valueOf(c.getCompanyId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


}
