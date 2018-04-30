package com.excilys.formation.persistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.QCompany;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDAOJdbc {

	private static QCompany qCompany = QCompany.company;
	private ComputerDAOJdbc computerDAO;
	private SessionFactory sessionFactory;

	@Autowired
	public CompanyDAOJdbc(ComputerDAOJdbc computerDAO, SessionFactory sessionFactory) {
		this.computerDAO = computerDAO;
		this.sessionFactory = sessionFactory;
	}

	public List<Company> getAll() {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Company> query = new HibernateQuery<>(session);
			return query.from(qCompany).fetch();
		}

	}

	public List<Company> get(long offset, long size) {
		try (Session session = sessionFactory.openSession()){
			HibernateQuery<Company> query = new HibernateQuery<>(session);
			return query.from(qCompany).offset(offset).limit(size).fetch();
		}
	}

	public List<Company> getByName(String name) {
		try (Session session = sessionFactory.openSession()){
			HibernateQuery<Company> query = new HibernateQuery<>(session);
			return query.from(qCompany).where(qCompany.name.like("%" + name + "%")).fetch();
		}
	}

	public boolean checkCompanyById(long id) throws DAOException {
		try (Session session = sessionFactory.openSession()){
			HibernateQuery<Company> query = new HibernateQuery<>(session);
			List<Company> companies = query.from(qCompany).where(qCompany.id.eq(id)).fetch();
			if (companies.size() == 1) {
				return true;
			}
			if (companies.isEmpty()) {
				return false;
			}
			throw new DAOException("Expected number of rows : 0 or 1, actual number of rows " + companies.size());

		}
		
	}

	public long count() {
		try (Session session = sessionFactory.openSession()){
			HibernateQuery<Company> query = new HibernateQuery<>(session);
			return query.from(qCompany).fetchCount();
		}
	}

	public void delete(long id) {
		try (Session session = sessionFactory.openSession()) {
			computerDAO.deleteCompany(id);
			HibernateDeleteClause delete = new HibernateDeleteClause(session, qCompany);
			delete.where(qCompany.id.eq(id)).execute();
		}
	}
}