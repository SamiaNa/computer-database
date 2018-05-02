package com.excilys.formation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.core.entities.UserInfo;
import com.excilys.formation.persistence.dao.UserDAO;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userDAO.getUserInfo(username);
		return User
				.withUsername(user.getUsername()).password(user.getPassword()).roles(user.getRole()).build();

	}
}
