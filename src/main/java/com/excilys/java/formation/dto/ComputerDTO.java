package com.excilys.java.formation.dto;

import java.util.Objects;

import com.excilys.java.formation.validator.CompanyValidator;
import com.excilys.java.formation.validator.ComputerValidator;
import com.excilys.java.formation.validator.ValidatorException;

public class ComputerDTO {

    private long id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyId;
    private String companyName;

    private ComputerDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.companyId = builder.companyId;
        this.companyName = builder.companyName;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
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
                && this.discontinued.equals(computer.discontinued) && this.companyId.equals(computer.companyId)
                && this.companyName.equals(computer.companyName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, introduced, discontinued, companyId, companyName);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Id=").append(this.id).append(", name=").append(this.name).append(", introduced=")
                .append(this.introduced).append(", discontinued=").append(this.discontinued).append(", company= ")
                .append(this.companyId).append(" ").append(this.companyName);
        return str.toString();
    }

    public static class Builder {

        private long id;
        private String name;
        private String introduced;
        private String discontinued;
        private String companyId;
        private String companyName;

        public Builder withName(String name) throws ValidatorException {
            ComputerValidator.INSTANCE.checkName(name);
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

        public Builder withCompanyId(String strId) {
            this.companyId = strId;
            return this;
        }

        public Builder withCompanyName(String name) {
            this.companyName = name;
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
                    .append(this.companyId).append(" ").append(this.companyName);
            return str.toString();
        }

    }

}
