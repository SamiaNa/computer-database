package com.excilys.java.formation.dto;

public class CompanyDTO {

    private String name;
    private long id;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return (this.name.equals(company.name) && this.id == company.id);
    }
}
