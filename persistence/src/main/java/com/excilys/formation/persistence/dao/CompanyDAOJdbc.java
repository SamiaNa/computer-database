package com.excilys.formation.persistence.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.QCompany;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class CompanyDAOJdbc {

   
    private ComputerDAOJdbc computerDAO;
    private HibernateQueryFactory queryFactory;
    

    @Autowired
    public CompanyDAOJdbc(ComputerDAOJdbc computerDAO, SessionFactory sessionFactory) {
        this.computerDAO = computerDAO;
        this.queryFactory = new HibernateQueryFactory(sessionFactory.openSession());
    }


    public List<Company> getAll() {
    	return (List<Company>) queryFactory.from(QCompany.company).fetch();
    }

    public List<Company> get(int offset, int size) {
    	return (List<Company>) queryFactory.from(QCompany.company).offset(offset).limit(size).fetch();
   
    }

    public List<Company> getByName(String name) {
    	QCompany company = QCompany.company;
    	return (List<Company>) queryFactory.from(company).where(company.name.like("%"+name+"%")).fetch();
    }

    public boolean checkCompanyById(long id) throws DAOException {
    	QCompany company = QCompany.company;
        List<Company> companies = (List<Company>) queryFactory.from(company).where(company.id.eq(id)).fetch();
        if (companies.size() == 1) {
            return true;
        }
        if (companies.isEmpty()) {
            return false;
        }
        throw new DAOException("Expected number of rows : 0 or 1, actual number of rows " + companies.size());
    }

    public int count() {
    	return (int) queryFactory.from(QCompany.company).fetchCount();
    }

    public void delete(long id) {
    	QCompany company = QCompany.company;
        computerDAO.deleteCompany(id);
        queryFactory.delete(QCompany.company).where(company.id.eq(id)).execute();
    }
}