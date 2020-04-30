package com.github.vskrahul.authserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.vskrahul.authserver.model.UserProfile;

@RestController
public class RestResource {
	
	@RequestMapping(method=RequestMethod.GET, path="/api/users/me", produces={"application/json"})
	public ResponseEntity<UserProfile> profile() {
		// Build some dummy data to return for testing
		String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = user + "@twitter.com";

		UserProfile profile = new UserProfile();
		profile.setName(user);
		profile.setEmail(email);

		return new ResponseEntity<UserProfile>(profile, HttpStatus.OK);
	}
}