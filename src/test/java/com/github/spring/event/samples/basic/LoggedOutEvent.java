package com.github.spring.event.samples.basic;

import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.EventHandledCallbackAware;

public class LoggedOutEvent extends EventHandledCallbackAware {
	
	public LoggedOutEvent(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}
}
