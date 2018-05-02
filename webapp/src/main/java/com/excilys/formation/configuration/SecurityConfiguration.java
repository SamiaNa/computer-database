package com.excilys.formation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { 

	@Autowired
	UserAuthService userDetailsService;
	 
    
   @Autowired
   public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService);
       auth.authenticationProvider(authenticationProvider());
   }
   
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 .antMatchers("/AddComputer", "/EditComputer", "/Delete", "/").hasRole("ADMIN")
		 .antMatchers("/").hasAnyRole("USER", "ADMIN")
	     .and().formLogin()
	     .loginPage("/login").permitAll()
	     .and().exceptionHandling().accessDeniedPage("/403")
	     //.and().exceptionHandling().accessDeniedPage("/403")
	     .and().csrf();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	   // authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	 
	/*@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}*/
}
	 

