package com.ecommerce.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class Utility {
	
	public static String getEmailOfAuthenticatedUser(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();
		if (principal == null) return null;
		
		String userEmail = null;
		
		if (principal instanceof UsernamePasswordAuthenticationToken
				|| principal instanceof RememberMeAuthenticationToken) {
			userEmail = request.getUserPrincipal().getName();
		} 
		
		return userEmail;
	}
}
