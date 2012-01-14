package com.googlecode.spring.event.samples.qualifiers;

import static org.mockito.Mockito.*;
import junit.framework.Assert;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.spring.event.Event;
import com.googlecode.spring.event.EventHandledCallback;
import com.googlecode.spring.event.annotation.ObservesAnotationBeanPostProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/itemListenerWithQualifiersTestContext.xml")
@Ignore
public class ItemListenerWithQualifiersTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@ItemCreated
	private Event<Item> itemCreatedEvent;
	
	@Autowired
	@ItemModified
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
