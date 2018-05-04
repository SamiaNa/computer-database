package com.excilys.formation.configuration;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@PropertySource(value = { "classpath:datasource.properties" })
@ComponentScan(basePackages = { "com.excilys.formation"})
public class WebServiceConfiguration implements WebMvcConfigurer{
    
   @Override
   public void configureMessageConverters(
     List<HttpMessageConverter<?>> converters) {
       converters.add(new MappingJackson2HttpMessageConverter());
   }
}
