package com.googlecode.spring.event.samples.basic;

import com.googlecode.spring.event.EventHandledCallbackAware;
import com.googlecode.spring.event.EventHandledCallback;

public class LoggedInEvent extends EventHandledCallbackAware {

	public LoggedInEvent(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

}
