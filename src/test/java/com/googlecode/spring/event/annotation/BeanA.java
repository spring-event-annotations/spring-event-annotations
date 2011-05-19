package com.googlecode.spring.event.annotation;


public class BeanA {
	
	private final ExecutorDelegate mockDelegate;

	public BeanA(ExecutorDelegate mockDelegate) {
		this.mockDelegate = mockDelegate;
	}

	public void observeString1(@Observes String param1) {
		System.out.println("BeanA.observeString1()");
		mockDelegate.execute();
	}
}
