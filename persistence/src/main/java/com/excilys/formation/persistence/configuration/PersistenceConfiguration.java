package com.excilys.formation.persistence.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.Computer;
import com.excilys.formation.core.entities.UserInfo;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.persistence" })
@PropertySource(value = { "classpath:datasource.properties" })
public class PersistenceConfiguration {

	@Value("${driver}")
	private String driver;

	@Value("${url}")
	private String url;

	@Value("${dbuser}")
	private String user;

	@Value("${dbpass}")
	private String pass;
	
	private Logger logger = LoggerFactory.getLogger(PersistenceConfiguration.class);

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setAnnotatedClasses(Company.class, Computer.class, UserInfo.class);
		sessionFactory.setPackagesToScan("com.excilys.formation.core.entities");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(pass);
		logger.error(" {}, {}, {}, {}", driver, url, user, pass);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.show_sql", "hibernate.show_sql");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.current_session_context_class", "thread");
		return hibernateProperties;
	}

}
