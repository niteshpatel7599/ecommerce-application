package com.egate.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

	String extractUserName(String token);
	String generateToken(UserDetails userDetails);
	Boolean isTokenValid( String token, UserDetails userDetails);
}
