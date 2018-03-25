package com.excilys.java.formation.entities;

import java.util.Objects;

import com.excilys.java.formation.dto.ComputerDTO;

public class Company {

    private long id;
    private String name;

    public Company() {

    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Id=").append(this.id).append(", name=").append(this.name);
        return str.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        Company company = (Company) o;
        return (this.id == company.id &&
                this.name.equals(company.name));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
