package com.googlecode.spring.event.samples.basic;

import com.googlecode.spring.event.EventHandledCallbackAware;
import com.googlecode.spring.event.EventHandledCallback;

public class LoggedOutEvent extends EventHandledCallbackAware {
	
	public LoggedOutEvent(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}
}
