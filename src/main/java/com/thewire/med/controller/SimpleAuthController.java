package com.thewire.med.controller;

import com.thewire.med.security.SecurityService;
import com.thewire.med.security.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleAuthController
{
	@Autowired
	SecurityService securityService;

	@PostMapping("/me")
	public User getUser() {
		return securityService.getUser();
	}

	@GetMapping("/create/token")
	public String getCustomToken() throws FirebaseAuthException
	{
		return FirebaseAuth.getInstance().createCustomToken(String.valueOf(securityService.getUser().getUid()));
	}
}
