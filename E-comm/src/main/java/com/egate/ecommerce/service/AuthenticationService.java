package com.egate.ecommerce.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.egate.ecommerce.dto.JWTAuthenticationResponse;
import com.egate.ecommerce.dto.SigninRequest;
import com.egate.ecommerce.dto.SignupReqest;
import com.egate.ecommerce.entity.User;
import com.egate.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;


public interface AuthenticationService  {

	User signup(SignupReqest signupReqest);
	JWTAuthenticationResponse signin(SigninRequest signinRequest);
//	JWTAuthenticationResponse refreshToken(RefreshedTokenRequest refreshedTokenRequest);
	
	
	
}
