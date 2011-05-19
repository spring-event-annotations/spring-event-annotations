package com.googlecode.spring.event.integration;

import org.springframework.stereotype.Service;

import com.googlecode.spring.event.annotation.Observes;

@Service
public class Observer {
	public void observesStrings(@Observes String string) {
		System.out.println("Observer.observesStrings()");
	}
	
	public void observesBean(@Observes Bean bean) {
		System.out.println("Observer.observesBean()");
	}
}
