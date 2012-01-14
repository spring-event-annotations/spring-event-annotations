package com.googlecode.spring.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.util.Assert;

@SuppressWarnings("rawtypes")
public class EventRegistry implements Event {
	
	private List<DefaultEvent> notQualifiedEvents = new ArrayList<DefaultEvent>();
	private List<QualifiedEvent> qualifiedEvents = new ArrayList<QualifiedEvent>();
	private Executor executor;
	
	public EventRegistry() {

	}
	
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
	
	public void registerEvent(Object bean, Method method, Class<?> parameterType, Annotation qualifier) {
		Assert.notNull(bean, "Cannot register event: Item reference cannot be null");
		Assert.notNull(method, "Cannot register event: Method reference cannot be null");
		Assert.notNull(parameterType, "Cannot register event: Paremeter type cannot be null");
		
		if(qualifier == null) {
			notQualifiedEvents.add(new DefaultEvent(bean, method, parameterType));
		} else {
			qualifiedEvents.add(new QualifiedEvent(bean, method, parameterType, qualifier));
		}
	}
	
	public void fire(final Object event) {
		for (final DefaultEvent e : notQualifiedEvents) {
			if(!e.isSupported(event.getClass())) {
				continue;
			}
			execute(event, e);
		}
	}

	void fire(final Object event, final Annotation qualifier) {
		for (final QualifiedEvent e : qualifiedEvents) {
			if(!e.isSupported(event.getClass(), qualifier)) {
				continue;
			}
			execute(event, e);
		}
	}
	
	private void execute(final Object event, final DefaultEvent listener) {
		executor.execute(new Runnable() {
			public void run() {
				listener.doFire(event);
			}
		});
	}
	
	private class DefaultEvent {
		protected final Class<?> parameterType;
		private final Method method;
		private final Object bean;

		private DefaultEvent(Object bean, Method method, Class<?> parameterType) {
			this.bean = bean;
			this.method = method;
			this.parameterType = parameterType;
		}

		private boolean isSupported(Class<?> eventType) {
			return parameterType.isAssignableFrom(eventType);
		}

		void doFire(Object event) {
			try {
				method.invoke(bean, event);
			} catch (Throwable e) {
				throw new RuntimeException("error while handling event " + event.getClass().getName(), e);
			}
		}

	}
	
	private class QualifiedEvent extends DefaultEvent {
		
		private final Annotation qualifier;

		private QualifiedEvent(Object bean, Method method, Class<?> parameterType, Annotation qualifier) {
			super(bean, method, parameterType);
			this.qualifier = qualifier;
		}
		
		private boolean isSupported(Class<?> eventType, Annotation qaulifier) {
			return parameterType.isAssignableFrom(eventType) && this.qualifier.equals(qaulifier);
		}
	}
}
