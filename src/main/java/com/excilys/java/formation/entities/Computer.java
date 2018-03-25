package com.excilys.java.formation.entities;

import java.time.LocalDate;

import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;
import com.excilys.java.formation.validator.CompanyValidator;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public class Computer  {

    private long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    public Computer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Computer(long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
        this.id = -1;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    public Computer(StringToComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public long getId() {
        return id;
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

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Id=").append(this.id).append(", name=").append(this.name).append(", introduced=")
        .append(this.introduced).append(", discontinued=").append(this.discontinued).append(", company= ")
        .append(this.company);
        return str.toString();
    }

    public static class StringToComputerBuilder {

        private long id;
        private String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;

    

        public StringToComputerBuilder setName(String name) throws ValidatorException {
            ComputerValidator.INSTANCE.checkName(name);
            this.name = name;
            return this;
        }

        public StringToComputerBuilder setId(String strId)
                throws DAOException, ValidatorException, ConnectionException {
            this.id = ComputerValidator.INSTANCE.checkComputerId(strId);
            this.id = Long.parseLong(strId);
            return this;
        }

        public StringToComputerBuilder setCompany(String strId)
                throws  DAOException, ValidatorException, ConnectionException {
            if (!(strId.equals("null") || strId.equals(""))) {
                Company comp = new Company();
                comp.setId(CompanyValidator.INSTANCE.checkCompanyIdOrNull(strId));
                comp.setId(Long.parseLong(strId));
                this.company = comp;
            }
            return this;
        }

        public StringToComputerBuilder setIntroduced(String introducedStr) throws ValidatorException {
            introduced = ComputerValidator.INSTANCE.getDate(introducedStr);
            return this;
        }

        public StringToComputerBuilder setDiscontinued(String discontinuedStr) throws ValidatorException {
            discontinued = ComputerValidator.INSTANCE.getDate(discontinuedStr);
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }

        public Computer build(ComputerStringAttributes compStr)
                throws ValidatorException, DAOException, ConnectionException {
            this.setName(compStr.getName()).setCompany(compStr.getCompanyId()).setId(compStr.getId())
            .setIntroduced(compStr.getIntroduced()).setDiscontinued(compStr.getDiscontinued());
            return new Computer(this);
        }
    }
}
