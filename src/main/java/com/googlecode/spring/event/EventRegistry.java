package com.googlecode.spring.event;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

import org.springframework.util.*;

@SuppressWarnings("rawtypes")
public class EventRegistry implements Event {
	
	private List<EventListener> registeredEvents = new ArrayList<EventListener>();
	private Executor executor;
	
	public EventRegistry() {

	}
	
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
	
	public void registerEvent(Object bean, Method method, Class<?> parameterType) {
		Assert.notNull(bean, "Cannot register event: Bean reference cannot be null");
		Assert.notNull(method, "Cannot register event: Method reference cannot be null");
		Assert.notNull(parameterType, "Cannot register event: Paremeter type cannot be null");
		registeredEvents.add(new EventListener(bean, method, parameterType));
	}
	
	public void fire(final Object event) {
		Assert.state(executor != null, "Could not fire event: Executor not specified");
		for (final EventListener listener : registeredEvents) {
			if(!listener.isSupported(event.getClass())) {
				continue;
			}
			executor.execute(new Runnable() {
				public void run() {
					listener.fire(event);
				}
			});
		}
	}
	
	private class EventListener implements Event {
		private final Class<?> parameterType;
		private final Method method;
		private final Object bean;

		private EventListener(Object bean, Method method, Class<?> parameterType) {
			this.bean = bean;
			this.method = method;
			this.parameterType = parameterType;
		}

		private boolean isSupported(Class<?> eventType) {
			return parameterType.isAssignableFrom(eventType);
		}

		public void fire(Object event) {
			try {
				method.invoke(bean, event);
			} catch (Throwable e) {
				throw new RuntimeException("error while handling event "
						+ event.getClass().getName(), e);
			}
		}
	}
}
