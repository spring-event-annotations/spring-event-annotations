package com.googlecode.spring.event.annotation;

import com.googlecode.spring.event.EventHandledCallback;

public class StringListener2 extends StringListener1 {

	public StringListener2(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

	public void observeString2(@Observes String param1) {
		eventHandled("observeString2");
	}
}
