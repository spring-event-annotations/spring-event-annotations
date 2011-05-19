package com.googlecode.spring.event.annotation;


public class BeanB {
	private final ExecutorDelegate mockDelegate;
	public BeanB(ExecutorDelegate mockDelegate) {
		this.mockDelegate = mockDelegate;
	}

	public void observeString1(@Observes String param1) {
		System.out.println("BeanB.observeString1()");
		mockDelegate.execute();
	}

	public void observeString2(@Observes String param1) {
		System.out.println("BeanB.observeString2()");
		mockDelegate.execute();
	}
}
