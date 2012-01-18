package com.github.spring.event.annotation;

import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.annotation.Observes;

public class StringListener2 extends StringListener1 {

	public StringListener2(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

	public void observeString2(@Observes String param1) {
		eventHandled("observeString2");
	}
}
