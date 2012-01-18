package com.github.spring.event;

public interface Event<T> {
	void fire(T event);
}
