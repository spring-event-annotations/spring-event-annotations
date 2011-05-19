package com.googlecode.spring.event.annotation;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;

import com.googlecode.spring.event.EventRegistry;


public class ObservesAnotationBeanPostProcessor implements BeanPostProcessor {
	
	private EventRegistry eventRegistry;
	
	public ObservesAnotationBeanPostProcessor() {
	
	}
	
	public void setEventRegistry(EventRegistry eventRegistry) {
		this.eventRegistry = eventRegistry;
	}
	
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		ReflectionUtils.doWithMethods(bean.getClass(), new ReflectionUtils.MethodCallback() {
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				int annotationsFound = 0;
				Class<?> annotatedParameterType = null;
				Class<?>[] parameterTypes = method.getParameterTypes();
				for(int i = 0; i < parameterTypes.length; i++) {
					Observes ann = new MethodParameter(method, i).getParameterAnnotation(Observes.class);
					if(ann != null) {
						annotationsFound++;
						annotatedParameterType = parameterTypes[i];
					}
				}
				if (annotationsFound > 1) {
					throw new IllegalStateException("Observer method must have exactly one annotated parameter, but found " + annotationsFound);
				}
				
				if(annotationsFound == 1) {
					if(method.isVarArgs()) {
						throw new IllegalStateException("Observer method was declared to take a variable number of arguments");
					}
					if (!method.getReturnType().getName().equals("void")) {
                        throw new IllegalStateException("Observer method " + method + " must return void");
                    }
					if (parameterTypes.length > 1) {
						throw new IllegalStateException("Observer method must have exactly one parameter, but found " + parameterTypes.length);
					}
					eventRegistry.registerEvent(bean, method, annotatedParameterType);
				}
			}
		}, ReflectionUtils.USER_DECLARED_METHODS);
		return bean;
	}

}
