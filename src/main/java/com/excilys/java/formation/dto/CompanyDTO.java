package com.excilys.java.formation.dto;

import java.util.Objects;

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
        if (!(o instanceof CompanyDTO)) {
            return false;
        }
        CompanyDTO company = (CompanyDTO) o;
        return (this.name.equals(company.name) && this.id == company.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
