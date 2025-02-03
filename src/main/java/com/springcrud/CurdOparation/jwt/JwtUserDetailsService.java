package com.springcrud.CurdOparation.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.springcrud.CurdOparation.model.UserTbl;
import com.springcrud.CurdOparation.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userDao;
	
//	@SuppressWarnings("unused")
//	@Autowired
//	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserTbl user = userDao.findByUsername(username);
//		UserTbl user = null;
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
}
