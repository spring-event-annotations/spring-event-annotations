/*
 * Copyright (C) 2015 CNH Industrial NV. All rights reserved.
 *
 * This software contains proprietary information of CNH Industrial NV. Neither
 * receipt nor possession thereof confers any right to reproduce, use, or
 * disclose in whole or in part any such information without written
 * authorization from CNH Industrial NV.
 */

package com.github.spring.event.samples.qualifiers;

import com.github.spring.event.Event;
import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.annotation.ObservesAnotationBeanPostProcessor;
import com.github.spring.event.config.EventConfiguration;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Executor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { EventConfiguration.class, ItemListenerWithQualifiersAnnotationTest.TestConfig.class })
public class ItemListenerWithQualifiersAnnotationTest {

	@Configuration
	@ComponentScan(value = {"com.github.spring.event.samples.qualifiers"})
	public static class TestConfig {

		@Bean
		@Primary
		public Executor getExecutor() {
			return new SyncTaskExecutor();
		}
	}

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@ItemCreated
	private Event<Item> itemCreatedEvent;
	
	@Autowired
	@ItemModified(someParam = "value")
	private Event<Item> itemModifiedEvent;
	
	private EventHandledCallback eventHandledCallback = mock(EventHandledCallback.class);
	
	@Before
	public void verifyContext() {
		Assert.assertNotNull(context.getBean(ObservesAnotationBeanPostProcessor.class));
	}
	
	@Test
	public void itemCreated() throws InterruptedException {
		itemCreatedEvent.fire(new Item(eventHandledCallback));
		verify(eventHandledCallback).eventHandled("itemCreated");
	}
	
	@Test
	public void itemModified() throws InterruptedException {
		itemModifiedEvent.fire(new Item(eventHandledCallback));
		verify(eventHandledCallback).eventHandled("itemModified");
	}
}
