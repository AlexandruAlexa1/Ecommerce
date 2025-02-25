package com.ecommerce.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ecommerce.admin.user.UserRepository;
import com.ecommerce.common.entity.User;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.getUserByEmail(email);
		if (user != null ) {
			return new EcommerceUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
