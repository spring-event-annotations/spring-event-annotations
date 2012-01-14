package com.googlecode.spring.event;

public interface Event<T> {
	void fire(T event);
}
