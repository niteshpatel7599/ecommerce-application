package com.egate.ecommerce.service.imp;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.egate.ecommerce.dto.JWTAuthenticationResponse;
import com.egate.ecommerce.dto.SigninRequest;
import com.egate.ecommerce.dto.SignupReqest;
import com.egate.ecommerce.entity.Role;
import com.egate.ecommerce.entity.User;
import com.egate.ecommerce.repository.UserRepository;
import com.egate.ecommerce.service.AuthenticationService;
import com.egate.ecommerce.service.JWTService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
	public User signup(SignupReqest signupReqest) {
		User user = new User();
		user.setEmail(signupReqest.getEmail());
		user.setFirstName(signupReqest.getFirstName());
		user.setLastName(signupReqest.getLastName());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(signupReqest.getPassword()));
		return userRepository.save(user);
	}
	
	public JWTAuthenticationResponse signin(SigninRequest signinRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
		var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() ->new  IllegalArgumentException("Invalid email or password"));
		var jwt = jwtService.generateToken(user);
//		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		
		JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
//		jwtAuthenticationResponse.setRefreshedToken(refreshToken);
		return jwtAuthenticationResponse;
	}
	
//	public JWTAuthenticationResponse refreshToken(RefreshedTokenRequest refreshedTokenRequest) {
//		String userEmail = jwtService.extractUserName(refreshedTokenRequest.getToken());
//		User user = userRepository.findByEmail(userEmail).orElseThrow();
//		if(jwtService.isTokenValid(refreshedTokenRequest.getToken(), user)) {
//			var jwt = jwtService.generateToken(user);
//			JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
//			jwtAuthenticationResponse.setToken(jwt);
//			jwtAuthenticationResponse.setRefreshedToken(refreshedTokenRequest.getToken());
//			return jwtAuthenticationResponse;
//		}
//		return null;
//	}
}
