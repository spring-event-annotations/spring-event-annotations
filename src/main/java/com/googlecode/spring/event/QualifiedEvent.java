package com.googlecode.spring.event;

import java.lang.annotation.Annotation;

public class QualifiedEvent<T> implements Event<T> {
	
	private Annotation qualifier;
	
	private EventRegistry eventRegistry;
	
	public QualifiedEvent(EventRegistry eventRegistry, Annotation qualifier) {
		this.eventRegistry = eventRegistry;
		this.qualifier = qualifier;
	}

	@Override
	public void fire(T event) {		
		eventRegistry.fire(event, qualifier);
	}
}
