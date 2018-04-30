package com.excilys.formation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.excilys.formation.core.entities.UserInfo;
import com.excilys.formation.persistence.dao.UserDAO;

@Service
public class Authentication implements UserDetailsService {

	private UserDAO userDAO;

    @Autowired
    public Authentication(UserDAO userDAO) {
    	this.userDAO = userDAO;
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) {
		//UserInfo userInfo = userDAO.getUserInfo(username);
		// return (UserDetails)
		//return (UserDetails) User.withDefaultPasswordEncoder().username(userInfo.getUsername()).password(userInfo.getPassword()).roles(userInfo.getRole()).build();
		return  User.withDefaultPasswordEncoder().username("username").password("password")
				.roles("user").build();
	}
}
