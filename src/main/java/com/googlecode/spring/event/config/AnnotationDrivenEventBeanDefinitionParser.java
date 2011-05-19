package com.googlecode.spring.event.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.googlecode.spring.event.EventRegistry;
import com.googlecode.spring.event.annotation.ObservesAnotationBeanPostProcessor;

public class AnnotationDrivenEventBeanDefinitionParser implements BeanDefinitionParser {

	static final String EVENT_REGISTRY_BEAN_NAME = AnnotationDrivenEventBeanDefinitionParser.class.getPackage().getName() + ".internalEventRegistry";
	static final String ANNOTATION_POST_PROCESSOR_BEAN_NAME = AnnotationDrivenEventBeanDefinitionParser.class.getPackage().getName() + ".internalAnnotationBeanPostProcessor";
	
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		
		if (!parserContext.getRegistry().containsBeanDefinition(ANNOTATION_POST_PROCESSOR_BEAN_NAME)) {
			Object source = parserContext.extractSource(element);
			final RuntimeBeanReference eventRegistryReference = initializeEventRegistry(element, parserContext, source);
			initializeAnnotationPostProcessor(element, parserContext, source, eventRegistryReference);
		}
		return null;
	}

	public RuntimeBeanReference initializeEventRegistry(Element element, ParserContext parserContext, Object elementSource) {
		final RootBeanDefinition eventRegistry = new RootBeanDefinition(EventRegistry.class);
		eventRegistry.setSource(elementSource);
        eventRegistry.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        
        String executor = element.getAttribute("executor");
		if (StringUtils.hasText(executor)) {
			final BeanDefinitionRegistry registry = parserContext.getRegistry();
			eventRegistry.getPropertyValues().add("executor", registry.getBeanDefinition(executor));
		} else {
			eventRegistry.getPropertyValues().add("executor", new SimpleAsyncTaskExecutor());
		}
        
        final BeanDefinitionRegistry registry = parserContext.getRegistry();
        registry.registerBeanDefinition(EVENT_REGISTRY_BEAN_NAME, eventRegistry);
        return new RuntimeBeanReference(EVENT_REGISTRY_BEAN_NAME);
	}
	
	private RuntimeBeanReference initializeAnnotationPostProcessor(Element element, ParserContext parserContext, Object source,
			RuntimeBeanReference eventRegistryReference) {
		final RootBeanDefinition annotationPostProcessor = new RootBeanDefinition(ObservesAnotationBeanPostProcessor.class);
        annotationPostProcessor.setSource(source);
        annotationPostProcessor.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        final MutablePropertyValues propertyValues = annotationPostProcessor.getPropertyValues();
        propertyValues.addPropertyValue("eventRegistry", eventRegistryReference);
        
        final BeanDefinitionRegistry registry = parserContext.getRegistry();
        registry.registerBeanDefinition(ANNOTATION_POST_PROCESSOR_BEAN_NAME, annotationPostProcessor);
        return new RuntimeBeanReference(ANNOTATION_POST_PROCESSOR_BEAN_NAME);
	}
	
}
