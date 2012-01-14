package com.googlecode.spring.event.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;

import com.googlecode.spring.event.*;

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
		ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				if(!field.getType().isAssignableFrom(Event.class)) {
					return;
				}
				Annotation[] annotations = field.getAnnotations();
				for(Annotation ann : annotations) {
					if(ann.annotationType().isAnnotationPresent(Qualifier.class)) {					
						field.setAccessible(true);
						// TODO If there is already object for given annotation, we should re-use it here
						field.set(bean, new QualifiedEvent(eventRegistry, ann));						
					}
				}
			}
		}, ReflectionUtils.COPYABLE_FIELDS);
		
		ReflectionUtils.doWithMethods(bean.getClass(), new ReflectionUtils.MethodCallback() {
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				int observesAnnotationsFound = 0;
				Annotation qualifierAnnotation = null;				
				Class<?> annotatedParameterType = null;
				Class<?>[] parameterTypes = method.getParameterTypes();
				for(int i = 0; i < parameterTypes.length; i++) {
					Annotation[] paramAnnotations = new MethodParameter(method, i).getParameterAnnotations();
					for(int c = 0; c < paramAnnotations.length; c++) {
						if(observesAnnotationsFound == 1) {
							if(paramAnnotations[c].annotationType().isAnnotationPresent(Qualifier.class)) {
								qualifierAnnotation = paramAnnotations[c];
							}
							break;
						}
						if(paramAnnotations[c].annotationType().equals(Observes.class)) {
							observesAnnotationsFound++;
							annotatedParameterType = parameterTypes[i];
						}
					}
				}
				if (observesAnnotationsFound > 1) {
					throw new IllegalStateException("Observer method must have exactly one annotated parameter, but found " + observesAnnotationsFound);
				}
				
				if(observesAnnotationsFound == 1) {
					if(method.isVarArgs()) {
						throw new IllegalStateException("Observer method was declared to take a variable number of arguments");
					}
					if (!method.getReturnType().getName().equals("void")) {
                        throw new IllegalStateException("Observer method " + method + " must return void");
                    }
					if (parameterTypes.length > 1) {
						throw new IllegalStateException("Observer method must have exactly one parameter, but found " + parameterTypes.length);
					}
					eventRegistry.registerEvent(bean, method, annotatedParameterType, qualifierAnnotation);
				}
			}
		}, ReflectionUtils.USER_DECLARED_METHODS);
		return bean;
	}
}
