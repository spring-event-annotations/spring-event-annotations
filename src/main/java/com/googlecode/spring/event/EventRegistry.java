package com.googlecode.spring.event;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.util.Assert;


public class EventRegistry implements Event {

	private List<EventListener> registeredEvents = new ArrayList<EventListener>();
	private Executor executor;
	
	public EventRegistry() {

	}
	
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
	
	public void registerEvent(Object bean, Method method, Class<?> parameterType) {
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
	
	private static class EventListener implements Event {
		private final WeakReference<Object> beanWeakRef;
		private final WeakReference<Method> methodWeakRef;
		private final Class<?> parameterType;

		private EventListener(Object bean, Method method, Class<?> parameterType) {
			this.beanWeakRef = new WeakReference<Object>(bean);
			this.methodWeakRef = new WeakReference<Method>(method);
			this.parameterType = parameterType;
		}

		private boolean isSupported(Class<?> eventType) {
			return parameterType.isAssignableFrom(eventType);
		}

		public void fire(Object event) {
			Object bean = beanWeakRef.get();
			Method method = methodWeakRef.get();
			try {
				method.invoke(bean, event);
			} catch (Throwable e) {
				throw new RuntimeException("error while handling event "
						+ event.getClass().getName(), e);
			}
		}
	}
}
