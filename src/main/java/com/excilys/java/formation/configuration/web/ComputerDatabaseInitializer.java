package com.excilys.java.formation.configuration.web;

import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Profile("!CLI")
public class ComputerDatabaseInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { ComputerDatabaseConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}