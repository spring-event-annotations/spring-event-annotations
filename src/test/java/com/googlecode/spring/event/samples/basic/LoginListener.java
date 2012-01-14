package com.googlecode.spring.event.samples.basic;

import org.springframework.stereotype.Service;

import com.googlecode.spring.event.annotation.Observes;

@Service
public class LoginListener {

	public void userLoggedIn(@Observes LoggedInEvent e) {
		e.eventHandled("userLoggedIn");
	}

	public void userLoggedOut(@Observes LoggedOutEvent e) {
		e.eventHandled("userLoggedOut");
	}
}
