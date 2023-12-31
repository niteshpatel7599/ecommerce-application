package com.egate.ecommerce.service.imp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.egate.ecommerce.repository.UserRepository;
import com.egate.ecommerce.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserDetailsService UserDetailsService() {
		return new UserDetailsService() {
			@Override
				public UserDetails loadUserByUsername(String username){
					return userRepository.findByEmail(username)
							.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
			}
		};
	}
}
