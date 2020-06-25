package com.thewire.med.controller;

import com.thewire.med.security.SecurityService;
import com.thewire.med.security.models.Credentials;
import com.thewire.med.security.models.SecurityProperties;
import com.thewire.med.utils.CookieUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.SessionCookieOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class SessionAuthController
{
	@Autowired
	SecurityService securityService;

	@Autowired
	CookieUtils cookieUtils;

	@Autowired
	SecurityProperties secProps;

	@PostMapping("/session/login")
	public void sessionLogin(HttpServletRequest request) {
		String idToken = securityService.getBearerToken(request);
		int sessionExpiryDays = secProps.getFirebaseProps().getSessionExpiryInDays();
		long expiresIn = TimeUnit.DAYS.toMillis(sessionExpiryDays);
		SessionCookieOptions options = SessionCookieOptions.builder().setExpiresIn(expiresIn).build();
		try {
			String sessionCookieValue = FirebaseAuth.getInstance().createSessionCookie(idToken, options);
			cookieUtils.setSecureCookie("session", sessionCookieValue,
					(int) TimeUnit.DAYS.toMinutes(sessionExpiryDays));
			cookieUtils.setCookie("authenticated", Boolean.toString(true),
					(int) TimeUnit.DAYS.toMinutes(sessionExpiryDays));
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/session/logout")
	public void sessionLogout() {
		if (securityService.getCredentials().getType() == Credentials.CredentialType.SESSION
				&& secProps.getFirebaseProps().isEnableLogoutEverywhere()) {
			try {
				FirebaseAuth.getInstance().revokeRefreshTokens(securityService.getUser().getUid());
			} catch (FirebaseAuthException e) {
				e.printStackTrace();
			}
		}
		cookieUtils.deleteSecureCookie("session");
		cookieUtils.deleteCookie("authenticated");

	}
}
