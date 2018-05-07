package com.excilys.formation.persistence.dao;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.core.entities.QCompany;
import com.excilys.formation.core.entities.QComputer;
import com.excilys.formation.persistence.daoexceptions.DAOException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
public class ComputerDAOJdbc {

	private static final String COMPUTER_ID = "idcomputer";
	private static final String COMPUTER_NAME = "namecomputer";
	private static final String COMPUTER_INTRO = "introcomputer";
	private static final String COMPUTER_DISC = "disccomputer";
	private static final String COMPUTER_COMPANY = "namecompany";
	private static final String ASCENDING = "ASC";
	private static final String DESCENDING = "DESC";
	private static final QComputer qComputer = QComputer.computer;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private SessionFactory sessionFactory;

	@Autowired
	public ComputerDAOJdbc(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ComputerDAOJdbc() {
		super();
	} 

	public List<Computer> getAll() {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			return query.from(qComputer).fetch();
		}

	}

	public List<Computer> get(long offset, long size) {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			return query.from(qComputer).offset(offset).limit(size).fetch();
		}
	}

	public Optional<Computer> getComputerById(long id) {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			List<Computer> computer = query.from(qComputer).where(qComputer.id.eq(id)).fetch();
			try {
				return Optional.of(computer.get(0));
			} catch (IndexOutOfBoundsException e) {
				return Optional.empty();
			}
		}
	}

	public List<Computer> getByName(String name, long offset, long limit) {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			String search = "%" + name + "%";
			return query.from(qComputer).where(qComputer.name.like(search).or(QCompany.company.name.like(search)))
					.limit(limit).offset(offset).fetch();
		}
	}

	public List<Computer> getByOrder(String orderCriteria, String order, long offset, long limit) throws DAOException {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			return query.from(qComputer).orderBy(getColumnName(orderCriteria, order)).limit(limit).offset(offset)
					.fetch();
		}
	}

	private OrderSpecifier<?> getColumnName(String orderCriteria, String order) throws DAOException {
		checkOrder(order);
		switch (orderCriteria) {
		case COMPUTER_ID:
			return ASCENDING.equalsIgnoreCase(order) ? qComputer.id.asc() : qComputer.id.desc();
		case COMPUTER_NAME:
			return ASCENDING.equalsIgnoreCase(order) ? qComputer.name.asc() : qComputer.name.desc();
		case COMPUTER_INTRO:
			return ASCENDING.equalsIgnoreCase(order) ? qComputer.introduced.asc() : qComputer.introduced.desc();
		case COMPUTER_DISC:
			return ASCENDING.equalsIgnoreCase(order) ? qComputer.discontinued.asc() : qComputer.discontinued.desc();
		case COMPUTER_COMPANY:
			return ASCENDING.equalsIgnoreCase(order) ? qComputer.company.name.asc() : qComputer.company.name.desc();
		default:
			String message = "Unknown order criteria : " + orderCriteria;
			logger.error(message);
			throw new DAOException(message);
		}
	}

	private void checkOrder(String order) throws DAOException {
		if (!order.equalsIgnoreCase(ASCENDING) && !order.equalsIgnoreCase(DESCENDING)) {
			String message = "Unknown parameter " + order;
			logger.error(message);
			throw new DAOException(message);
		}

	}

	public List<Computer> getByOrder(String orderCriteria, String order, String search, long offset, long limit)
			throws DAOException {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			String searchParam = "%" + search + "%";
			return query.from(qComputer).orderBy(getColumnName(orderCriteria, order))
					.where(qComputer.name.like(searchParam).or(QCompany.company.name.like(searchParam))).limit(limit)
					.offset(offset).fetch();
		} 
	}

	public long createComputer(Computer c) {
		try (Session session = sessionFactory.openSession()) {
			session.save(c);
			return c.getId();
		}
	}

	public void update(Computer c) {
		try (Session session = sessionFactory.openSession()) {
			session.update(c);
			session.flush();
		}
	}

	public void delete(long id) {
		try (Session session = sessionFactory.openSession()) {
			HibernateDeleteClause delete = new HibernateDeleteClause(session, qComputer);
			delete.where(qComputer.id.eq(id)).execute();
		}
	}

	public void deleteCompany(long id) {
		try (Session session = sessionFactory.openSession()) {
			HibernateDeleteClause delete = new HibernateDeleteClause(session, qComputer);
			delete.where(qComputer.company.id.eq(id)).execute();
		}
	}

	public void delete(List<Long> ids) {
		try (Session session = sessionFactory.openSession()) {
			HibernateDeleteClause delete = new HibernateDeleteClause(session, qComputer);
			delete.where(qComputer.id.in(ids)).execute();
		}
	}

	public long count() {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			return query.from(qComputer).fetchCount();
		}
	}

	public long count(String name) {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<Computer> query = new HibernateQuery<>(session);
			String search = "%" + name + "%";
			return query.from(qComputer).where(qComputer.name.like(search).or(QCompany.company.name.like(search)))
					.fetchCount();
		}
	}
}