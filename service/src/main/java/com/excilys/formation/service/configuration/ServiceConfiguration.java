package com.excilys.formation.service.configuration;


import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.excilys.formation.core.entities.Company;
import com.excilys.formation.core.entities.Computer;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.service" })
public class ServiceConfiguration {

 
}
