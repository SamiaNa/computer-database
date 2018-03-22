package com.excilys.java.formation.dto;

public class ComputerDTO {

    private long id;
    private String name;
    private String introduced;
    private String discontinued;
    private long companyId;
    private long companyName;

    public long getId() {
        return id;
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

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getCompanyName() {
        return companyName;
    }

    public void setCompanyName(long companyName) {
        this.companyName = companyName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
