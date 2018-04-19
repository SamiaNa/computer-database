package com.excilys.java.formation.dto;

import java.util.Objects;

public class ComputerDTO {

    private long id;
    private String name;
    private String introduced;
    private String discontinued;

    private CompanyDTO company;

    private ComputerDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    private ComputerDTO() {

    }

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

    public CompanyDTO getCompany() {
        return this.company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public void setCompanyId(long id) {
        if (this.company == null) {
            this.company = new CompanyDTO();
        }
        this.company.setId(id);
    }


    public long getCompanyID() {
        return this.company.getId();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComputerDTO)) {
            return false;
        }
        ComputerDTO computer = (ComputerDTO) o;
        return (this.id == computer.id && this.name.equals(computer.name) && this.introduced.equals(computer.introduced)
                && this.discontinued.equals(computer.discontinued) && this.company.equals(computer.company));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, introduced, discontinued, company);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Id=").append(this.id).append(", name=").append(this.name).append(", introduced=")
        .append(this.introduced).append(", discontinued=").append(this.discontinued).append(", company= ")
        .append(this.company == null ? "null" : this.company.toString());
        return str.toString();
    }

    public static class Builder {

        private long id;
        private String name;
        private String introduced;
        private String discontinued;
        private CompanyDTO company;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withId(String strId) {
            this.id = Long.parseLong(strId);
            return this;
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withCompany(CompanyDTO company) {
            this.company = company;
            return this;
        }

        public Builder withIntroduced(String introducedStr) {
            this.introduced = introducedStr;
            return this;
        }

        public Builder withDiscontinued(String discontinuedStr) {
            discontinued = discontinuedStr;
            return this;
        }

        public ComputerDTO build() {
            return new ComputerDTO(this);
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("Id=").append(this.id).append(", name=").append(this.name).append(", introduced=")
            .append(this.introduced).append(", discontinued=").append(this.discontinued).append(", company= ")
            .append(this.company == null ? "null" : this.company.toString());
            return str.toString();
        }

    }

}
