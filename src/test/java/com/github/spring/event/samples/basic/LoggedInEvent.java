package com.github.spring.event.samples.basic;

import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.EventHandledCallbackAware;

public class LoggedInEvent extends EventHandledCallbackAware {

	public LoggedInEvent(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

}
