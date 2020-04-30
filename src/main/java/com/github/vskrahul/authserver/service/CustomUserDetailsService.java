package com.github.vskrahul.authserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.vskrahul.authserver.model.UserProfile;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserProfile profile = new UserProfile();
		profile.setName(username);
		profile.setEmail("abc@twitter.com");
		return profile;
	}
}