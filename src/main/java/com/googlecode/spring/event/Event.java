package com.googlecode.spring.event;

public interface Event<T> {
	public void fire(T event);
}
