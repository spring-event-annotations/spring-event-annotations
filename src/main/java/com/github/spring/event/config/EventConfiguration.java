/*
 * Copyright (C) 2015 CNH Industrial NV. All rights reserved.
 *
 * This software contains proprietary information of CNH Industrial NV. Neither
 * receipt nor possession thereof confers any right to reproduce, use, or
 * disclose in whole or in part any such information without written
 * authorization from CNH Industrial NV.
 */

package com.github.spring.event.config;

import com.github.spring.event.EventRegistry;
import com.github.spring.event.annotation.ObservesAnotationBeanPostProcessor;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configures @Observes events
 * @author kedzie
 */
@Configuration
public class EventConfiguration {

   @Bean(name = AnnotationDrivenEventBeanDefinitionParser.EXECUTOR_BEAN_NAME)
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public Executor executor() {
      return new SimpleAsyncTaskExecutor();
   }

   @Bean(name = AnnotationDrivenEventBeanDefinitionParser.EVENT_REGISTRY_BEAN_NAME)
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public EventRegistry eventRegistry(Executor executor) {
      EventRegistry registry = new EventRegistry();
      registry.setExecutor(executor);
      return registry;
   }

   @Bean(name = AnnotationDrivenEventBeanDefinitionParser.ANNOTATION_POST_PROCESSOR_BEAN_NAME)
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public ObservesAnotationBeanPostProcessor eventAnnotationProcessor(EventRegistry r) {
      ObservesAnotationBeanPostProcessor processor = new ObservesAnotationBeanPostProcessor();
      processor.setEventRegistry(r);
      return processor;
   }
}
