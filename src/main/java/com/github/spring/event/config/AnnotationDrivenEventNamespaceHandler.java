package com.github.spring.event.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class AnnotationDrivenEventNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		 this.registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenEventBeanDefinitionParser());
	}
}
