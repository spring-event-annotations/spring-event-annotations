package com.github.spring.event.annotation;

import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.EventHandledCallbackAware;
import com.github.spring.event.annotation.Observes;

public class StringListener1 extends EventHandledCallbackAware {

	public StringListener1(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

	public void observeString1(@Observes String param1) {
		eventHandled("observeString1");
	}
}
