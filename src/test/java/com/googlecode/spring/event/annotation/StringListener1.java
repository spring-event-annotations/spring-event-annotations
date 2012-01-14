package com.googlecode.spring.event.annotation;

import com.googlecode.spring.event.EventHandledCallbackAware;
import com.googlecode.spring.event.EventHandledCallback;

public class StringListener1 extends EventHandledCallbackAware {

	public StringListener1(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

	public void observeString1(@Observes String param1) {
		eventHandled("observeString1");
	}
}
