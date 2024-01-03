package com.egate.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egate.ecommerce.dto.JWTAuthenticationResponse;
import com.egate.ecommerce.dto.SigninRequest;
import com.egate.ecommerce.dto.SignupReqest;
import com.egate.ecommerce.entity.User;
import com.egate.ecommerce.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@RequestBody SignupReqest signupReqest){
		return ResponseEntity.ok(authenticationService.signup(signupReqest));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthenticationResponse> signIn(@RequestBody SigninRequest signinRequest){
		return ResponseEntity.ok(authenticationService.signin(signinRequest));
	}
	
//	@PostMapping("/refresh")
//	public ResponseEntity<JWTAuthenticationResponse> refresh(@RequestBody RefreshedTokenRequest refreshedTokenRequest){
//		return ResponseEntity.ok(authenticationService.refreshToken(refreshedTokenRequest));
//	}
}
