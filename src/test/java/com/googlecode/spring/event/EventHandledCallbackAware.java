package com.googlecode.spring.event;

public abstract class EventHandledCallbackAware {
	
	private final EventHandledCallback eventHandledCallback;

	public EventHandledCallbackAware(EventHandledCallback eventHandledCallback) {
		this.eventHandledCallback = eventHandledCallback;
	}

	public void eventHandled(Object object) {
		eventHandledCallback.eventHandled(object);
	}
}
