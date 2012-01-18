package com.github.spring.event.samples.basic;

import static org.mockito.Mockito.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.spring.event.Event;
import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.annotation.ObservesAnotationBeanPostProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/loginListenerTestContext.xml")
public class LoginListenerTest {
	
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
