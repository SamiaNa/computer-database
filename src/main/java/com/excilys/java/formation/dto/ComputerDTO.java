package com.excilys.java.formation.dto;

import java.util.Objects;

import com.excilys.java.formation.persistence.ConnectionException;
import com.excilys.java.formation.persistence.DAOException;
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


    private ComputerDTO(ComputerDTOBuilder computerDTOBuilder) {
        this.id = computerDTOBuilder.id;
        this.name = computerDTOBuilder.name;
        this.introduced = computerDTOBuilder.introduced;
        this.discontinued = computerDTOBuilder.discontinued;
        this.companyId = computerDTOBuilder.companyId;
        this.companyName = computerDTOBuilder.companyName;
        
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
        return (this.id == computer.id &&
                this.name.equals(computer.name) &&
                this.introduced.equals(computer.introduced) &&
                this.discontinued.equals(computer.discontinued) &&
                this.companyId.equals(computer.companyId) &&
                this.companyName.equals(computer.companyName));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, introduced, discontinued, companyId, companyName);
    }
    
    
    public static class ComputerDTOBuilder {

        private long id;
        private String name;
        private String introduced;
        private String discontinued;
        private String companyId;
        private String companyName;

        public ComputerDTOBuilder setName(String name) throws ValidatorException {
            ComputerValidator.INSTANCE.checkName(name);
            this.name = name;
            return this;
        }
        
        public ComputerDTOBuilder setId(String strId) {
            this.id = Long.parseLong(strId);
            return this;
        }
        
        public ComputerDTOBuilder setId(long id) {
            this.id = id;
            return this;
        }
        
        public ComputerDTOBuilder setCompanyId(String strId) throws DAOException, ValidatorException, ConnectionException {
            if (!(strId.equals("null") || strId.equals(""))) {
                CompanyValidator.INSTANCE.checkCompanyIdOrNull(strId);
                this.companyId = strId;
            }
            return this;
        }
        
        public ComputerDTOBuilder setCompanyName(String name){
            this.name = name;
            return this;
        }
    
        public ComputerDTOBuilder setIntroduced(String introducedStr) {
            this.introduced = introducedStr;
            return this;
        }

        public ComputerDTOBuilder setDiscontinued(String discontinuedStr) {
            discontinued = discontinuedStr;
            return this;
        }

        public ComputerDTO build() {
            return new ComputerDTO(this);
        }

    
    }

}
