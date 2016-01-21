/*
 * Copyright (C) 2015 CNH Industrial NV. All rights reserved.
 *
 * This software contains proprietary information of CNH Industrial NV. Neither
 * receipt nor possession thereof confers any right to reproduce, use, or
 * disclose in whole or in part any such information without written
 * authorization from CNH Industrial NV.
 */

package com.github.spring.event.samples.basic;

import com.github.spring.event.Event;
import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.annotation.Observes;
import com.github.spring.event.annotation.ObservesAnotationBeanPostProcessor;
import com.github.spring.event.config.EventConfiguration;
import com.github.spring.event.samples.qualifiers.Item;
import com.github.spring.event.samples.qualifiers.ItemEventListener;
import com.github.spring.event.samples.qualifiers.ItemModified;
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
@ContextConfiguration(classes={ EventConfiguration.class, LoginListenerAnnotationTest.TestConfig.class })
public class LoginListenerAnnotationTest {

	@Configuration
	@ComponentScan(value = {"com.github.spring.event.samples.basic"})
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
	private Event<LoggedInEvent> loggedInEvent;
	
	@Autowired
	private Event<LoggedOutEvent> loggedOutEvent;
	
	private EventHandledCallback eventHandledCallback = mock(EventHandledCallback.class);
	
	@Before
	public void verifyContext() {
		Assert.assertNotNull(context.getBean(ObservesAnotationBeanPostProcessor.class));
	}
	
	@Test
	public void receivedLoggedInEvent() throws InterruptedException {
		loggedInEvent.fire(new LoggedInEvent(eventHandledCallback));
		verify(eventHandledCallback).eventHandled("userLoggedIn");
	}
	
	@Test
	public void receivedLoggedOutEvent() throws InterruptedException {
		loggedOutEvent.fire(new LoggedOutEvent(eventHandledCallback));
		verify(eventHandledCallback).eventHandled("userLoggedOut");		
	}
}
