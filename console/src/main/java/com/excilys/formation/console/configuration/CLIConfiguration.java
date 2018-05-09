package com.excilys.formation.console.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.service", "com.excilys.formation.persistence",
		"com.excilys.formation.core", "com.excilys.formation.binding", "com.excilys.formation.console"})
public class CLIConfiguration {

}
