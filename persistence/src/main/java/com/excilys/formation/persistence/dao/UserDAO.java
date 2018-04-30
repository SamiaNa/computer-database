package com.excilys.formation.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.core.entities.QUserInfo;
import com.excilys.formation.core.entities.UserInfo;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
public class UserDAO {

	private SessionFactory sessionFactory;

	
	@Autowired
	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public UserInfo getUserInfo(String username) {
		try (Session session = sessionFactory.openSession()) {
			HibernateQuery<UserInfo> query = new HibernateQuery<>(session);
			return query.from(QUserInfo.userInfo).where(QUserInfo.userInfo.username.eq(username)).fetchOne();
		}

	}
}
